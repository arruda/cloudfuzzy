# -*- coding: utf-8 -*-

from django.test import TestCase

from fuzzy_modeling.models.systems import SystemModel

from fuzzy.System import System
# from model_mommy import mommy

class QuerySetMock(object):
    pass

class SystemModelTest(TestCase):


    def setUp(self):
        # self.aluno = mommy.make_one(Aluno)
        pass

    def _named_and_pyfuzzymixin_mock(self, name):
        """
        mock a variable or  to be an object that has attr name and a get_pyfuzzy that returns this name
        """
        var = lambda : None
        var.name = name
        var.get_pyfuzzy = lambda : name
        return var

    def _mock_system(self):
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
        inputvariablemodel_set = lambda : None
        inputvariablemodel_set.all = lambda : self.input_variable_mock
        SystemModel.inputvariablemodel_set = inputvariablemodel_set

        # mocking outputvariablemodel_set
        outputvariablemodel_set = lambda : None
        outputvariablemodel_set.all = lambda : self.output_variable_mock
        SystemModel.outputvariablemodel_set = outputvariablemodel_set


        # mocking rulemodel_set
        rulemodel_set = lambda : None
        rulemodel_set.all = lambda : self.rules_mock
        SystemModel.rulemodel_set = rulemodel_set

        return self.system


    def test_system_get_pyfuzzy(self):
        " shoud return the correct corresponding pyfuzzy object "

        new_system = self._mock_system()

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




