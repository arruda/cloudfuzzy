# -*- coding: utf-8 -*-
import inspect

from django.db import models

from django.contrib.contenttypes import generic

from django.utils.translation import ugettext_lazy as _


from fuzzy_modeling.utils import get_class_by_python_path, get_choices_from_python_path_listing
from fuzzy_modeling.models.utils import PyFuzzyMixin
from fuzzy_modeling.models.parameters import ParameterModel


class NormModel(models.Model, PyFuzzyMixin):
    """
    A Fuzzy Norm base model
    """

    class Meta:
        app_label = 'fuzzy_modeling'

    NORM_CHOICES = get_choices_from_python_path_listing('fuzzy.norm',ignores=['Norm','ParametricNorm',])

    norm_type = models.CharField(_("Norm Type"),
                choices=NORM_CHOICES,
                max_length=250,
                blank=False, null=False,
                default=NORM_CHOICES[0][0]
            )

    parameters = generic.GenericRelation(ParameterModel)

    def get_pyfuzzy(self):
        """
        Return the Pyfuzzy class of this model
        """
        NormClass = get_class_by_python_path(self.norm_type)
        parameters_dict = {}
        for p in self.parameters.all():
            parameters_dict[p.name] = p.get_value()
        norm = NormClass(**parameters_dict)
        return norm

    @classmethod
    def from_pyfuzzy(cls, pyfuzzy):
        """
        Return the model representation of an instance of the pyfuzzy attr
        """

        norm_model = cls()
        # import bpdb; bpdb.set_trace()

        norm_type = 'fuzzy.norm.%s.%s' % (
                pyfuzzy.__class__.__name__ ,
                pyfuzzy.__class__.__name__
            )
        norm_model.norm_type = norm_type
        norm_model.save()

        # parameters
        for arg in inspect.getargspec(pyfuzzy.__init__).args:
            if arg != 'self':
                arg_value = getattr(pyfuzzy,arg)
                arg_type = ParameterModel.get_type_from_python_type(arg_value)
                norm_model.parameters.create(
                            name = arg,
                            value = arg_value,
                            value_type = arg_type
                    )



        norm_model.save()
        return norm_model

    def __unicode__(self):
        return self.get_norm_type_display()

    # @classmethod
    # def print_all_init_args(cls):

    #     for norm_type_tuple in NormModel.NORM_CHOICES:
    #         norm_type = norm_type_tuple[0]

    #         PyfuzzyNormClass = get_class_by_python_path(norm_type)

    #         try:
    #             inspect_init = inspect.getargspec(PyfuzzyNormClass.__init__)
    #         except TypeError:
    #             pass
    #         else:
    #             print "Class: %s" % norm_type
    #             print ">>args", inspect_init.args
