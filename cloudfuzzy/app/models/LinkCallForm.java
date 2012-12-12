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
     * (not in use!)
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

        /**
         * Returns a variable dot for a given fuzzy system, giving a Variable(Fuzzy) as parameter
         * If is a inner variable, then it return the first rulebasecall that has it as an output
         *  in the connection:
         *   rbc0.vo1 -> rbc1.vi1 
         *   we have the inner variable "i0" doing this connection, this is the variable that is passed to this function
         *   So if you pass the "i0" variable it will return the VariableDot representing the origin(rbc0.vo1)
         * @param sys
         * @param var
         */
        public static VariableDot searchDot(FuzzySystem sys, Variable var){
        	VariableDot varDot = null;
        	if(var == null) return varDot;
        	
        	//if is a inner variable means I have to search for it pair's in another rulebasecall
        	if(var.isInner()){
            	
            	xfuzzy.lang.RulebaseCall rulebaseCalls[] = sys.getSpecification().getSystemModule().getRulebaseCalls();

            	//go through all rulebasecalls and get the first one that has this var as an output
            	for (int irbc = 0; irbc < rulebaseCalls.length; irbc++) {
            		xfuzzy.lang.RulebaseCall rulebaseCall = rulebaseCalls[irbc];
            		
            		for (int iov = 0; iov < rulebaseCall.getOutputVariables().length; iov++) {
						Variable linkVariable = rulebaseCall.getOutputVariables()[iov];
						//found it
						if(linkVariable == var){
							varDot = new VariableDot();
							varDot.idRuleBaseCall = irbc;
							varDot.idBaseVar = iov;
							varDot.kindBaseVar = Variable.OUTPUT;
							varDot.setBaseVar(linkVariable);
							return varDot;
						}
					}
            		
            		  
            		  
            		  
    			}
            	
        	}
        	//If isnot a inner variable, then I can just look for the systems vars
        	else{
        		
        		//check if is input or output
        		if(var.isInput()){
            		Variable []sysInputVars = sys.getSpecification().getSystemModule().getInputs();
            		for (int isiv = 0; isiv < sysInputVars.length; isiv++) {
            			//found it
    					if(sysInputVars[isiv] == var){
    						varDot = new VariableDot();
    						varDot.idSysVar = isiv;
    						varDot.kindSysVar = Variable.INPUT;
    						varDot.setSysVar(sysInputVars[isiv]);
    						return varDot;
    					}
    				}
        		}
        		else{
            		Variable []sysOutputVars = sys.getSpecification().getSystemModule().getOutputs();
            		for (int isov = 0; isov < sysOutputVars.length; isov++) {
            			//found it
    					if(sysOutputVars[isov] == var){
    						varDot = new VariableDot();
    						varDot.idSysVar = isov;
    						varDot.kindSysVar = Variable.OUTPUT;
    						varDot.setSysVar(sysOutputVars[isov]);
    						return varDot;
    					}
    				}
        		}
        		
        		
        	}
        	
        	
        	return varDot;
        }
        

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
        
    	//loop through all rulebasecalls
    	for (int irbc = 0; irbc < rulebaseCalls.length; irbc++) {
    		xfuzzy.lang.RulebaseCall rulebaseCall = rulebaseCalls[irbc];
    		
    		//loop in all inputvariables
    		for (int ivi = 0; ivi < rulebaseCall.getInputVariables().length; ivi++) {
    			Variable linkVariable =  rulebaseCall.getInputVariables()[ivi];    	
    			
    			//There is a connection to here.(this is the destDot)
				if(!linkVariable.getName().equals("NULL")){
					VariableDot destDot = new VariableDot();
					destDot.idRuleBaseCall = irbc;
					destDot.idBaseVar = ivi;
					destDot.kindBaseVar = Variable.INPUT;

					//find the original dot of this connection passing the link variable
					VariableDot origDot = VariableDot.searchDot(sys, linkVariable);
					
					//if have both of them(should have unless there was a problem) then add a new link
					//to the links list
					if(destDot != null && origDot != null){
						LinkCallForm newLink = new LinkCallForm();
						newLink.system = sys;
						newLink.variableDots = new ArrayList<LinkCallForm.VariableDot>();
						newLink.variableDots.add(origDot);
						newLink.variableDots.add(destDot);
						
						links.add(newLink);
					}
				}
				
			}
    		

    		//loop in all outputvariables
    		for (int ivo = 0; ivo < rulebaseCall.getOutputVariables().length; ivo++) {
    			Variable linkVariable =  rulebaseCall.getOutputVariables()[ivo];    	
    			
    			//There is a connection to here.(this is the originDot)
    			//in this case only the variables that are output should be considered
				if(!linkVariable.getName().equals("NULL") && linkVariable.isOutput()){
					VariableDot origDot = new VariableDot();
					origDot.idRuleBaseCall = irbc;
					origDot.idBaseVar = ivo;
					origDot.kindBaseVar = Variable.OUTPUT;

					//find the destdot of this connection passing the link variable
					VariableDot destDot = VariableDot.searchDot(sys, linkVariable);
					
					//if have both of them(should have unless there was a problem) then add a new link
					//to the links list
					if(destDot != null && origDot != null){
						LinkCallForm newLink = new LinkCallForm();
						newLink.system = sys;
						newLink.variableDots = new ArrayList<LinkCallForm.VariableDot>();
						newLink.variableDots.add(origDot);
						newLink.variableDots.add(destDot);
						
						links.add(newLink);
					}
				}
				
			}
    		
    		
		}
    	return links;
    }
    
    
}