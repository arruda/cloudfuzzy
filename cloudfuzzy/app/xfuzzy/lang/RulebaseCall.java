/*
 * @(#)RulebaseCall.java        1.0 2000/05/09
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
//	CLASE QUE ENCAPSULA UNA LLAMADA A UNA BASE DE REGLAS	//
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

package xfuzzy.lang;

import java.util.Vector;

public class RulebaseCall {
 Rulebase base;
 Variable inputvar[];
 Variable outputvar[];
 double degree[];
 MemFunc outputvalue[];

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //			    CONSTRUCTOR				//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 public RulebaseCall(Rulebase base, Variable inputvar[], Variable outputvar[]) {
  this.base = base; this.base.link();
  this.inputvar = inputvar;
  this.outputvar = outputvar;
  this.outputvalue = new MemFunc[outputvar.length];
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //		FUNCIONES DE LECTURA DE LOS CAMPOS		//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 public Rulebase getRulebase() { return base; }
 public Variable[] getInputVariables() { return inputvar; }
 public Variable[] getOutputVariables() { return outputvar; }

 public Variable getCallInput(Variable inner) {
  Variable[] inn = base.getInputs();
  for(int i=0; i<inn.length; i++) if(inn[i] == inner) return inputvar[i];
  return null;
 }
 
 public Variable getCallOutput(Variable inner) {
  Variable[] inn = base.getOutputs();
  for(int i=0; i<inn.length; i++) if(inn[i] == inner) return outputvar[i];
  return null;
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //	     FUNCIONES DE MODIFICACION DE LOS CAMPOS		//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 public void setInputVariables(Variable[] var) {
  this.inputvar = var;
 }

 public void setOutputVariables(Variable[] var) {
  this.outputvar = var;
  this.outputvalue = new MemFunc[var.length];
 }

 public void setInputVariable(Variable basevar, Variable sysvar) {
  Variable[] inn = base.getInputs();
  for(int i=0; i<inn.length; i++) if(inn[i] == basevar) inputvar[i] = sysvar;
 }

 public void setOutputVariable(Variable basevar, Variable sysvar) {
  Variable[] inn = base.getOutputs();
  for(int i=0; i<inn.length; i++) if(inn[i] == basevar) outputvar[i] = sysvar;
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //		FUNCIONES DE GENERACION DE CODIGO		//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 public String toXfl() {
  String eol = System.getProperty("line.separator", "\n");
  String code = "  "+base.getName()+"(";
  for(int i=0; i<inputvar.length; i++)
   code += (i==0? "": ", ")+inputvar[i].getName();
  code += " : ";
  for(int i=0; i<outputvar.length; i++)
   code += (i==0? "": ", ")+outputvar[i].getName();
  code += ");"+eol;
  return code;
 }

 public String toJava() {
  String eol = System.getProperty("line.separator", "\n");
  String code = "  _call = RL_"+base+"(";
  for(int j=0; j < inputvar.length; j++) code += (j==0? "":",")+inputvar[j];
  code += ");";
  for(int j=0; j < outputvar.length; j++)
    code += " "+outputvar[j]+"=_call["+j+"];";
  code += eol;
  return code;
 }

 public String toC() {
  String eol = System.getProperty("line.separator", "\n");
  String code = "";
  code += " RL_"+base+"(";
  for(int i=0; i < inputvar.length; i++) code += (i==0? "" : ", ")+inputvar[i];
  for(int i=0; i < outputvar.length; i++) code += ", &"+outputvar[i];
  code += ");"+eol;
  return code;
 }

 public String toCpp() {
  String eol = System.getProperty("line.separator", "\n");
  String code = "";
  code += " RL_"+base+"(";
  for(int i=0; i < inputvar.length; i++) {
   if(inputvar[i].isInput()) code += (i==0? "" : ", ")+inputvar[i];
   else code += (i==0? "*" : ", *")+inputvar[i];
  }
  for(int i=0; i < outputvar.length; i++) code += ", &"+outputvar[i];
  code += ");"+eol;
  return code;
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //			FUNCIONES DE CALCULO			//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 public void compute() {
  this.degree = new double[base.getRules().length];
  base.compute(inputvar, outputvar, degree, outputvalue);
 }

 public void derivative(double[] deriv) throws XflException {
  base.derivative(deriv);
 }

 public void derivative() throws XflException {
  base.derivative();
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //	    FUNCIONES QUE DEVUELVEN RESULTADOS INTERNOS		//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 public double[] getDegree() {
  return this.degree;
 }

 public MemFunc[] getFuzzyValues() {
  return this.outputvalue;
 }

 public MemFunc[] getTrueValues() {
  MemFunc value[] = new MemFunc[outputvar.length];
  for(int i=0; i<outputvar.length; i++) value[i] = outputvar[i].getValue();
  return value;
 }

 public MemFunc[] getInputValues() {
  MemFunc value[] = new MemFunc[inputvar.length];
  for(int i=0; i<inputvar.length; i++) value[i] = inputvar[i].getValue();
  return value;
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //			FUNCION DE INTERCAMBIO			//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 public void exchangeRulebase(Rulebase oldrb, Rulebase newrb) {
  if(base == oldrb) base = newrb;
 }
}

