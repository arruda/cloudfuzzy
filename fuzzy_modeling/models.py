# -*- coding: utf-8 -*-

from decimal import Decimal

from django.db import models

from django.utils.translation import ugettext_lazy as _


from fuzzy.System import System
from fuzzy.Variable import Variable
from fuzzy.InputVariable import InputVariable

from fuzzy_modeling.utils import get_class_by_python_path


class SystemModel(models.Model):
    """
    A Fuzzy system model
    """

    description = models.TextField(_("Description"))

    # class Meta:
    #     verbose_name_plural = "Colaboradores"

    def __unicode__(self):
        return self.description

    def get_pyfuzzy(self):
        """
        Return the Pyfuzzy class of this model
        """
        system = System(description=self.description)
        for ivar in self.inputvariablemodel_set.all():
            fuzzy_var = ivar.get_pyfuzzy()
            system.variables[ivar.name] = fuzzy_var


        return system



class VariableModel(models.Model):
    """
    A Fuzzy variable model
    """

    class Meta:
        abstract = True

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
        return self.description


class InputVariableModel(VariableModel):
    """
    A Fuzzy input variable model
    """

    FUZZIFY_CHOICES = (
        ('fuzzy.fuzzify.Plain.Plain', _("Plain")),
        ('fuzzy.fuzzify.Dict.Dict', _("Dict")),
    )


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
        return ivar


    def __unicode__(self):
        return self.description
