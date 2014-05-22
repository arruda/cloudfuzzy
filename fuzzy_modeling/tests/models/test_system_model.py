# -*- coding: utf-8 -*-
import inspect

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
        mock a variable or to be an object that has attr name and a get_pyfuzzy that returns this name
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



    @classmethod
    def _createSystem(cls):
        import fuzzy.System
        system = fuzzy.System.System(description=
        """This fuzzy system is to control the inverted pendulum into an upright position as well as
        at the position X=0.

        It also is used to demonstrate some features of pyfuzzy.
        This is the reason, it uses different fuzzy norm in normally
        symmetrical rules.""")

        from fuzzy.norm.AlgebraicProduct import AlgebraicProduct
        from fuzzy.norm.AlgebraicSum import AlgebraicSum
        from fuzzy.fuzzify.Plain import Plain
        from fuzzy.defuzzify.COG import COG
        # set defuzzification method and default norms
        INF = AlgebraicProduct()
        ACC = AlgebraicSum()
        COM = AlgebraicSum()
        CER = AlgebraicProduct()
        COG = COG(INF=INF,ACC=ACC,failsafe = 0., segment_size=0.5)

        from fuzzy.InputVariable import InputVariable
        from fuzzy.OutputVariable import OutputVariable
        from fuzzy.Adjective import Adjective
        from fuzzy.set.Polygon import Polygon

        angle = InputVariable(fuzzify=Plain(),description='angle',min=0.,max=360.,unit='degrees')
        system.variables['Phi'] = angle
        angle.adjectives['up_more_right'] = Adjective(Polygon([(0.,0.),(30.,1.),(60.,0.)]))
        angle.adjectives['up_right'] = Adjective(Polygon([(30.,0.),(60.,1.),(90.,0.)]))
        angle.adjectives['up'] = Adjective(Polygon([(60.,0.),(90.,1.),(120.,0.)]))
        angle.adjectives['up_left'] = Adjective(Polygon([(90.,0.),(120.,1.),(150.,0.)]))
        angle.adjectives['up_more_left'] = Adjective(Polygon([(120.,0.),(150.,1.),(180.,0.)]))
        angle.adjectives['down_more_left'] = Adjective(Polygon([(180.,0.),(210.,1.),(240.,0.)]))
        angle.adjectives['down_left'] = Adjective(Polygon([(210.,0.),(240.,1.),(270.,0.)]))
        angle.adjectives['down'] = Adjective(Polygon([(240.,0.),(270.,1.),(300.,0.)]))
        angle.adjectives['down_right'] = Adjective(Polygon([(270.,0.),(300.,1.),(330.,0.)]))
        angle.adjectives['down_more_right'] = Adjective(Polygon([(300.,0.),(330.,1.),(360.,0.)]))

        angle_velocity = InputVariable(fuzzify=Plain(),description='angle velocity',min=-600.,max=600.,unit='degrees per second')
        system.variables['dPhi_dT'] = angle_velocity
        angle_velocity.adjectives['cw_fast'] = Adjective(Polygon([(-600.,1.),(-300.,0.)]))
        angle_velocity.adjectives['cw_slow'] = Adjective(Polygon([(-600.,0.),(-300.,1.),(0.,0.)]))
        angle_velocity.adjectives['stop'] = Adjective(Polygon([(-300.,0.),(0.,1.),(300.,0.)]))
        angle_velocity.adjectives['ccw_slow'] = Adjective(Polygon([(0.,0.),(300.,1.),(600.,0.)]))
        angle_velocity.adjectives['ccw_fast'] = Adjective(Polygon([(300.,0.),(600.,1.)]))

        position = InputVariable(fuzzify=Plain(),description='position',min=-20.,max=20.,unit='meter')
        system.variables['X'] = position
        position.adjectives['left_far'] = Adjective(Polygon([(-20.,1.),(-10.,0.)]))
        position.adjectives['left_near'] = Adjective(Polygon([(-20.,0.),(-5.,1.),(0.,0.)]))
        position.adjectives['stop'] = Adjective(Polygon([(-5.,0.),(0.,1.),(5.,0.)]))
        position.adjectives['right_near'] = Adjective(Polygon([(0.,0.),(5.,1.),(20.,0.)]))
        position.adjectives['right_far'] = Adjective(Polygon([(10.,0.),(20.,1.)]))

        velocity = InputVariable(fuzzify=Plain(),description='velocity',min=-10.,max=10.,unit='meter per second')
        system.variables['dX_dT'] = velocity
        velocity.adjectives['left_fast'] = Adjective(Polygon([(-10.,1.),(-5.,0.)]))
        velocity.adjectives['left_slow'] = Adjective(Polygon([(-10.,0.),(-2.,1.),(0.,0.)]))
        velocity.adjectives['stop'] = Adjective(Polygon([(-2.,0.),(0.,1.),(2.,0.)]))
        velocity.adjectives['right_slow'] = Adjective(Polygon([(0.,0.),(2.,1.),(10.,0.)]))
        velocity.adjectives['right_fast'] = Adjective(Polygon([(5.,0.),(10.,1.)]))

        acceleration = OutputVariable(defuzzify=COG,description='acceleration',min=-50.,max=50.,unit='meter per second^2')
        system.variables['a'] = acceleration
        acceleration.adjectives['left_fast']  = a_left_fast  = Adjective(Polygon([(-50.,0.),(-20.,1.),(-10.,0.)]),COM=COM)
        acceleration.adjectives['left_slow']  = a_left_slow  = Adjective(Polygon([(-20.,0.),(-10.,1.),(0.,0.)]),COM=COM)
        acceleration.adjectives['stop']       = a_stop       = Adjective(Polygon([(-10.,0.),(0.,1.),(10.,0.)]),COM=COM)
        acceleration.adjectives['right_slow'] = a_right_slow = Adjective(Polygon([(0.,0.),(10.,1.),(20.,0.)]),COM=COM)
        acceleration.adjectives['right_fast'] = a_right_fast = Adjective(Polygon([(10.,0.),(20.,1.),(50.,0.)]),COM=COM)

        from fuzzy.Rule import Rule
        from fuzzy.norm.Max import Max
        #from fuzzy.norm.Min import Min
        #from fuzzy.norm.BoundedDifference import BoundedDifference
        #from fuzzy.norm.DrasticSum import DrasticSum
        from fuzzy.norm.EinsteinSum import EinsteinSum
        from fuzzy.norm.DombiUnion import DombiUnion
        from fuzzy.operator.Compound import Compound
        from fuzzy.operator.Input import Input
        from fuzzy.operator.Not import Not

        system.rules['stop'] = Rule(
            adjective=a_stop,
            # it gets its value from here
            operator=Compound(
                Max(),
                Compound(
                    AlgebraicProduct(),
                    Input(system.variables["Phi"].adjectives["up"]),
                    Input(system.variables["dPhi_dT"].adjectives["stop"])
                ),
                Compound(
                    AlgebraicProduct(),
                    Input(system.variables["Phi"].adjectives["up_right"]),
                    Input(system.variables["dPhi_dT"].adjectives["ccw_slow"])
                ),
                Compound(
                    AlgebraicProduct(),
                    Input(system.variables["Phi"].adjectives["up_left"]),
                    Input(system.variables["dPhi_dT"].adjectives["cw_slow"])
                )
            ),
            CER=CER
        )

        system.rules['tilts right'] = Rule(
            adjective=a_right_slow,
            # it gets its value from here
            operator=Compound(
                AlgebraicProduct(),
                Not(
                    Compound(
                        AlgebraicProduct(),
                        Compound(
                            AlgebraicSum(),
                            Input(system.variables["X"].adjectives["left_near"]),
                            Input(system.variables["X"].adjectives["left_far"])
                        ),
                        Compound(
                            EinsteinSum(),
                            Input(system.variables["dX_dT"].adjectives["left_slow"]),
                            Input(system.variables["dX_dT"].adjectives["left_fast"])
                        )
                    ),
                ),
                Input(system.variables["Phi"].adjectives["up_right"])
            ),
            CER=CER
        )

        system.rules['tilts left'] = Rule(
            adjective=a_left_slow,
            # it gets its value from here
            operator=Compound(
                AlgebraicProduct(),
                Not(
                    Compound(
                        AlgebraicProduct(),
                        Compound(
                            AlgebraicSum(),
                            Input(system.variables["X"].adjectives["right_near"]),
                            Input(system.variables["X"].adjectives["right_far"])
                        ),
                        Compound(
                            DombiUnion(0.25),
                            Input(system.variables["dX_dT"].adjectives["right_slow"]),
                            Input(system.variables["dX_dT"].adjectives["right_fast"])
                        )
                    ),
                ),
                Input(system.variables["Phi"].adjectives["up_left"])
            ),
            CER=CER
        )

        system.rules['far right'] = Rule(
            adjective=a_right_fast,
            # it gets its value from here
            operator=Input(system.variables["Phi"].adjectives["up_more_right"]),
            CER=CER
        )

        system.rules['far left'] = Rule(
            adjective=a_left_fast,
            # it gets its value from here
            operator=Input(system.variables["Phi"].adjectives["up_more_left"]),
            CER=CER
        )

        system.rules['accelerate cw if down'] = Rule(
            adjective=a_right_slow,
            # it gets its value from here
            operator=Compound(
                AlgebraicProduct(),
                Input(system.variables["Phi"].adjectives["down"]),
                Compound(
                    AlgebraicProduct(),
                    Input(system.variables["dPhi_dT"].adjectives["cw_slow"]),
                    Input(system.variables["dPhi_dT"].adjectives["cw_slow"]),
                )
            ),
            CER=CER
        )

        system.rules['accelerate ccw if down'] = Rule(
            adjective=a_left_slow,
            # it gets its value from here
            operator=Compound(
                AlgebraicProduct(),
                Input(system.variables["Phi"].adjectives["down"]),
                Compound(
                    AlgebraicProduct(),
                    Input(system.variables["dPhi_dT"].adjectives["ccw_slow"]),
                    Input(system.variables["dPhi_dT"].adjectives["ccw_slow"]),
                )
            ),
            CER=CER
        )

        return system



    def test_system_from_pyfuzzy(self):
        " shoud return the correct corresponding Model for the pyfuzzy object "

        pyfuzzy_system_expected = self._createSystem()
        new_pyfuzzy_system = SystemModel.from_pyfuzzy(pyfuzzy_system_expected).get_pyfuzzy()


        import math
        input_dict = {}
        input_dict["X"]       =  0.0  #: position [m]
        input_dict["dX_dT"]   =  0.0 #: velocity [m/s]
        input_dict["Phi"]     = math.radians(45.0) #: angle [rad]
        input_dict["dPhi_dT"] = math.radians(0.0) #: angle velocity [rad/s]

        i_dict1 = input_dict.copy()
        i_dict2 = input_dict.copy()

        output_dict1 = {
        'a' : 0.0 #: acceleration [m/s²]
        }
        output_dict2 = {
        'a' : 0.0 #: acceleration [m/s²]
        }


        pyfuzzy_system_expected.fuzzify(i_dict1)
        pyfuzzy_system_expected.inference()
        # pyfuzzy_system_expected.defuzzify(output_dict1)

        new_pyfuzzy_system.fuzzify(i_dict2)
        new_pyfuzzy_system.inference()
        # new_pyfuzzy_system.defuzzify(output_dict2)

        for var_name, var in pyfuzzy_system_expected.variables.items():
            new_var = new_pyfuzzy_system.variables[var_name]

            self.assertIsInstance(new_var, var.__class__)
            self.assertEquals(new_var.description, var.description)
            self.assertEquals(new_var.min, var.min)
            self.assertEquals(new_var.max, var.max)
            self.assertEquals(new_var.unit, var.unit)

            for adj_name, adj in var.adjectives.items():
                new_adj = var.adjectives[adj_name]
                self._test_adj(adj, new_adj)

            #: is input
            if hasattr(var, 'fuzzify'):
                self._test_fuzzify(var.fuzzify, new_var.fuzzify)
            #: output
            else:
                self._test_defuzzify(var.defuzzify, new_var.defuzzify)

            var_value = var.getValue()
            new_var_value = new_var.getValue()
            # if var_value != new_var_value:
            #     import pdb; pdb.set_trace()

            self.assertEquals(new_var_value, var_value)

        for rule_name, rule in pyfuzzy_system_expected.rules.items():
            new_rule = new_pyfuzzy_system.rules[rule_name]
            self._test_rule(rule, new_rule)

        # pyfuzzy_system_expected.calculate(i_dict1, output_dict1)
        # new_pyfuzzy_system.calculate(i_dict2, output_dict2)

        # self.assertEquals(output_dict1['a'], output_dict2['a'])

    def _test_adj(self, adj, new_adj):
        " test only a given adjective "
        self.assertIsInstance(new_adj, adj.__class__)
        self._test_set(adj.set, new_adj.set)
        if adj.COM is not None and new_adj.COM is not None:
            self._test_norm(adj.COM, new_adj.COM)

        membership = adj.getMembership()
        new_membership = new_adj.getMembership()
        if membership != new_membership:
            print '>>' + adj.name
            # import pdb; pdb.set_trace()

        self.assertEquals(
            membership,
            new_membership,
            msg="%s != %s in %s" % (membership, new_membership, adj)
        )

    def _test_set(self, set, new_set):
        " test only a given set "
        self.assertIsInstance(new_set, set.__class__)

        params = []
        try:
            for arg in inspect.getargspec(set.__init__).args:
                if arg != 'self':
                    params.append(arg)
        # will raise this exception when the given type don't implement a __init__ function
        # (never overrided the object.__init__)
        except TypeError:
            pass

        for param_name in params:
            arg = getattr(set, param_name)
            new_arg = getattr(new_set, param_name)
            print "points>>>", arg
            print "new_points>>>", new_arg
            self.assertEquals(new_arg, arg)

        cog = None
        new_cog = None

        try:
            cog = set.getCOG()
        except:
            self.assertRaises(Exception, new_set.getCOG)
        else:
            new_cog = new_set.getCOG()
            self.assertEquals(new_cog, cog)
        self.assertEquals(new_set.points, set.points)

    def _test_norm(self, norm, new_norm):
        " test only a given norm "
        self.assertIsInstance(new_norm, norm.__class__)

        params = []
        try:
            for arg in inspect.getargspec(norm.__init__).args:
                if arg != 'self':
                    params.append(arg)
        # will raise this exception when the given type don't implement a __init__ function
        # (never overrided the object.__init__)
        except TypeError:
            pass

        for param_name in params:
            arg = getattr(norm, param_name)
            new_arg = getattr(new_norm, param_name)
            self.assertEquals(new_arg, arg)

        self.assertEquals(new_norm.UNKNOWN, norm.UNKNOWN)
        self.assertEquals(new_norm.T_NORM, norm.T_NORM)
        self.assertEquals(new_norm.S_NORM, norm.S_NORM)

    def _test_fuzzify(self, fuzzify, new_fuzzify):
        " test only a given fuzzify "
        self.assertIsInstance(new_fuzzify, fuzzify.__class__)

    def _test_defuzzify(self, defuzzify, new_defuzzify):
        " test only a given fuzzify "
        self.assertIsInstance(new_defuzzify, defuzzify.__class__)

        params = []
        try:
            for arg in inspect.getargspec(defuzzify.__init__).args:
                if arg != 'self' and arg != 'INF' and arg != 'ACC':
                    params.append(arg)
        # will raise this exception when the given type don't implement a __init__ function
        # (never overrided the object.__init__)
        except TypeError:
            pass

        for param_name in params:
            arg = getattr(defuzzify, param_name)
            new_arg = getattr(new_defuzzify, param_name)
            self.assertEquals(new_arg, arg)

        self._test_norm(defuzzify.INF, new_defuzzify.INF)
        self._test_norm(defuzzify._INF, new_defuzzify._INF)

        self._test_norm(defuzzify.ACC, new_defuzzify.ACC)
        self._test_norm(defuzzify._ACC, new_defuzzify._ACC)

    def _test_rule(self, rule, new_rule):
        "test only a given rule"

        self.assertIsInstance(new_rule, rule.__class__)

        self.assertEquals(rule.certainty, new_rule.certainty)
        print "rule: ", rule.name
        self._test_adj(rule.adjective, new_rule.adjective)
        self._test_norm(rule.CER, new_rule.CER)
        self._test_norm(rule.CER, new_rule.CER)
        self._test_operator(rule.operator, new_rule.operator)

    def _test_operator(self, operator, new_operator):
        "test only a given rule"
        self.assertIsInstance(new_operator, operator.__class__)

        if operator.__class__.__name__ == 'Compound':
            self._test_norm(operator.norm, new_operator.norm)

            for i_inputs in xrange(0, len(operator.inputs)):
                inp = operator.inputs[i_inputs]
                new_inp = new_operator.inputs[i_inputs]

                self._test_operator(inp, new_inp)

        elif operator.__class__.__name__ == 'Const':
            self.assertEquals(new_operator.value, operator.value)

        elif operator.__class__.__name__ == 'Input':

            print "op: INPUT"
            self._test_adj(operator.adjective, new_operator.adjective)

        elif operator.__class__.__name__ == 'Not':
            self._test_operator(operator.input, new_operator.input)

        op_call = operator()
        new_op_call = new_operator()
        self.assertEquals(
            op_call,
            new_op_call,
            msg="%s != %s in %s" % (op_call, new_op_call, operator)
        )
