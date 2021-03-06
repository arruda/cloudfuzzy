# -*- coding: utf-8 -*-

from django.db import models


from django.utils.translation import ugettext_lazy as _

from fuzzy.Adjective import Adjective


from fuzzy_modeling.models.sets import SetModel
from fuzzy_modeling.models.norms import NormModel
from fuzzy_modeling.models.variables import InputVariableModel
from fuzzy_modeling.models.utils import PyFuzzyMixin
from fuzzy_modeling.models.variables import OutputVariableModel


class AdjectiveModel(models.Model, PyFuzzyMixin):
    """
    A Fuzzy Adjective base model
    """
    class Meta:
        app_label = 'fuzzy_modeling'

    name = models.CharField(_("Name"), blank=True, null=True, max_length=250)

    set = models.ForeignKey(SetModel)
    com = models.ForeignKey(NormModel, null=True, blank=True)

    ivar = models.ForeignKey(InputVariableModel, null=True, blank=True)
    ovar = models.ForeignKey(OutputVariableModel, null=True, blank=True)

    def get_pyfuzzy(self):
        """
        Return the Pyfuzzy class of this model
        """
        set = self.set.get_pyfuzzy()
        com = self.com.get_pyfuzzy() if self.com else None
        kwargs = {
            'set': set,
        }
        if com:
            kwargs['COM'] = com

        adjective = Adjective(**kwargs)
        return adjective

    @classmethod
    def from_pyfuzzy(cls, pyfuzzy):
        """
        Return the model representation of an instance of the pyfuzzy attr
        """
        try:
            name = pyfuzzy.name
        except AttributeError:
            name = None

        adj_model = cls(
                name=name
        )

        # SET
        set_model = cls.set.field.related.parent_model.from_pyfuzzy(pyfuzzy.set)
        adj_model.set = set_model

        # COM
        if pyfuzzy.COM:
            com_model = cls.com.field.related.parent_model.from_pyfuzzy(pyfuzzy.COM)
            adj_model.com = com_model

        adj_model.save()
        return adj_model

    def __unicode__(self):
        return self.name

    @classmethod
    def _get_existing_adjective_model(cls, system, systemModel, adjective):

        # import pdb; pdb.set_trace()
        adj_name, var_name = system.findAdjectiveName(adjective)
        var = system.variables[var_name]

        #: input
        if hasattr(var, 'fuzzify'):
            vars_pks = systemModel.inputvariablemodel_set.filter(name=var_name).values_list('pk', flat=True)
            return AdjectiveModel.objects.get(name=adj_name, ivar__in=vars_pks)
        else:
            vars_pks = systemModel.outputvariablemodel_set.filter(name=var_name).values_list('pk', flat=True)
            return AdjectiveModel.objects.get(name=adj_name, ovar__in=vars_pks)
