package models;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

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

    public List<Variable> outputvars;



    // private Rule rule[];
    // private int link;



    /**
    * Return (creating it from the spec file) the given RuleBase,
    * Passing it's FuzzySystem, and it's id(the position of this RuleBase in the RuleBases array)
    * if any error occours, like the id_rb is out of the array bound, then raise an Exception
    * that should be treated in along
    */
    public static RuleBase get(FuzzySystem sys, Integer id_rb)
    throws Exception {

      return RuleBase.createFromFuzzyRuleBase(
                sys,
                RuleBase.getFuzzy(sys, id_rb),
                id_rb
        );

    }
    
    /**
    * Return the given RuleBase(The xFuzzy one) for a given id_rb,
    * Passing it's FuzzySystem, and it's id(the position of this RuleBase in the RuleBases array)
    * if any error occours, like the id_rb is out of the array bound, then raise an Exception
    * that should be treated in along
    */
    public static xfuzzy.lang.Rulebase getFuzzy(FuzzySystem sys, Integer id_rb)
    throws Exception {
      Specification spec=null;
      //can throw an exception
      spec = sys.getSpecification();  
      

      //the RuleBases for this modeling
      xfuzzy.lang.Rulebase [] rbs = spec.getRulebases();

      //ensures that the required type id is within the bounds tps array
      if(rbs.length <= id_rb || id_rb < 0){
            throw new Exception("Wrong Rule Base ID:"+id_rb);
      }

      return rbs[id_rb];
    }


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

    /**
    * Creates a RuleBase using a XFuzzy RuleBase as base
    */
    public static RuleBase createFromFuzzyRuleBase(FuzzySystem sys, xfuzzy.lang.Rulebase fRB, Integer id_rb){
        RuleBase rb = new RuleBase();
        rb.id = id_rb;
        rb.name = fRB.getName();
        Map<String,String> operatorSetsMap = FuzzySystem.getAvailableOperatorSetForFuzzySystem(sys); 

        // System.out.println("Oparation : " + fRB.operation.getName());      
        //put the correct idOperatorSet
        for (Map.Entry<String,String> entry : operatorSetsMap.entrySet()) {
            if( entry.getValue().equals(fRB.operation.getName()) ){
                rb.idOperatorSet = entry.getKey();
            }
        }

        //add the input/output vars    
        rb.inputvars = new ArrayList<Variable>();
        xfuzzy.lang.Variable inputVars[] = fRB.getInputs();    
        for(int i =0; i< inputVars.length; i++){
            rb.inputvars.add(Variable.createFromFuzzyVariable(sys,inputVars[i],i));
        } 
        rb.outputvars = new ArrayList<Variable>();
        xfuzzy.lang.Variable outputVars[]= fRB.getOutputs();    
        for(int i =0; i< outputVars.length; i++){
            rb.outputvars.add(Variable.createFromFuzzyVariable(sys,outputVars[i],i));
        }

        return rb;
    }
}