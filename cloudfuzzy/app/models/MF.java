package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import models.FuzzySystem;

import play.data.validation.Constraints;
import xfuzzy.lang.ParamMemFunc;
import xfuzzy.lang.Specification;
import xfuzzy.lang.XflException;
import xfuzzy.lang.XflPackage;
import xfuzzy.lang.Definition;


import br.blog.arruda.plot.Plot;
import br.blog.arruda.plot.data.PlotData;

public class MF{
    
    /**
     * This ID is the position of the fuzzy mf in the array of
     * MFs in a type.
     */
    public Integer id;

    @Constraints.Required
    public String label;
    
    
    public String name;

    @Constraints.Required
    public String idFunction;

    public String pkg;
    
    @Constraints.Required
    public ArrayList<Double> params;
//    public Double paramA;
//    public Double paramB;
//    public Double paramC;
//    public Double paramD;
//    public Double paramE;
    
    
    // public static final Map<String,String> MFTYPES = new HashMap<String, String>();
    // static {
    //     MFTYPES.put(1, "Triangular Something");
    // };

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
    *Returns the vector of MFs Types
    * from the xfl package 
    */
    public static Vector getAvailableMFTypes(){        
        Vector pkglist = FuzzySystem.getLoadedPackages();
        Vector available = new Vector();
        for(int i=0, size=pkglist.size(); i<size; i++) {
            XflPackage pkg = (XflPackage) pkglist.elementAt(i);
            Vector vv = pkg.get(XflPackage.MFUNC);
            int vvsize = vv.size();
            for(int j=0; j<vvsize; j++)
            {
                available.addElement( vv.elementAt(j) ); 
            } 
        }
        return available;
    }

    /**
    * Returns a Map of the available types to be used in the MFType identification
    */
    public static Map<String,String> getMFTypes(){
        Map<String,String> mftps = new HashMap<String, String>();


        Vector available = MF.getAvailableMFTypes();
        for(int i = 0; i < available.size(); i++){

            //String type = available.elementAt(i).pkg + "." + available.elementAt(i).name;
            mftps.put(String.valueOf(i), available.elementAt(i).toString());
        }
        return mftps;
    }


    /**
    * Return a blank MF(Fuzzy) for the given MFType
    * MF Type(id_mfType).
    * this id is the position of the MFType in the Available MF Types Vector
    */
    public static ParamMemFunc getBlankMFForMFType(Integer id_mfType){

        //get the MF definition for this type
        Definition def = null;
        try{ 
            def = (Definition) MF.getAvailableMFTypes().elementAt(id_mfType); 
        }
        catch(Exception ex) {
        } 

        //Instantiate a MF to get its total parameters
        ParamMemFunc mf  = (ParamMemFunc) def.instantiate(); 

        return mf;
    }

    /**
    * Return the total number of parameters of a given
    * MF Type(id_mfType).
    * this id is the position of the MFType in the Available MF Types Vector
    */
    public static Integer getNumParamsForMFType(Integer id_mfType){



        //Instantiate a MF to get its total parameters
        ParamMemFunc mf  = getBlankMFForMFType(id_mfType); 

        return mf.parameter.length;

    }


    // public static Map<String,String> getMFTYPES(FuzzySystem sys, Integer id_tp,

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

    /**
    *Create a MF(fuzzy) with the given MF as source, adding it to the Type given in the spec.
    */
    public static void create(MF newMF, xfuzzy.lang.Type tp, Specification spec)
    throws Exception{



        //Instantiate a new MF for the given type.
        ParamMemFunc newFuzzyMF = MF.getBlankMFForMFType(Integer.valueOf(newMF.idFunction));
        //set its name and universe.
        newFuzzyMF.label = newMF.label;
        newFuzzyMF.u = tp.getUniverse();

        //set the parameters
       for(int i=0; i < newMF.params.size(); i++) {
            newFuzzyMF.parameter[i].value = newMF.params.get(i);
       }

        //test if the MF works out with the given parameters
        if(!newFuzzyMF.test()) {
            throw new Exception("Invalid parameters");
        }

        //create a new array for the MFs using the original as base
        //leaving the last position of the new array for the new MF
        ParamMemFunc originalMfs[] = tp.getMembershipFunctions();  
        ParamMemFunc newMFs[] = new ParamMemFunc[originalMfs.length+1];
        System.arraycopy(originalMfs,0,newMFs,0,originalMfs.length);

        //set the new MF
        newMFs[originalMfs.length] = newFuzzyMF;

        //set the Type's MFs to be this new MF array
        tp.setMembershipFunctions(newMFs);

        //save the spec.
        spec.setModified(true);
        spec.save();        
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


    /**
    * Creates a PlotData passing by a Fuzzy MF as parameter and the specification.
    * Used to make the grafics in the Type/MF detail/create/edit
    */
    public static PlotData getMFPlotData(xfuzzy.lang.ParamMemFunc fuzzyMF, xfuzzy.lang.Type tp){
        PlotData plotData;

        ArrayList<Double> xAxis = new ArrayList<Double>();
        ArrayList<Double> yAxis = new ArrayList<Double>();
        double min = tp.getUniverse().min(); 
        double max = tp.getUniverse().max(); 
        double step = tp.getUniverse().step(); 

        // if(fuzzyMF instanceof pkg.xfl_mf_singleton) {
        //     double value = fuzzyMF.get()[0];
        //     double x = (value - min)/(max-min);

        //     //what should do here?
        //     // if(x>=x0 && x<=x1) gc.drawLine(x,y0,x,y1);
        //     // return;
        // }

        double next = min + step;
        double xp = 0;
        double yp = fuzzyMF.compute(min); //should be 0 here
        // xAxis.add(xp);
        // yAxis.add(yp);
        for(int xi=0+1; xi<=100; xi++) {
            double x = min + xi*(max-min)/100;

            while(x >= next) {
                double yi = fuzzyMF.compute(next);

                //what should do here?
                //gc.drawLine(xp,yp,xi,yi);
                xAxis.add(xp);
                yAxis.add(yp);
                xp = xi;
                yp = yi;
                next += step;

            }
            double yi = fuzzyMF.compute(x);
            //what should do here?  
            // gc.drawLine(xp,yp,xi,yi);
            // xAxis.add(xp);
            // yAxis.add(yp);
            xp = xi;
            yp = yi;
        }

        plotData = Plot.generatePlotData(xAxis, yAxis);
        plotData.setLabel(fuzzyMF.label);
        return plotData;

    }

  // private void paintFunction(ParamMemFunc mf, Graphics2D gc) {
  //  double min = copy.getUniverse().min(); 
  //  double max = copy.getUniverse().max(); 
  //  double step = copy.getUniverse().step(); 
  //  if(mf instanceof pkg.xfl_mf_singleton) {
  //   double value = mf.get()[0];
  //   int x = (int) ((value - min)*(x1-x0)/(max-min)) + x0;
  //   if(x>=x0 && x<=x1) gc.drawLine(x,y0,x,y1);
  //   return;
  //  }
  //  double next = min + step;
  //  int xp = x0;
  //  int yp = (int) (mf.compute(min)*(y1-y0)) + y0;
  //  for(int xi=x0+1; xi<=x1; xi++) {
  //   double x = min + (xi-x0)*(max-min)/(x1-x0);
  //   while(x >= next) {
  //    int yi = (int) (mf.compute(next)*(y1-y0)) + y0;
  //    gc.drawLine(xp,yp,xi,yi);
  //    xp = xi;
  //    yp = yi;
  //    next += step;
  //   }
  //   int yi = (int) (mf.compute(x)*(y1-y0)) + y0;
  //   gc.drawLine(xp,yp,xi,yi);
  //   xp = xi;
  //   yp = yi;
  //  }
  // }
    
}