
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
object main extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template4[String,Html,Html,Html,play.api.templates.Html] {

    /**/
    def apply/*1.2*/(title: String)(navHeader: Html)(menu: Html)(content: Html):play.api.templates.Html = {
        _display_ {
def /*9.2*/cssExtra/*9.10*/:play.api.templates.Html = {_display_(

Seq[Any](format.raw/*9.14*/("""
    """),format.raw/*11.116*/("""
    <link rel="stylesheet" href="http://code.jquery.com/ui/1.9.1/themes/base/jquery-ui.css" />
    <link rel="stylesheet" media="screen" href=""""),_display_(Seq[Any](/*13.50*/routes/*13.56*/.Assets.at("stylesheets/main.css"))),format.raw/*13.90*/("""">
    <link rel="icon" href=""""),_display_(Seq[Any](/*14.29*/routes/*14.35*/.Assets.at("favicon.ico"))),format.raw/*14.60*/("""" type="image/x-icon">
""")))};def /*17.2*/jsExtra/*17.9*/:play.api.templates.Html = {_display_(

Seq[Any](format.raw/*17.13*/("""
    <script src=""""),_display_(Seq[Any](/*18.19*/routes/*18.25*/.Assets.at("javascripts/bootstrap-button.js"))),format.raw/*18.70*/("""" type="text/javascript"></script>
    """),format.raw/*21.9*/("""
    <script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>
    
    """),format.raw/*26.9*/("""
    <script src=""""),_display_(Seq[Any](/*27.19*/routes/*27.25*/.Assets.at("javascripts/flot/jquery.flot.js"))),format.raw/*27.70*/("""" type="text/javascript"></script>
    <script  src=""""),_display_(Seq[Any](/*28.20*/routes/*28.26*/.Assets.at("javascripts/flot/jquery.flot.axislabels.js"))),format.raw/*28.82*/("""" type="text/javascript"></script>
    
<!--  
<script src=""""),_display_(Seq[Any](/*31.15*/routes/*31.21*/.Assets.at("javascripts/jquery-cookie/jquery.cookie.js"))),format.raw/*31.77*/("""" type="text/javascript"></script>
    <script src=""""),_display_(Seq[Any](/*32.19*/routes/*32.25*/.Assets.at("javascripts/ajax_functions.js"))),format.raw/*32.68*/("""" type="text/javascript"></script>
-->
    """),format.raw/*37.9*/("""

""")))};def /*41.2*/footer/*41.8*/:play.api.templates.Html = {_display_(

Seq[Any](format.raw/*41.12*/("""
    <p>CloudFuzzy - The Online Fuzzy Toolbox Solution.</p>
""")))};def /*45.2*/jquery_docready/*45.17*/:play.api.templates.Html = {_display_(

Seq[Any](format.raw/*45.21*/("""

    //This is the login ajax call
    $('#login').click(function() """),format.raw("""{"""),format.raw/*48.35*/("""
      $('#content').load('/login #content');
    """),format.raw("""}"""),format.raw/*50.6*/(""");
      
    var do_login = function()"""),format.raw("""{"""),format.raw/*52.31*/("""
      $.ajax("""),format.raw("""{"""),format.raw/*53.15*/("""
        type: 'POST',
        url : '/login',
        dataType : 'json',
        data: $("#login_form").serialize(),
        success: function(data, textStatus, jqXHR) """),format.raw("""{"""),format.raw/*58.53*/("""                  
          window.location.href = '"""),_display_(Seq[Any](/*59.36*/routes/*59.42*/.Application.index)),format.raw/*59.60*/("""';
        """),format.raw("""}"""),format.raw/*60.10*/(""",
        error: function(jqXHR, textStatus, errorThrown) """),format.raw("""{"""),format.raw/*61.58*/("""
          //$('#content').load('/login #content');
        """),format.raw("""}"""),format.raw/*63.10*/("""
      """),format.raw("""}"""),format.raw/*64.8*/(""");

    """),format.raw("""}"""),format.raw/*66.6*/("""

    $("#login_post").live("click", function(event)"""),format.raw("""{"""),format.raw/*68.52*/("""
        event.preventDefault();
        do_login();
    """),format.raw("""}"""),format.raw/*71.6*/(""");


""")))};def /*76.2*/jquery_winload/*76.16*/:play.api.templates.Html = {_display_(

Seq[Any](format.raw/*76.20*/("""

""")))};
Seq[Any](format.raw/*1.61*/("""

"""),format.raw/*6.5*/("""


"""),format.raw/*15.2*/("""

"""),format.raw/*39.2*/("""

"""),format.raw/*43.2*/("""

"""),format.raw/*74.2*/("""

"""),format.raw/*78.2*/("""

"""),_display_(Seq[Any](/*80.2*/h5bp/*80.6*/.index("CloudFuzzy - " + title)("CloudFuzzy - The Online Fuzzy Toolbox Solution.")(cssExtra)(jsExtra)(jquery_docready)(jquery_winload)(header_slice())/*80.156*/{_display_(Seq[Any](format.raw/*80.157*/("""
    <nav id='menu' >
        <ul>
            """),_display_(Seq[Any](/*83.14*/menu)),format.raw/*83.18*/("""
        </ul>
    </nav> 

    <div id='content_area'>
      <div id='content'>
        <h2 class='content_header'>"""),_display_(Seq[Any](/*89.37*/navHeader)),format.raw/*89.46*/("""</h2>
        """),_display_(Seq[Any](/*90.10*/content)),format.raw/*90.17*/("""
      </div>  
    </div>
        
""")))}(footer))))}
    }
    
    def render(title:String,navHeader:Html,menu:Html,content:Html) = apply(title)(navHeader)(menu)(content)
    
    def f:((String) => (Html) => (Html) => (Html) => play.api.templates.Html) = (title) => (navHeader) => (menu) => (content) => apply(title)(navHeader)(menu)(content)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Tue Nov 06 10:18:05 BRST 2012
                    SOURCE: /home/arruda/projetos/cloudfuzzy/cloudfuzzy/app/views/main.scala.html
                    HASH: 053bf5fab0e040d8df45e7f9546b0499168f794d
                    MATRIX: 769->1|888->213|904->221|971->225|1005->441|1186->586|1201->592|1257->626|1324->657|1339->663|1386->688|1433->715|1448->722|1516->726|1571->745|1586->751|1653->796|1719->883|1829->1011|1884->1030|1899->1036|1966->1081|2056->1135|2071->1141|2149->1197|2246->1258|2261->1264|2339->1320|2428->1373|2443->1379|2508->1422|2578->1583|2604->1589|2618->1595|2686->1599|2770->1663|2794->1678|2862->1682|2979->1752|3076->1803|3163->1843|3225->1858|3442->2028|3532->2082|3547->2088|3587->2106|3646->2118|3752->2177|3860->2238|3914->2246|3969->2255|4069->2308|4173->2366|4202->2375|4225->2389|4293->2393|4335->60|4363->209|4393->712|4422->1586|4451->1660|4480->2372|4509->2396|4547->2399|4559->2403|4719->2553|4759->2554|4843->2602|4869->2606|5022->2723|5053->2732|5104->2747|5133->2754
                    LINES: 27->1|29->9|29->9|31->9|32->11|34->13|34->13|34->13|35->14|35->14|35->14|36->17|36->17|38->17|39->18|39->18|39->18|40->21|43->26|44->27|44->27|44->27|45->28|45->28|45->28|48->31|48->31|48->31|49->32|49->32|49->32|51->37|53->41|53->41|55->41|57->45|57->45|59->45|62->48|64->50|66->52|67->53|72->58|73->59|73->59|73->59|74->60|75->61|77->63|78->64|80->66|82->68|85->71|88->76|88->76|90->76|93->1|95->6|98->15|100->39|102->43|104->74|106->78|108->80|108->80|108->80|108->80|111->83|111->83|117->89|117->89|118->90|118->90
                    -- GENERATED --
                */
            