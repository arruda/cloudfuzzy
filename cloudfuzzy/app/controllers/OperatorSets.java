package controllers;

import java.io.File;
import models.FuzzySystem;
import models.User;
import models.OperatorSet;

import play.*;
import play.mvc.*;
import play.data.*;
import views.html.fuzzy.system.operatorsets.*;

import xfuzzy.lang.XflParser;
import xfuzzy.lang.Specification;
// import xfuzzy.util.XConstants;

@Security.Authenticated(Secured.class)
public class OperatorSets extends Controller {
  
  static Form<OperatorSet> newOPSetForm = form(OperatorSet.class);
  // static Form<Type> editTypeForm = form(Type.class);
  

  //=================== CRUD ===================//
  /**
  * prepare to creates a new Fuzzy OperatorSet.
  */
  public static Result prepareCreate(Long systemId) {
      OperatorSet opSetWithOperators = new OperatorSet();
      opSetWithOperators.setDefaultOperatorsList();

      return ok(
               prepareCreate.render(systemId,newOPSetForm.fill(opSetWithOperators))
              );
  }

  /**
  * Creates a new Fuzzy OperatorSet with some parameters.
  */
  public static Result create(Long systemId) {
      Form<OperatorSet> filledForm = newOPSetForm.bindFromRequest();

      // Specification spec=null;
      // xfuzzy.lang.Type fuzzyTp = null;
      // Type tp = null;
      // try{
      //     spec = sys.getSpecification();
      //     fuzzyTp = Type.getFuzzy(sys,id_tp);      
      //     tp = Type.get(sys,id_tp);             
      // }
      // catch(Exception e){
      //     e.printStackTrace();
      // }

      FuzzySystem sys = FuzzySystem.find.byId(systemId);
      Specification spec=null;
      try{
          spec = sys.getSpecification();             
      }
      catch(Exception e){
          e.printStackTrace();
      }
      

      //checks if the Operatorset exists with this name
      if(spec.searchOperatorset(filledForm.field("name").valueOr("")) != null){
          filledForm.reject("name", "Already exist a Operatorset with this name");  
      }
      if(!FuzzySystem.isIdentifier(filledForm.field("name").valueOr(""))) {
        filledForm.reject("name", "Invalid name");  
      }

      
      if(filledForm.hasErrors()) {
        return badRequest(
          prepareCreate.render(systemId,filledForm)
        );
      } else {
          OperatorSet newOPSet = filledForm.get();
          
          //spec.addOperatorset(copy);
          System.out.println("newOPSet:"+newOPSet);
          for(OperatorSet.Operator op : newOPSet.operators){
            System.out.println("op:"+op.name+ "->" + op.selectedOption);

          }
         OperatorSet.create(newOPSet,spec);
         return ok(prepareCreate.render(systemId,filledForm));
         //redirect(routes.Systems.detail(systemId)); 
      }
  }
}