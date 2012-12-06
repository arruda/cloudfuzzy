
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
object operatorset_template extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template3[FuzzySystem,models.OperatorSet,Html,play.api.templates.Html] {

    /**/
    def apply/*1.2*/(fuzzySystem : FuzzySystem, opset : models.OperatorSet)(content: Html):play.api.templates.Html = {
        _display_ {
def /*7.2*/navHeader/*7.11*/:play.api.templates.Html = {_display_(

Seq[Any](format.raw/*7.15*/("""
    <a href=""""),_display_(Seq[Any](/*8.15*/routes/*8.21*/.Systems.detail(fuzzySystem.id))),format.raw/*8.52*/("""">"""),_display_(Seq[Any](/*8.55*/fuzzySystem/*8.66*/.name)),format.raw/*8.71*/("""</a>>
    """),_display_(Seq[Any](/*9.6*/opset/*9.11*/.name)),format.raw/*9.16*/("""
""")))};
Seq[Any](format.raw/*1.72*/("""
"""),format.raw/*4.5*/("""


"""),format.raw/*10.2*/("""  

"""),_display_(Seq[Any](/*12.2*/main("OperatorSet")(navHeader)(fuzzy.system.operatorsets.menu(fuzzySystem,opset))(content))))}
    }
    
    def render(fuzzySystem:FuzzySystem,opset:models.OperatorSet,content:Html) = apply(fuzzySystem,opset)(content)
    
    def f:((FuzzySystem,models.OperatorSet) => (Html) => play.api.templates.Html) = (fuzzySystem,opset) => (content) => apply(fuzzySystem,opset)(content)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Tue Nov 06 10:18:05 BRST 2012
                    SOURCE: /home/arruda/projetos/cloudfuzzy/cloudfuzzy/app/views/fuzzy/system/operatorsets/operatorset_template.scala.html
                    HASH: 3d3dd94ab654a42e28f5f8d35858e96785f7b915
                    MATRIX: 825->1|955->160|972->169|1039->173|1089->188|1103->194|1155->225|1193->228|1212->239|1238->244|1283->255|1296->260|1322->265|1363->71|1390->156|1420->267|1460->272
                    LINES: 27->1|29->7|29->7|31->7|32->8|32->8|32->8|32->8|32->8|32->8|33->9|33->9|33->9|35->1|36->4|39->10|41->12
                    -- GENERATED --
                */
            