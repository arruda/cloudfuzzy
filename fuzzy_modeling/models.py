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


from fuzzy_modeling.utils import get_class_by_python_path, get_choices_from_python_path_listing


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

        for ovar in self.outputvariablemodel_set.all():
            fuzzy_var = ovar.get_pyfuzzy()
            system.variables[ovar.name] = fuzzy_var


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
        return ivar


    def __unicode__(self):
        return self.description

class ParameterModel(models.Model):
    """A simple parameter in a funcion.
        Has the param name and value, and it's type
    """

    TYPE_CHOICES = (
        ('boolean', _("Boolean")),
        ('string', _("String")),
        ('decimal', _("Decimal")),
        ('integer', _("Integer")),
    )

    name = models.CharField(_("Name"),
                max_length=250,
                blank=False, null=False,
            )
    value = models.CharField(_("Value"),
                max_length=250,
                blank=False, null=False,
            )
    value_type = models.CharField(_("Value Type"),
                choices=TYPE_CHOICES,
                max_length=80,
                blank=False, null=False,
                default=TYPE_CHOICES[0][0]
            )

    content_type = models.ForeignKey(ContentType)
    object_id = models.PositiveIntegerField()
    content_object = generic.GenericForeignKey('content_type', 'object_id')

    def get_value(self):
        if self.value_type == self.TYPE_CHOICES[0][0]:
            return self.value == "True"

        elif  self.value_type == self.TYPE_CHOICES[1][0]:
            return self.value

        elif  self.value_type == self.TYPE_CHOICES[2][0]:
            return Decimal(self.value)

        elif  self.value_type == self.TYPE_CHOICES[3][0]:
            return Decimal(self.value)



class NormModel(models.Model):
    """
    A Fuzzy Norm base model
    """
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

class DefuzzifyModel(models.Model):
    """
    A Fuzzy defuzzify base model
    """

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

class OutputVariableModel(VariableModel):
    """
    A Fuzzy output variable model
    """


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


    def __unicode__(self):
        return self.description

class SetModel(models.Model):
    """
    A Fuzzy set base model
    """

    SET_CHOICES = get_choices_from_python_path_listing('fuzzy.set',ignores=['operations',])

    set = models.CharField(_("Set"),
                choices=SET_CHOICES,
                max_length=250,
                blank=False, null=False,
                default=SET_CHOICES[0][0]
            )

    parameters = generic.GenericRelation(ParameterModel)

    def __unicode__(self):
        pre = ""
        for choice in self.SET_CHOICES:
            if self.set == choice[0]:
                pre = choice[1]

        return pre

    def get_pyfuzzy(self):
        """
        Return the Pyfuzzy class of this model
        """
        SetClass = get_class_by_python_path(self.set)

        parameters_dict = {
        }
        for p in self.parameters.all():
            parameters_dict[p.name] = p.get_value()

        set = SetClass(**parameters_dict)
        return set


class AdjectiveModel(models.Model):
    """
    A Fuzzy Adjective base model
    """


    set =   models.ForeignKey( SetModel  )
    com =   models.ForeignKey( NormModel , null=True, blank=True)

    ivar =   models.ForeignKey( InputVariableModel , null=True, blank=True)
    ovar =   models.ForeignKey( OutputVariableModel , null=True, blank=True)


    def get_pyfuzzy(self):
        """
        Return the Pyfuzzy class of this model
        """
        set  = self.set.get_pyfuzzy()
        com  = self.com.get_pyfuzzy() if self.com else None
        kwargs = {
            'set':set,
        }
        if com:
            kwargs['com'] = com

        adjective = Adjective(**kwargs)
        return adjective


    def __unicode__(self):
        pre = ""
        for choice in self.DEFUZZIFY_CHOICES:
            if self.defuzzify == choice[0]:
                pre = choice[1]

        return pre
