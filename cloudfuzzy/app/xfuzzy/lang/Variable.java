/*
 * @(#)Variable.java        1.0 2000/05/09
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
//			  VARIABLE DIFUSA			//
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

package xfuzzy.lang;

public class Variable {
 public final static int INPUT = 0;
 public final static int OUTPUT = 1;
 public final static int INNER = 2;

 private String name;
 private Type type;
 private MemFunc value;
 private int access;
 private int link;
 private Rulebase mod;

 public Variable(String name, int access) {
  this.name = name;
  this.type = null;
  this.mod = null;
  this.value = null;
  this.access = access;
  this.link = 0;
 }

 public Variable(String name, Type type, int access) {
  this.name = name;
  this.type = type; this.type.link();
  this.mod = null;
  this.value = null;
  this.access = access;
  this.link = 0;
 }

 public Variable(String name, Type type, Rulebase mod) {
  this.name = name;
  this.type = type; this.type.link();
  this.mod = mod;
  this.value = null;
  this.access = OUTPUT;
  this.link = 0;
 }

 public String getName() { return new String(this.name); }
 public Type getType() { return this.type; }
 public MemFunc getValue() { return this.value; }
 public String toString() { return new String(this.name); }
 public boolean equals(String name) { return this.name.equals(name); }
 public void link() { this.link++; }
 public void unlink() { this.link--; }
 public boolean isLinked() { return (this.link>0); }
 public void setName(String name) { this.name = name; }

 public String toXfl() {
  if(access != INNER && this.type != null) return this.type+" "+this.name;
  return "";
 }

 public ParamMemFunc search(String mfname) {
  if(type == null) return null;
  return this.type.search(mfname);
 }

 public boolean isInput() { return (this.access == INPUT); }
 public boolean isOutput() { return (this.access == OUTPUT); }
 public boolean isInner() { return (this.access == INNER); }

 public double point(double x) {
  return this.type.getUniverse().point(x);
 }

 public double range() {
  return this.type.getUniverse().range();
 }

 public double getRate(double x) {
  return this.type.getUniverse().getRate(x);
 }

 public void set(MemFunc mf) {
  this.value = mf;
 }

 public void set(double x) {
  ParamMemFunc singleton = new pkg.xfl_mf_singleton();
  singleton.set(x);
  this.value = singleton;
 }

 public void addConclusion(ImpliedMemFunc imf) {
  if(value == null) value = new AggregateMemFunc(type.getUniverse(), mod);
  AggregateMemFunc amf = (AggregateMemFunc) value;
  amf.add(imf);
 }

 public double getCrispValue() throws XflException {
  if(!(this.value instanceof pkg.xfl_mf_singleton)) throw new XflException(18);
  ParamMemFunc singleton = (ParamMemFunc) this.value;
  double[] param = singleton.get();
  return param[0];
 }

 public void reset() {
  this.value = null;
 }

 public void derivative(double derror) throws XflException {
  if(this.access != OUTPUT) return;
  AggregateMemFunc amf = (AggregateMemFunc) this.value;
  mod.operation.defuz.derivative(amf,derror);
 }
}

