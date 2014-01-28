 
	/*
	*
    * (c) 2013, 2014 INT - National Institute of Technology
	*
    * CloudFuzzy's team: 
	*	Dayse Mourao Arruda, Felipe Arruda Pontes, Bruno Barcellos Farias de Oliveira.
	*
    * The authors may be contacted by the email address: dayse.arruda@int.gov.br
	*
	* This file is part of CloudFuzzy - version Beta - 2014/January.
	*
    * We apply XFuzzy 3.0 as fuzzy engine to generate and evaluate alternative plans.
    * XFuzzy is a free design environment for fuzzy logic based systems distributed by IMSE-CNM.
	* If you need more information about the XFuzzy 3.0, please go to project's site:
	* http://www2.imse-cnm.csic.es/Xfuzzy/
    *                    
	* CloudFuzzy is free software: you can redistribute it and/or modify
	* it under the terms of the GNU General Public License as published by
	* the Free Software Foundation, either version 3 of the License, or
	* (at your option) any later version.
	*
	* CloudFuzzy is distributed in the hope that it will be useful,
	* but WITHOUT ANY WARRANTY; without even the implied warranty of
	* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
	* GNU General Public License for more details.
	*
	* You should have received a copy of the GNU General Public License
	* along with CloudFuzzy. If not, see <http://www.gnu.org/license/>.
    */
        
           
     
package controllers;

import play.*;
import play.mvc.*;
import play.mvc.Http.*;
import play.data.*;


import views.html.auth.*;
import views.html.*;

import models.User;

import play.libs.Json;
/**
*Deals with authentication stuff, like login, register.
*/
public class Authentication extends Controller {
  
    
    // -- Authentication
    
    public static class Login {
        
        public String email;
        public String password;
        
        public String validate() {
            if(User.authenticate(email, password) == null) {
                return "Invalid user or password";
            }
            return null;
        }
        
    }

    /**
     * Login page.
     */
    public static Result login() {
        // System.out.println("aaa");
        return ok(
            login.render(form(Login.class))
        );
    }
    
    /**
     * Handle login form submission.
     */
    public static Result authenticate() {
        System.out.println(session().get("email"));

        Form<Login> loginForm = form(Login.class).bindFromRequest();
        DynamicForm data = form().bindFromRequest();
        Login newlogin = new Login();
        newlogin.email = data.get("email");
        newlogin.password = data.get("password");

        System.out.println("email:"+newlogin.email);
        System.out.println("password:"+newlogin.password);

        if(newlogin.validate() != null) {
            return badRequest(views.html.auth.login.render(loginForm));
        } else {

            System.out.println("valid");
            session("email", newlogin.email);

            return ok(
                    Json.toJson(true)
            );
        }
    }

    /**
     * Logout and clean the session.
     */
    public static Result logout() {
        session().clear();
        flash("success", "You've been logged out");
        return redirect(
            routes.Application.index()
        );
    }

    // -- SignUp

    /**
     * Defines a form wrapping the User class.
     */ 
    final static Form<User> signupForm = form(User.class);
  
    /**
     * Display a blank form.
     */ 
    public static Result register() {
        return ok(signup_form.render(signupForm));
    }
  
    /**
     * Display a form pre-filled with an existing account.
     */
    public static Result edit() {
        // System.out.println("username>>"+Context.current().session().get("email"));

//            
        User existingUser = User.findByEmail(Context.current().session().get("email"));
        return ok(signup_form.render(signupForm.fill(existingUser)));
    }
  
    /**
     * Handle the form submission.
     */
    public static Result submit() {
        Form<User> filledForm = signupForm.bindFromRequest();
        
        // Check accept conditions
        if(!"true".equals(filledForm.field("accept").value())) {
            filledForm.reject("accept", "You must accept the terms and conditions");
        }
        
        // Check repeated password
        if(!filledForm.field("password").valueOr("").isEmpty()) {
            if(!filledForm.field("password").valueOr("").equals(filledForm.field("repeatPassword").value())) {
                filledForm.reject("repeatPassword", "Password don't match");
            }
        }

        
        if(filledForm.hasErrors()) {
            return badRequest(signup_form.render(filledForm));
        } else {
            User created = filledForm.get();
            User.create(created);
            session("email", created.email);
            return ok(summary.render(created));
        }
    }
}
