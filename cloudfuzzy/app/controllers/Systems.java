package controllers;

import java.io.File;
import models.FuzzySystem;
import models.User;
import models.Variable;
import models.Type;
// import models.MF;
// import models.Type;

import play.*;
import play.mvc.*;
import play.data.*;
import play.libs.Json;

import views.html.fuzzy.system.*;

import xfuzzy.*;
import xfuzzy.lang.AggregateMemFunc;
import xfuzzy.lang.MemFunc;
import xfuzzy.lang.ParamMemFunc;
import xfuzzy.lang.Specification;
import xfuzzy.lang.Universe;
import xfuzzy.lang.XflParser;
// import xfuzzy.util.XConstants;

@Security.Authenticated(Secured.class)
public class Systems extends Controller {
  
  static Form<FuzzySystem> fuzzySystemForm = form(FuzzySystem.class);
  // static Form<Type> newTypeForm = form(Type.class);
  // static Form<Type> editTypeForm = form(Type.class);
  // static Form<MF> editMFForm = form(MF.class);
  


  //=================== CRUD ===================//
  /**
  * List all the fuzzy Systems of a user.
  */  
  public static Result list() {
      return ok(
               list.render(FuzzySystem.listFuzzySystemsByUser(request().username()))
              );
  }

  /**
  * Prepares to create a new Fuzzy System form.
  */  
  public static Result prepareCreate() {
      return ok(
               prepareCreate.render(fuzzySystemForm)
              );
  }
  
  /**
  * Creates the given fuzzy system
  */
  public static Result create() {
      Form<FuzzySystem> filledForm = fuzzySystemForm.bindFromRequest();
      if(filledForm.hasErrors()) {
        return badRequest(
                prepareCreate.render(filledForm)
        );
      } else {
         FuzzySystem newSys = filledForm.get();
         newSys.fileName = newSys.name+".xfl";
         newSys.user = User.findByEmail(request().username());
         FuzzySystem.create(newSys);

            return redirect(routes.Systems.list()); 
      }
  }

  /**
  * Detail a system, and present the other options that the user may want to use, like adding new type and stuf.
  */
  public static Result detail(Long id) {
      if(Secured.isOwnerOf(id)){
        return ok(
                detail.render(FuzzySystem.find.byId(id))
                ); 

      }else{        
            return forbidden();
      }
  }


  /**
  * Deletes a fuzzy system
  */
  public static Result delete(Long id) {
      if(Secured.isOwnerOf(id)){
        FuzzySystem.delete(id);

        return redirect(
                routes.Systems.list()
                ); 

      }else{        
            return forbidden();
      }

  }

  /**
  * Deletes a given FussySystem variable, 
  *passing the system id, the variable id, and the kind(Variable.INPUT, OUTPUT, or other)
  */
  public static Result deleteVariable(Long id_sys, Integer id_variable, Integer kind) {
    FuzzySystem sys = FuzzySystem.find.byId(id_sys);

    xfuzzy.lang.Specification spec = sys.getSpecification();

    xfuzzy.lang.Variable var = null;
    try{
      if(kind == Variable.INPUT){
        var = spec.getSystemModule().getInputs()[id_variable];
      }
      else{
        var = spec.getSystemModule().getOutputs()[id_variable];
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

    System.out.println("xfl:");
    System.out.println(var.toXfl());
    spec.getSystemModule().removeVariable(var);

    spec.setModified(true);
    spec.save();      


    return redirect(routes.Systems.detail(id_sys)); 
  }
  
  //=================== OTHERS ===================//
  /**
  * Prints the xfl file of the given fuzzy system.
  * for now will only print the text in a text area,
  * but the idea is to make a file download with the text content.
  */
  public static Result print(Long id){
      if(Secured.isOwnerOf(id)){

        return ok(
                print.render(FuzzySystem.find.byId(id))
                ); 


      }else{        
            return forbidden();
      }

  }
  
   //=================== AJAX ===================//

    /**
    * Adds a new variable for a given rule id and fuzzy system id.
    * looks for the keys:
    * 'name' -> the name of the new variable
    * 'kind' -> if is input, output or inner.
    * 'idType' -> the type's id for this new variable
    * 
    * If new var is ok, then add it to the system
    * if not then return the list of errors.
    */
    public static Result ajaxAddVariable(Long id_sys)
    {
        Form<Variable> filledForm = form(Variable.class).bindFromRequest();

        FuzzySystem sys = FuzzySystem.find.byId(id_sys);

        Specification spec = sys.getSpecification();

        //checks if the rulebase exists with this name
        if(spec.getSystemModule().searchVariable(filledForm.field("name").valueOr("")) != null){
            filledForm.reject("name", "Already exist a Variable with this name in this System");  
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

          try{

            Variable.create(
                  newVar,
                  Type.getFuzzy(sys,Integer.valueOf(newVar.idType)),
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

  
}