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
          
          System.out.println("newOPSet:"+newOPSet);
          for(OperatorSet.Operator op : newOPSet.operators){
            System.out.println("op:"+op.name+ "->" + op.selectedOption);

          }
         OperatorSet.create(newOPSet,spec);
         return redirect(routes.Systems.detail(systemId)); 
      }
  }

  /**
  * Detail a given OperatorSet, the id_opset is nothing related to DB model.
  * Its the position of the type in the spec.getOperatorsets array.
  */
  public static Result detail(Long id_sys, Integer id_opset) {
      FuzzySystem sys = FuzzySystem.find.byId(id_sys);
      

      OperatorSet opset = null;
      try{
        opset = OperatorSet.get(sys,id_opset);
      }
      catch(Exception e){
        e.printStackTrace();
            return badRequest();        
      }
      
    return ok(
            detail.render(sys,opset)
            ); 
  }


  /**
  * Delete a given OperatorSet, the id_opset is nothing related to DB model.
  * Its the position of the OperatorSet in the spec.getOperatorsets array.
  */
  public static Result delete(Long id_sys, Integer id_opset) {
      FuzzySystem sys = FuzzySystem.find.byId(id_sys);
      xfuzzy.lang.Specification spec = sys.getSpecification();
      xfuzzy.lang.Operatorset opset = null;
      try{
            opset = OperatorSet.getFuzzy(sys,id_opset);
      }
      catch(Exception e){
        e.printStackTrace(); 
        return badRequest();
      }

      //cant remove if been used in the system.
      if(opset.isLinked()){          
        String msg = "Cannot remove operator set\""+opset+"\".";
        msg+="\nThere are rulebases using this operator set.";
        return badRequest();  
      }

      spec.removeOperatorset(opset);
      //save the spec.
      spec.setModified(true);
      spec.save();        

    return redirect(routes.Systems.detail(id_sys)); 
  }



}