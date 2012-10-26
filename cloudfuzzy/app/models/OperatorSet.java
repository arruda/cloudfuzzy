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

import xfuzzy.lang.Definition;
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

    /**
    *Represent a operator that appear to be selected in the creation or edition of a OperatorsSet
    */
    public static class Operator{
        //whats the position of this operator in the opcode[]
        public Integer id;

        // public Integer opType;
        @Constraints.Required
        public String name;

        //what is the selected option for this 
        @Constraints.Required
        public String selectedOption;

        //the available options list
        @Valid
        public List<String> options;


        /**
        * Create a Operator from a given id_opType(id for the operator type)  and FuzzyOperator
        */    
        public static Operator createFromFuzzyOperator(Integer id_opType,                                                 
                                                 FuzzyOperator fuzzyOperator){

            Operator newOperator= new Operator();
            newOperator.id = id_opType;
            newOperator.name = oplabel[id_opType];
            newOperator.selectedOption = "0";
            newOperator.options =  new ArrayList<String>();

            Vector available = getAvailableOptionsForOperatorsByOPType(id_opType);
            for(int op_pos=0; op_pos < available.size(); op_pos++){ 
                //add to options                        
                String option = String.valueOf(available.elementAt(op_pos));
                newOperator.options.add(option);   
                //if its the default one wont try the cast to definition             
                if(op_pos == 0){
                    continue;
                }

                Definition def = (Definition) available.elementAt(op_pos);

                 if (def.getPackageName().equals(fuzzyOperator.pkg) &&
                    def.getName().equals(fuzzyOperator.name)){
                        //set as the selected option
                        newOperator.selectedOption=String.valueOf(op_pos);
                 }
            }


            //add the parameters

             // public double[] get() {
             //  double[] p = new double[parameter.length];
             //  for(int i=0; i<parameter.length; i++) p[i] = parameter[i].value;
             //  return p;
             // }

            return newOperator;
        }
        public String getOptionName(){
            Integer op_pos = Integer.valueOf(this.selectedOption);

            Vector available = getAvailableOptionsForOperatorsByOPType(this.id);

            String optionName = String.valueOf(available.elementAt(op_pos));
            return optionName;
        }
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



    /**
    * Return (creating it from the spec file) the given OperatorSet,
    * Passing it's FuzzySystem, and it's id(the position of this type in the OperatorSet array)
    * if any error occours, like the id_opset is out of the array bound, then raise an Exception
    * that should be treated in along
    */
    public static OperatorSet get(FuzzySystem sys, Integer id_opset)
    throws Exception {

      return OperatorSet.createFromFuzzyOperatorSet(
                OperatorSet.getFuzzy(sys, id_opset),
                id_opset
        );

    }
    /**
    * Return  the given OperatorSet(the fuzzy one),
    * Passing it's FuzzySystem, and it's id(the position of this OperatorSet in the OperatorSets array)
    * if any error occours, like the id_opset is out of the array bound, then raise an Exception
    * that should be treated in along
    */
    public static xfuzzy.lang.Operatorset getFuzzy(FuzzySystem sys, Integer id_opset)
    throws Exception {
      Specification spec=null;
      //can throw an exception
      spec = sys.getSpecification();  
      

      //the Operatorset for this modeling
      xfuzzy.lang.Operatorset [] opsets = spec.getOperatorsets();

      //ensures that the required type id is within the bounds tps array
      if(opsets.length <= id_opset || id_opset < 0){
            throw new Exception("Wrong OperatorSet ID:"+id_opset);
      }

      return opsets[id_opset];
    }

    /**
    *Create a OperatorSet(fuzzy) with the given OperatorSet as source, adding it to the given spec.
    */
    public static void create(OperatorSet newOpSet,  Specification spec){



        //Creates the newFuzzyOpSet with the correct name
        xfuzzy.lang.Operatorset newFuzzyOpSet = new xfuzzy.lang.Operatorset(newOpSet.name);

        for(int id_opType =0; id_opType < newOpSet.operators.size(); id_opType++){
            Integer id_option = Integer.valueOf(newOpSet.operators.get(id_opType).selectedOption);

            // FuzzyOperator newFuzOp = createFuzzyOperatorByOpType(id_opType, id_option)
            newFuzzyOpSet.set( 
                    createFuzzyOperatorByOpType(id_opType, id_option),  
                    //the FuzzyOperator Kind, ex: FuzzyOperator.AND
                    opcode[id_opType]
                    );
        }
        
        spec.addOperatorset(newFuzzyOpSet);
        //save the spec.
        spec.setModified(true);
        spec.save();        
    }

    
    /**
    * Create a OperatorSet from a given fuzzy Operator set and with the id_opset
    */    
    public static OperatorSet createFromFuzzyOperatorSet(xfuzzy.lang.Operatorset fuzzyOpset, Integer id_opset){
        OperatorSet opSet = new OperatorSet();
        
        opSet.id = id_opset;
        
        opSet.name = fuzzyOpset.getName();        
        
        //sets the operators
        opSet.operators = new ArrayList<Operator>();
        //should create a Operator for each one of the FuzzyOperators
        for(int id_opType=0; id_opType<opcode.length; id_opType++){
            Operator op = Operator.createFromFuzzyOperator(id_opType,fuzzyOpset.get(opcode[id_opType]));
            //add this new operator in the operators list
            opSet.operators.add(op);
        }

        return opSet;
    }
    

    /**
    *Create a FuzzyOperator(fuzzy) with the given id_opType (if its the AND, OR, NOT, etc..)
    * and id_option (which option was selected from the Operator.options)
    */
    public static FuzzyOperator createFuzzyOperatorByOpType(Integer id_opType, Integer id_option){
        Definition def = null;
        FuzzyOperator operator;
        

        try{
            def = (Definition) getAvailableOptionsForOperatorsByOPType(id_opType).elementAt(id_option);
        }
        catch(Exception ex) {
        }

        //Enters here if id_opType was the one for default
        if(def == null)
        {
            operator = xfuzzy.lang.Operatorset.getDefault(opcode[id_opType]);
        }
        //if is not the Default one, then isntantiate it from the definition
        else 
        {
            //get the new operator
            operator = (FuzzyOperator) def.instantiate();
        }
        //if this operator has no parameter or is default then just return it
        if(operator.get().length == 0 || operator.isDefault()){
            // copy.set(operator,opcode[id_opType]);
            return operator;
        }
        //if has more then one parameter and is not the default one
        //then need to get the parameter for it.
        // else {
            // ParamDialog dialog = new ParamDialog(xfeditor, operator, id_opType);
            // if(dialog.showDialog()){
            //     copy.set(operator,opcode[id_opType]);
            // }    
        // }

       return operator;
    }

}