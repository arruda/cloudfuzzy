
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
object edit extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template3[FuzzySystem,models.Type,Form[models.Type],play.api.templates.Html] {

    /**/
    def apply/*1.2*/(fuzzySystem : FuzzySystem, tp : models.Type, editTypeForm: Form[models.Type]):play.api.templates.Html = {
        _display_ {import helper._


Seq[Any](format.raw/*1.80*/("""

"""),format.raw/*4.1*/("""
"""),_display_(Seq[Any](/*5.2*/fuzzy/*5.7*/.system.types.type_template(fuzzySystem, tp)/*5.51*/ {_display_(Seq[Any](format.raw/*5.53*/("""    
    <div class="sub_content sub_limited">
        <h3 class='sub_content_header'>New Fuzzy Type</h3> 
            """),_display_(Seq[Any](/*8.14*/helper/*8.20*/.form(action = routes.Types.edit(fuzzySystem.id, tp.id), 'id -> "editTypeForm")/*8.99*/ {_display_(Seq[Any](format.raw/*8.101*/("""

                """),_display_(Seq[Any](/*10.18*/if(editTypeForm.hasGlobalErrors)/*10.50*/ {_display_(Seq[Any](format.raw/*10.52*/(""" 
                    <p class="error">
                        """),_display_(Seq[Any](/*12.26*/editTypeForm/*12.38*/.globalError.message)),format.raw/*12.58*/("""
                    </p>
                """)))})),format.raw/*14.18*/("""

                <fieldset>
                    <legend>General Information for the new Type</legend>
                    
                    """),_display_(Seq[Any](/*19.22*/inputText(
                        editTypeForm("name"), 
                        '_label -> "Name", 
                        '_help -> "The name of this type."
                    ))),format.raw/*23.22*/("""                


                </fieldset>           
                
                <fieldset>
                    <legend>Universe</legend>
                    """),_display_(Seq[Any](/*30.22*/inputText(
                        editTypeForm("min"), 
                        '_label -> "Min", 
                        '_help -> "Minimun of the Universe."
                    ))),format.raw/*34.22*/("""      
                    """),_display_(Seq[Any](/*35.22*/inputText(
                        editTypeForm("max"), 
                        '_label -> "Max", 
                        '_help -> "Maximun of the Universe."
                    ))),format.raw/*39.22*/("""      
                
                    """),_display_(Seq[Any](/*41.22*/inputText(
                        editTypeForm("card"), 
                        '_label -> "Cardinality", 
                        '_help -> "Cardinality of the Universe."
                    ))),format.raw/*45.22*/("""      
                
                </fieldset>         
                
                <div class="actions">
                    <input type="submit" class="btn primary" value="Save">
                </div>
                
            """)))})),format.raw/*53.14*/("""
 
    </div>
    
""")))})))}
    }
    
    def render(fuzzySystem:FuzzySystem,tp:models.Type,editTypeForm:Form[models.Type]) = apply(fuzzySystem,tp,editTypeForm)
    
    def f:((FuzzySystem,models.Type,Form[models.Type]) => play.api.templates.Html) = (fuzzySystem,tp,editTypeForm) => apply(fuzzySystem,tp,editTypeForm)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Tue Nov 06 10:18:05 BRST 2012
                    SOURCE: /home/arruda/projetos/cloudfuzzy/cloudfuzzy/app/views/fuzzy/system/types/edit.scala.html
                    HASH: e3ddbe342336e9e27be784a8071caaafbe98474a
                    MATRIX: 808->1|979->79|1007->98|1043->100|1055->105|1107->149|1146->151|1301->271|1315->277|1402->356|1442->358|1497->377|1538->409|1578->411|1679->476|1700->488|1742->508|1817->551|1998->696|2202->878|2407->1047|2611->1229|2675->1257|2879->1439|2960->1484|3177->1679|3453->1923
                    LINES: 27->1|31->1|33->4|34->5|34->5|34->5|34->5|37->8|37->8|37->8|37->8|39->10|39->10|39->10|41->12|41->12|41->12|43->14|48->19|52->23|59->30|63->34|64->35|68->39|70->41|74->45|82->53
                    -- GENERATED --
                */
            