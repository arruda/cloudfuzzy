 
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
        
           
     
package testhelper;

import java.io.File;
import java.io.OutputStream;
import java.io.FileOutputStream;


import models.FuzzySystem;
import models.User;


import org.junit.*;
import static org.junit.Assert.*;

/**
 * Isoleted xfl file
 */
public class IsolatedXflFile {

    protected FuzzySystem testSystem;

    protected void prepareXflFile(String content, File file) throws Exception {
        try {
            OutputStream stream = new FileOutputStream(file);
            stream.write(content.getBytes());
            stream.close();
        } catch (Exception e) { 
            throw new Exception("Error writing XFL3 file. Changes not applied.");
        }

    }

    protected FuzzySystem createFuzzySystem(String content, String name,User user) throws Exception{
        FuzzySystem newSys = new FuzzySystem();

        newSys.name = name;
        newSys.description = "test xfl";
        newSys.fileName = newSys.name+".xfl";
        newSys.user = user;
        FuzzySystem.create(newSys);
        this.testSystem = FuzzySystem.find.where().eq("name", name).eq("user.email", user.email).findUnique();
        this.prepareXflFile(content,newSys.getFile());
        return this.testSystem;
    }

    @After
    public void removeIsolatedFuzzySystem() {
        if(this.testSystem != null){            
            this.testSystem.delete();
        }
    }

}
