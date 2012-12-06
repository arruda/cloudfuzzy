
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
object index extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template0[play.api.templates.Html] {

    /**/
    def apply/*1.2*/():play.api.templates.Html = {
        _display_ {
def /*3.2*/navHeader/*3.11*/:play.api.templates.Html = {_display_(

Seq[Any](format.raw/*3.15*/("""
    Cloud Fuzzy
""")))};
Seq[Any](format.raw/*1.4*/("""

"""),format.raw/*5.2*/("""  

"""),_display_(Seq[Any](/*7.2*/main("Welcome to Play 2.0")( navHeader )(menu_default())/*7.58*/ {_display_(Seq[Any](format.raw/*7.60*/("""

    <p>Bacon ipsum dolor sit amet turkey chuck jowl short loin.  Capicola pastrami pig tenderloin pancetta sausage andouille jowl boudin flank short ribs pork loin shoulder meatloaf chicken.  Hamburger flank salami bresaola, tongue prosciutto beef.  Tongue jowl tenderloin sausage swine bresaola.</p>
    <p>Tri-tip cow meatloaf t-bone turducken, chuck boudin bacon ground round salami corned beef beef ribs pork chop.  Shankle bresaola jerky pancetta chicken sausage.  Fatback bresaola capicola strip steak turkey sausage.  Ball tip fatback bacon, brisket sausage tail spare ribs shoulder beef beef ribs turducken bresaola.</p>
    <p>Leberkas pork belly brisket bresaola tail ground round.  Pancetta pork loin t-bone, meatball filet mignon venison rump turkey ribeye corned beef pig brisket sirloin.  Ribeye ham hock frankfurter ham shank pork chop.  Ball tip shank beef salami venison brisket.</p><p>Kielbasa tongue andouille bacon pork loin shoulder.  Pork chop t-bone cow, pork belly spare ribs chicken turducken rump drumstick short ribs turkey shoulder boudin.  Ham hock cow filet mignon flank, bresaola sausage shoulder.  Fatback tail sirloin capicola meatloaf flank hamburger pancetta short loin venison chuck beef ribs boudin drumstick sausage.</p>
    <p>Ball tip pork salami capicola, tongue biltong shank shankle.  Ham andouille hamburger meatball beef ribs sausage corned beef pork loin ribeye t-bone.  Tail shank chuck, beef meatloaf turducken capicola.  Prosciutto chicken kielbasa rump tri-tip.</p>
    <p>Capicola short ribs rump, boudin biltong shank jerky spare ribs prosciutto short loin meatball turkey.  Andouille tongue strip steak biltong flank ribeye.  Prosciutto tenderloin turkey, kielbasa chuck bresaola spare ribs venison filet mignon pork belly salami short ribs jerky.  Cow tri-tip beef ribs tongue salami bacon kielbasa bresaola tenderloin meatloaf capicola.  Ham beef beef ribs bacon ribeye frankfurter.</p>
   

    <div class="sub_content sub_limited">
    <h3 class='sub_content_header'>Bla bla bla1</h3> 
    <p>Tongue jerky meatball beef ribs.  Pastrami hamburger kielbasa beef ribs, prosciutto spare ribs leberkas short ribs rump sausage filet mignon boudin capicola chicken shoulder.  T-bone shankle ribeye meatloaf short loin.  Turducken pancetta shoulder ball tip jerky rump short ribs swine bresaola ground round pork loin salami beef ribs flank hamburger.  Pastrami chuck drumstick ribeye boudin, shank prosciutto beef ribs meatball capicola frankfurter.  Kielbasa pork chop flank jerky hamburger corned beef, strip steak bacon rump.  Turducken meatball jerky bacon, short loin tenderloin drumstick pastrami rump hamburger shankle filet mignon pork loin sausage.</p><p>Ground round tenderloin bresaola beef ribs capicola pastrami sirloin.  Frankfurter pork chop tongue tenderloin.  Ham kielbasa salami capicola meatball hamburger short loin andouille ground round fatback meatloaf.  Cow prosciutto pastrami pig bresaola beef ribs spare ribs t-bone biltong meatball drumstick bacon.  Pork hamburger biltong pastrami ribeye frankfurter tongue.  Flank t-bone pork belly tongue short ribs.</p>
    </div>
    
    
 <p>Spare ribs short loin cow, prosciutto turducken t-bone pancetta shank flank tongue.  Drumstick shoulder turducken tail corned beef beef.  Beef ball tip turkey salami kielbasa andouille shankle jerky shank biltong.  Rump tongue bresaola pork belly pork chop.  Spare ribs chicken biltong, shank ground round tri-tip cow.  Pork chop sausage fatback cow pork belly prosciutto.  Pork chop shank swine, tail bresaola spare ribs tenderloin t-bone.</p>
    <p>Ball tip rump kielbasa chuck ham hock ground round turkey.  Jerky tail drumstick ground round.  Hamburger rump strip steak meatloaf, flank cow shankle.  Swine pork belly biltong andouille cow beef jerky.</p>

    <div class="sub_content sub_limited">
    <h3 class='sub_content_header'>Bla bla bla2</h3> 
    <p>Tongue jerky meatball beef ribs.  Pastrami hamburger kielbasa beef ribs, prosciutto spare ribs leberkas short ribs rump sausage filet mignon boudin capicola chicken shoulder.  T-bone shankle ribeye meatloaf short loin.  Turducken pancetta shoulder ball tip jerky rump short ribs swine bresaola ground round pork loin salami beef ribs flank hamburger.  Pastrami chuck drumstick ribeye boudin, shank prosciutto beef ribs meatball capicola frankfurter.  Kielbasa pork chop flank jerky hamburger corned beef, strip steak bacon rump.  Turducken meatball jerky bacon, short loin tenderloin drumstick pastrami rump hamburger shankle filet mignon pork loin sausage.</p><p>Ground round tenderloin bresaola beef ribs capicola pastrami sirloin.  Frankfurter pork chop tongue tenderloin.  Ham kielbasa salami capicola meatball hamburger short loin andouille ground round fatback meatloaf.  Cow prosciutto pastrami pig bresaola beef ribs spare ribs t-bone biltong meatball drumstick bacon.  Pork hamburger biltong pastrami ribeye frankfurter tongue.  Flank t-bone pork belly tongue short ribs.</p>
    </div>
    
        
""")))})))}
    }
    
    def render() = apply()
    
    def f:(() => play.api.templates.Html) = () => apply()
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Tue Nov 06 10:18:05 BRST 2012
                    SOURCE: /home/arruda/projetos/cloudfuzzy/cloudfuzzy/app/views/index.scala.html
                    HASH: e3abd9d5290da17a42fb3237d96e77a815254130
                    MATRIX: 748->1|810->6|827->15|894->19|950->3|978->37|1017->42|1081->98|1120->100
                    LINES: 27->1|29->3|29->3|31->3|34->1|36->5|38->7|38->7|38->7
                    -- GENERATED --
                */
            