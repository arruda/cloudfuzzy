package controllers;

import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.libs.F.*;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;
import static org.junit.Assert.*;

import java.util.Map;
import java.util.HashMap;

import com.google.common.collect.ImmutableMap;

//use with application test
import testhelper.WithApplication;

import models.Type;
import models.FuzzySystem;
import models.User;

public class TypeTest  extends WithApplication {

    FuzzySystem testSystem;

    @Before
    public void setUp() {
        start(fakeApplication(inMemoryDatabase()));

        this.testSystem = FuzzySystem.find.where().eq("name", "test").findUnique();
    }

    @Test
    public void loadedFixtures() {

        User testUser = User.find.where().eq("email", "test@test.com").findUnique();
        assertNotNull(testUser);
        assertEquals("Test", testUser.name);

        FuzzySystem testSystem = FuzzySystem.find.where().eq("name", "test").findUnique();
        assertNotNull(testSystem);
        assertEquals("test", testSystem.name);
        try{
            Type t1 = Type.get(testSystem,0);
            assertNotNull(t1);
            assertEquals("t1", t1.name);

        }
        catch(Exception e){
            fail(e.getMessage());
        }
    }

    @Test
    public void detailType() {
        Long system_id = 1l;
        Integer type_id = 0;

        Result result = callAction(
            controllers.routes.ref.Types.detail(system_id,type_id),
            fakeRequest().withSession("email", "test@test.com")
        );
        assertEquals(OK, status(result));
        
    }

    // @Test
    // public void detailTypeWrongUser() {
    //     Long system_id = 1l;
    //     Integer type_id = 0;

    //     Result result = callAction(
    //         controllers.routes.ref.Types.detail(system_id,type_id),
    //         fakeRequest().withSession("email", "test2@test.com")
    //     );
    //     assertEquals(FORBIDDEN, status(result));
        
    // }

    @Test
    public void addType() {
        FuzzySystem newSystem = addNewSystem();

        Map<String,String> post_data = new HashMap<String,String>();
        post_data.put("name", "newtype");  
        post_data.put("type", "0");  
        post_data.put("min", "0");  
        post_data.put("max", "100");  
        post_data.put("card", "100");  
        post_data.put("numMFs", "7");  

        Result result = callAction(
            controllers.routes.ref.Types.create(newSystem.id),
            fakeRequest().withSession("email", "test@test.com")
            .withFormUrlEncodedBody(post_data)
        );
        assertEquals(303, status(result));

        assertEquals("/system/"+newSystem.id, header("Location", result));

        try{
            Type newType = Type.get(newSystem,0);
            assertNotNull(newType);
            assertEquals("newtype", newType.name);

        }
        catch(Exception e){
            fail(e.getMessage());
        }

    }


    @Test
    public void deleteType() {
        FuzzySystem newSystem = addNewSystem();

        //first add a new type
        Map<String,String> post_data = new HashMap<String,String>();
        post_data.put("name", "newtype");  
        post_data.put("type", "0");  
        post_data.put("min", "0");  
        post_data.put("max", "100");  
        post_data.put("card", "100");  
        post_data.put("numMFs", "7");  

        Result result = callAction(
            controllers.routes.ref.Types.create(newSystem.id),
            fakeRequest().withSession("email", "test@test.com")
            .withFormUrlEncodedBody(post_data)
        );


        //now can delete it
        result = callAction(
            controllers.routes.ref.Types.delete(newSystem.id, 0),
            fakeRequest().withSession("email", "test@test.com")
        );

        try{
            Type newType = Type.get(newSystem,0);
            fail("Didn't deleted the fuzzy type");

        }
        catch(Exception e){
        }


    }


    public FuzzySystem addNewSystem(){        
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
        return FuzzySystem.find.where().eq("name", "newsystem").findUnique();
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