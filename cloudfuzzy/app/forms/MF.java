package forms;

import java.util.ArrayList;

import play.data.validation.Constraints;
import xfuzzy.lang.ParamMemFunc;
import xfuzzy.lang.Specification;
import xfuzzy.lang.XflException;

public class MF{
    
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
    * Creates a MF using a XFuzzy ParamMemFunc as base
    */
    public static MF createFromFuzzyMF(xfuzzy.lang.ParamMemFunc pmf){
    	MF mf = new MF();
    	
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