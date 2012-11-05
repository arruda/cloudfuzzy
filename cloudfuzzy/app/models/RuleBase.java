package models;

import java.util.List;
import java.util.ArrayList;


import play.data.validation.Constraints;



public class RuleBase{
    
    /**
     * This ID is the position of the RuleBase  in the array of
     * RuleBases
     */
    public Integer id;

    
    @Constraints.Required
    public String name;

    @Constraints.Required
    public OperatorSet operatorSet;
    

    public List<Variable> inputvars;

    public List<Variable> outputvar;



    // private Rule rule[];
    // private int link;
    
}