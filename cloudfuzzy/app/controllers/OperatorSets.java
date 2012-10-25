package controllers;

import java.io.File;
import models.FuzzySystem;
import models.User;
import models.OperatorSet;

import play.*;
import play.mvc.*;
import play.data.*;
import views.html.fuzzy.system.operatorsets.*;

import xfuzzy.lang.XflParser;
// import xfuzzy.util.XConstants;

@Security.Authenticated(Secured.class)
public class OperatorSets extends Controller {
  
  static Form<OperatorSet> newOPSetForm = form(OperatorSet.class);
  // static Form<Type> editTypeForm = form(Type.class);
  

  //=================== CRUD ===================//
  /**
  * prepare to creates a new Fuzzy OperatorSet.
  */
  public static Result prepareCreate(Long systemId) {
      OperatorSet opSetWithOperators = new OperatorSet();
      opSetWithOperators.name="test";
      opSetWithOperators.setDefaultOperatorsList();
      for(OperatorSet.Operator op : opSetWithOperators.operators){
        System.out.println("op:"+op.name);

      }

      return ok(
               prepareCreate.render(systemId,newOPSetForm.fill(opSetWithOperators))
              );
  }

}