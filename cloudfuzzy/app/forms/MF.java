package forms;

import java.util.ArrayList;

import models.FuzzySystem;

import play.data.validation.Constraints;
import xfuzzy.lang.ParamMemFunc;
import xfuzzy.lang.Specification;
import xfuzzy.lang.XflException;

public class MF{
    
    /**
     * This ID is the position of the fuzzy mf in the array of
     * MFs in a type.
     */
    public Integer id;

    @Constraints.Required
    public String label;
    
    @Constraints.Required
    public String name;

    @Constraints.Required
    public String idFunction;

    public String pkg;
    
    public ArrayList<Double> params;
//    public Double paramA;
//    public Double paramB;
//    public Double paramC;
//    public Double paramD;
//    public Double paramE;
    
    /**
     * 
     *returns in the format: pkg_mf_function
     * @return
     */
    public String fullFunctionName(){
    	return this.pkg + "_mf_" + this.name; 
    }


    /**
     * 
     *returns in the format: pkg.function
     * @return
     */
    public String functionName(){
        return this.pkg + "." + this.name; 
    }
    


    /**
    * Return (creating it from the spec file) the given MF,
    * Passing it's FuzzySystem, and it's id(the position of this MF in the MFs array)
    * if any error occours, like the id_mf is out of the array bound, then raise an Exception
    * that should be treated in along
    */
    public static MF get(FuzzySystem sys, Integer id_tp,Integer id_mf)
    throws Exception {

        //Get the given Fuzzy Type or raise an exception
        xfuzzy.lang.ParamMemFunc fuzzyMF = MF.getFuzzy(sys, id_tp, id_mf);

      return MF.createFromFuzzyMF(
                fuzzyMF,
                id_mf
        );

    }
    

    /**
    * Return  the given MF(the fuzzy one),
    * Passing it's FuzzySystem, and it's id(the position of this MF in the MFs array)
    * if any error occours, like the id_mf is out of the array bound, then raise an Exception
    * that should be treated in along
    */
    public static xfuzzy.lang.ParamMemFunc getFuzzy(FuzzySystem sys, Integer id_tp, Integer id_mf)
    throws Exception {

        //Get the given Fuzzy Type or raise an exception
        xfuzzy.lang.Type fuzzyType = Type.getFuzzy(sys,id_tp);


        xfuzzy.lang.ParamMemFunc mfs [] =  fuzzyType.getAllMembershipFunctions();


        //ensures that the required mf id is within the bounds mfs array
        if(mfs.length <= id_mf || id_mf < 0){
            throw new Exception("Wrong MF ID:"+id_mf);
        }


      return mfs[id_mf];
    }

    

    /**
    * Creates a MF using a XFuzzy ParamMemFunc as base
    */
    public static MF createFromFuzzyMF(xfuzzy.lang.ParamMemFunc pmf, Integer id_mf){
    	MF mf = new MF();
    	mf.id = id_mf;
		mf.label = pmf.label;
		mf.name = pmf.name;
		mf.pkg = pmf.pkg;
		
		//add the parameters for this mfs
		mf.params = new ArrayList<Double>();
		for(int i=0; i < pmf.parameter.length; i++){
			mf.params.add(pmf.parameter[i].value);
		}
    	
    	
			
    			
    	return mf;
    }

    public static void edit(MF editedMF, xfuzzy.lang.ParamMemFunc fuzzyMF,  Specification spec){

		fuzzyMF.label = editedMF.label;
		fuzzyMF.name = editedMF.name;
		fuzzyMF.pkg = editedMF.pkg;
  
		//if different sizes then create a new vector for the fuzzy one.
		if(fuzzyMF.parameter.length != editedMF.params.size()){
			fuzzyMF.parameter = new xfuzzy.lang.Parameter[editedMF.params.size()];
		}
		//changes the parameters
		for(int i=0; i < editedMF.params.size(); i++){
			// fuzzyMF.parameter[i].pmf.parameter[i].value);
		}


		  spec.setModified(true);
		  spec.save();		  
    }
    
}