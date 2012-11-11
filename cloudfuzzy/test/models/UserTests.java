package models;

import models.*;

import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.libs.F.*;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;
import static org.junit.Assert.assertEquals;

//use with application test
import testhelper.WithApplication;

public class UserTests  extends WithApplication {

    @Before
    public void setUp() {
        start(fakeApplication(inMemoryDatabase()));
    }

    @Test
    public void createAndRetrieveUser() {
        User newUser = new User();
        newUser.email = "bob@gmail.com";
        newUser.name = "Bob";
        newUser.password = "secret";
        newUser.save();

        User bob = User.find.where().eq("email", "bob@gmail.com").findUnique();
        // assertThat(bob).isEqualsTo();
        assertEquals("Bob", bob.name);
    }    
}