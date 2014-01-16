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


public class Monitorization {

		@Valid
		public List<Variable> inputvars;

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
			return monit;
		}

}