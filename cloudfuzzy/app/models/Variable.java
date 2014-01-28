 
	/*
	*
    * (c) 2013, 2014 INT - National Institute of Technology
	*
    * CloudFuzzy's team: 
	*	Dayse Mourao Arruda, Felipe Arruda Pontes, Bruno Barcellos Farias de Oliveira.
	*
    * The authors may be contacted by the email address: dayse.arruda@int.gov.br
	*
	* This file is part of CloudFuzzy - version Beta - 2014/January.
	*
    * We apply XFuzzy 3.0 as fuzzy engine to generate and evaluate alternative plans.
    * XFuzzy is a free design environment for fuzzy logic based systems distributed by IMSE-CNM.
	* If you need more information about the XFuzzy 3.0, please go to project's site:
	* http://www2.imse-cnm.csic.es/Xfuzzy/
    *                    
	* CloudFuzzy is free software: you can redistribute it and/or modify
	* it under the terms of the GNU General Public License as published by
	* the Free Software Foundation, either version 3 of the License, or
	* (at your option) any later version.
	*
	* CloudFuzzy is distributed in the hope that it will be useful,
	* but WITHOUT ANY WARRANTY; without even the implied warranty of
	* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
	* GNU General Public License for more details.
	*
	* You should have received a copy of the GNU General Public License
	* along with CloudFuzzy. If not, see <http://www.gnu.org/license/>.
    */
        
           
     
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

	/* the actual value for this variable */
	public Double value;

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
        var.value = fVar.point(0.5);


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
    * Return the given Variable(The xFuzzy one) for a given id_var, and the Fuzzy Rulebase instead of it's id
    * Passing it's FuzzySystem, and it's id(the position of this var in the vars of the given kind array)
    * if any error occours, like the id_var is out of the array bound, then raise an Exception
    * that should be treated in along
    *
    */
    public static xfuzzy.lang.Variable getFuzzy(FuzzySystem sys,xfuzzy.lang.Rulebase fRB, Integer id_var, Integer kind)
    throws Exception {
      xfuzzy.lang.Variable var=null;
      Specification spec=null;
      //can throw an exception
      spec = sys.getSpecification();  
      


        if(kind==Variable.INPUT){
            if(fRB.getInputs().length <= id_var || id_var < 0){
               throw new Exception("Wrong Variable ID:"+id_var);
            }
            var = fRB.getInputs()[id_var];

        }
        else{
            if(fRB.getOutputs().length <= id_var || id_var < 0){
               throw new Exception("Wrong Variable ID:"+id_var);
            }
            var = fRB.getOutputs()[id_var];   
        }

      return var;
    }

    /**
    * Return the given Variable(The xFuzzy one) for a given id_var,
    * Passing it's FuzzySystem, and it's id(the position of this var in the vars of the given kind array)
    * if any error occours, like the id_var is out of the array bound, then raise an Exception
    * that should be treated in along
    *
    */
    public static xfuzzy.lang.Variable getFuzzy(FuzzySystem sys,Integer id_rb, Integer id_var, Integer kind)
    throws Exception {
      xfuzzy.lang.Variable var=null;
      Specification spec=null;
      //can throw an exception
      spec = sys.getSpecification();  
      

      if(id_rb != null){

            xfuzzy.lang.Rulebase fRB = spec.getRulebases()[id_rb];
            var = Variable.getFuzzy(sys,fRB,id_var,kind);
      }
      else{
            if(kind==Variable.INPUT){
                if(spec.getSystemModule().getInputs().length <= id_var || id_var < 0){
                   throw new Exception("Wrong Variable ID:"+id_var);
                }
                var = spec.getSystemModule().getInputs()[id_var];
            }
            else{
                
                if(spec.getSystemModule().getOutputs().length <= id_var || id_var < 0){
                   throw new Exception("Wrong Variable ID:"+id_var);
                }
                var = spec.getSystemModule().getOutputs()[id_var];   
            }
      }

      return var;
    }

    /**
    * Return the given Variable(The xFuzzy one) for a given id_var, this function returns the variable for a system
    * so there is no need to pass a rulebase.
    * Passing it's FuzzySystem, and it's id(the position of this var in the vars of the given kind array)
    * if any error occours, like the id_var is out of the array bound, then raise an Exception
    * that should be treated in along
    *
    */
    public static xfuzzy.lang.Variable getFuzzy(FuzzySystem sys, Integer id_var, Integer kind)
    throws Exception {
      xfuzzy.lang.Variable var=null;
      Specification spec=null;
      //can throw an exception
      spec = sys.getSpecification();  
      

        if(kind==Variable.INPUT){
            if(spec.getSystemModule().getInputs().length <= id_var || id_var < 0){
               throw new Exception("Wrong Variable ID:"+id_var);
            }
            var = spec.getSystemModule().getInputs()[id_var];
        }
        else{
            
            if(spec.getSystemModule().getOutputs().length <= id_var || id_var < 0){
               throw new Exception("Wrong Variable ID:"+id_var);
            }
            var = spec.getSystemModule().getOutputs()[id_var];   
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
