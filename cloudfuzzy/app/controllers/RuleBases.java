package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import models.FuzzySystem;
import models.User;
import models.RuleBase;

import play.*;
import play.mvc.*;
import play.data.*;
import play.libs.Json;
// import views.html.fuzzy.system.rulebases.*;

import xfuzzy.lang.XflParser;
import xfuzzy.lang.Specification;
// import xfuzzy.util.XConstants;

@Security.Authenticated(Secured.class)
public class RuleBases extends Controller {
  
  static Form<RuleBase> newRBForm = form(RuleBase.class);
  // static Form<Type> editTypeForm = form(Type.class);
  

  //=================== CRUD ===================//
  // /**
  // * prepare to creates a new Fuzzy RuleBase.
  // */
  // public static Result prepareCreate(Long systemId) {
  //     FuzzySystem sys = FuzzySystem.find.byId(systemId);
      
  //     OperatorSet opSetWithOperators = new OperatorSet();
  //     opSetWithOperators.setDefaultOperatorsList();

  //     return ok(
  //              prepareCreate.render(sys,newOPSetForm.fill(opSetWithOperators))
  //             );
  // }



}