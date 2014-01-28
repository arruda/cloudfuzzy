 
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
        
           
     
package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import models.FuzzySystem;
import models.User;
import models.RuleBase;
import models.Variable;
import models.Type;

import play.*;
import play.mvc.*;
import play.data.*;
import play.libs.Json;
import views.html.fuzzy.system.rulebases.*;

import xfuzzy.lang.XflParser;
import xfuzzy.lang.Specification;

@Security.Authenticated(Secured.class)
public class RuleBases extends Controller {
  
  static Form<RuleBase> newRBForm = form(RuleBase.class);
  

  //=================== CRUD ===================//

  /**
  * prepare to creates a new Fuzzy RuleBase.
  */
  public static Result prepareCreate(Long systemId) {
      FuzzySystem sys = FuzzySystem.find.byId(systemId);
      
      return ok(
               prepareCreate.render(sys,newRBForm)
              );
  }


  /**
  * Creates a new Fuzzy Fuzzy RuleBase.
  */
  public static Result create(Long systemId) {

      Form<RuleBase> filledForm = newRBForm.bindFromRequest();

      FuzzySystem sys = FuzzySystem.find.byId(systemId);
      Specification spec=null;
      try{
          spec = sys.loadSpecification();             
      }
      catch(Exception e){
          e.printStackTrace();
      }
      
      //checks if the rulebase exists with this name
      if(spec.searchRulebase(filledForm.field("name").valueOr("")) != null){
          filledForm.reject("name", "Already exist a Rule Base with this name");  
      }
      if(!FuzzySystem.isIdentifier(filledForm.field("name").valueOr(""))) {
        filledForm.reject("name", "Invalid name");  
      }
      
      if(filledForm.hasErrors()) {
        System.out.println("errors:"+ filledForm.errors());
        return badRequest(
          prepareCreate.render(sys,filledForm)
        );
      } else {
        System.out.println("created");
        RuleBase rb = filledForm.get();

        //tries to save the given MF, if catch an exception get the error and put in the form, as a param error
         try{
            RuleBase.create(rb,spec);
         }
         catch(Exception e){
            filledForm.reject("Invalid");  

            return badRequest(
              prepareCreate.render(sys,filledForm)
            );
         }
        return redirect(routes.Systems.detail(sys.id)); 
      }
  }
  

  public static Result detail(Long id_sys, Integer id_rb){
      FuzzySystem sys = FuzzySystem.find.byId(id_sys);

      RuleBase rb = null;
      try{
        rb = RuleBase.get(sys,id_rb);
      }
      catch(Exception e){
        e.printStackTrace();
        return badRequest();
      }


      return ok(
              detail.render(sys,rb)
              ); 


  }

  /**
   * 
   * Deletes a Rulebesa
   * 
   * @param id_sys Identifier of system
   * @param id_rb Identifier of Rulebase
   * @return Refresh the detail system views
   * 
   * @author bruno.oliveira
   * 
   */
  public static Result delete(Long id_sys, Integer id_rb){
	  FuzzySystem sys = FuzzySystem.find.byId(id_sys);
	  xfuzzy.lang.Specification spec = sys.getSpecification();
	  xfuzzy.lang.Rulebase rb = null;
	  try{
		  rb = RuleBase.getFuzzy(sys, id_rb);
	  }
	  catch(Exception e){
		  return badRequest();
	  }
	  if(rb.isLinked()){
		  String msg = "Cannot remove rulebase \"" + rb.getName() + "\".";
		  System.out.println(msg);
		  return badRequest();
	  }
	  spec.removeRulebase(rb);
	  spec.setModified(true);
	  spec.save();
	  
	  return redirect(routes.Systems.detail(id_sys));
  }

  /**
  * Deletes a given rulebase variable, 
  *passing the system id, the rulebase id, the variable id, and the kind(Variable.INPUT, OUTPUT, or other)
  */
  public static Result deleteVariable(Long id_sys, Integer id_rb, Integer id_variable, Integer kind) {
    FuzzySystem sys = FuzzySystem.find.byId(id_sys);

    xfuzzy.lang.Specification spec = sys.getSpecification();

    xfuzzy.lang.Rulebase rb = null;
    try{
      rb = RuleBase.getFuzzy(sys,id_rb);
    }
    catch(Exception e){
          return badRequest();        
    }

    xfuzzy.lang.Variable var = null;
    try{
      if(kind == Variable.INPUT){
        var = rb.getInputs()[id_variable];
      }
      else{
        var = rb.getOutputs()[id_variable];
      }

    }
    catch(Exception e){
        System.out.println(e.getMessage());

          return badRequest();        
    }


    if(var.isLinked()) {
        String msg = "Cannot remove variable \""+var.getName()+"\".";
        msg+="\nThere are rules using this variable.";
        System.out.println(msg); 
        return badRequest();  
    }
  
    if(kind == Variable.INPUT){
      rb.removeInputVar(var);
    } 
    else{
      rb.removeOutputVar(var);
    } 

    spec.setModified(true);
    spec.save();      


    return redirect(routes.RuleBases.detail(id_sys,id_rb)); 
  }

  /**
  * Deletes a given rulebase rule, 
  *passing the system id, the rulebase id, the rule id
  */
  public static Result deleteRule(Long id_sys, Integer id_rb, Integer id_rule) {
    FuzzySystem sys = FuzzySystem.find.byId(id_sys);

    xfuzzy.lang.Specification spec = sys.getSpecification();

    xfuzzy.lang.Rulebase rb = null;
    try{
      rb = RuleBase.getFuzzy(sys,id_rb);
    }
    catch(Exception e){
          return badRequest();        
    }

    xfuzzy.lang.Rule rule = null;
    try{
        rule = rb.getRules()[id_rule];
    }
    catch(Exception e){
        System.out.println(e.getMessage());

          return badRequest();        
    }


    // if(rule.isLinked()) {
    //     String msg = "Cannot remove variable \""+var.getName()+"\".";
    //     msg+="\nThere are rules using this variable.";
    //     System.out.println(msg); 
    //     return badRequest();  
    // }
  
    rb.remove(rule);

    spec.setModified(true);
    spec.save();      


    return redirect(routes.RuleBases.detail(id_sys,id_rb)); 
  }

   //=================== AJAX ===================//

    /**
    * Adds a new variable for a given rule id and fuzzy system id.
    * looks for the keys:
    * 'name' -> the name of the new variable
    * 'kind' -> if is input, output or inner.
    * 'id_tp' -> the type's id for this new variable
    * 
    * If new var is ok, then add it to the rulebase
    * if not then return the list of errors.
    */
    public static Result ajaxAddVariable(Long id_sys, Integer id_rb)
    {
        Form<Variable> filledForm = form(Variable.class).bindFromRequest();

        FuzzySystem sys = FuzzySystem.find.byId(id_sys);

        xfuzzy.lang.Rulebase fRB = null;
        try{
          fRB = RuleBase.getFuzzy(sys,id_rb);
        }
        catch(Exception e){
          e.printStackTrace();
          return badRequest();
        }


        //checks if the rulebase exists with this name
        if(fRB.searchVariable(filledForm.field("name").valueOr("")) != null){
            filledForm.reject("name", "Already exist a Variable with this name in this Rule Base");  
        }
        if(!FuzzySystem.isIdentifier(filledForm.field("name").valueOr(""))) {
          filledForm.reject("name", "Invalid name");  
        }

        if(filledForm.hasErrors()) {
          System.out.println("errors:"+ filledForm.errors());
          return badRequest(
            filledForm.errorsAsJson()
          );
        }else{
          Variable newVar = filledForm.get();
          newVar.idRuleBase = String.valueOf(id_rb);

          try{

            Variable.create(
                  newVar,
                  Type.getFuzzy(sys,Integer.valueOf(newVar.idType)),
                  fRB,
                  sys.getSpecification()
            );

          }
          catch(Exception e){
            filledForm.reject("Invalid");  

            return badRequest(
              filledForm.errorsAsJson()
            );

          }

          

          System.out.println("newVar"+ newVar.name);

          return ok(
                      Json.toJson("ok")
                      );

        }

    }

    /**
    * Adds a new rule for a given rule base id and fuzzy system id.
    * looks for the keys:
    * 'degree' -> the degree of the new rule
    * 'input[x]' -> whats the mf id selected for this input (x is the id of the inputs of the rulebase)
    * 'output[x]' -> whats the mf id selected for this output (x is the id of the outputs of the rulebase)
    * 
    * If new rule is ok, then add it to the rulebase and return 'ok'
    * if not then return the list of errors.
    */
    public static Result ajaxAddRuleFromTable(Long id_sys, Integer id_rb)
    {
      Form<forms.RuleTableForm> filledForm = form(forms.RuleTableForm.class).bindFromRequest();


      FuzzySystem sys = FuzzySystem.find.byId(id_sys);

      xfuzzy.lang.Rulebase fRB = null;
      try{
        fRB = RuleBase.getFuzzy(sys,id_rb);
      }
      catch(Exception e){
        e.printStackTrace();
        return badRequest();
      }

      if(filledForm.hasErrors()) {
        System.out.println("errors:"+ filledForm.errors());
        return badRequest(
          filledForm.errorsAsJson()
        );
      }else{
        xfuzzy.lang.Rule newRule = filledForm.get().getFuzzyRule(fRB);

        System.out.println("newrule:"+ newRule);
        fRB.addRule(newRule);
        Specification spec = sys.getSpecification();

        //save the spec.
        spec.setModified(true);
        spec.save();        
        return ok(
                    Json.toJson("ok")
                    );
      }
    } 

}
