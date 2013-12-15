# -*- coding: utf-8 -*-
import mock

from django.test import TestCase


from fuzzy_modeling.tests.utils import ResetMock

from fuzzy_modeling.models.sets import SetModel

from fuzzy.set.Set import Set
from fuzzy.set.Polygon import Polygon
from fuzzy.set.Triangle import Triangle


class SetModelTest(TestCase, ResetMock):


    # def setUp(self):
    #     pass

    def tearDown(self):

        self.reset_all_pre_mocks(SetModel)

    def _parameters_mock(self, name, value):
        """
        mock a parameter
        """
        param = mock.Mock()
        param.name = name
        param.get_value = lambda : value
        return param


    def _mock_setModel(self, set_choice):
        self.set_choice = set_choice
        self.set = SetModel(set=set_choice)


        return self.set


    def test_set_get_pyfuzzy_for_set_type(self):
        " shoud return the correct corresponding pyfuzzy object for the Set type "

        new_set = self._mock_setModel('fuzzy.set.Set.Set')

        new_pyfuzzy_set = new_set.get_pyfuzzy()

        # the expected pyfuzzy system
        pyfuzzy_set_expected = Set()

        # are from the same class
        self.assertEquals(type(pyfuzzy_set_expected), type(new_pyfuzzy_set))

    def test_set_get_pyfuzzy_for_polygon_type(self):
        " shoud return the correct corresponding pyfuzzy object for the Polygon type "

        new_set = self._mock_setModel('fuzzy.set.Polygon.Polygon')

        points = [(0.,0.),(30.,1.),(60.,0.)]
        points_value = str(points)

        self.parameters_mock = [
                self._parameters_mock(name="points", value=points_value)
            ]

        # mocking parameters (queryset)
        parameters_queryset = mock.Mock()
        parameters_queryset.all = lambda : self.parameters_mock
        self.set_pre_mock(SetModel,'parameters')
        SetModel.parameters = parameters_queryset


        new_pyfuzzy_set = new_set.get_pyfuzzy()

        # the expected pyfuzzy system
        pyfuzzy_set_expected = Polygon(points=points)

        # are from the same class
        self.assertEquals(type(pyfuzzy_set_expected), type(new_pyfuzzy_set))

        # have the same points
        self.assertEquals(pyfuzzy_set_expected.points, new_pyfuzzy_set.points)

    def test_set_get_pyfuzzy_for_triangle_type(self):
        " shoud return the correct corresponding pyfuzzy object for the Triangle type "

        new_set = self._mock_setModel('fuzzy.set.Triangle.Triangle')

        m = 1.2
        alpha = 2.3
        beta = 3.4
        y_max = 4.5
        y_min = 5.4


        self.parameters_mock = [
                self._parameters_mock(name="m", value=m),
                self._parameters_mock(name="alpha", value=alpha),
                self._parameters_mock(name="beta", value=beta),
                self._parameters_mock(name="y_max", value=y_max),
                self._parameters_mock(name="y_min", value=y_min)
            ]

        # mocking parameters (queryset)
        parameters_queryset = mock.Mock()
        parameters_queryset.all = lambda : self.parameters_mock
        self.set_pre_mock(SetModel,'parameters')
        SetModel.parameters = parameters_queryset


        new_pyfuzzy_set = new_set.get_pyfuzzy()

        # the expected pyfuzzy system
        pyfuzzy_set_expected = Triangle(m = m, alpha = alpha, beta = beta, y_max = y_max, y_min = y_min)

        # are from the same class
        self.assertEquals(type(pyfuzzy_set_expected), type(new_pyfuzzy_set))

        # have the same args
        self.assertEquals(pyfuzzy_set_expected.m, new_pyfuzzy_set.m)
        self.assertEquals(pyfuzzy_set_expected.alpha, new_pyfuzzy_set.alpha)
        self.assertEquals(pyfuzzy_set_expected.beta, new_pyfuzzy_set.beta)
        self.assertEquals(pyfuzzy_set_expected.y_max, new_pyfuzzy_set.y_max)
        self.assertEquals(pyfuzzy_set_expected.y_min, new_pyfuzzy_set.y_min)
