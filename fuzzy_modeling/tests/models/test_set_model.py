# -*- coding: utf-8 -*-
import mock

from django.test import TestCase


from fuzzy_modeling.tests.utils import ResetMock

from fuzzy_modeling.models.sets import SetModel

from fuzzy.set.Set import Set
from fuzzy.set.Polygon import Polygon
from fuzzy.set.Triangle import Triangle
from fuzzy.set.Singleton import Singleton
from fuzzy.set.Trapez import Trapez
from fuzzy.set.Function import Function
from fuzzy.set.SFunction import SFunction
from fuzzy.set.ZFunction import ZFunction
from fuzzy.set.PiFunction import PiFunction


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

    def test_set_get_pyfuzzy_for_singleton_type(self):
        " shoud return the correct corresponding pyfuzzy object for the Singleton type "

        new_set = self._mock_setModel('fuzzy.set.Singleton.Singleton')

        x = 1.2


        self.parameters_mock = [
                self._parameters_mock(name="x", value=x),
            ]

        # mocking parameters (queryset)
        parameters_queryset = mock.Mock()
        parameters_queryset.all = lambda : self.parameters_mock
        self.set_pre_mock(SetModel,'parameters')
        SetModel.parameters = parameters_queryset


        new_pyfuzzy_set = new_set.get_pyfuzzy()

        # the expected pyfuzzy system
        pyfuzzy_set_expected = Singleton(x=x)

        # are from the same class
        self.assertEquals(type(pyfuzzy_set_expected), type(new_pyfuzzy_set))

        # have the same args
        self.assertEquals(pyfuzzy_set_expected.x, new_pyfuzzy_set.x)

    def test_set_get_pyfuzzy_for_trapez_type(self):
        " shoud return the correct corresponding pyfuzzy object for the Trapez type "

        new_set = self._mock_setModel('fuzzy.set.Trapez.Trapez')

        m1 = 1.2
        m2 = 1.3
        alpha = 2.3
        beta = 3.4
        y_max = 4.5
        y_min = 5.4


        self.parameters_mock = [
                self._parameters_mock(name="m1", value=m1),
                self._parameters_mock(name="m2", value=m2),
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
        pyfuzzy_set_expected = Trapez(m1 = m1, m2 = m2, alpha = alpha, beta = beta, y_max = y_max, y_min = y_min)

        # are from the same class
        self.assertEquals(type(pyfuzzy_set_expected), type(new_pyfuzzy_set))

        # have the same args
        self.assertEquals(pyfuzzy_set_expected.m1, new_pyfuzzy_set.m1)
        self.assertEquals(pyfuzzy_set_expected.m2, new_pyfuzzy_set.m2)
        self.assertEquals(pyfuzzy_set_expected.alpha, new_pyfuzzy_set.alpha)
        self.assertEquals(pyfuzzy_set_expected.beta, new_pyfuzzy_set.beta)
        self.assertEquals(pyfuzzy_set_expected.y_max, new_pyfuzzy_set.y_max)
        self.assertEquals(pyfuzzy_set_expected.y_min, new_pyfuzzy_set.y_min)

    def test_set_get_pyfuzzy_for_function_type(self):
        " shoud return the correct corresponding pyfuzzy object for the Function type "

        new_set = self._mock_setModel('fuzzy.set.Function.Function')


        new_pyfuzzy_set = new_set.get_pyfuzzy()

        # the expected pyfuzzy system
        pyfuzzy_set_expected = Function()

        # are from the same class
        self.assertEquals(type(pyfuzzy_set_expected), type(new_pyfuzzy_set))

    def test_set_get_pyfuzzy_for_sfunction_type(self):
        " shoud return the correct corresponding pyfuzzy object for the SFunction type "

        new_set = self._mock_setModel('fuzzy.set.SFunction.SFunction')

        a = 1.2
        delta = 2.3


        self.parameters_mock = [
                self._parameters_mock(name="a", value=a),
                self._parameters_mock(name="delta", value=delta),
            ]

        # mocking parameters (queryset)
        parameters_queryset = mock.Mock()
        parameters_queryset.all = lambda : self.parameters_mock
        self.set_pre_mock(SetModel,'parameters')
        SetModel.parameters = parameters_queryset


        new_pyfuzzy_set = new_set.get_pyfuzzy()

        # the expected pyfuzzy system
        pyfuzzy_set_expected = SFunction(a = a, delta = delta)

        # are from the same class
        self.assertEquals(type(pyfuzzy_set_expected), type(new_pyfuzzy_set))

        # have the same args
        self.assertEquals(pyfuzzy_set_expected.a, new_pyfuzzy_set.a)
        self.assertEquals(pyfuzzy_set_expected.delta, new_pyfuzzy_set.delta)

    def test_set_get_pyfuzzy_for_zfunction_type(self):
        " shoud return the correct corresponding pyfuzzy object for the ZFunction type "

        new_set = self._mock_setModel('fuzzy.set.ZFunction.ZFunction')

        a = 1.2
        delta = 2.3


        self.parameters_mock = [
                self._parameters_mock(name="a", value=a),
                self._parameters_mock(name="delta", value=delta),
            ]

        # mocking parameters (queryset)
        parameters_queryset = mock.Mock()
        parameters_queryset.all = lambda : self.parameters_mock
        self.set_pre_mock(SetModel,'parameters')
        SetModel.parameters = parameters_queryset


        new_pyfuzzy_set = new_set.get_pyfuzzy()

        # the expected pyfuzzy system
        pyfuzzy_set_expected = ZFunction(a = a, delta = delta)

        # are from the same class
        self.assertEquals(type(pyfuzzy_set_expected), type(new_pyfuzzy_set))

        # have the same args
        self.assertEquals(pyfuzzy_set_expected.a, new_pyfuzzy_set.a)
        self.assertEquals(pyfuzzy_set_expected.delta, new_pyfuzzy_set.delta)

    def test_set_get_pyfuzzy_for_pifunction_type(self):
        " shoud return the correct corresponding pyfuzzy object for the PiFunction type "

        new_set = self._mock_setModel('fuzzy.set.PiFunction.PiFunction')

        a = 1.2
        delta = 2.3


        self.parameters_mock = [
                self._parameters_mock(name="a", value=a),
                self._parameters_mock(name="delta", value=delta),
            ]

        # mocking parameters (queryset)
        parameters_queryset = mock.Mock()
        parameters_queryset.all = lambda : self.parameters_mock
        self.set_pre_mock(SetModel,'parameters')
        SetModel.parameters = parameters_queryset


        new_pyfuzzy_set = new_set.get_pyfuzzy()

        # the expected pyfuzzy system
        pyfuzzy_set_expected = PiFunction(a = a, delta = delta)

        # are from the same class
        self.assertEquals(type(pyfuzzy_set_expected), type(new_pyfuzzy_set))

        # have the same args
        self.assertEquals(pyfuzzy_set_expected.a, new_pyfuzzy_set.a)
        self.assertEquals(pyfuzzy_set_expected.delta, new_pyfuzzy_set.delta)

    def test_set_from_pyfuzzy_for_set_type(self):
        " shoud return the correct corresponding SetModel for the Set pyfuzzy object "

        pyfuzzy_set = Set()


        new_set = SetModel.from_pyfuzzy(pyfuzzy_set)

        pyfuzzy_set_full_namespace = pyfuzzy_set.__module__ + "." + pyfuzzy_set.__class__.__name__

        # are from the same class
        self.assertEquals(pyfuzzy_set_full_namespace, new_set.set)

    def test_set_from_pyfuzzy_for_polygon_type(self):
        " shoud return the correct corresponding SetModel for the Polygon pyfuzzy object "

        points = [(0.,0.),(30.,1.),(60.,0.)]
        pyfuzzy_set = Polygon(points=points)


        new_set = SetModel.from_pyfuzzy(pyfuzzy_set)

        pyfuzzy_set_full_namespace = pyfuzzy_set.__module__ + "." + pyfuzzy_set.__class__.__name__

        # are from the same class
        self.assertEquals(pyfuzzy_set_full_namespace, new_set.set)

        # have the same args
        self.assertEquals(1,new_set.parameters.all().count())
        points_param = new_set.parameters.all()[0]
        self.assertEquals("points",points_param.name)
        self.assertEquals(str(points),points_param.get_value())

    def test_set_from_pyfuzzy_for_triangle_type(self):
        " shoud return the correct corresponding SetModel for the Triangle pyfuzzy object "

        m = 1.2
        alpha = 2.3
        beta = 3.4
        y_max = 4.5
        y_min = 5.4
        pyfuzzy_set = Triangle(m = m, alpha = alpha, beta = beta, y_max = y_max, y_min = y_min)


        new_set = SetModel.from_pyfuzzy(pyfuzzy_set)

        pyfuzzy_set_full_namespace = pyfuzzy_set.__module__ + "." + pyfuzzy_set.__class__.__name__

        # are from the same class
        self.assertEquals(pyfuzzy_set_full_namespace, new_set.set)

        # have the same args
        self.assertEquals(5,new_set.parameters.all().count())

        m_param = new_set.parameters.get(name="m")
        alpha_param = new_set.parameters.get(name="alpha")
        beta_param = new_set.parameters.get(name="beta")
        y_max_param = new_set.parameters.get(name="y_max")
        y_min_param = new_set.parameters.get(name="y_min")

        self.assertEquals(pyfuzzy_set.m, m_param.get_value())
        self.assertEquals(pyfuzzy_set.alpha, alpha_param.get_value())
        self.assertEquals(pyfuzzy_set.beta, beta_param.get_value())
        self.assertEquals(pyfuzzy_set.y_max, y_max_param.get_value())
        self.assertEquals(pyfuzzy_set.y_min, y_min_param.get_value())

    def test_set_from_pyfuzzy_for_trapez_type(self):
        " shoud return the correct corresponding SetModel for the Trapez pyfuzzy object "

        m1= 1.2
        m2= 1.3
        alpha = 2.3
        beta = 3.4
        y_max = 4.5
        y_min = 5.4
        pyfuzzy_set = Trapez(m1 = m1, m2 = m2, alpha = alpha, beta = beta, y_max = y_max, y_min = y_min)


        new_set = SetModel.from_pyfuzzy(pyfuzzy_set)

        pyfuzzy_set_full_namespace = pyfuzzy_set.__module__ + "." + pyfuzzy_set.__class__.__name__

        # are from the same class
        self.assertEquals(pyfuzzy_set_full_namespace, new_set.set)

        # have the same args
        self.assertEquals(6,new_set.parameters.all().count())

        m1_param = new_set.parameters.get(name="m1")
        m2_param = new_set.parameters.get(name="m2")
        alpha_param = new_set.parameters.get(name="alpha")
        beta_param = new_set.parameters.get(name="beta")
        y_max_param = new_set.parameters.get(name="y_max")
        y_min_param = new_set.parameters.get(name="y_min")

        self.assertEquals(pyfuzzy_set.m1, m1_param.get_value())
        self.assertEquals(pyfuzzy_set.m2, m2_param.get_value())
        self.assertEquals(pyfuzzy_set.alpha, alpha_param.get_value())
        self.assertEquals(pyfuzzy_set.beta, beta_param.get_value())
        self.assertEquals(pyfuzzy_set.y_max, y_max_param.get_value())
        self.assertEquals(pyfuzzy_set.y_min, y_min_param.get_value())

    def test_set_from_pyfuzzy_for_function_type(self):
        " shoud return the correct corresponding SetModel for the Function pyfuzzy object "

        pyfuzzy_set = Function()


        new_set = SetModel.from_pyfuzzy(pyfuzzy_set)

        pyfuzzy_set_full_namespace = pyfuzzy_set.__module__ + "." + pyfuzzy_set.__class__.__name__

        # are from the same class
        self.assertEquals(pyfuzzy_set_full_namespace, new_set.set)


    def test_set_from_pyfuzzy_for_sfunction_type(self):
        " shoud return the correct corresponding SetModel for the SFunction pyfuzzy object "

        a = 1.2
        delta = 2.3
        pyfuzzy_set = SFunction(a = a, delta = delta)

        new_set = SetModel.from_pyfuzzy(pyfuzzy_set)

        pyfuzzy_set_full_namespace = pyfuzzy_set.__module__ + "." + pyfuzzy_set.__class__.__name__

        # are from the same class
        self.assertEquals(pyfuzzy_set_full_namespace, new_set.set)

        # have the same args
        self.assertEquals(2,new_set.parameters.all().count())

        a_param = new_set.parameters.get(name="a")
        delta_param = new_set.parameters.get(name="delta")

        self.assertEquals(pyfuzzy_set.a, a_param.get_value())
        self.assertEquals(pyfuzzy_set.delta, delta_param.get_value())

    def test_set_from_pyfuzzy_for_zfunction_type(self):
        " shoud return the correct corresponding SetModel for the ZFunction pyfuzzy object "

        a = 1.2
        delta = 2.3
        pyfuzzy_set = ZFunction(a = a, delta = delta)

        new_set = SetModel.from_pyfuzzy(pyfuzzy_set)

        pyfuzzy_set_full_namespace = pyfuzzy_set.__module__ + "." + pyfuzzy_set.__class__.__name__

        # are from the same class
        self.assertEquals(pyfuzzy_set_full_namespace, new_set.set)

        # have the same args
        self.assertEquals(2,new_set.parameters.all().count())

        a_param = new_set.parameters.get(name="a")
        delta_param = new_set.parameters.get(name="delta")

        self.assertEquals(pyfuzzy_set.a, a_param.get_value())
        self.assertEquals(pyfuzzy_set.delta, delta_param.get_value())

    def test_set_from_pyfuzzy_for_pifunction_type(self):
        " shoud return the correct corresponding SetModel for the PiFunction pyfuzzy object "

        a = 1.2
        delta = 2.3
        pyfuzzy_set = PiFunction(a = a, delta = delta)

        new_set = SetModel.from_pyfuzzy(pyfuzzy_set)

        pyfuzzy_set_full_namespace = pyfuzzy_set.__module__ + "." + pyfuzzy_set.__class__.__name__

        # are from the same class
        self.assertEquals(pyfuzzy_set_full_namespace, new_set.set)

        # have the same args
        self.assertEquals(2,new_set.parameters.all().count())

        a_param = new_set.parameters.get(name="a")
        delta_param = new_set.parameters.get(name="delta")

        self.assertEquals(pyfuzzy_set.a, a_param.get_value())
        self.assertEquals(pyfuzzy_set.delta, delta_param.get_value())
