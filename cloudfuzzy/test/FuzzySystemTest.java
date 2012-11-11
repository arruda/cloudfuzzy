package test;

import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.libs.F.*;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

//use with application test
import testhelper.WithApplication;

public class FuzzySystemTest  extends WithApplication {

    @Before
    public void setUp() {
        start(fakeApplication(inMemoryDatabase()));
    }
    
    @Test
    public void callIndex() {
        Result result = callAction(
          controllers.routes.ref.Systems.list()
        );
        // assertThat(status(result)).isEqualTo(OK);
        // assertThat(contentType(result)).isEqualTo("text/html");
        // assertThat(charset(result)).isEqualTo("utf-8");
        // assertThat(contentAsString(result)).contains("Hello Kiki");
    }

}