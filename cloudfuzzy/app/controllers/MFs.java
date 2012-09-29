package controllers;

import java.io.File;
import java.util.Map;

import models.FuzzySystem;
import models.User;

import models.MF;
import models.Type;

import play.*;
import play.mvc.*;
import play.data.*;
import play.libs.Json;

import views.html.fuzzy.system.types.mfs.*;
// import views.html.*;

import xfuzzy.*;
import xfuzzy.lang.AggregateMemFunc;
import xfuzzy.lang.MemFunc;
import xfuzzy.lang.ParamMemFunc;
import xfuzzy.lang.Specification;
import xfuzzy.lang.Universe;
import xfuzzy.lang.XflParser;

@Security.Authenticated(Secured.class)
public class MFs extends Controller {
  
  static Form<MF> newMFForm = form(MF.class);
  // static Form<Type> editTypeForm = form(Type.class);
  // static Form<MF> editMFForm = form(MF.class);
  


  //=================== CRUD ===================//

  /**
  * prepare to creates a new Fuzzy Type.
  */
  public static Result prepareCreate(Long id_sys, Integer id_tp) {
      FuzzySystem sys = FuzzySystem.find.byId(id_sys);

      Type tp = null;
      try{
            tp = Type.get(sys,id_tp);
      }
      catch(Exception e){
        return badRequest();
      }

      

      //should put some information first? like the mf.id?
      //see if this will be a problem ahead.
      return ok(
               prepareCreate.render(sys,tp,newMFForm)
              );
  }


  /**
  * Creates a new Fuzzy type with some parameters.
  */
  public static Result create(Long id_sys, Integer id_tp) {
      Form<MF> filledForm = newMFForm.bindFromRequest();

      FuzzySystem sys = FuzzySystem.find.byId(id_sys);

      Specification spec=null;
      xfuzzy.lang.Type fuzzyTp = null;
      Type tp = null;
      try{
          spec = sys.getSpecification();
          fuzzyTp = Type.getFuzzy(sys,id_tp);      
          tp = Type.get(sys,id_tp);             
      }
      catch(Exception e){
          e.printStackTrace();
      }
      
      ParamMemFunc originalMfs[] = fuzzyTp.getMembershipFunctions();

      //check if in that type a mf with that label already exist
      for (int i=0; i < originalMfs.length; i++ ) {
        if(originalMfs[i].label.equals(filledForm.field("label").valueOr(""))){
          filledForm.reject("label", "Already exist a MF with this label");  

        }        
      }

      //--check if the params are correct
      //get the num of params this MF Type should have
      int numParams = MF.getNumParamsForMFType(Integer.valueOf(filledForm.field("idFunction").valueOr("")));
      for (int i =0; i< numParams; i++) {

        try{
          Double newParam = Double.valueOf(filledForm.field("params["+i+"]").valueOr(""));
        }
        catch(Exception e){
          //Integer letter = i;
          Character letter = (char)(97+i);
          filledForm.reject("params", "Invalid Parameter("+letter+"): Real Numbers required");
        }
        
      }
      
      if(!FuzzySystem.isIdentifier(filledForm.field("label").valueOr(""))) {
        filledForm.reject("label", "Invalid linguistic label");  
      }


      if(filledForm.hasErrors()) {
        return badRequest(
          prepareCreate.render(sys,tp,filledForm)
        );
      } else {

         MF newMF = filledForm.get();
         //tries to save the given MF, if catch an exception get the error and put in the form, as a param error
         try{
            MF.create(newMF,fuzzyTp,spec);
         }
         catch(Exception e){
            filledForm.reject("params", "Invalid parameters");  

            return badRequest(
              prepareCreate.render(sys,tp,filledForm)
            );
         }

         return redirect(routes.Types.detail(sys.id,tp.id)); 
      }
  }


  /**
  * Detail a given MF, the id_mf is nothing related to DB model.
  * Its the position of the MF in the spec.types.getMembershipFunctions array.
  */
  public static Result detail(Long id_sys, Integer id_tp, Integer id_mf) {
      FuzzySystem sys = FuzzySystem.find.byId(id_sys);

      MF mf = null;
      Type tp = null;
      try{
            tp = Type.get(sys,id_tp);
            mf = MF.get(sys,id_tp, id_mf);
      }
      catch(Exception e){
        return badRequest();
      }
      
    return ok(
            detail.render(sys,tp,mf)
            ); 
  }

  /**
  * Delete a given MF, the id_mf is nothing related to DB model.
  * Its the position of the MF in the spec.types.getMembershipFunctions array.
  */
  public static Result delete(Long id_sys, Integer id_tp, Integer id_mf) {
      FuzzySystem sys = FuzzySystem.find.byId(id_sys);
      xfuzzy.lang.Specification spec = sys.getSpecification();

      ParamMemFunc mf = null;
      xfuzzy.lang.Type tp = null;
      try{
            tp = Type.getFuzzy(sys,id_tp);
            mf = MF.getFuzzy(sys,id_tp, id_mf);
      }
      catch(Exception e){
        return badRequest();
      }

      //cant remove if been used in the system.
      if(mf.isLinked()){          
        String msg = "Cannot remove membership function\""+mf+"\".";
        msg+="\nThere are rulebases using this function..";
        System.out.println(msg); 
        return badRequest();  
      }

      tp.remove(mf);
      //save the spec.
      spec.setModified(true);
      spec.save();        

    return redirect(routes.Types.detail(id_sys,id_tp)); 
  }



   //=================== AJAX ===================//
    /**
    * Returns a json with the number of parameters of the given mf type
    * it looks in the  request.GET for a 'mf_key' value.
    */
    public static Result ajaxGetNumParams()
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