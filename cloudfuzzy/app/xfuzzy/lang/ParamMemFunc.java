/*
 * @(#)ParamMemFunc.java        1.0 2000/05/09
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
//              FUNCION DE PERTENENCIA PARAMETRICA		//
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

package xfuzzy.lang;

public abstract class ParamMemFunc implements MemFunc, Cloneable {
 public String label;
 public String name;
 public String pkg;
 public Parameter[] parameter;
 public Universe u;
 private boolean adjustable = true;
 private int link = 0;

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //			FUNCIONES GENERALES			//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 public boolean equals(String label) {
  return this.label.equals(label);
 }

 public String toString() {
  return this.label;
 }

 public Object clone(Universe cu) {
  try {
   ParamMemFunc clone = (ParamMemFunc) getClass().newInstance();
   clone.set(this.label,cu);
   clone.set(get());
   return clone;
  }
  catch(Exception ex) { return null; }
/*
  try {
   int auxlink = this.link;
   this.link = 0;
   ParamMemFunc clone = (ParamMemFunc) super.clone();
   this.link = auxlink;
   clone.label = this.label.toString();
   clone.name = this.name.toString();
   clone.pkg = this.pkg.toString();
   clone.u = cu;
   clone.parameter = new Parameter[this.parameter.length];
   for(int i=0; i<this.parameter.length; i++)
    clone.parameter[i] = (Parameter) this.parameter[i].clone();
   return clone;
  }
  catch (CloneNotSupportedException e) { return null; }
*/
  }

 public void set(String name, Universe u){
  this.label = name;
  this.u = u;
 }

 public Universe universe() {
  return this.u;
 }

 public void setAdjustable() {
  this.adjustable = false;
  for(int i=0; i<parameter.length; i++)
   if(parameter[i].isAdjustable()) this.adjustable = true;
 }

 public boolean isAdjustable() { return this.adjustable; }

 public void link() { this.link++; }
 public void unlink() { this.link--; }
 public boolean isLinked() { return (this.link>0); }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //		   FUNCIONES QUE GENERAN CODIGO			//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 public String toXfl() {
  String code = pkg+"."+name+"(";
  for(int i=0; i<parameter.length; i++)
   if(i==0) code += parameter[i].value; else code += ","+parameter[i].value;
  code += ")";
  return code;
 }

 public abstract String toJava();
 public abstract String toC() throws XflException;
 public abstract String toCpp(String name) throws XflException;
 public abstract String toHpp(String name) throws XflException;

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //		FUNCIONES DE MANEJO DE LOS PARAMETROS		//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 public abstract boolean test();

 public void set(double p[]) throws XflException {
  if(p.length != parameter.length) throw new XflException(34);
  for(int i=0;i<parameter.length;i++) parameter[i].value = p[i];
  if(!test()) throw new XflException(35);
 }

 public void set(double p) {
  parameter[0].value = p;
 }

 public double[] get() {
  double[] p = new double[parameter.length];
  for(int i=0; i<parameter.length; i++) p[i] = parameter[i].value;
  return p;
 }

 public double[] getDesp() {
  double[] d = new double[parameter.length];
  for(int i=0; i<parameter.length; i++) d[i] = parameter[i].getDesp();
  return d;
 }

 public boolean[] getAdjustable() {
  boolean[] d = new boolean[parameter.length];
  for(int i=0; i<parameter.length; i++) d[i] = parameter[i].isAdjustable();
  return d;
 }

 public void addDeriv(int index, double deriv) {
  this.parameter[index].addDeriv(deriv);
 }

 public void update() {
  if(!adjustable) return;
  double[] desp = getDesp();
  double[] prevvalue = get();
  for(int i=0; i<parameter.length; i++) parameter[i].value += desp[i];
  if(!test()) {
   for(int i=0; i<parameter.length; i++) parameter[i].value -= desp[i];
   for(int j=0; j<10; j++) {
    for(int i=0; i<desp.length; i++) desp[i] /= 2;
    for(int i=0; i<parameter.length; i++) parameter[i].value += desp[i];
    if(!test())
     for(int i=0; i<parameter.length; i++) parameter[i].value -= desp[i];
   }
  }
  for(int i=0; i<parameter.length; i++) {
   parameter[i].setDesp(0.0);
   parameter[i].setPrevDesp(parameter[i].value - prevvalue[i]);
  }
 }

 protected double[] sortedUpdate(double[] value,double[] desp,boolean[] enable){
  int[] cont = new int[value.length];
  double[] time = new double[value.length-1];
  double[] pos = new double[value.length];
  double step = this.u.step();

  double oldval = value[0]-3*step;
  for(int i=0; i<value.length; i++) {
   pos[i] = (value[i] < oldval+step? oldval+step : value[i]);
   cont[i] = 1;
   oldval = pos[i];
  }

  double timeup = 1;
  int num = value.length;
  while(timeup>0) {
   double mintime= timeup;

   for(int i=0;i<num-1;i++) {
    double dist = (pos[i+1]-pos[i] -step*(cont[i]+cont[i+1])/2 );
    if(desp[i]==desp[i+1]) time[i]=2;
    else if(dist<0 && desp[i]>desp[i+1]) time[i]=0; /* por redondeos */
    else time[i]= dist/(desp[i]-desp[i+1]);
    if(time[i]>=0 && time[i]<mintime) mintime = time[i];
   }

   for(int i=0;i<num;i++) pos[i]+=desp[i]*mintime;
 
   for(int i=0;i<num-1;i++)
    if(time[i]==mintime) {
     if(enable[i] && enable[i+1]) {
      desp[i]=(cont[i]*desp[i]+cont[i+1]*desp[i+1])/(cont[i]+cont[i+1]);
      pos[i]=(cont[i]*pos[i]+cont[i+1]*pos[i+1])/(cont[i]+cont[i+1]);
      cont[i] += cont[i+1];
      num--;
      for(int j=i+1;j<num;j++) {
       desp[j]= desp[j+1];
       pos[j]= pos[j+1];
       cont[j]= cont[j+1];
       enable[j]= enable[j+1];
      }
     } else if(!enable[i] && enable[i+1]) {
      desp[i+1]= 0;
      pos[i+1]= pos[i]+step;
     } else if(enable[i] && !enable[i+1]) {
      desp[i]= 0;
      pos[i]= pos[i+1]-step;
     }
    }

   timeup -= mintime;
  }
 
  double [] output = new double[value.length];
  int i=0, j=1-cont[i];
  for(int k=0; k<value.length; k++) {
   output[k] = pos[i] + j*step/2;
   j = j+2;
   cont[i]--;
   if(cont[i]==0 && k<value.length-1) { i++; j=1-cont[i]; }
  }
  return output;
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //		FUNCIONES DE CALCULO DE LA FUNCION		//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 public abstract double compute(double x);

 public double greatereq(double x) {
  double min = this.u.min();
  double step = this.u.step();
  double degree = 0;
  double mu;

  for(double y = min; y<=x ; y+=step) {
   mu = compute(y);
   if( mu>degree ) degree = mu;
  }
  return degree;
 }

 public double smallereq(double x) {
  double max = this.u.max();
  double step = this.u.step();
  double degree = 0;
  double mu;

  for(double y = max; y>=x ; y-=step) {
   mu = compute(y);
   if( mu>degree ) degree = mu;
  }
  return degree;
 }

 public double center() { return 0; }

 public double basis() { return 0; }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //		FUNCIONES DE CALCULO DE LA DERIVADA		//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 public double[] deriv_eq(double x) throws XflException {
  throw new XflException(19);
 }

 public double[] deriv_greq(double x) throws XflException {
  throw new XflException(19);
 }

 public double[] deriv_smeq(double x) throws XflException {
  throw new XflException(19);
 }

 public double[] deriv_center() throws XflException {
  throw new XflException(19);
 }

 public double[] deriv_basis() throws XflException {
  throw new XflException(19);
 }
}
