# -*- coding: utf-8 -*-
from decimal import Decimal

from django.db import models

from django.utils.translation import ugettext_lazy as _

from mptt.models import MPTTModel, TreeForeignKey

from fuzzy_modeling.models.norms import NormModel

from fuzzy_modeling.utils import get_class_by_python_path
from fuzzy_modeling.models.adjectives import AdjectiveModel
from fuzzy_modeling.models.utils import PyFuzzyMixin


class OperatorModel(MPTTModel, PyFuzzyMixin):
    """
    A Fuzzy Operator base model
    this one handles all operators types
    """

    class Meta:
        app_label = 'fuzzy_modeling'

    class MPTTMeta:
        parent_attr = 'compound_inputs'
        # order_insertion_by = ['id']

    OPERATOR_TYPES = (
        ('fuzzy.operator.Compound.Compound', _('Compound')),
        ('fuzzy.operator.Const.Const', _('Const')),
        ('fuzzy.operator.Input.Input', _('Input')),
        ('fuzzy.operator.Not.Not', _('Not')),
    )

    operator_type = models.CharField(_("Operator Type"),
                choices=OPERATOR_TYPES,
                max_length=250,
                blank=False, null=False,
                default=OPERATOR_TYPES[0][0]
            )

    compound_norm = models.ForeignKey(NormModel, null=True, blank=True)
    compound_inputs = TreeForeignKey(
        'self',
        related_name="compound_inputs_children",
        null=True,
        blank=True,
        verbose_name=_('Input')
    )

    const_value = models.DecimalField(
        _("Constant Value"),
        max_digits=10,
        decimal_places=2,
        default=float("0"),
        blank=True,
        null=True
    )

    input_adjective = models.ForeignKey(AdjectiveModel, blank=True, null=True)

    not_input = models.ForeignKey('self', blank=True, null=True, related_name='not_input_set')

    def _get_pyfuzzy_compound(self):
        """
        Return the Pyfuzzy class of this model for the Compound type
        """
        Compound = get_class_by_python_path(self.operator_type)
        norm = self.compound_norm.get_pyfuzzy()
        inputs = [op.get_pyfuzzy() for op in self.get_children()]

        compound = Compound(norm, *inputs)
        return compound

    def _get_pyfuzzy_const(self):
        """
        Return the Pyfuzzy class of this model for the Const type
        """
        Const = get_class_by_python_path(self.operator_type)
        value = self.const_value

        const = Const(value)
        return const

    def _get_pyfuzzy_input(self):
        """
        Return the Pyfuzzy class of this model for the Input type
        """
        Input = get_class_by_python_path(self.operator_type)
        adjective = self.input_adjective.get_pyfuzzy()

        input_op = Input(adjective)
        return input_op

    def _get_pyfuzzy_not(self):
        """
        Return the Pyfuzzy class of this model for the Not type
        """
        Not = get_class_by_python_path(self.operator_type)
        op = self.not_input.get_pyfuzzy()

        not_op = Not(op)
        return not_op

    def get_pyfuzzy(self):
        """
        Return the Pyfuzzy class of this model
        """
        if self.operator_type == 'fuzzy.operator.Compound.Compound':
            return self._get_pyfuzzy_compound()

        if self.operator_type == 'fuzzy.operator.Const.Const':
            return self._get_pyfuzzy_const()

        if self.operator_type == 'fuzzy.operator.Input.Input':
            return self._get_pyfuzzy_input()

        if self.operator_type == 'fuzzy.operator.Not.Not':
            return self._get_pyfuzzy_not()

        return None

    @classmethod
    def _from_pyfuzzy_compound(cls, pyfuzzy, system=None, systemModel=None):
        op_model = cls(operator_type='fuzzy.operator.Compound.Compound')

        # norm
        norm_model = cls.compound_norm.field.related.parent_model.from_pyfuzzy(pyfuzzy.norm)
        op_model.compound_norm = norm_model
        op_model.save()

        # inputs
        for op_input in pyfuzzy.inputs:
            op_i_model = cls.from_pyfuzzy(op_input, system=system, systemModel=systemModel)
            op_model.compound_inputs_children.add(op_i_model)

        op_model.save()
        return op_model

    @classmethod
    def _from_pyfuzzy_const(cls, pyfuzzy):
        op_model = cls(operator_type='fuzzy.operator.Const.Const', const_value=pyfuzzy.value)

        op_model.save()
        return op_model

    @classmethod
    def _from_pyfuzzy_input(cls, pyfuzzy, system=None, systemModel=None):
        op_model = cls(operator_type='fuzzy.operator.Input.Input')

        # adj
        # try:
        #     adj_model = cls.input_adjective.field.related.parent_model._get_existing_adjective_model(system, systemModel, pyfuzzy)
        # except:
        adj_model = cls.input_adjective.field.related.parent_model.from_pyfuzzy(pyfuzzy.adjective)

        op_model.input_adjective = adj_model
        op_model.save()

        return op_model

    @classmethod
    def _from_pyfuzzy_not(cls, pyfuzzy, system=None, systemModel=None):
        op_model = cls(operator_type='fuzzy.operator.Not.Not')

        # operator
        op_not_model = cls.not_input.field.related.parent_model.from_pyfuzzy(pyfuzzy.input, system=system, systemModel=systemModel)
        op_model.not_input = op_not_model
        op_model.save()

        return op_model

    @classmethod
    def from_pyfuzzy(cls, pyfuzzy, system=None, systemModel=None):
        """
        Return the model representation of an instance of the pyfuzzy attr
        """

        if pyfuzzy.__class__.__name__ == 'Compound':
            return cls._from_pyfuzzy_compound(pyfuzzy, system=system, systemModel=systemModel)

        if pyfuzzy.__class__.__name__ == 'Const':
            return cls._from_pyfuzzy_const(pyfuzzy)

        if pyfuzzy.__class__.__name__ == 'Input':
            return cls._from_pyfuzzy_input(pyfuzzy, system=system, systemModel=systemModel)

        if pyfuzzy.__class__.__name__ == 'Not':
            return cls._from_pyfuzzy_not(pyfuzzy, system=system, systemModel=systemModel)

    def __unicode__(self):
        return "%s - %s" % (self.get_operator_type_display(), self.id)
