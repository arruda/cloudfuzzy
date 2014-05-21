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

        pyfuzzy_system_expected.calculate(i_dict1,output_dict1)
        new_pyfuzzy_system.calculate(i_dict2,output_dict2)

        self.assertEquals(output_dict1['a'], output_dict2['a'])
