# -*- coding: utf-8 -*-
import mock

from django.test import TestCase


from fuzzy_modeling.tests.utils import ResetMock

from fuzzy_modeling.models.systems import SystemModel

from fuzzy.System import System


class SystemModelTest(TestCase, ResetMock):


    def setUp(self):
        # self.aluno = mommy.make_one(Aluno)
        # from fuzzy_modeling.models.systems import SystemModel as SystemModelOriginal
        # import pdb; pdb.set_trace()
        # reset_mock(SystemModel,'inputvariablemodel_set')
        # reset_mock(SystemModel,'outputvariablemodel_set')
        # reset_mock(SystemModel,'rulemodel_set')
        pass

    def tearDown(self):
        """And kill it when done"""

        self.reset_all_pre_mocks(SystemModel)

    def _named_and_pyfuzzymixin_mock(self, name):
        """
        mock a variable or  to be an object that has attr name and a get_pyfuzzy that returns this name
        """
        var = mock.Mock()
        var.name = name
        var.get_pyfuzzy = lambda : name
        return var

    def _mock_systemModel(self):
        self.system_description = "System description"
        self.system = SystemModel(description=self.system_description)

        self.input_variable_mock = [
                self._named_and_pyfuzzymixin_mock("iv%d" % i)
                    for i in xrange(1,2)
            ]
        self.output_variable_mock = [
                self._named_and_pyfuzzymixin_mock("ov%d" % i)
                    for i in xrange(1,2)
        ]


        self.rules_mock = [
                self._named_and_pyfuzzymixin_mock("r%d" % i)
                    for i in xrange(1,2)
        ]


        # mocking inputvariablemodel_set
        # inputvariablemodel_set = lambda : None
        inputvariablemodel_set = mock.Mock()
        inputvariablemodel_set.all = lambda : self.input_variable_mock
        self.set_pre_mock(SystemModel,'inputvariablemodel_set')
        SystemModel.inputvariablemodel_set = inputvariablemodel_set


        # mocking outputvariablemodel_set
        outputvariablemodel_set = mock.Mock()
        outputvariablemodel_set.all = lambda : self.output_variable_mock
        self.set_pre_mock(SystemModel,'outputvariablemodel_set')
        SystemModel.outputvariablemodel_set = outputvariablemodel_set


        # mocking rulemodel_set
        rulemodel_set = mock.Mock()
        rulemodel_set.all = lambda : self.rules_mock
        self.set_pre_mock(SystemModel,'rulemodel_set')
        SystemModel.rulemodel_set = rulemodel_set

        return self.system


    def test_system_get_pyfuzzy(self):
        " shoud return the correct corresponding pyfuzzy object "

        new_system = self._mock_systemModel()

        new_pyfuzzy_system = new_system.get_pyfuzzy()

        # the expected pyfuzzy system
        pyfuzzy_system_expected = System(self.system_description)

        variable_dict = { var.name : var.get_pyfuzzy() for var in self.input_variable_mock + self.output_variable_mock }
        pyfuzzy_system_expected.variables = variable_dict

        rules_dict = { rule.name : rule.get_pyfuzzy() for rule in self.rules_mock }
        pyfuzzy_system_expected.rules = rules_dict

        self.assertEquals(pyfuzzy_system_expected.description, new_pyfuzzy_system.description)
        self.assertDictEqual(pyfuzzy_system_expected.variables, new_pyfuzzy_system.variables)
        self.assertDictEqual(pyfuzzy_system_expected.rules, new_pyfuzzy_system.rules)





    def test_system_from_pyfuzzy(self):
        " shoud return the correct corresponding Model for the pyfuzzy object "
        py_fuzzy_system = System("System Description")

        import math
        input_dict = {}
        input_dict["X"]       =  0.0  #: position [m]
        input_dict["dX_dT"]   =  0.0 #: velocity [m/s]
        input_dict["Phi"]     = math.radians(45.0) #: angle [rad]
        input_dict["dPhi_dT"] = math.radians(0.0) #: angle velocity [rad/s]

        i_dict1 = input_dict.copy()
        i_dict2 = input_dict.copy()

        output_dict = {
        'a' : 0.0 #: acceleration [m/s²]
        }
