package testhelper;

import java.io.File;
import java.io.OutputStream;
import java.io.FileOutputStream;


import models.FuzzySystem;
import models.User;


import org.junit.*;
import static org.junit.Assert.*;

/**
 * Isoleted xfl file
 */
public class IsolatedXflFile {

    protected FuzzySystem testSystem;

    protected void prepareXflFile(String content, File file) throws Exception {
        try {
            OutputStream stream = new FileOutputStream(file);
            stream.write(content.getBytes());
            stream.close();
        } catch (Exception e) { 
            throw new Exception("Error writing XFL3 file. Changes not applied.");
        }

    }

    protected FuzzySystem createFuzzySystem(String content, String name,User user) throws Exception{
        FuzzySystem newSys = new FuzzySystem();

        newSys.name = name;
        newSys.description = "test xfl";
        newSys.fileName = newSys.name+".xfl";
        newSys.user = user;
        FuzzySystem.create(newSys);
        this.testSystem = FuzzySystem.find.where().eq("name", name).eq("user.email", user.email).findUnique();
        this.prepareXflFile(content,newSys.getFile());
        return this.testSystem;
    }

    @After
    public void removeIsolatedFuzzySystem() {
        if(this.testSystem != null){            
            this.testSystem.delete();
        }
    }

}