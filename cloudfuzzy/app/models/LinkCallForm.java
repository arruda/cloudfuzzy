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
                System.out.println("Variable.getSysVar():"+e.getMessage());
                var = new Variable("NULL",Variable.INNER);
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
                System.out.println("Variable.getBaseVar():"+e.getMessage());
                //var = new Variable("NULL",Variable.INNER);
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

//        /**
//         * Returns a variable dot for a given fuzzy system, giving a Variable(Fuzzy) as parameter
//         * @param sys
//         * @param var
//         */
//        public static VariableDot get(FuzzySystem sys, Variable var){
//        	VariableDot varDot = null;
//        	if(var == null) return varDot;
//        	
//        	//if is a inner variable means I have to search for it pair's in another rulebasecall
//        	if(var.isInner()){
//            	
//            	xfuzzy.lang.RulebaseCall rulebaseCalls[] = sys.getSpecification().getSystemModule().getRulebaseCalls();
//            	
//            	for (int irbc = 0; irbc < rulebaseCalls.length; irbc++) {
//            		xfuzzy.lang.RulebaseCall rulebaseCall = rulebaseCalls[irbc];
//            		
//            		
//            		  
//            		  
//            		  
//    			}
//            	
//        	}
//        	//If isnot a inner variable, then I can just look for the systems vars
//        	else{
//        		if(var.isInner()){
//            		Variable []sysInputVars = sys.getSpecification().getSystemModule().getInputs();
//            		for (int isiv = 0; isiv < sysInputVars.length; isiv++) {
//            			//found it
//    					if(sysInputVars[isiv] == var){
//    						varDot = new VariableDot();
//    						varDot.idSysVar = isiv;
//    						varDot.kindSysVar = Variable.INPUT;
//    						varDot.setSysVar(sysInputVars[isiv]);
//    						return varDot;
//    					}
//    				}
//        		}
//        		else{
//            		Variable []sysOutputVars = sys.getSpecification().getSystemModule().getOutputs();
//            		for (int isov = 0; isov < sysOutputVars.length; isov++) {
//            			//found it
//    					if(sysOutputVars[isov] == var){
//    						varDot = new VariableDot();
//    						varDot.idSysVar = isov;
//    						varDot.kindSysVar = Variable.OUTPUT;
//    						varDot.setSysVar(sysOutputVars[isov]);
//    						return varDot;
//    					}
//    				}
//        		}
//        		
//        		
//        	}
//        	
//        	
//        	return varDot;
//        }
        

    }
    
    /**
     *  Returns all linkcallform for a given sys.
     *  
     * @param sys
     * @return
     */
    public static ArrayList<LinkCallForm> getLinks(FuzzySystem sys){

    	ArrayList<LinkCallForm> links = new ArrayList<LinkCallForm>();
    	xfuzzy.lang.RulebaseCall rulebaseCalls[] = sys.getSpecification().getSystemModule().getRulebaseCalls();
        

    	for (int irbc = 0; irbc < rulebaseCalls.length; irbc++) {
    		xfuzzy.lang.RulebaseCall rulebaseCall = rulebaseCalls[irbc];
    		
    		for (int ivi = 0; ivi < rulebaseCall.getInputValues().length; ivi++) {
    			Variable linkVariable =  rulebaseCall.getInputVariables()[ivi];    	
    			
    			//There is a connection to here.(this is the destDot)
				if(!linkVariable.getName().equals("NULL")){
					VariableDot destDot = new VariableDot();
					destDot.idRuleBaseCall = irbc;
					destDot.idBaseVar = ivi;
					destDot.kindBaseVar = Variable.INPUT;
				}
				
			}
    		
		}
    	return null;
    }
    
}