package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import models.FuzzySystem;
import models.RuleBaseCall;
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
    
    /**
    * tries to remove a rulebasecall with a given id_rb(rulebase id).
    */
    public static Result ajaxRemoveCall(Long id_sys, Integer id_rb)
    {

        FuzzySystem sys = FuzzySystem.find.byId(id_sys);


        SystemModule systemModule = sys.getSpecification().getSystemModule();
        models.RuleBaseCall rbc_pos = models.RuleBaseCall.findByFuzzySystemIdAndPosition(id_sys, id_rb);
        xfuzzy.lang.RulebaseCall rbc = null;
        try{
          rbc = sys.getSpecification().getSystemModule().getRulebaseCalls()[id_rb];
        }
        catch(Exception e){
          e.printStackTrace();
          return badRequest();
        }


        try{


          systemModule.removeCall(rbc);

          sys.getSpecification().setModified(true);
          sys.getSpecification().save();
          rbc_pos.delete();
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


    /**
     * Update the position of a rulebasecall with the given id_rb
    * tries to create a rulebasecall with a given id_rbc(rulebasecall positon).
    * 
    * Get the post infos:
    * xPos;
    * yPos;
    */
    public static Result ajaxMoveCall(Long id_sys, Integer id_rb)
    {

        Form<models.RuleBaseCall> filledForm = form(models.RuleBaseCall.class).bindFromRequest();
        
        models.RuleBaseCall rbc_pos = models.RuleBaseCall.findByFuzzySystemIdAndPosition(id_sys, id_rb);

        Double xPos = Double.valueOf(filledForm.field("xPos").valueOr("328.0"));
        Double yPos = Double.valueOf(filledForm.field("yPos").valueOr("160.0"));
        
        if(rbc_pos!= null){
            rbc_pos.xPos = xPos;
            rbc_pos.yPos = yPos;
            rbc_pos.save();
        }
        //has no rbc_pos to this id_rbc, then check if there is a fuzzy rulebasecall in that position
        //if it has then create a new pos.
        else{

            FuzzySystem sys = FuzzySystem.find.byId(id_sys);

            try{
                xfuzzy.lang.RulebaseCall rbc = sys.getSpecification().getSystemModule().getRulebaseCalls()[id_rb];
                rbc_pos = new RuleBaseCall();
                rbc_pos.position = id_rb;
                rbc_pos.system = sys;
                rbc_pos.xPos = xPos;
                rbc_pos.yPos = yPos;
                rbc_pos.save();
            }
            catch(IndexOutOfBoundsException e){
            	//really doesn't have this rulebasecall, so raise error
                return badRequest(

                          Json.toJson("error:'"+e.getMessage()+"'")
                );
            }
            
        }
        



        

        return ok(
                    Json.toJson("ok")
                    );


    }



  /**
  * Add a link for a give id_sys.
  * The parameters expected for this are:  
  *
  *      variableDots[0].idRuleBaseCall
  *      variableDots[0].idSysVar 
  *      variableDots[0].kindSysVar 
  *      variableDots[0].idBaseVar
  *      variableDots[0].kindBaseVar
  *
  *      variableDots[1].idRuleBaseCall
  *      variableDots[1].idSysVar 
  *      variableDots[1].kindSysVar 
  *      variableDots[1].idBaseVar
  *      variableDots[1].kindBaseVar
  * The test cases should serve as example of what's the possible cases that exist.
  */  
  public static Result ajaxAddLink(Long id_sys) {
    
    Form<models.LinkCallForm> filledForm = form(models.LinkCallForm.class).bindFromRequest();
    
    FuzzySystem sys = FuzzySystem.find.byId(id_sys);
    xfuzzy.lang.SystemModule sysModule = sys.getSpecification().getSystemModule();

    if(filledForm.hasErrors()) {
      System.out.println("errors:"+ filledForm.errors());
      return badRequest(
        filledForm.errorsAsJson()
      );
    }else{

      models.LinkCallForm linkCall = filledForm.get();
      // System.out.println("variableDot:"+ linkCall.variableDots);
      
      if(linkCall.variableDots == null || linkCall.variableDots.size() != 2){
          filledForm.reject("", "Invalid Link");  

          System.out.println("incorrect number of variable dots or null");
          return badRequest(
            filledForm.errorsAsJson()
          );

      }
      //if are equals should give an error too.

      models.LinkCallForm.VariableDot a = linkCall.variableDots.get(0);
      models.LinkCallForm.VariableDot b = linkCall.variableDots.get(1);
      a.system = sys;
      b.system = sys;

      models.LinkCallForm.VariableDot origdot = null;
      models.LinkCallForm.VariableDot destdot = null;
      if( (a.idBaseVar == null && a.getSysVar().isOutput()) ||
        (a.idBaseVar != null && a.getBaseVar().isInput()) ) destdot = a;
      if( (b.idBaseVar == null && b.getSysVar().isOutput()) ||
        (b.idBaseVar != null && b.getBaseVar().isInput()) ) destdot = b;
      if( (a.idBaseVar == null && a.getSysVar().isInput()) ||
        (a.idBaseVar != null && a.getBaseVar().isOutput()) ) origdot = a;
      if( (b.idBaseVar == null && b.getSysVar().isInput()) ||
        (b.idBaseVar != null && b.getBaseVar().isOutput()) ) origdot = b;
      if( origdot == null || destdot == null){
        filledForm.reject("", "Invalid Link"); 
          System.out.println("origdot or destdot are null:"+origdot +" - " + destdot);

        return badRequest(
          filledForm.errorsAsJson()
        );
      }


      if(origdot.getRuleBaseCall() != null && origdot.getRuleBaseCall() == destdot.getRuleBaseCall()) {
        filledForm.reject("", "Cannot make loops"); 

        return badRequest(
          filledForm.errorsAsJson()
        );
      }

      //how to do this?
      // if(origdot.call != null && origdot.call.isPrevious(destdot.call) ) {
      //   filledForm.reject("", "Cannot make loops"); 

      //   return badRequest(
      //     filledForm.errorsAsJson()
      //   );
      // }


      //enter here if is a rulebase
      if(destdot.getBaseVar() != null) { 
        if(destdot.getBaseVar().isOutput()){
          filledForm.reject("", "Invalid Link"); 

          return badRequest(
            filledForm.errorsAsJson()
          );
        }

        if(origdot.getSysVar().equals("NULL") || origdot.getSysVar().isOutput()) {
          String newname = "i0";
          for(int i=0; sysModule.searchVariable(newname)!=null; i++){
            newname="i"+i;
          }

          xfuzzy.lang.Variable inner = new xfuzzy.lang.Variable(newname,xfuzzy.lang.Variable.INNER);
          sysModule.addVariable(inner);
          origdot.getRuleBaseCall().setOutputVariable(origdot.getBaseVar(), inner);
          origdot.setSysVar(inner);
        }

        destdot.getRuleBaseCall().setInputVariable(destdot.getBaseVar(), origdot.getSysVar());
        destdot.setSysVar(origdot.getSysVar());
      }

      //enter here if it's a global variable
      else {

        if(!destdot.getSysVar().isOutput()){        
            filledForm.reject("", "Invalid Link"); 

            return badRequest(
              filledForm.errorsAsJson()
            );
        }
        if(origdot.getBaseVar() == null){       
            filledForm.reject("", "Invalid Link"); 

            return badRequest(
              filledForm.errorsAsJson()
            );
        };

        origdot.getRuleBaseCall().setOutputVariable(origdot.getBaseVar(), destdot.getSysVar());
        origdot.setSysVar(destdot.getSysVar());
      }
      
      Specification spec = sys.getSpecification();

      //save the spec.
      spec.setModified(true);
      spec.save();        

      // for(models.LinkCallForm.VariableDot vd : linkCall.variableDots){


      //   System.out.println("vd:");
      //   System.out.println("vd.idRuleBaseCall:"+vd.idRuleBaseCall);
      //   System.out.println("vd.idSysVar:"+vd.idSysVar);
      //   System.out.println("vd.kindSysVar:"+vd.kindSysVar);
      //   System.out.println("vd.idBaseVar:"+vd.idBaseVar);
      //   System.out.println("vd.kindBaseVar:"+vd.kindBaseVar);

      // }

      return ok(
                  Json.toJson("ok")
                );
    }
  }

  /**
  * Removes a link for a give id_sys.
  * The parameters expected for this are:  
  *      variableDots[0].idRuleBaseCall
  *      variableDots[0].idSysVar 
  *      variableDots[0].kindSysVar 
  *      variableDots[0].idBaseVar
  *      variableDots[0].kindBaseVar
  *
  *      variableDots[1].idRuleBaseCall
  *      variableDots[1].idSysVar 
  *      variableDots[1].kindSysVar 
  *      variableDots[1].idBaseVar
  *      variableDots[1].kindBaseVar
  */  
  public static Result ajaxRemoveLink(Long id_sys) {

    Form<models.LinkCallForm> filledForm = 
                      form(models.LinkCallForm.class).bindFromRequest();
    
    FuzzySystem sys = FuzzySystem.find.byId(id_sys);
    xfuzzy.lang.SystemModule sysModule = sys.getSpecification().getSystemModule();

    if(filledForm.hasErrors()) {
      System.out.println("errors:"+ filledForm.errors());
      return badRequest(
        filledForm.errorsAsJson()
      );
    }
    else{

      models.LinkCallForm linkCall = filledForm.get();

      if(linkCall.variableDots == null || linkCall.variableDots.size() != 2){
          filledForm.reject("", "Invalid Link");  

          System.out.println("incorrect number of variable dots:"+linkCall.variableDots.size());
          return badRequest(
            filledForm.errorsAsJson()
          );

      }
      //if are equals should give an error too.


      models.LinkCallForm.VariableDot a = linkCall.variableDots.get(0);
      models.LinkCallForm.VariableDot b = linkCall.variableDots.get(1);
      a.system = sys;
      b.system = sys;

      models.LinkCallForm.VariableDot origdot = null;
      models.LinkCallForm.VariableDot destdot = null;
      if( (a.idBaseVar == null && a.getSysVar().isOutput()) ||
        (a.idBaseVar != null && a.getBaseVar().isInput()) ) destdot = a;
      if( (b.idBaseVar == null && b.getSysVar().isOutput()) ||
        (b.idBaseVar != null && b.getBaseVar().isInput()) ) destdot = b;
      if( (a.idBaseVar == null && a.getSysVar().isInput()) ||
        (a.idBaseVar != null && a.getBaseVar().isOutput()) ) origdot = a;
      if( (b.idBaseVar == null && b.getSysVar().isInput()) ||
        (b.idBaseVar != null && b.getBaseVar().isOutput()) ) origdot = b;
      if( origdot == null || destdot == null){
        filledForm.reject("", "Invalid Link"); 
          System.out.println("origdot or destdot are null:"+origdot +" - " + destdot);

        return badRequest(
          filledForm.errorsAsJson()
        );
      }



      xfuzzy.lang.Variable nullvar = sysModule.searchVariable("NULL");


      //deleting a link that origins from a rulebase output
      if(origdot.getBaseVar() != null){        
          origdot.getRuleBaseCall().setOutputVariable(origdot.getBaseVar(), nullvar);
          if(destdot.getBaseVar() != null){
            destdot.getRuleBaseCall().setInputVariable(destdot.getBaseVar(), nullvar);
          }
      }

      //deleting a link that origins from a system input
      if(origdot.getBaseVar() == null){        
        if(destdot.getBaseVar() != null){
          destdot.getRuleBaseCall().setInputVariable(destdot.getBaseVar(), nullvar);
        }
      }

      Specification spec = sys.getSpecification();

      //save the spec.
      spec.setModified(true);
      spec.save();        


      return ok(
                  Json.toJson("ok")
                );

    } 
  }



}