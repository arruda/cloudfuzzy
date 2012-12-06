
package views.html.h5bp

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
object index extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template9[String,String,Html,Html,Html,Html,Html,Html,Html,play.api.templates.Html] {

    /**/
    def apply/*1.2*/(title: String)(description: String)(cssExtra: Html)(jsExtra : Html)(jquery_docready : Html)(jquery_winload : Html)(header: Html)(content: Html)(footer: Html):play.api.templates.Html = {
        _display_ {

Seq[Any](format.raw/*1.160*/("""

<!doctype html>
<!-- paulirish.com/2008/conditional-stylesheets-vs-css-hacks-answer-neither/ -->
<!--[if lt IE 7]> <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang="en"> <![endif]-->
<!--[if IE 7]>    <html class="no-js lt-ie9 lt-ie8" lang="en"> <![endif]-->
<!--[if IE 8]>    <html class="no-js lt-ie9" lang="en"> <![endif]-->
<!-- Consider adding a manifest.appcache: h5bp.com/d/Offline -->
<!--[if gt IE 8]><!--> <html class="no-js" lang="en"> <!--<![endif]-->
<head>
  <meta charset="utf-8">

  <!-- Use the .htaccess and remove these lines to avoid edge case issues.
       More info: h5bp.com/i/378 -->
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

  <title>"""),_display_(Seq[Any](/*17.11*/title)),format.raw/*17.16*/("""</title>
  <meta name="description" content=""""),_display_(Seq[Any](/*18.38*/description)),format.raw/*18.49*/("""">

  <!-- Mobile viewport optimized: h5bp.com/viewport -->
  <meta name="viewport" content="width=device-width">

  <!-- Place favicon.ico and apple-touch-icon.png in the root directory: mathiasbynens.be/notes/touch-icons -->

  <link rel="stylesheet" media="screen" href=""""),_display_(Seq[Any](/*25.48*/routes/*25.54*/.Assets.at("stylesheets/style.css"))),format.raw/*25.89*/("""">

  """),format.raw/*29.8*/("""
  """),_display_(Seq[Any](/*30.4*/cssExtra)),format.raw/*30.12*/("""

  <!-- More ideas for your <head> here: h5bp.com/d/head-Tips -->

  <!-- All JavaScript at the bottom, except this Modernizr build.
       Modernizr enables HTML5 elements & feature detects for optimal performance.
       Create your own custom Modernizr build: www.modernizr.com/download/ -->
  <script src=""""),_display_(Seq[Any](/*37.17*/routes/*37.23*/.Assets.at("javascripts/modernizr-2.5.3.min.js"))),format.raw/*37.71*/("""" type="text/javascript"></script>   
<!-- Grab Google CDN's jQuery, with a protocol relative URL; fall back to local if offline -->
  <script src="//ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script> 
  <script>window.jQuery || document.write('<script src=""""),_display_(Seq[Any](/*40.58*/routes/*40.64*/.Assets.at("javascripts/jquery-1.7.1.min.js"))),format.raw/*40.109*/(""""><\/script>')</script>
  
</head>
<body>
  <!-- Prompt IE 6 users to install Chrome Frame. Remove this if you support IE 6.
       chromium.org/developers/how-tos/chrome-frame-getting-started -->
  <!--[if lt IE 7]><p class=chromeframe>Your browser is <em>ancient!</em> <a href="http://browsehappy.com/">Upgrade to a different browser</a> or <a href="http://www.google.com/chromeframe/?redirect=true">install Google Chrome Frame</a> to experience this site.</p><![endif]-->
  <header>
    """),_display_(Seq[Any](/*48.6*/header)),format.raw/*48.12*/("""
  </header>
  <div id="container"   class="clearfix"  role="main">
    """),_display_(Seq[Any](/*51.6*/content)),format.raw/*51.13*/("""
  </div>
  <footer>
    """),_display_(Seq[Any](/*54.6*/footer)),format.raw/*54.12*/("""
  </footer>


  <!-- JavaScript at the bottom for fast page loading -->

  

  """),format.raw/*64.9*/("""
  """),_display_(Seq[Any](/*65.4*/jsExtra)),format.raw/*65.11*/("""


  """),format.raw/*70.8*/("""
  <script type="text/javascript">
    $(document).ready(function() """),format.raw("""{"""),format.raw/*72.35*/("""
      """),_display_(Seq[Any](/*73.8*/jquery_docready)),format.raw/*73.23*/("""
    """),format.raw("""}"""),format.raw/*74.6*/(""");
    $(window).load(function() """),format.raw("""{"""),format.raw/*75.32*/("""
      """),_display_(Seq[Any](/*76.8*/jquery_winload)),format.raw/*76.22*/("""
    """),format.raw("""}"""),format.raw/*77.6*/(""");     
  </script>  
  <!-- end scripts -->

  <!-- Asynchronous Google Analytics snippet. Change UA-XXXXX-X to be your site's ID.
       mathiasbynens.be/notes/async-analytics-snippet -->
  <script>
    // var _gaq=[['_setAccount','UA-XXXXX-X'],['_trackPageview']];
    // (function(d,t)"""),format.raw("""{"""),format.raw/*85.23*/("""var g=d.createElement(t),s=d.getElementsByTagName(t)[0];
    // g.src=('https:'==location.protocol?'//ssl':'//www')+'.google-analytics.com/ga.js';
    // s.parentNode.insertBefore(g,s)"""),format.raw("""}"""),format.raw/*87.39*/("""(document,'script'));
  </script>
</body>
</html>"""))}
    }
    
    def render(title:String,description:String,cssExtra:Html,jsExtra:Html,jquery_docready:Html,jquery_winload:Html,header:Html,content:Html,footer:Html) = apply(title)(description)(cssExtra)(jsExtra)(jquery_docready)(jquery_winload)(header)(content)(footer)
    
    def f:((String) => (String) => (Html) => (Html) => (Html) => (Html) => (Html) => (Html) => (Html) => play.api.templates.Html) = (title) => (description) => (cssExtra) => (jsExtra) => (jquery_docready) => (jquery_winload) => (header) => (content) => (footer) => apply(title)(description)(cssExtra)(jsExtra)(jquery_docready)(jquery_winload)(header)(content)(footer)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Tue Oct 30 10:43:50 BRST 2012
                    SOURCE: /home/arruda/projetos/cloudfuzzy/cloudfuzzy/app/views/h5bp/index.scala.html
                    HASH: 0c3ddbad9a3cd51284eee59edb5f3870c8ede284
                    MATRIX: 802->1|1038->159|1758->843|1785->848|1867->894|1900->905|2211->1180|2226->1186|2283->1221|2316->1268|2355->1272|2385->1280|2733->1592|2748->1598|2818->1646|3130->1922|3145->1928|3213->1973|3739->2464|3767->2470|3875->2543|3904->2550|3965->2576|3993->2582|4100->2688|4139->2692|4168->2699|4200->2781|4316->2850|4359->2858|4396->2873|4448->2879|4529->2913|4572->2921|4608->2935|4660->2941|4997->3231|5229->3416
                    LINES: 27->1|30->1|46->17|46->17|47->18|47->18|54->25|54->25|54->25|56->29|57->30|57->30|64->37|64->37|64->37|67->40|67->40|67->40|75->48|75->48|78->51|78->51|81->54|81->54|89->64|90->65|90->65|93->70|95->72|96->73|96->73|97->74|98->75|99->76|99->76|100->77|108->85|110->87
                    -- GENERATED --
                */
            