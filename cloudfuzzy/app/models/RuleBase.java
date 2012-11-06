package models;

import java.util.List;
import java.util.ArrayList;


import play.data.validation.Constraints;

import xfuzzy.lang.Specification;


public class RuleBase {
    
    /**
     * This ID is the position of the RuleBase  in the array of
     * RuleBases
     */
    public Integer id;


    public String name;


    /* the id of the operator set selected for this */
    public String idOperatorSet;


    public List<Variable> inputvars;

    public List<Variable> outputvar;



    // private Rule rule[];
    // private int link;


    /**
    *Create a RuleBase(fuzzy) with the given RuleBase as source given spec.
    */
    public static void create(RuleBase rb, Specification spec)
    throws Exception{

        //Instantiate a new RuleBase with the correct name
        xfuzzy.lang.Rulebase newFuzzyRB = new xfuzzy.lang.Rulebase(rb.name);

        xfuzzy.lang.Operatorset fuzzyOpSet;

        //if its the default
        if(rb.idOperatorSet.equals("")){
            fuzzyOpSet = new xfuzzy.lang.Operatorset();
        }
        //else gets it from the specs
        else{
             fuzzyOpSet= spec.getOperatorsets()[Integer.valueOf(rb.idOperatorSet)];
        }
        newFuzzyRB.operation = fuzzyOpSet;

        spec.addRulebase(newFuzzyRB);

        //save the spec.
        spec.setModified(true);
        spec.save();        
    }
    
}