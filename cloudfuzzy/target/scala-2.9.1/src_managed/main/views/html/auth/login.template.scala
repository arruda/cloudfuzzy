
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
object login extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template1[Form[Authentication.Login],play.api.templates.Html] {

    /**/
    def apply/*1.2*/(loginForm: Form[Authentication.Login]):play.api.templates.Html = {
        _display_ {import helper._

def /*5.2*/navHeader/*5.11*/:play.api.templates.Html = {_display_(

Seq[Any](format.raw/*5.15*/("""
    Login
""")))};
Seq[Any](format.raw/*1.41*/("""

"""),format.raw/*4.1*/("""
"""),format.raw/*7.2*/("""  

"""),_display_(Seq[Any](/*9.2*/main("Login")(navHeader)(menu_default())/*9.42*/{_display_(Seq[Any](format.raw/*9.43*/("""
    """),format.raw/*12.9*/("""


    """),_display_(Seq[Any](/*15.6*/helper/*15.12*/.form(action = routes.Authentication.authenticate, 'id -> "login_form")/*15.83*/ {_display_(Seq[Any](format.raw/*15.85*/("""
        
                
        <legend>Sign in <small>or <a href=""""),_display_(Seq[Any](/*18.45*/routes/*18.51*/.Authentication.register)),format.raw/*18.75*/("""">register</a>.</small></legend>
        
        """),_display_(Seq[Any](/*20.10*/if(loginForm.hasGlobalErrors)/*20.39*/ {_display_(Seq[Any](format.raw/*20.41*/(""" 
        

            <div class="clearfix  error" id="global_erros">
            
                <div class="input">
                    <span class="help-inline">"""),_display_(Seq[Any](/*26.48*/loginForm/*26.57*/.globalError.message)),format.raw/*26.77*/("""</span>
                </div>
            </div>
        """)))})),format.raw/*29.10*/("""
        
        
        
        """),_display_(Seq[Any](/*33.10*/inputText(
            loginForm("email"), 
            '_label -> "Email", 
            '_help -> "Enter your email."
        ))),format.raw/*37.10*/("""
        
        """),_display_(Seq[Any](/*39.10*/inputPassword(
            loginForm("password"), 
            '_label -> "Password", 
            '_help -> "Enter your password."
        ))),format.raw/*43.10*/("""
        
        
        <button id="login_post">Login</button>  
        
    """)))})),format.raw/*48.6*/("""        
        
""")))})),format.raw/*50.2*/("""
"""))}
    }
    
    def render(loginForm:Form[Authentication.Login]) = apply(loginForm)
    
    def f:((Form[Authentication.Login]) => play.api.templates.Html) = (loginForm) => apply(loginForm)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Tue Nov 06 10:18:05 BRST 2012
                    SOURCE: /home/arruda/projetos/cloudfuzzy/cloudfuzzy/app/views/auth/login.scala.html
                    HASH: 60fb9574492f68e44bf481db4fcf67902df77811
                    MATRIX: 780->1|895->61|912->70|979->74|1030->40|1058->59|1085->86|1124->91|1172->131|1210->132|1242->177|1285->185|1300->191|1380->262|1420->264|1527->335|1542->341|1588->365|1675->416|1713->445|1753->447|1957->615|1975->624|2017->644|2108->703|2181->740|2331->868|2386->887|2549->1028|2662->1110|2712->1129
                    LINES: 27->1|30->5|30->5|32->5|35->1|37->4|38->7|40->9|40->9|40->9|41->12|44->15|44->15|44->15|44->15|47->18|47->18|47->18|49->20|49->20|49->20|55->26|55->26|55->26|58->29|62->33|66->37|68->39|72->43|77->48|79->50
                    -- GENERATED --
                */
            