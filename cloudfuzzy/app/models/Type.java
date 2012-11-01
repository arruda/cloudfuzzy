package models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.validation.Constraint;

import play.Play;
import play.data.validation.*;

import models.FuzzySystem;

import xfuzzy.lang.Specification;
import xfuzzy.lang.Universe;
import xfuzzy.lang.XflException;
import xfuzzy.lang.ParamMemFunc;


import br.blog.arruda.plot.Plot;
import br.blog.arruda.plot.data.PlotData;

public class Type {
	
	/**
	 * This ID is the position of the fuzzy type in the array of
	 * types in a specification.
	 */
    public Integer id;
	
    @Constraints.Required
    public String name;
    
    @Constraints.Required
    public Integer type;

    @Constraints.Required
    public Double max;

    @Constraints.Required
    public Double min;

    @Constraints.Required
    public Integer card;
    
    public Integer numMFs;

    public ArrayList<MF> MFs;
    
    public static final Map<Integer,String> MFTYPES = new HashMap<Integer, String>();
    static {
    	MFTYPES.put(1, "Triangular Something");
    };



    /**
    * Return (creating it from the spec file) the given Type,
    * Passing it's FuzzySystem, and it's id(the position of this type in the Types array)
    * if any error occours, like the id_tp is out of the array bound, then raise an Exception
    * that should be treated in along
    */
    public static Type get(FuzzySystem sys, Integer id_tp)
    throws Exception {

      return Type.createFromFuzzyType(
                Type.getFuzzy(sys, id_tp),
                id_tp
        );

    }
    

    /**
    * Return the given Type(The xFuzzy one) for a given id_tp,
    * Passing it's FuzzySystem, and it's id(the position of this type in the Types array)
    * if any error occours, like the id_tp is out of the array bound, then raise an Exception
    * that should be treated in along
    */
    public static xfuzzy.lang.Type getFuzzy(FuzzySystem sys, Integer id_tp)
    throws Exception {
      Specification spec=null;
      //can throw an exception
      spec = sys.getSpecification();  
      

      //the types for this modeling
      xfuzzy.lang.Type [] tps = spec.getTypes();

      //ensures that the required type id is within the bounds tps array
      if(tps.length <= id_tp || id_tp < 0){
            throw new Exception("Wrong Type ID:"+id_tp);
      }

      return tps[id_tp];
    }


    public static void create(Type newType, Specification spec){
    	int i = Integer.valueOf("12");
    	xfuzzy.lang.Universe u = null;
		  //Creates the universe
		  try {
			u = new xfuzzy.lang.Universe(newType.min, newType.max, newType.card);
		} catch (XflException e) {
		}

//		  if(parent == null) { u = getUniverse(); if(u == null) return null; }

//		  if(parent == null) tp = new Type(name,u);
//		  else tp = new Type(name,parent);

		  xfuzzy.lang.Type tp = new xfuzzy.lang.Type(newType.name, u);
		  tp.createMemFuncs(newType.type,newType.numMFs);
		  
		  spec.addType(tp);
		  spec.setModified(true);
		  spec.save();		  
    }
    
    
    public static Type createFromFuzzyType(xfuzzy.lang.Type tp, Integer id_tp){
    	Type type = new Type();
    	
    	type.id = id_tp;
    	
    	type.name = tp.getName();
    	
    	//sets type to zero, since this is not used in this case but is required
    	type.type = 0;

    	//universe
    	type.max = tp.getUniverse().max();
    	type.min = tp.getUniverse().min();
    	type.card = tp.getUniverse().card();
    	
    	type.numMFs = tp.getMembershipFunctions().length;

        type.MFs = new ArrayList<MF>();
        int mf_id=0;
        for(xfuzzy.lang.ParamMemFunc pmf : tp.getAllMembershipFunctions()){
            MF newMF = MF.createFromFuzzyMF(pmf,mf_id);
            type.MFs.add(newMF);
            mf_id+=1;
        }
		
    			
    	return type;
    }
    

    public static void edit(Type editedType, xfuzzy.lang.Type fuzzyType,  Specification spec)
        throws Exception{

    	//sets the new name, or keep the same if not changed.
    	fuzzyType.setName(editedType.name);
    	
        xfuzzy.lang.Universe originalUniverse = fuzzyType.getUniverse();

        //need to check if this universe is valid.
	    //changes the universe
	    try {
  			  originalUniverse.set(editedType.min,editedType.max,editedType.card);
		} catch (XflException e) {
			e.printStackTrace();
		}

        //Change the MF's universe to use the new one
        ParamMemFunc editedMFs[] = fuzzyType.getMembershipFunctions();

        for(int i=0; i<editedMFs.length; i++){
            //well... this 'u' lost here is the MF's universe...
            editedMFs[i].u = originalUniverse;
        } 


        //set this new MF's in the fuzzyType
        fuzzyType.setMembershipFunctions(editedMFs);

          if(!fuzzyType.testUniverse(editedType.min,editedType.max,editedType.card)) {
            throw new Exception("Universe conflicts with defined MFs");
          }

          //spec.exchange(fuzzyType,fuzzyType);
		  spec.setModified(true);
		  spec.save();		  
    }
    
    /**
    * Return the MFs Plot for this given id_type and id_sys
    */
    public static Plot getMFsPlots(xfuzzy.lang.Type tp){

        xfuzzy.lang.ParamMemFunc[] mfs = tp.getAllMembershipFunctions();

        ArrayList<PlotData> datas = new ArrayList<PlotData>();
        for(int i=0; i<mfs.length; i++){
            datas.add(MF.getMFPlotData(mfs[i],tp));
        } 

        //generate a simple plot using the datas set above and with xlabel and ylabel.
        Plot plot = Plot.generatePlot(datas, "x axis", "y axis");
        return plot;
    }



  // private void paintFunctions() {
  //  ParamMemFunc[] mf = copy.getAllMembershipFunctions();
  //  for(int i=0; i<mf.length; i++) if(mf[i]!=mfexcl) paintFunction(mf[i],gmf);
  //  if(mfsel != null && mfsel.test() ) paintFunction(mfsel,gsel);
  // }
}
