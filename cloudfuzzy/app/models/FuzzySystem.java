 
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

import java.io.File;
import java.util.*;

import play.Play;
import play.db.ebean.Model;
import play.data.validation.Constraints.*;
import xfuzzy.Xfuzzy;
import xfuzzy.lang.AggregateMemFunc;
import xfuzzy.lang.Specification;
import xfuzzy.lang.XflParser;
import xfuzzy.lang.PkgParser;
import xfuzzy.lang.XflPackage;

import javax.persistence.*;

@Entity
public class FuzzySystem extends Model {
    
  @Id
  public Long id;
  
  @Required
  public String name;

  @Required
  public String description;

  public String fileName;
  
  public String filePath;

  @ManyToOne
  public User user;
  
  private Specification loadedSpecification = null;


  ////////// Queries //////////

  /**
  * Returns a finder by ID as the index for the FuzzySystem class.
  */
  public static Finder<Long,FuzzySystem> find = new Finder(
    Long.class, FuzzySystem.class
  );
  
  /**
  * Returns all fuzzySystems.
  */
  public static List<FuzzySystem> all() {
    return find.all();
  }  

  /**
  * Returns all fuzzySystems for a given user email.
  */
  public static List<FuzzySystem> listFuzzySystemsByUser(String user) {

    return FuzzySystem.find.where()
          .eq("user.email",user)
          .findList();
  }  


    // /**
    //  * Retrieve project for user
    //  */
    // public static List<Project> findInvolving(String user) {
    //     return find.where()
    //         .eq("members.email", user)
    //         .findList();
    // }
  
  /**
   * Return the xflPath for the given user, that is:
   *  the xflPath in the sys
   * and appent the user email to the end of it
   * @param sys
   * @return
   */
  public static String generateFilePath(FuzzySystem sys){
		String xflPath = Play.application().configuration().getString("xflPath");
	    //appends the useremail with the xfl path.
	    xflPath += sys.user.email + File.separator;
	    return xflPath;
  }

  /**
   * Creates a Fuzzy System with a default content for the file, using the filename.
   * the filePath is set to the default path of xfl + the user email.
   */
  public static void create(FuzzySystem sys) {

	//sets the filepath to be in the user email folder.
	String xflPath = Play.application().configuration().getString("xflPath");
    //appends the useremail with the xfl path.
    sys.filePath=generateFilePath(sys);

    File file = new File(sys.filePath,sys.fileName);
    //create the parent dirs if they don't exist
    file.getParentFile().mkdirs();
    
    Specification newspec = new Specification(file);
    newspec.save();
    sys.save();
  }
  
  /**
  * Delets the FuzzySystem with the given ID, 
  *calling the delete method from FuzzySystem object.
  */
  public static void delete(Long id) {
	  FuzzySystem sys = find.byId(id);
	  sys.delete();
  }
  
    
  /**
   * Check if a user is the owner of this FuzzySystem
   */
  public static boolean isOwner(Long system, String user) {

    // return FuzzySystem.find.where()
    //       .eq("fuzzy_system.user.email",user)
    //       .findList();

      return FuzzySystem.find.where()
          .eq("user.email", user)
          .eq("id", system)
          .findRowCount() > 0;
  }
  
  ////////// Queries END //////////




  /**
  *Return the vector o loaded pkgs
  */
  public static Vector getLoadedPackages(){
    PkgParser pkgparser = new PkgParser();
    File path = new File( Xfuzzy.fuzzyPath );
    pkgparser.addPath(new File(path,"pkg"));
    XflPackage xflpkg = pkgparser.parse("xfl");

    Vector loadedpkg = new Vector();
    loadedpkg.add(xflpkg);
    return loadedpkg;
  }

  /**
  * Simple method taken from xFuzzy.utils.XConstant
  * returns if the name is a identifier
  */
 static public boolean isIdentifier(String name) {
  if(name.equals("")) return false;
  if(name.equals("operation")) return false;
  if(name.equals("type")) return false;
  if(name.equals("module")) return false;
  if(name.equals("extends")) return false;
  if(name.equals("using")) return false;
  if(name.equals("import")) return false;
  if(name.equals("if")) return false;
  char [] namec = name.toCharArray();
  if(!Character.isLetter(namec[0]) && namec[0] != '_') return false;
  for(int i=1; i<namec.length; i++)
   if(!Character.isLetterOrDigit(namec[i]) && namec[i] != '_') return false;
  return true;
 }

  /**
  *Returns this FuzzySystem full file path.
  */
  public String getFileFullPath(){
	  return  this.filePath + File.separator + this.fileName; 
  }
    
  /**
  *Returns the file for this FuzzySystem if exists, or null.
  */  
  public File getFile(){	  
	  File file = new File(this.getFileFullPath());

	  if(!file.isFile()){

		  return null;
	  }

	  return file;
	  
  }

  /**
  *Overriding delete method so that it delets the file
  *atached to this FuzzySystem.
  */
  @Override
  public void delete(){
	  super.delete();
	  File file = this.getFile();
	  if(file.isFile())
		  file.delete();
	  
  }





  /**
  *Return the Specification(XFuzzy) that this FuzzySystem uses or null.
  * if this FuzzySystem has already loaded a spec, then will use that.
  */
  public Specification getSpecification(){

    if(this.loadedSpecification == null){

  	  try{
        this.loadSpecification();
  	  }
  	  catch(Exception e){
  		  e.printStackTrace();
  	  }

    }
    return this.loadedSpecification;  
  }
  
  /**
  *Loads and return the Specification for this FuzzySystem or throw exception if 
  *no spec is loaded.
  */
  public Specification loadSpecification() throws Exception{
 
		XflParser parser = new XflParser();
		Specification modeling = parser.parse(this.getFile().getAbsolutePath());

		if (modeling == null) {
			modeling =null;
			throw new Exception(
					"motorInferencia.INCONSISTENCIA_MODELAGEM");
		}

		this.loadedSpecification = modeling;
		return modeling;
  }
  


    /**
    * Get the OperatorSet map for a given FuzzySystem id.
    * this map is the id (pos in the array) -> name
    * and the -1 is the default?
    */
    public static Map<String,String> getAvailableOperatorSetForFuzzySystem(FuzzySystem sys){

      Specification spec=null;
      try{
        spec = sys.getSpecification(); 
      }
      catch(Exception e){
        e.printStackTrace();
      }

      Map<String,String> availableOperatorSets = new HashMap<String, String>();

  // nameform.setText(copy.getName());
  // Vector opsetlist = new Vector();
  // opsetlist.add("default");
  // for(int i=0; i<opsetdef.length; i++) opsetlist.add(opsetdef[i]);
  // opsetbox.setList(opsetlist);
  // Operatorset used = copy.getOperatorset();
  // if(used == null || used.isDefault()) opsetbox.setSelectedIndex(0);
  // else opsetbox.setSelectedItem(used);

      //adds default as -1 index
      availableOperatorSets.put("","Default");

      xfuzzy.lang.Operatorset[] fuzzyOpSetList = spec.getOperatorsets();    
      for (int i=0; i< fuzzyOpSetList.length; i++ ) {
        // OperatorSet opSet = OperatorSet.createFromFuzzyOperatorSet(fuzzyOpSetList[i], i);

        availableOperatorSets.put(String.valueOf(i), fuzzyOpSetList[i].getName());
      }

      return availableOperatorSets;
    }


    /**
    * Get the Types map for a given FuzzySystem id.
    * this map is the id (pos in the array) -> name
    */
    public static Map<String,String> getAvailableTypesMapForFuzzySystem(FuzzySystem sys){

      Specification spec=null;
      try{
        spec = sys.getSpecification(); 
      }
      catch(Exception e){
        e.printStackTrace();
      }

      Map<String,String> availableTypes = new HashMap<String, String>();

  // nameform.setText(copy.getName());
  // Vector opsetlist = new Vector();
  // opsetlist.add("default");
  // for(int i=0; i<opsetdef.length; i++) opsetlist.add(opsetdef[i]);
  // opsetbox.setList(opsetlist);
  // Operatorset used = copy.getOperatorset();
  // if(used == null || used.isDefault()) opsetbox.setSelectedIndex(0);
  // else opsetbox.setSelectedItem(used);


      xfuzzy.lang.Type[] fuzzyTypeList = spec.getTypes();    
      for (int i=0; i< fuzzyTypeList.length; i++ ) {

        availableTypes.put(String.valueOf(i), fuzzyTypeList[i].getName());
      }

      return availableTypes;
    }
    
    /**
     * Tryies to retrieve a rulebasecall of a system from it's 
     * position in the array of ruleasecalls
     * 
     * @return
     */
    public RuleBaseCall getRulebaseCallPos(Integer position){
    	Specification spec = this.getSpecification();
    	
    	//see if there is indead that position
    	if(spec.getSystemModule().getRulebaseCalls().length <= position){
    		return null;
    	}
    	
    	RuleBaseCall rbc = RuleBaseCall.findByFuzzySystemIdAndPosition(this.id, position);
    	
    	
    	
    	return rbc;
    	
    }
    
    /**
     * Execute the fuzzy inference for a given input value
     * @param inputValue
     * @return
     * @throws Exception
     */
    public double[] executeInference(double [] inputValue) throws Exception{
    	for (int i = 0; i < inputValue.length; i++) {
			double d = inputValue[i];
	    	System.out.println("inputvalue["+i+"]:"+d);
			
		}
		// executa o motor de inferência e retorna o resultado num vetor
		// result[]da classe MemFunc
		xfuzzy.lang.MemFunc result[] = null;
		try {
			result = this.getSpecification().getSystemModule().fuzzyInference(inputValue);
		} catch (NullPointerException npe) {
			throw npe;
		}
		// cria um vetor de double com o número de variáveis correspondentes ao
		// length do vetor result
		double[] saida = new double[result.length];
		// percorre o vetor de saida fazendo typecast de double para
		// AggregateMemFunc
		for (int i = 0; i < saida.length; i++) {
			double val = 0;
			// obtem o valor defuzzificado que representa essa função de
			// pertinencia
			// no caso de ser singleton devolve a propria abcissa como sendo o
			// valor defuzzificado
			if (result[i] instanceof pkg.xfl_mf_singleton) {
				val = ((pkg.xfl_mf_singleton) result[i]).get()[0];

			} else {// Obtem a funcao de pertinencia resultante da agregacao de
					// todas as regras
				// usa o metodo defuzzify para obter o valor defuzzificado desse
				// conjunto resultante
				// faz typecast de result para o tipo AggregateMemFunc
				xfuzzy.lang.AggregateMemFunc amf = (xfuzzy.lang.AggregateMemFunc) result[i];
				val = amf.defuzzify();
			}

			saida[i] = val;
		}
		
		return saida;
    }
}
