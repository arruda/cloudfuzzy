/*
 * @(#)Conclusion.java        1.0 2000/05/09
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
//		CONCLUSION DE UNA REGLA DIFUSA			//
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

package xfuzzy.lang;

public class Conclusion {
 private Variable var;
 private ParamMemFunc mf;
 private Rulebase mod;
 private ImpliedMemFunc imf;

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //			    CONSTRUCTOR				//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 public Conclusion(Variable var, ParamMemFunc mf, Rulebase mod) {
  this.var = var;
  this.var.link();
  this.mf = mf;
  this.mf.link();
  this.mod = mod;
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //			FUNCIONES GENERALES			//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 Object clone(Rulebase rbclone) {
  return new Conclusion(this.var, this.mf, rbclone);
 }

 public void dispose() { this.var.unlink(); this.mf.unlink(); }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //		FUNCIONES DE LECTURA DE LOS CAMPOS		//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 public Variable getVariable() { return this.var; }
 public ParamMemFunc getMembershipFunction() { return this.mf; }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //	     FUNCIONES DE MODIFICACION DE LOS CAMPOS		//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 public void setVariable(Variable var) { this.var = var; }
 public void setMembershipFunction(ParamMemFunc mf) { this.mf = mf; }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //		FUNCIONES DE BUSQUEDA E INTERCAMBIO		//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 void exchange(ParamMemFunc oldmf, ParamMemFunc newmf) {
  if(this.mf == oldmf) {
   this.mf.unlink();
   this.mf = newmf;
   this.mf.link();
  }
 }

 void exchange(Variable oldvar, Variable newvar) {
  if(this.var == oldvar) {
   this.var.unlink();
   this.var = newvar;
   this.var.link();
  }
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //		FUNCIONES DE GENERACION DE CODIGO		//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 public String toXfl() { return this.var.getName() + " = " + this.mf; }

 public String toJava() {
  return "  "+var+".set(_i_"+var+",_rl, _t_"+var+"."+mf+"); _i_"+var+"++;";
 }

 public String toC() {
  String eol = System.getProperty("line.separator", "\n");
  String code =  " "+var+"->degree[_i_"+var+"] = _rl;"+eol;
  code += " "+var+"->conc[_i_"+var+"] =  _t_"+var+"."+mf+";"+eol;
  code += " _i_"+var+"++;"+eol;
  return code;
 }

 public String toCpp() {
  String eol = System.getProperty("line.separator", "\n");
  String code =  " (*"+var+").conc[_i_"+var+"] = new RuleConclusion(_rl, ";
  code += "_t_"+var+"."+mf+".dup());";
  code += " _i_"+var+"++;"+eol;
  return code;
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //			FUNCIONES DE CALCULO			//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 void compute(double degree) {
  this.imf = new ImpliedMemFunc(mf,mod,degree);
  this.var.addConclusion(this.imf);
 }

 double getDegreeDeriv() { return this.imf.getDegreeDeriv(); }
}
