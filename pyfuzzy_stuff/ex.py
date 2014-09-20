# -*- coding: utf-8 -*-

# Cet exemple permet de dÃ©montrer l'utilisation de pyfuzzy pour la prise de dÃ©cision.
# L'exemple utilisÃ© est tirÃ© d'un examen Median de l'UV SY10
# enseignÃ©e par Z. Zalila (Groupe de logique floue) de l'UniversitÃ© de Technologie de
# CompiÃ¨gne.  (d'aprÃ¨s une idÃ©e de A. Biannic & F. Linget, SY 10 A97)


def create_system():
    """Create fuzzy system."""
    #import fuzzy
    import fuzzy.System
    import fuzzy.InputVariable
    import fuzzy.OutputVariable
    import fuzzy.fuzzify.Plain
    import fuzzy.defuzzify.Dict
    import fuzzy.Adjective
    import fuzzy.Rule
    import fuzzy.operator.Input
    import fuzzy.operator.Compound
    import fuzzy.norm.Min
    import fuzzy.set.Function
    import fuzzy.set.Polygon
    import fuzzy.set.Triangle

    # create system object
    system = fuzzy.System.System()

    # definition of input variable
    input_Nbre_habitants = fuzzy.InputVariable.InputVariable(
                fuzzy.fuzzify.Plain.Plain(),
                description="Taille aglomÃ©ration",
                min=0.0,max=10000.0,
                unit="Milliers d'habitants"
                )


    # add variable to system ###########################################
    system.variables["input_Nbre_habitants"] = input_Nbre_habitants

    # create fuzzy set
    #in1_set = fuzzy.set.Trapez.Trapez(m1=1.,m2=20.,alpha=0., beta=20.)
    in1_set = fuzzy.set.Polygon.Polygon([(1.,1.),(20.,1.),(40.,0.)])
    # make it to an adjective
    in1 = fuzzy.Adjective.Adjective(in1_set)
    # and add it to the input variable
    input_Nbre_habitants.adjectives["Petite"] = in1

    # create fuzzy set
    in2_set = fuzzy.set.Triangle.Triangle(m=60.,alpha=40., beta=40.)
    # make it to an adjective
    in2 = fuzzy.Adjective.Adjective(in2_set)
    # and add it to the input variable
    input_Nbre_habitants.adjectives["Moyenne"] = in2

    # create fuzzy set
    #in3_set = fuzzy.set.Trapez.Trapez(m1=100., m2=10000. ,alpha=20., beta=0.)
    in3_set = fuzzy.set.Polygon.Polygon([(80.,0.),(100.,1.),(120.,1.)])
    # make it to an adjective
    in3 = fuzzy.Adjective.Adjective(in3_set)
    # and add it to the input variable
    input_Nbre_habitants.adjectives["Grande"] = in3


    # definition of input variable
    input_dist_frontiere = fuzzy.InputVariable.InputVariable(
                fuzzy.fuzzify.Plain.Plain(),
                description="Distance de la frontiÃ¨re",
                min=0.0,max=1000.0,
                unit="km"
                )


    # add variable to system ###########################################
    system.variables["input_dist_frontiere"] = input_dist_frontiere

    # create fuzzy set
    #in1_set = fuzzy.set.Trapez.Trapez(m1=0.001,m2=50.,alpha=0., beta=50.)
    in1_set = fuzzy.set.Polygon.Polygon([(0.,1.),(50.,1.),(100.,0.)])
    # make it to an adjective
    in1 = fuzzy.Adjective.Adjective(in1_set)
    # and add it to the input variable
    input_dist_frontiere.adjectives["Faible"] = in1

    # create fuzzy set
    in2_set = fuzzy.set.Triangle.Triangle(m=100.,alpha=50., beta=50.)
    # make it to an adjective
    in2 = fuzzy.Adjective.Adjective(in2_set)
    # and add it to the input variable
    input_dist_frontiere.adjectives["Moyenne"] = in2

    # create fuzzy set
    #in3_set = fuzzy.set.Trapez.Trapez(m1=200., m2=1000. ,alpha=75., beta=0.)
    in3_set = fuzzy.set.Polygon.Polygon([(125.,0.),(200.,1.),(250.,1.)])
    # make it to an adjective
    in3 = fuzzy.Adjective.Adjective(in3_set)
    # and add it to the input variable
    input_dist_frontiere.adjectives["Elevee"] = in3


    # definition of output variable
    # we want use later the center of gravity method
    output_risque_sit_geo = fuzzy.OutputVariable.OutputVariable(defuzzify=fuzzy.defuzzify.Dict.Dict())
    # more properties could be (not implemented)
    # - output as value or membership of adjectives in a dictionary
    #   (input could work this way too.)

    # add to system
    system.variables["output_risque_sit_geo"] = output_risque_sit_geo

    out1_set = fuzzy.set.Function.Function()
    out1 = fuzzy.Adjective.Adjective(out1_set)
    output_risque_sit_geo.adjectives["Nul"] = out1

    out2_set = fuzzy.set.Function.Function()
    out2 = fuzzy.Adjective.Adjective(out2_set) # (out2_set)
    output_risque_sit_geo.adjectives["Faible"] = out2

    out3_set = fuzzy.set.Function.Function()
    out3 = fuzzy.Adjective.Adjective(out3_set) # (out2_set)
    output_risque_sit_geo.adjectives["Moyen"] = out3

    out4_set = fuzzy.set.Function.Function()
    out4 = fuzzy.Adjective.Adjective(out4_set) # (out2_set)
    output_risque_sit_geo.adjectives["Eleve"] = out4

    out5_set = fuzzy.set.Function.Function()
    out5 = fuzzy.Adjective.Adjective(out5_set) # (out2_set)
    output_risque_sit_geo.adjectives["Inacceptable"] = out5


    # create a fuzzy rule
    rule1 = fuzzy.Rule.Rule(
        adjective=system.variables["output_risque_sit_geo"].adjectives["Nul"],
        operator=fuzzy.operator.Compound.Compound(
            fuzzy.norm.Min.Min(),
            fuzzy.operator.Input.Input(
                system.variables["input_Nbre_habitants"].adjectives["Petite"],
                    ),
                fuzzy.operator.Input.Input(
                    system.variables["input_dist_frontiere"].adjectives["Elevee"],
                    )
        )
    )

    # create a fuzzy rule
    rule2 = fuzzy.Rule.Rule(
        adjective=system.variables["output_risque_sit_geo"].adjectives["Faible"],
        operator=fuzzy.operator.Compound.Compound(
            fuzzy.norm.Min.Min(),
            fuzzy.operator.Input.Input(
                system.variables["input_Nbre_habitants"].adjectives["Moyenne"],
                    ),
                fuzzy.operator.Input.Input(
                    system.variables["input_dist_frontiere"].adjectives["Elevee"],
                    )
        )
    )

    # create a fuzzy rule
    rule3 = fuzzy.Rule.Rule(
        adjective=system.variables["output_risque_sit_geo"].adjectives["Eleve"],
        operator=fuzzy.operator.Compound.Compound(
            fuzzy.norm.Min.Min(),
            fuzzy.operator.Input.Input(
                system.variables["input_Nbre_habitants"].adjectives["Grande"],
                    ),
                fuzzy.operator.Input.Input(
                    system.variables["input_dist_frontiere"].adjectives["Elevee"],
                    )
        )
    )

    # create a fuzzy rule
    rule4 = fuzzy.Rule.Rule(
        adjective=system.variables["output_risque_sit_geo"].adjectives["Faible"],
        operator=fuzzy.operator.Compound.Compound(
            fuzzy.norm.Min.Min(),
            fuzzy.operator.Input.Input(
                system.variables["input_Nbre_habitants"].adjectives["Petite"],
                    ),
                fuzzy.operator.Input.Input(
                    system.variables["input_dist_frontiere"].adjectives["Moyenne"],
                    )
        )
    )

    # create a fuzzy rule
    rule5 = fuzzy.Rule.Rule(
        adjective=system.variables["output_risque_sit_geo"].adjectives["Moyen"],
        operator=fuzzy.operator.Compound.Compound(
            fuzzy.norm.Min.Min(),
            fuzzy.operator.Input.Input(
                system.variables["input_Nbre_habitants"].adjectives["Moyenne"],
                    ),
                fuzzy.operator.Input.Input(
                    system.variables["input_dist_frontiere"].adjectives["Moyenne"],
                    )
        )
    )

    # create a fuzzy rule
    rule6 = fuzzy.Rule.Rule(
        adjective=system.variables["output_risque_sit_geo"].adjectives["Inacceptable"],
        operator=fuzzy.operator.Compound.Compound(
            fuzzy.norm.Min.Min(),
            fuzzy.operator.Input.Input(
                system.variables["input_Nbre_habitants"].adjectives["Grande"],
                    ),
                fuzzy.operator.Input.Input(
                    system.variables["input_dist_frontiere"].adjectives["Moyenne"],
                    )
        )
    )

    # create a fuzzy rule
    rule7 = fuzzy.Rule.Rule(
        adjective=system.variables["output_risque_sit_geo"].adjectives["Eleve"],
        operator=fuzzy.operator.Compound.Compound(
            fuzzy.norm.Min.Min(),
            fuzzy.operator.Input.Input(
                system.variables["input_Nbre_habitants"].adjectives["Petite"],
                    ),
                fuzzy.operator.Input.Input(
                    system.variables["input_dist_frontiere"].adjectives["Faible"],
                    )
        )
    )

    # create a fuzzy rule
    rule8 = fuzzy.Rule.Rule(
        adjective=system.variables["output_risque_sit_geo"].adjectives["Inacceptable"],
        operator=fuzzy.operator.Compound.Compound(
            fuzzy.norm.Min.Min(),
            fuzzy.operator.Input.Input(
                system.variables["input_Nbre_habitants"].adjectives["Moyenne"],
                    ),
                fuzzy.operator.Input.Input(
                    system.variables["input_dist_frontiere"].adjectives["Faible"],
                    )
        )
    )

    # create a fuzzy rule
    rule9 = fuzzy.Rule.Rule(
        adjective=system.variables["output_risque_sit_geo"].adjectives["Inacceptable"],
        operator=fuzzy.operator.Compound.Compound(
            fuzzy.norm.Min.Min(),
            fuzzy.operator.Input.Input(
                system.variables["input_Nbre_habitants"].adjectives["Grande"],
                    ),
                fuzzy.operator.Input.Input(
                    system.variables["input_dist_frontiere"].adjectives["Faible"],
                    )
        )
    )
    # add rules to system
    system.rules["rule1"] = rule1
    system.rules["rule2"] = rule2
    system.rules["rule3"] = rule3
    system.rules["rule4"] = rule4
    system.rules["rule5"] = rule5
    system.rules["rule6"] = rule6
    system.rules["rule7"] = rule7
    system.rules["rule8"] = rule8
    system.rules["rule9"] = rule9

    # system ready to use
    return system
