package controllers;

import java.io.File;
import models.FuzzySystem;
import models.User;
// import forms.MF;
import forms.Type;

import play.*;
import play.mvc.*;
import play.data.*;
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
  
  static Form<Type> newMFForm = form(Type.class);
  // static Form<Type> editTypeForm = form(Type.class);
  // static Form<MF> editMFForm = form(MF.class);
  

  /**
  * prepare to creates a new Fuzzy Type.
  */
  // public static Result prepareCreate(Long systemId) {
  //     return ok(
  //              prepareCreate.render(systemId,newTypeForm)
  //              // index.render()
  //             );
  // }

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