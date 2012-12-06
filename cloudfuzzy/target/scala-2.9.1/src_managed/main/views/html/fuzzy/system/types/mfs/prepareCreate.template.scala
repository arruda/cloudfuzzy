
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
object prepareCreate extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template3[FuzzySystem,models.Type,Form[models.MF],play.api.templates.Html] {

    /**/
    def apply/*1.2*/(fuzzySystem : FuzzySystem, tp : models.Type, newMFForm : Form[models.MF]):play.api.templates.Html = {
        _display_ {import helper._


Seq[Any](format.raw/*1.76*/("""

"""),format.raw/*4.1*/("""
"""),_display_(Seq[Any](/*5.2*/fuzzy/*5.7*/.system.types.type_template(fuzzySystem, tp)/*5.51*/ {_display_(Seq[Any](format.raw/*5.53*/(""" 
    

    <div class="sub_content sub_limited">
        <h3 class='sub_content_header'>New Membership Function</h3> 
            """),_display_(Seq[Any](/*10.14*/helper/*10.20*/.form(action = routes.MFs.create(fuzzySystem.id,tp.id), 'id -> "newMFForm")/*10.95*/ {_display_(Seq[Any](format.raw/*10.97*/("""
                
                <fieldset>
                    <legend>General Information for the new MF</legend>
                    
                    """),_display_(Seq[Any](/*15.22*/inputText(
                        newMFForm("label"), 
                        '_label -> "Label", 
                        '_help -> "The label of this MF"
                    ))),format.raw/*19.22*/("""          
                    
                    """),_display_(Seq[Any](/*21.22*/select(
                        field = newMFForm("idFunction"),
                            options = options(models.MF.getMFTypes()),
                        '_label -> "Member Functions Type",
                        '_help -> "Select the function for this MF"
                    ))),format.raw/*26.22*/("""                        
                </fieldset>           
                
                <fieldset>
                    <legend>Parameters</legend>

                    <div id="params">
                    </div>

                    """),format.raw/*51.25*/("""
                
                <div class="actions">
                    <input type="submit" class="btn primary" value="Create">
                    <input type="button" class="btn secondary" value="Cancel"onclick="window.location = '"""),_display_(Seq[Any](/*55.107*/routes/*55.113*/.Types.detail(fuzzySystem.id, tp.id))),format.raw/*55.149*/("""' "/>
                </div>
                
            """)))})),format.raw/*58.14*/("""
 

    </div>
    <script type="text/javascript">

        $(document).ready(function() """),format.raw("""{"""),format.raw/*64.39*/("""

          $('#idFunction').change(getParamsForMf);
          getParamsForMf();
          //get parameters for a given mf type
          function getParamsForMf() """),format.raw("""{"""),format.raw/*69.38*/("""
            var idFunction = $('#idFunction');
            $.get('/ajax/get_num_params_mf',
                  """),format.raw("""{"""),format.raw/*72.20*/("""mf_key: idFunction.val()"""),format.raw("""}"""),format.raw/*72.45*/(""",
                  function(data) """),format.raw("""{"""),format.raw/*73.35*/("""
                    setParamsForMf(data);
                  """),format.raw("""}"""),format.raw/*75.20*/(""");
          """),format.raw("""}"""),format.raw/*76.12*/("""

          /* set parameters for a given mf type
          adding to the form the number of input equivalent to 
          */
          function setParamsForMf(num_params) """),format.raw("""{"""),format.raw/*81.48*/("""
                var paramsDiv = $('#params');
                var paramsHTML = '';

                """),_display_(Seq[Any](/*85.18*/if(newMFForm("params").hasErrors)/*85.51*/ {_display_(Seq[Any](format.raw/*85.53*/("""
                    """),_display_(Seq[Any](/*86.22*/newMFForm("params")/*86.41*/.errors().map/*86.54*/ { error =>_display_(Seq[Any](format.raw/*86.65*/("""
                     paramsHTML += '<dd class="error">"""),_display_(Seq[Any](/*87.56*/error/*87.61*/.message())),format.raw/*87.71*/("""</dd>'
                    """)))})),format.raw/*88.22*/("""
                """)))})),format.raw/*89.18*/("""
                
                for (var i = 0; i < num_params; i++) """),format.raw("""{"""),format.raw/*91.55*/("""
                    paramsHTML += getNewParamHtml(i);
                """),format.raw("""}"""),format.raw/*93.18*/(""";
                //window.alert(paramsHTML);
                // paramsDiv.fadeIn('fast');
                paramsDiv.html($(paramsHTML).fadeIn('fast'));

            
          """),format.raw("""}"""),format.raw/*99.12*/("""

          /* Creates the html for the new param input and label*/
          function getNewParamHtml(i)"""),format.raw("""{"""),format.raw/*102.39*/("""
            var letter = String.fromCharCode(97 + i);
            var paramHTML ='<dl class=""""),_display_(Seq[Any](/*104.41*/if(newMFForm("params").hasErrors)/*104.74*/ {_display_(Seq[Any](format.raw/*104.76*/("""error""")))})),format.raw/*104.82*/(""" " id="params_ID__field">\
                               <dt><label for="params_ID_">Param.  LETTER</label></dt>\
                                <dd><input type="text" id="params_ID_" name="params[ID]" value="" ></dd>'


                paramHTML += '</dl>'; 

            paramHTML = paramHTML.replace(/ID/g, i);
            paramHTML = paramHTML.replace(/LETTER/g, letter);


            // var paramHTML =   '<dl class=" " id="params_'+i+'__field">';

            //     paramHTML +=  '<label for="params_'+i+'_">params['+i+']</label>';
            //     paramHTML +=  '<input type="text" id="params_'+ i +'_" name="params['+ i +']" value="">';
                return paramHTML;
          """),format.raw("""}"""),format.raw/*120.12*/("""



        """),format.raw("""}"""),format.raw/*124.10*/(""");
                
    </script>
""")))})))}
    }
    
    def render(fuzzySystem:FuzzySystem,tp:models.Type,newMFForm:Form[models.MF]) = apply(fuzzySystem,tp,newMFForm)
    
    def f:((FuzzySystem,models.Type,Form[models.MF]) => play.api.templates.Html) = (fuzzySystem,tp,newMFForm) => apply(fuzzySystem,tp,newMFForm)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Mon Nov 26 13:28:54 BRST 2012
                    SOURCE: /home/arruda/projetos/cloudfuzzy/cloudfuzzy/app/views/fuzzy/system/types/mfs/prepareCreate.scala.html
                    HASH: fc6c8c091eb68a9ca6438325dedabaa157d1649b
                    MATRIX: 819->1|986->75|1014->94|1050->96|1062->101|1114->145|1153->147|1321->279|1336->285|1420->360|1460->362|1655->521|1856->700|1945->753|2252->1038|2523->1874|2799->2113|2815->2119|2874->2155|2965->2214|3102->2304|3314->2469|3473->2581|3545->2606|3628->2642|3737->2704|3798->2718|4019->2892|4157->2994|4199->3027|4239->3029|4297->3051|4325->3070|4347->3083|4396->3094|4488->3150|4502->3155|4534->3165|4594->3193|4644->3211|4763->3283|4882->3355|5107->3533|5261->3639|5393->3734|5436->3767|5477->3769|5516->3775|6260->4471|6321->4484
                    LINES: 27->1|31->1|33->4|34->5|34->5|34->5|34->5|39->10|39->10|39->10|39->10|44->15|48->19|50->21|55->26|64->51|68->55|68->55|68->55|71->58|77->64|82->69|85->72|85->72|86->73|88->75|89->76|94->81|98->85|98->85|98->85|99->86|99->86|99->86|99->86|100->87|100->87|100->87|101->88|102->89|104->91|106->93|112->99|115->102|117->104|117->104|117->104|117->104|133->120|137->124
                    -- GENERATED --
                */
            