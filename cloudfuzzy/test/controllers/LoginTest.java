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