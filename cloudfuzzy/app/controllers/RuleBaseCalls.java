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


}