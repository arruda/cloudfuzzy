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

    public Integer kindSysVar;

    public Integer idBaseVar;
    public Integer kindBaseVar;

    public FuzzySystem system;

    
    public Variable getSysVar(){
        xfuzzy.lang.RulebaseCall rbc = null;
        xfuzzy.lang.Rulebase fRB = null;
        if(this.idRuleBaseCall != null){

            rbc = this.system.getSpecification().getSystemModule().getRulebaseCalls()[this.idRuleBaseCall];
            fRB = rbc.getRulebase();
        }

        Variable var = null;
        try{
            
            if(fRB == null){
                var = models.Variable.getFuzzy(this.system, this.idSysVar,  this.kindSysVar);
            }
            else{            
                var = models.Variable.getFuzzy(this.system, fRB,  this.idSysVar, this.kindSysVar);
            }
        }
        catch(Exception e){    
            System.out.println(e.getMessage());
        }

       return var;
    }
}