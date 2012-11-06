package models;

import java.util.ArrayList;


import play.data.validation.Constraints;



public class Variable{
    
    /**
     * This ID is the position of the fuzzy variable in the array of
     * Variables(can be input or output).
     */
    public Integer id;

    
    @Constraints.Required
    public String name;

    /* the type ID */
    @Constraints.Required
    public String idType;


    /* this is what xFuzzy calls access, determines if this variable is input, output or inner */
    private int kind;

    /* if its an out put this is set */
    public RuleBase ruleBase;
    
}