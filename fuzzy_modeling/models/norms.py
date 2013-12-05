# -*- coding: utf-8 -*-

from django.db import models

from django.contrib.contenttypes import generic

from django.utils.translation import ugettext_lazy as _


from fuzzy_modeling.utils import get_class_by_python_path, get_choices_from_python_path_listing
from fuzzy_modeling.models.parameters import ParameterModel


class NormModel(models.Model):
    """
    A Fuzzy Norm base model
    """

    class Meta:
        app_label = 'fuzzy_modeling'

    NORM_CHOICES = get_choices_from_python_path_listing('fuzzy.norm',ignores=['Norm',])

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


    def __unicode__(self):
        pre = ""
        for choice in self.NORM_CHOICES:
            if self.norm_type == choice[0]:
                pre = choice[1]

        return pre
