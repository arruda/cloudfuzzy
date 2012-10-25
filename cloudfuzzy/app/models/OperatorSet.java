package models;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import models.FuzzySystem;

import javax.validation.*;
import play.data.validation.Constraints;
import xfuzzy.lang.Specification;
import xfuzzy.lang.XflPackage;

import xfuzzy.lang.FuzzyOperator;


public class OperatorSet{
    
    /**
     * This ID is the position of the fuzzy OperatorSet in the vector of spec.getOperatorsets
     */
    public Integer id;
    
    @Constraints.Required
    public String name;
    @Valid
    public List<Operator> operators;
    //make the Operators
     // public Binary and;
     // public Binary or;
     // public Binary imp;
     // public Binary also;
     // public Unary not;
     // public Unary very;
     // public Unary moreorless;
     // public Unary slightly;
     // public DefuzMethod defuz;


    // /**
    // *Returns the vector of MFs Types
    // * from the xfl package 
    // */
    // public static Vector getAvailableMFTypes(){        
    //     Vector pkglist = FuzzySystem.getLoadedPackages();
    //     Vector available = new Vector();
    //     for(int i=0, size=pkglist.size(); i<size; i++) {
    //         XflPackage pkg = (XflPackage) pkglist.elementAt(i);
    //         Vector vv = pkg.get(XflPackage.MFUNC);
    //         int vvsize = vv.size();
    //         for(int j=0; j<vvsize; j++)
    //         {
    //             available.addElement( vv.elementAt(j) ); 
    //         } 
    //     }
    //     return available;
    // }

    /**
    *Represent a operator that appear to be selected in the creation or edition of a OperatorsSet
    */
    public static class Operator{
        // public Integer opType;
        @Constraints.Required
        public String name;

        //what is the selected option for this 
        @Constraints.Required
        public String selectedOption;

        //the available options list
        @Valid
        public List<String> options;
    };

    /**
    * Represents the options codes
    */
     private static int opcode[] = 
      { FuzzyOperator.AND, FuzzyOperator.OR, FuzzyOperator.NOT, FuzzyOperator.ALSO,
        FuzzyOperator.IMP, FuzzyOperator.MOREORLESS, FuzzyOperator.VERY,
        FuzzyOperator.SLIGHTLY, FuzzyOperator.DEFUZMETHOD };

    /**
    * The labels for the operators
    */
     private static String oplabel[] = 
      {"and", "or", "not", "also", "implication", "moreorless", "strongly",
       "slightly", "defuzzification" };

    /**
    *Get the available Operator for a given Operator Type
    */
    public static Vector getAvailableOptionsForOperatorsByOPType(int id_opType){
        //retrieve whats the kind of the operator
        int kind;
        switch(opcode[id_opType]) {
            case FuzzyOperator.AND:
            case FuzzyOperator.OR:
            case FuzzyOperator.ALSO:
            case FuzzyOperator.IMP: kind = XflPackage.BINARY; break;

            case FuzzyOperator.NOT:
            case FuzzyOperator.MOREORLESS:
            case FuzzyOperator.VERY:
            case FuzzyOperator.SLIGHTLY: kind = XflPackage.UNARY; break;

            case FuzzyOperator.DEFUZMETHOD: kind =  XflPackage.DEFUZ; break;

            default: kind = -1;
        }

        //prepare the available  Operators vector
        Vector available = new Vector();
        //add the default option
        available.addElement("default");

        //for each loaded package get the all the options for a given kind
        Vector pkglist = FuzzySystem.getLoadedPackages();
        for(int i=0, size=pkglist.size(); i<size; i++) {
            XflPackage pkg = (XflPackage) pkglist.elementAt(i);
            Vector vv = pkg.get(kind);
            for(int j=0, vvsize=vv.size(); j<vvsize; j++)
                available.addElement( vv.elementAt(j) );
        }

        return available;
    }


    /**
    * Get the Options map for a given Operator, ex:
    * and: (xfl.default, xfl.something, etc)
    */
    public static Map<String,String> getAvailableOptionsMapForOperatorsByOperatorLabel(String operatorLabel){

        System.out.println("operatorLabel:"+operatorLabel);
        Integer id_opType=null;
        
        //gets what's the id_opType of this operator label
        for (int i=0; i< oplabel.length; i++ ) {
            if(operatorLabel.equals(oplabel[i])){
                id_opType = i;
                break;
            }   
        }
        if(id_opType == null){
            return null;
        }

        Map<String,String> availableOptions = new HashMap<String, String>();

        //get the available options for this operator
        Vector available = getAvailableOptionsForOperatorsByOPType(id_opType);
        for(int op_pos=0; op_pos < available.size(); op_pos++){
            availableOptions.put(String.valueOf(op_pos), available.elementAt(op_pos).toString());
        }




        return availableOptions;
    }

    /**
    * Sets the operators of this operator set to be with the correct default value
    */
    public void setDefaultOperatorsList(){
        this.operators = new ArrayList<Operator>();

        //should create a Operator for each one of the FuzzyOperators
        for(int i=0; i<opcode.length; i++){
            Operator op = new Operator();
            op.name = oplabel[i];
            op.options = new ArrayList<String>();

            //get the available options for this operator
            Vector available = getAvailableOptionsForOperatorsByOPType(i);
            for(int op_pos=0; op_pos < available.size(); op_pos++){
                op.options.add(String.valueOf(available.elementAt(op_pos)));
            }

            //leaves the default as the default selected option
            op.selectedOption = String.valueOf(0);

            //add this new operator in the operators list
            this.operators.add(op);
        }
    }


 // private boolean apply() {
 //  String name = nameform.getText();
 //  if(!XConstants.isIdentifier(name)) {
 //   nameform.setText("");
 //   XDialog.showMessage(nameform,"Invalid Name");
 //   return false;
 //  }
 //  Operatorset search = spec.searchOperatorset(name);
 //  if(search != null && search != original) {
 //   nameform.setText("");
 //   XDialog.showMessage(nameform,"Invalid Name: Operatorset already exists");
 //   return false;
 //  }
 //  if(original != null) {
 //   original.setName(name);
 //   for(int k=1; k<10; k++) original.set(copy.get(k),k);
 //  }
 //  else {
 //   copy.setName(name);
 //   spec.addOperatorset(copy);
 //   original = copy;
 //   original.setEditing(true);
 //  }
 //  spec.setModified(true);
 //  copy = (Operatorset) original.clone();
 //  xfeditor.refresh();
 //  return true;
 // }

    
}