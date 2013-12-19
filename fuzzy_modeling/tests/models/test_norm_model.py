# -*- coding: utf-8 -*-
import mock

from django.test import TestCase


from fuzzy_modeling.tests.utils import ResetMock

from fuzzy_modeling.models.norms import NormModel

from fuzzy_modeling.utils import get_class_by_python_path

from fuzzy.set.Set import Set
from fuzzy.set.Polygon import Polygon
from fuzzy.set.Triangle import Triangle
from fuzzy.set.Singleton import Singleton
from fuzzy.set.Trapez import Trapez
from fuzzy.set.Function import Function
from fuzzy.set.SFunction import SFunction
from fuzzy.set.ZFunction import ZFunction
from fuzzy.set.PiFunction import PiFunction



class NormModelTest(TestCase, ResetMock):


    # def setUp(self):
    #     pass

    def tearDown(self):

        self.reset_all_pre_mocks(NormModel)

    def _parameters_mock(self, name, value):
        """
        mock a parameter
        """
        param = mock.Mock()
        param.name = name
        param.get_value = lambda : value
        return param


    def _mock_normModel(self, norm_type, init_kwargs = {}):
        self.norm_type = norm_type
        self.norm = NormModel(norm_type=norm_type)

        self.parameters_mock = []
        for arg_name, arg_value in init_kwargs.items():

            self.parameters_mock.append(
                self._parameters_mock(name=arg_name, value=arg_value)
            )

        # mocking parameters (queryset)
        parameters_queryset = mock.Mock()
        parameters_queryset.all = lambda : self.parameters_mock
        self.set_pre_mock(NormModel,'parameters')
        NormModel.parameters = parameters_queryset


        return self.norm


    def _get_init_args_dict_for_class(self, cls):
        import inspect

        init_kwargs = {}
        inspect_init = None
        try:
            inspect_init = inspect.getargspec(cls.__init__)
        # will raise this exception when the given type don't implement a __init__ function
        # (never overrided the object.__init__)
        except TypeError:
            pass
        else:
            defaults = inspect_init.defaults if inspect_init.defaults else []
            init_kwargs = dict(zip(reversed(inspect_init.args), reversed(defaults)))

        return init_kwargs


    def _mock_norm_pyfuzzy(self, PyfuzzyNormClass, init_kwargs={}):

        # the expected pyfuzzy norm
        pyfuzzy_set_expected = PyfuzzyNormClass(**init_kwargs)

        return pyfuzzy_set_expected


    def _test_a_especific_norm_type_get_pyfuzzy(self,norm_type):
        PyfuzzyNormClass = get_class_by_python_path(norm_type)

        init_kwargs = self._get_init_args_dict_for_class(PyfuzzyNormClass)


        pyfuzzy_norm_expected = self._mock_norm_pyfuzzy(PyfuzzyNormClass, init_kwargs = init_kwargs)

        new_norm = self._mock_normModel(norm_type, init_kwargs = init_kwargs)

        new_pyfuzzy_norm = new_norm.get_pyfuzzy()


        # are from the same class
        self.assertEquals(type(pyfuzzy_norm_expected), type(new_pyfuzzy_norm))

        # assert all args
        for arg_name, arg_value in init_kwargs.items():
            pyfuzzy_expected_arg_value = getattr(pyfuzzy_norm_expected,arg_name)
            pyfuzzy_new_arg_value = getattr(new_pyfuzzy_norm,arg_name)
            self.assertEquals(pyfuzzy_expected_arg_value, pyfuzzy_new_arg_value)


    def test_norm_get_pyfuzzy_for_all_possible_norm_types(self):
        " shoud return the correct corresponding pyfuzzy object for the all norm types "

        for norm_type in NormModel.NORM_CHOICES:
            self._test_a_especific_norm_type_get_pyfuzzy(norm_type[0])

    def _test_a_especific_norm_type_from_pyfuzzy(self,norm_type):
        PyfuzzyNormClass = get_class_by_python_path(norm_type)

        init_kwargs = self._get_init_args_dict_for_class(PyfuzzyNormClass)

        pyfuzzy_norm = self._mock_norm_pyfuzzy(PyfuzzyNormClass, init_kwargs = init_kwargs)

        new_norm = NormModel.from_pyfuzzy(pyfuzzy_norm)



        pyfuzzy_norm_full_namespace = pyfuzzy_norm.__module__ + "." + pyfuzzy_norm.__class__.__name__
        # are from the same class
        self.assertEquals(pyfuzzy_norm_full_namespace, new_norm.norm_type)

        # assert all args
        for arg_name, arg_value in init_kwargs.items():
            param = new_norm.parameters.get(name=arg_name)
            self.assertEquals(arg_value, param.get_value())


    def test_norm_from_pyfuzzy_for_all_possible_norm_types(self):
        " shoud return the correct corresponding NormModel for all the Norm types as pyfuzzy object "

        for norm_type in NormModel.NORM_CHOICES:
            self._test_a_especific_norm_type_from_pyfuzzy(norm_type[0])

        # points = [(0.,0.),(30.,1.),(60.,0.)]
        # pyfuzzy_set = Polygon(points=points)


        # new_set = SetModel.from_pyfuzzy(pyfuzzy_set)

        # pyfuzzy_set_full_namespace = pyfuzzy_set.__module__ + "." + pyfuzzy_set.__class__.__name__

        # # are from the same class
        # self.assertEquals(pyfuzzy_set_full_namespace, new_set.set)

        # # have the same args
        # self.assertEquals(1,new_set.parameters.all().count())
        # points_param = new_set.parameters.all()[0]
        # self.assertEquals("points",points_param.name)
        # self.assertEquals(str(points),points_param.get_value())
