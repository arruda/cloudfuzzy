package controllers;

import java.io.File;
import models.FuzzySystem;
import models.User;
// import models.MF;
// import models.Type;

import play.*;
import play.mvc.*;
import play.data.*;
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
         FuzzySystem.create(filledForm.get());

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
  
  
}