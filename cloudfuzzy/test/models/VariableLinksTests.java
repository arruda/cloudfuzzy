 
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
        
           
     
package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import models.*;

import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.libs.F.*;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;
import static org.junit.Assert.*;

//use with application test
import testhelper.WithApplicationAndIsoletedXfl;

public class VariableLinksTests  extends WithApplicationAndIsoletedXfl {
    protected User testUser;

    @Before
    public void setUp() {
        start(fakeApplication(inMemoryDatabase()));

        this.testUser = User.find.where().eq("email", "test@test.com").findUnique();

        String content = 
            "type t1 [1.0,100.0;100] {\n" +
              "mf0 xfl.triangle(-15.5,1.0,17.5);\n"+
              "mf1 xfl.triangle(1.0,17.5,34.0);\n"+
              "mf2 xfl.triangle(17.5,34.0,50.5);\n"+
              "mf3 xfl.triangle(34.0,50.5,67.0);\n"+
              "mf4 xfl.triangle(50.5,67.0,83.5);\n"+
              "mf5 xfl.triangle(67.0,83.5,100.0);\n"+
              "mf6 xfl.triangle(83.5,100.0,116.5);\n"+
            " }\n"+

            "rulebase aa (t1 vi1, t1 vi2, t1 vi3, t1 vi4 : t1 vo1, t1 vo2)  {\n"+
              "if(vi1 == mf0 & vi2 == mf1 & vi4 == mf3) -> vo2 = mf5;\n"+
              "[0.8]if(vi2 == mf1 & vi3 == mf2) -> vo1 = mf1, vo2 = mf3;\n"+
             "}\n"+

            "rulebase bb (t1 vi1 : t1 vo1)  {\n"+
              "if(vi1 == mf0) -> vo1 = mf2;\n"+
             "}\n"+

            "system (t1 vgi1, t1 vgi2 : t1 vgo1, t1 vgo2) {\n"+
            "  aa(NULL, vgi1, NULL, NULL : i0, NULL);\n"+
            "  bb(i0 : vgo1);\n"+
             "}\n";

//    	System.out.println(content);
        try{

            this.createFuzzySystem(content,"testSystem", this.testUser);
        }   
        catch(Exception e){
            fail(e.getMessage());
        }
    }

    @Test
    public void loadedFixtures() {

        assertNotNull(this.testUser);
        assertEquals("Test", this.testUser.name);

        assertNotNull(this.testSystem);
        assertEquals("testSystem", this.testSystem.name);
    }


    /**
     * This test will try to retrieve the next liks:
     * vgi1 -> rbc0.vi2
     * rbc0.vo1 -> rbc1.vi1
     * rbc1.vo1 -> vgo1
     * 
     */
    @Test
    public void retrieveLinksForSystem(){
        xfuzzy.lang.RulebaseCall rbc0 = this.testSystem.getSpecification().getSystemModule().getRulebaseCalls()[0];
        xfuzzy.lang.RulebaseCall rbc1 = this.testSystem.getSpecification().getSystemModule().getRulebaseCalls()[1];
        
       
        
        
        ArrayList<models.LinkCallForm> links = models.LinkCallForm.getLinks(this.testSystem);
        //check size first
        assertEquals(3,links.size());
        
        //check each link
        models.LinkCallForm check_link = null;
        Map<String,Integer> original_data = null;

        //vgi1 -> rbc0.vi2
        check_link = links.get(0);
        original_data = new HashMap<String,Integer>();
        original_data.put("variableDots[0].idRuleBaseCall", null);  
        original_data.put("variableDots[0].idSysVar", 0);  
        original_data.put("variableDots[0].kindSysVar", 0);  
        original_data.put("variableDots[0].idBaseVar", null);  
        original_data.put("variableDots[0].kindBaseVar", null);  
        
        original_data.put("variableDots[1].idRuleBaseCall", 0);  
        original_data.put("variableDots[1].idSysVar", null);  
        original_data.put("variableDots[1].kindSysVar", null);  
        original_data.put("variableDots[1].idBaseVar", 1);  
        original_data.put("variableDots[1].kindBaseVar", 0);  
        verify_link(check_link,original_data);

        //rbc0.vo1 -> rbc1.vi1
        check_link = links.get(1);
        original_data = new HashMap<String,Integer>();
        original_data.put("variableDots[0].idRuleBaseCall", 0);  
        original_data.put("variableDots[0].idSysVar", null);  
        original_data.put("variableDots[0].kindSysVar", null);  
        original_data.put("variableDots[0].idBaseVar", 0);  
        original_data.put("variableDots[0].kindBaseVar", 1);  
        
        original_data.put("variableDots[1].idRuleBaseCall", 1);  
        original_data.put("variableDots[1].idSysVar", null);  
        original_data.put("variableDots[1].kindSysVar", null);  
        original_data.put("variableDots[1].idBaseVar", 0);  
        original_data.put("variableDots[1].kindBaseVar", 0);  
        verify_link(check_link,original_data);
        
        //rbc1.vo1 -> vgo1
        check_link = links.get(2);
        original_data = new HashMap<String,Integer>();
        original_data.put("variableDots[0].idRuleBaseCall", 1);  
        original_data.put("variableDots[0].idSysVar", null);  
        original_data.put("variableDots[0].kindSysVar", null);  
        original_data.put("variableDots[0].idBaseVar", 0);  
        original_data.put("variableDots[0].kindBaseVar", 1);  
        
        original_data.put("variableDots[1].idRuleBaseCall", null);  
        original_data.put("variableDots[1].idSysVar", 0);  
        original_data.put("variableDots[1].kindSysVar", 1);  
        original_data.put("variableDots[1].idBaseVar", null);  
        original_data.put("variableDots[1].kindBaseVar", null);  
        verify_link(check_link,original_data);
        
    }
    
    /**
     * Helper function that verifies a single link against a map of datas
     * 
     * @param link
     * @param original_data
     */
    public void verify_link(models.LinkCallForm link, Map<String,Integer> original_data){
        //should have 2 variableDots
        assertEquals(2, link.variableDots.size());

    	//check things for the first and the second variableDot
        for (int i = 0; i < 2; i++) {
        	models.LinkCallForm.VariableDot variableDot = link.variableDots.get(i);
        	String variableNum = "variableDots[" + i +"].";
        	String field = variableNum;
        	
        	//idRuleBaseCall
        	field = variableNum+"idRuleBaseCall";
            assertEquals("error:" + field,original_data.get(field), variableDot.idRuleBaseCall);
            
            //idBaseVar
        	field = variableNum+"idBaseVar";
            assertEquals("error:" + field,original_data.get(field), variableDot.idBaseVar);
            //kindBaseVar
        	field = variableNum+"kindBaseVar";
            assertEquals("error:" + field,original_data.get(field), variableDot.kindBaseVar);
            
            //idSysVar
        	field = variableNum+"idSysVar";
            assertEquals("error:" + field,original_data.get(field), variableDot.idSysVar);
            //kindSysVar
        	field = variableNum+"kindSysVar";
            assertEquals("error:" + field,original_data.get(field), variableDot.kindSysVar);
			
		}
        
    }
    
}
