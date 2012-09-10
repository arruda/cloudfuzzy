/*
 * @(#)Rulebase.java        1.0 2000/05/09
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
//			  BASE DE REGLAS			//
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

package xfuzzy.lang;

import java.util.Vector;

public class Rulebase implements Cloneable {
 public Operatorset operation;

 private String name;
 private Variable inputvar[];
 private Variable outputvar[];
 private Rule rule[];
 private int link;
 private boolean editing = false;

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //			    CONSTRUCTOR				//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 public Rulebase(String name) {
  this.name = name;
  this.operation = null;
  this.inputvar = new Variable[0];
  this.outputvar = new Variable[0];
  this.rule = new Rule[0];
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //			FUNCIONES GENERALES			//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 public String toString() { return new String(this.name); }

 public boolean equals(String name) { return this.name.equals(name); }

 public Object clone() {
  Rulebase clone = new Rulebase(this.name);
  clone.setOperatorset(this.operation);
  Variable cloneinputvar[] = new Variable[inputvar.length];
  Variable cloneoutputvar[] = new Variable[outputvar.length];
  for(int i=0; i<inputvar.length; i++) {
   String varname = inputvar[i].getName();
   Type vartype = inputvar[i].getType();
   cloneinputvar[i] = new Variable(varname,vartype, Variable.INPUT);
   clone.addInputVariable(cloneinputvar[i]);
  }
  for(int i=0; i<outputvar.length; i++) {
   String varname = outputvar[i].getName();
   Type vartype = outputvar[i].getType();
   cloneoutputvar[i] = new Variable(varname,vartype, clone);
   clone.addOutputVariable(cloneoutputvar[i]);
  }
  for(int i=0; i<rule.length; i++) clone.addRule( (Rule) rule[i].clone(clone));
  for(int i=0; i<inputvar.length; i++)
   clone.exchange(inputvar[i], cloneinputvar[i]);
  for(int i=0; i<outputvar.length; i++)
   clone.exchange(outputvar[i], cloneoutputvar[i]);
  return clone;
 }

 public void dispose() {
  while(rule.length>0) remove(rule[0]);
  while(inputvar.length>0) removeInputVar(inputvar[0]);
  while(outputvar.length>0) removeOutputVar(outputvar[0]);
  setOperatorset(null);
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //		FUNCIONES DE LECTURA DE LOS CAMPOS		//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 public String getName() { return this.name; }
 public Operatorset getOperatorset() { return this.operation; }
 public Variable[] getInputs() { return this.inputvar; }
 public Variable[] getOutputs() { return this.outputvar; }
 public Rule[] getRules() { return this.rule; }
 public boolean isLinked() { return (this.link>0); }
 public boolean isEditing() { return this.editing; }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //	     FUNCIONES DE MODIFICACION DE LOS CAMPOS		//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 public void setOperatorset(Operatorset op) {
  if(this.operation != null) this.operation.unlink();
  this.operation = op;
  if(this.operation != null) this.operation.link();
 }

 public void link() { this.link++; }
 public void unlink() { this.link--; }
 public void setEditing(boolean editing) { this.editing = editing; }
 public void setName(String name) { this.name = name; }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //		  FUNCIONES PARA ANNADIR VALORES		//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 public void addInputVariable(Variable var) {
  Variable av[] = new Variable[this.inputvar.length+1];
  System.arraycopy(this.inputvar,0,av,0,this.inputvar.length);
  av[this.inputvar.length] = var;
  this.inputvar = av;
 }

 public void addOutputVariable(Variable var) {
  Variable av[] = new Variable[this.outputvar.length+1];
  System.arraycopy(this.outputvar,0,av,0,this.outputvar.length);
  av[this.outputvar.length] = var;
  this.outputvar = av;
 }

 public void addRule(Rule rule) {
  Rule arl[] = new Rule[this.rule.length+1];
  System.arraycopy(this.rule,0,arl,0,this.rule.length);
  arl[this.rule.length] = rule;
  this.rule = arl;
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //		  FUNCIONES PARA ELIMINAR VALORES		//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 public void remove(Rule rl) {
  int index = -1;
  for(int i=0; i<rule.length; i++) if(rule[i] == rl) index = i;
  if(index == -1) return;
  Rule[] aux = new Rule[rule.length-1];
  System.arraycopy(rule,0,aux,0,index);
  System.arraycopy(rule,index+1,aux,index,aux.length-index);
  rl.dispose();
  rule = aux;
 }

 public void removeAllRules() {
  for(int i=0; i<this.rule.length; i++) this.rule[i].dispose();
  this.rule = new Rule[0];
 }

 public void removeInputVar(Variable var) {
  boolean contains = false;
  if(inputvar.length == 0) return;
  Variable[] aux = new Variable[inputvar.length-1];
  for(int i=0,j=0; i<inputvar.length; i++)
    if(inputvar[i] != var) { aux[j]=inputvar[i]; j++; } else contains = true;
  if(contains) { var.getType().unlink(); inputvar = aux; }
 }

 public void removeOutputVar(Variable var) {
  boolean contains = false;
  if(outputvar.length == 0) return;
  Variable[] aux = new Variable[outputvar.length-1];
  for(int i=0,j=0; i<outputvar.length; i++)
    if(outputvar[i] != var) { aux[j]=outputvar[i]; j++; } else contains = true;
  if(contains) { var.getType().unlink(); outputvar = aux; }
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //		FUNCIONES DE BUSQUEDA E INTERCAMBIO		//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 public Variable searchVariable(String varname) {
  for(int i=0; i<this.inputvar.length; i++)
    if( this.inputvar[i].equals(varname) ) return this.inputvar[i];
  for(int i=0; i<this.outputvar.length; i++)
    if( this.outputvar[i].equals(varname) ) return this.outputvar[i];
  return null;
 }

 public void exchange(ParamMemFunc oldmf, ParamMemFunc newmf) {
  for(int i=0; i<rule.length; i++) rule[i].exchange(oldmf,newmf);
 }

 public void exchange(Variable oldvar, Variable newvar) {
  for(int i=0; i<rule.length; i++) rule[i].exchange(oldvar,newvar);
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //		FUNCIONES DE GENERACION DE CODIGO		//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 public String toXfl() {
  String eol = System.getProperty("line.separator", "\n");
  String code = "rulebase "+this.name+" (";
  for(int i=0; i<inputvar.length; i++)
   code += (i==0?"" :", ")+inputvar[i].toXfl();
  code += " : ";
  for(int i=0; i<outputvar.length; i++)
   code += (i==0?"" :", ")+outputvar[i].toXfl();
  code += ") ";
  if(!this.operation.isDefault()) code += "using "+this.operation.getName();
  code += " {"+eol;
  for(int i=0; i<this.rule.length; i++) code += this.rule[i].toXfl();
  code += " }"+eol;
  return code;
 }

 public String toJava() {
  String eol = System.getProperty("line.separator", "\n");
  String code = "";
  code += " //+++++++++++++++++++++++++++++++++++++++++++++++++++++//"+eol;
  code += " //  Rulebase RL_"+name+"  //"+eol;
  code += " //+++++++++++++++++++++++++++++++++++++++++++++++++++++//"+eol+eol;
  code += " private MembershipFunction[] RL_"+this.name+"(";
  for(int i=0; i<inputvar.length; i++)
     code += (i==0? "":", ")+"MembershipFunction "+inputvar[i];
  code += ") {"+eol;
  code += "  double _rl;"+eol;
  code += "  double _input[] = new double["+inputvar.length+"];"+eol;
  for(int i=0; i<this.inputvar.length; i++) {
     code += "  if("+inputvar[i]+" instanceof FuzzySingleton)"+eol;
     code += "   _input["+i+"] = ((FuzzySingleton) "+inputvar[i];
     code += ").getValue();"+eol;
    }
  code += "  OP_"+operation.getName()+" _op";
  code += " = new OP_"+operation.getName()+"();"+eol;
  for(int i=0; i<outputvar.length; i++) {
     code += "  OutputMembershipFunction "+outputvar[i];
     code += " = new OutputMembershipFunction();"+eol;
     code += "  "+outputvar[i]+".set("+computeOutputSize(i)+",_op,_input);"+eol;
    }
  for(int i=0; i<inputvar.length; i++) {
     code += "  TP_"+inputvar[i].getType()+" _t_"+inputvar[i];
     code += " = new TP_"+inputvar[i].getType()+"();"+eol;
    }
  for(int i=0; i<outputvar.length; i++) {
     code += "  TP_"+outputvar[i].getType()+" _t_"+outputvar[i];
     code += " = new TP_"+outputvar[i].getType()+"();"+eol;
    }
  for(int i=0; i<outputvar.length; i++)
     code += "  int _i_"+outputvar[i]+"=0;"+eol;
  for(int i=0; i<this.rule.length; i++) code += rule[i].toJava();
  code += "  MembershipFunction[] _output";
  code += " = new MembershipFunction["+outputvar.length+"];"+eol;
  if(operation.defuz.isDefault()) 
    for(int i=0; i<outputvar.length; i++) 
     code += "  _output["+i+"] = "+outputvar[i]+";"+eol;
  else for(int i=0; i<outputvar.length; i++) {
     code += "  _output["+i+"]";
     code += " = new FuzzySingleton("+outputvar[i]+".defuzzify());"+eol;
    }
  code += "  return _output;"+eol;
  code += " }"+eol+eol;
  return code;
 }

 public String toC() {
  String eol = System.getProperty("line.separator", "\n");
  String code = "";
  code += eol+"/***************************************/"+eol;
  code += "/*  Rulebase RL_"+this.name+" */"+eol;
  code += "/***************************************/"+eol;
  code += eol+"static void RL_"+this.name+"(";
  for(int i=0; i<inputvar.length; i++)
    code += (i==0? "": ", ")+"FuzzyNumber "+inputvar[i];
  for(int i=0; i<outputvar.length; i++)
    code += ", FuzzyNumber *"+outputvar[i];
  code += ") {"+eol;
  code += " OperatorSet _op = createOP_"+operation.getName()+"();"+eol;
  code += " double _rl, _output;"+eol;
  for(int i=0; i<outputvar.length; i++)
     code += " int _i_"+outputvar[i]+"=0;"+eol;
  for(int i=0; i<inputvar.length; i++) {
     code += " TP_"+inputvar[i].getType()+" _t_"+inputvar[i];
     code += " = createTP_"+inputvar[i].getType()+"();"+eol;
    }
  for(int i=0; i<outputvar.length; i++) {
     code += " TP_"+outputvar[i].getType()+" _t_"+outputvar[i];
     code += " = createTP_"+outputvar[i].getType()+"();"+eol;
    }
  code += " double *_input = (double*) malloc";
  code += "("+inputvar.length+"*sizeof(double));"+eol;
  for(int i=0; i<inputvar.length; i++)
     code += " _input["+i+"] = "+inputvar[i]+".crispvalue;"+eol;
  for(int i=0; i<outputvar.length; i++) {
     code += " *"+outputvar[i];
     code += " = createFuzzyNumber("+computeOutputSize(i);
     code += ","+inputvar.length+",_input,_op);"+eol;
    }
  for(int i=0; i<this.rule.length; i++) code += rule[i].toC();
  if(!operation.defuz.isDefault())
   for(int i=0; i<outputvar.length; i++) {
     code += " _output = _op.defuz(*"+outputvar[i]+");"+eol;
     code += " free("+outputvar[i]+"->degree);"+eol;
     code += " free("+outputvar[i]+"->conc);"+eol;
     code += " *"+outputvar[i]+" = createCrispNumber(_output);"+eol;
    }
  code += "}"+eol;
  return code;
 }

 public String toHpp() {
  String eol = System.getProperty("line.separator", "\n");
  String code = "";
  code += " void RL_"+this.name+"(";
  for(int i=0; i<inputvar.length; i++)
     code += (i==0? "": ", ")+"MembershipFunction &"+inputvar[i];
  for(int i=0; i<outputvar.length; i++)
     code += ", MembershipFunction ** _o_"+outputvar[i];
  code += ");"+eol;
  return code;
 }

 public String toCpp(String spname) {
  String eol = System.getProperty("line.separator", "\n");
  String code = "";
  code += "//+++++++++++++++++++++++++++++++++++++//"+eol;
  code += "//  Rulebase RL_"+this.name+" //"+eol;
  code += "//+++++++++++++++++++++++++++++++++++++//"+eol+eol;
  code += "void "+spname+"::RL_"+this.name+"(";
  for(int i=0; i<inputvar.length; i++)
     code += (i==0? "": ", ")+"MembershipFunction &"+inputvar[i];
  for(int i=0; i<outputvar.length; i++)
     code += ", MembershipFunction ** _o_"+outputvar[i];
  code += ") {"+eol;
  code += " OP_"+spname+"_"+operation.getName()+" _op;"+eol;
  code += " double _rl;"+eol;
  for(int i=0; i<outputvar.length; i++)
     code += " int _i_"+outputvar[i]+"=0;"+eol;
  code += " double *_input = new double["+inputvar.length+"];"+eol;
  for(int i=0; i<inputvar.length; i++)
     code += " _input["+i+"] = "+inputvar[i]+".getValue();"+eol;
  for(int i=0; i<outputvar.length; i++) {
     code += " OutputMembershipFunction *"+outputvar[i];
     code += " = new OutputMembershipFunction(";
     code += "new OP_"+spname+"_"+operation.getName()+"(),";
     code += computeOutputSize(i)+","+inputvar.length+",_input);"+eol;
    }
  for(int i=0; i<inputvar.length; i++)
     code+=" TP_"+spname+"_"+inputvar[i].getType()+" _t_"+inputvar[i]+";"+eol;
  for(int i=0; i<outputvar.length; i++)
     code+=" TP_"+spname+"_"+outputvar[i].getType()+" _t_"+outputvar[i]+";"+eol;

  for(int i=0; i<this.rule.length; i++) code += rule[i].toCpp();

  if(operation.defuz.isDefault())
    for(int i=0; i<outputvar.length; i++)
     code += " *_o_"+outputvar[i]+" = "+outputvar[i]+";"+eol;
  else for(int i=0; i<outputvar.length; i++) {
     code += " *_o_"+outputvar[i];
     code += " = new FuzzySingleton( (*"+outputvar[i]+").defuzzify());"+eol;
     code += " delete "+outputvar[i]+";"+eol;
    }
  code += " delete _input;"+eol;
  code += "}"+eol;
  return code;
 }

 private int computeOutputSize(int i) {
  int counter=0;
  for(int j=0; j<rule.length; j++) {
   Conclusion conc[] = rule[j].getConclusions();
   for(int k=0; k<conc.length; k++)
    if(conc[k].getVariable() == outputvar[i]) counter++;
  }
  return counter;
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //			FUNCIONES DE CALCULO			//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 public void compute(Variable[] ivar, Variable[] ovar, double[] degree,
 MemFunc[] value) {
  double ival[] = new double[ivar.length];
  for(int i=0; i<this.inputvar.length; i++)
   try{ ival[i] = ivar[i].getCrispValue(); } catch(Exception ex) { ival[i]=0; }

  for(int i=0; i<this.outputvar.length; i++) this.outputvar[i].reset();
  for(int i=0; i<this.inputvar.length; i++)
    this.inputvar[i].set(ivar[i].getValue());

  try { for(int i=0; i<this.rule.length;i++) degree[i]=this.rule[i].compute(); }
  catch(XflException e) {}

  for(int i=0; i<this.outputvar.length; i++)
   ((AggregateMemFunc) outputvar[i].getValue()).input = ival;

  if(this.operation.defuz.isDefault())
   for(int i=0; i<this.outputvar.length; i++)
    ovar[i].set(outputvar[i].getValue());
  else
   for(int i=0; i<this.outputvar.length; i++)
    ovar[i].set( ((AggregateMemFunc) outputvar[i].getValue()).defuzzify() );

  for(int i=0; i<this.outputvar.length; i++) value[i] = outputvar[i].getValue();
 }

 public void derivative(double[] derror) throws XflException {
  for(int i=0; i<outputvar.length; i++) outputvar[i].derivative(derror[i]);
  for(int i=0; i<rule.length; i++) rule[i].derivative();
 }

 public void derivative() throws XflException {
  for(int i=0; i<rule.length; i++) rule[i].derivative();
 }

 public void setAdjustable() {
  for(int i=0; i<rule.length; i++) rule[i].setAdjustable();
 }

 public void resetMaxActivation() {
  for(int i=0; i<rule.length; i++) rule[i].resetMaxActivation();
 }
}
