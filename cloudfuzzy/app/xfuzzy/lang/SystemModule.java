/*
 * @(#)SystemModule.java        1.0 2000/05/09
 *
 * This file is part of Xfuzzy 3.0, a design environment for fuzzy logic
 * based systems.
 *
 * (c) 2000 IMSE-CNM. The authors may be contacted by the email address:
 *                    xfuzzy-team@imse.cnm.es
 *
 * Xfuzzy is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation.
 *
 * Xfuzzy is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * for more details.
 */

//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
//		 ESTRUCTURA GLOBAL DEL SISTEMA			//
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

package xfuzzy.lang;

public class SystemModule {
 private Variable var[];
 private RulebaseCall call[];

 public SystemModule() {
  this.var = new Variable[1];
  this.var[0] = new Variable("NULL",Variable.INNER);
  this.call = new RulebaseCall[0];
 }

 public void exchangeRulebase(Rulebase oldrb, Rulebase newrb) {
  for(int i=0; i<call.length; i++) call[i].exchangeRulebase(oldrb, newrb);
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //		   FUNCIONES QUE GENERAN CODIGO			//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 public String toXfl() {
  String eol = System.getProperty("line.separator", "\n");
  Variable input[] = getInputs();
  Variable output[] = getOutputs();
  String code = eol+"system (";
  for(int i=0; i<input.length; i++) code += (i==0? "" : ", ")+input[i].toXfl();
  code += " : ";
  for(int i=0; i<output.length; i++) code += (i==0? "":", ")+output[i].toXfl();
  code += ") {"+eol;
  for(int i=0; i<call.length; i++) code += call[i].toXfl();
  code += " }"+eol;
  return code;
 }

 public String toJava() {
  String eol = System.getProperty("line.separator", "\n");
  String code = "";
  code += " //+++++++++++++++++++++++++++++++++++++++++++++++++++++//"+eol;
  code += " //               Fuzzy Inference Engine                //"+eol;
  code += " //+++++++++++++++++++++++++++++++++++++++++++++++++++++//"+eol+eol;
  code += toJava(true,true);
  code += toJava(false,true);
  code += toJava(true,false);
  code += toJava(false,false);
  return code;
 }

 private String toJava(boolean crispInput, boolean crispOutput) {
  String eol = System.getProperty("line.separator", "\n");
  String inputtype = (crispInput? "double" : "MembershipFunction");
  String outputtype = (crispOutput? "double" : "MembershipFunction");
  String function = (crispOutput? "crispInference" : "fuzzyInference");
  Variable input[] = getInputs();
  Variable output[] = getOutputs();
  Variable inner[] = getInners();
  String code = " public "+outputtype+"[] "+function;
  code += "("+inputtype+"[] _input) {"+eol;
  for(int i=0; i<input.length; i++) {
   code += "  MembershipFunction "+input[i];
   if(crispInput) code += " = new FuzzySingleton(_input["+i+"]);"+eol;
   else code += " = _input["+i+"];"+eol;
  }
  for(int i=0; i<output.length; i++) 
   code += "  MembershipFunction "+output[i]+";"+eol;
  for(int i=1; i<inner.length; i++) 
   code += "  MembershipFunction "+inner[i]+";"+eol;
  code += "  MembershipFunction[] _call;"+eol;
  for(int i=0; i<call.length; i++) code += call[i].toJava();
  code += "  "+outputtype+" _output[] =";
  code += " new "+outputtype+"["+output.length+"];"+eol;
  if(crispOutput) for(int i=0; i<output.length; i++) {
   code += "  if("+output[i]+" instanceof FuzzySingleton)"+eol;
   code += "   _output["+i+"] = ((FuzzySingleton) "+output[i]+").getValue();";
   code += eol;
   code += "  else _output["+i+"]";
   code += " = ((OutputMembershipFunction) "+output[i]+").defuzzify();"+eol;
  } 
  else for(int i=0; i<output.length; i++) {
   code += "  _output["+i+"] = "+output[i]+";"+eol;
  }
  code += "  return _output;"+eol;
  code += " }"+eol+eol;
  return code;
 }

 public String toC(String name) {
  String eol = System.getProperty("line.separator", "\n");
  Variable input[] = getInputs();
  Variable output[] = getOutputs();
  Variable inner[] = getInners();

  String code = eol;
  code += "/***************************************/"+eol;
  code += "/*          Inference Engine           */"+eol;
  code += "/***************************************/"+eol+eol;
  code += "void "+name+"(";
  for(int i=0; i<input.length; i++) 
   code += (i==0? "" : ", ")+"double _d_"+input[i];
  for(int i=0; i<output.length; i++) 
   code += ", double *_d_"+output[i];
  code += ") {"+eol;
  for(int i=0; i<input.length; i++) {
   code += " FuzzyNumber "+input[i];
   code += " = createCrispNumber(_d_"+input[i]+");"+eol;
  }
  code += " FuzzyNumber ";
  for(int i=0; i<output.length; i++) code += (i!=0? ", ": "")+output[i];
  for(int i=1; i<inner.length; i++) code += ", "+inner[i];
  code += ";"+eol;
  for(int i=0; i<call.length; i++) code += call[i].toC();
  for(int i=0; i<output.length; i++) {
   code += " *_d_"+output[i]+" = ("+output[i]+".crisp? ";
   code += output[i]+".crispvalue :"+output[i]+".op.defuz("+output[i]+"));"+eol;
  }
  code += "}"+eol+eol;
  return code;
 }

 public String toCpp(String spname) {
  String eol = System.getProperty("line.separator", "\n");
  Variable input[] = getInputs();
  Variable output[] = getOutputs();
  Variable inner[] = getInners();

  String code = "";
  code += "//+++++++++++++++++++++++++++++++++++++//"+eol;
  code += "//          Inference Engine           //"+eol;
  code += "//+++++++++++++++++++++++++++++++++++++//"+eol+eol;
  code += "double* "+spname+"::crispInference(double *_input) {"+eol;
  for(int i=0; i<input.length; i++)
   code += " FuzzySingleton "+input[i]+"(_input["+i+"]);"+eol;
  code += " MembershipFunction ";
  for(int i=0; i<output.length; i++) code += (i!=0? ", *": "*")+output[i];
  for(int i=1; i<inner.length; i++) code += ", *"+inner[i];
  code += ";"+eol;
  for(int i=0; i<call.length; i++) code += call[i].toCpp();
  code += " double *_output = new double["+output.length+"];"+eol;
  for(int i=0; i<output.length; i++) {
   code += " if("+output[i]+"->getType() == MembershipFunction::CRISP) ";
   code += "_output["+i+"] = ((FuzzySingleton *) ";
   code += output[i]+")->getValue();"+eol;
   code += " else _output["+i+"] = ((OutputMembershipFunction *) ";
   code += output[i]+")->defuzzify();"+eol;
  }
  for(int i=0; i<output.length; i++) code += " delete "+output[i]+";"+eol;
  for(int i=1; i<inner.length; i++) code += " delete "+inner[i]+";"+eol;
  code += " return _output;"+eol;
  code += "}"+eol+eol;

  code += "double* "+spname+"::crispInference";
  code += "(MembershipFunction* &_input) {"+eol;
  for(int i=0; i<input.length; i++)
   code += " MembershipFunction & "+input[i]+" = _input["+i+"];"+eol;
  code += " MembershipFunction ";
  for(int i=0; i<output.length; i++) code += (i!=0? ", *": "*")+output[i];
  for(int i=1; i<inner.length; i++) code += ", *"+inner[i];
  code += ";"+eol;
  for(int i=0; i<call.length; i++) code += call[i].toCpp();
  code += " double *_output = new double["+output.length+"];"+eol;
  for(int i=0; i<output.length; i++) {
   code += " if("+output[i]+"->getType() == MembershipFunction::CRISP) ";
   code += "_output["+i+"] = ((FuzzySingleton *) ";
   code += output[i]+")->getValue();"+eol;
   code += " else _output["+i+"] = ((OutputMembershipFunction *) ";
   code += output[i]+")->defuzzify();"+eol;
  }
  for(int i=0; i<output.length; i++) code += " delete "+output[i]+";"+eol;
  for(int i=1; i<inner.length; i++) code += " delete "+inner[i]+";"+eol;
  code += " return _output;"+eol;
  code += "}"+eol+eol;

  code += "MembershipFunction** "+spname+"::fuzzyInference";
  code += "(double *_input) {"+eol;
  for(int i=0; i<input.length; i++)
   code += " FuzzySingleton "+input[i]+"(_input["+i+"]);"+eol;
  code += " MembershipFunction ";
  for(int i=0; i<output.length; i++) code += (i!=0? ", *": "*")+output[i];
  for(int i=1; i<inner.length; i++) code += ", *"+inner[i];
  code += ";"+eol;
  for(int i=0; i<call.length; i++) code += call[i].toCpp();
  for(int i=1; i<inner.length; i++) code += " delete "+inner[i]+";"+eol;
  code += " MembershipFunction **_output = ";
  code += "new (MembershipFunction *)["+output.length+"];"+eol;
  for(int i=0; i<output.length; i++)
   code += " _output["+i+"] = "+output[i]+";"+eol;
  code += " return _output;"+eol;
  code += "}"+eol+eol;

  code += "MembershipFunction** "+spname+"::fuzzyInference";
  code += "(MembershipFunction* &_input) {"+eol;
  for(int i=0; i<input.length; i++)
   code += " MembershipFunction & "+input[i]+" = _input["+i+"];"+eol;
  code += " MembershipFunction ";
  for(int i=0; i<output.length; i++) code += (i!=0? ", *": "*")+output[i];
  for(int i=1; i<inner.length; i++) code += ", *"+inner[i];
  code += ";"+eol;
  for(int i=0; i<call.length; i++) code += call[i].toCpp();
  for(int i=1; i<inner.length; i++) code += " delete "+inner[i]+";"+eol;
  code += " MembershipFunction **_output = ";
  code += "new (MembershipFunction *)["+output.length+"];"+eol;
  for(int i=0; i<output.length; i++)
   code += " _output["+i+"] = "+output[i]+";"+eol;
  code += " return _output;"+eol;
  code += "}"+eol+eol;

  code += "void "+spname+"::inference(";
  for(int i=0; i<input.length; i++) code+=(i!=0?",":"")+" double _i_"+input[i];
  for(int i=0; i<output.length; i++) code += ", double *_o_"+output[i];
  code += " ) {"+eol;
  for(int i=0; i<input.length; i++)
   code += " FuzzySingleton "+input[i]+"(_i_"+input[i]+");"+eol;
  code += " MembershipFunction ";
  for(int i=0; i<output.length; i++) code += (i!=0? ", *": "*")+output[i];
  for(int i=1; i<inner.length; i++) code += ", *"+inner[i];
  code += ";"+eol;
  for(int i=0; i<call.length; i++) code += call[i].toCpp();
  for(int i=0; i<output.length; i++) {
   code += " if("+output[i]+"->getType() == MembershipFunction::CRISP) ";
   code += "(*_o_"+output[i]+") = ((FuzzySingleton *) ";
   code += output[i]+")->getValue();"+eol;
   code += " else (*_o_"+output[i]+") = ((OutputMembershipFunction *) ";
   code += output[i]+")->defuzzify();"+eol;
  }
  for(int i=0; i<output.length; i++) code += " delete "+output[i]+";"+eol;
  for(int i=1; i<inner.length; i++) code += " delete "+inner[i]+";"+eol;
  code += "}"+eol+eol;

  return code;
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //		FUNCIONES DE ACESO A LAS VARIABLES		//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 public Variable[] getInputs() {
  int size = 0;
  for(int i=0; i<var.length; i++) if(var[i].isInput()) size++;
  Variable[] input = new Variable[size];
  for(int i=0,j=0; i<this.var.length; i++)
   if(var[i].isInput()) { input[j] = var[i]; j++; }
  return input;
 }

 public Variable[] getOutputs() {
  int size = 0;
  for(int i=0; i<var.length; i++) if(var[i].isOutput()) size++;
  Variable[] output = new Variable[size];
  for(int i=0,j=0; i<var.length; i++)
   if(var[i].isOutput()) { output[j] = var[i]; j++; }
  return output;
 }

 public Variable[] getInners() {
  int size = 0;
  for(int i=0; i<var.length; i++) if(var[i].isInner()) size++;
  Variable[] inner = new Variable[size];
  for(int i=0,j=0; i<var.length; i++)
   if(var[i].isInner()) { inner[j] = var[i]; j++; }
  return inner;
 }

 public Variable[] getVariables() {
  return this.var;
 }

 public void addVariable(Variable newvar) {
  Variable aux[] = new Variable[var.length+1];
  System.arraycopy(var,0,aux,0,var.length);
  aux[var.length] = newvar;
  var = aux;
 }

 public void removeVariable(Variable rmvar) {
  int index = -1;
  for(int i=0; i<var.length; i++) if(var[i] == rmvar) index = i;
  if(index <= 0 || rmvar.isLinked() ) return;
  Variable[] aux = new Variable[var.length-1];
  System.arraycopy(var,0,aux,0,index);
  System.arraycopy(var,index+1,aux,index,aux.length-index);
  if(rmvar.getType() != null) rmvar.getType().unlink(); 
  var = aux;
 }

 public Variable searchVariable(String varname) {
  for(int i=0; i<var.length; i++) if( var[i].equals(varname) ) return var[i];
  return null;
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //		FUNCIONES DE ACESO A LAS LLAMADAS		//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 public RulebaseCall[] getRulebaseCalls() { return call; }

 public void addCall(Rulebase ref, Variable ivar[], Variable ovar[]) {
  RulebaseCall aux[] = new RulebaseCall[call.length+1];
  System.arraycopy(call,0,aux,0,call.length);
  aux[call.length] = new RulebaseCall(ref,ivar,ovar);
  call = aux;
 }

 public void removeCall(RulebaseCall rmcall) {
  int index = -1;
  for(int i=0; i<call.length; i++) if(call[i] == rmcall) index = i;
  if(index == -1) return;
  RulebaseCall[] aux = new RulebaseCall[call.length-1];
  System.arraycopy(call,0,aux,0,index);
  System.arraycopy(call,index+1,aux,index,aux.length-index);
  call = aux;
 }

 public void exchangeCall(int i, int j) {
  RulebaseCall aux = call[i];
  call[i] = call[j];
  call[j] = aux;
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //		FUNCIONES QUE NECESITA EL APRENDIZAJE		//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 public void derivative(double[] deriv) throws XflException {
  Variable[] outputvar = getOutputs();
  for(int i=call.length-1; i>=0; i--) {
   Variable[] calloutput = call[i].getOutputVariables();
   double[] callderiv = new double[calloutput.length];
   for(int j=0; j<calloutput.length; j++)
    for(int k=0; k<outputvar.length; k++)
     if(calloutput[j] == outputvar[k])
      callderiv[j] = deriv[k];
   call[i].derivative(callderiv);
  }
 }

 public void derivative() throws XflException {
  for(int i=call.length-1; i>=0; i--) call[i].derivative();
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //		      FUNCIONES DE INFERENCIA			//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 public double [] crispInference(double input[]) {
  Variable[] inputvar = getInputs();
  Variable[] outputvar = getOutputs();
  double output[] = new double[outputvar.length];

  for(int i=0; i<var.length; i++) var[i].reset();
  for(int i=0; i<input.length; i++) inputvar[i].set(input[i]);
  for(int i=0; i<call.length; i++) call[i].compute();
  for(int i=0; i<outputvar.length; i++)
   if(outputvar[i].getValue() instanceof pkg.xfl_mf_singleton)
     output[i] = ((pkg.xfl_mf_singleton) outputvar[i].getValue()).get()[0];
   else output[i] = ((AggregateMemFunc) outputvar[i].getValue()).defuzzify();
  return output;
 }

 public double [] crispInference(MemFunc input[]) {
  Variable[] inputvar = getInputs();
  Variable[] outputvar = getOutputs();
  double output[] = new double[outputvar.length];

  for(int i=0; i<var.length; i++) var[i].reset();
  for(int i=0; i<input.length; i++) inputvar[i].set(input[i]);
  for(int i=0; i<call.length; i++) call[i].compute();
  for(int i=0; i<outputvar.length; i++)
   if(outputvar[i].getValue() instanceof pkg.xfl_mf_singleton)
     output[i] = ((pkg.xfl_mf_singleton) outputvar[i].getValue()).get()[0];
   else output[i] = ((AggregateMemFunc) outputvar[i].getValue()).defuzzify();
  return output;
 }

 public MemFunc [] fuzzyInference(double input[]) {
  Variable[] inputvar = getInputs();
  Variable[] outputvar = getOutputs();
  MemFunc output[] = new MemFunc[outputvar.length];

  for(int i=0; i<var.length; i++) var[i].reset();
  for(int i=0; i<input.length; i++) inputvar[i].set(input[i]);
  for(int i=0; i<call.length; i++) call[i].compute();
  for(int i=0; i<outputvar.length; i++) output[i] = outputvar[i].getValue();
  return output;
 }

 public MemFunc [] fuzzyInference(MemFunc input[]) {
  Variable[] inputvar = getInputs();
  Variable[] outputvar = getOutputs();
  MemFunc output[] = new MemFunc[outputvar.length];

  for(int i=0; i<var.length; i++) var[i].reset();
  for(int i=0; i<input.length; i++) inputvar[i].set(input[i]);
  for(int i=0; i<call.length; i++) call[i].compute();
  for(int i=0; i<outputvar.length; i++) output[i] = outputvar[i].getValue();
  return output;
 }

 public AggregateMemFunc[] getFuzzyValues() {
  Variable[] outputvar = getOutputs();
  AggregateMemFunc[] fuzzyvalue = new AggregateMemFunc[outputvar.length];
  for(int i=0; i<call.length; i++) {
   Variable[] calloutput = call[i].getOutputVariables();
   MemFunc[] callvalue = call[i].getFuzzyValues();
   for(int j=0; j<calloutput.length; j++)
    for(int k=0; k<outputvar.length; k++)
     if(calloutput[j] == outputvar[k])
      fuzzyvalue[k] = (AggregateMemFunc) callvalue[j];
  }
  return fuzzyvalue;
 }
}

