# -*- coding: utf-8 -*-

from django.db import models


from django.utils.translation import ugettext_lazy as _


from fuzzy.System import System



class SystemModel(models.Model):
    """
    A Fuzzy system model
    """

    class Meta:
        app_label = 'fuzzy_modeling'

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

        for rule in self.rulemodel_set.all():
            fuzzy_rule = rule.get_pyfuzzy()
            system.rules[rule.name] = fuzzy_rule


        return system

