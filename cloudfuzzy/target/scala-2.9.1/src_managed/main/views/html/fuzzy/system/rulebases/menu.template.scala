
package views.html.fuzzy.system.rulebases

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
object menu extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template2[FuzzySystem,models.RuleBase,play.api.templates.Html] {

    /**/
    def apply/*1.2*/(fuzzySystem : FuzzySystem, rb : models.RuleBase):play.api.templates.Html = {
        _display_ {

Seq[Any](format.raw/*1.51*/("""
"""),format.raw/*4.5*/("""

<li ><a class='left_menu' href=""""),_display_(Seq[Any](/*6.34*/routes/*6.40*/.Systems.detail(fuzzySystem.id))),format.raw/*6.71*/("""">Back</a></li>
<li><a href="#">Edit</a></li>
<li ><a  href="#">Delete</a></li>"""))}
    }
    
    def render(fuzzySystem:FuzzySystem,rb:models.RuleBase) = apply(fuzzySystem,rb)
    
    def f:((FuzzySystem,models.RuleBase) => play.api.templates.Html) = (fuzzySystem,rb) => apply(fuzzySystem,rb)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Mon Nov 26 13:28:54 BRST 2012
                    SOURCE: /home/arruda/projetos/cloudfuzzy/cloudfuzzy/app/views/fuzzy/system/rulebases/menu.scala.html
                    HASH: fe6795c37dc76cd9086e5dedcc551aa862637fa5
                    MATRIX: 798->1|924->50|951->116|1021->151|1035->157|1087->188
                    LINES: 27->1|30->1|31->4|33->6|33->6|33->6
                    -- GENERATED --
                */
            