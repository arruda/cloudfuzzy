
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
object rulebase_template extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template3[FuzzySystem,models.RuleBase,Html,play.api.templates.Html] {

    /**/
    def apply/*1.2*/(fuzzySystem : FuzzySystem, rb : models.RuleBase)(content: Html):play.api.templates.Html = {
        _display_ {
def /*7.2*/navHeader/*7.11*/:play.api.templates.Html = {_display_(

Seq[Any](format.raw/*7.15*/("""
    <a href=""""),_display_(Seq[Any](/*8.15*/routes/*8.21*/.Systems.detail(fuzzySystem.id))),format.raw/*8.52*/("""">"""),_display_(Seq[Any](/*8.55*/fuzzySystem/*8.66*/.name)),format.raw/*8.71*/("""</a>>
    """),_display_(Seq[Any](/*9.6*/rb/*9.8*/.name)),format.raw/*9.13*/("""
""")))};
Seq[Any](format.raw/*1.66*/("""
"""),format.raw/*4.5*/("""


"""),format.raw/*10.2*/("""  

"""),_display_(Seq[Any](/*12.2*/main("Rule Base")(navHeader)(fuzzy.system.rulebases.menu(fuzzySystem,rb))(content))))}
    }
    
    def render(fuzzySystem:FuzzySystem,rb:models.RuleBase,content:Html) = apply(fuzzySystem,rb)(content)
    
    def f:((FuzzySystem,models.RuleBase) => (Html) => play.api.templates.Html) = (fuzzySystem,rb) => (content) => apply(fuzzySystem,rb)(content)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Mon Nov 26 13:28:54 BRST 2012
                    SOURCE: /home/arruda/projetos/cloudfuzzy/cloudfuzzy/app/views/fuzzy/system/rulebases/rulebase_template.scala.html
                    HASH: bae06e241fb42f6bc2fa0952983c99aadc9aaf47
                    MATRIX: 816->1|940->151|957->160|1024->164|1074->179|1088->185|1140->216|1178->219|1197->230|1223->235|1268->246|1277->248|1303->253|1344->65|1371->147|1401->255|1441->260
                    LINES: 27->1|29->7|29->7|31->7|32->8|32->8|32->8|32->8|32->8|32->8|33->9|33->9|33->9|35->1|36->4|39->10|41->12
                    -- GENERATED --
                */
            