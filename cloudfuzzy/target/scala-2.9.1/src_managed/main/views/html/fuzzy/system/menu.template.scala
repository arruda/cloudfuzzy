
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
object menu extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template1[FuzzySystem,play.api.templates.Html] {

    /**/
    def apply/*1.2*/(fuzzySystem : FuzzySystem):play.api.templates.Html = {
        _display_ {

Seq[Any](format.raw/*1.29*/("""
"""),format.raw/*4.5*/("""

<li ><a class='left_menu' href=""""),_display_(Seq[Any](/*6.34*/routes/*6.40*/.Systems.list())),format.raw/*6.55*/("""">Back</a></li>
<li><a href="#">Verification</a></li>
<li><a href="#">Synthesis</a></li>
<li ><a class='right_menu' href=""""),_display_(Seq[Any](/*9.35*/routes/*9.41*/.Systems.print(fuzzySystem.id))),format.raw/*9.71*/("""">XFL</a></li>"""))}
    }
    
    def render(fuzzySystem:FuzzySystem) = apply(fuzzySystem)
    
    def f:((FuzzySystem) => play.api.templates.Html) = (fuzzySystem) => apply(fuzzySystem)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Tue Nov 06 10:18:05 BRST 2012
                    SOURCE: /home/arruda/projetos/cloudfuzzy/cloudfuzzy/app/views/fuzzy/system/menu.scala.html
                    HASH: a2370ded0540aae43b4b297b019814b7656ff20d
                    MATRIX: 772->1|876->28|903->92|973->127|987->133|1023->148|1181->271|1195->277|1246->307
                    LINES: 27->1|30->1|31->4|33->6|33->6|33->6|36->9|36->9|36->9
                    -- GENERATED --
                */
            