# -*- coding: utf-8 -*-

from decimal import Decimal

from django.db import models

from django.contrib.contenttypes.models import ContentType
from django.contrib.contenttypes import generic

from django.utils.translation import ugettext_lazy as _


from fuzzy.System import System
from fuzzy.Variable import Variable
from fuzzy.InputVariable import InputVariable
from fuzzy.OutputVariable import OutputVariable
from fuzzy.Adjective import Adjective


from fuzzy_modeling.models.systems import SystemModel
from fuzzy_modeling.models.defuzzifys import DefuzzifyModel

from fuzzy_modeling.utils import get_class_by_python_path, get_choices_from_python_path_listing

class VariableModel(models.Model):
    """
    A Fuzzy variable model
    """

    class Meta:
        abstract = True
        app_label = 'fuzzy_modeling'

    name = models.CharField(_("Name"), blank=False, null=False, max_length=250)

    description = models.TextField(_("Description"))
    min = models.DecimalField(_("Min"),max_digits=10, decimal_places=2,default=Decimal("0"))
    max = models.DecimalField(_("Max"),max_digits=10, decimal_places=2,default=Decimal("0"))
    unit = models.CharField(_("Unit"), max_length=250)

    system = models.ForeignKey(SystemModel, blank=False, null=False)

    def get_pyfuzzy(self):
        """
        Return the Pyfuzzy class of this model
        """
        var = Variable(description=self.description,min=self.min, max=self.max, unit=self.unit)
        return var

    def __unicode__(self):
        return self.name


class InputVariableModel(VariableModel):
    """
    A Fuzzy input variable model
    """

    class Meta(VariableModel.Meta):
        abstract = False

    FUZZIFY_CHOICES = get_choices_from_python_path_listing('fuzzy.fuzzify',ignores=['Base',])
    # (
    #     ('fuzzy.fuzzify.Plain.Plain', _("Plain")),
    #     ('fuzzy.fuzzify.Dict.Dict', _("Dict")),
    # )


    fuzzify = models.CharField(_("Fuzzify"),
                choices=FUZZIFY_CHOICES,
                max_length=250,
                blank=False, null=False,
                default=FUZZIFY_CHOICES[0][0]
            )

    def get_pyfuzzy(self):
        """
        Return the Pyfuzzy class of this model
        """
        FuzzifyClass = get_class_by_python_path(self.fuzzify)

        ivar = InputVariable(
            fuzzify=FuzzifyClass(),
            description=self.description,
            min=self.min,
            max=self.max,
            unit=self.unit
        )
        adjs = self.adjectivemodel_set.all()
        for adj in adjs:
            ivar.adjectives[adj.name] = adj.get_pyfuzzy()

        return ivar


class OutputVariableModel(VariableModel):
    """
    A Fuzzy output variable model
    """

    class Meta(VariableModel.Meta):
        abstract = False


    defuzzify = models.ForeignKey( DefuzzifyModel )

    def get_pyfuzzy(self):
        """
        Return the Pyfuzzy class of this model
        """
        defuzzify = self.defuzzify.get_pyfuzzy()

        ovar = OutputVariable(
            defuzzify=defuzzify,
            description=self.description,
            min=self.min,
            max=self.max,
            unit=self.unit
        )
        return ovar
