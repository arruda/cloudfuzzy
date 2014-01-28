 
	/*
	*
    * (c) 2013, 2014 INT - National Institute of Technology
	*
    * CloudFuzzy's team: 
	*	Dayse Mourao Arruda, Felipe Arruda Pontes, Bruno Barcellos Farias de Oliveira.
	*
    * The authors may be contacted by the email address: dayse.arruda@int.gov.br
	*
	* This file is part of CloudFuzzy - version Beta - 2014/January.
	*
    * We apply XFuzzy 3.0 as fuzzy engine to generate and evaluate alternative plans.
    * XFuzzy is a free design environment for fuzzy logic based systems distributed by IMSE-CNM.
	* If you need more information about the XFuzzy 3.0, please go to project's site:
	* http://www2.imse-cnm.csic.es/Xfuzzy/
    *                    
	* CloudFuzzy is free software: you can redistribute it and/or modify
	* it under the terms of the GNU General Public License as published by
	* the Free Software Foundation, either version 3 of the License, or
	* (at your option) any later version.
	*
	* CloudFuzzy is distributed in the hope that it will be useful,
	* but WITHOUT ANY WARRANTY; without even the implied warranty of
	* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
	* GNU General Public License for more details.
	*
	* You should have received a copy of the GNU General Public License
	* along with CloudFuzzy. If not, see <http://www.gnu.org/license/>.
    */
        
           
     
package models;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import models.FuzzySystem;

import javax.validation.*;
import play.data.validation.Constraints;
import xfuzzy.lang.Specification;
import xfuzzy.lang.XflPackage;

import xfuzzy.lang.Definition;
import xfuzzy.lang.FuzzyOperator;


public class Parameter{
    
    // /**
    //  * This ID is the position of the fuzzy OperatorSet in the vector of spec.getOperatorsets
    //  */
    // public Integer id;
    
    @Constraints.Required
    public String name;

    @Constraints.Required
    public Double value;


    public Parameter(){
        this.name = "";
        this.value = 0.0;
    }
    public Parameter(String name){
        this.name = name;
        this.value = 0.0;
    }
    public Parameter(String name, Double value){
        this.name = name;
        this.value = value;
    }

    // /**
    // * Return (creating it from the spec file) the given OperatorSet,
    // * Passing it's FuzzySystem, and it's id(the position of this type in the OperatorSet array)
    // * if any error occours, like the id_opset is out of the array bound, then raise an Exception
    // * that should be treated in along
    // */
    // public static OperatorSet get(FuzzySystem sys, Integer id_opset)
    // throws Exception {

    //   return OperatorSet.createFromFuzzyOperatorSet(
    //             OperatorSet.getFuzzy(sys, id_opset),
    //             id_opset
    //     );

    // }
    // /**
    // * Return  the given OperatorSet(the fuzzy one),
    // * Passing it's FuzzySystem, and it's id(the position of this OperatorSet in the OperatorSets array)
    // * if any error occours, like the id_opset is out of the array bound, then raise an Exception
    // * that should be treated in along
    // */
    // public static xfuzzy.lang.Operatorset getFuzzy(FuzzySystem sys, Integer id_opset)
    // throws Exception {
    //   Specification spec=null;
    //   //can throw an exception
    //   spec = sys.getSpecification();  
      

    //   //the Operatorset for this modeling
    //   xfuzzy.lang.Operatorset [] opsets = spec.getOperatorsets();

    //   //ensures that the required type id is within the bounds tps array
    //   if(opsets.length <= id_opset || id_opset < 0){
    //         throw new Exception("Wrong OperatorSet ID:"+id_opset);
    //   }

    //   return opsets[id_opset];
    // }


    

}
