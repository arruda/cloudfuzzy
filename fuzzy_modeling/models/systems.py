# -*- coding: utf-8 -*-

from django.db import models


from django.utils.translation import ugettext_lazy as _


from fuzzy_modeling.models.utils import PyFuzzyMixin
from fuzzy.System import System



class SystemModel(models.Model, PyFuzzyMixin):
    """
    A Fuzzy system model
    """

    class Meta:
        app_label = 'fuzzy_modeling'

    description = models.TextField(_("Description"))

    # class Meta:
    #     verbose_name_plural = "Colaboradores"

    def __unicode__(self):
        return self.description

    def get_pyfuzzy(self):
        """
        Return the Pyfuzzy class of this model
        """
        system = System(description=self.description)
        for ivar in self.inputvariablemodel_set.all():
            fuzzy_var = ivar.get_pyfuzzy()
            system.variables[ivar.name] = fuzzy_var

        for ovar in self.outputvariablemodel_set.all():
            fuzzy_var = ovar.get_pyfuzzy()
            system.variables[ovar.name] = fuzzy_var

        for rule in self.rulemodel_set.all():
            fuzzy_rule = rule.get_pyfuzzy()
            system.rules[rule.name] = fuzzy_rule


        return system

    @classmethod
    def from_pyfuzzy(cls, pyfuzzy):
        """
        Return the model representation of an instance of the pyfuzzy attr
        """
        system_model = cls()
        system_model.description = pyfuzzy.description
        system_model.save()

        #variables
        for v_name, pyfuzzy_var in pyfuzzy.variables.items():
            # set the name of this var inside the pyfuzzy_var
            pyfuzzy_var.name = v_name

            # is an output variable
            if pyfuzzy_var.__class__.__name__ == 'OutputVariable':
                OutputvarModel = system_model.outputvariablemodel_set.model
                outputvar_model = OutputvarModel.from_pyfuzzy(pyfuzzy_var)
                system_model.outputvariablemodel_set.add(outputvar_model)

            # is an input variable
            else:
                InputvarModel = system_model.inputvariablemodel_set.model
                inputvar_model = InputvarModel.from_pyfuzzy(pyfuzzy_var)
                system_model.inputvariablemodel_set.add(inputvar_model)


        # rules
        for r_name, rule in pyfuzzy.rules.items():
            # set the name of this var to be used latter
            rule.name = r_name
            rule_model = system_model.rulemodel_set.model.from_pyfuzzy(rule)
            system_model.rulemodel_set.add(rule_model)


        system_model.save()
        return system_model


def _createSystem():
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
