
package views.html.fuzzy.system.operatorsets

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
object menu extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template2[FuzzySystem,models.OperatorSet,play.api.templates.Html] {

    /**/
    def apply/*1.2*/(fuzzySystem : FuzzySystem, opset : models.OperatorSet):play.api.templates.Html = {
        _display_ {

Seq[Any](format.raw/*1.57*/("""
"""),format.raw/*4.5*/("""

<li ><a class='left_menu' href=""""),_display_(Seq[Any](/*6.34*/routes/*6.40*/.Systems.detail(fuzzySystem.id))),format.raw/*6.71*/("""">Back</a></li>
<li><a href="#">Edit</a></li>
<li ><a  href=""""),_display_(Seq[Any](/*8.17*/routes/*8.23*/.OperatorSets.delete(fuzzySystem.id,opset.id))),format.raw/*8.68*/("""">Delete</a></li>"""))}
    }
    
    def render(fuzzySystem:FuzzySystem,opset:models.OperatorSet) = apply(fuzzySystem,opset)
    
    def f:((FuzzySystem,models.OperatorSet) => play.api.templates.Html) = (fuzzySystem,opset) => apply(fuzzySystem,opset)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Tue Nov 06 10:18:05 BRST 2012
                    SOURCE: /home/arruda/projetos/cloudfuzzy/cloudfuzzy/app/views/fuzzy/system/operatorsets/menu.scala.html
                    HASH: e1bdd043bd0d917bc7937c69a72944809631b54a
                    MATRIX: 804->1|936->56|963->125|1033->160|1047->166|1099->197|1196->259|1210->265|1276->310
                    LINES: 27->1|30->1|31->4|33->6|33->6|33->6|35->8|35->8|35->8
                    -- GENERATED --
                */
            