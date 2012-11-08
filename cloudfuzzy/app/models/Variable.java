package models;

import java.util.ArrayList;
import java.util.Map;


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

    /* if its an output this is set */
    public String idRuleBase;
    


    /**
    * Creates a Variable using a XFuzzy Variable as base
    */
    public static Variable createFromFuzzyVariable(FuzzySystem sys, xfuzzy.lang.Variable fVar, Integer id_var){
        Variable var = new Variable();
        var.id = id_var;
        var.name = fVar.getName();


        //add the input/output vars    
        Map<String,String> availableTypesMap = FuzzySystem.getAvailableTypesMapForFuzzySystem(sys); 

        //put the correct idType
        for (Map.Entry<String,String> entry : availableTypesMap.entrySet()) {
            if( entry.getValue().equals(fVar.getName()) ){
                var.idType = entry.getKey();
            }
        }
        
        return var;
    }
}