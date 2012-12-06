
package views.html.fuzzy.system

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
object detail extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template1[FuzzySystem,play.api.templates.Html] {

    /**/
    def apply/*1.2*/(fuzzySystem : FuzzySystem):play.api.templates.Html = {
        _display_ {import helper._


Seq[Any](format.raw/*1.29*/("""

"""),format.raw/*4.1*/("""
"""),_display_(Seq[Any](/*5.2*/fuzzy/*5.7*/.system.system_template(fuzzySystem)/*5.43*/{_display_(Seq[Any](format.raw/*5.44*/("""   

    """),_display_(Seq[Any](/*7.6*/defining(fuzzySystem.getSpecification())/*7.46*/{ spec =>_display_(Seq[Any](format.raw/*7.55*/("""  

    <div class="sub_full">

        <div class="sub_lefted">

            <div class="sub_content sub_limited">
                <h3 class='sub_content_header'>Types for Linguistic Variables</h3>

                     """),_display_(Seq[Any](/*16.23*/if(spec.getTypes().length <= 0)/*16.54*/{_display_(Seq[Any](format.raw/*16.55*/("""
                        <p>There are no Types yet.</br>
                        Create a <a href=""""),_display_(Seq[Any](/*18.44*/routes/*18.50*/.Types.prepareCreate(fuzzySystem.id))),format.raw/*18.86*/("""">new one!</a></p>
                     """)))}/*19.24*/else/*19.29*/{_display_(Seq[Any](format.raw/*19.30*/("""
                        <ul>
                        """),_display_(Seq[Any](/*21.26*/for( i <- 0 to spec.getTypes().length-1 ) yield /*21.67*/ {_display_(Seq[Any](format.raw/*21.69*/("""
                            <li><a href=""""),_display_(Seq[Any](/*22.43*/routes/*22.49*/.Types.detail(fuzzySystem.id, i))),format.raw/*22.81*/("""">
                                    """),_display_(Seq[Any](/*23.38*/spec/*23.42*/.getTypes()(i)/*23.56*/.getName())),format.raw/*23.66*/(""" 
                            </a> """),_display_(Seq[Any](/*24.35*/forms_util/*24.45*/.actions_icons(routes.Types.prepareEdit(fuzzySystem.id, i).toString(), routes.Types.delete(fuzzySystem.id, i).toString()))),format.raw/*24.166*/("""</li>
                         """)))})),format.raw/*25.27*/("""
                        </ul>
                    """)))})),format.raw/*27.22*/("""
                <div class="actions">

                    <form action=""""),_display_(Seq[Any](/*30.36*/routes/*30.42*/.Types.prepareCreate(fuzzySystem.id))),format.raw/*30.78*/("""" method="GET" >
                        
                        <input type="submit" value="Add Type">
                                            
                    </form>
                </div>
            </div>


            <div class="sub_content sub_limited">
                <h3 class='sub_content_header'>Input Variables</h3>         
                <div id="input_vars">       
                    """),_display_(Seq[Any](/*42.22*/if(spec.getSystemModule().getInputs() != null)/*42.68*/{_display_(Seq[Any](format.raw/*42.69*/("""
                        <ul>
                        """),_display_(Seq[Any](/*44.26*/for( i <- 0 to spec.getSystemModule().getInputs().length-1 ) yield /*44.86*/ {_display_(Seq[Any](format.raw/*44.88*/("""
                            <li>    
                                """),_display_(Seq[Any](/*46.34*/spec/*46.38*/.getSystemModule().getInputs()(i)/*46.71*/.getName())),format.raw/*46.81*/(""" """),_display_(Seq[Any](/*46.83*/forms_util/*46.93*/.actions_icons("#",routes.Systems.deleteVariable(fuzzySystem.id,i, models.Variable.INPUT).toString()))),format.raw/*46.194*/("""
                            </li>
                         """)))})),format.raw/*48.27*/("""
                        </ul>
                    """)))})),format.raw/*50.22*/("""
                </div>  
                <input id="new_ivar" type="button" class="btn secondary" value="Add New Variable" />

            </div>
            <div class="sub_content sub_limited">
                <h3 class='sub_content_header'>Output Variables</h3>
                <div id="output_vars">
                    """),_display_(Seq[Any](/*58.22*/if(spec.getSystemModule().getOutputs() != null)/*58.69*/{_display_(Seq[Any](format.raw/*58.70*/("""
                        <ul>
                        """),_display_(Seq[Any](/*60.26*/for( i <- 0 to spec.getSystemModule().getOutputs().length-1 ) yield /*60.87*/ {_display_(Seq[Any](format.raw/*60.89*/("""
                            <li>    
                                """),_display_(Seq[Any](/*62.34*/spec/*62.38*/.getSystemModule().getOutputs()(i)/*62.72*/.getName())),format.raw/*62.82*/("""  """),_display_(Seq[Any](/*62.85*/forms_util/*62.95*/.actions_icons("#",routes.Systems.deleteVariable(fuzzySystem.id,i, models.Variable.OUTPUT).toString()))),format.raw/*62.197*/("""
                            </li>
                         """)))})),format.raw/*64.27*/("""
                        </ul>
                    """)))})),format.raw/*66.22*/("""
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

                                """),_display_(Seq[Any](/*82.34*/for( i <- 0 to fuzzySystem.getSpecification().getTypes().length-1 ) yield /*82.101*/ {_display_(Seq[Any](format.raw/*82.103*/("""
                                
                                        <option value=""""),_display_(Seq[Any](/*84.57*/i)),format.raw/*84.58*/("""" >
                                            """),_display_(Seq[Any](/*85.46*/fuzzySystem/*85.57*/.getSpecification().getTypes()(i)/*85.90*/.getName())),format.raw/*85.100*/("""
                                        </option>
                                        
                                 """)))})),format.raw/*88.35*/("""
                            </select>
                        </li>
                        <li>
                            <input type="hidden" id="var_kind" name="kind" value="" > 
                        </li>
                    </ul>
                </form>
            </div>

        </div>
        <div class="sub_righted">


            <div class="sub_content sub_limited">
                <h3 class='sub_content_header'>Operators Set</h3>

                     """),_display_(Seq[Any](/*105.23*/if(spec.getOperatorsets().length <= 0)/*105.61*/{_display_(Seq[Any](format.raw/*105.62*/("""
                        <p>There are no Operators Set yet.</br>
                        Create a <a href=""""),_display_(Seq[Any](/*107.44*/routes/*107.50*/.OperatorSets.prepareCreate(fuzzySystem.id))),format.raw/*107.93*/("""">new one!</a></p>
                     """)))}/*108.24*/else/*108.29*/{_display_(Seq[Any](format.raw/*108.30*/("""
                        <ul>
                        """),_display_(Seq[Any](/*110.26*/for( i <- 0 to spec.getOperatorsets().length-1 ) yield /*110.74*/ {_display_(Seq[Any](format.raw/*110.76*/("""
                            <li><a href=""""),_display_(Seq[Any](/*111.43*/routes/*111.49*/.OperatorSets.detail(fuzzySystem.id, i))),format.raw/*111.88*/("""">
                                    """),_display_(Seq[Any](/*112.38*/spec/*112.42*/.getOperatorsets()(i)/*112.63*/.getName())),format.raw/*112.73*/("""
                            </a>  """),_display_(Seq[Any](/*113.36*/forms_util/*113.46*/.actions_icons("#", routes.OperatorSets.delete(fuzzySystem.id, i).toString()))),format.raw/*113.123*/("""</li>
                         """)))})),format.raw/*114.27*/("""
                        </ul>
                    """)))})),format.raw/*116.22*/("""
                     
                <div class="actions">
                    <form action=""""),_display_(Seq[Any](/*119.36*/routes/*119.42*/.OperatorSets.prepareCreate(fuzzySystem.id))),format.raw/*119.85*/("""" method="GET" >
                        
                        <input type="submit" value="Add Operator Set">
                                            
                    </form>
                </div>
            </div>


            <div class="sub_content sub_limited">
                <h3 class='sub_content_header'>Rule Bases</h3>

                     """),_display_(Seq[Any](/*131.23*/if(spec.getRulebases().length <= 0)/*131.58*/{_display_(Seq[Any](format.raw/*131.59*/("""
                        <p>There are no Rule Bases yet.</br>
                        Create a <a href=""""),_display_(Seq[Any](/*133.44*/routes/*133.50*/.RuleBases.prepareCreate(fuzzySystem.id))),format.raw/*133.90*/("""">new one!</a></p>
                     """)))}/*134.24*/else/*134.29*/{_display_(Seq[Any](format.raw/*134.30*/("""
                        <ul>
                        """),_display_(Seq[Any](/*136.26*/for( i <- 0 to spec.getRulebases().length-1 ) yield /*136.71*/ {_display_(Seq[Any](format.raw/*136.73*/("""
                            <li><a href=""""),_display_(Seq[Any](/*137.43*/routes/*137.49*/.RuleBases.detail(fuzzySystem.id, i))),format.raw/*137.85*/("""">
                                    """),_display_(Seq[Any](/*138.38*/spec/*138.42*/.getRulebases()(i)/*138.60*/.getName())),format.raw/*138.70*/("""
                            </a> """),_display_(Seq[Any](/*139.35*/forms_util/*139.45*/.actions_icons("#", "#"))),format.raw/*139.69*/("""</li>
                         """)))})),format.raw/*140.27*/("""
                        </ul>
                    """)))})),format.raw/*142.22*/("""
                     
                <div class="actions">
                    <form action=""""),_display_(Seq[Any](/*145.36*/routes/*145.42*/.RuleBases.prepareCreate(fuzzySystem.id))),format.raw/*145.82*/("""" method="GET" >
                        
                        <input type="submit" value="Add Rule Base">
                                            
                    </form>
                </div>
            </div>

        </div>
    </div>
    <div class="sub_full">
        <div class="sub_content">
            <h3 class='sub_content_header'>System Design</h3>
            ...
        </div>
    </div>






    <script type="text/javascript">

        $(document).ready(function() """),format.raw("""{"""),format.raw/*169.39*/("""

            var num_ivars = 0;
            """),_display_(Seq[Any](/*172.14*/if(spec.getSystemModule().getInputs() != null)/*172.60*/{_display_(Seq[Any](format.raw/*172.61*/("""
                num_ivars = """),_display_(Seq[Any](/*173.30*/spec/*173.34*/.getSystemModule().getInputs().length)),format.raw/*173.71*/(""";
            """)))})),format.raw/*174.14*/("""
            var num_ovars = 0;
            """),_display_(Seq[Any](/*176.14*/if(spec.getSystemModule().getOutputs() != null)/*176.61*/{_display_(Seq[Any](format.raw/*176.62*/("""
                num_ovars = """),_display_(Seq[Any](/*177.30*/spec/*177.34*/.getSystemModule().getOutputs().length)),format.raw/*177.72*/(""";
            """)))})),format.raw/*178.14*/("""

            $( "#var-dialog-modal" ).dialog("""),format.raw("""{"""),format.raw/*180.46*/("""
                autoOpen: false,
                modal: true,
                buttons: """),format.raw("""{"""),format.raw/*183.27*/("""
                    Ok: function() """),format.raw("""{"""),format.raw/*184.37*/("""
                        var new_var_form = $( this ).find("#variable-dl-form");

                        postNewVar(function(data)"""),format.raw("""{"""),format.raw/*187.51*/("""
                            console.log(data);



                            //add the new var to the list
                            // var var_list = $("#input_vars");
                            // if(new_var_form.find("[name='kind']").val() == """),_display_(Seq[Any](/*194.80*/models/*194.86*/.Variable.OUTPUT)),format.raw/*194.102*/(""")"""),format.raw("""{"""),format.raw/*194.104*/("""
                            //     var var_list = $("#output_vars");
                            // """),format.raw("""}"""),format.raw/*196.33*/("""
                            // var_list = var_list.find("ul");
                            // var_list.append("<li>" + new_var_form.find("[name='name']").val() + "</li>");

                            $( "#var-dialog-modal" ).dialog( "close" );
                            location.reload();

                        """),format.raw("""}"""),format.raw/*203.26*/(""");

                    """),format.raw("""}"""),format.raw/*205.22*/(""",
                    Cancel: function() """),format.raw("""{"""),format.raw/*206.41*/("""
                        $( this ).dialog( "close" );
                    """),format.raw("""}"""),format.raw/*208.22*/("""
                """),format.raw("""}"""),format.raw/*209.18*/(""",
                close: function() """),format.raw("""{"""),format.raw/*210.36*/("""                   
                    $( this ).dialog( "close" );
                    var new_var_form = $( this ).find("#variable-dl-form");

                    new_var_form.find("input").val("");
                    new_var_form.find(".error").remove();   
                """),format.raw("""}"""),format.raw/*216.18*/("""
            """),format.raw("""}"""),format.raw/*217.14*/(""");

            
            /* Add a new variable to the form, passing if its an isInputVar */
            function addVariableDialog(isInputVar)"""),format.raw("""{"""),format.raw/*221.52*/("""
                var dialog = $( "#var-dialog-modal" );
                var new_var_form = $("#variable-dl-form");

                var title = "Create New KIND Variable"
                if(isInputVar)"""),format.raw("""{"""),format.raw/*226.32*/("""
                    title = title.replace(/KIND/g, "Input");
                    //sets the val of the kind find  to be the correct one.
                    //based on xfuzzy definition
                    new_var_form.find("[name='kind']").val("""),_display_(Seq[Any](/*230.61*/models/*230.67*/.Variable.INPUT)),format.raw/*230.82*/(""");
                """),format.raw("""}"""),format.raw/*231.18*/("""
                else"""),format.raw("""{"""),format.raw/*232.22*/("""
                    title = title.replace(/KIND/g, "Output");         
                    //sets the val of the kind find  to be the correct one.
                    //based on xfuzzy definition          
                    new_var_form.find("[name='kind']").val("""),_display_(Seq[Any](/*236.61*/models/*236.67*/.Variable.OUTPUT)),format.raw/*236.83*/("""); 
                """),format.raw("""}"""),format.raw/*237.18*/("""
                $('.ui-dialog-title').html(title);  

                dialog.dialog( "open" );    
            """),format.raw("""}"""),format.raw/*241.14*/("""

            /* Post the new variable */
            function postNewVar(callback)"""),format.raw("""{"""),format.raw/*244.43*/("""
                var new_var_form = $("#variable-dl-form");
                // var post_url = '/ajax/ajax_add_variable/ID_SYS';                
                var post_url = '"""),_display_(Seq[Any](/*247.34*/routes/*247.40*/.Systems.ajaxAddVariable(fuzzySystem.id))),format.raw/*247.80*/("""'; 
                    // post_url = post_url.replace(/ID_SYS/g, """"),_display_(Seq[Any](/*248.65*/fuzzySystem/*248.76*/.id)),format.raw/*248.79*/("""");     

                $.post(post_url,
                    new_var_form.serialize(),
                    function(data) """),format.raw("""{"""),format.raw/*252.37*/("""
                        callback(data);
                    """),format.raw("""}"""),format.raw/*254.22*/(""","json")
                    .error(function(xhr, textStatus, errorThrown) """),format.raw("""{"""),format.raw/*255.68*/("""
                        var errors = JSON.parse(xhr.responseText);

                        //for each field in the errrors
                        $.each(errors, function(key, value) """),format.raw("""{"""),format.raw/*259.62*/(""" 
                           var field = new_var_form.find("[name='" + key + "']");

                            //add each error to the given field
                            $.each(value, function(index, val)"""),format.raw("""{"""),format.raw/*263.64*/("""
                               field.parent().append("<span class='error'>"+val+"</span></br>");

                            """),format.raw("""}"""),format.raw/*266.30*/(""");
                        """),format.raw("""}"""),format.raw/*267.26*/(""");

                        console.log(errors); 
                    """),format.raw("""}"""),format.raw/*270.22*/(""");
            """),format.raw("""}"""),format.raw/*271.14*/("""

            //change the add buttons to add new vars
            $('#new_ivar').click(function()"""),format.raw("""{"""),format.raw/*274.45*/("""
                event.preventDefault();
                console.log("new_ivar");
                addVariableDialog(true);
            """),format.raw("""}"""),format.raw/*278.14*/(""");
            $('#new_ovar').click(function()"""),format.raw("""{"""),format.raw/*279.45*/("""
                event.preventDefault();
                console.log("new_ovar");
                addVariableDialog(false);
            """),format.raw("""}"""),format.raw/*283.14*/(""");


        """),format.raw("""}"""),format.raw/*286.10*/(""");
                
    </script>
    """)))})),format.raw/*289.6*/("""   
    
""")))})))}
    }
    
    def render(fuzzySystem:FuzzySystem) = apply(fuzzySystem)
    
    def f:((FuzzySystem) => play.api.templates.Html) = (fuzzySystem) => apply(fuzzySystem)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Mon Nov 26 13:28:54 BRST 2012
                    SOURCE: /home/arruda/projetos/cloudfuzzy/cloudfuzzy/app/views/fuzzy/system/detail.scala.html
                    HASH: 0366d31512ba4051509cdae66955fa0a6d2ab1ac
                    MATRIX: 774->1|894->28|922->47|958->49|970->54|1014->90|1052->91|1096->101|1144->141|1190->150|1448->372|1488->403|1527->404|1663->504|1678->510|1736->546|1796->588|1809->593|1848->594|1939->649|1996->690|2036->692|2115->735|2130->741|2184->773|2260->813|2273->817|2296->831|2328->841|2400->877|2419->887|2563->1008|2627->1040|2711->1092|2822->1167|2837->1173|2895->1209|3346->1624|3401->1670|3440->1671|3531->1726|3607->1786|3647->1788|3754->1859|3767->1863|3809->1896|3841->1906|3879->1908|3898->1918|4022->2019|4115->2080|4199->2132|4561->2458|4617->2505|4656->2506|4747->2561|4824->2622|4864->2624|4971->2695|4984->2699|5027->2733|5059->2743|5098->2746|5117->2756|5242->2858|5335->2919|5419->2971|6157->3673|6241->3740|6282->3742|6408->3832|6431->3833|6516->3882|6536->3893|6578->3926|6611->3936|6769->4062|7281->4537|7329->4575|7369->4576|7514->4684|7530->4690|7596->4733|7657->4775|7671->4780|7711->4781|7803->4836|7868->4884|7909->4886|7989->4929|8005->4935|8067->4974|8144->5014|8158->5018|8189->5039|8222->5049|8295->5085|8315->5095|8416->5172|8481->5204|8566->5256|8699->5352|8715->5358|8781->5401|9184->5767|9229->5802|9269->5803|9411->5908|9427->5914|9490->5954|9551->5996|9565->6001|9605->6002|9697->6057|9759->6102|9800->6104|9880->6147|9896->6153|9955->6189|10032->6229|10046->6233|10074->6251|10107->6261|10179->6296|10199->6306|10246->6330|10311->6362|10396->6414|10529->6510|10545->6516|10608->6556|11154->7054|11237->7100|11293->7146|11333->7147|11400->7177|11414->7181|11474->7218|11522->7233|11604->7278|11661->7325|11701->7326|11768->7356|11782->7360|11843->7398|11891->7413|11986->7460|12123->7549|12208->7586|12388->7718|12677->7970|12693->7976|12733->7992|12784->7994|12934->8096|13301->8415|13374->8440|13464->8482|13587->8557|13653->8575|13738->8612|14066->8892|14128->8906|14323->9053|14573->9255|14857->9502|14873->9508|14911->9523|14979->9543|15049->9565|15353->9832|15369->9838|15408->9854|15477->9875|15638->9988|15770->10072|15984->10249|16000->10255|16063->10295|16168->10363|16189->10374|16215->10377|16388->10502|16498->10564|16622->10640|16856->10826|17116->11038|17292->11166|17368->11194|17487->11265|17551->11281|17698->11380|17882->11516|17977->11563|18162->11700|18224->11714|18295->11753
                    LINES: 27->1|31->1|33->4|34->5|34->5|34->5|34->5|36->7|36->7|36->7|45->16|45->16|45->16|47->18|47->18|47->18|48->19|48->19|48->19|50->21|50->21|50->21|51->22|51->22|51->22|52->23|52->23|52->23|52->23|53->24|53->24|53->24|54->25|56->27|59->30|59->30|59->30|71->42|71->42|71->42|73->44|73->44|73->44|75->46|75->46|75->46|75->46|75->46|75->46|75->46|77->48|79->50|87->58|87->58|87->58|89->60|89->60|89->60|91->62|91->62|91->62|91->62|91->62|91->62|91->62|93->64|95->66|111->82|111->82|111->82|113->84|113->84|114->85|114->85|114->85|114->85|117->88|134->105|134->105|134->105|136->107|136->107|136->107|137->108|137->108|137->108|139->110|139->110|139->110|140->111|140->111|140->111|141->112|141->112|141->112|141->112|142->113|142->113|142->113|143->114|145->116|148->119|148->119|148->119|160->131|160->131|160->131|162->133|162->133|162->133|163->134|163->134|163->134|165->136|165->136|165->136|166->137|166->137|166->137|167->138|167->138|167->138|167->138|168->139|168->139|168->139|169->140|171->142|174->145|174->145|174->145|198->169|201->172|201->172|201->172|202->173|202->173|202->173|203->174|205->176|205->176|205->176|206->177|206->177|206->177|207->178|209->180|212->183|213->184|216->187|223->194|223->194|223->194|223->194|225->196|232->203|234->205|235->206|237->208|238->209|239->210|245->216|246->217|250->221|255->226|259->230|259->230|259->230|260->231|261->232|265->236|265->236|265->236|266->237|270->241|273->244|276->247|276->247|276->247|277->248|277->248|277->248|281->252|283->254|284->255|288->259|292->263|295->266|296->267|299->270|300->271|303->274|307->278|308->279|312->283|315->286|318->289
                    -- GENERATED --
                */
            