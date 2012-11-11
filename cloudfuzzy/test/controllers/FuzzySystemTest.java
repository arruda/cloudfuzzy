package controllers;

import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.libs.F.*;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;
import static org.junit.Assert.*;
import com.google.common.collect.ImmutableMap;

//use with application test
import testhelper.WithApplication;

import models.FuzzySystem;
import models.User;

public class FuzzySystemTest  extends WithApplication {

    @Before
    public void setUp() {
        start(fakeApplication(inMemoryDatabase()));
    }

    @Test
    public void loadedFixtures() {

        User testUser = User.find.where().eq("email", "test@test.com").findUnique();
        assertNotNull(testUser);
        assertEquals("Test", testUser.name);

        FuzzySystem testSystem = FuzzySystem.find.where().eq("name", "test").findUnique();
        assertNotNull(testSystem);
        assertEquals("test", testSystem.name);
    }


    @Test
    public void listSystems() {
        Result result = callAction(
            controllers.routes.ref.Systems.list(),
            fakeRequest().withSession("email", "test@test.com")
        );
        assertEquals(200, status(result));

        //should have at leat part of the link to the test system detail
        assertThat(contentAsString(result)).contains(">test</a>");
    }

    @Test
    public void detailSystem() {
        Long system_id = 1l;

        Result result = callAction(
            controllers.routes.ref.Systems.detail(system_id),
            fakeRequest().withSession("email", "test@test.com")
        );
        assertEquals(OK, status(result));
        
    }

    @Test
    public void detailSystemWrongUser() {
        Long system_id = 1l;

        Result result = callAction(
            controllers.routes.ref.Systems.detail(system_id),
            fakeRequest().withSession("email", "test2@test.com")
        );
        assertEquals(FORBIDDEN, status(result));
        
    }

    @Test
    public void addSystem() {

        ImmutableMap post_data = 
                ImmutableMap.of(
                    "name", "newsystem",
                    "description", "a new fuzzy System"
                );

        Result result = callAction(
            controllers.routes.ref.Systems.create(),
            fakeRequest().withSession("email", "test@test.com")
            .withFormUrlEncodedBody(post_data)
        );
        assertEquals(303, status(result));

        //check if this new system was created
        FuzzySystem newSystem = FuzzySystem.find.where().eq("name", "newsystem").findUnique();
        assertNotNull(newSystem);
        assertEquals("newsystem", newSystem.name);

        //should be able to get the specification:
        xfuzzy.lang.Specification spec = newSystem.getSpecification();
        assertNotNull(spec);
        //spec and fuzzy system should have same name
        assertEquals(spec.getName(), newSystem.name);

    }

    @Test
    public void deleteSystem() {

        //first create a new fuzzy system
        ImmutableMap post_data = 
                ImmutableMap.of(
                    "name", "newsystem",
                    "description", "a new fuzzy System"
                );

        Result result = callAction(
            controllers.routes.ref.Systems.create(),
            fakeRequest().withSession("email", "test@test.com")
            .withFormUrlEncodedBody(post_data)
        );
        //check if this new system was created
        FuzzySystem newSystem = FuzzySystem.find.where().eq("name", "newsystem").findUnique();

        assertNotNull(newSystem);


        //now can delete it
        result = callAction(
            controllers.routes.ref.Systems.delete(newSystem.id),
            fakeRequest().withSession("email", "test@test.com")
        );

        //check if this new system was deleted
        newSystem = FuzzySystem.find.where().eq("name", "newsystem").findUnique();

        assertNull(newSystem);

    }

    @Test
    public void deleteSystemWrongUser() {
        Long system_id = 1l;

        Result result = callAction(
            controllers.routes.ref.Systems.delete(system_id),
            fakeRequest().withSession("email", "test2@test.com")
        );
        assertEquals(FORBIDDEN, status(result));


    }


    //delete the fuzzy systems that where created in this tests
    @After
    public void cleanFuzzySystems(){
        FuzzySystem newSystem = FuzzySystem.find.where().eq("name", "newsystem").findUnique();
        if(newSystem != null){
            newSystem.delete();
        }
    }

}