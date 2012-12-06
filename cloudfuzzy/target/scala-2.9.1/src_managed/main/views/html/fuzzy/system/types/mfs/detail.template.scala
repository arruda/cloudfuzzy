
package views.html.fuzzy.system.types.mfs

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
object detail extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template3[FuzzySystem,models.Type,models.MF,play.api.templates.Html] {

    /**/
    def apply/*1.2*/(fuzzySystem : FuzzySystem, tp : models.Type, mf : models.MF):play.api.templates.Html = {
        _display_ {import helper._

def /*5.2*/navHeader/*5.11*/:play.api.templates.Html = {_display_(

Seq[Any](format.raw/*5.15*/("""
    My Fuzzy Systems
""")))};
Seq[Any](format.raw/*1.63*/("""

"""),format.raw/*4.1*/("""
"""),format.raw/*7.2*/("""  
"""),_display_(Seq[Any](/*8.2*/fuzzy/*8.7*/.system.types.mfs.mf_template(fuzzySystem, tp, mf)/*8.57*/{_display_(Seq[Any](format.raw/*8.58*/("""  

    <div class="sub_content sub_limited">
        <h3 class='sub_content_header'>General Information</h3>

        <p>Label: """),_display_(Seq[Any](/*13.20*/mf/*13.22*/.label)),format.raw/*13.28*/("""</p>
        <p>Function: """),_display_(Seq[Any](/*14.23*/mf/*14.25*/.functionName())),format.raw/*14.40*/("""</p>

        
    </div>
    <div class="sub_content sub_limited">
        <h3 class='sub_content_header'>Parameters</h3>

        <ol type="a">
        """),_display_(Seq[Any](/*22.10*/for( param <- mf.params ) yield /*22.35*/ {_display_(Seq[Any](format.raw/*22.37*/("""
            <li>"""),_display_(Seq[Any](/*23.18*/param)),format.raw/*23.23*/("""</li>
         """)))})),format.raw/*24.11*/("""
        </ol>
        
    </div>
    
""")))})),format.raw/*29.2*/("""
        """))}
    }
    
    def render(fuzzySystem:FuzzySystem,tp:models.Type,mf:models.MF) = apply(fuzzySystem,tp,mf)
    
    def f:((FuzzySystem,models.Type,models.MF) => play.api.templates.Html) = (fuzzySystem,tp,mf) => apply(fuzzySystem,tp,mf)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Tue Nov 06 10:18:05 BRST 2012
                    SOURCE: /home/arruda/projetos/cloudfuzzy/cloudfuzzy/app/views/fuzzy/system/types/mfs/detail.scala.html
                    HASH: bd7dd4d113d1215e292f3947a01852b048a1e68d
                    MATRIX: 806->1|943->83|960->92|1027->96|1089->62|1117->81|1144->119|1182->123|1194->128|1252->178|1290->179|1456->309|1467->311|1495->317|1558->344|1569->346|1606->361|1797->516|1838->541|1878->543|1932->561|1959->566|2007->582|2079->623
                    LINES: 27->1|30->5|30->5|32->5|35->1|37->4|38->7|39->8|39->8|39->8|39->8|44->13|44->13|44->13|45->14|45->14|45->14|53->22|53->22|53->22|54->23|54->23|55->24|60->29
                    -- GENERATED --
                */
            