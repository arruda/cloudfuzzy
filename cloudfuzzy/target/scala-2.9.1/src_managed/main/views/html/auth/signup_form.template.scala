
package views.html.auth

import play.templates._
import play.templates.TemplateMagic._

import play.api.templates._
import play.api.templates.PlayMagic._
import models._
import controllers._
import java.lang._
import java.util._
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import play.api.i18n._
import play.api.templates.PlayMagicForJava._
import play.mvc._
import play.data._
import play.api.data.Field
import com.avaje.ebean._
import play.mvc.Http.Context.Implicit._
import views.html._
/**/
object signup_form extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template1[Form[User],play.api.templates.Html] {

    /**/
    def apply/*1.2*/(signupForm: Form[User]):play.api.templates.Html = {
        _display_ {import helper._

def /*6.2*/navHeader/*6.11*/:play.api.templates.Html = {_display_(

Seq[Any](format.raw/*6.15*/("""
    Register
""")))};
Seq[Any](format.raw/*1.26*/("""

"""),format.raw/*4.1*/("""
  
"""),format.raw/*8.2*/("""  

"""),_display_(Seq[Any](/*10.2*/main("Register")(navHeader)(menu_default())/*10.45*/ {_display_(Seq[Any](format.raw/*10.47*/("""

    """),_display_(Seq[Any](/*12.6*/helper/*12.12*/.form(action = routes.Authentication.submit, 'id -> "registerForm")/*12.79*/ {_display_(Seq[Any](format.raw/*12.81*/("""
        
        <fieldset>
            <legend>Account informations</legend>
            
            """),_display_(Seq[Any](/*17.14*/inputText(
                signupForm("name"), 
                '_label -> "Name", 
                '_help -> "Please choose a valid Name.",
                '_error -> signupForm.globalError
            ))),format.raw/*22.14*/("""
            
            """),_display_(Seq[Any](/*24.14*/inputText(
                signupForm("email"), '_label -> "Email",
                '_help -> "Enter a valid email address."
            ))),format.raw/*27.14*/("""
            
            """),_display_(Seq[Any](/*29.14*/inputPassword(
                signupForm("password"), 
                '_label -> "Password",
                '_help -> "A password must be at least 6 characters. "
            ))),format.raw/*33.14*/("""
            
            """),_display_(Seq[Any](/*35.14*/inputPassword(
                signupForm("repeatPassword"), 
                '_label -> "Repeat password",
                '_help -> "Please repeat your password again.",
                '_error -> signupForm.error("password")
            ))),format.raw/*40.14*/("""
            
        </fieldset>
        
        <fieldset>
            
            """),_display_(Seq[Any](/*46.14*/checkbox(
                signupForm("accept"), 
                '_label -> None, '_text -> "You aggree the Terms and conditions",
                '_showConstraints -> false
            ))),format.raw/*50.14*/("""
            
        </fieldset>
        
        <div class="actions">
            <input type="submit" class="btn primary" value="Sign Up">
            <a href=""""),_display_(Seq[Any](/*56.23*/routes/*56.29*/.Application.index)),format.raw/*56.47*/("""" class="btn">Cancel</a>
        </div>
        
    """)))})),format.raw/*59.6*/("""
    
""")))})))}
    }
    
    def render(signupForm:Form[User]) = apply(signupForm)
    
    def f:((Form[User]) => play.api.templates.Html) = (signupForm) => apply(signupForm)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Tue Nov 06 10:18:05 BRST 2012
                    SOURCE: /home/arruda/projetos/cloudfuzzy/cloudfuzzy/app/views/auth/signup_form.scala.html
                    HASH: 3be4854d2ac22d083bcb04bccc848802fcd487cf
                    MATRIX: 770->1|870->49|887->58|954->62|1008->25|1036->44|1066->77|1106->82|1158->125|1198->127|1240->134|1255->140|1331->207|1371->209|1512->314|1738->518|1801->545|1961->683|2024->710|2225->889|2288->916|2551->1157|2675->1245|2884->1432|3085->1597|3100->1603|3140->1621|3225->1675
                    LINES: 27->1|30->6|30->6|32->6|35->1|37->4|39->8|41->10|41->10|41->10|43->12|43->12|43->12|43->12|48->17|53->22|55->24|58->27|60->29|64->33|66->35|71->40|77->46|81->50|87->56|87->56|87->56|90->59
                    -- GENERATED --
                */
            