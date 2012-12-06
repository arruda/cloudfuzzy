
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
object header_slice extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template0[play.api.templates.Html] {

    /**/
    def apply/*1.2*/():play.api.templates.Html = {
        _display_ {

Seq[Any](format.raw/*1.4*/("""
<div id='top_menu' class="clearfix" >
        <a href=""""),_display_(Seq[Any](/*3.19*/routes/*3.25*/.Application.index)),format.raw/*3.43*/(""""><h1 class="logo">CloudFuzzy</h1></a>
        <nav>
            <a href="#">About</a>            
            <a href="#">Blog</a>
            <a href="#">FAQs</a>
            <div id="container_login">
                """),_display_(Seq[Any](/*9.18*/if(session.get("email") != null)/*9.50*/{_display_(Seq[Any](format.raw/*9.51*/("""
                    <a id="user" href=""""),_display_(Seq[Any](/*10.41*/routes/*10.47*/.Application.index)),format.raw/*10.65*/("""">"""),_display_(Seq[Any](/*10.68*/session/*10.75*/.get("email"))),format.raw/*10.88*/("""</a>
                    <a id="logout" href=""""),_display_(Seq[Any](/*11.43*/routes/*11.49*/.Authentication.logout)),format.raw/*11.71*/("""" >Logout</a>
                """)))}/*12.19*/else/*12.23*/{_display_(Seq[Any](format.raw/*12.24*/("""
                    <a id="login" href=""""),_display_(Seq[Any](/*13.42*/routes/*13.48*/.Authentication.login)),format.raw/*13.69*/("""" onclick="return false;">Login</a>
                """)))})),format.raw/*14.18*/("""
            </div>
        </nav> 
       

</div>  """))}
    }
    
    def render() = apply()
    
    def f:(() => play.api.templates.Html) = () => apply()
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Tue Oct 30 10:43:49 BRST 2012
                    SOURCE: /home/arruda/projetos/cloudfuzzy/cloudfuzzy/app/views/header_slice.scala.html
                    HASH: 5a2479e6a38d155a714e2c0c4c5c8a08a004b488
                    MATRIX: 755->1|833->3|925->60|939->66|978->84|1234->305|1274->337|1312->338|1389->379|1404->385|1444->403|1483->406|1499->413|1534->426|1617->473|1632->479|1676->501|1726->533|1739->537|1778->538|1856->580|1871->586|1914->607|1999->660
                    LINES: 27->1|30->1|32->3|32->3|32->3|38->9|38->9|38->9|39->10|39->10|39->10|39->10|39->10|39->10|40->11|40->11|40->11|41->12|41->12|41->12|42->13|42->13|42->13|43->14
                    -- GENERATED --
                */
            