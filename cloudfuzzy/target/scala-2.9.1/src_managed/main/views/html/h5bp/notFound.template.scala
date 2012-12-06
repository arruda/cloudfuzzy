
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
object notFound extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template1[String,play.api.templates.Html] {

    /**/
    def apply/*1.2*/(title: String):play.api.templates.Html = {
        _display_ {

Seq[Any](format.raw/*1.17*/("""

<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>"""),_display_(Seq[Any](/*7.11*/title)),format.raw/*7.16*/("""</title>
  <style>
    ::-moz-selection """),format.raw("""{"""),format.raw/*9.23*/(""" background: #fe57a1; color: #fff; text-shadow: none; """),format.raw("""}"""),format.raw/*9.78*/("""
    ::selection """),format.raw("""{"""),format.raw/*10.18*/(""" background: #fe57a1; color: #fff; text-shadow: none; """),format.raw("""}"""),format.raw/*10.73*/("""
    html """),format.raw("""{"""),format.raw/*11.11*/(""" padding: 30px 10px; font-size: 20px; line-height: 1.4; color: #737373; background: #f0f0f0; -webkit-text-size-adjust: 100%; -ms-text-size-adjust: 100%; """),format.raw("""}"""),format.raw/*11.165*/("""
    html, input """),format.raw("""{"""),format.raw/*12.18*/(""" font-family: "Helvetica Neue", Helvetica, Arial, sans-serif; """),format.raw("""}"""),format.raw/*12.81*/("""
    body """),format.raw("""{"""),format.raw/*13.11*/(""" max-width: 500px; _width: 500px; padding: 30px 20px 50px; border: 1px solid #b3b3b3; border-radius: 4px; margin: 0 auto; box-shadow: 0 1px 10px #a7a7a7, inset 0 1px 0 #fff; background: #fcfcfc; """),format.raw("""}"""),format.raw/*13.207*/("""
    h1 """),format.raw("""{"""),format.raw/*14.9*/(""" margin: 0 10px; font-size: 50px; text-align: center; """),format.raw("""}"""),format.raw/*14.64*/("""
    h1 span """),format.raw("""{"""),format.raw/*15.14*/(""" color: #bbb; """),format.raw("""}"""),format.raw/*15.29*/("""
    h3 """),format.raw("""{"""),format.raw/*16.9*/(""" margin: 1.5em 0 0.5em; """),format.raw("""}"""),format.raw/*16.34*/("""
    p """),format.raw("""{"""),format.raw/*17.8*/(""" margin: 1em 0; """),format.raw("""}"""),format.raw/*17.25*/("""
    ul """),format.raw("""{"""),format.raw/*18.9*/(""" padding: 0 0 0 40px; margin: 1em 0; """),format.raw("""}"""),format.raw/*18.47*/("""
    .container """),format.raw("""{"""),format.raw/*19.17*/(""" max-width: 380px; _width: 380px; margin: 0 auto; """),format.raw("""}"""),format.raw/*19.68*/("""
    /* google search */
    #goog-fixurl ul """),format.raw("""{"""),format.raw/*21.22*/(""" list-style: none; padding: 0; margin: 0; """),format.raw("""}"""),format.raw/*21.65*/("""
    #goog-fixurl form """),format.raw("""{"""),format.raw/*22.24*/(""" margin: 0; """),format.raw("""}"""),format.raw/*22.37*/("""
    #goog-wm-qt, #goog-wm-sb """),format.raw("""{"""),format.raw/*23.31*/(""" border: 1px solid #bbb; font-size: 16px; line-height: normal; vertical-align: top; color: #444; border-radius: 2px; """),format.raw("""}"""),format.raw/*23.149*/("""
    #goog-wm-qt """),format.raw("""{"""),format.raw/*24.18*/(""" width: 220px; height: 20px; padding: 5px; margin: 5px 10px 0 0; box-shadow: inset 0 1px 1px #ccc; """),format.raw("""}"""),format.raw/*24.118*/("""
    #goog-wm-sb """),format.raw("""{"""),format.raw/*25.18*/(""" display: inline-block; height: 32px; padding: 0 10px; margin: 5px 0 0; white-space: nowrap; cursor: pointer; background-color: #f5f5f5; background-image: -webkit-linear-gradient(rgba(255,255,255,0), #f1f1f1); background-image: -moz-linear-gradient(rgba(255,255,255,0), #f1f1f1); background-image: -ms-linear-gradient(rgba(255,255,255,0), #f1f1f1); background-image: -o-linear-gradient(rgba(255,255,255,0), #f1f1f1); -webkit-appearance: none; -moz-appearance: none; appearance: none; *overflow: visible; *display: inline; *zoom: 1; """),format.raw("""}"""),format.raw/*25.551*/("""
    #goog-wm-sb:hover, #goog-wm-sb:focus """),format.raw("""{"""),format.raw/*26.43*/(""" border-color: #aaa; box-shadow: 0 1px 1px rgba(0, 0, 0, 0.1); background-color: #f8f8f8; """),format.raw("""}"""),format.raw/*26.134*/("""
    #goog-wm-qt:focus, #goog-wm-sb:focus """),format.raw("""{"""),format.raw/*27.43*/(""" border-color: #105cb6; outline: 0; color: #222; """),format.raw("""}"""),format.raw/*27.93*/("""
    input::-moz-focus-inner """),format.raw("""{"""),format.raw/*28.30*/(""" padding: 0; border: 0; """),format.raw("""}"""),format.raw/*28.55*/("""
  </style>
</head>
<body>
  <div class="container">
    <h1>Not found <span>:(</span></h1>
    <p>Sorry, but the page you were trying to view does not exist.</p>
    <p>It looks like this was the result of either:</p>
    <ul>
      <li>a mistyped address</li>
      <li>an out-of-date link</li>
    </ul>
    <script>
      var GOOG_FIXURL_LANG = (navigator.language || '').slice(0,2),GOOG_FIXURL_SITE = location.host;
    </script>
    <script src="http://linkhelp.clients.google.com/tbproxy/lh/wm/fixurl.js"></script>
  </div>

"""))}
    }
    
    def render(title:String) = apply(title)
    
    def f:((String) => play.api.templates.Html) = (title) => apply(title)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Tue Oct 30 10:43:50 BRST 2012
                    SOURCE: /home/arruda/projetos/cloudfuzzy/cloudfuzzy/app/views/h5bp/notFound.scala.html
                    HASH: a805f83eebca894a94ab0ec0ce30a2d27c324f53
                    MATRIX: 763->1|855->16|967->93|993->98|1080->139|1181->194|1246->212|1348->267|1406->278|1608->432|1673->450|1783->513|1841->524|2085->720|2140->729|2242->784|2303->798|2365->813|2420->822|2492->847|2546->855|2610->872|2665->881|2750->919|2814->936|2912->987|3005->1033|3095->1076|3166->1100|3226->1113|3304->1144|3470->1262|3535->1280|3683->1380|3748->1398|4329->1931|4419->1974|4558->2065|4648->2108|4745->2158|4822->2188|4894->2213
                    LINES: 27->1|30->1|36->7|36->7|38->9|38->9|39->10|39->10|40->11|40->11|41->12|41->12|42->13|42->13|43->14|43->14|44->15|44->15|45->16|45->16|46->17|46->17|47->18|47->18|48->19|48->19|50->21|50->21|51->22|51->22|52->23|52->23|53->24|53->24|54->25|54->25|55->26|55->26|56->27|56->27|57->28|57->28
                    -- GENERATED --
                */
            