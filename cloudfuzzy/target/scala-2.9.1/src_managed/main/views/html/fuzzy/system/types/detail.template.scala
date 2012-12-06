
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
object detail extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template2[FuzzySystem,models.Type,play.api.templates.Html] {

    /**/
    def apply/*1.2*/(fuzzySystem : FuzzySystem, tp : models.Type):play.api.templates.Html = {
        _display_ {import helper._


Seq[Any](format.raw/*1.47*/("""

"""),format.raw/*4.1*/("""
"""),_display_(Seq[Any](/*5.2*/fuzzy/*5.7*/.system.types.type_template(fuzzySystem, tp)/*5.51*/ {_display_(Seq[Any](format.raw/*5.53*/("""   

    <div class="sub_lefted">
        <div class="sub_content sub_limited">
            <h3 class='sub_content_header'>Universe of Discourse</h3>

            <p>Min: """),_display_(Seq[Any](/*11.22*/tp/*11.24*/.min)),format.raw/*11.28*/("""</p>
            <p>Max: """),_display_(Seq[Any](/*12.22*/tp/*12.24*/.max)),format.raw/*12.28*/("""</p>
            <p>Card: """),_display_(Seq[Any](/*13.23*/tp/*13.25*/.card)),format.raw/*13.30*/("""</p>
            
        </div>
        <div class="sub_content sub_limited">
            <h3 class='sub_content_header'>Membership Functions</h3>

            <ul>
            """),_display_(Seq[Any](/*20.14*/for( mf <- tp.MFs ) yield /*20.33*/ {_display_(Seq[Any](format.raw/*20.35*/("""
                <li><a href=""""),_display_(Seq[Any](/*21.31*/routes/*21.37*/.MFs.detail(fuzzySystem.id,tp.id,mf.id))),format.raw/*21.76*/("""">
                        """),_display_(Seq[Any](/*22.26*/mf/*22.28*/.label)),format.raw/*22.34*/("""
                </a> """),_display_(Seq[Any](/*23.23*/forms_util/*23.33*/.actions_icons("#", routes.MFs.delete(fuzzySystem.id, tp.id, mf.id).toString()))),format.raw/*23.112*/("""</li>
             """)))})),format.raw/*24.15*/("""
            </ul>
            
            <div class="actions">
                <form action=""""),_display_(Seq[Any](/*28.32*/routes/*28.38*/.MFs.prepareCreate(fuzzySystem.id,tp.id))),format.raw/*28.78*/("""" method="GET" >
                    
                    <input type="submit" value="Add Membership Function">
                                        
                </form>
            </div>
        </div>

    </div>
    <div class="sub_righted">
        <div class="sub_content">
            <h3 class='sub_content_header'>Membership Functions Plot</h3>
            <table id="choices" style="font-weight: bold"><tr ><td>Show:</td></tr></table>
            <div id="mfs_plot" style="width:500px;height:300px" class="sub_limited">
            </div>

        </div>

    </div>



    <script type="text/javascript">

        $(document).ready(function() """),format.raw("""{"""),format.raw/*52.39*/("""

                 
  

            var plotData = [];
            var plotOptions = """),format.raw("""{"""),format.raw("""}"""),format.raw/*58.33*/(""";

            //get parameters for a given operator selected option
            function getMfsDataAndOptions(id_sys, id_type) """),format.raw("""{"""),format.raw/*61.61*/(""" 

                console.log("getMfsDataAndOptions(" + id_sys + "," + id_type + ")");

                $.getJSON('/ajax/get_mfs_plot_data', 
                      """),format.raw("""{"""),format.raw/*66.24*/("""
                        id_sys: id_sys,
                        id_type: id_type
                      """),format.raw("""}"""),format.raw/*69.24*/(""",
                    function(data) """),format.raw("""{"""),format.raw/*70.37*/("""
                        var newData  = data.datas;
                        var newOptions  = data.options;
                        console.log(newData);
                        console.log(newOptions);
                        plotData = newData;
                        plotOptions = newOptions;


                        """),_display_(Seq[Any](/*79.26*/flot_tags/*79.35*/.checkbox.prepareChoices("#mfs_plot"))),format.raw/*79.72*/(""" 

                        plotAccordingToChoices();
                        
                """),format.raw("""}"""),format.raw/*83.18*/(""");
            """),format.raw("""}"""),format.raw/*84.14*/("""

            """),_display_(Seq[Any](/*86.14*/flot_tags/*86.23*/.checkbox.plotAccordingToChoices("#mfs_plot"))),format.raw/*86.68*/(""" 

            getMfsDataAndOptions("""),_display_(Seq[Any](/*88.35*/fuzzySystem/*88.46*/.id)),format.raw/*88.49*/(""", """),_display_(Seq[Any](/*88.52*/tp/*88.54*/.id)),format.raw/*88.57*/(""");

        """),format.raw("""}"""),format.raw/*90.10*/(""");
                
    </script>

""")))})),format.raw/*94.2*/("""
"""))}
    }
    
    def render(fuzzySystem:FuzzySystem,tp:models.Type) = apply(fuzzySystem,tp)
    
    def f:((FuzzySystem,models.Type) => play.api.templates.Html) = (fuzzySystem,tp) => apply(fuzzySystem,tp)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Mon Nov 26 13:28:54 BRST 2012
                    SOURCE: /home/arruda/projetos/cloudfuzzy/cloudfuzzy/app/views/fuzzy/system/types/detail.scala.html
                    HASH: 6f2905b9b95a33ab0e015c73f0193517522a4982
                    MATRIX: 792->1|930->46|958->65|994->67|1006->72|1058->116|1097->118|1305->290|1316->292|1342->296|1404->322|1415->324|1441->328|1504->355|1515->357|1542->362|1757->541|1792->560|1832->562|1899->593|1914->599|1975->638|2039->666|2050->668|2078->674|2137->697|2156->707|2258->786|2310->806|2443->903|2458->909|2520->949|3229->1611|3382->1698|3558->1827|3771->1993|3923->2098|4008->2136|4368->2460|4386->2469|4445->2506|4587->2601|4650->2617|4701->2632|4719->2641|4786->2686|4859->2723|4879->2734|4904->2737|4943->2740|4954->2742|4979->2745|5039->2758|5106->2794
                    LINES: 27->1|31->1|33->4|34->5|34->5|34->5|34->5|40->11|40->11|40->11|41->12|41->12|41->12|42->13|42->13|42->13|49->20|49->20|49->20|50->21|50->21|50->21|51->22|51->22|51->22|52->23|52->23|52->23|53->24|57->28|57->28|57->28|81->52|87->58|90->61|95->66|98->69|99->70|108->79|108->79|108->79|112->83|113->84|115->86|115->86|115->86|117->88|117->88|117->88|117->88|117->88|117->88|119->90|123->94
                    -- GENERATED --
                */
            