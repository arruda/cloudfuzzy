
class ResetMock(object):

    @classmethod
    def reset_mock(cls, obj, attr_name):

        pre_mock_attr_name = '_pre_mock_' + attr_name
        default_attr = getattr(obj, pre_mock_attr_name, None)
        if default_attr:
            setattr(obj, attr_name, default_attr)
            delattr(obj, pre_mock_attr_name)

    @classmethod
    def reset_all_pre_mocks(cls, obj):
        fields = dir(obj)
        for attr_name in fields:
            if attr_name.startswith('_pre_mock_'):
                cls.reset_mock(obj, attr_name.replace('_pre_mock_', ''))

    @classmethod
    def set_pre_mock(cls, obj, attr_name):
        pre_mock_attr_name = '_pre_mock_' + attr_name
        default_attr = getattr(obj, attr_name)
        setattr(obj, pre_mock_attr_name, default_attr)


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
