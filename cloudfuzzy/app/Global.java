import play.*;
import play.mvc.*;
import play.mvc.Http.*;

import static play.mvc.Results.*;


import views.html.*;

import play.libs.*;

import java.util.*;

import com.avaje.ebean.*;

import models.*;

public class Global extends GlobalSettings {
    
    public void onStart(Application app) {
        InitialData.insert(app);
    }
    
    static class InitialData {
        
        public static void insert(Application app) {
            if(Ebean.find(User.class).findRowCount() == 0) {
                
                Map<String,List<Object>> all = (Map<String,List<Object>>)Yaml.load("initial-data.yml");

                // Insert users first
                Ebean.save(all.get("users"));
                
                Ebean.save(all.get("systems"));

                
            }
        }
        


   }

  // @Override
  // public Result onHandlerNotFound(String uri) {
  //   return notFound(
  //     views.h5bp.notFound(uri)
  //   );
  // }  
    

  /**
  * Custom page not found.
  */
  @Override
    public Result onHandlerNotFound(RequestHeader request) {
        return notFound(
            views.html.h5bp.notFound.render("Page not Found")
        );
    }
}