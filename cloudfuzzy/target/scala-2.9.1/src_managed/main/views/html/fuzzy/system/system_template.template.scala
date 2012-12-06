
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
object system_template extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template2[FuzzySystem,Html,play.api.templates.Html] {

    /**/
    def apply/*1.2*/(fuzzySystem : FuzzySystem)(content: Html):play.api.templates.Html = {
        _display_ {
def /*6.2*/navHeader/*6.11*/:play.api.templates.Html = {_display_(

Seq[Any](format.raw/*6.15*/("""
    """),_display_(Seq[Any](/*7.6*/fuzzySystem/*7.17*/.name)),format.raw/*7.22*/("""
""")))};
Seq[Any](format.raw/*1.44*/("""
"""),format.raw/*4.5*/("""

"""),format.raw/*8.2*/("""  

"""),_display_(Seq[Any](/*10.2*/main(fuzzySystem.name)(navHeader)(fuzzy.system.menu(fuzzySystem))(content))))}
    }
    
    def render(fuzzySystem:FuzzySystem,content:Html) = apply(fuzzySystem)(content)
    
    def f:((FuzzySystem) => (Html) => play.api.templates.Html) = (fuzzySystem) => (content) => apply(fuzzySystem)(content)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Tue Nov 06 10:18:05 BRST 2012
                    SOURCE: /home/arruda/projetos/cloudfuzzy/cloudfuzzy/app/views/fuzzy/system/system_template.scala.html
                    HASH: 837b07fe4f76eac54da49ebe1c23660afd43b116
                    MATRIX: 788->1|890->126|907->135|974->139|1014->145|1033->156|1059->161|1100->43|1127->123|1155->163|1195->168
                    LINES: 27->1|29->6|29->6|31->6|32->7|32->7|32->7|34->1|35->4|37->8|39->10
                    -- GENERATED --
                */
            