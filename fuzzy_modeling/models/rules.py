# -*- coding: utf-8 -*-
from decimal import Decimal

from django.db import models

from django.utils.translation import ugettext_lazy as _


from fuzzy_modeling.models.adjectives import AdjectiveModel
from fuzzy_modeling.models.norms import NormModel

from fuzzy_modeling.models.systems import SystemModel
from fuzzy_modeling.models.operators import OperatorModel

from fuzzy.Rule import Rule

class RuleModel(models.Model):
    """
    A Fuzzy Rule model
    """

    class Meta:
        app_label = 'fuzzy_modeling'

    name = models.CharField(_("Name"), blank=False, null=False, max_length=250)

    adjective = models.ForeignKey(AdjectiveModel)
    cer = models.ForeignKey(NormModel)
    operator = models.ForeignKey(OperatorModel)

    certainty = models.DecimalField(_("Certainty"),max_digits=10, decimal_places=2,default=Decimal("1"))

    system = models.ForeignKey(SystemModel, blank=False, null=False)

    def get_pyfuzzy(self):
        """
        Return the Pyfuzzy class of this model
        """
        adjective = self.adjective.get_pyfuzzy()
        cer = self.cer.get_pyfuzzy()
        operator = self.operator.get_pyfuzzy()

        rule = Rule(adjective=adjective,operator=operator,certainty=self.certainty,CER=cer)

        return rule

    def __unicode__(self):
        return self.name
