 
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

import models.User;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;

import play.mvc.*;
import play.libs.*;
import play.test.*;
import static play.test.Helpers.*;
import com.avaje.ebean.Ebean;
import com.google.common.collect.ImmutableMap;

//use with application test
import testhelper.WithApplication;

public class LoginTest extends WithApplication {
    @Before
    public void setUp() {
        start(fakeApplication(inMemoryDatabase()));

    }

    @Test
    public void loadedFixtures() {

        User testUser = User.find.where().eq("email", "test@test.com").findUnique();
        assertNotNull(testUser);
        assertEquals("Test", testUser.name);
    }

    @Test
    public void authenticateSuccess() {

        ImmutableMap post_data = 
                ImmutableMap.of(
                    "email", "test@test.com",
                    "password", "test"
                );

        Result result = callAction(
            controllers.routes.ref.Authentication.authenticate(),
            fakeRequest().withFormUrlEncodedBody(post_data)
        );
        assertEquals(200, status(result));
        assertEquals("test@test.com", session(result).get("email"));
    }

    
    @Test
    public void authenticated() {
        Result result = callAction(
            controllers.routes.ref.Systems.list(),
            fakeRequest().withSession("email", "test@test.com")
        );
        assertEquals(200, status(result));
    }    
    @Test
    public void notAuthenticated() {
        Result result = callAction(
            controllers.routes.ref.Systems.list(),
            fakeRequest()
        );
        assertEquals(303, status(result));
        assertEquals("/login", header("Location", result));
    }

}
