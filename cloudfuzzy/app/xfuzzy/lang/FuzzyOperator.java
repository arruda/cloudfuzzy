/*
 * @(#)FuzzyOperator.java        1.0 2000/05/09
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
//		OPERADORES DIFUSOS (EN GENERAL)			//
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

package xfuzzy.lang;

public abstract class FuzzyOperator implements Cloneable {
 public final static int AND = 1;
 public final static int OR = 2;
 public final static int NOT = 3;
 public final static int ALSO = 4;
 public final static int IMP = 5;
 public final static int MOREORLESS = 6;
 public final static int VERY = 7;
 public final static int SLIGHTLY = 8;
 public final static int DEFUZMETHOD = 9;

 public String name;
 public String pkg;
 public Parameter parameter[];
 private boolean isdefault = false;

 public void set(double p[]) {
  for(int i=0; i<parameter.length; i++) parameter[i].value = p[i];
 }

 public void set(double p) {
  parameter[0].value = p;
 }

 public double[] get() {
  double[] p = new double[parameter.length];
  for(int i=0; i<parameter.length; i++) p[i] = parameter[i].value;
  return p;
 }

 public void setDefault(boolean isdefault) {
  this.isdefault = isdefault;
 }

 public boolean isDefault() {
  return this.isdefault;
 }

 public String toXfl() {
  if(isdefault) return "";
  String code = pkg+"."+name+"(";
  for(int i=0; i<parameter.length; i++) 
   if(i==0) code += parameter[i].value; else code += ","+parameter[i].value;
  code += ")";
  return code;
 }

 public Object clone() {
  try {
   FuzzyOperator clone = (FuzzyOperator) super.clone();
   clone.name = this.name.toString();
   clone.pkg = this.pkg.toString();
   clone.parameter = new Parameter[this.parameter.length];
   for(int i=0; i<this.parameter.length; i++)
    clone.parameter[i] = (Parameter) this.parameter[i].clone();
   return clone;
  }
  catch (CloneNotSupportedException e) { return null; }
 }

 public abstract boolean test();
 public abstract String toJava(String name);
 public abstract String toC(String name) throws XflException;
 public abstract String toCpp(String name) throws XflException;
}
