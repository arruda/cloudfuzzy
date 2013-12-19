# -*- coding: utf-8 -*-

from django.db import models


from django.utils.translation import ugettext_lazy as _


from fuzzy_modeling.models.utils import PyFuzzyMixin
from fuzzy.System import System



class SystemModel(models.Model, PyFuzzyMixin):
    """
    A Fuzzy system model
    """

    class Meta:
        app_label = 'fuzzy_modeling'

    description = models.TextField(_("Description"))

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

        for rule in self.rulemodel_set.all():
            fuzzy_rule = rule.get_pyfuzzy()
            system.rules[rule.name] = fuzzy_rule


        return system

    @classmethod
    def from_pyfuzzy(cls, pyfuzzy):
        """
        Return the model representation of an instance of the pyfuzzy attr
        """
        system_model = cls()
        system_model.description = pyfuzzy.description
        system_model.save()

        #variables
        for v_name, pyfuzzy_var in pyfuzzy.variables.items():
            # set the name of this var inside the pyfuzzy_var
            pyfuzzy_var.name = v_name

            # is an output variable
            if pyfuzzy_var.__class__.__name__ == 'OutputVariable':
                OutputvarModel = system_model.outputvariablemodel_set.model
                outputvar_model = OutputvarModel.from_pyfuzzy(pyfuzzy_var)
                system_model.outputvariablemodel_set.add(outputvar_model)

            # is an input variable
            else:
                InputvarModel = system_model.inputvariablemodel_set.model
                inputvar_model = InputvarModel.from_pyfuzzy(pyfuzzy_var)
                system_model.inputvariablemodel_set.add(inputvar_model)


        # rules
        for r_name, rule in pyfuzzy.rules.items():
            # set the name of this var to be used latter
            rule.name = r_name
            rule_model = system_model.rulemodel_set.model.from_pyfuzzy(rule)
            system_model.rulemodel_set.add(rule_model)


        system_model.save()
        return system_model

