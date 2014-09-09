 
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

            	List<Object> systems =  all.get("systems");
              ((FuzzySystem)systems.get(0)).filePath = FuzzySystem.generateFilePath((FuzzySystem)systems.get(0));
              ((FuzzySystem)systems.get(1)).filePath = FuzzySystem.generateFilePath((FuzzySystem)systems.get(1));
                Ebean.save(systems);
                
                Ebean.save(all.get("rulebasecalls"));

                
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
