
package views.html.fuzzy.system.types

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
object type_template extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template3[FuzzySystem,models.Type,Html,play.api.templates.Html] {

    /**/
    def apply/*1.2*/(fuzzySystem : FuzzySystem, tp : models.Type)(content: Html):play.api.templates.Html = {
        _display_ {
def /*7.2*/navHeader/*7.11*/:play.api.templates.Html = {_display_(

Seq[Any](format.raw/*7.15*/("""
    <a href=""""),_display_(Seq[Any](/*8.15*/routes/*8.21*/.Systems.detail(fuzzySystem.id))),format.raw/*8.52*/("""">"""),_display_(Seq[Any](/*8.55*/fuzzySystem/*8.66*/.name)),format.raw/*8.71*/("""</a>>
    """),_display_(Seq[Any](/*9.6*/tp/*9.8*/.name)),format.raw/*9.13*/("""
""")))};
Seq[Any](format.raw/*1.62*/("""
"""),format.raw/*4.5*/("""


"""),format.raw/*10.2*/("""  

"""),_display_(Seq[Any](/*12.2*/main("Type")(navHeader)(fuzzy.system.types.menu(fuzzySystem,tp))(content))))}
    }
    
    def render(fuzzySystem:FuzzySystem,tp:models.Type,content:Html) = apply(fuzzySystem,tp)(content)
    
    def f:((FuzzySystem,models.Type) => (Html) => play.api.templates.Html) = (fuzzySystem,tp) => (content) => apply(fuzzySystem,tp)(content)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Tue Nov 06 10:18:05 BRST 2012
                    SOURCE: /home/arruda/projetos/cloudfuzzy/cloudfuzzy/app/views/fuzzy/system/types/type_template.scala.html
                    HASH: cda7e5d2c4189ff085f7f1fce324f85e960b7885
                    MATRIX: 804->1|924->143|941->152|1008->156|1058->171|1072->177|1124->208|1162->211|1181->222|1207->227|1252->238|1261->240|1287->245|1328->61|1355->139|1385->247|1425->252
                    LINES: 27->1|29->7|29->7|31->7|32->8|32->8|32->8|32->8|32->8|32->8|33->9|33->9|33->9|35->1|36->4|39->10|41->12
                    -- GENERATED --
                */
            