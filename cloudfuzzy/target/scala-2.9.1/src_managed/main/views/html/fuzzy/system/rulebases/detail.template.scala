
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
object detail extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template2[FuzzySystem,models.RuleBase,play.api.templates.Html] {

    /**/
    def apply/*1.2*/(fuzzySystem : FuzzySystem, rb : models.RuleBase):play.api.templates.Html = {
        _display_ {import helper._

def /*6.2*/navHeader/*6.11*/:play.api.templates.Html = {_display_(

Seq[Any](format.raw/*6.15*/("""
    """),_display_(Seq[Any](/*7.6*/fuzzySystem/*7.17*/.name)),format.raw/*7.22*/(""" + " -> " + """),_display_(Seq[Any](/*7.35*/rb/*7.37*/.name)),format.raw/*7.42*/("""
""")))};
Seq[Any](format.raw/*1.51*/("""

"""),format.raw/*4.1*/("""
  
"""),format.raw/*8.2*/("""  

"""),_display_(Seq[Any](/*10.2*/fuzzy/*10.7*/.system.rulebases.rulebase_template(fuzzySystem, rb)/*10.59*/{_display_(Seq[Any](format.raw/*10.60*/("""   
    
    <div class="sub_full">
        <div class="sub_lefted">
            <div class="sub_content sub_limited">
                <h3 class='sub_content_header'>General Information</h3>

                <p>Name: """),_display_(Seq[Any](/*17.27*/rb/*17.29*/.name)),format.raw/*17.34*/("""</p>

                <p>Operator Set: """),_display_(Seq[Any](/*19.35*/FuzzySystem/*19.46*/.getAvailableOperatorSetForFuzzySystem(fuzzySystem)(rb.idOperatorSet))),format.raw/*19.115*/("""</p>
                
            </div>
        </div>
        <div class="sub_righted">

            <div class="sub_content sub_limited">
                <h3 class='sub_content_header'>Input Variables</h3>         
                <div id="input_vars">       
                    """),_display_(Seq[Any](/*28.22*/if(rb.inputvars != null)/*28.46*/{_display_(Seq[Any](format.raw/*28.47*/("""
                        <ul>
                        """),_display_(Seq[Any](/*30.26*/for( i <- 0 to rb.inputvars.length-1 ) yield /*30.64*/ {_display_(Seq[Any](format.raw/*30.66*/("""
                            <li>    
                                """),_display_(Seq[Any](/*32.34*/rb/*32.36*/.inputvars(i).name)),format.raw/*32.54*/(""" """),_display_(Seq[Any](/*32.56*/forms_util/*32.66*/.actions_icons("#",routes.RuleBases.deleteVariable(fuzzySystem.id,rb.id,i, models.Variable.INPUT).toString()))),format.raw/*32.175*/("""
                            </li>
                         """)))})),format.raw/*34.27*/("""
                        </ul>
                    """)))})),format.raw/*36.22*/("""
                </div>  
                <input id="new_ivar" type="button" class="btn secondary" value="Add New Variable" />

            </div>
            <div class="sub_content sub_limited">
                <h3 class='sub_content_header'>Output Variables</h3>
                <div id="output_vars">
                    """),_display_(Seq[Any](/*44.22*/if(rb.outputvars != null)/*44.47*/{_display_(Seq[Any](format.raw/*44.48*/("""
                        <ul>
                        """),_display_(Seq[Any](/*46.26*/for( i <- 0 to rb.outputvars.length-1 ) yield /*46.65*/ {_display_(Seq[Any](format.raw/*46.67*/("""
                            <li>    
                                """),_display_(Seq[Any](/*48.34*/rb/*48.36*/.outputvars(i).name)),format.raw/*48.55*/("""  """),_display_(Seq[Any](/*48.58*/forms_util/*48.68*/.actions_icons("#",routes.RuleBases.deleteVariable(fuzzySystem.id,rb.id,i, models.Variable.OUTPUT).toString()))),format.raw/*48.178*/("""
                            </li>
                         """)))})),format.raw/*50.27*/("""
                        </ul>
                    """)))})),format.raw/*52.22*/("""
                </div>  
                <input id="new_ovar" type="button" class="btn secondary" value="Add New Variable" />

            </div>
            <div id="var-dialog-modal" title="New Variable">
                <form id="variable-dl-form">
                    <ul style="list-style:none;">
                        <li>
                            <label for="name">Name</label>
                            <input type="text" id="var_name" name="name" value="" >
                        </li>
                        <li>
                            <label for="idType">Type</label></br>
                            <select id="var_idType" name="idType" >

                                """),_display_(Seq[Any](/*68.34*/for( i <- 0 to fuzzySystem.getSpecification().getTypes().length-1 ) yield /*68.101*/ {_display_(Seq[Any](format.raw/*68.103*/("""
                                
                                        <option value=""""),_display_(Seq[Any](/*70.57*/i)),format.raw/*70.58*/("""" >
                                            """),_display_(Seq[Any](/*71.46*/fuzzySystem/*71.57*/.getSpecification().getTypes()(i)/*71.90*/.getName())),format.raw/*71.100*/("""
                                        </option>
                                        
                                 """)))})),format.raw/*74.35*/("""
                            </select>
                        </li>
                        <li>
                            <input type="hidden" id="var_kind" name="kind" value="" > 
                        </li>
                    </ul>
                </form>
            </div>
        </div>
    </div>

    <div class="sub_full">
        <div class="sub_content">
            <h3 class='sub_content_header'>Rules</h3>
            """),_display_(Seq[Any](/*89.14*/rules_as_table(fuzzySystem, rb))),format.raw/*89.45*/("""
        </div>
    </div>

    <script type="text/javascript">

        $(document).ready(function() """),format.raw("""{"""),format.raw/*95.39*/("""

        	var num_ivars = 0;
        	"""),_display_(Seq[Any](/*98.11*/if(rb.inputvars != null)/*98.35*/{_display_(Seq[Any](format.raw/*98.36*/("""
	            num_ivars = """),_display_(Seq[Any](/*99.27*/rb/*99.29*/.inputvars.length)),format.raw/*99.46*/(""";
        	""")))})),format.raw/*100.11*/("""
        	var num_ovars = 0;
        	"""),_display_(Seq[Any](/*102.11*/if(rb.outputvars != null)/*102.36*/{_display_(Seq[Any](format.raw/*102.37*/("""
	            num_ovars = """),_display_(Seq[Any](/*103.27*/rb/*103.29*/.outputvars.length)),format.raw/*103.47*/(""";
        	""")))})),format.raw/*104.11*/("""

            $( "#var-dialog-modal" ).dialog("""),format.raw("""{"""),format.raw/*106.46*/("""
                autoOpen: false,
                modal: true,
                buttons: """),format.raw("""{"""),format.raw/*109.27*/("""
                    Ok: function() """),format.raw("""{"""),format.raw/*110.37*/("""
                        var new_var_form = $( this ).find("#variable-dl-form");

                        postNewVar(function(data)"""),format.raw("""{"""),format.raw/*113.51*/("""
                            console.log(data);



                            //add the new var to the list
                            var var_list = $("#input_vars");
                            if(new_var_form.find("[name='kind']").val() == """),_display_(Seq[Any](/*120.77*/Variable/*120.85*/.OUTPUT)),format.raw/*120.92*/(""")"""),format.raw("""{"""),format.raw/*120.94*/("""
                                var var_list = $("#output_vars");
                            """),format.raw("""}"""),format.raw/*122.30*/("""
                            var_list = var_list.find("ul");
                            var_list.append("<li>" + new_var_form.find("[name='name']").val() + "</li>");

                            $( "#var-dialog-modal" ).dialog( "close" );
                            location.reload();

                        """),format.raw("""}"""),format.raw/*129.26*/(""");

                    """),format.raw("""}"""),format.raw/*131.22*/(""",
                    Cancel: function() """),format.raw("""{"""),format.raw/*132.41*/("""
                        $( this ).dialog( "close" );
                    """),format.raw("""}"""),format.raw/*134.22*/("""
                """),format.raw("""}"""),format.raw/*135.18*/(""",
                close: function() """),format.raw("""{"""),format.raw/*136.36*/("""                   
                    $( this ).dialog( "close" );
                    var new_var_form = $( this ).find("#variable-dl-form");

                    new_var_form.find("input").val("");
                    new_var_form.find(".error").remove();   
                """),format.raw("""}"""),format.raw/*142.18*/("""
            """),format.raw("""}"""),format.raw/*143.14*/(""");

            
            /* Add a new variable to the form, passing if its an isInputVar */
            function addVariableDialog(isInputVar)"""),format.raw("""{"""),format.raw/*147.52*/("""
                var dialog = $( "#var-dialog-modal" );
                var new_var_form = $("#variable-dl-form");

                var title = "Create New KIND Variable"
                if(isInputVar)"""),format.raw("""{"""),format.raw/*152.32*/("""
                    title = title.replace(/KIND/g, "Input");
                    //sets the val of the kind find  to be the correct one.
                    //based on xfuzzy definition
                    new_var_form.find("[name='kind']").val("""),_display_(Seq[Any](/*156.61*/models/*156.67*/.Variable.INPUT)),format.raw/*156.82*/(""");
                """),format.raw("""}"""),format.raw/*157.18*/("""
                else"""),format.raw("""{"""),format.raw/*158.22*/("""
                    title = title.replace(/KIND/g, "Output");         
                    //sets the val of the kind find  to be the correct one.
                    //based on xfuzzy definition          
                    new_var_form.find("[name='kind']").val("""),_display_(Seq[Any](/*162.61*/models/*162.67*/.Variable.OUTPUT)),format.raw/*162.83*/("""); 
                """),format.raw("""}"""),format.raw/*163.18*/("""
                $('.ui-dialog-title').html(title);  

                dialog.dialog( "open" );    
            """),format.raw("""}"""),format.raw/*167.14*/("""

            /* Post the new variable */
            function postNewVar(callback)"""),format.raw("""{"""),format.raw/*170.43*/("""
                var new_var_form = $("#variable-dl-form");
                var post_url = '/ajax/ajax_add_variable_rb/ID_SYS/ID_RB';                
                    post_url = post_url.replace(/ID_SYS/g, """"),_display_(Seq[Any](/*173.62*/fuzzySystem/*173.73*/.id)),format.raw/*173.76*/("""");     
                    post_url = post_url.replace(/ID_RB/g, """"),_display_(Seq[Any](/*174.61*/rb/*174.63*/.id)),format.raw/*174.66*/("""");     

                $.post(post_url,
                    new_var_form.serialize(),
                    function(data) """),format.raw("""{"""),format.raw/*178.37*/("""
                        callback(data);
                    """),format.raw("""}"""),format.raw/*180.22*/(""","json")
                    .error(function(xhr, textStatus, errorThrown) """),format.raw("""{"""),format.raw/*181.68*/("""
                        var errors = JSON.parse(xhr.responseText);

                        //for each field in the errrors
                        $.each(errors, function(key, value) """),format.raw("""{"""),format.raw/*185.62*/(""" 
                           var field = new_var_form.find("[name='" + key + "']");

                            //add each error to the given field
                            $.each(value, function(index, val)"""),format.raw("""{"""),format.raw/*189.64*/("""
                               field.parent().append("<span class='error'>"+val+"</span></br>");

                            """),format.raw("""}"""),format.raw/*192.30*/(""");
                        """),format.raw("""}"""),format.raw/*193.26*/(""");

                        console.log(errors); 
                    """),format.raw("""}"""),format.raw/*196.22*/(""");
            """),format.raw("""}"""),format.raw/*197.14*/("""

            //change the add buttons to add new vars
            $('#new_ivar').click(function()"""),format.raw("""{"""),format.raw/*200.45*/("""
                event.preventDefault();
                console.log("new_ivar");
                addVariableDialog(true);
            """),format.raw("""}"""),format.raw/*204.14*/(""");
            $('#new_ovar').click(function()"""),format.raw("""{"""),format.raw/*205.45*/("""
                event.preventDefault();
                console.log("new_ovar");
                addVariableDialog(false);
            """),format.raw("""}"""),format.raw/*209.14*/(""");


        """),format.raw("""}"""),format.raw/*212.10*/(""");
                
    </script>
    
""")))})),format.raw/*216.2*/("""
        """))}
    }
    
    def render(fuzzySystem:FuzzySystem,rb:models.RuleBase) = apply(fuzzySystem,rb)
    
    def f:((FuzzySystem,models.RuleBase) => play.api.templates.Html) = (fuzzySystem,rb) => apply(fuzzySystem,rb)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Mon Nov 26 13:28:54 BRST 2012
                    SOURCE: /home/arruda/projetos/cloudfuzzy/cloudfuzzy/app/views/fuzzy/system/rulebases/detail.scala.html
                    HASH: 73c41f5de8ba05852fa4fbe1844ee32d867a5d94
                    MATRIX: 800->1|925->74|942->83|1009->87|1049->93|1068->104|1094->109|1142->122|1152->124|1178->129|1219->50|1247->69|1277->131|1317->136|1330->141|1391->193|1430->194|1684->412|1695->414|1722->419|1798->459|1818->470|1910->539|2230->823|2263->847|2302->848|2393->903|2447->941|2487->943|2594->1014|2605->1016|2645->1034|2683->1036|2702->1046|2834->1155|2927->1216|3011->1268|3373->1594|3407->1619|3446->1620|3537->1675|3592->1714|3632->1716|3739->1787|3750->1789|3791->1808|3830->1811|3849->1821|3982->1931|4075->1992|4159->2044|4897->2746|4981->2813|5022->2815|5148->2905|5171->2906|5256->2955|5276->2966|5318->2999|5351->3009|5509->3135|5984->3574|6037->3605|6187->3708|6263->3748|6296->3772|6335->3773|6398->3800|6409->3802|6448->3819|6493->3831|6569->3870|6604->3895|6644->3896|6708->3923|6720->3925|6761->3943|6806->3955|6901->4002|7038->4091|7123->4128|7303->4260|7586->4506|7604->4514|7634->4521|7684->4523|7828->4619|8189->4932|8262->4957|8352->4999|8475->5074|8541->5092|8626->5129|8954->5409|9016->5423|9211->5570|9461->5772|9745->6019|9761->6025|9799->6040|9867->6060|9937->6082|10241->6349|10257->6355|10296->6371|10365->6392|10526->6505|10658->6589|10906->6800|10927->6811|10953->6814|11059->6883|11071->6885|11097->6888|11270->7013|11380->7075|11504->7151|11738->7337|11998->7549|12174->7677|12250->7705|12369->7776|12433->7792|12580->7891|12764->8027|12859->8074|13044->8211|13106->8225|13178->8265
                    LINES: 27->1|30->6|30->6|32->6|33->7|33->7|33->7|33->7|33->7|33->7|35->1|37->4|39->8|41->10|41->10|41->10|41->10|48->17|48->17|48->17|50->19|50->19|50->19|59->28|59->28|59->28|61->30|61->30|61->30|63->32|63->32|63->32|63->32|63->32|63->32|65->34|67->36|75->44|75->44|75->44|77->46|77->46|77->46|79->48|79->48|79->48|79->48|79->48|79->48|81->50|83->52|99->68|99->68|99->68|101->70|101->70|102->71|102->71|102->71|102->71|105->74|120->89|120->89|126->95|129->98|129->98|129->98|130->99|130->99|130->99|131->100|133->102|133->102|133->102|134->103|134->103|134->103|135->104|137->106|140->109|141->110|144->113|151->120|151->120|151->120|151->120|153->122|160->129|162->131|163->132|165->134|166->135|167->136|173->142|174->143|178->147|183->152|187->156|187->156|187->156|188->157|189->158|193->162|193->162|193->162|194->163|198->167|201->170|204->173|204->173|204->173|205->174|205->174|205->174|209->178|211->180|212->181|216->185|220->189|223->192|224->193|227->196|228->197|231->200|235->204|236->205|240->209|243->212|247->216
                    -- GENERATED --
                */
            