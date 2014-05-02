# -*- coding: utf-8 -*-

from django.db import models

from django.contrib.contenttypes.models import ContentType
from django.contrib.contenttypes import generic

from django.utils.translation import ugettext_lazy as _


class ParameterModel(models.Model):
    """A simple parameter in a funcion.
        Has the param name and value, and it's type
    """

    class Meta:
        app_label = 'fuzzy_modeling'

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

        elif self.value_type == self.TYPE_CHOICES[1][0]:
            return self.value

        elif self.value_type == self.TYPE_CHOICES[2][0]:
            return float(self.value)

        elif self.value_type == self.TYPE_CHOICES[3][0]:
            return float(self.value)

    @classmethod
    def get_type_from_python_type(cls, obj):

        if 'bool' in type(obj).__name__.lower():
            return cls.TYPE_CHOICES[0][0]

        if 'float' in type(obj).__name__.lower() or 'decimal' in type(obj).__name__.lower():
            return cls.TYPE_CHOICES[2][0]

        if 'int' in type(obj).__name__.lower():
            return cls.TYPE_CHOICES[3][0]

        #if none of the above return string
        return cls.TYPE_CHOICES[1][0]
