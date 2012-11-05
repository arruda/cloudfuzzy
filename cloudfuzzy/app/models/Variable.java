package models;

import java.util.ArrayList;


import play.data.validation.Constraints;



public class Variable{
    
    /**
     * This ID is the position of the fuzzy mf in the array of
     * Variables(can be input or output).
     */
    public Integer id;

    
    @Constraints.Required
    public String name;

    @Constraints.Required
    public Type type;


    /* this is what xFuzzy calls access, determines if this variable is input, output or inner */
    private int kind;

    /* if its an out put this is set */
    // public RuleBase ruleBase;
    
}