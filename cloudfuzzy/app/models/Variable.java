package models;

import java.util.ArrayList;
import java.util.Map;


import play.data.validation.Constraints;

import xfuzzy.lang.Specification;


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
    public int kind;

    /* if its an output this is set */
    public String idRuleBase;
    
    public final static int INPUT = xfuzzy.lang.Variable.INPUT;
    public final static int OUTPUT = xfuzzy.lang.Variable.OUTPUT;
    public final static int INNER = xfuzzy.lang.Variable.INNER;


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
            if( entry.getValue().equals(fVar.getType().getName()) ){
                var.idType = entry.getKey();
            }
        }
        

        return var;
    }

    /**
    *Create a Variable(fuzzy) with the given Variable as source, with a given type(fuzzy).
    * in this case its for creating a variable in a rulebase, so a rulebase has to be passed too.
    */
    public static void create(Variable var, xfuzzy.lang.Type type, xfuzzy.lang.Rulebase fRB, Specification spec)
    throws Exception{

        xfuzzy.lang.Variable fVar;
        if(var.kind == Variable.INPUT){
            fVar = new xfuzzy.lang.Variable(var.name,type,Variable.INPUT);
        } 
        else{
            fVar = new xfuzzy.lang.Variable(var.name,type,fRB);
        } 

        if(var.kind == xfuzzy.lang.Variable.INPUT){
             fRB.addInputVariable(fVar);
        }
        else{
            fRB.addOutputVariable(fVar);
        } 
        // spec.exchangeRulebase(original,copy);
        spec.setModified(true);
        spec.save();

    }

    /**
    *Create a Variable(fuzzy) with the given Variable as source, with a given type(fuzzy).
    * in this case its for creating a variable in a FuzzySystem, so don't need to pass a rulebase.
    */
    public static void create(Variable var, xfuzzy.lang.Type type,Specification spec)
    throws Exception{

        xfuzzy.lang.Variable fVar;
        if(var.kind == Variable.INPUT){
            fVar = new xfuzzy.lang.Variable(var.name,type,Variable.INPUT);
        } 
        else{
            fVar = new xfuzzy.lang.Variable(var.name,type,Variable.OUTPUT);
        } 

        spec.getSystemModule().addVariable(fVar);
        spec.setModified(true);
        spec.save();

    }

    /**
    * Return the type of this variable using the attribute idType
    *passing the given FuzzySystem
    */
    public Type getType(FuzzySystem sys){
        try{
            return Type.get(sys, Integer.valueOf(this.idType));
        }
        catch(Exception e){
            return null;
        }
    }

}