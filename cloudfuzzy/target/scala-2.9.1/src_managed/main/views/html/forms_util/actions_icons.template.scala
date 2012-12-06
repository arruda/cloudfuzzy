
package views.html.forms_util

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
object actions_icons extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template2[String,String,play.api.templates.Html] {

    /**/
    def apply/*1.2*/(editUrl : String, deleteUrl: String):play.api.templates.Html = {
        _display_ {

Seq[Any](format.raw/*1.39*/("""
<a href=""""),_display_(Seq[Any](/*2.11*/editUrl)),format.raw/*2.18*/("""" class="edit" title="Edit" alt="Edit"></a>
<a href=""""),_display_(Seq[Any](/*3.11*/deleteUrl)),format.raw/*3.20*/("""" class="delete" title="Delete" alt="Delete"></a>"""))}
    }
    
    def render(editUrl:String,deleteUrl:String) = apply(editUrl,deleteUrl)
    
    def f:((String,String) => play.api.templates.Html) = (editUrl,deleteUrl) => apply(editUrl,deleteUrl)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Mon Nov 26 13:28:54 BRST 2012
                    SOURCE: /home/arruda/projetos/cloudfuzzy/cloudfuzzy/app/views/forms_util/actions_icons.scala.html
                    HASH: 7a7f3f8aafcbbaec45e669dce90bad8ad0146454
                    MATRIX: 781->1|895->38|941->49|969->56|1058->110|1088->119
                    LINES: 27->1|30->1|31->2|31->2|32->3|32->3
                    -- GENERATED --
                */
            