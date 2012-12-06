
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
object login_form extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template1[Form[Authentication.Login],play.api.templates.Html] {

    /**/
    def apply/*1.2*/(loginForm: Form[Authentication.Login]):play.api.templates.Html = {
        _display_ {import helper._


Seq[Any](format.raw/*1.41*/("""

"""),format.raw/*4.1*/("""

"""),_display_(Seq[Any](/*6.2*/helper/*6.8*/.form(routes.Authentication.authenticate)/*6.49*/ {_display_(Seq[Any](format.raw/*6.51*/("""
    
            
    <legend>Sign in <small>or <a href=""""),_display_(Seq[Any](/*9.41*/routes/*9.47*/.Authentication.register)),format.raw/*9.71*/("""">register</a>.</small></legend>
    
    """),_display_(Seq[Any](/*11.6*/if(loginForm.hasGlobalErrors)/*11.35*/ {_display_(Seq[Any](format.raw/*11.37*/(""" 
    

        <div class="clearfix  error" id="global_erros">
        
            <div class="input">
                <span class="help-inline">"""),_display_(Seq[Any](/*17.44*/loginForm/*17.53*/.globalError.message)),format.raw/*17.73*/("""</span>
            </div>
        </div>
    """)))})),format.raw/*20.6*/("""
    
    
    
    """),_display_(Seq[Any](/*24.6*/inputText(
        loginForm("email"), 
        '_label -> "Email", 
        '_help -> "Enter your email."
    ))),format.raw/*28.6*/("""
    
    """),_display_(Seq[Any](/*30.6*/inputPassword(
        loginForm("password"), 
        '_label -> "Password", 
        '_help -> "Enter your password."
    ))),format.raw/*34.6*/("""
    
    
    <div class="actions">
        <input type="submit" class="btn primary" value="Login">
    </div>
    
""")))})))}
    }
    
    def render(loginForm:Form[Authentication.Login]) = apply(loginForm)
    
    def f:((Form[Authentication.Login]) => play.api.templates.Html) = (loginForm) => apply(loginForm)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Tue Oct 30 10:43:50 BRST 2012
                    SOURCE: /home/arruda/projetos/cloudfuzzy/cloudfuzzy/app/views/auth/login_form.scala.html
                    HASH: cba6787306bfb267fae4d54f33b700d59a3e8267
                    MATRIX: 785->1|917->40|945->59|982->62|995->68|1044->109|1083->111|1177->170|1191->176|1236->200|1314->243|1352->272|1392->274|1576->422|1594->431|1636->451|1714->498|1770->519|1903->631|1949->642|2095->767
                    LINES: 27->1|31->1|33->4|35->6|35->6|35->6|35->6|38->9|38->9|38->9|40->11|40->11|40->11|46->17|46->17|46->17|49->20|53->24|57->28|59->30|63->34
                    -- GENERATED --
                */
            