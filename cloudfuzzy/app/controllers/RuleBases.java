package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import models.FuzzySystem;
import models.User;
import models.RuleBase;

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

}