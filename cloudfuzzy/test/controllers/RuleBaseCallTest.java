package controllers;

import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.libs.F.*;

import static play.mvc.Http.Status.FORBIDDEN;
import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;
import static org.junit.Assert.*;

import java.util.Map;
import java.util.HashMap;

import com.google.common.collect.ImmutableMap;


//use with application test
import testhelper.WithApplicationAndIsoletedXfl;
import models.Type;
import models.FuzzySystem;
import models.User;
import models.Variable;

public class RuleBaseCallTest  extends WithApplicationAndIsoletedXfl {

  String eol = System.getProperty("line.separator", "\n");
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
            "  aa(NULL, NULL, NULL, NULL : NULL, NULL);\n"+
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

    /**
    *Test if the call is added, blank, simple as that.
    */
    @Test
    public void ajaxAddCall() {

        Integer id_rb = 0;
        xfuzzy.lang.Rulebase rb = null;
        try{
            rb = models.RuleBase.getFuzzy(this.testSystem,id_rb);
        } 
        catch(Exception e){
            fail(e.getMessage());
        }

        Result result = callAction(
            controllers.routes.ref.RuleBaseCalls.ajaxAddCall(this.testSystem.id,id_rb),
            fakeRequest().withSession("email", this.testUser.email)
        );
        assertThat(contentAsString(result)).contains("ok");
        try{

            this.testSystem.loadSpecification();
        }
        catch(Exception e){
            fail(e.getMessage());
        }


        xfuzzy.lang.RulebaseCall rbc =
              this.testSystem.getSpecification().getSystemModule().getRulebaseCalls()[1];

        //special atention to the space before the rulebase name, and the \n in the end
        String new_rbc_to_xfl = "  aa(NULL, NULL, NULL, NULL : NULL, NULL);" + eol;

        assertEquals(new_rbc_to_xfl, rbc.toXfl());


    }

    /**
    *Helper to be used in the cases for the ajaxAddLink,
    *this here only do the call and return the result
    */
    public Result  ajaxAddLinkPostData(Map<String,String> post_data){

        Result result = callAction(
            controllers.routes.ref.RuleBaseCalls.ajaxAddLink(this.testSystem.id),
            fakeRequest().withSession("email", this.testUser.email)
            .withFormUrlEncodedBody(post_data)
        );
        return result;
    }

    /**
    *This is one of the cases, for the tests of adding a link: A1
    * 
    * vgi1 -> rbc0(aa).vi1
    *
    */
    @Test
    public void ajaxAddLinkA1() {


        Map<String,String> post_data = new HashMap<String,String>();
        post_data.put("variableDots[0].idRuleBaseCall", null);  
        post_data.put("variableDots[0].idSysVar", "0");  
        post_data.put("variableDots[0].kindSysVar", String.valueOf(Variable.INPUT));  
        post_data.put("variableDots[0].idBaseVar", null);  
        post_data.put("variableDots[0].kindBaseVar", null);  

        post_data.put("variableDots[1].idRuleBaseCall", "0");  
        post_data.put("variableDots[1].idSysVar", null);  
        post_data.put("variableDots[1].kindSysVar", null);  
        post_data.put("variableDots[1].idBaseVar", "0");  
        post_data.put("variableDots[1].kindBaseVar", String.valueOf(Variable.INPUT));  

        Result result = ajaxAddLinkPostData(post_data);
        assertThat(contentAsString(result)).contains("ok");

        //should be with the link in the given rulebaseCall now
        xfuzzy.lang.RulebaseCall rbc =
              this.testSystem.getSpecification().getSystemModule().getRulebaseCalls()[0];

        //special atention to the space before the rulebase name, and the \n in the end
        String newLinkToRBCToXfl = "  aa(vgi1, NULL, NULL, NULL : NULL, NULL);" + eol;
        assertEquals(newLinkToRBCToXfl, rbc.toXfl());

    }

    /**
    *This is one of the cases, for the tests of adding a link: A2
    * 
    * vgi2 -> rbc0(aa).vi3
    *
    */
    @Test
    public void ajaxAddLinkA2() {


        Map<String,String> post_data = new HashMap<String,String>();
        post_data.put("variableDots[0].idRuleBaseCall", null);  
        post_data.put("variableDots[0].idSysVar", "1");  
        post_data.put("variableDots[0].kindSysVar", String.valueOf(Variable.INPUT));  
        post_data.put("variableDots[0].idBaseVar", null);  
        post_data.put("variableDots[0].kindBaseVar", null);  

        post_data.put("variableDots[1].idRuleBaseCall", "0");  
        post_data.put("variableDots[1].idSysVar", null);  
        post_data.put("variableDots[1].kindSysVar", null);  
        post_data.put("variableDots[1].idBaseVar", "2");  
        post_data.put("variableDots[1].kindBaseVar", String.valueOf(Variable.INPUT));  

        Result result = ajaxAddLinkPostData(post_data);
        assertThat(contentAsString(result)).contains("ok");

        //should be with the link in the given rulebaseCall now
        xfuzzy.lang.RulebaseCall rbc =
              this.testSystem.getSpecification().getSystemModule().getRulebaseCalls()[0];

        //special atention to the space before the rulebase name, and the \n in the end
        String newLinkToRBCToXfl = "  aa(NULL, NULL, vgi2, NULL : NULL, NULL);" + eol;
        assertEquals(newLinkToRBCToXfl, rbc.toXfl());

    }

    /**
    *This is one of the cases, for the tests of adding a link: A3
    * 
    * rbc0(aa).vi3 -> vgi2
    *
    */
    @Test
    public void ajaxAddLinkA3() {


        Map<String,String> post_data = new HashMap<String,String>();
        post_data.put("variableDots[0].idRuleBaseCall", "0");  
        post_data.put("variableDots[0].idSysVar", null);  
        post_data.put("variableDots[0].kindSysVar", null);  
        post_data.put("variableDots[0].idBaseVar", "2");  
        post_data.put("variableDots[0].kindBaseVar", String.valueOf(Variable.INPUT));  

        post_data.put("variableDots[1].idRuleBaseCall", null);  
        post_data.put("variableDots[1].idSysVar", "1");  
        post_data.put("variableDots[1].kindSysVar", String.valueOf(Variable.INPUT));  
        post_data.put("variableDots[1].idBaseVar", null);  
        post_data.put("variableDots[1].kindBaseVar", null);  


        Result result = ajaxAddLinkPostData(post_data);
        assertThat(contentAsString(result)).contains("ok");

        //should be with the link in the given rulebaseCall now
        xfuzzy.lang.RulebaseCall rbc =
              this.testSystem.getSpecification().getSystemModule().getRulebaseCalls()[0];

        //special atention to the space before the rulebase name, and the \n in the end
        String newLinkToRBCToXfl = "  aa(NULL, NULL, vgi2, NULL : NULL, NULL);" + eol;
        assertEquals(newLinkToRBCToXfl, rbc.toXfl());

    }

    /**
    *This is one of the cases, for the tests of adding a link: A4
    * 
    * rbc0(aa).vo1 -> vgo1
    *
    */
    @Test
    public void ajaxAddLinkA4() {


        Map<String,String> post_data = new HashMap<String,String>();
        post_data.put("variableDots[0].idRuleBaseCall", "0");  
        post_data.put("variableDots[0].idSysVar", null);  
        post_data.put("variableDots[0].kindSysVar", null);  
        post_data.put("variableDots[0].idBaseVar", "0");  
        post_data.put("variableDots[0].kindBaseVar", String.valueOf(Variable.OUTPUT));  

        post_data.put("variableDots[1].idRuleBaseCall", null);  
        post_data.put("variableDots[1].idSysVar", "0");  
        post_data.put("variableDots[1].kindSysVar", String.valueOf(Variable.OUTPUT));  
        post_data.put("variableDots[1].idBaseVar", null);  
        post_data.put("variableDots[1].kindBaseVar", null);  


        Result result = ajaxAddLinkPostData(post_data);
        assertThat(contentAsString(result)).contains("ok");

        //should be with the link in the given rulebaseCall now
        xfuzzy.lang.RulebaseCall rbc =
              this.testSystem.getSpecification().getSystemModule().getRulebaseCalls()[0];

        //special atention to the space before the rulebase name, and the \n in the end
        String newLinkToRBCToXfl = "  aa(NULL, NULL, NULL, NULL : vgo1, NULL);" + eol;
        assertEquals(newLinkToRBCToXfl, rbc.toXfl());

    }


    /**
    *This is one of the cases, for the tests of adding a link: A5
    * 
    * vgo1 -> rbc0(aa).vo2
    *
    */
    @Test
    public void ajaxAddLinkA5() {


        Map<String,String> post_data = new HashMap<String,String>();
        post_data.put("variableDots[1].idRuleBaseCall", "0");  
        post_data.put("variableDots[1].idSysVar", null);  
        post_data.put("variableDots[1].kindSysVar", null);  
        post_data.put("variableDots[1].idBaseVar", "1");  
        post_data.put("variableDots[1].kindBaseVar", String.valueOf(Variable.OUTPUT));  

        post_data.put("variableDots[0].idRuleBaseCall", null);  
        post_data.put("variableDots[0].idSysVar", "0");  
        post_data.put("variableDots[0].kindSysVar", String.valueOf(Variable.OUTPUT));  
        post_data.put("variableDots[0].idBaseVar", null);  
        post_data.put("variableDots[0].kindBaseVar", null);  


        Result result = ajaxAddLinkPostData(post_data);
        assertThat(contentAsString(result)).contains("ok");

        //should be with the link in the given rulebaseCall now
        xfuzzy.lang.RulebaseCall rbc =
              this.testSystem.getSpecification().getSystemModule().getRulebaseCalls()[0];

        //special atention to the space before the rulebase name, and the \n in the end
        String newLinkToRBCToXfl = "  aa(NULL, NULL, NULL, NULL : NULL, vgo1);" + eol;
        assertEquals(newLinkToRBCToXfl, rbc.toXfl());

    }

    /**
    *This is one of the cases, for the tests of adding a link: A6
    * 
    * vgi2 -> rbc0(aa).vi3
    * rbc0(aa).vo1 -> vgo1
    *
    */
    @Test
    public void ajaxAddLinkA6() {

        //add the first link vgi2 -> rbc0(aa).vi3
        Map<String,String> post_data = new HashMap<String,String>();
        post_data.put("variableDots[0].idRuleBaseCall", null);  
        post_data.put("variableDots[0].idSysVar", "1");  
        post_data.put("variableDots[0].kindSysVar", String.valueOf(Variable.INPUT));  
        post_data.put("variableDots[0].idBaseVar", null);  
        post_data.put("variableDots[0].kindBaseVar", null);  

        post_data.put("variableDots[1].idRuleBaseCall", "0");  
        post_data.put("variableDots[1].idSysVar", null);  
        post_data.put("variableDots[1].kindSysVar", null);  
        post_data.put("variableDots[1].idBaseVar", "2");  
        post_data.put("variableDots[1].kindBaseVar", String.valueOf(Variable.INPUT));  

        Result result = ajaxAddLinkPostData(post_data);
        assertThat(contentAsString(result)).contains("ok");

        //should be with the link in the given rulebaseCall now
        xfuzzy.lang.RulebaseCall rbc =
              this.testSystem.getSpecification().getSystemModule().getRulebaseCalls()[0];

        //special atention to the space before the rulebase name, and the \n in the end
        String newLinkToRBCToXfl = "  aa(NULL, NULL, vgi2, NULL : NULL, NULL);" + eol;
        assertEquals(newLinkToRBCToXfl, rbc.toXfl());



        //add the second link: rbc0(aa).vo1 -> vgo1

        post_data = new HashMap<String,String>();
        post_data.put("variableDots[0].idRuleBaseCall", "0");  
        post_data.put("variableDots[0].idSysVar", null);  
        post_data.put("variableDots[0].kindSysVar", null);  
        post_data.put("variableDots[0].idBaseVar", "0");  
        post_data.put("variableDots[0].kindBaseVar", String.valueOf(Variable.OUTPUT));  

        post_data.put("variableDots[1].idRuleBaseCall", null);  
        post_data.put("variableDots[1].idSysVar", "0");  
        post_data.put("variableDots[1].kindSysVar", String.valueOf(Variable.OUTPUT));  
        post_data.put("variableDots[1].idBaseVar", null);  
        post_data.put("variableDots[1].kindBaseVar", null);  

        result = ajaxAddLinkPostData(post_data);
        assertThat(contentAsString(result)).contains("ok");


        //remember to reload after this consequent change
        try{

            this.testSystem.loadSpecification();
        }
        catch(Exception e){
            fail(e.getMessage());
        }

        //should be with the link in the given rulebaseCall now
        rbc = this.testSystem.getSpecification().getSystemModule().getRulebaseCalls()[0];

        //special atention to the space before the rulebase name, and the \n in the end
        newLinkToRBCToXfl = "  aa(NULL, NULL, vgi2, NULL : vgo1, NULL);" + eol;
        assertEquals(newLinkToRBCToXfl, rbc.toXfl());

    }


    /**
    *This is one of the cases, for the tests of adding a link: A7
    * 
    * +rbc1(bb)
    * rbc0(aa).vo2 -> rbc1(bb).vi1
    *
    */
    @Test
    public void ajaxAddLinkA7() {

        Integer id_rb = 1;
        xfuzzy.lang.Rulebase rb = null;
        try{
            rb = models.RuleBase.getFuzzy(this.testSystem,id_rb);
        } 
        catch(Exception e){
            fail(e.getMessage());
        }

        Result result = callAction(
            controllers.routes.ref.RuleBaseCalls.ajaxAddCall(this.testSystem.id,id_rb),
            fakeRequest().withSession("email", this.testUser.email)
        );
        assertThat(contentAsString(result)).contains("ok");
        try{

            this.testSystem.loadSpecification();
        }
        catch(Exception e){
            fail(e.getMessage());
        }


        xfuzzy.lang.RulebaseCall rbc =
              this.testSystem.getSpecification().getSystemModule().getRulebaseCalls()[1];

        //special atention to the space before the rulebase name, and the \n in the end
        String new_rbc_to_xfl = "  bb(NULL : NULL);" + eol;
        assertEquals(new_rbc_to_xfl, rbc.toXfl());


        Map<String,String> post_data = new HashMap<String,String>();
        post_data.put("variableDots[0].idRuleBaseCall", "0");  
        post_data.put("variableDots[0].idSysVar", null);  
        post_data.put("variableDots[0].kindSysVar", null);  
        post_data.put("variableDots[0].idBaseVar", "1");  
        post_data.put("variableDots[0].kindBaseVar", String.valueOf(Variable.OUTPUT));  

        post_data.put("variableDots[1].idRuleBaseCall", "1");  
        post_data.put("variableDots[1].idSysVar", null);  
        post_data.put("variableDots[1].kindSysVar", null);  
        post_data.put("variableDots[1].idBaseVar", "0");  
        post_data.put("variableDots[1].kindBaseVar", String.valueOf(Variable.INPUT));  

        result = ajaxAddLinkPostData(post_data);
        assertThat(contentAsString(result)).contains("ok");

        try{

            this.testSystem.loadSpecification();
        }
        catch(Exception e){
            fail(e.getMessage());
        }

        //should be with the link in the given rulebaseCall now
        xfuzzy.lang.RulebaseCall rbc1 =
             this.testSystem.getSpecification().getSystemModule().getRulebaseCalls()[0];
        xfuzzy.lang.RulebaseCall rbc2 =
             this.testSystem.getSpecification().getSystemModule().getRulebaseCalls()[1];

        //special atention to the space before the rulebase name, and the \n in the end
        String rbc1ToXfl = "  aa(NULL, NULL, NULL, NULL : NULL, i0);" + eol;
        //special atention to the space before the rulebase name, and the \n in the end
        String rbc2ToXfl = "  bb(i0 : NULL);" + eol;
        assertEquals(rbc1ToXfl, rbc1.toXfl());
        assertEquals(rbc2ToXfl, rbc2.toXfl());

    }

    /**
    *This is one of the cases, for the tests of adding a link: A8
    * +rbc1(bb)
    * rbc0(aa).vo2 -> rbc1(bb).vi1
    * +rbc2(aa)
    * rbc1(bb).vo1 -> rbc2(aa).vi2
    *
    */
    @Test
    public void ajaxAddLinkA8() {

        //======================== +rbc1(bb) =====================//
        Integer id_rb = 1;
        xfuzzy.lang.Rulebase rb = null;
        try{
            rb = models.RuleBase.getFuzzy(this.testSystem,id_rb);
        } 
        catch(Exception e){
            fail(e.getMessage());
        }

        Result result = callAction(
            controllers.routes.ref.RuleBaseCalls.ajaxAddCall(this.testSystem.id,id_rb),
            fakeRequest().withSession("email", this.testUser.email)
        );
        assertThat(contentAsString(result)).contains("ok");
        try{

            this.testSystem.loadSpecification();
        }
        catch(Exception e){
            fail(e.getMessage());
        }


        xfuzzy.lang.RulebaseCall rbc =
              this.testSystem.getSpecification().getSystemModule().getRulebaseCalls()[1];

        //special atention to the space before the rulebase name, and the \n in the end
        String new_rbc_to_xfl = "  bb(NULL : NULL);" + eol;
        assertEquals(new_rbc_to_xfl, rbc.toXfl());

        //======================== rbc0(aa).vo2 -> rbc1(bb).vi1 =====================//

        Map<String,String> post_data = new HashMap<String,String>();
        post_data.put("variableDots[0].idRuleBaseCall", "0");  
        post_data.put("variableDots[0].idSysVar", null);  
        post_data.put("variableDots[0].kindSysVar", null);  
        post_data.put("variableDots[0].idBaseVar", "1");  
        post_data.put("variableDots[0].kindBaseVar", String.valueOf(Variable.OUTPUT));  

        post_data.put("variableDots[1].idRuleBaseCall", "1");  
        post_data.put("variableDots[1].idSysVar", null);  
        post_data.put("variableDots[1].kindSysVar", null);  
        post_data.put("variableDots[1].idBaseVar", "0");  
        post_data.put("variableDots[1].kindBaseVar", String.valueOf(Variable.INPUT));  

        result = ajaxAddLinkPostData(post_data);
        assertThat(contentAsString(result)).contains("ok");

        try{

            this.testSystem.loadSpecification();
        }
        catch(Exception e){
            fail(e.getMessage());
        }

        //should be with the link in the given rulebaseCall now
        xfuzzy.lang.RulebaseCall rbc1 =
             this.testSystem.getSpecification().getSystemModule().getRulebaseCalls()[0];
        xfuzzy.lang.RulebaseCall rbc2 =
             this.testSystem.getSpecification().getSystemModule().getRulebaseCalls()[1];

        //special atention to the space before the rulebase name, and the \n in the end
        String rbc1ToXfl = "  aa(NULL, NULL, NULL, NULL : NULL, i0);" + eol;
        //special atention to the space before the rulebase name, and the \n in the end
        String rbc2ToXfl = "  bb(i0 : NULL);" + eol;
        assertEquals(rbc1ToXfl, rbc1.toXfl());
        assertEquals(rbc2ToXfl, rbc2.toXfl());

        //======================== +rbc2(aa) =====================//


        id_rb = 0;
        rb = null;
        try{
            rb = models.RuleBase.getFuzzy(this.testSystem,id_rb);
        } 
        catch(Exception e){
            fail(e.getMessage());
        }

        result = callAction(
            controllers.routes.ref.RuleBaseCalls.ajaxAddCall(this.testSystem.id,id_rb),
            fakeRequest().withSession("email", this.testUser.email)
        );
        assertThat(contentAsString(result)).contains("ok");
        try{

            this.testSystem.loadSpecification();
        }
        catch(Exception e){
            fail(e.getMessage());
        }


        rbc = this.testSystem.getSpecification().getSystemModule().getRulebaseCalls()[2];

        //special atention to the space before the rulebase name, and the \n in the end
        new_rbc_to_xfl = "  aa(NULL, NULL, NULL, NULL : NULL, NULL);" + eol;
        assertEquals(new_rbc_to_xfl, rbc.toXfl());
        //======================== rbc1(bb).vo1 -> rbc2(aa).vi2 =====================//

        post_data = new HashMap<String,String>();
        post_data.put("variableDots[0].idRuleBaseCall", "1");  
        post_data.put("variableDots[0].idSysVar", null);  
        post_data.put("variableDots[0].kindSysVar", null);  
        post_data.put("variableDots[0].idBaseVar", "0");  
        post_data.put("variableDots[0].kindBaseVar", String.valueOf(Variable.OUTPUT));  

        post_data.put("variableDots[1].idRuleBaseCall", "2");  
        post_data.put("variableDots[1].idSysVar", null);  
        post_data.put("variableDots[1].kindSysVar", null);  
        post_data.put("variableDots[1].idBaseVar", "1");  
        post_data.put("variableDots[1].kindBaseVar", String.valueOf(Variable.INPUT));  

        result = ajaxAddLinkPostData(post_data);
        assertThat(contentAsString(result)).contains("ok");

        try{

            this.testSystem.loadSpecification();
        }
        catch(Exception e){
            fail(e.getMessage());
        }

        //should be with the link in the given rulebaseCall now
        rbc2 = this.testSystem.getSpecification().getSystemModule().getRulebaseCalls()[1];
        xfuzzy.lang.RulebaseCall rbc3 =
             this.testSystem.getSpecification().getSystemModule().getRulebaseCalls()[2];

        //special atention to the space before the rulebase name, and the \n in the end
        rbc2ToXfl = "  bb(i0 : i1);" + eol;
        //special atention to the space before the rulebase name, and the \n in the end
        String rbc3ToXfl = "  aa(NULL, i1, NULL, NULL : NULL, NULL);" + eol;

        assertEquals(rbc2ToXfl, rbc2.toXfl());
        assertEquals(rbc3ToXfl, rbc3.toXfl());

    }

    /**
    *This is one of the cases, for the tests of adding a link: A9
    * +rbc1(bb)
    *rbc1(bb).vi1 ->  rbc0(aa).vo2 
    * +rbc2(aa)
    * rbc2(aa).vi2 -> rbc1(bb).vo1
    *
    */
    @Test
    public void ajaxAddLinkA9() {

        //======================== +rbc1(bb) =====================//
        Integer id_rb = 1;
        xfuzzy.lang.Rulebase rb = null;
        try{
            rb = models.RuleBase.getFuzzy(this.testSystem,id_rb);
        } 
        catch(Exception e){
            fail(e.getMessage());
        }

        Result result = callAction(
            controllers.routes.ref.RuleBaseCalls.ajaxAddCall(this.testSystem.id,id_rb),
            fakeRequest().withSession("email", this.testUser.email)
        );
        assertThat(contentAsString(result)).contains("ok");
        try{

            this.testSystem.loadSpecification();
        }
        catch(Exception e){
            fail(e.getMessage());
        }


        xfuzzy.lang.RulebaseCall rbc =
              this.testSystem.getSpecification().getSystemModule().getRulebaseCalls()[1];

        //special atention to the space before the rulebase name, and the \n in the end
        String new_rbc_to_xfl = "  bb(NULL : NULL);" + eol;
        assertEquals(new_rbc_to_xfl, rbc.toXfl());

        //======================== rbc0(aa).vo2 -> rbc1(bb).vi1 =====================//

        Map<String,String> post_data = new HashMap<String,String>();
        post_data.put("variableDots[1].idRuleBaseCall", "0");  
        post_data.put("variableDots[1].idSysVar", null);  
        post_data.put("variableDots[1].kindSysVar", null);  
        post_data.put("variableDots[1].idBaseVar", "1");  
        post_data.put("variableDots[1].kindBaseVar", String.valueOf(Variable.OUTPUT));  

        post_data.put("variableDots[0].idRuleBaseCall", "1");  
        post_data.put("variableDots[0].idSysVar", null);  
        post_data.put("variableDots[0].kindSysVar", null);  
        post_data.put("variableDots[0].idBaseVar", "0");  
        post_data.put("variableDots[0].kindBaseVar", String.valueOf(Variable.INPUT));  

        result = ajaxAddLinkPostData(post_data);
        assertThat(contentAsString(result)).contains("ok");

        try{

            this.testSystem.loadSpecification();
        }
        catch(Exception e){
            fail(e.getMessage());
        }

        //should be with the link in the given rulebaseCall now
        xfuzzy.lang.RulebaseCall rbc1 =
             this.testSystem.getSpecification().getSystemModule().getRulebaseCalls()[0];
        xfuzzy.lang.RulebaseCall rbc2 =
             this.testSystem.getSpecification().getSystemModule().getRulebaseCalls()[1];

        //special atention to the space before the rulebase name, and the \n in the end
        String rbc1ToXfl = "  aa(NULL, NULL, NULL, NULL : NULL, i0);" + eol;
        //special atention to the space before the rulebase name, and the \n in the end
        String rbc2ToXfl = "  bb(i0 : NULL);" + eol;
        assertEquals(rbc1ToXfl, rbc1.toXfl());
        assertEquals(rbc2ToXfl, rbc2.toXfl());

        //======================== +rbc2(aa) =====================//


        id_rb = 0;
        rb = null;
        try{
            rb = models.RuleBase.getFuzzy(this.testSystem,id_rb);
        } 
        catch(Exception e){
            fail(e.getMessage());
        }

        result = callAction(
            controllers.routes.ref.RuleBaseCalls.ajaxAddCall(this.testSystem.id,id_rb),
            fakeRequest().withSession("email", this.testUser.email)
        );
        assertThat(contentAsString(result)).contains("ok");
        try{

            this.testSystem.loadSpecification();
        }
        catch(Exception e){
            fail(e.getMessage());
        }


        rbc = this.testSystem.getSpecification().getSystemModule().getRulebaseCalls()[2];

        //special atention to the space before the rulebase name, and the \n in the end
        new_rbc_to_xfl = "  aa(NULL, NULL, NULL, NULL : NULL, NULL);" + eol;
        assertEquals(new_rbc_to_xfl, rbc.toXfl());
        //======================== rbc1(bb).vo1 -> rbc2(aa).vi2 =====================//

        post_data = new HashMap<String,String>();
        post_data.put("variableDots[1].idRuleBaseCall", "1");  
        post_data.put("variableDots[1].idSysVar", null);  
        post_data.put("variableDots[1].kindSysVar", null);  
        post_data.put("variableDots[1].idBaseVar", "0");  
        post_data.put("variableDots[1].kindBaseVar", String.valueOf(Variable.OUTPUT));  

        post_data.put("variableDots[0].idRuleBaseCall", "2");  
        post_data.put("variableDots[0].idSysVar", null);  
        post_data.put("variableDots[0].kindSysVar", null);  
        post_data.put("variableDots[0].idBaseVar", "1");  
        post_data.put("variableDots[0].kindBaseVar", String.valueOf(Variable.INPUT));  

        result = ajaxAddLinkPostData(post_data);
        assertThat(contentAsString(result)).contains("ok");

        try{

            this.testSystem.loadSpecification();
        }
        catch(Exception e){
            fail(e.getMessage());
        }

        //should be with the link in the given rulebaseCall now
        rbc2 = this.testSystem.getSpecification().getSystemModule().getRulebaseCalls()[1];
        xfuzzy.lang.RulebaseCall rbc3 =
             this.testSystem.getSpecification().getSystemModule().getRulebaseCalls()[2];

        //special atention to the space before the rulebase name, and the \n in the end
        rbc2ToXfl = "  bb(i0 : i1);" + eol;
        //special atention to the space before the rulebase name, and the \n in the end
        String rbc3ToXfl = "  aa(NULL, i1, NULL, NULL : NULL, NULL);" + eol;

        assertEquals(rbc2ToXfl, rbc2.toXfl());
        assertEquals(rbc3ToXfl, rbc3.toXfl());

    }


    /**
    *This is one of the cases, for the tests of adding a link: A9
    * +rbc1(aa)
    * +rbc2(bb)
    * rbc0(aa).v01 -> rbc2(bb).vi1
    * rbc2(bb).vo1 -> rbc1(bb).vi1
    *
    */
    @Test
    public void ajaxAddLinkA10() {

        //======================== +rbc1(aa) =====================//
        Integer id_rb = 0;
        xfuzzy.lang.Rulebase rb = null;
        try{
            rb = models.RuleBase.getFuzzy(this.testSystem,id_rb);
        } 
        catch(Exception e){
            fail(e.getMessage());
        }

        Result result = callAction(
            controllers.routes.ref.RuleBaseCalls.ajaxAddCall(this.testSystem.id,id_rb),
            fakeRequest().withSession("email", this.testUser.email)
        );
        assertThat(contentAsString(result)).contains("ok");
        try{

            this.testSystem.loadSpecification();
        }
        catch(Exception e){
            fail(e.getMessage());
        }


        xfuzzy.lang.RulebaseCall rbc =
              this.testSystem.getSpecification().getSystemModule().getRulebaseCalls()[1];

        //special atention to the space before the rulebase name, and the \n in the end
        String new_rbc_to_xfl = "  aa(NULL, NULL, NULL, NULL : NULL, NULL);" + eol;
        assertEquals(new_rbc_to_xfl, rbc.toXfl());

        

      //======================== +rbc2(bb) =====================//


      id_rb = 1;
      rb = null;
      try{
          rb = models.RuleBase.getFuzzy(this.testSystem,id_rb);
      } 
      catch(Exception e){
          fail(e.getMessage());
      }

      result = callAction(
          controllers.routes.ref.RuleBaseCalls.ajaxAddCall(this.testSystem.id,id_rb),
          fakeRequest().withSession("email", this.testUser.email)
      );
      assertThat(contentAsString(result)).contains("ok");
      try{

          this.testSystem.loadSpecification();
      }
      catch(Exception e){
          fail(e.getMessage());
      }


      rbc = this.testSystem.getSpecification().getSystemModule().getRulebaseCalls()[2];

      //special atention to the space before the rulebase name, and the \n in the end
      new_rbc_to_xfl = "  bb(NULL : NULL);" + eol;
      assertEquals(new_rbc_to_xfl, rbc.toXfl());
        
        
        
        //======================== rbc0(aa).v01 -> rbc2(bb).vi1 =====================//

        Map<String,String> post_data = new HashMap<String,String>();
        post_data.put("variableDots[1].idRuleBaseCall", "0");  
        post_data.put("variableDots[1].idSysVar", null);  
        post_data.put("variableDots[1].kindSysVar", null);  
        post_data.put("variableDots[1].idBaseVar", "0");  
        post_data.put("variableDots[1].kindBaseVar", String.valueOf(Variable.OUTPUT));  

        post_data.put("variableDots[0].idRuleBaseCall", "2");  
        post_data.put("variableDots[0].idSysVar", null);  
        post_data.put("variableDots[0].kindSysVar", null);  
        post_data.put("variableDots[0].idBaseVar", "0");  
        post_data.put("variableDots[0].kindBaseVar", String.valueOf(Variable.INPUT));  

        result = ajaxAddLinkPostData(post_data);
        assertThat(contentAsString(result)).contains("ok");

        try{

            this.testSystem.loadSpecification();
        }
        catch(Exception e){
            fail(e.getMessage());
        }

        //should be with the link in the given rulebaseCall now
        xfuzzy.lang.RulebaseCall rbc0 =
             this.testSystem.getSpecification().getSystemModule().getRulebaseCalls()[0];
        xfuzzy.lang.RulebaseCall rbc2 =
             this.testSystem.getSpecification().getSystemModule().getRulebaseCalls()[2];

        //special atention to the space before the rulebase name, and the \n in the end
        String rbc0ToXfl = "  aa(NULL, NULL, NULL, NULL : i0, NULL);" + eol;
        //special atention to the space before the rulebase name, and the \n in the end
        String rbc2ToXfl = "  bb(i0 : NULL);" + eol;
        assertEquals(rbc0ToXfl, rbc0.toXfl());
        assertEquals(rbc2ToXfl, rbc2.toXfl());

        //======================== rbc2(bb).vo1 -> rbc1(bb).vi1 =====================//

        post_data = new HashMap<String,String>();
        post_data.put("variableDots[1].idRuleBaseCall", "2");  
        post_data.put("variableDots[1].idSysVar", null);  
        post_data.put("variableDots[1].kindSysVar", null);  
        post_data.put("variableDots[1].idBaseVar", "0");  
        post_data.put("variableDots[1].kindBaseVar", String.valueOf(Variable.OUTPUT));  

        post_data.put("variableDots[0].idRuleBaseCall", "1");  
        post_data.put("variableDots[0].idSysVar", null);  
        post_data.put("variableDots[0].kindSysVar", null);  
        post_data.put("variableDots[0].idBaseVar", "0");  
        post_data.put("variableDots[0].kindBaseVar", String.valueOf(Variable.INPUT));  

        result = ajaxAddLinkPostData(post_data);
        assertThat(contentAsString(result)).contains("ok");

        try{

            this.testSystem.loadSpecification();
        }
        catch(Exception e){
            fail(e.getMessage());
        }

        //should be with the link in the given rulebaseCall now
        //in this case the rulebases should have exchanded(rbc1 with rbc2)
        xfuzzy.lang.RulebaseCall rbc1 = this.testSystem.getSpecification().getSystemModule().getRulebaseCalls()[1];
        rbc2 =
             this.testSystem.getSpecification().getSystemModule().getRulebaseCalls()[2];

        //special atention to the space before the rulebase name, and the \n in the end
        String rbc1ToXfl = "  bb(i0 : i1);" + eol;
        //special atention to the space before the rulebase name, and the \n in the end
        rbc2ToXfl = "  aa(i1, NULL, NULL, NULL : NULL, NULL);" + eol;

        assertEquals(rbc1ToXfl, rbc1.toXfl());
        assertEquals(rbc2ToXfl, rbc2.toXfl());

    }
    
    
    
    /**
    *Helper to be used in the cases for the ajaxRemoveLink,
    *this here only do the call and return the result
    */
    public Result  ajaxRemoveLinkPostData(Map<String,String> post_data){

        Result result = callAction(
            controllers.routes.ref.RuleBaseCalls.ajaxRemoveLink(this.testSystem.id),
            fakeRequest().withSession("email", this.testUser.email)
            .withFormUrlEncodedBody(post_data)
        );
        return result;
    }

    /**
    *This is one of the cases, for the tests of removing a link: B1
    * vgi2 -> rbc0(aa).vi3
    * vgi2 -/- rbc0(aa).vi3
    */
    @Test
    public void ajaxRemoveLinkB1() {

        //======================== vgi2 -> rbc0(aa).vi3 =====================//
        Map<String,String> post_data = new HashMap<String,String>();
        post_data.put("variableDots[0].idRuleBaseCall", null);  
        post_data.put("variableDots[0].idSysVar", "1");  
        post_data.put("variableDots[0].kindSysVar", String.valueOf(Variable.INPUT));  
        post_data.put("variableDots[0].idBaseVar", null);  
        post_data.put("variableDots[0].kindBaseVar", null);  

        post_data.put("variableDots[1].idRuleBaseCall", "0");  
        post_data.put("variableDots[1].idSysVar", null);  
        post_data.put("variableDots[1].kindSysVar", null);  
        post_data.put("variableDots[1].idBaseVar", "2");  
        post_data.put("variableDots[1].kindBaseVar", String.valueOf(Variable.INPUT));  

        Result result = ajaxAddLinkPostData(post_data);
        assertThat(contentAsString(result)).contains("ok");

        try{

            this.testSystem.loadSpecification();
        }
        catch(Exception e){
            fail(e.getMessage());
        }


        //should be with the link in the given rulebaseCall now
        xfuzzy.lang.RulebaseCall rbc =
              this.testSystem.getSpecification().getSystemModule().getRulebaseCalls()[0];

        //special atention to the space before the rulebase name, and the \n in the end
        String newLinkToRBCToXfl = "  aa(NULL, NULL, vgi2, NULL : NULL, NULL);" + eol;
        assertEquals(newLinkToRBCToXfl, rbc.toXfl());


        // //======================== vgi2 -/- rbc0(aa).vi3 =====================//


        result = ajaxRemoveLinkPostData(post_data);
        assertThat(contentAsString(result)).contains("ok");

        try{

            this.testSystem.loadSpecification();
        }
        catch(Exception e){
            fail(e.getMessage());
        }


        //should be with the link in the given rulebaseCall now
        rbc = this.testSystem.getSpecification().getSystemModule().getRulebaseCalls()[0];

        //special atention to the space before the rulebase name, and the \n in the end
        newLinkToRBCToXfl = "  aa(NULL, NULL, NULL, NULL : NULL, NULL);" + eol;
        assertEquals(newLinkToRBCToXfl, rbc.toXfl());

    }

    /**
    *This is one of the cases, for the tests of removing a link: B2
    * vgi2 -> rbc0(aa).vi3
    * +rbc1(bb)
    * rbc1(bb).vi1 ->  rbc0(aa).vo2 
    * rbc1(bb).vi1 -/-  rbc0(aa).vo2 
    */
    @Test
    public void ajaxRemoveLinkB2() {

        //======================== vgi2 -> rbc0(aa).vi3 =====================//
        Map<String,String> post_data = new HashMap<String,String>();
        post_data.put("variableDots[0].idRuleBaseCall", null);  
        post_data.put("variableDots[0].idSysVar", "1");  
        post_data.put("variableDots[0].kindSysVar", String.valueOf(Variable.INPUT));  
        post_data.put("variableDots[0].idBaseVar", null);  
        post_data.put("variableDots[0].kindBaseVar", null);  

        post_data.put("variableDots[1].idRuleBaseCall", "0");  
        post_data.put("variableDots[1].idSysVar", null);  
        post_data.put("variableDots[1].kindSysVar", null);  
        post_data.put("variableDots[1].idBaseVar", "2");  
        post_data.put("variableDots[1].kindBaseVar", String.valueOf(Variable.INPUT));  

        Result result = ajaxAddLinkPostData(post_data);
        assertThat(contentAsString(result)).contains("ok");

        try{

            this.testSystem.loadSpecification();
        }
        catch(Exception e){
            fail(e.getMessage());
        }


        //should be with the link in the given rulebaseCall now
        xfuzzy.lang.RulebaseCall rbc =
              this.testSystem.getSpecification().getSystemModule().getRulebaseCalls()[0];

        //special atention to the space before the rulebase name, and the \n in the end
        String newLinkToRBCToXfl = "  aa(NULL, NULL, vgi2, NULL : NULL, NULL);" + eol;
        assertEquals(newLinkToRBCToXfl, rbc.toXfl());









        //======================== +rbc1(bb) =====================//
        Integer id_rb = 1;
        xfuzzy.lang.Rulebase rb = null;
        try{
            rb = models.RuleBase.getFuzzy(this.testSystem,id_rb);
        } 
        catch(Exception e){
            fail(e.getMessage());
        }

        result = callAction(
            controllers.routes.ref.RuleBaseCalls.ajaxAddCall(this.testSystem.id,id_rb),
            fakeRequest().withSession("email", this.testUser.email)
        );
        assertThat(contentAsString(result)).contains("ok");
        try{

            this.testSystem.loadSpecification();
        }
        catch(Exception e){
            fail(e.getMessage());
        }


        rbc =this.testSystem.getSpecification().getSystemModule().getRulebaseCalls()[1];

        //special atention to the space before the rulebase name, and the \n in the end
        String new_rbc_to_xfl = "  bb(NULL : NULL);" + eol;
        assertEquals(new_rbc_to_xfl, rbc.toXfl());

        //======================== rbc0(aa).vo2 -> rbc1(bb).vi1 =====================//

        post_data = new HashMap<String,String>();
        post_data.put("variableDots[1].idRuleBaseCall", "0");  
        post_data.put("variableDots[1].idSysVar", null);  
        post_data.put("variableDots[1].kindSysVar", null);  
        post_data.put("variableDots[1].idBaseVar", "1");  
        post_data.put("variableDots[1].kindBaseVar", String.valueOf(Variable.OUTPUT));  

        post_data.put("variableDots[0].idRuleBaseCall", "1");  
        post_data.put("variableDots[0].idSysVar", null);  
        post_data.put("variableDots[0].kindSysVar", null);  
        post_data.put("variableDots[0].idBaseVar", "0");  
        post_data.put("variableDots[0].kindBaseVar", String.valueOf(Variable.INPUT));  

        result = ajaxAddLinkPostData(post_data);
        assertThat(contentAsString(result)).contains("ok");

        try{

            this.testSystem.loadSpecification();
        }
        catch(Exception e){
            fail(e.getMessage());
        }

        //should be with the link in the given rulebaseCall now
        xfuzzy.lang.RulebaseCall rbc1 =
             this.testSystem.getSpecification().getSystemModule().getRulebaseCalls()[0];
        xfuzzy.lang.RulebaseCall rbc2 =
             this.testSystem.getSpecification().getSystemModule().getRulebaseCalls()[1];

        //special atention to the space before the rulebase name, and the \n in the end
        String rbc1ToXfl = "  aa(NULL, NULL, vgi2, NULL : NULL, i0);" + eol;
        //special atention to the space before the rulebase name, and the \n in the end
        String rbc2ToXfl = "  bb(i0 : NULL);" + eol;
        assertEquals(rbc1ToXfl, rbc1.toXfl());
        assertEquals(rbc2ToXfl, rbc2.toXfl());

        //======================== rbc1(bb).vi1 -/-  rbc0(aa).vo2  =====================//


        result = ajaxRemoveLinkPostData(post_data);
        assertThat(contentAsString(result)).contains("ok");

        try{

            this.testSystem.loadSpecification();
        }
        catch(Exception e){
            fail(e.getMessage());
        }


        //should be with the link in the given rulebaseCall now
        rbc1 = this.testSystem.getSpecification().getSystemModule().getRulebaseCalls()[0];

        //should be with the link in the given rulebaseCall now
        rbc2 = this.testSystem.getSpecification().getSystemModule().getRulebaseCalls()[1];

        //special atention to the space before the rulebase name, and the \n in the end
        rbc1ToXfl = "  aa(NULL, NULL, vgi2, NULL : NULL, NULL);" + eol;
        rbc2ToXfl = "  bb(NULL : NULL);" + eol;
        assertEquals(rbc1ToXfl, rbc1.toXfl());
        assertEquals(rbc2ToXfl, rbc2.toXfl());

    }
}