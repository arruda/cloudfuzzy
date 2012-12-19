package models;

import java.util.ArrayList;

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

public class FuzzySystemTests  extends WithApplicationAndIsoletedXfl {
    protected User testUser;

    @Before
    public void setUp() {
        start(fakeApplication(inMemoryDatabase()));

        this.testUser = User.find.where().eq("email", "test@test.com").findUnique();

        // this.testSystem = FuzzySystem.find.where().eq("name", "test").findUnique();
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

            "system (t1 vgi1, t1 vgi2 : t1 vgo1, t1 vgo2) {\n"+
             "}\n";

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


    @Test
    public void testSaveArrayDoubleAndRetrieve() {
    	this.testSystem.rulebaseCallsPos = new ArrayList<Double>();
    	this.testSystem.rulebaseCallsPos.add(1.0);
    	this.testSystem.save();
    	Long id_sys = this.testSystem.id;
    	this.testSystem = null;
    	this.testSystem = FuzzySystem.find.byId(id_sys);
    	System.out.println(this.testSystem.rulebaseCallsPos);
    	assertFalse("No rulebaseCall pos", this.testSystem.rulebaseCallsPos == null);
    	assertFalse("No rulebaseCall pos", this.testSystem == null);
//    	assertEquals(1.0, this.testSystem);
    }    

    
}