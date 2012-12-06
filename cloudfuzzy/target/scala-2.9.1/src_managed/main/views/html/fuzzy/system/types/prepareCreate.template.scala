
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
object prepareCreate extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template2[FuzzySystem,Form[models.Type],play.api.templates.Html] {

    /**/
    def apply/*1.2*/(fuzzySystem: FuzzySystem, newTypeForm: Form[models.Type]):play.api.templates.Html = {
        _display_ {import helper._


Seq[Any](format.raw/*1.60*/("""

"""),format.raw/*4.1*/("""

"""),_display_(Seq[Any](/*6.2*/fuzzy/*6.7*/.system.system_template(fuzzySystem)/*6.43*/{_display_(Seq[Any](format.raw/*6.44*/("""      
    <div class="sub_content sub_limited">
        <h3 class='sub_content_header'>New Fuzzy Type</h3> 
            """),_display_(Seq[Any](/*9.14*/helper/*9.20*/.form(action = routes.Types.create(fuzzySystem.id), 'id -> "newTypeForm")/*9.93*/ {_display_(Seq[Any](format.raw/*9.95*/("""
                
                <fieldset>
                    <legend>General Information for the new Type</legend>
                    
                    """),_display_(Seq[Any](/*14.22*/inputText(
                        newTypeForm("name"), 
                        '_label -> "Name", 
                        '_help -> "The name of this type."
                    ))),format.raw/*18.22*/("""                

                    
                    
                    """),_display_(Seq[Any](/*22.22*/inputRadioGroup(
                      newTypeForm("type"),
                      options = options(
                            "0"->"Equi-spaced Triangles",
                            "1"->"Triangles with 'straps'",
                            "2"->"Equi-spaced Bells",
                            "3"->"Bells with 'straps'",
                            "4"->"Equi-spaced Trapezoids",
                            "5"->"Equi-spaced Singletons",
                            "6"->"Centered Bells",
                            "7"->"Centered Singletons",
                            "8"->"Blank",
                            "9"->"Extends"
                            ),
                      '_label -> "Member Functions Type",
                      '_error -> newTypeForm("type").error.map(_.withMessage("select Type"))
                    ))),format.raw/*38.22*/("""

                </fieldset>           
                
                <fieldset>
                    <legend>Universe</legend>
                    """),_display_(Seq[Any](/*44.22*/inputText(
                        newTypeForm("min"), 
                        '_label -> "Min", 
                        '_help -> "Minimun of the Universe."
                    ))),format.raw/*48.22*/("""      
                    """),_display_(Seq[Any](/*49.22*/inputText(
                        newTypeForm("max"), 
                        '_label -> "Max", 
                        '_help -> "Maximun of the Universe."
                    ))),format.raw/*53.22*/("""      
                
                    """),_display_(Seq[Any](/*55.22*/inputText(
                        newTypeForm("card"), 
                        '_label -> "Cardinality", 
                        '_help -> "Cardinality of the Universe."
                    ))),format.raw/*59.22*/("""      
                
                </fieldset>         
                <fieldset>
                    <legend>Membership Functions</legend>
                    """),_display_(Seq[Any](/*64.22*/inputText(
                        newTypeForm("numMFs"), 
                        '_label -> "No. Membership Function", 
                        '_help -> "The amount of Membership functions."
                    ))),format.raw/*68.22*/("""      
                </fieldset>             
                
                <div class="actions">
                    <input type="submit" class="btn primary" value="Create">
                    <input type="button" class="btn secondary" value="Cancel"onclick="window.location = '"""),_display_(Seq[Any](/*73.107*/routes/*73.113*/.Systems.detail(fuzzySystem.id))),format.raw/*73.144*/("""' "/>
                </div>
                
            """)))})),format.raw/*76.14*/("""
 
    </div>
    
""")))})))}
    }
    
    def render(fuzzySystem:FuzzySystem,newTypeForm:Form[models.Type]) = apply(fuzzySystem,newTypeForm)
    
    def f:((FuzzySystem,Form[models.Type]) => play.api.templates.Html) = (fuzzySystem,newTypeForm) => apply(fuzzySystem,newTypeForm)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Mon Nov 26 13:28:54 BRST 2012
                    SOURCE: /home/arruda/projetos/cloudfuzzy/cloudfuzzy/app/views/fuzzy/system/types/prepareCreate.scala.html
                    HASH: 307403a28436c7ae3a0ede98762e51778450473f
                    MATRIX: 805->1|956->59|984->78|1021->81|1033->86|1077->122|1115->123|1272->245|1286->251|1367->324|1406->326|1603->487|1806->668|1923->749|2787->1591|2975->1743|3178->1924|3242->1952|3445->2133|3526->2178|3742->2372|3945->2539|4182->2754|4505->3040|4521->3046|4575->3077|4666->3136
                    LINES: 27->1|31->1|33->4|35->6|35->6|35->6|35->6|38->9|38->9|38->9|38->9|43->14|47->18|51->22|67->38|73->44|77->48|78->49|82->53|84->55|88->59|93->64|97->68|102->73|102->73|102->73|105->76
                    -- GENERATED --
                */
            