
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
object menu extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template2[FuzzySystem,models.Type,play.api.templates.Html] {

    /**/
    def apply/*1.2*/(fuzzySystem : FuzzySystem, tp : models.Type):play.api.templates.Html = {
        _display_ {

Seq[Any](format.raw/*1.47*/("""
"""),format.raw/*4.5*/("""

<li ><a class='left_menu' href=""""),_display_(Seq[Any](/*6.34*/routes/*6.40*/.Systems.detail(fuzzySystem.id))),format.raw/*6.71*/("""">Back</a></li>
<li><a href=""""),_display_(Seq[Any](/*7.15*/routes/*7.21*/.Types.prepareEdit(fuzzySystem.id,tp.id))),format.raw/*7.61*/("""">Edit</a></li>
<li ><a  href=""""),_display_(Seq[Any](/*8.17*/routes/*8.23*/.Types.delete(fuzzySystem.id,tp.id))),format.raw/*8.58*/("""">Delete</a></li>"""))}
    }
    
    def render(fuzzySystem:FuzzySystem,tp:models.Type) = apply(fuzzySystem,tp)
    
    def f:((FuzzySystem,models.Type) => play.api.templates.Html) = (fuzzySystem,tp) => apply(fuzzySystem,tp)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Tue Nov 06 10:18:05 BRST 2012
                    SOURCE: /home/arruda/projetos/cloudfuzzy/cloudfuzzy/app/views/fuzzy/system/types/menu.scala.html
                    HASH: 9fda9538322fe3ce94bc968f2b55677d5a5772e9
                    MATRIX: 790->1|912->46|939->108|1009->143|1023->149|1075->180|1140->210|1154->216|1215->256|1282->288|1296->294|1352->329
                    LINES: 27->1|30->1|31->4|33->6|33->6|33->6|34->7|34->7|34->7|35->8|35->8|35->8
                    -- GENERATED --
                */
            