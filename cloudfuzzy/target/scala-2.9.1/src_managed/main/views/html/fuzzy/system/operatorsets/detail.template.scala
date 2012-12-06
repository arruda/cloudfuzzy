
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
object detail extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template2[FuzzySystem,models.OperatorSet,play.api.templates.Html] {

    /**/
    def apply/*1.2*/(fuzzySystem : FuzzySystem, opSet : models.OperatorSet):play.api.templates.Html = {
        _display_ {import helper._

def /*6.2*/navHeader/*6.11*/:play.api.templates.Html = {_display_(

Seq[Any](format.raw/*6.15*/("""
    """),_display_(Seq[Any](/*7.6*/fuzzySystem/*7.17*/.name)),format.raw/*7.22*/(""" + " -> " + """),_display_(Seq[Any](/*7.35*/opSet/*7.40*/.name)),format.raw/*7.45*/("""
""")))};
Seq[Any](format.raw/*1.57*/("""

"""),format.raw/*4.1*/("""
  
"""),format.raw/*8.2*/("""  

"""),_display_(Seq[Any](/*10.2*/fuzzy/*10.7*/.system.operatorsets.operatorset_template(fuzzySystem, opSet)/*10.68*/{_display_(Seq[Any](format.raw/*10.69*/("""   

    <div class="sub_content sub_limited">
        <h3 class='sub_content_header'>General Information</h3>

        <p>Name: """),_display_(Seq[Any](/*15.19*/opSet/*15.24*/.name)),format.raw/*15.29*/("""</p>
        
    </div>
    <div class="sub_content sub_limited">
        <h3 class='sub_content_header'>Operators</h3>

        <ul>
        """),_display_(Seq[Any](/*22.10*/for( op <- opSet.operators ) yield /*22.38*/ {_display_(Seq[Any](format.raw/*22.40*/("""
            <li>
                    """),_display_(Seq[Any](/*24.22*/op/*24.24*/.name)),format.raw/*24.29*/(""":   """),_display_(Seq[Any](/*24.34*/op/*24.36*/.getOptionName())),format.raw/*24.52*/("""
            </li>
         """)))})),format.raw/*26.11*/("""
        </ul>
    </div>
    
""")))})),format.raw/*30.2*/("""
        """))}
    }
    
    def render(fuzzySystem:FuzzySystem,opSet:models.OperatorSet) = apply(fuzzySystem,opSet)
    
    def f:((FuzzySystem,models.OperatorSet) => play.api.templates.Html) = (fuzzySystem,opSet) => apply(fuzzySystem,opSet)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Tue Nov 06 10:18:05 BRST 2012
                    SOURCE: /home/arruda/projetos/cloudfuzzy/cloudfuzzy/app/views/fuzzy/system/operatorsets/detail.scala.html
                    HASH: d9af245686a2afff7826399a2a9f9f0d2fb4ae3e
                    MATRIX: 806->1|937->80|954->89|1021->93|1061->99|1080->110|1106->115|1154->128|1167->133|1193->138|1234->56|1262->75|1292->140|1332->145|1345->150|1415->211|1454->212|1620->342|1634->347|1661->352|1841->496|1885->524|1925->526|2000->565|2011->567|2038->572|2079->577|2090->579|2128->595|2189->624|2252->656
                    LINES: 27->1|30->6|30->6|32->6|33->7|33->7|33->7|33->7|33->7|33->7|35->1|37->4|39->8|41->10|41->10|41->10|41->10|46->15|46->15|46->15|53->22|53->22|53->22|55->24|55->24|55->24|55->24|55->24|55->24|57->26|61->30
                    -- GENERATED --
                */
            