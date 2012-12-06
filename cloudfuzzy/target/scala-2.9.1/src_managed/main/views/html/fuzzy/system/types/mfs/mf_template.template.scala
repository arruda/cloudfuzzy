
package views.html.fuzzy.system.types.mfs

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
object mf_template extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template4[FuzzySystem,models.Type,models.MF,Html,play.api.templates.Html] {

    /**/
    def apply/*1.2*/(fuzzySystem : FuzzySystem, tp : models.Type, mf : models.MF)(content: Html):play.api.templates.Html = {
        _display_ {
def /*7.2*/navHeader/*7.11*/:play.api.templates.Html = {_display_(

Seq[Any](format.raw/*7.15*/("""
    <a href=""""),_display_(Seq[Any](/*8.15*/routes/*8.21*/.Systems.detail(fuzzySystem.id))),format.raw/*8.52*/("""">"""),_display_(Seq[Any](/*8.55*/fuzzySystem/*8.66*/.name)),format.raw/*8.71*/("""</a>>
    <a href=""""),_display_(Seq[Any](/*9.15*/routes/*9.21*/.Types.detail(fuzzySystem.id, tp.id))),format.raw/*9.57*/("""">"""),_display_(Seq[Any](/*9.60*/tp/*9.62*/.name)),format.raw/*9.67*/("""</a>>
    """),_display_(Seq[Any](/*10.6*/mf/*10.8*/.label)),format.raw/*10.14*/("""
""")))};
Seq[Any](format.raw/*1.78*/("""
"""),format.raw/*4.5*/("""


"""),format.raw/*11.2*/("""  

"""),_display_(Seq[Any](/*13.2*/main("Type")(navHeader)(fuzzy.system.types.mfs.menu(fuzzySystem,tp,mf))(content))))}
    }
    
    def render(fuzzySystem:FuzzySystem,tp:models.Type,mf:models.MF,content:Html) = apply(fuzzySystem,tp,mf)(content)
    
    def f:((FuzzySystem,models.Type,models.MF) => (Html) => play.api.templates.Html) = (fuzzySystem,tp,mf) => (content) => apply(fuzzySystem,tp,mf)(content)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Tue Nov 06 10:18:05 BRST 2012
                    SOURCE: /home/arruda/projetos/cloudfuzzy/cloudfuzzy/app/views/fuzzy/system/types/mfs/mf_template.scala.html
                    HASH: d93cc039fe8ea004e189b1ae43e88368ce503c7f
                    MATRIX: 816->1|952->157|969->166|1036->170|1086->185|1100->191|1152->222|1190->225|1209->236|1235->241|1290->261|1304->267|1361->303|1399->306|1409->308|1435->313|1481->324|1491->326|1519->332|1560->77|1587->153|1617->334|1657->339
                    LINES: 27->1|29->7|29->7|31->7|32->8|32->8|32->8|32->8|32->8|32->8|33->9|33->9|33->9|33->9|33->9|33->9|34->10|34->10|34->10|36->1|37->4|40->11|42->13
                    -- GENERATED --
                */
            