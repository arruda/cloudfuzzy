package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.io.File;

import models.FuzzySystem;
import models.OperatorSet.Operator;
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
  static Form<OperatorSet> editOPSetForm = form(OperatorSet.class);
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
          
          System.out.println("operators[0].name:"+filledForm.field("operators[0].name").valueOr(""));
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

  /**
   * 
   * Prepare the view for edition of the operator set populating
   * the form with data correctly.
   * 
   * @param id_sys - Id of operatorSet's system.
   * @param id_opset - Id of operatorSet.
   * @return If haven't errors load the edition view with a form populated.
   * 
   * @author bruno.oliveira
   * 
   */
  public static Result prepareEdit(Long id_sys, Integer id_opset){
	  
	  FuzzySystem sys = FuzzySystem.find.byId(id_sys);
	  
	  OperatorSet opset = null;
	  try {
		  opset = OperatorSet.get(sys, id_opset);
	  } catch (Exception e) {
		return badRequest();
	  }
	  editOPSetForm = form(OperatorSet.class).fill(
			  opset
	  );
	  
	  return ok(
			  edit.render(sys, opset, editOPSetForm)
			  );
	  
  }
  
  /**
   * 
   * Responsable for receive the new datas of operatorSet and save it.
   * 
   * @param id_sys - Id of operatorSet's system.
   * @param id_opset - Id of operatorSet.
   * @return If haven't errors will call the model for persist the datas in the bank.
   * 
   * @author bruno.oliveira
   * 
   */
  public static Result edit(Long id_sys, Integer id_opset){
	  Form<OperatorSet> filledForm = editOPSetForm.bindFromRequest();
	  FuzzySystem sys = FuzzySystem.find.byId(id_sys);
	  Specification spec = null;
	  
	  try{
		  spec = sys.getSpecification();
	  }
	  catch(Exception e){
		  e.printStackTrace();
	  }
	  
	  OperatorSet opset = null;
	  xfuzzy.lang.Operatorset fuzzyOperator = null;
	  
	  try{
		  opset = OperatorSet.get(sys, id_opset);
		  fuzzyOperator = OperatorSet.getFuzzy(sys, id_opset);
	  }
	  catch(Exception e){
		  return badRequest();
	  }
	  
	  // Checks if the OperatorSet exists with this name
	  //considering that need to ignore the original operator edited.
	  xfuzzy.lang.Operatorset search_opset = spec.searchOperatorset(filledForm.field("name").valueOr(""));
	 // if(spec.searchOperatorset(filledForm.field("name").valueOr("")) != null){
	  if(search_opset != null && search_opset != fuzzyOperator){
		  filledForm.reject("name", "Already exist a OperatorSet whit this name");
	  }
	  
	  if(!FuzzySystem.isIdentifier(filledForm.field("name").valueOr(""))){
		  filledForm.reject("name", "Invalid name");
	  }
	  
	  if(filledForm.hasErrors()){
		  System.out.println("errors: " + filledForm.errors());
		  return badRequest(
				  edit.render(sys, opset, filledForm)	
				  );
	  }else{
          OperatorSet editedOPSet = filledForm.get();
          try{
        	  OperatorSet.edit(editedOPSet, fuzzyOperator, spec);
          }catch(Exception e){
        	  filledForm.reject("params", "Invalid parameters");
        	  
        	  return badRequest( 
        			  edit.render(sys, opset, filledForm)
        	  );
          }
          return redirect( routes.OperatorSets.detail(id_sys,id_opset) ); 
	  }
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