
$(document).ready(function() {

  $('#idFunction').change(getParamsForMf);
  getParamsForMf();
  //get parameters for a given mf type
  function getParamsForMf() {
    var idFunction = $('#idFunction');
    $.get('/ajax/get_num_params_mf',
          {mf_key: idFunction.val()},
          function(data) {
            setParamsForMf(data);
          });
  }

  /* set parameters for a given mf type
  adding to the form the number of input equivalent to 
  */
  function setParamsForMf(num_params) {
        var paramsDiv = $('#params');
        var paramsHTML = '';
        for (var i = 0; i < num_params; i++) {
            paramsHTML += getNewParamHtml(i);
        };
        //window.alert(paramsHTML);
        // paramsDiv.fadeIn('fast');
        paramsDiv.html($(paramsHTML).fadeIn('fast'));

    
  }

  /* Creates the html for the new param input and label*/
  function getNewParamHtml(i){

     var paramHTML ='<dl class=" " id="params_ID__field">\
                       <dt><label for="params_ID_">params[ID]</label></dt>\
                        <dd>\
                        <input type="text" id="params_ID_" name="params[ID]" value="" >\
                    </dd>'

    paramHTML = paramHTML.replace(/ID/g, i);

    // var paramHTML =   '<dl class=" " id="params_'+i+'__field">';

    //     paramHTML +=  '<label for="params_'+i+'_">params['+i+']</label>';
    //     paramHTML +=  '<input type="text" id="params_'+ i +'_" name="params['+ i +']" value="">';
        return paramHTML;
  }



});

        