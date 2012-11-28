package models;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import models.FuzzySystem;
// import models.Variable;

import javax.validation.*;
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

    @Valid
    public List<VariableDot> variableDots;

    public FuzzySystem system;

    public static class VariableDot{

        public Integer idRuleBaseCall;

        public Integer idSysVar;
        public Integer kindSysVar;

        public Integer idBaseVar;
        public Integer kindBaseVar;


        
        public Variable getSysVar(FuzzySystem system){
            xfuzzy.lang.RulebaseCall rbc = null;
            xfuzzy.lang.Rulebase fRB = null;
            if(this.idRuleBaseCall != null){

                rbc = system.getSpecification().getSystemModule().getRulebaseCalls()[this.idRuleBaseCall];
                fRB = rbc.getRulebase();
            }

            Variable var = null;
            try{
                
                if(fRB == null){
                    var = models.Variable.getFuzzy(system, this.idSysVar,  this.kindSysVar);
                }
                else{            
                    var = models.Variable.getFuzzy(system, fRB,  this.idSysVar, this.kindSysVar);
                }
            }
            catch(Exception e){    
                System.out.println(e.getMessage());
            }

           return var;
        }

        public Variable getBaseVar(FuzzySystem system){
            xfuzzy.lang.RulebaseCall rbc = null;
            xfuzzy.lang.Rulebase fRB = null;
            if(this.idRuleBaseCall != null){

                rbc = system.getSpecification().getSystemModule().getRulebaseCalls()[this.idRuleBaseCall];
                fRB = rbc.getRulebase();
            }

            Variable var = null;
            try{
                
                if(fRB == null){
                    var = models.Variable.getFuzzy(system, this.idBaseVar,  this.kindBaseVar);
                }
                else{            
                    var = models.Variable.getFuzzy(system, fRB,  this.idBaseVar, this.kindBaseVar);
                }
            }
            catch(Exception e){    
                System.out.println(e.getMessage());
            }

           return var;
        }
    }

}