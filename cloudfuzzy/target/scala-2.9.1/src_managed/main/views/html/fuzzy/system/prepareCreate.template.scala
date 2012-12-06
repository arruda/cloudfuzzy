
package views.html.fuzzy.system

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
object prepareCreate extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template1[Form[FuzzySystem],play.api.templates.Html] {

    /**/
    def apply/*1.2*/(systemForm: Form[FuzzySystem]):play.api.templates.Html = {
        _display_ {import helper._

def /*5.2*/navHeader/*5.11*/:play.api.templates.Html = {_display_(

Seq[Any](format.raw/*5.15*/("""
    My Fuzzy Systems
""")))};
Seq[Any](format.raw/*1.33*/("""

"""),format.raw/*4.1*/("""
"""),format.raw/*7.2*/("""  

"""),_display_(Seq[Any](/*9.2*/main("My Fuzzy Systems")(navHeader)(menu_default())/*9.53*/{_display_(Seq[Any](format.raw/*9.54*/(""" 
    <div class="sub_content sub_limited">
        <h3 class='sub_content_header'>New Fuzzy System</h3> 
            """),_display_(Seq[Any](/*12.14*/helper/*12.20*/.form(action = routes.Systems.create, 'id -> "systemForm")/*12.78*/ {_display_(Seq[Any](format.raw/*12.80*/("""
                
                <fieldset>
                    <legend>New System Information</legend>
                    
                    """),_display_(Seq[Any](/*17.22*/inputText(
                        systemForm("name"), 
                        '_label -> "Name", 
                        '_help -> "Please choose a valid Name for this Fuzzy system."
                    ))),format.raw/*21.22*/("""          
                    """),_display_(Seq[Any](/*22.22*/inputText(
                        systemForm("description"), 
                        '_label -> "Description", 
                        '_help -> "Descrive in feel words whats is this Fuzzy System about."
                    ))),format.raw/*26.22*/("""              

                </fieldset>        
                
                <div class="actions">
                    <input type="submit" class="btn primary" value="Create">
                    <input type="button" class="btn secondary" value="Cancel"onclick="window.location = '"""),_display_(Seq[Any](/*32.107*/routes/*32.113*/.Systems.list())),format.raw/*32.128*/("""' "/>
                </div>
                
            """)))})),format.raw/*35.14*/("""
 
    </div>
    
""")))})))}
    }
    
    def render(systemForm:Form[FuzzySystem]) = apply(systemForm)
    
    def f:((Form[FuzzySystem]) => play.api.templates.Html) = (systemForm) => apply(systemForm)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Mon Nov 26 13:28:54 BRST 2012
                    SOURCE: /home/arruda/projetos/cloudfuzzy/cloudfuzzy/app/views/fuzzy/system/prepareCreate.scala.html
                    HASH: 41071ad59c9d4393b4229603e94f75866afb63be
                    MATRIX: 787->1|894->53|911->62|978->66|1040->32|1068->51|1095->89|1134->94|1193->145|1231->146|1386->265|1401->271|1468->329|1508->331|1691->478|1920->685|1988->717|2238->945|2565->1235|2581->1241|2619->1256|2710->1315
                    LINES: 27->1|30->5|30->5|32->5|35->1|37->4|38->7|40->9|40->9|40->9|43->12|43->12|43->12|43->12|48->17|52->21|53->22|57->26|63->32|63->32|63->32|66->35
                    -- GENERATED --
                */
            