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
import xfuzzy.lang.RulebaseCall;
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

        public FuzzySystem system;

        //this are populated using the getters and setters
        private Variable sysVar;
        private Variable baseVar;
        
        public Variable getSysVar(){
            if(this.sysVar != null){
                return this.sysVar;
            }
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
        public void setSysVar(Variable sysVar){
            this.sysVar = sysVar;
        }

        public Variable getBaseVar(){            
            if(this.baseVar != null){
                return this.baseVar;
            }
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
        public void setBaseVar(Variable baseVar){
            this.baseVar = baseVar;
        }

        public RulebaseCall getRuleBaseCall(){
            xfuzzy.lang.RulebaseCall rbc = null;
            if(this.idRuleBaseCall != null){

                rbc = system.getSpecification().getSystemModule().getRulebaseCalls()[this.idRuleBaseCall];
            }
            return rbc;

        }
    }

}