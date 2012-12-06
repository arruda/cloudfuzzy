
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
object prepareChoices extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template4[String,String,String,String,play.api.templates.Html] {

    /**/
    def apply/*1.2*/(plotContainer : String , plotDataVar : String = "plotData", plotOptionsVar : String = "plotOptions", choiceContainer : String = "#choices"):play.api.templates.Html = {
        _display_ {

Seq[Any](format.raw/*1.142*/("""
    
        // hard-code color indices to prevent them from shifting as
        // datas are turned on/off
        var i = 1;
        $.each("""),_display_(Seq[Any](/*6.17*/plotDataVar)),format.raw/*6.28*/(""", function(key, val) """),format.raw("""{"""),format.raw/*6.50*/("""
            val.color = i;
            ++i;
        """),format.raw("""}"""),format.raw/*9.10*/(""");

        // insert checkboxes                    
        var choiceContainer = $(""""),_display_(Seq[Any](/*12.35*/choiceContainer)),format.raw/*12.50*/("""");  
        var iterator = 0;
        $.each("""),_display_(Seq[Any](/*14.17*/plotDataVar)),format.raw/*14.28*/(""", function(key, val) """),format.raw("""{"""),format.raw/*14.50*/("""
                if(iterator % 2==0)"""),format.raw("""{"""),format.raw/*15.37*/("""
                    choiceContainer.append('<td style="padding-right: 10px"><input type="checkbox" name="' + key +
                   '" checked="checked" id="id' + key + '"/>' +
                   '<label for="id' + key + '">'
                    + val.label + '</label></td>');                             
                """),format.raw("""}"""),format.raw/*20.18*/("""
        """),format.raw("""}"""),format.raw/*21.10*/(""");
        choiceContainer.find("input").click(plotAccordingToChoices);
    """))}
    }
    
    def render(plotContainer:String,plotDataVar:String,plotOptionsVar:String,choiceContainer:String) = apply(plotContainer,plotDataVar,plotOptionsVar,choiceContainer)
    
    def f:((String,String,String,String) => play.api.templates.Html) = (plotContainer,plotDataVar,plotOptionsVar,choiceContainer) => apply(plotContainer,plotDataVar,plotOptionsVar,choiceContainer)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Thu Nov 01 12:21:33 BRST 2012
                    SOURCE: /home/arruda/projetos/cloudfuzzy/cloudfuzzy/app/views/flot_tags/checkbox/prepareChoices.scala.html
                    HASH: 979eec1b609e1bdea6088f9801ca11ba917f17a5
                    MATRIX: 804->1|1022->141|1201->285|1233->296|1301->318|1401->372|1524->459|1561->474|1645->522|1678->533|1747->555|1831->592|2205->919|2262->929
                    LINES: 27->1|30->1|35->6|35->6|35->6|38->9|41->12|41->12|43->14|43->14|43->14|44->15|49->20|50->21
                    -- GENERATED --
                */
            