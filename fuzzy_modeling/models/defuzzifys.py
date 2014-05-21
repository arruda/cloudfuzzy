# -*- coding: utf-8 -*-

import inspect

from django.db import models

from django.contrib.contenttypes import generic

from django.utils.translation import ugettext_lazy as _


from fuzzy_modeling.utils import get_class_by_python_path, get_choices_from_python_path_listing
from fuzzy_modeling.models.norms import NormModel
from fuzzy_modeling.models.utils import PyFuzzyMixin
from fuzzy_modeling.models.parameters import ParameterModel


class DefuzzifyModel(models.Model, PyFuzzyMixin):
    """
    A Fuzzy defuzzify base model
    """
    class Meta:
        app_label = 'fuzzy_modeling'

    DEFUZZIFY_CHOICES = get_choices_from_python_path_listing(
        'fuzzy.defuzzify',
        ignores=['Base', ]
    )
    # (
    #     ('fuzzy.defuzzify.COG.COG', _("COG")),
    #     ('fuzzy.defuzzify.Dict.Dict', _("Dict")),
    #     ('fuzzy.defuzzify.COGS.COGS', _("COGS")),
    #     ('fuzzy.defuzzify.LM.LM', _("LM")),
    #     ('fuzzy.defuzzify.MaxLeft.MaxLeft', _("MaxLeft")),
    #     ('fuzzy.defuzzify.MaxRight.MaxRight', _("MaxRight")),
    #     ('fuzzy.defuzzify.RM.RM', _("RM")),
    # )

    defuzzify = models.CharField(
                _("Defuzzify"),
                choices=DEFUZZIFY_CHOICES,
                max_length=250,
                blank=False, null=False,
                default=DEFUZZIFY_CHOICES[0][0]
            )

    inf = models.ForeignKey(NormModel, related_name="defuzzify_inf_set", blank=True, null=True)
    acc = models.ForeignKey(NormModel, related_name="defuzzify_acc_set", blank=True, null=True)

    parameters = generic.GenericRelation(ParameterModel)

    def get_pyfuzzy(self):
        """
        Return the Pyfuzzy class of this model
        """
        DefuzzifyClass = get_class_by_python_path(self.defuzzify)

        inf = self.inf.get_pyfuzzy() if self.inf else None
        acc = self.acc.get_pyfuzzy() if self.acc else None

        # parameters =
        parameters_dict = {
            'INF': inf,
            'ACC': acc
        }
        for p in self.parameters.all():
            if p.name != 'INF' and p.name != 'ACC':
                parameters_dict[p.name] = p.get_value()

        defuzzify = DefuzzifyClass(**parameters_dict)
        return defuzzify

    @classmethod
    def from_pyfuzzy(cls, pyfuzzy):
        """
        Return the model representation of an instance of the pyfuzzy attr
        """
        defuzz_model = cls()

        defuzzify = 'fuzzy.defuzzify.%s.%s' % (
                pyfuzzy.__class__.__name__,
                pyfuzzy.__class__.__name__
        )
        defuzz_model.defuzzify = defuzzify

        # INF
        inf_model = None
        if pyfuzzy.INF:
            inf_model = cls.inf.field.related.parent_model.from_pyfuzzy(pyfuzzy.INF)
        defuzz_model.inf = inf_model

        # ACC
        acc_model = None
        if pyfuzzy.ACC:
            acc_model = cls.acc.field.related.parent_model.from_pyfuzzy(pyfuzzy.ACC)
        defuzz_model.acc = acc_model

        defuzz_model.save()

        # parameters
        for arg in inspect.getargspec(pyfuzzy.__init__).args:
            if arg != 'self':
                arg_value = getattr(pyfuzzy, arg)
                if arg_value is not None:
                    arg_type = ParameterModel.get_type_from_python_type(arg_value)
                    defuzz_model.parameters.create(
                                name=arg,
                                value=arg_value,
                                value_type=arg_type
                    )

        defuzz_model.save()

        return defuzz_model

    def __unicode__(self):

        return self.get_defuzzify_display()
