package controllers;

import java.util.Map;
import java.util.ArrayList;
import java.io.File;

import models.FuzzySystem;
import models.User;
// import models.MF;
import models.Type;

import play.*;
import play.mvc.*;
import play.data.*;
import play.libs.Json;
import views.html.fuzzy.system.types.*;
// import views.html.*;

import xfuzzy.*;
import xfuzzy.lang.AggregateMemFunc;
import xfuzzy.lang.MemFunc;
import xfuzzy.lang.ParamMemFunc;
import xfuzzy.lang.Specification;
import xfuzzy.lang.Universe;
import xfuzzy.lang.XflParser;
// import xfuzzy.util.XConstants;


import br.blog.arruda.plot.Plot;
import br.blog.arruda.plot.data.PlotData;


@Security.Authenticated(Secured.class)
public class Types extends Controller {
  
  static Form<Type> newTypeForm = form(Type.class);
  static Form<Type> editTypeForm = form(Type.class);
  // static Form<MF> editMFForm = form(MF.class);
  

  //=================== CRUD ===================//
  /**
  * prepare to creates a new Fuzzy Type.
  */
  public static Result prepareCreate(Long systemId) {
      return ok(
               prepareCreate.render(systemId,newTypeForm)
               // index.render()
              );
  }

  /**
  * Creates a new Fuzzy type with some parameters.
  */
  public static Result create(Long systemId) {
      Form<Type> filledForm = newTypeForm.bindFromRequest();

      // Check the numMFs
      if(!filledForm.field("type").valueOr("").isEmpty()) {
          int type = Integer.valueOf(filledForm.field("type").valueOr(""));
          //if needed the number of mfs
          if(type != 8 || type != 9) {
              if(filledForm.field("numMFs").valueOr("").isEmpty()){
                  filledForm.reject("numMFs", "Need the number of MFs");                  
              }
              else{
                  int numMFs = Integer.valueOf(filledForm.field("numMFs").valueOr("")); 
                  if(numMFs<0){
                      filledForm.reject("numMFs", "Need a positive number");    
                      
                  }
              }
              
          }
      }
      FuzzySystem sys = FuzzySystem.find.byId(systemId);
      Specification spec=null;
      try{
          spec = sys.loadSpecification();             
      }
      catch(Exception e){
          e.printStackTrace();
      }
      
      //checks if the type exists with this name
      if(spec.searchType(filledForm.field("name").valueOr("")) != null){
          filledForm.reject("name", "Already exist a type with this name");  
      }
      if(!FuzzySystem.isIdentifier(filledForm.field("name").valueOr(""))) {
        filledForm.reject("name", "Invalid name");  
      }
      
      if(filledForm.hasErrors()) {
        return badRequest(
          prepareCreate.render(systemId,filledForm)
        );
      } else {
          Type newType = filledForm.get();
          Type.create(newType, spec);
         return redirect(routes.Systems.detail(systemId)); 
      }
  }


  /**
  * Detail a given type, the id_tp is nothing related to DB model.
  * Its the position of the type in the spec.types array.
  */
  public static Result detail(Long id_sys, Integer id_tp) {
      FuzzySystem sys = FuzzySystem.find.byId(id_sys);
      

      Type tp = null;
      try{
        tp = Type.get(sys,id_tp);
      }
      catch(Exception e){
            return badRequest();        
      }
      
    return ok(
            detail.render(sys,tp)
            ); 
  }

  /**
  * Deletes a given type, the id_tp is nothing related to DB model.
  * Its the position of the type in the spec.types array.
  */
  public static Result delete(Long id_sys, Integer id_tp) {
      FuzzySystem sys = FuzzySystem.find.byId(id_sys);
      xfuzzy.lang.Specification spec = sys.getSpecification();

      // xfuzzy.lang.Type [] tps = spec.getTypes();

      
      // if(tps.length <= id_tp || id_tp < 0){
      //       return badRequest();
      // }
      
      // xfuzzy.lang.Type tp = tps[id_tp];

      xfuzzy.lang.Type tp = null;
      try{
        tp = Type.getFuzzy(sys,id_tp);
      }
      catch(Exception e){
            return badRequest();        
      }


    // if(selection == null || selection.isEditing()) return;

    // Type selection = (Type) typelist.getSelectedValue();
    if(tp.isLinked()) {
        String msg = "Cannot remove type \""+tp.getName()+"\".";
        msg+="\nSystem is using this type.";
        System.out.println(msg); 
        return badRequest();  
    }
  
    spec.removeType(tp);
    spec.setModified(true);
    spec.save();      


    return redirect(routes.Systems.detail(id_sys)); 
  }


  /**
  *Prepare to Edit the given type.
  */
  public static Result prepareEdit(Long id_sys, Integer id_tp) {

      FuzzySystem sys = FuzzySystem.find.byId(id_sys);

      Type tp = null;
      try{
        tp = Type.get(sys,id_tp);
      }
      catch(Exception e){
            return badRequest();        
      }

      editTypeForm = form(Type.class).fill(
          tp
      );



      return ok(
               edit.render(sys, tp,editTypeForm)
               // index.render()
              );
  }
  /**
  *Edit the given type.
  */
  public static Result edit(Long id_sys, Integer id_tp) {

      Form<Type> filledForm = editTypeForm.bindFromRequest();

      FuzzySystem sys = FuzzySystem.find.byId(id_sys);
      Specification spec=null;
      try{
          spec = sys.getSpecification();             
      }
      catch(Exception e){
          e.printStackTrace();
      }

      Type tp = null;
      xfuzzy.lang.Type fuzzyType = null;
      try{
        tp = Type.get(sys,id_tp);
        fuzzyType = Type.getFuzzy(sys,id_tp);
      }
      catch(Exception e){
            return badRequest();        
      }
      

      
      //checks if the type exists with this name
      //considering that need to ignore the original type been edited.
      xfuzzy.lang.Type search_tp = spec.searchType(filledForm.field("name").valueOr(""));
      if(search_tp != null && search_tp != fuzzyType){
          filledForm.reject("name", "Already exist a type with this name");  
      }
    
      if(!FuzzySystem.isIdentifier(filledForm.field("name").valueOr(""))) {
        filledForm.reject("name", "Invalid name");  
      }
      
      if(filledForm.field("name").errors().size() > 0 ||
         filledForm.field("max").errors().size() > 0  ||
         filledForm.field("min").errors().size() > 0  ||
         filledForm.field("card").errors().size() > 0 ) {

        return badRequest(
                edit.render(sys, tp,filledForm)
        );
      } else {

          Type editedType = tp;

          //Type test = filledForm.get();
          editedType.name = filledForm.field("name").value();
          editedType.max = Double.valueOf(filledForm.field("max").value());
          editedType.min = Double.valueOf(filledForm.field("min").value());
          editedType.card = Integer.valueOf(filledForm.field("card").value());

          try{
            Type.edit(editedType,fuzzyType, spec);
          }
          catch(Exception e){
            filledForm.reject(e.getMessage());  

            return badRequest(
                edit.render(sys, tp,filledForm)
            );
          }

         return redirect( routes.Types.detail(id_sys,id_tp) ); 
      }
  }

   //=================== AJAX ===================//

    /**
    * Returns a json with the plot data for the mfs of a given id_sys and id_type
    */
    public static Result ajaxGetMFsPlotData()
    {
        //get the keys from request GET
        Map<String,String[]> queryParameters = request().queryString();
        Integer id_sys = Integer.valueOf( queryParameters.get("id_sys")[0] );
        Integer id_type = Integer.valueOf( queryParameters.get("id_type")[0] );



        //populate the axis information with some data
        ArrayList<Double> xAxis = new ArrayList<Double>();
        ArrayList<Double> yAxis = new ArrayList<Double>();
        for (int i =0;i<10;i++){
                xAxis.add(Double.valueOf(i));
                yAxis.add(Double.valueOf(i*10));
        }
        //populate the axis information with some data
        ArrayList<Double> xAxis2 = new ArrayList<Double>();
        ArrayList<Double> yAxis2 = new ArrayList<Double>();
        for (int i =0;i<10;i++){
                xAxis2.add(Double.valueOf(i*i));
                yAxis2.add(Double.valueOf(i*20));
        }
        //generate a PlotData from the axis information created above
        ArrayList<PlotData> datas = new ArrayList<PlotData>();
        datas.add(Plot.generatePlotData(xAxis, yAxis));
        datas.add(Plot.generatePlotData(xAxis2, yAxis2));

        //generate a simple plot using the datas set above and with xlabel and ylabel.
        Plot plot = Plot.generatePlot(datas, "x axis", "y axis");

        String jsonData = plot.printData();
        jsonData = jsonData.substring(0, jsonData.length()-2) + "]";


        return ok( 
                    "{\"datas\": " + 
                    jsonData + ", \"options\":" + plot.printOptions()
                    + "}"
                    );
    }

  
  
}