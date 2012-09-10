/*
 * @(#)AggregateMemFunc.java        1.0 2000/05/09
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
//              FUNCION DE PERTENENCIA AGREGADA			//
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

package xfuzzy.lang;

public class AggregateMemFunc implements MemFunc {
 public ImpliedMemFunc conc[];
 public double input[];
 private Universe u;
 private Rulebase mod;

 AggregateMemFunc(Universe universe, Rulebase mod) {
  this.u = universe;
  this.mod = mod;
  this.conc = new ImpliedMemFunc[0];
 }

 void add(ImpliedMemFunc imf) {
  ImpliedMemFunc amf[] = new ImpliedMemFunc[this.conc.length+1];
  System.arraycopy(this.conc,0,amf,0,this.conc.length);
  amf[this.conc.length] = imf;
  this.conc = amf;
 }

 public double compute(double x) {
  double degree = this.conc[0].compute(x);
  for(int i=1; i<this.conc.length; i++)
   degree = this.mod.operation.also.compute(degree, this.conc[i].compute(x));
  return degree;
 }

 public double defuzzify() { return this.mod.operation.defuz.compute(this); }
 public double min() { return this.u.min(); }
 public double max() { return this.u.max(); }
 public double step() { return this.u.step(); }

 public boolean isDiscrete() {
  for(int i=0; i<conc.length; i++)
   if( !(conc[i].getMF() instanceof pkg.xfl_mf_singleton) ) return false;
  return true;
 } 

 public double[][] getDiscreteValues() {
  double[][] value = new double[conc.length][2];
  for(int i=0; i<conc.length; i++) {
   value[i][0] = conc[i].getParam()[0];
   value[i][1] = conc[i].getDegree();
  }
  return value;
 }

 public double getActivationDegree(double label) {
  double degree = 0;
  for(int i=0; i<conc.length; i++) 
   if(conc[i].center() == label)
    if(degree<conc[i].getDegree()) degree = conc[i].getDegree();
  return degree;
 }
}
