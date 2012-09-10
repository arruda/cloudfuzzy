/*
 * @(#)ImpliedMemFunc.java        1.0 2000/05/09
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
//              FUNCION DE PERTENENCIA IMPLICADA		//
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

package xfuzzy.lang;

public class ImpliedMemFunc implements MemFunc {
 private Rulebase mod;
 private ParamMemFunc mf;
 private double degree;
 private double derivative;

 ImpliedMemFunc(ParamMemFunc mf, Rulebase mod, double degree) {
  this.mf = mf;
  this.mod = mod;
  this.degree = degree;
  this.derivative = 0;
 }

 public double compute(double x) {
  return this.mod.operation.imp.compute(this.degree, this.mf.compute(x));
 }

 public double degree() {
  return this.degree;
 }

 public double param(int i) {
  return (this.mf.get())[i];
 }

 public double center() {
  return mf.center();
 }

 public double basis() {
  return mf.basis();
 }

 public void setDegreeDeriv(double deriv) {
  this.derivative += deriv;
 }

 public void setParamDeriv(int index, double deriv) {
  this.mf.addDeriv(index,deriv);
 }

 public void setCenterDeriv(double deriv) {
  try { 
   double paramdev[] = mf.deriv_center();
   for(int i=0; i<paramdev.length; i++) mf.addDeriv(i,paramdev[i]*deriv);
  }
  catch(XflException ex) {}
 }

 public void setBasisDeriv(double deriv) {
  try { 
   double paramdev[] = mf.deriv_basis();
   for(int i=0; i<paramdev.length; i++) mf.addDeriv(i,paramdev[i]*deriv);
  }
  catch(XflException ex) {}
 }

 public double[] getParam() { return this.mf.get(); }
 public double getDegree() { return this.degree; }
 public double getDegreeDeriv() { return this.derivative; }
 public ParamMemFunc getMF() { return this.mf; }
 public void setDegree(double degree) { this.degree = degree; }
 public void setDerivative(double deriv) { this.derivative = deriv; }
}
