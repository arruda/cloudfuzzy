package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.io.File;

import models.FuzzySystem;
import models.User;
import models.OperatorSet;
import models.Parameter;

import play.*;
import play.mvc.*;
import play.data.*;
import play.libs.Json;
import views.html.fuzzy.system.operatorsets.*;

import xfuzzy.lang.XflParser;
import xfuzzy.lang.Specification;

@Security.Authenticated(Secured.class)
public class OperatorSets extends Controller {
  
  static Form<OperatorSet> newOPSetForm = form(OperatorSet.class);
  // static Form<Type> editTypeForm = form(Type.class);
  

  //=================== CRUD ===================//
  /**
  * prepare to creates a new Fuzzy OperatorSet.
  */
  public static Result prepareCreate(Long systemId) {
      FuzzySystem sys = FuzzySystem.find.byId(systemId);
      
      OperatorSet opSetWithOperators = new OperatorSet();
      opSetWithOperators.setDefaultOperatorsList();

      return ok(
               prepareCreate.render(sys,newOPSetForm.fill(opSetWithOperators))
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
          System.out.println("erros:"+filledForm.errors());
        return badRequest(
          prepareCreate.render(sys,filledForm)
        );
      } else {
          OperatorSet newOPSet = filledForm.get();
          
          System.out.println("newOPSet:"+newOPSet);
          for(OperatorSet.Operator op : newOPSet.operators){
            System.out.println("op:"+op.name+ "->" + op.selectedOption );
            if(op.params != null){
              for(Parameter param : op.params){
                System.out.println(">>Param:"+param.name+ "->" + param.value);

              }

            }

          }

         try{
            OperatorSet.create(newOPSet,spec);
         }
         catch(Exception e){
            filledForm.reject("params", "Invalid parameters");  

            return badRequest(
                prepareCreate.render(sys,filledForm)
            );
         }
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

   //=================== AJAX ===================//

    /**
    * Returns a json with the number of parameters of the given operator id and the option id
    * id_opType is the position of the operator in the OperatorSet.operators array
    * id_option is the position of the option Operator.options array
    *
    * it looks in the  request.GET for a:
    * 'id_opType'
    * 'id_option'
    */
    public static Result ajaxGetNumParamsForOption()
    {

        //get the keys from request GET
        Map<String,String[]> queryParameters = request().queryString();
        Integer id_opType = Integer.valueOf( queryParameters.get("id_opType")[0] );
        Integer id_option = Integer.valueOf( queryParameters.get("id_option")[0] );


        //Get the number of parameters for this Operator option.
        List<Parameter> params = OperatorSet.Operator.getParamsForOption(id_opType, id_option);
        return ok(
                    Json.toJson(params)
                    );
    }



}