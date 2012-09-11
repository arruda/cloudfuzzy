package models;

import java.io.File;
import java.util.*;

import play.Play;
import play.db.ebean.Model;
import play.data.validation.Constraints.*;
import xfuzzy.Xfuzzy;
import xfuzzy.lang.Specification;
import xfuzzy.lang.XflParser;

import javax.persistence.*;

@Entity
public class FuzzySystem extends Model {
    
  @Id
  public Long id;
  
  @Required
  public String name;

  public String fileName;
  
  public String filePath;

  @ManyToOne
  public User user;
  
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
   * Creates a Fuzzy System with a default content for the file, using the filename.
   * the filePath is set to the default path of xfl + the user email.
   */
  public static void create(FuzzySystem sys) {

	//sets the filepath to be in the user email folder.
	String xflPath = Play.application().configuration().getString("xflPath");
    //appends the useremail with the xfl path.
    xflPath += sys.user.email + "/";
    sys.filePath=xflPath;

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
      return find.where()
          .eq("fuzzy_system.user.email", user)
          .eq("id", system)
          .findRowCount() > 0;
  }
  
  ////////// Queries END //////////


  /**
  *Returns this FuzzySystem full file path.
  */
  public String getFileFullPath(){
	  return  this.filePath + "/"+ this.fileName; 
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
  */
  public Specification getSpecification(){
	  try{
		  return this.loadSpecification();
	  }
	  catch(Exception e){
		  e.printStackTrace();
	  }
	  return null;
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
		
		return modeling;
  }
  
}
