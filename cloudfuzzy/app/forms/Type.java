package forms;

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

import xfuzzy.lang.Specification;
import xfuzzy.lang.Universe;
import xfuzzy.lang.XflException;

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
        for(xfuzzy.lang.ParamMemFunc pmf : tp.getAllMembershipFunctions()){
            MF newMF = MF.createFromFuzzyMF(pmf);
            type.MFs.add(newMF);
        }
		
    			
    	return type;
    }
    

    public static void edit(Type editedType, xfuzzy.lang.Type fuzzyType,  Specification spec){
    	
    	//sets the new name, or keep the same if not changed.
    	fuzzyType.setName(editedType.name);
    	
		  //changes the universe
		  try {
		  	  //need to check if this universe is valid.
			  xfuzzy.lang.Universe ou = fuzzyType.getUniverse();
			  ou.set(editedType.min,editedType.max,editedType.card);
		} catch (XflException e) {
			e.printStackTrace();
		}


		  spec.setModified(true);
		  spec.save();		  
    }
    
    
}
