# -*- coding: utf-8 -*-

from django.test import TestCase


from fuzzy_modeling.tests.utils import ResetMock

from fuzzy_modeling.models.parameters import ParameterModel


class ParameterModelTest(TestCase, ResetMock):

    def test_get_value_should_return_boolean_value(self):
        " get_value should return a boolean for the boolean value type"

        parameter = ParameterModel()

        parameter.value_type = parameter.TYPE_CHOICES[0][0]

        parameter.value = "True"
        self.assertEquals(True, parameter.get_value())

        parameter.value = "False"
        self.assertEquals(False, parameter.get_value())

    def test_get_value_should_return_string_value(self):
        " get_value should return a string for the string value type"

        parameter = ParameterModel()

        parameter.value_type = parameter.TYPE_CHOICES[1][0]

        parameter.value = "Any String"
        self.assertEquals("Any String", parameter.get_value())

    def test_get_value_should_return_decimal_value(self):
        " get_value should return a float for the decimal value type"

        parameter = ParameterModel()

        parameter.value_type = parameter.TYPE_CHOICES[2][0]

        parameter.value = "10.23"
        self.assertEquals(float(10.23), parameter.get_value())

    def test_get_value_should_return_integer_value(self):
        " get_value should return a integer for the integer value type"

        parameter = ParameterModel()

        parameter.value_type = parameter.TYPE_CHOICES[3][0]

        parameter.value = "10"
        self.assertEquals(int(10), parameter.get_value())

    def test_get_type_from_python_type_for_a_boolean(self):
        " get_type_from_python_type return the correct value_type for a boolean "

        py_obj = True
        expected_value_type = 'boolean'

        value_type = ParameterModel.get_type_from_python_type(py_obj)

        self.assertEquals(expected_value_type, value_type)

    def test_get_type_from_python_type_for_a_float_or_decimal(self):
        " get_type_from_python_type return the correct value_type for a float or a decimal "
        from decimal import Decimal

        py_obj_float = float(10.23)
        py_obj_decimal = Decimal("10.23")
        expected_value_type = 'decimal'

        value_type_float = ParameterModel.get_type_from_python_type(py_obj_float)
        value_type_decimal = ParameterModel.get_type_from_python_type(py_obj_decimal)

        self.assertEquals(expected_value_type, value_type_float)
        self.assertEquals(expected_value_type, value_type_decimal)

    def test_get_type_from_python_type_for_an_integer(self):
        " get_type_from_python_type return the correct value_type for an integer "

        py_obj = int(10)
        expected_value_type = 'integer'

        value_type = ParameterModel.get_type_from_python_type(py_obj)

        self.assertEquals(expected_value_type, value_type)

    def test_get_type_from_python_type_for_a_string(self):
        " get_type_from_python_type return the correct value_type for a string "

        py_obj = " some string "
        expected_value_type = 'string'

        value_type = ParameterModel.get_type_from_python_type(py_obj)

        self.assertEquals(expected_value_type, value_type)
