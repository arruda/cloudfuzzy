# -*- coding: utf-8 -*-
import mock

from django.test import TestCase


from fuzzy_modeling.tests.utils import ResetMock

from fuzzy_modeling.models.defuzzifys import DefuzzifyModel


from fuzzy.defuzzify.COG import COG
from fuzzy.defuzzify.COGS import COGS
from fuzzy.defuzzify.Dict import Dict
from fuzzy.defuzzify.LM import LM
from fuzzy.defuzzify.MaxLeft import MaxLeft
from fuzzy.defuzzify.MaxRight import MaxRight
from fuzzy.defuzzify.RM import RM


class DefuzzifyModelTest(TestCase, ResetMock):

    # def setUp(self):
    #     pass

    def tearDown(self):

        self.reset_all_pre_mocks(DefuzzifyModel)

    def _parameters_mock(self, name, value):
        """
        mock a parameter
        """
        param = mock.Mock()
        param.name = name
        param.get_value = lambda: value
        return param

    def _norm_mock(self, value):
        """
        mock a norm
        """
        norm = mock.Mock()
        norm.get_pyfuzzy = lambda: value
        return norm

    def _mock_defuzzifyModel(self, defuzzify_type, init_kwargs={}):
        self.defuzzify_type = defuzzify_type
        self.defuzzify = DefuzzifyModel(defuzzify=defuzzify_type)

        self.parameters_mock = []
        for arg_name, arg_value in init_kwargs.items():

            self.parameters_mock.append(
                self._parameters_mock(name=arg_name, value=arg_value)
            )

        # mocking parameters (queryset)
        parameters_queryset = mock.Mock()
        parameters_queryset.all = lambda: self.parameters_mock
        self.set_pre_mock(DefuzzifyModel, 'parameters')
        DefuzzifyModel.parameters = parameters_queryset

        # # mocking inf
        # self.set_pre_mock(DefuzzifyModel,'inf')
        # DefuzzifyModel.inf = self._norm_mock("inf")

        # # mocking acc
        # self.set_pre_mock(DefuzzifyModel,'acc')
        # DefuzzifyModel.acc = self._norm_mock("acc")

        return self.defuzzify

    def test_defuzzify_get_pyfuzzy_for_cog_type(self):
        " shoud return the correct corresponding pyfuzzy object for the COG type "

        init_kwargs = {
            'failsafe': 1,
            'segment_size': 2
        }
        new_defuzzify = self._mock_defuzzifyModel(
            'fuzzy.defuzzify.COG.COG',
            init_kwargs=init_kwargs
        )

        new_pyfuzzy_defuzzify = new_defuzzify.get_pyfuzzy()

        # the expected pyfuzzy object
        pyfuzzy_defuzzify_expected = COG(**init_kwargs)

        # are from the same class
        self.assertEquals(type(pyfuzzy_defuzzify_expected), type(new_pyfuzzy_defuzzify))

        # have the same args
        self.assertEquals(pyfuzzy_defuzzify_expected.failsafe, new_pyfuzzy_defuzzify.failsafe)
        self.assertEquals(
            pyfuzzy_defuzzify_expected.segment_size,
            new_pyfuzzy_defuzzify.segment_size
        )

    def test_defuzzify_get_pyfuzzy_for_cogs_type(self):
        " shoud return the correct corresponding pyfuzzy object for the COGS type "

        init_kwargs = {
            'failsafe': 1,
        }
        new_defuzzify = self._mock_defuzzifyModel(
            'fuzzy.defuzzify.COGS.COGS',
            init_kwargs=init_kwargs
        )

        new_pyfuzzy_defuzzify = new_defuzzify.get_pyfuzzy()

        # the expected pyfuzzy object
        pyfuzzy_defuzzify_expected = COGS(**init_kwargs)

        # are from the same class
        self.assertEquals(type(pyfuzzy_defuzzify_expected), type(new_pyfuzzy_defuzzify))

        # have the same args
        self.assertEquals(pyfuzzy_defuzzify_expected.failsafe, new_pyfuzzy_defuzzify.failsafe)

    def test_defuzzify_get_pyfuzzy_for_dict_type(self):
        " shoud return the correct corresponding pyfuzzy object for the Dict type "

        new_defuzzify = self._mock_defuzzifyModel('fuzzy.defuzzify.Dict.Dict')

        new_pyfuzzy_defuzzify = new_defuzzify.get_pyfuzzy()

        # the expected pyfuzzy object
        pyfuzzy_defuzzify_expected = Dict()

        # are from the same class
        self.assertEquals(type(pyfuzzy_defuzzify_expected), type(new_pyfuzzy_defuzzify))

    def test_defuzzify_get_pyfuzzy_for_lm_type(self):
        " shoud return the correct corresponding pyfuzzy object for the LM type "

        init_kwargs = {
            'failsafe': 1,
        }

        new_defuzzify = self._mock_defuzzifyModel('fuzzy.defuzzify.LM.LM', init_kwargs=init_kwargs)

        new_pyfuzzy_defuzzify = new_defuzzify.get_pyfuzzy()

        # the expected pyfuzzy object
        pyfuzzy_defuzzify_expected = LM(**init_kwargs)

        # are from the same class
        self.assertEquals(type(pyfuzzy_defuzzify_expected), type(new_pyfuzzy_defuzzify))

        # have the same args
        self.assertEquals(pyfuzzy_defuzzify_expected.failsafe, new_pyfuzzy_defuzzify.failsafe)

    def test_defuzzify_get_pyfuzzy_for_maxleft_type(self):
        " shoud return the correct corresponding pyfuzzy object for the MaxLeft type "

        init_kwargs = {
            'failsafe': 1,
        }

        new_defuzzify = self._mock_defuzzifyModel(
            'fuzzy.defuzzify.MaxLeft.MaxLeft',
            init_kwargs=init_kwargs
        )

        new_pyfuzzy_defuzzify = new_defuzzify.get_pyfuzzy()

        # the expected pyfuzzy object
        pyfuzzy_defuzzify_expected = MaxLeft(**init_kwargs)

        # are from the same class
        self.assertEquals(type(pyfuzzy_defuzzify_expected), type(new_pyfuzzy_defuzzify))

        # have the same args
        self.assertEquals(pyfuzzy_defuzzify_expected.failsafe, new_pyfuzzy_defuzzify.failsafe)

    def test_defuzzify_get_pyfuzzy_for_maxright_type(self):
        " shoud return the correct corresponding pyfuzzy object for the MaxRight type "

        init_kwargs = {
            'failsafe': 1,
        }

        new_defuzzify = self._mock_defuzzifyModel(
            'fuzzy.defuzzify.MaxRight.MaxRight',
            init_kwargs=init_kwargs
        )

        new_pyfuzzy_defuzzify = new_defuzzify.get_pyfuzzy()

        # the expected pyfuzzy object
        pyfuzzy_defuzzify_expected = MaxRight(**init_kwargs)

        # are from the same class
        self.assertEquals(type(pyfuzzy_defuzzify_expected), type(new_pyfuzzy_defuzzify))

        # have the same args
        self.assertEquals(pyfuzzy_defuzzify_expected.failsafe, new_pyfuzzy_defuzzify.failsafe)

    def test_defuzzify_get_pyfuzzy_for_rm_type(self):
        " shoud return the correct corresponding pyfuzzy object for the RM type "

        init_kwargs = {
            'failsafe': 1,
        }

        new_defuzzify = self._mock_defuzzifyModel('fuzzy.defuzzify.RM.RM', init_kwargs=init_kwargs)

        new_pyfuzzy_defuzzify = new_defuzzify.get_pyfuzzy()

        # the expected pyfuzzy object
        pyfuzzy_defuzzify_expected = RM(**init_kwargs)

        # are from the same class
        self.assertEquals(type(pyfuzzy_defuzzify_expected), type(new_pyfuzzy_defuzzify))

        # have the same args
        self.assertEquals(pyfuzzy_defuzzify_expected.failsafe, new_pyfuzzy_defuzzify.failsafe)

    def test_defuzzify_from_pyfuzzy_for_cog_type(self):
        " shoud return the correct corresponding DefuzzifyModel for the COG pyfuzzy object "

        init_kwargs = {
            'failsafe': 1,
            'segment_size': 2
        }

        pyfuzzy_defuzzify = COG(**init_kwargs)

        new_defuzzify = DefuzzifyModel.from_pyfuzzy(pyfuzzy_defuzzify)

        pyfuzzy_defuzzify_full_namespace = pyfuzzy_defuzzify.__module__
        pyfuzzy_defuzzify_full_namespace += "." + pyfuzzy_defuzzify.__class__.__name__

        # are from the same class
        self.assertEquals(pyfuzzy_defuzzify_full_namespace, new_defuzzify.defuzzify)

        # have the same args
        self.assertEquals(2, new_defuzzify.parameters.all().count())
        failsafe_param = new_defuzzify.parameters.get(name='failsafe')
        self.assertEquals(1.0, failsafe_param.get_value())
        segment_size_param = new_defuzzify.parameters.get(name='segment_size')
        self.assertEquals(2.0, segment_size_param.get_value())

    def test_defuzzify_from_pyfuzzy_for_cogs_type(self):
        " shoud return the correct corresponding DefuzzifyModel for the COGS pyfuzzy object "

        init_kwargs = {
            'failsafe': 1,
        }

        pyfuzzy_defuzzify = COGS(**init_kwargs)

        new_defuzzify = DefuzzifyModel.from_pyfuzzy(pyfuzzy_defuzzify)

        pyfuzzy_defuzzify_full_namespace = pyfuzzy_defuzzify.__module__
        pyfuzzy_defuzzify_full_namespace += "." + pyfuzzy_defuzzify.__class__.__name__

        # are from the same class
        self.assertEquals(pyfuzzy_defuzzify_full_namespace, new_defuzzify.defuzzify)

        # have the same args
        self.assertEquals(1, new_defuzzify.parameters.all().count())
        failsafe_param = new_defuzzify.parameters.get(name='failsafe')
        self.assertEquals(1.0, failsafe_param.get_value())

    def test_defuzzify_from_pyfuzzy_for_dict_type(self):
        " shoud return the correct corresponding DefuzzifyModel for the Dict pyfuzzy object "

        pyfuzzy_defuzzify = Dict()

        new_defuzzify = DefuzzifyModel.from_pyfuzzy(pyfuzzy_defuzzify)

        pyfuzzy_defuzzify_full_namespace = pyfuzzy_defuzzify.__module__
        pyfuzzy_defuzzify_full_namespace += "." + pyfuzzy_defuzzify.__class__.__name__

        # are from the same class
        self.assertEquals(pyfuzzy_defuzzify_full_namespace, new_defuzzify.defuzzify)

    def test_defuzzify_from_pyfuzzy_for_lm_type(self):
        " shoud return the correct corresponding DefuzzifyModel for the LM pyfuzzy object "

        init_kwargs = {
            'failsafe': 1,
        }

        pyfuzzy_defuzzify = LM(**init_kwargs)

        new_defuzzify = DefuzzifyModel.from_pyfuzzy(pyfuzzy_defuzzify)

        pyfuzzy_defuzzify_full_namespace = pyfuzzy_defuzzify.__module__
        pyfuzzy_defuzzify_full_namespace += "." + pyfuzzy_defuzzify.__class__.__name__

        # are from the same class
        self.assertEquals(pyfuzzy_defuzzify_full_namespace, new_defuzzify.defuzzify)

        # have the same args
        self.assertEquals(1, new_defuzzify.parameters.all().count())
        failsafe_param = new_defuzzify.parameters.get(name='failsafe')
        self.assertEquals(1.0, failsafe_param.get_value())

    def test_defuzzify_from_pyfuzzy_for_maxleft_type(self):
        " shoud return the correct corresponding DefuzzifyModel for the MaxLeft pyfuzzy object "

        init_kwargs = {
            'failsafe': 1,
        }

        pyfuzzy_defuzzify = MaxLeft(**init_kwargs)

        new_defuzzify = DefuzzifyModel.from_pyfuzzy(pyfuzzy_defuzzify)

        pyfuzzy_defuzzify_full_namespace = pyfuzzy_defuzzify.__module__
        pyfuzzy_defuzzify_full_namespace += "." + pyfuzzy_defuzzify.__class__.__name__

        # are from the same class
        self.assertEquals(pyfuzzy_defuzzify_full_namespace, new_defuzzify.defuzzify)

        # have the same args
        self.assertEquals(1, new_defuzzify.parameters.all().count())
        failsafe_param = new_defuzzify.parameters.get(name='failsafe')
        self.assertEquals(1.0, failsafe_param.get_value())

    def test_defuzzify_from_pyfuzzy_for_maxright_type(self):
        " shoud return the correct corresponding DefuzzifyModel for the MaxRight pyfuzzy object "

        init_kwargs = {
            'failsafe': 1,
        }

        pyfuzzy_defuzzify = MaxRight(**init_kwargs)

        new_defuzzify = DefuzzifyModel.from_pyfuzzy(pyfuzzy_defuzzify)

        pyfuzzy_defuzzify_full_namespace = pyfuzzy_defuzzify.__module__
        pyfuzzy_defuzzify_full_namespace += "." + pyfuzzy_defuzzify.__class__.__name__

        # are from the same class
        self.assertEquals(pyfuzzy_defuzzify_full_namespace, new_defuzzify.defuzzify)

        # have the same args
        self.assertEquals(1, new_defuzzify.parameters.all().count())
        failsafe_param = new_defuzzify.parameters.get(name='failsafe')
        self.assertEquals(1.0, failsafe_param.get_value())

    def test_defuzzify_from_pyfuzzy_for_rm_type(self):
        " shoud return the correct corresponding DefuzzifyModel for the RM pyfuzzy object "

        init_kwargs = {
            'failsafe': 1,
        }

        pyfuzzy_defuzzify = RM(**init_kwargs)

        new_defuzzify = DefuzzifyModel.from_pyfuzzy(pyfuzzy_defuzzify)

        pyfuzzy_defuzzify_full_namespace = pyfuzzy_defuzzify.__module__
        pyfuzzy_defuzzify_full_namespace += "." + pyfuzzy_defuzzify.__class__.__name__

        # are from the same class
        self.assertEquals(pyfuzzy_defuzzify_full_namespace, new_defuzzify.defuzzify)

        # have the same args
        self.assertEquals(1, new_defuzzify.parameters.all().count())
        failsafe_param = new_defuzzify.parameters.get(name='failsafe')
        self.assertEquals(1.0, failsafe_param.get_value())
