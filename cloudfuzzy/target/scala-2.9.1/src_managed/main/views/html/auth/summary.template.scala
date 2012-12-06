
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
object summary extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template1[User,play.api.templates.Html] {

    /**/
    def apply/*1.2*/(user: User):play.api.templates.Html = {
        _display_ {
def /*3.2*/navHeader/*3.11*/:play.api.templates.Html = {_display_(

Seq[Any](format.raw/*3.15*/("""
    Register
""")))};
Seq[Any](format.raw/*1.14*/("""

"""),format.raw/*5.2*/("""  


"""),_display_(Seq[Any](/*8.2*/main("Account created!")(navHeader)(menu_default())/*8.53*/ {_display_(Seq[Any](format.raw/*8.55*/("""
    
    <h2>Your account:</h2>
    
    <h6>Name</h6>
    
    <p>
        """),_display_(Seq[Any](/*15.10*/user/*15.14*/.name)),format.raw/*15.19*/("""
    </p>
    
    <h6>Email</h6>
    
    <p>
        """),_display_(Seq[Any](/*21.10*/user/*21.14*/.email)),format.raw/*21.20*/("""
    </p>
    
    <h6>Password</h6>
    
    <p>
        """),_display_(Seq[Any](/*27.10*/user/*27.14*/.password)),format.raw/*27.23*/("""
    </p>
    
""")))})))}
    }
    
    def render(user:User) = apply(user)
    
    def f:((User) => play.api.templates.Html) = (user) => apply(user)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Tue Nov 06 10:18:05 BRST 2012
                    SOURCE: /home/arruda/projetos/cloudfuzzy/cloudfuzzy/app/views/auth/summary.scala.html
                    HASH: 5949bbb3155aa47b095830bf80f5e12a97c5160b
                    MATRIX: 760->1|832->16|849->25|916->29|970->13|998->44|1038->50|1097->101|1136->103|1250->181|1263->185|1290->190|1382->246|1395->250|1423->256|1518->315|1531->319|1562->328
                    LINES: 27->1|29->3|29->3|31->3|34->1|36->5|39->8|39->8|39->8|46->15|46->15|46->15|52->21|52->21|52->21|58->27|58->27|58->27
                    -- GENERATED --
                */
            