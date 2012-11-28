package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import models.FuzzySystem;
import models.User;
import models.RuleBase;
import models.Variable;
import models.Type;

import play.*;
import play.mvc.*;
import play.data.*;
import play.libs.Json;
// import views.html.fuzzy.system.rulebasecalls.*;

import xfuzzy.lang.XflParser;
import xfuzzy.lang.Specification;
import xfuzzy.lang.RulebaseCall;
import xfuzzy.lang.SystemModule;


@Security.Authenticated(Secured.class)
public class RuleBaseCalls extends Controller {
  
  // static Form<RuleBase> newRBForm = form(RuleBase.class);
  

  //=================== CRUD ===================//


   //=================== AJAX ===================//

    /**
    * tries to create a rulebasecall with a given id_rb(rulebase id).
    */
    public static Result ajaxAddCall(Long id_sys, Integer id_rb)
    {

        FuzzySystem sys = FuzzySystem.find.byId(id_sys);


        SystemModule systemModule = sys.getSpecification().getSystemModule();
        xfuzzy.lang.Rulebase fRB = null;
        try{
          fRB = RuleBase.getFuzzy(sys,id_rb);
        }
        catch(Exception e){
          e.printStackTrace();
          return badRequest();
        }


        try{


          xfuzzy.lang.Variable nullvar = systemModule.searchVariable("NULL");
          xfuzzy.lang.Variable sysinputvar[] = new xfuzzy.lang.Variable[fRB.getInputs().length];
          xfuzzy.lang.Variable sysoutputvar[] = new xfuzzy.lang.Variable[fRB.getOutputs().length];
          for(int i=0; i<sysinputvar.length; i++){
            sysinputvar[i] = nullvar;
          } 
          for(int i=0; i<sysoutputvar.length; i++){
             sysoutputvar[i] = nullvar;
          }
          systemModule.addCall(fRB,sysinputvar,sysoutputvar);

          sys.getSpecification().setModified(true);
          sys.getSpecification().save();
        }
        catch(Exception e){

          return badRequest(

                    Json.toJson("error:'"+e.getMessage()+"'")
          );

        }

        

        return ok(
                    Json.toJson("ok")
                    );


    }


  public static Result ajaxAddLink(Long id_sys) {
    
    Form<models.LinkCallForm> filledForm = form(models.LinkCallForm.class).bindFromRequest();
    
    if(filledForm.hasErrors()) {
      System.out.println("errors:"+ filledForm.errors());
      return badRequest(
        filledForm.errorsAsJson()
      );
    }else{

      models.LinkCallForm linkCall = filledForm.get();
      System.out.println("variableDot:"+ linkCall.variableDots);
      
      for(models.LinkCallForm.VariableDot vd : linkCall.variableDots){


        System.out.println("vd:");
        System.out.println("vd.idRuleBaseCall:"+vd.idRuleBaseCall);
        System.out.println("vd.idSysVar:"+vd.idSysVar);
        System.out.println("vd.kindSysVar:"+vd.kindSysVar);
        System.out.println("vd.idBaseVar:"+vd.idBaseVar);
        System.out.println("vd.kindBaseVar:"+vd.kindBaseVar);

      }

      return ok(
                  Json.toJson("ok")
                  );
    }

  //   VariableDot a, VariableDot b=null;
  // if(a == null || b == null || a == b) return;

  // VariableDot origdot = null;
  // VariableDot destdot = null;
  // if( (a.basevar == null && a.sysvar.isOutput()) ||
  //     (a.basevar != null && a.basevar.isInput()) ) destdot = a;
  // if( (b.basevar == null && b.sysvar.isOutput()) ||
  //     (b.basevar != null && b.basevar.isInput()) ) destdot = b;
  // if( (a.basevar == null && a.sysvar.isInput()) ||
  //     (a.basevar != null && a.basevar.isOutput()) ) origdot = a;
  // if( (b.basevar == null && b.sysvar.isInput()) ||
  //     (b.basevar != null && b.basevar.isOutput()) ) origdot = b;
  // if( origdot == null || destdot == null) return;

  // if(origdot.basevar == null && origdot.sysvar.isOutput()) return;

  // if(origdot.call != null && origdot.call == destdot.call) {
  //  XDialog.showMessage(this,"Cannot make loops");
  //  return;
  // }

  // if(origdot.call != null && origdot.call.isPrevious(destdot.call) ) {
  //  XDialog.showMessage(this,"Cannot make loops");
  //  return;
  // }

  // if(destdot.basevar != null) { /* DESTINO A UNA BASE DE REGLAS */
  //  if(destdot.basevar.isOutput()) return;
  //  if(origdot.sysvar.equals("NULL") || origdot.sysvar.isOutput()) {
  //   String newname = "i0";
  //   for(int i=0; system.searchVariable(newname)!=null; i++) newname="i"+i;
  //   Variable inner = new Variable(newname,Variable.INNER);
  //   system.addVariable(inner);
  //   origdot.call.call.setOutputVariable(origdot.basevar, inner);
  //   origdot.sysvar = inner;
  //  }
  //  destdot.call.call.setInputVariable(destdot.basevar, origdot.sysvar);
  //  destdot.sysvar = origdot.sysvar;
  //  reallocate();
  //  refresh();
  // }
  // else { /* DESTINO A UNA VARIABLE GLOBAL */
  //  if(!destdot.sysvar.isOutput()) return;
  //  if(origdot.basevar == null) return;
  //  origdot.call.call.setOutputVariable(origdot.basevar, destdot.sysvar);
  //  origdot.sysvar = destdot.sysvar;
  //  refresh();
  // }
  }

}