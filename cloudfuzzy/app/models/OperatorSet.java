package models;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import models.FuzzySystem;

import play.data.validation.Constraints;
import xfuzzy.lang.Specification;
import xfuzzy.lang.XflPackage;

import xfuzzy.lang.FuzzyOperator;


public class OperatorSet{
    
    /**
     * This ID is the position of the fuzzy OperatorSet in the vector of spec.getOperatorsets
     */
    public Integer id;
    
    @Constraints.Required
    public String name;

    public List<Operator> operators;
    //make the Operators
     // public Binary and;
     // public Binary or;
     // public Binary imp;
     // public Binary also;
     // public Unary not;
     // public Unary very;
     // public Unary moreorless;
     // public Unary slightly;
     // public DefuzMethod defuz;


    // /**
    // *Returns the vector of MFs Types
    // * from the xfl package 
    // */
    // public static Vector getAvailableMFTypes(){        
    //     Vector pkglist = FuzzySystem.getLoadedPackages();
    //     Vector available = new Vector();
    //     for(int i=0, size=pkglist.size(); i<size; i++) {
    //         XflPackage pkg = (XflPackage) pkglist.elementAt(i);
    //         Vector vv = pkg.get(XflPackage.MFUNC);
    //         int vvsize = vv.size();
    //         for(int j=0; j<vvsize; j++)
    //         {
    //             available.addElement( vv.elementAt(j) ); 
    //         } 
    //     }
    //     return available;
    // }

    /**
    *Represent a operator that appear to be selected in the creation or edition of a OperatorsSet
    */
    private class Operator{
        // public Integer opType;
        public String name;

        //what is the selected option for this 
        public Integer selectedOption;

        //the available options list
        public List<String> options;

    }


    /**
    *Get the available Operator for a given Operator Type.s
    */
    public static Vector getAvailableOperatorsForOPType(int id_opType){

        //retrieve whats the kind of the operator
        int kind;
        switch(id_opType) {
            case FuzzyOperator.AND:
            case FuzzyOperator.OR:
            case FuzzyOperator.ALSO:
            case FuzzyOperator.IMP: kind = XflPackage.BINARY; break;

            case FuzzyOperator.NOT:
            case FuzzyOperator.MOREORLESS:
            case FuzzyOperator.VERY:
            case FuzzyOperator.SLIGHTLY: kind = XflPackage.UNARY; break;

            case FuzzyOperator.DEFUZMETHOD: kind =  XflPackage.DEFUZ; break;

            default: kind = -1;
        }

        Vector available = new Vector();
        available.addElement("default");
        Vector pkglist = FuzzySystem.getLoadedPackages();
        for(int i=0, size=pkglist.size(); i<size; i++) {
            XflPackage pkg = (XflPackage) pkglist.elementAt(i);
            Vector vv = pkg.get(kind);
            for(int j=0, vvsize=vv.size(); j<vvsize; j++)
                available.addElement( vv.elementAt(j) );
        }

        return available;
    }


    // /**
    // * Returns a Map of the available types to be used in the MFType identification
    // */
    // public static Map<String,String> getMFTypes(){
    //     Map<String,String> mftps = new HashMap<String, String>();


    //     Vector available = MF.getAvailableMFTypes();
    //     for(int i = 0; i < available.size(); i++){

    //         //String type = available.elementAt(i).pkg + "." + available.elementAt(i).name;
    //         mftps.put(String.valueOf(i), available.elementAt(i).toString());
    //     }
    //     return mftps;
    // }


    // /**
    // * Return (creating it from the spec file) the given MF,
    // * Passing it's FuzzySystem, and it's id(the position of this MF in the MFs array)
    // * if any error occours, like the id_mf is out of the array bound, then raise an Exception
    // * that should be treated in along
    // */
    // public static MF get(FuzzySystem sys, Integer id_tp,Integer id_mf)
    // throws Exception {

    //     //Get the given Fuzzy Type or raise an exception
    //     xfuzzy.lang.ParamMemFunc fuzzyMF = MF.getFuzzy(sys, id_tp, id_mf);

    //   return MF.createFromFuzzyMF(
    //             fuzzyMF,
    //             id_mf
    //     );

    // }
    

    // /**
    // * Return  the given MF(the fuzzy one),
    // * Passing it's FuzzySystem, and it's id(the position of this MF in the MFs array)
    // * if any error occours, like the id_mf is out of the array bound, then raise an Exception
    // * that should be treated in along
    // */
    // public static xfuzzy.lang.ParamMemFunc getFuzzy(FuzzySystem sys, Integer id_tp, Integer id_mf)
    // throws Exception {

    //     //Get the given Fuzzy Type or raise an exception
    //     xfuzzy.lang.Type fuzzyType = Type.getFuzzy(sys,id_tp);


    //     xfuzzy.lang.ParamMemFunc mfs [] =  fuzzyType.getAllMembershipFunctions();


    //     //ensures that the required mf id is within the bounds mfs array
    //     if(mfs.length <= id_mf || id_mf < 0){
    //         throw new Exception("Wrong MF ID:"+id_mf);
    //     }


    //   return mfs[id_mf];
    // }

    

  //   /**
  //   * Creates a MF using a XFuzzy ParamMemFunc as base
  //   */
  //   public static MF createFromFuzzyMF(xfuzzy.lang.ParamMemFunc pmf, Integer id_mf){
  //   	MF mf = new MF();
  //   	mf.id = id_mf;
		// mf.label = pmf.label;
		// mf.name = pmf.name;
		// mf.pkg = pmf.pkg;
		
		// //add the parameters for this mfs
		// mf.params = new ArrayList<Double>();
		// for(int i=0; i < pmf.parameter.length; i++){
		// 	mf.params.add(pmf.parameter[i].value);
		// }
			
    			
  //   	return mf;
  //   }


    
}