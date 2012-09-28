package controllers;

import java.io.File;
import java.util.Map;

import models.FuzzySystem;
import models.User;

import forms.MF;
import forms.Type;

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
// import xfuzzy.util.XConstants;

@Security.Authenticated(Secured.class)
public class MFs extends Controller {
  
  static Form<MF> newMFForm = form(MF.class);
  // static Form<Type> editTypeForm = form(Type.class);
  // static Form<MF> editMFForm = form(MF.class);
  

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

    public static Result get()
    {
        Map<String,String[]> queryParameters = request().queryString();
        return ok(String.format("Here's my server-side data using $.get(), and you sent me [%s]",
                                queryParameters.get("foo")[0]));
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