# -*- coding: utf-8 -*-

from decimal import Decimal

from django.db import models

from django.contrib.contenttypes import generic

from django.utils.translation import ugettext_lazy as _


from fuzzy_modeling.models.parameters import ParameterModel
from fuzzy_modeling.utils import get_class_by_python_path, get_choices_from_python_path_listing


class SetModel(models.Model):
    """
    A Fuzzy set base model
    """

    class Meta:
        app_label = 'fuzzy_modeling'

    SET_CHOICES = get_choices_from_python_path_listing('fuzzy.set',ignores=['operations',])

    set = models.CharField(_("Set"),
                choices=SET_CHOICES,
                max_length=250,
                blank=False, null=False,
                default=SET_CHOICES[0][0]
            )

    parameters = generic.GenericRelation(ParameterModel)

    def __unicode__(self):

        return self.get_set_display()

    def get_pyfuzzy(self):
        """
        Return the Pyfuzzy class of this model
        """
        SetClass = get_class_by_python_path(self.set)

        parameters_dict = {
        }

        if self.get_set_display() == 'Polygon':
            import re
            p = self.parameters.all()[0]
            # change '(1,2),(3,4)' to ['', '1,2', ',', '3,4', '']
            points =[]
            tmp = re.split("\(|\)", p.value)
            for element in tmp:
                if element != '' and element != ',':
                    x,y = element.split(',')
                    x = Decimal(x)
                    y = Decimal(y)
                    points.append((x,y))

            parameters_dict[p.name]= points
        else:
            for p in self.parameters.all():
                parameters_dict[p.name] = p.get_value()



        set = SetClass(**parameters_dict)
        return set
