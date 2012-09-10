/*
 * @(#)Rule.java        1.0 2000/05/09
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
//			       REGLA				//
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

package xfuzzy.lang;

public class Rule {
 private Relation premise;
 private Conclusion conclusion[];
 private double degree = 1;
 private double maxactivation = 0;
 private boolean adjustable = true;

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //			    CONSTRUCTOR				//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 public Rule(Relation premise) {
  this.premise = premise;
  this.conclusion = new Conclusion[0];
 }

 public Rule(Relation premise, double degree) {
  this.degree = degree;
  this.premise = premise;
  this.conclusion = new Conclusion[0];
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //			FUNCIONES GENERALES			//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 public Object clone(Rulebase rb) {
  Relation premiseclone = (Relation) premise.clone(rb);
  Rule clone = new Rule(premiseclone,this.degree);
  for(int i=0; i<conclusion.length; i++)
    clone.add( (Conclusion) conclusion[i].clone(rb) );
  return clone;
 }

 public void dispose() {
  this.premise.dispose();
  for(int i=0; i<this.conclusion.length; i++) this.conclusion[i].dispose();
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //		FUNCIONES DE LECTURA DE LOS CAMPOS		//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 public double getDegree() { return this.degree; }
 public Relation getPremise() { return this.premise; }
 public Conclusion[] getConclusions() { return this.conclusion; }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //		     FUNCIONES DE INTERCAMBIO			//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 public void exchange(ParamMemFunc oldmf, ParamMemFunc newmf) {
  premise.exchange(oldmf,newmf);
  for(int i=0; i<conclusion.length; i++) conclusion[i].exchange(oldmf,newmf);
 }

 public void exchange(Variable oldvar, Variable newvar) {
  premise.exchange(oldvar,newvar);
  for(int i=0; i<conclusion.length; i++) conclusion[i].exchange(oldvar,newvar);
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //		  FUNCIONES PARA ANNADIR VALORES		//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 public void add(Conclusion ncc) {
  Conclusion acc[] = new Conclusion[this.conclusion.length+1];
  System.arraycopy(this.conclusion,0,acc,0,this.conclusion.length);
  acc[this.conclusion.length] = ncc;
  this.conclusion = acc;
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //		FUNCIONES DE GENERACION DE CODIGO		//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 public String toXfl() {
  String eol = System.getProperty("line.separator", "\n");
  String code = "  ";
  if(degree != 1.0) code += "["+degree+"]";
  code += "if(";
  code += this.premise.toXfl();
  code += ") -> ";
  code += this.conclusion[0].toXfl();
  for(int i=1; i<this.conclusion.length; i++) 
    code += ", "+this.conclusion[i].toXfl();
  code += ";"+eol;
  return code;
 }

 public String toJava() {
  String eol = System.getProperty("line.separator", "\n");
  String code = "  _rl = ";
  if(degree != 1.0) code += degree+"*";
  code += premise.toJava()+";"+eol;
  for(int j=0; j<conclusion.length; j++)
    code += conclusion[j].toJava()+eol;
  return code;
 }

 public String toC() {
  String eol = System.getProperty("line.separator", "\n");
  String code = " _rl = ";
  if(degree != 1.0) code += degree+"*";
  code += premise.toC()+";"+eol;
  for(int j=0; j<conclusion.length; j++) code += conclusion[j].toC();
  return code;
 }

 public String toCpp() {
  String eol = System.getProperty("line.separator", "\n");
  String code = " _rl = ";
  if(degree != 1.0) code += degree+"*";
  code += premise.toCpp()+";"+eol;
  for(int j=0; j<conclusion.length; j++) code += conclusion[j].toCpp();
  return code;
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //			FUNCIONES DE CALCULO			//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 public double compute() throws XflException {
  double activation = this.degree * this.premise.compute();
  if(activation > maxactivation ) maxactivation = activation;
  for(int i=0; i<this.conclusion.length; i++)
    this.conclusion[i].compute(activation);
  return activation;
 }

 public void derivative() throws XflException {
  if(!adjustable) return;
  double deriv = 0;
  for(int i=0; i<this.conclusion.length; i++)
    deriv += this.conclusion[i].getDegreeDeriv();
  deriv = deriv * this.degree;
  if(deriv != 0) this.premise.derivative(deriv);
 }

 public void resetMaxActivation() { this.maxactivation = 0; }
 public double getMaxActivation() { return this.maxactivation; }
 public void setAdjustable() { this.adjustable = this.premise.isAdjustable(); }
 public boolean isAdjustable() { return this.adjustable; }
}
