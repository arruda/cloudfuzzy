package forms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import models.FuzzySystem;
// import models.Variable;

import play.data.validation.Constraints;

import xfuzzy.lang.ParamMemFunc;
import xfuzzy.lang.Specification;
import xfuzzy.lang.Relation;
import xfuzzy.lang.Rulebase;
import xfuzzy.lang.Rule;
import xfuzzy.lang.Conclusion;
import xfuzzy.lang.Variable;


public class LinkCallForm{
    
    /**
     * This ID is the position in the array
     */
    public Integer id;

    public Integer idRuleBaseCall;

    public Integer idSysVar;

    public Integer idBaseVar;

    public FuzzySystem system;

    
    // public Variable getSysVar(){
    //     xfuzzy.lang.RulebaseCall rbc = null;
    //     Integer id_rb = null;
    //     if(this.idRuleBaseCall != null){

    //         rbc = this.system.getRulebaseCalls()[this.idRuleBaseCall];
    //         id_rb = rbc.getRulebase();
    //     }

    //     Variable models.Variable.getFuzzy(this.system, this.id, Integer id_var, Interger kind);
    //     Variable outputs[] = rb.getOutputs();

    //     Relation premise = null;
    //     for(int j=inputs.length-1; j>=0; j--)  {
    //         //if the actual input is not blank in the mf selection

    //         if(!this.inputs.get(j).equals("")){
    //             //get the actual selected mf for the current input var
    //             ParamMemFunc imf = 
    //                 inputs[j].getType().getAllMembershipFunctions()[Integer.valueOf(this.inputs.get(j))];

    //             Relation rel = Relation.create(Relation.IS,null,null,inputs[j],imf,null);
    //             if(premise == null) premise = rel;
    //             else premise=Relation.create(Relation.AND,rel,premise,null,null,rb);
    //         }
    //     }

    //     Rule newrule = new Rule(premise,this.degree);
    //     for(int j=0; j<outputs.length; j++) {
    //         if(!this.outputs.get(j).equals("")){
    //             //get the actual selected mf for the current output var
    //             ParamMemFunc omf = 
    //                 outputs[j].getType().getAllMembershipFunctions()[Integer.valueOf(this.outputs.get(j))];
    //             newrule.add(new Conclusion(outputs[j],omf,rb));
    //         }
    //     }

    //    return newrule;
    // }
}