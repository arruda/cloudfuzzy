/*
 * @(#)Parameter.java        1.0 2000/05/09
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
//		PARAMETRO DE UNA FUNCION DE PERTENENCIA		//
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

package xfuzzy.lang;

public class Parameter implements Cloneable {
 public double value;
 public double[] oderiv;
 public double tderiv;

 private String name;
 private boolean adjustable = true;
 private double[] copy = new double[0];
 private double deriv;
 private double prevderiv;
 private double desp;
 private double prevdesp;
 private double misc;

 public Parameter(String name) {
  this.name = name;
 }

 public void set(double value) { this.value = value; }
 public void setDeriv(double deriv) { this.deriv = deriv; }
 public void setPrevDeriv(double prevderiv) { this.prevderiv = prevderiv; }
 public void setDesp(double desp) { this.desp = desp; }
 public void setPrevDesp(double prevdesp) { this.prevdesp = prevdesp; }
 public void setMisc(double misc) {this.misc = misc; }
 public void setAdjustable(boolean enable) { this.adjustable = enable; }
 
 public String getName() { return this.name; }
 public double get() { return this.value; }
 public double getDeriv() { return this.deriv; }
 public double getDesp() { return this.desp; }
 public double getPrevDeriv() { return this.prevderiv; }
 public double getPrevDesp() { return this.prevdesp; }
 public double getMisc() { return this.misc; }
 public boolean isAdjustable() { return this.adjustable; }

 public void addDeriv(double deriv) { this.deriv += deriv; }
 public void forward() { prevderiv=deriv; deriv=0; }
 public void backup(int i) { copy[i] = value; }
 public double restore(int i) { return copy[i]; }

 public void backup() {
  double aux[] = new double[copy.length+1];
  System.arraycopy(copy,0,aux,0,copy.length);
  aux[copy.length] = value;
  copy = aux;
 }

 public Object clone() { 
  try { return super.clone(); }
  catch (CloneNotSupportedException e) {return null; }
 }
}
