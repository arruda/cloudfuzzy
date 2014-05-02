# -*- coding: utf-8 -*-

from django.db import models

from django.utils.translation import ugettext_lazy as _


from fuzzy_modeling.models.adjectives import AdjectiveModel
from fuzzy_modeling.models.norms import NormModel

from fuzzy_modeling.models.systems import SystemModel
from fuzzy_modeling.models.operators import OperatorModel

from fuzzy.Rule import Rule
from fuzzy_modeling.models.utils import PyFuzzyMixin


class RuleModel(models.Model, PyFuzzyMixin):
    """
    A Fuzzy Rule model
    """

    class Meta:
        app_label = 'fuzzy_modeling'

    name = models.CharField(_("Name"), blank=False, null=False, max_length=250)

    adjective = models.ForeignKey(AdjectiveModel)
    cer = models.ForeignKey(NormModel)
    operator = models.ForeignKey(OperatorModel)

    certainty = models.DecimalField(
        _("Certainty"),
        max_digits=10,
        decimal_places=2,
        default=float("1")
    )

    # set to true so that it can be made by parts
    system = models.ForeignKey(SystemModel, blank=True, null=True)

    def get_pyfuzzy(self):
        """
        Return the Pyfuzzy class of this model
        """
        adjective = self.adjective.get_pyfuzzy()
        cer = self.cer.get_pyfuzzy()
        operator = self.operator.get_pyfuzzy()

        rule = Rule(
            adjective=adjective,
            operator=operator,
            certainty=self.certainty,
            CER=cer
        )

        return rule

    @classmethod
    def from_pyfuzzy(cls, pyfuzzy):
        """
        Return the model representation of an instance of the pyfuzzy attr
        """
        rule_model = cls(name=pyfuzzy.name, certainty=pyfuzzy.certainty)
        # rule_model.save()

        # adj
        adj_model = cls.adjective.field.related.parent_model.from_pyfuzzy(pyfuzzy.adjective)
        rule_model.adjective = adj_model

        # cer
        cer_model = cls.cer.field.related.parent_model.from_pyfuzzy(pyfuzzy.CER)
        rule_model.cer = cer_model

        # operator
        op_model = cls.operator.field.related.parent_model.from_pyfuzzy(pyfuzzy.operator)
        rule_model.operator = op_model

        rule_model.save()

        return rule_model

    def __unicode__(self):
        return self.name
