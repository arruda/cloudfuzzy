# -*- coding: utf-8 -*-
from django.contrib import admin

from fuzzy_modeling.models import SystemModel, VariableModel, InputVariableModel

admin.site.register(SystemModel, admin.ModelAdmin)
# admin.site.register(VariableModel, admin.ModelAdmin)
admin.site.register(InputVariableModel, admin.ModelAdmin)
