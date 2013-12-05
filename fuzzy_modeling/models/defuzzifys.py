# -*- coding: utf-8 -*-

from django.db import models

from django.contrib.contenttypes import generic

from django.utils.translation import ugettext_lazy as _


from fuzzy_modeling.utils import get_class_by_python_path, get_choices_from_python_path_listing
from fuzzy_modeling.models.norms import NormModel
from fuzzy_modeling.models.parameters import ParameterModel


class DefuzzifyModel(models.Model):
    """
    A Fuzzy defuzzify base model
    """
    class Meta:
        app_label = 'fuzzy_modeling'

    DEFUZZIFY_CHOICES = get_choices_from_python_path_listing('fuzzy.defuzzify',ignores=['Base',])
    # (
    #     ('fuzzy.defuzzify.COG.COG', _("COG")),
    #     ('fuzzy.defuzzify.Dict.Dict', _("Dict")),
    #     ('fuzzy.defuzzify.COGS.COGS', _("COGS")),
    #     ('fuzzy.defuzzify.LM.LM', _("LM")),
    #     ('fuzzy.defuzzify.MaxLeft.MaxLeft', _("MaxLeft")),
    #     ('fuzzy.defuzzify.MaxRight.MaxRight', _("MaxRight")),
    #     ('fuzzy.defuzzify.RM.RM', _("RM")),
    # )


    defuzzify = models.CharField(_("Defuzzify"),
                choices=DEFUZZIFY_CHOICES,
                max_length=250,
                blank=False, null=False,
                default=DEFUZZIFY_CHOICES[0][0]
            )

    inf =   models.ForeignKey( NormModel , related_name="defuzzify_inf_set" )
    acc =   models.ForeignKey( NormModel, related_name="defuzzify_acc_set")

    parameters = generic.GenericRelation(ParameterModel)

    def get_pyfuzzy(self):
        """
        Return the Pyfuzzy class of this model
        """
        DefuzzifyClass = get_class_by_python_path(self.defuzzify)
        inf  = self.inf.get_pyfuzzy()
        acc  = self.acc.get_pyfuzzy()

        # parameters =
        parameters_dict = {
            'INF': inf,
            'ACC':acc
        }
        for p in self.parameters.all():
            parameters_dict[p.name] = p.get_value()

        defuzzify = DefuzzifyClass(**parameters_dict)
        return defuzzify


    def __unicode__(self):
        pre = ""
        for choice in self.DEFUZZIFY_CHOICES:
            if self.defuzzify == choice[0]:
                pre = choice[1]

        return pre
