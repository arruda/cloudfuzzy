package models;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import models.FuzzySystem;

import javax.validation.*;

import play.data.validation.Constraints;
import xfuzzy.lang.AggregateMemFunc;
import xfuzzy.lang.MemFunc;
import xfuzzy.lang.Specification;
import xfuzzy.lang.XflPackage;
import xfuzzy.lang.Definition;
import xfuzzy.lang.FuzzyOperator;


public class Monitorization {

		public List<Variable> inputvars;
		
		public List<Variable> outputvars;

		public String validate() {
		//          if(User.authenticate(email, password) == null) {
		//              return "Invalid user or password";
		//          }
			return null;
		}

		public static Monitorization get(FuzzySystem sys){
			Specification spec=null;
			spec = sys.getSpecification();


			Monitorization monit = new Monitorization();
			monit.inputvars = new ArrayList<Variable>();
			for (int i = 0; i < spec.getSystemModule().getInputs().length; i++) {
				Variable ivar = Variable.createFromFuzzyVariable(
											sys,
											spec.getSystemModule().getInputs()[i],
											i
						);
				monit.inputvars.add(ivar);
			}
			
			monit.outputvars = new ArrayList<Variable>();
			for (int i = 0; i < spec.getSystemModule().getOutputs().length; i++) {
				Variable ovar = Variable.createFromFuzzyVariable(
											sys,
											spec.getSystemModule().getOutputs()[i],
											i
						);
				monit.outputvars.add(ovar);
			}
			
			
			return monit;
		}

        /**
        * Executes the monitorization for this instance of Monitorization.
        * setting the outputs values of the same instance as the results of the monitorization.
         * @throws Exception 
        **/
        public void run(FuzzySystem sys) throws Exception{

			Specification spec=null;
			spec = sys.getSpecification();
			
        	//prepare the inputs to run the fuzzy inference
            double [] inputs = new double[this.inputvars.size()];
            for (int i = 0; i < this.inputvars.size(); i++) {
            	Variable ivar = this.inputvars.get(i);
            	
            	inputs[i] = ivar.value;
            	ivar = Variable.createFromFuzzyVariable(
						sys,
						spec.getSystemModule().getInputs()[i],
						i
    				);
            	this.inputvars.get(i).name = ivar.name;
            	
			}

			
			double [] outputs = sys.executeInference(inputs);
			this.outputvars = new ArrayList<Variable>();
			for (int i = 0; i < spec.getSystemModule().getOutputs().length; i++) {
				Variable ovar = Variable.createFromFuzzyVariable(
											sys,
											spec.getSystemModule().getOutputs()[i],
											i
						);
            	ovar.value = outputs[i];
            	this.outputvars.add(ovar);
			}

        }

}
