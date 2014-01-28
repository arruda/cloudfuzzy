package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

import models.OperatorSet;

public class Application extends Controller {
  
 

    public static Result index() {
        return ok(
                index.render()
        );
    }

    public static Result about() {
        return ok(
                about.render()
        );
    }
    
}