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
      //get the mfKey from request GET
      Map<String,String[]> queryParameters = request().queryString();

      String mfKey = queryParameters.get("mf_key")[0];

      //Get the number of parameters for this MFType.
      Integer numParams = MF.getNumParamsForMFType(Integer.valueOf(mfKey));
      return ok(
                  Json.toJson(numParams)
                  );
    } 

}