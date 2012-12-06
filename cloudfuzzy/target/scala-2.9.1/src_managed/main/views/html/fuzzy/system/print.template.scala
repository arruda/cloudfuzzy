
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
object print extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template1[FuzzySystem,play.api.templates.Html] {

    /**/
    def apply/*1.2*/(fuzzySystem : FuzzySystem):play.api.templates.Html = {
        _display_ {import helper._

def /*5.2*/navHeader/*5.11*/:play.api.templates.Html = {_display_(

Seq[Any](format.raw/*5.15*/("""
    My Fuzzy Systems
""")))};
Seq[Any](format.raw/*1.29*/("""

"""),format.raw/*4.1*/("""
"""),format.raw/*7.2*/("""  

"""),_display_(Seq[Any](/*9.2*/main("XFL content for " + fuzzySystem.name +  " System" )(navHeader)(fuzzy.system.menu(fuzzySystem))/*9.102*/{_display_(Seq[Any](format.raw/*9.103*/("""   

        """),_display_(Seq[Any](/*11.10*/defining(fuzzySystem.getSpecification())/*11.50*/{ spec =>_display_(Seq[Any](format.raw/*11.59*/("""
            <textarea name="xfl" disabled="true" rows="40" cols="60">"""),_display_(Seq[Any](/*12.71*/spec/*12.75*/.toXfl())),format.raw/*12.83*/("""</textarea>             
        """)))})),format.raw/*13.10*/("""
    
""")))})))}
    }
    
    def render(fuzzySystem:FuzzySystem) = apply(fuzzySystem)
    
    def f:((FuzzySystem) => play.api.templates.Html) = (fuzzySystem) => apply(fuzzySystem)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Tue Nov 06 10:18:05 BRST 2012
                    SOURCE: /home/arruda/projetos/cloudfuzzy/cloudfuzzy/app/views/fuzzy/system/print.scala.html
                    HASH: 32f62539c626e7a7d811504e08d04540d1736a8c
                    MATRIX: 773->1|876->49|893->58|960->62|1022->28|1050->47|1077->85|1116->90|1225->190|1264->191|1314->205|1363->245|1410->254|1517->325|1530->329|1560->337|1626->371
                    LINES: 27->1|30->5|30->5|32->5|35->1|37->4|38->7|40->9|40->9|40->9|42->11|42->11|42->11|43->12|43->12|43->12|44->13
                    -- GENERATED --
                */
            