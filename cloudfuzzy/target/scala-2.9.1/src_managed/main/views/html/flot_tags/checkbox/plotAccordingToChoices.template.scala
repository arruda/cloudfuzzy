
package views.html.flot_tags.checkbox

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
object plotAccordingToChoices extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template4[String,String,String,String,play.api.templates.Html] {

    /**/
    def apply/*1.2*/(plotContainer : String , plotDataVar : String = "plotData", plotOptionsVar : String = "plotOptions", choiceContainer : String = "#choices"):play.api.templates.Html = {
        _display_ {

Seq[Any](format.raw/*1.142*/("""

    function plotAccordingToChoices() """),format.raw("""{"""),format.raw/*3.40*/("""
        var choiceContainer = $(""""),_display_(Seq[Any](/*4.35*/choiceContainer)),format.raw/*4.50*/("""");  
        var newdata = [];
        choiceContainer.find("input:checked").each(function () """),format.raw("""{"""),format.raw/*6.65*/("""
            var key = $(this).attr("name");
            if (key)
                if ("""),_display_(Seq[Any](/*9.22*/{plotDataVar + "[key]"})),format.raw/*9.45*/(""")
                    newdata.push("""),_display_(Seq[Any](/*10.35*/{plotDataVar + "[key]"})),format.raw/*10.58*/(""");
        """),format.raw("""}"""),format.raw/*11.10*/(""");
        if (newdata.length > 0)
            $.plot($(""""),_display_(Seq[Any](/*13.24*/plotContainer)),format.raw/*13.37*/(""""), newdata, """),_display_(Seq[Any](/*13.51*/plotOptionsVar)),format.raw/*13.65*/(""");
        
    """),format.raw("""}"""),format.raw/*15.6*/("""
	"""))}
    }
    
    def render(plotContainer:String,plotDataVar:String,plotOptionsVar:String,choiceContainer:String) = apply(plotContainer,plotDataVar,plotOptionsVar,choiceContainer)
    
    def f:((String,String,String,String) => play.api.templates.Html) = (plotContainer,plotDataVar,plotOptionsVar,choiceContainer) => apply(plotContainer,plotDataVar,plotOptionsVar,choiceContainer)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Thu Nov 01 12:20:48 BRST 2012
                    SOURCE: /home/arruda/projetos/cloudfuzzy/cloudfuzzy/app/views/flot_tags/checkbox/plotAccordingToChoices.scala.html
                    HASH: a16fcfaf44d3f0c83e6f1adc8eeb88ce6a551a93
                    MATRIX: 812->1|1030->141|1117->182|1187->217|1223->232|1365->328|1487->415|1531->438|1603->474|1648->497|1707->509|1801->567|1836->580|1886->594|1922->608|1985->625
                    LINES: 27->1|30->1|32->3|33->4|33->4|35->6|38->9|38->9|39->10|39->10|40->11|42->13|42->13|42->13|42->13|44->15
                    -- GENERATED --
                */
            