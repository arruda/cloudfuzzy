
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
object menu extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template3[FuzzySystem,models.Type,models.MF,play.api.templates.Html] {

    /**/
    def apply/*1.2*/(fuzzySystem : FuzzySystem, tp : models.Type, mf : models.MF):play.api.templates.Html = {
        _display_ {

Seq[Any](format.raw/*1.63*/("""
"""),format.raw/*4.5*/("""

<li ><a class='left_menu' href=""""),_display_(Seq[Any](/*6.34*/routes/*6.40*/.Types.detail(fuzzySystem.id, tp.id))),format.raw/*6.76*/("""">Back</a></li>
<li><a href="#">Edit</a></li>
<li ><a  href=""""),_display_(Seq[Any](/*8.17*/routes/*8.23*/.MFs.delete(fuzzySystem.id, tp.id,mf.id))),format.raw/*8.63*/("""">Delete</a></li>"""))}
    }
    
    def render(fuzzySystem:FuzzySystem,tp:models.Type,mf:models.MF) = apply(fuzzySystem,tp,mf)
    
    def f:((FuzzySystem,models.Type,models.MF) => play.api.templates.Html) = (fuzzySystem,tp,mf) => apply(fuzzySystem,tp,mf)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Tue Nov 06 10:18:05 BRST 2012
                    SOURCE: /home/arruda/projetos/cloudfuzzy/cloudfuzzy/app/views/fuzzy/system/types/mfs/menu.scala.html
                    HASH: 5ea765cc82a753e8c1ca880d9a452deaec0895e1
                    MATRIX: 804->1|942->62|969->122|1039->157|1053->163|1110->199|1207->261|1221->267|1282->307
                    LINES: 27->1|30->1|31->4|33->6|33->6|33->6|35->8|35->8|35->8
                    -- GENERATED --
                */
            