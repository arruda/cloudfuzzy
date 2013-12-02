# -*- coding: utf-8 -*-
from django.contrib import admin
from django.contrib.contenttypes.generic import GenericStackedInline
from fuzzy_modeling.models import SystemModel, VariableModel, InputVariableModel, ParameterModel, DefuzzifyModel, OutputVariableModel

from fuzzy_modeling.models import NormModel

class ParameterModelInlineAdmin(GenericStackedInline):
    model = ParameterModel

class DefuzzifyModelAdmin(admin.ModelAdmin):
    inlines = [ParameterModelInlineAdmin,]

class NormModelAdmin(admin.ModelAdmin):
    inlines = [ParameterModelInlineAdmin,]




admin.site.register(SystemModel, admin.ModelAdmin)
admin.site.register(InputVariableModel, admin.ModelAdmin)
admin.site.register(DefuzzifyModel, DefuzzifyModelAdmin)
admin.site.register(OutputVariableModel, admin.ModelAdmin)
admin.site.register(NormModel, NormModelAdmin)
