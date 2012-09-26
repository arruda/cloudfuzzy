package controllers;

import java.io.File;
import models.FuzzySystem;
import models.User;
// import forms.MF;
// import forms.Type;

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

  //=================== SYSTEM ===================//
  
//   public static Result newSystem() {
//       Form<FuzzySystem> filledForm = fuzzySystemForm.bindFromRequest();
//       if(filledForm.hasErrors()) {
//         return badRequest(
//                 views.html.fuzzy.list.render(FuzzySystem.all(),filledForm)
//         );
//       } else {
//           System.out.println("good");
//          FuzzySystem newSys = filledForm.get();
//          newSys.fileName = newSys.name+".xfl";
//          newSys.user = User.findByEmail(request().username());
//          FuzzySystem.create(filledForm.get());

//             return redirect(routes.Fuzzy.list()); 
//       }
//   }
  
//   public static Result deleteSystem(Long id) {
//       FuzzySystem.delete(id);

//         return redirect(routes.Fuzzy.list()); 
//   }
  
//   public static Result detailSystem(Long id) {
//         return ok(
//                 views.html.fuzzy.systemDetail.render(FuzzySystem.find.byId(id),newTypeForm)
//                 ); 
//   }
  
  
//   public static Result runSystem(Long id){
//       FuzzySystem sys = FuzzySystem.find.ref(id);
//         Specification spec = null;
//         double [] res = null;
//           try {
//              double [] input = {0.0, 10.0};
//             res = executaMotorDeInferencia(input, sys.fileName);
//             System.out.println("res>>" + res[0]);
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
      
//       return ok(
//                 views.html.run.render(id, res[0])
//               );  
      
     
//   }

//   //=================== END SYSTEM ===================//
  

//   //=================== Type (Linguistic Variabel Type) ===================//

//   public static Result newType(Long systemId) {
//       Form<Type> filledForm = newTypeForm.bindFromRequest();

//       // Check the numMFs
//       if(!filledForm.field("type").valueOr("").isEmpty()) {
//           int type = Integer.valueOf(filledForm.field("type").valueOr(""));
//           //if needed the number of mfs
//           if(type != 8 || type != 9) {
//               if(filledForm.field("numMFs").valueOr("").isEmpty()){
//                   filledForm.reject("numMFs", "Need the number of MFs");                  
//               }
//               else{
//                   int numMFs = Integer.valueOf(filledForm.field("numMFs").valueOr("")); 
//                   if(numMFs<0){
//                       filledForm.reject("numMFs", "Need a positive number");    
                      
//                   }
//               }
              
//           }
//       }
//       FuzzySystem sys = FuzzySystem.find.byId(systemId);
//       Specification spec=null;
//       try{
//           spec = sys.loadSpecification();             
//       }
//       catch(Exception e){
//           e.printStackTrace();
//       }
      
//       //checks if the type exists with this name
//       if(spec.searchType(filledForm.field("name").valueOr("")) != null){
//           filledForm.reject("name", "Already exist a type with this name");  
//       }
      
//       if(filledForm.hasErrors()) {
//         return badRequest(
//                 views.html.fuzzy.systemDetail.render(FuzzySystem.find.byId(systemId),filledForm)
//         );
//       } else {
//           Type newType = filledForm.get();
//           Type.create(newType, spec);
//          return redirect(routes.Fuzzy.list()); 
//       }
//   }

//   public static Result detailType(Long id_sys, Integer id_tp) {
//       FuzzySystem sys = FuzzySystem.find.byId(id_sys);
//       xfuzzy.lang.Specification spec = sys.getSpecification();
//       xfuzzy.lang.Type [] tps = spec.getTypes();

      
//       if(tps.length <= id_tp || id_tp < 0){
//             return badRequest();
//       }
      
//       Type tp = Type.createFromFuzzyType(tps[id_tp], id_tp);
//       Form<Type> editTypeForm = form(Type.class).fill(tp);
      
//     return ok(
//             views.html.fuzzy.detailType.render(id_sys,tp,editTypeForm)
//             ); 
//   }

//   public static Result editType(Long id_sys, Integer id_tp) {
//       Form<Type> filledForm = editTypeForm.bindFromRequest();

//       FuzzySystem sys = FuzzySystem.find.byId(id_sys);
//       Specification spec=null;
//       try{
//           spec = sys.loadSpecification();             
//       }
//       catch(Exception e){
//           e.printStackTrace();
//       }
      

//       //the types for this modeling
//       xfuzzy.lang.Type [] tps = spec.getTypes();

//       //ensures that the required type id is within the bounds tps array
//       if(tps.length <= id_tp || id_tp < 0){
//             return badRequest();
//       }
      
//       //checks if the type exists with this name
//       //considering that need to ignore the original type been edited.
//       xfuzzy.lang.Type search_tp = spec.searchType(filledForm.field("name").valueOr(""));
//       if(search_tp != null && search_tp != tps[id_tp]){
//           filledForm.reject("name", "Already exist a type with this name");  
//       }

      
//       if(filledForm.hasErrors()) {
//         return badRequest(
//                 views.html.fuzzy.detailType.render(id_sys, null,filledForm)
//         );
//       } else {

//           Type editedType = filledForm.get();
//           Type.edit(editedType,tps[id_tp], spec);
//          return redirect(routes.Fuzzy.detailSystem(id_sys)); 
//       }
//   }

// //  public static 
  
// //=================== END Type (Linguistic Variabel Type) ===================//

//   //=================== MF (Membership Function) ===================//

//   public static Result editMF(Long id_sys, Integer id_tp, Integer id_mf) {
//       Form<MF> filledForm = editMFForm.bindFromRequest();

//       FuzzySystem sys = FuzzySystem.find.byId(id_sys);
//       Specification spec=null;
//       try{
//           spec = sys.loadSpecification();             
//       }
//       catch(Exception e){
//           e.printStackTrace();
//       }
       

//       //the types for this modeling
//       xfuzzy.lang.Type [] tps = spec.getTypes();

//       //ensures that the required type id is within the bounds tps array
//       if(tps.length <= id_tp || id_tp < 0){
//             return badRequest();
//       }
      
//       xfuzzy.lang.Type tp = spec.getTypes()[id_tp];

//       //ensures that the required mf  id is within the bounds mfs array
//       if(tp.getMembershipFunctions().length <= id_mf || id_mf < 0){
//             return badRequest();
//       }
      
//       //get the mf beein edited
//       xfuzzy.lang.ParamMemFunc mf = tp.getMembershipFunctions()[id_mf];
      
      
//       //

//       //checks if the type exists with this name
//        if(!XConstants.isIdentifier(filledForm.field("label").valueOr(""))) {
//         filledForm.reject("label", "Invalid linguistic label");  
//        }
//        ParamMemFunc[] mfs = tp.getMembershipFunctions();
//        for(int i=0; i<mfs.length; i++)
//         if(i != id_mf && mfs[i].label.equals(filledForm.field("label").valueOr(""))) {
//          String msg = "Label "+filledForm.field("label").valueOr("")+" already exists";
//          filledForm.reject("label", msg);  
//          break;
//         }

      
//       if(filledForm.hasErrors()) {
//         return badRequest(
//                 views.html.fuzzy.editMF.render(id_sys, id_tp, filledForm)
//         );
//       } else {

//           MF editedMF = filledForm.get();
//           MF.edit(editedMF,mf, spec);
//          return redirect(routes.Fuzzy.detailSystem(id_sys)); 
//       }
//   }
  
//   //=================== END MF (Membership Function) ===================//

  
  
}