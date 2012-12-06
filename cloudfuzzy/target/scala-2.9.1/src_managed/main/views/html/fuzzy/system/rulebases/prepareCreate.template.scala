
package views.html.fuzzy.system.rulebases

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
object prepareCreate extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template2[FuzzySystem,Form[models.RuleBase],play.api.templates.Html] {

    /**/
    def apply/*1.2*/(fuzzySystem: FuzzySystem,  newRBForm: Form[models.RuleBase]):play.api.templates.Html = {
        _display_ {import helper._


Seq[Any](format.raw/*1.63*/("""

"""),format.raw/*4.1*/("""


"""),_display_(Seq[Any](/*7.2*/fuzzy/*7.7*/.system.system_template(fuzzySystem)/*7.43*/{_display_(Seq[Any](format.raw/*7.44*/("""   
    <div class="sub_content sub_limited">
        <h3 class='sub_content_header'>New Rule Base</h3> 
            """),_display_(Seq[Any](/*10.14*/helper/*10.20*/.form(action = routes.RuleBases.create(fuzzySystem.id), 'id -> "newRBForm")/*10.95*/ {_display_(Seq[Any](format.raw/*10.97*/("""
                
                <fieldset>
                    <legend>General Information for the new Rule Bas</legend>
                    
                    """),_display_(Seq[Any](/*15.22*/inputText(
                        newRBForm("name"), 
                        '_label -> "Name", 
                        '_help -> "The name of this Rule Base."
                    ))),format.raw/*19.22*/("""                

                    """),_display_(Seq[Any](/*21.22*/select(
                        newRBForm("idOperatorSet"),
                        options =  options(                        
                                        models.FuzzySystem.getAvailableOperatorSetForFuzzySystem(
                                            fuzzySystem
                                        )
                                    ),
                        '_label -> "Operator Set",
                        '_help -> "Select the Operator Set that will be used"
                    ))),format.raw/*30.22*/("""          

                </fieldset>           

                
                
                <div class="actions">
                    <input type="submit" class="btn primary" value="Create">
                    <input type="button" class="btn secondary" value="Cancel"onclick="window.location = '"""),_display_(Seq[Any](/*38.107*/routes/*38.113*/.Systems.detail(fuzzySystem.id))),format.raw/*38.144*/("""' "/>

                </div>
                
            """)))})),format.raw/*42.14*/("""
 
    </div>


""")))})))}
    }
    
    def render(fuzzySystem:FuzzySystem,newRBForm:Form[models.RuleBase]) = apply(fuzzySystem,newRBForm)
    
    def f:((FuzzySystem,Form[models.RuleBase]) => play.api.templates.Html) = (fuzzySystem,newRBForm) => apply(fuzzySystem,newRBForm)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Mon Nov 26 13:28:54 BRST 2012
                    SOURCE: /home/arruda/projetos/cloudfuzzy/cloudfuzzy/app/views/fuzzy/system/rulebases/prepareCreate.scala.html
                    HASH: 9d485f53de0cc3cbf15971cf5103914a5588e908
                    MATRIX: 813->1|967->62|995->81|1033->85|1045->90|1089->126|1127->127|1281->245|1296->251|1380->326|1420->328|1621->493|1827->677|1902->716|2437->1229|2781->1536|2797->1542|2851->1573|2943->1633
                    LINES: 27->1|31->1|33->4|36->7|36->7|36->7|36->7|39->10|39->10|39->10|39->10|44->15|48->19|50->21|59->30|67->38|67->38|67->38|71->42
                    -- GENERATED --
                */
            