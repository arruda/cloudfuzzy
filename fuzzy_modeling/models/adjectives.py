# -*- coding: utf-8 -*-

from django.db import models


from django.utils.translation import ugettext_lazy as _

from fuzzy.Adjective import Adjective


from fuzzy_modeling.models.sets import SetModel
from fuzzy_modeling.models.norms import NormModel
from fuzzy_modeling.models.variables import InputVariableModel
from fuzzy_modeling.models.variables import OutputVariableModel


class AdjectiveModel(models.Model):
    """
    A Fuzzy Adjective base model
    """
    class Meta:
        app_label = 'fuzzy_modeling'

    name = models.CharField(_("Name"), blank=False, null=False, max_length=250)

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
        return self.name
