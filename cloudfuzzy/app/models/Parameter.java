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