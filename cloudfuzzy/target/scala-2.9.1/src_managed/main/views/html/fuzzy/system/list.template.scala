
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
object list extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template1[List[FuzzySystem],play.api.templates.Html] {

    /**/
    def apply/*1.2*/(fuzzySystems: List[FuzzySystem]):play.api.templates.Html = {
        _display_ {import helper._

def /*6.2*/navHeader/*6.11*/:play.api.templates.Html = {_display_(

Seq[Any](format.raw/*6.15*/("""
    My Fuzzy Systems
""")))};
Seq[Any](format.raw/*1.35*/("""

"""),format.raw/*4.1*/("""
  
"""),format.raw/*8.2*/("""  
"""),_display_(Seq[Any](/*9.2*/main("My Fuzzy Systems")(navHeader)(menu_default())/*9.53*/{_display_(Seq[Any](format.raw/*9.54*/("""   
    <p>Here are presented the Fuzzy Systems created by you so far.</br>
    You can detail, modify and delete any of then, or create a new one.
    </p>
    """),_display_(Seq[Any](/*13.6*/for(fuzzySystem <- fuzzySystems) yield /*13.38*/ {_display_(Seq[Any](format.raw/*13.40*/("""
        <div class="sub_content sub_limited">
            <h3 class='sub_content_header'>
                <a href=""""),_display_(Seq[Any](/*16.27*/routes/*16.33*/.Systems.detail(fuzzySystem.id))),format.raw/*16.64*/("""">"""),_display_(Seq[Any](/*16.67*/fuzzySystem/*16.78*/.name)),format.raw/*16.83*/("""</a>
            </h3> 

                        <p>"""),_display_(Seq[Any](/*19.29*/fuzzySystem/*19.40*/.description)),format.raw/*19.52*/("""</p>
                        """),format.raw/*24.29*/("""
            <div class="actions">

                """),_display_(Seq[Any](/*27.18*/form(routes.Systems.delete(fuzzySystem.id))/*27.61*/ {_display_(Seq[Any](format.raw/*27.63*/("""
                    <input type="submit" value="Delete">
                """)))})),format.raw/*29.18*/("""
            </div>
        </div>
    """)))})),format.raw/*32.6*/("""
    
""")))})))}
    }
    
    def render(fuzzySystems:List[FuzzySystem]) = apply(fuzzySystems)
    
    def f:((List[FuzzySystem]) => play.api.templates.Html) = (fuzzySystems) => apply(fuzzySystems)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Tue Nov 06 10:18:05 BRST 2012
                    SOURCE: /home/arruda/projetos/cloudfuzzy/cloudfuzzy/app/views/fuzzy/system/list.scala.html
                    HASH: 3750918c374c2ce63e34cf69eec9ed042dd30481
                    MATRIX: 778->1|887->58|904->67|971->71|1033->34|1061->53|1091->94|1129->98|1188->149|1226->150|1423->312|1471->344|1511->346|1664->463|1679->469|1732->500|1771->503|1791->514|1818->519|1907->572|1927->583|1961->595|2018->823|2107->876|2159->919|2199->921|2306->996|2377->1036
                    LINES: 27->1|30->6|30->6|32->6|35->1|37->4|39->8|40->9|40->9|40->9|44->13|44->13|44->13|47->16|47->16|47->16|47->16|47->16|47->16|50->19|50->19|50->19|51->24|54->27|54->27|54->27|56->29|59->32
                    -- GENERATED --
                */
            