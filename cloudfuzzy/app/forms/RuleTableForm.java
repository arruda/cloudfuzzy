package forms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import models.FuzzySystem;

import play.data.validation.Constraints;

import xfuzzy.lang.ParamMemFunc;
import xfuzzy.lang.Specification;
import xfuzzy.lang.Relation;
import xfuzzy.lang.Rulebase;
import xfuzzy.lang.Rule;
import xfuzzy.lang.Conclusion;
import xfuzzy.lang.Variable;


public class RuleTableForm{
    
    /**
     * This ID is the position of the fuzzy mf in the array of
     * MFs in a type.
     */
    public Integer id;

    @Constraints.Required
    public Double degree;
    
    //a array of the id of the mfs used in each input
    public ArrayList<String> inputs;

    //a array of the id of the mfs used in each output
    public ArrayList<String> outputs;

    
    public Rule getFuzzyRule(Rulebase rb){
        Variable inputs[] = rb.getInputs();
        Variable outputs[] = rb.getOutputs();

        Relation premise = null;
        for(int j=inputs.length-1; j>=0; j--)  {
            //if the actual input is not blank in the mf selection

            if(!this.inputs.get(j).equals("")){
                //get the actual selected mf for the current input var
                ParamMemFunc imf = 
                    inputs[j].getType().getAllMembershipFunctions()[Integer.valueOf(this.inputs.get(j))];

                Relation rel = Relation.create(Relation.IS,null,null,inputs[j],imf,null);
                if(premise == null) premise = rel;
                else premise=Relation.create(Relation.AND,rel,premise,null,null,rb);
            }
        }

        Rule newrule = new Rule(premise,this.degree);
        for(int j=0; j<outputs.length; j++) {
            if(!this.outputs.get(j).equals("")){
                //get the actual selected mf for the current output var
                ParamMemFunc omf = 
                    outputs[j].getType().getAllMembershipFunctions()[Integer.valueOf(this.outputs.get(j))];
                newrule.add(new Conclusion(outputs[j],omf,rb));
            }
        }

       return newrule;
    }
    
}