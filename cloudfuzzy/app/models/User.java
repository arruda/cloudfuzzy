package models;

import java.io.File;
import java.util.*;
import javax.persistence.*;
import javax.validation.Constraint;

import play.Play;
import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;
import xfuzzy.lang.Specification;

import com.avaje.ebean.*;

/**
 * User entity managed by Ebean
 */
@Entity 
@Table(name="user")
public class User extends Model {

    @Id
    @Constraints.Required
    @Constraints.Email
    @Formats.NonEmpty
    public String email;
    
    @Constraints.Required
    public String name;
    
    @Constraints.Required
    public String password;
    
    // -- Queries
    
    public static Model.Finder<String,User> find = new Model.Finder(String.class, User.class);
    
    /**
     * Retrieve all users.
     */
    public static List<User> findAll() {
        return find.all();
    }

    /**
     * Retrieve a User from email.
     */
    public static User findByEmail(String email) {
         System.out.println("findemail>>"+email);
        return find.where().eq("email", email).findUnique();
    }

    //- End of Queries

    
    /**
     * Authenticate a User.
     */
    public static User authenticate(String email, String password) {
        return find.where()
            .eq("email", email)
            .eq("password", password)
            .findUnique();
    }

    /**
     * Creates the given user
     */
    public static void create(User user) {
    	user.save();
    }
    // --
    
    public String toString() {
        return "User(" + email + ")";
    }

}
