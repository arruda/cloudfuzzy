# -*- coding: utf-8 -*-
from django.contrib import admin
from django.contrib.contenttypes.generic import GenericStackedInline
from fuzzy_modeling.models import SystemModel, VariableModel, InputVariableModel, ParameterModel, DefuzzifyModel, OutputVariableModel

from fuzzy_modeling.models import NormModel, AdjectiveModel, SetModel

from fuzzy_modeling.models import RuleModel, OperatorModel


class ParameterModelInlineAdmin(GenericStackedInline):
    model = ParameterModel

class DefuzzifyModelAdmin(admin.ModelAdmin):
    inlines = [ParameterModelInlineAdmin,]

class NormModelAdmin(admin.ModelAdmin):
    inlines = [ParameterModelInlineAdmin,]


class OutputVariableModelInlineAdmin(admin.StackedInline):
    model = OutputVariableModel
    extra = 2

class InputVariableModelInlineAdmin(admin.StackedInline):
    model = InputVariableModel
    extra = 2

class SystemModelAdmin(admin.ModelAdmin):
    inlines = [InputVariableModelInlineAdmin,OutputVariableModelInlineAdmin]


class AdjectiveModelInlineForInputAdmin(admin.StackedInline):
    model = AdjectiveModel
    exclude = ['ovar',]
    extra = 2


class InputVariableModelAdmin(admin.ModelAdmin):
    inlines = [AdjectiveModelInlineForInputAdmin]

class AdjectiveModelInlineForOutputAdmin(admin.StackedInline):
    model = AdjectiveModel
    exclude = ['ivar',]
    extra = 2

class OutputVariableModelAdmin(admin.ModelAdmin):
    inlines = [AdjectiveModelInlineForOutputAdmin]


class SetModelAdmin(admin.ModelAdmin):
    inlines = [ParameterModelInlineAdmin,]


class OperatorModelInlineAdmin(admin.StackedInline):
    model = OperatorModel
    fk_name = "compound_inputs"
    extra = 2


class OperatorModeldmin(admin.ModelAdmin):
    inlines = [OperatorModelInlineAdmin]

admin.site.register(SystemModel, SystemModelAdmin)
admin.site.register(DefuzzifyModel, DefuzzifyModelAdmin)
admin.site.register(NormModel, NormModelAdmin)

admin.site.register(AdjectiveModel, admin.ModelAdmin)
admin.site.register(SetModel, SetModelAdmin)
admin.site.register(InputVariableModel, InputVariableModelAdmin)
admin.site.register(OutputVariableModel, OutputVariableModelAdmin)

admin.site.register(RuleModel, admin.ModelAdmin)
admin.site.register(OperatorModel, OperatorModeldmin)
