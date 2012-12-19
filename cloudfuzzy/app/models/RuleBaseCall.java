package models;

import java.io.File;
import java.util.*;

import play.Play;
import play.db.ebean.Model;
import play.data.validation.Constraints.*;
import xfuzzy.Xfuzzy;
import xfuzzy.lang.Specification;
import xfuzzy.lang.XflParser;
import xfuzzy.lang.PkgParser;
import xfuzzy.lang.XflPackage;


import javax.persistence.*;

@Entity
public class RuleBaseCall extends Model {
    
  @Id
  public Long id;
  
  @Required
  public Integer position;

  @Required
  public Double xPos;
  
  @Required
  public Double yPos;

  @ManyToOne
  public FuzzySystem system;
  


  ////////// Queries //////////

  /**
  * Returns a finder by ID as the index for the FuzzySystem class.
  */
  public static Finder<Long,RuleBaseCall> find = new Finder(
    Long.class, RuleBaseCall.class
  );
  
  /**
  * Returns all fuzzySystems.
  */
  public static List<RuleBaseCall> all() {
    return find.all();
  }  

  /**
  * Returns all fuzzySystems for a given user email.
  */
  public static List<RuleBaseCall> listRuleBaseCallsByFuzzySystemsId(Long id_sys) {

    return RuleBaseCall.find.where()
          .eq("system.id",id_sys)
          .orderBy("position asc")
          .findList();
  }  


  /**
   * Creates a RuleBaseCall.
   */
  public static void create(RuleBaseCall rbc) {
	  rbc.save();
  }
  
  /**
   * Return the RuleBaseCall that has the given parameters
   * @param sys_id
   * @param pos
   * @return
   */
  public static RuleBaseCall findByFuzzySystemIdAndPosition(Long sys_id, Integer pos)
  {
	    return RuleBaseCall.find.where()
	            .eq("system.id",sys_id)
	            .eq("position",pos)
	            .findUnique();
	  
  }

  /**
  * Delets the RulebaseCall with the given ID, 
  *calling the delete method from RulebaseCall object.
  */
  public static void delete(Long id) {

	  RuleBaseCall rbc = find.byId(id);
	  rbc.delete();
  }
  
  /**
  * Delets the RulebaseCall with the given System ID and position, 
  *calling the delete method from RulebaseCall object.
  */
  public static void delete(Long id_sys, Integer pos) {
	  RuleBaseCall rbc = findByFuzzySystemIdAndPosition(id_sys,pos);
	  rbc.delete();
  }
  
    
  /**
   * Check if a user is the owner of this FuzzySystem
   */
  public static boolean isOwner(Long system, String user) {

    // return FuzzySystem.find.where()
    //       .eq("fuzzy_system.user.email",user)
    //       .findList();

      return RuleBaseCall.find.where()
          .eq("user.email", user)
          .eq("id", system)
          .findRowCount() > 0;
  }
  
  ////////// Queries END //////////


  public String toString() {
      return "RuleBaseCall (" + this.system.name + " - "+ this.position +")";
  }

}
