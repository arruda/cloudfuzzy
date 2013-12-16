package controllers;

import play.*;
import play.mvc.*;
import play.mvc.Http.*;
import models.*;

public class Secured extends Security.Authenticator {
    
    @Override
    public String getUsername(Context ctx) {
        return ctx.session().get("email");
    }
    
    @Override
    public Result onUnauthorized(Context ctx) {
        return redirect(routes.Authentication.login());
    }
    
    // Access rights
    
    public static boolean isMemberOf(Long project) {
//        return Project.isMember(
//            project,
//            Context.current().request().username()
//        );
    	return true;
    }
    
    public static boolean isOwnerOf(Long system, String username) {
       return FuzzySystem.isOwner(
           system,
           username
       );
    	// return true;
    }  
  /*
    public static boolean isOwnerOf(Long system, Context ctx) {
    	return FuzzySystem.isOwner(
            system,
            ctx.current().request().username()
        );
     	// return true;
     }   
    */
}