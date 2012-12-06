
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
object rules_as_table extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template2[FuzzySystem,models.RuleBase,play.api.templates.Html] {

    /**/
    def apply/*1.2*/(fuzzySystem : FuzzySystem, rb : models.RuleBase):play.api.templates.Html = {
        _display_ {

Seq[Any](format.raw/*1.51*/("""

<table border="1">


    <thead>
           <tr>
				<th>Degree</th>
				<th>  </th>

				<!-- Inputs -->  
	                """),_display_(Seq[Any](/*12.19*/for(vinput <- rb.inputvars ) yield /*12.47*/{_display_(Seq[Any](format.raw/*12.48*/("""
						<th>"""),_display_(Seq[Any](/*13.12*/vinput/*13.18*/.name)),format.raw/*13.23*/("""</th>
						<th>  </th>
	             	""")))})),format.raw/*15.17*/("""

				<!-- Outputs -->    
	                """),_display_(Seq[Any](/*18.19*/for(voutput <- rb.outputvars ) yield /*18.49*/{_display_(Seq[Any](format.raw/*18.50*/("""
						<th>"""),_display_(Seq[Any](/*19.12*/voutput/*19.19*/.name)),format.raw/*19.24*/("""</th>
	             	""")))})),format.raw/*20.17*/("""
                <th>Actions</th>  
           </tr>
    </thead>

    <tbody>
        """),_display_(Seq[Any](/*26.10*/for(rule <- forms.RuleTableForm.getInfoMapForTableRule(fuzzySystem,rb.id)) yield /*26.84*/{_display_(Seq[Any](format.raw/*26.85*/("""
            <tr>
                <td>
                    """),_display_(Seq[Any](/*29.22*/rule/*29.26*/.get("degree")(0))),format.raw/*29.43*/("""
                </td>   

                <td>if</td>


                <!-- Inputs -->    
                """),_display_(Seq[Any](/*36.18*/for(i <- 0 to rule.get("inputs").length -1) yield /*36.61*/{_display_(Seq[Any](format.raw/*36.62*/("""
                    <td>
                        """),_display_(Seq[Any](/*38.26*/rule/*38.30*/.get("inputs")(i))),format.raw/*38.47*/("""
                    </td>
                    """),_display_(Seq[Any](/*40.22*/if(i != rule.get("inputs").length -1)/*40.59*/{_display_(Seq[Any](format.raw/*40.60*/("""
                        <td>&</td>
                    """)))})),format.raw/*42.22*/("""
                """)))})),format.raw/*43.18*/("""


                <td>-></td>


                <!-- Outputs -->    

                """),_display_(Seq[Any](/*51.18*/for(output <- rule.get("outputs")) yield /*51.52*/{_display_(Seq[Any](format.raw/*51.53*/("""
                    <td>
                        """),_display_(Seq[Any](/*53.26*/output)),format.raw/*53.32*/("""
                    </td>
                """)))})),format.raw/*55.18*/("""
                    <td>
                        """),_display_(Seq[Any](/*57.26*/forms_util/*57.36*/.actions_icons("#",
                            routes.RuleBases.deleteRule(fuzzySystem.id,rb.id,
                                java.lang.Integer.valueOf(rule.get("id")(0))
                            ).toString()
                        ))),format.raw/*61.26*/("""
                    </td>


           </tr>    
        """)))})),format.raw/*66.10*/("""
    </tbody>

</table>
<input id="new_rule" type="button" class="btn secondary" value="New Rule" />	


<div id="rule-dialog-modal" title="New Rule">
    <form id="rule-dl-form">

		<table border="1">
		    <thead>
		           <tr>
						<th>Degree</th>
						<th>  </th>

						<!-- Inputs -->  
			                """),_display_(Seq[Any](/*83.21*/for(vinput <- rb.inputvars ) yield /*83.49*/{_display_(Seq[Any](format.raw/*83.50*/("""
								<th>"""),_display_(Seq[Any](/*84.14*/vinput/*84.20*/.name)),format.raw/*84.25*/("""</th>
								<th>  </th>
			             	""")))})),format.raw/*86.19*/("""

						<!-- Outputs -->    
			                """),_display_(Seq[Any](/*89.21*/for(voutput <- rb.outputvars ) yield /*89.51*/{_display_(Seq[Any](format.raw/*89.52*/("""
								<th>"""),_display_(Seq[Any](/*90.14*/voutput/*90.21*/.name)),format.raw/*90.26*/("""</th>
			             	""")))})),format.raw/*91.19*/("""
		           </tr>
		    </thead>

		    <tbody>
		        <tr>
					<td>						
    		        	<input type="text" id="rule_degree" name="degree" value="" >
					</td>	

					<td>if</td>


					<!-- Inputs -->    

		                """),_display_(Seq[Any](/*106.20*/for(i <- 0 to rb.inputvars.length -1) yield /*106.57*/{_display_(Seq[Any](format.raw/*106.58*/("""
							<td>

		    					<select id="rule_input__"""),_display_(Seq[Any](/*109.37*/{i})),format.raw/*109.40*/("""__" name="inputs["""),_display_(Seq[Any](/*109.58*/i)),format.raw/*109.59*/("""]" >
    								<option value="" ></option>
		    			            """),_display_(Seq[Any](/*111.23*/for( id_mf <- 0 to rb.inputvars(i).getType(fuzzySystem).MFs.length-1 ) yield /*111.93*/ {_display_(Seq[Any](format.raw/*111.95*/("""
		    						
		    								<option value=""""),_display_(Seq[Any](/*113.31*/id_mf)),format.raw/*113.36*/("""" >
		    									"""),_display_(Seq[Any](/*114.17*/rb/*114.19*/.inputvars(i).name)),format.raw/*114.37*/(""" 
		    									== 
		    									"""),_display_(Seq[Any](/*116.17*/rb/*116.19*/.inputvars(i).getType(fuzzySystem).MFs(id_mf).label)),format.raw/*116.70*/("""
		    								</option>
		    		                        
		    			             """)))})),format.raw/*119.24*/("""
		    					</select>
							</td>
							"""),_display_(Seq[Any](/*122.9*/if(i != rb.inputvars.length -1)/*122.40*/{_display_(Seq[Any](format.raw/*122.41*/("""
								<td>&</td>

							""")))}/*125.9*/else/*125.13*/{_display_(Seq[Any](format.raw/*125.14*/("""
								<td> -> </td>
							""")))})),format.raw/*127.9*/("""
		             	""")))})),format.raw/*128.18*/("""


					<!-- Outputs -->    

		                """),_display_(Seq[Any](/*133.20*/for(i <- 0 to rb.outputvars.length -1) yield /*133.58*/{_display_(Seq[Any](format.raw/*133.59*/(""" 
							<td>

		    					<select id="rule_output__"""),_display_(Seq[Any](/*136.38*/{i})),format.raw/*136.41*/("""__" name="outputs["""),_display_(Seq[Any](/*136.60*/i)),format.raw/*136.61*/("""]" >

    								<option value="" ></option>
		    			            """),_display_(Seq[Any](/*139.23*/for( id_mf <- 0 to rb.outputvars(i).getType(fuzzySystem).MFs.length-1 ) yield /*139.94*/ {_display_(Seq[Any](format.raw/*139.96*/("""
		    						
		    								<option value=""""),_display_(Seq[Any](/*141.31*/id_mf)),format.raw/*141.36*/("""" >
		    									"""),_display_(Seq[Any](/*142.17*/rb/*142.19*/.outputvars(i).name)),format.raw/*142.38*/(""" 
		    									== 
		    									"""),_display_(Seq[Any](/*144.17*/rb/*144.19*/.outputvars(i).getType(fuzzySystem).MFs(id_mf).label)),format.raw/*144.71*/("""
		    								</option>
		    		                        
		    			             """)))})),format.raw/*147.24*/("""
		    					</select>
							</td>
		             	""")))})),format.raw/*150.18*/("""


		       </tr>	
		    </tbody>

		</table>
    </form>
</div>


<script type="text/javascript">

    $(document).ready(function() """),format.raw("""{"""),format.raw/*163.35*/("""

        $( "#rule-dialog-modal" ).dialog("""),format.raw("""{"""),format.raw/*165.43*/("""
            width: 'auto',
            autoOpen: false,
            modal: true,
            buttons: """),format.raw("""{"""),format.raw/*169.23*/("""
                Ok: function() """),format.raw("""{"""),format.raw/*170.33*/("""
                    var new_rule_form = $( this ).find("#rule-dl-form");


                    $.post('"""),_display_(Seq[Any](/*174.30*/routes/*174.36*/.RuleBases.ajaxAddRuleFromTable(fuzzySystem.id, rb.id))),format.raw/*174.90*/("""',
                        new_rule_form.serialize(),
                        function(data) """),format.raw("""{"""),format.raw/*176.41*/("""
                            console.log(data);
                            $("#rule-dialog-modal").dialog( "close" );
                            location.reload();
                        """),format.raw("""}"""),format.raw/*180.26*/(""","json")
                        .error(function(xhr, textStatus, errorThrown) """),format.raw("""{"""),format.raw/*181.72*/("""
                            var errors = JSON.parse(xhr.responseText);

                            // //for each field in the errrors
                            // $.each(errors, function(key, value) """),format.raw("""{"""),format.raw/*185.69*/(""" 
                            //    var field = new_var_form.find("[name='" + key + "']");

                            //     //add each error to the given field
                            //     $.each(value, function(index, val)"""),format.raw("""{"""),format.raw/*189.71*/("""
                            //        field.parent().append("<span class='error'>"+val+"</span></br>");

                            //     """),format.raw("""}"""),format.raw/*192.37*/(""");
                            // """),format.raw("""}"""),format.raw/*193.33*/(""");

                            console.log(errors); 
                        """),format.raw("""}"""),format.raw/*196.26*/(""");
                """),format.raw("""}"""),format.raw/*197.18*/(""",
                Cancel: function() """),format.raw("""{"""),format.raw/*198.37*/("""
                    $( this ).dialog( "close" );
                """),format.raw("""}"""),format.raw/*200.18*/("""
            """),format.raw("""}"""),format.raw/*201.14*/(""",
            close: function() """),format.raw("""{"""),format.raw/*202.32*/("""                   
                $( this ).dialog( "close" );
                var new_var_form = $( this ).find("#rule-dl-form");

                new_var_form.find("input").val("");
                new_var_form.find("select").val("");                
                new_var_form.find(".error").remove();   
            """),format.raw("""}"""),format.raw/*209.14*/("""
        """),format.raw("""}"""),format.raw/*210.10*/(""");

        //change the add buttons to add new vars
        $('#new_rule').click(function()"""),format.raw("""{"""),format.raw/*213.41*/("""
            event.preventDefault();
            console.log("new_rule");
            $( "#rule-dialog-modal" ).dialog( "open" );
        """),format.raw("""}"""),format.raw/*217.10*/(""");
    """),format.raw("""}"""),format.raw/*218.6*/(""");
                
</script>"""))}
    }
    
    def render(fuzzySystem:FuzzySystem,rb:models.RuleBase) = apply(fuzzySystem,rb)
    
    def f:((FuzzySystem,models.RuleBase) => play.api.templates.Html) = (fuzzySystem,rb) => apply(fuzzySystem,rb)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Mon Nov 26 13:28:54 BRST 2012
                    SOURCE: /home/arruda/projetos/cloudfuzzy/cloudfuzzy/app/views/fuzzy/system/rulebases/rules_as_table.scala.html
                    HASH: 2c7815b1ebc681aa0f802e0945259465fd96585f
                    MATRIX: 808->1|934->50|1098->178|1142->206|1181->207|1229->219|1244->225|1271->230|1343->270|1424->315|1470->345|1509->346|1557->358|1573->365|1600->370|1654->392|1778->480|1868->554|1907->555|2003->615|2016->619|2055->636|2201->746|2260->789|2299->790|2386->841|2399->845|2438->862|2522->910|2568->947|2607->948|2696->1005|2746->1023|2870->1111|2920->1145|2959->1146|3046->1197|3074->1203|3150->1247|3237->1298|3256->1308|3519->1549|3610->1608|3964->1926|4008->1954|4047->1955|4097->1969|4112->1975|4139->1980|4215->2024|4300->2073|4346->2103|4385->2104|4435->2118|4451->2125|4478->2130|4534->2154|4805->2388|4859->2425|4899->2426|4986->2476|5012->2479|5067->2497|5091->2498|5195->2565|5282->2635|5323->2637|5404->2681|5432->2686|5489->2706|5501->2708|5542->2726|5616->2763|5628->2765|5702->2816|5816->2897|5895->2940|5936->2971|5976->2972|6024->3001|6038->3005|6078->3006|6141->3037|6192->3055|6278->3104|6333->3142|6373->3143|6462->3195|6488->3198|6544->3217|6568->3218|6673->3286|6761->3357|6802->3359|6883->3403|6911->3408|6968->3428|6980->3430|7022->3449|7096->3486|7108->3488|7183->3540|7297->3621|7382->3673|7564->3807|7656->3851|7808->3955|7889->3988|8031->4093|8047->4099|8124->4153|8266->4247|8505->4438|8633->4518|8885->4722|9166->4955|9356->5097|9439->5132|9566->5211|9634->5231|9720->5269|9835->5336|9897->5350|9978->5383|10351->5708|10409->5718|10550->5811|10737->5950|10792->5958
                    LINES: 27->1|30->1|41->12|41->12|41->12|42->13|42->13|42->13|44->15|47->18|47->18|47->18|48->19|48->19|48->19|49->20|55->26|55->26|55->26|58->29|58->29|58->29|65->36|65->36|65->36|67->38|67->38|67->38|69->40|69->40|69->40|71->42|72->43|80->51|80->51|80->51|82->53|82->53|84->55|86->57|86->57|90->61|95->66|112->83|112->83|112->83|113->84|113->84|113->84|115->86|118->89|118->89|118->89|119->90|119->90|119->90|120->91|135->106|135->106|135->106|138->109|138->109|138->109|138->109|140->111|140->111|140->111|142->113|142->113|143->114|143->114|143->114|145->116|145->116|145->116|148->119|151->122|151->122|151->122|154->125|154->125|154->125|156->127|157->128|162->133|162->133|162->133|165->136|165->136|165->136|165->136|168->139|168->139|168->139|170->141|170->141|171->142|171->142|171->142|173->144|173->144|173->144|176->147|179->150|192->163|194->165|198->169|199->170|203->174|203->174|203->174|205->176|209->180|210->181|214->185|218->189|221->192|222->193|225->196|226->197|227->198|229->200|230->201|231->202|238->209|239->210|242->213|246->217|247->218
                    -- GENERATED --
                */
            