
package views.html

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
object menu_default extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template0[play.api.templates.Html] {

    /**/
    def apply/*1.2*/():play.api.templates.Html = {
        _display_ {

Seq[Any](format.raw/*1.4*/("""
"""),format.raw/*4.5*/("""
<li ><a class='left_menu' href=""""),_display_(Seq[Any](/*5.34*/routes/*5.40*/.Systems.list())),format.raw/*5.55*/("""">Systems</a></li>
<li><a href=""""),_display_(Seq[Any](/*6.15*/routes/*6.21*/.Systems.prepareCreate())),format.raw/*6.45*/("""">New System</a></li>
<li ><a class='right_menu' href="/blog/">Blog</a></li>"""))}
    }
    
    def render() = apply()
    
    def f:(() => play.api.templates.Html) = () => apply()
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Tue Oct 30 10:43:49 BRST 2012
                    SOURCE: /home/arruda/projetos/cloudfuzzy/cloudfuzzy/app/views/menu_default.scala.html
                    HASH: ef0786fc7389287904bb95560dd2347b53b192ef
                    MATRIX: 755->1|833->3|860->42|929->76|943->82|979->97|1047->130|1061->136|1106->160
                    LINES: 27->1|30->1|31->4|32->5|32->5|32->5|33->6|33->6|33->6
                    -- GENERATED --
                */
            