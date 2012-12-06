
package views.html.fuzzy.system.operatorsets

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
object prepareCreate extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template2[FuzzySystem,Form[models.OperatorSet],play.api.templates.Html] {

    /**/
    def apply/*1.2*/(fuzzySystem: FuzzySystem,  newOPSetForm: Form[models.OperatorSet]):play.api.templates.Html = {
        _display_ {import helper._

def /*6.2*/hiddenField/*6.13*/(field: Field, className: String = ""):play.api.templates.Html = {_display_(

Seq[Any](format.raw/*6.55*/("""
    """),_display_(Seq[Any](/*7.6*/input(field, '_label -> null , '_class -> null )/*7.54*/ { (id, name, value, _) =>_display_(Seq[Any](format.raw/*7.80*/("""
        <input type="hidden" name=""""),_display_(Seq[Any](/*8.37*/name)),format.raw/*8.41*/("""" value=""""),_display_(Seq[Any](/*8.51*/value)),format.raw/*8.56*/("""" class=""""),_display_(Seq[Any](/*8.66*/className)),format.raw/*8.75*/(""""> 
    """)))})),format.raw/*9.6*/("""
""")))};def /*12.2*/operatorField/*12.15*/(field: Field):play.api.templates.Html = {_display_(

Seq[Any](format.raw/*12.33*/("""


    """),_display_(Seq[Any](/*15.6*/hiddenField(field("name")))),format.raw/*15.32*/("""

    """),_display_(Seq[Any](/*17.6*/select(
        field("selectedOption"),
        options =  options(
                        models.OperatorSet.getAvailableOptionsMapForOperatorsByOperatorLabel(
                            field("name").value.getOrElse("")
                        )
                    ),
        '_label -> field("name").value,
        '_class -> "option_field"
    ))),format.raw/*26.6*/("""          

    

""")))};
Seq[Any](format.raw/*1.69*/("""

"""),format.raw/*4.1*/("""

"""),format.raw/*10.2*/("""

"""),format.raw/*30.2*/("""


"""),_display_(Seq[Any](/*33.2*/fuzzy/*33.7*/.system.system_template(fuzzySystem)/*33.43*/{_display_(Seq[Any](format.raw/*33.44*/("""   
    <div class="sub_content sub_limited">
        <h3 class='sub_content_header'>New Fuzzy Operator Set</h3> 
            """),_display_(Seq[Any](/*36.14*/helper/*36.20*/.form(action = routes.OperatorSets.create(fuzzySystem.id), 'id -> "newOPSetForm")/*36.101*/ {_display_(Seq[Any](format.raw/*36.103*/("""
                
                <fieldset>
                    <legend>General Information for the new Operator Set</legend>
                    
                    """),_display_(Seq[Any](/*41.22*/inputText(
                        newOPSetForm("name"), 
                        '_label -> "Name", 
                        '_help -> "The name of this Operator Set."
                    ))),format.raw/*45.22*/("""                

                    
                </fieldset>           
                
                <fieldset>
                    <legend>Select Operators</legend>

                    """),_display_(Seq[Any](/*53.22*/repeat(newOPSetForm("operators"))/*53.55*/ { operator =>_display_(Seq[Any](format.raw/*53.69*/("""
                        
                        """),_display_(Seq[Any](/*55.26*/operatorField(operator))),format.raw/*55.49*/("""

                        """),_display_(Seq[Any](/*57.26*/repeat(operator("params"))/*57.52*/ { params =>_display_(Seq[Any](format.raw/*57.64*/("""
                            
                            """),_display_(Seq[Any](/*59.30*/hiddenField(params("name"), "parameter"))),format.raw/*59.70*/("""
                            """),_display_(Seq[Any](/*60.30*/hiddenField(params("value"), "parameter"))),format.raw/*60.71*/("""
                            
                        """)))})),format.raw/*62.26*/("""   
                        
                    """)))})),format.raw/*64.22*/("""   

                </fieldset>         
                
                <div class="actions">
                    <input type="submit" class="btn primary" value="Create">
                    <input type="button" class="btn secondary" value="Cancel"onclick="window.location = '"""),_display_(Seq[Any](/*70.107*/routes/*70.113*/.Systems.detail(fuzzySystem.id))),format.raw/*70.144*/("""' "/>
                </div>
                
            """)))})),format.raw/*73.14*/("""
 
    </div>
    <div id="dialog-modal" title="Parameters Information">
        <p>This option requires the definition of some parameters, please expecify them.</p>
        <form id="params-dl-form">
        </form>
    </div>

    <script type="text/javascript">

        $(document).ready(function() """),format.raw("""{"""),format.raw/*84.39*/("""

            $( "#dialog-modal" ).dialog("""),format.raw("""{"""),format.raw/*86.42*/("""
                autoOpen: false,
                modal: true,
                buttons: """),format.raw("""{"""),format.raw/*89.27*/("""
                    Ok: function() """),format.raw("""{"""),format.raw/*90.37*/("""
                        var dl_form = $( this ).find("#params-dl-form");

                        var option = $( this ).data("option");
                        var id_opType = $( this ).data("id_opType");
                        var id_option = $( this ).data("id_option");

                        var newParams = [];

                        dl_form.find("label").each(function()"""),format.raw("""{"""),format.raw/*99.63*/("""
                            var newParam = """),format.raw("""{"""),format.raw("""}"""),format.raw/*100.46*/(""";
                            var name = $( this ).html();
                            var val = $("#" + $( this ).attr("for")).val();
                            newParam.name =name;
                            newParam.value = val;
                            newParams.push(newParam);
                        """),format.raw("""}"""),format.raw/*106.26*/(""");

                        setParamsHtmls(option, id_opType, id_option, newParams);
                        option.data("prev", option.val());
                        $( this ).dialog( "close" );
                        dl_form.contents("input").remove();
                        dl_form.contents("label").remove();
                    """),format.raw("""}"""),format.raw/*113.22*/(""",
                    Cancel: function() """),format.raw("""{"""),format.raw/*114.41*/("""
                        $( this ).dialog( "close" );
                        var dl_form = $( this ).find("#params-dl-form");
                        var option = $($( this ).data("option"));
                        option.val("" + option.data("prev"));
                        //option.find("option:selected").attr("selected",false);
                        //option.find("[value='"+ option.val() +"']").attr("selected",true);
                        //console.log(option);

                        dl_form.contents("input").remove();
                        dl_form.contents("label").remove();
                    """),format.raw("""}"""),format.raw/*125.22*/("""
                """),format.raw("""}"""),format.raw/*126.18*/(""",
                close: function() """),format.raw("""{"""),format.raw/*127.36*/("""                   
                    $( this ).dialog( "close" );
                    var dl_form = $( this ).find("#params-dl-form");
                    var option =  $($( this ).data("option"));
                    //var option = $("#operators_2__selectedOption");
                    // option.find("option:selected").attr("selected",false);
                    // option.find("[value='"+ option.val() +"']").attr("selected",true);
                    option.val(option.data("prev"));
                    // console.log(option);
                    // console.log(option.val());
                    // console.log(option.data("prev"));

                    dl_form.contents("input").remove();
                    dl_form.contents("label").remove();
                """),format.raw("""}"""),format.raw/*141.18*/("""
            """),format.raw("""}"""),format.raw/*142.14*/(""");

            //get parameters for a given operator selected option
            function getParamsForOption(option) """),format.raw("""{"""),format.raw/*145.50*/(""" 
                //$("#operators_0__selectedOption")
                var id_option = option.val();
                var id_opType = option.attr('id').split("operators_")[1].split("__selectedOption")[0];

                console.log("getParamsForOption(" + id_opType + "," + id_option + ")");
                //get the parameters for the operator
                $.get('/ajax/get_num_params_operator_option',
                      """),format.raw("""{"""),format.raw/*153.24*/("""
                        id_option: id_option,
                        id_opType: id_opType
                      """),format.raw("""}"""),format.raw/*156.24*/(""",
                      function(data) """),format.raw("""{"""),format.raw/*157.39*/("""
                        var newParams = data;
                        //if has params then open dialog, else will probably just remove any that was there before
                        if(newParams.length == 0)"""),format.raw("""{"""),format.raw/*160.51*/("""
                            setParamsHtmls(option, id_opType, id_option, newParams);                           
                            console.log(option.data("prev")); 
                            console.log("setting new val:"+ id_option);
                            option.data("prev", option.val());                          
                            console.log(option.data("prev"));
                        """),format.raw("""}"""),format.raw/*166.26*/("""
                        else"""),format.raw("""{"""),format.raw/*167.30*/("""
                            openDialogForParams(option, id_opType, id_option, newParams);
                        """),format.raw("""}"""),format.raw/*169.26*/("""
                        
                """),format.raw("""}"""),format.raw/*171.18*/(""");
            """),format.raw("""}"""),format.raw/*172.14*/("""

            $("[id$='selectedOption']").each(function() """),format.raw("""{"""),format.raw/*174.58*/("""
                var jqThis = $(this);
                jqThis.data("prev",jqThis.val())
                jqThis.change(function()"""),format.raw("""{"""),format.raw/*177.42*/("""
                    console.log("prev:"+ jqThis.data("prev"))
                    getParamsForOption(jqThis);
                    // jqThis.data("prev new:",jqThis.val())

                """),format.raw("""}"""),format.raw/*182.18*/(""");
                //getParamsForOption($(this));
            """),format.raw("""}"""),format.raw/*184.14*/(""");

            /* Creates the html for the new param hidden inputs */
            function getNewParamHtml(newParamInfo, name, value)"""),format.raw("""{"""),format.raw/*187.65*/("""
                var paramNameHTML ='<input type="hidden" name="NAME" value="VAL" class="parameter">';

                paramNameHTML = paramNameHTML.replace(/NAME/g, newParamInfo+'.name');
                paramNameHTML = paramNameHTML.replace(/VAL/g, name);

                var paramValueHTML ='<input type="hidden" name="NAME" value="VAL" class="parameter">';

                paramValueHTML = paramValueHTML.replace(/NAME/g, newParamInfo+'.value');
                paramValueHTML = paramValueHTML.replace(/VAL/g, value);


                return paramNameHTML + paramValueHTML;
            """),format.raw("""}"""),format.raw/*200.14*/("""

            /* Adds the html for the inputs of each new param in the form, and remove the old ones if any */
            function setParamsHtmls(option, id_opType, id_option, newParams)"""),format.raw("""{"""),format.raw/*203.78*/("""

                console.log("setParamsHtmls: " + option.attr('id') + ", " + id_opType + ", "+id_option + ", " + newParams); 
                console.log("newParams: "); 
                console.log(newParams); 

                var form = $("#newOPSetForm");
                // operators[0].options[1].name

                //see if should add the i or the newParams[i]
                var oldParams = $("[name^='operators["+ id_opType +"].params']");

                //remove if any
                oldParams.each(function()"""),format.raw("""{"""),format.raw/*216.43*/("""
                    console.log("removing: "+ $(this).attr('name'));
                    $(this).remove();
                """),format.raw("""}"""),format.raw/*219.18*/(""");
                for (var i = 0; i < newParams.length; i++) """),format.raw("""{"""),format.raw/*220.61*/("""

                    //add new params inputs
                    var newParamInfo = "operators[" + id_opType + "].params[" + i +"]";
                    var newParamHtml = getNewParamHtml(newParamInfo, newParams[i].name, newParams[i].value);
                    form.append(newParamHtml);
                    console.log("adding: "+newParamHtml + "\n" +"to: " + form.attr('id'));
                """),format.raw("""}"""),format.raw/*227.18*/(""";
            """),format.raw("""}"""),format.raw/*228.14*/("""

            /* Open the dialog to input the data of the new params */
            function openDialogForParams(option, id_opType, id_option, newParams)"""),format.raw("""{"""),format.raw/*231.83*/("""
                var dialog = $( "#dialog-modal" );


                var dl_form = dialog.find("#params-dl-form");

                for (var i = 0; i < newParams.length; i++) """),format.raw("""{"""),format.raw/*237.61*/("""
                    var param_label= "<label for='param_"+ i +"_value'>"+ newParams[i].name +"</label>";
                    var param_input= "<input type='text' id='param_"+ i +"_value' name='param[" + i + "].value' value='" + newParams[i].value + "' >";                            
                    dl_form.append(param_label+param_input);
                    console.log("adding: "+param_label+param_input + "\n" +"to: " + dl_form.attr('id'));
                """),format.raw("""}"""),format.raw/*242.18*/(""";
                                

                dialog.data("option", option);
                dialog.data("id_opType", id_opType);
                dialog.data("id_option", id_option);
                dialog.data("newParams", newParams);
                dialog.dialog( "open" );

            """),format.raw("""}"""),format.raw/*251.14*/("""


        """),format.raw("""}"""),format.raw/*254.10*/(""");
                
    </script>
    
""")))})))}
    }
    
    def render(fuzzySystem:FuzzySystem,newOPSetForm:Form[models.OperatorSet]) = apply(fuzzySystem,newOPSetForm)
    
    def f:((FuzzySystem,Form[models.OperatorSet]) => play.api.templates.Html) = (fuzzySystem,newOPSetForm) => apply(fuzzySystem,newOPSetForm)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Mon Nov 26 13:28:54 BRST 2012
                    SOURCE: /home/arruda/projetos/cloudfuzzy/cloudfuzzy/app/views/fuzzy/system/operatorsets/prepareCreate.scala.html
                    HASH: f205f88a1e3c86577a097bf1d0611f622c0101a5
                    MATRIX: 819->1|962->90|981->101|1086->143|1126->149|1182->197|1245->223|1317->260|1342->264|1387->274|1413->279|1458->289|1488->298|1527->307|1552->312|1574->325|1656->343|1699->351|1747->377|1789->384|2163->737|2221->68|2249->87|2278->309|2307->756|2346->760|2359->765|2404->801|2443->802|2606->929|2621->935|2712->1016|2753->1018|2958->1187|3170->1377|3404->1575|3446->1608|3498->1622|3585->1673|3630->1696|3693->1723|3728->1749|3778->1761|3873->1820|3935->1860|4001->1890|4064->1931|4151->1986|4233->2036|4550->2316|4566->2322|4620->2353|4711->2412|5062->2716|5152->2759|5288->2848|5372->2885|5803->3269|5916->3315|6277->3628|6663->3966|6753->4008|7419->4626|7485->4644|7570->4681|8391->5454|8453->5468|8620->5587|9099->6018|9262->6133|9350->6173|9610->6385|10082->6809|10160->6839|10324->6955|10415->6998|10479->7014|10586->7073|10763->7202|11001->7392|11112->7455|11295->7590|11938->8185|12174->8373|12751->8902|12924->9027|13035->9090|13481->9488|13544->9503|13746->9657|13971->9834|14487->10302|14832->10599|14892->10611
                    LINES: 27->1|30->6|30->6|32->6|33->7|33->7|33->7|34->8|34->8|34->8|34->8|34->8|34->8|35->9|36->12|36->12|38->12|41->15|41->15|43->17|52->26|57->1|59->4|61->10|63->30|66->33|66->33|66->33|66->33|69->36|69->36|69->36|69->36|74->41|78->45|86->53|86->53|86->53|88->55|88->55|90->57|90->57|90->57|92->59|92->59|93->60|93->60|95->62|97->64|103->70|103->70|103->70|106->73|117->84|119->86|122->89|123->90|132->99|133->100|139->106|146->113|147->114|158->125|159->126|160->127|174->141|175->142|178->145|186->153|189->156|190->157|193->160|199->166|200->167|202->169|204->171|205->172|207->174|210->177|215->182|217->184|220->187|233->200|236->203|249->216|252->219|253->220|260->227|261->228|264->231|270->237|275->242|284->251|287->254
                    -- GENERATED --
                */
            