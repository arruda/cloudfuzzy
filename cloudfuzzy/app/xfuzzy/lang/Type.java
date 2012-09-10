/*
 * @(#)Type.java        1.0 2000/05/09
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
//		    TIPO DE VARIABLE LINGUISTICA		//
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

package xfuzzy.lang;

import java.util.Vector;

public class Type implements Cloneable {
 private String name;
 private Universe u;
 private Type parent;
 private Type[] child;
 private ParamMemFunc mf[];
 private boolean adjustable = true;
 private int link;
 private boolean editing = false;

 public Type(String name) {
  this.name = name;
  this.u = new Universe();
  this.parent = null;
  this.child = new Type[0];
  this.mf = new ParamMemFunc[0];
 }

 public Type(String name, Universe u) {
  this.name = name;
  this.u = u;
  this.parent = null;
  this.child = new Type[0];
  this.mf = new ParamMemFunc[0];
 }

 public Type(String name, Type parent) {
  this.name = name;
  this.u = parent.getUniverse();
  this.parent = parent;
  this.parent.addChild(this);
  this.child = new Type[0];
  this.mf = new ParamMemFunc[0];
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //		FUNCIONES PARA ACCEDER A LOS DATOS		//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 public boolean isLinked() { return (this.link>0); }
 public boolean isEditing() { return this.editing; }
 public boolean isAdjustable() { return this.adjustable; }
 public boolean hasParent() { return this.parent != null; }
 public boolean hasChild() { return this.child.length>0; }

 public String getName() { return this.name; }
 public Universe getUniverse() { return this.u; }
 public Type getParent() { return this.parent; }
 public Type[] getChild() { return this.child; }
 public ParamMemFunc[] getMembershipFunctions() { return this.mf; }
 public ParamMemFunc[] getAllMembershipFunctions() {
  if(parent == null) return this.mf;
  Vector vmf = new Vector();
  for(int i=0; i<this.mf.length; i++) vmf.addElement(this.mf[i]);
  ParamMemFunc[] pmf = parent.getAllMembershipFunctions();
  for(int i=0; i<pmf.length; i++) {
   boolean overwrite = false;
   for(int j=0; j<this.mf.length; j++)
    if(this.mf[j].label.equals(pmf[i].label)) overwrite = true;
   if(!overwrite) vmf.addElement(pmf[i]);
  }
  ParamMemFunc[] allmf = new ParamMemFunc[vmf.size()];
  for(int i=0;i<allmf.length;i++) allmf[i] = (ParamMemFunc) vmf.elementAt(i);
  return allmf;
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //		FUNCIONES PARA MODIFICAR LOS DATOS		//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 public void setName(String name) { this.name = name; }
 public void setUniverse(Universe u) { 
  this.u = u;
  for(int i=0; i<this.mf.length; i++) this.mf[i].u = u;
  for(int i=0; i<this.child.length; i++) this.child[i].setUniverse(u);
 }
 public void setMembershipFunctions(ParamMemFunc[] mf) { this.mf = mf; }
 public void setAdjustable() {
  for(int i=0; i<mf.length; i++) mf[i].setAdjustable();
  adjustable = false;
  for(int i=0; i<mf.length; i++) if(mf[i].isAdjustable()) adjustable = true;
 }

 public void createMemFuncs(int sel, int mfs) {
  if(sel<0 || sel>7) return;

  double min = u.min();
  double max = u.max();
  double[] param;
  double r;
  ParamMemFunc[] pmf = new ParamMemFunc[mfs];
  ParamMemFunc mf0, mfl;
  String prefix = "mf";
  boolean exists = true;
  while(exists) {
   exists = false;
   for(int i=0;i<mfs;i++) if(search(prefix+i) != null) exists = true;
   if(exists) prefix += "_";
  }

  switch(sel) {
   case 0: /* TRIANGULOS EQUIESPACIADOS */
    param = new double[3];
    r = (max-min)/(mfs-1);
    for(int i=0; i<mfs; i++) {
     param[0] = min + (i-1)*r;
     param[1] = min + i*r;
     param[2] = min + (i+1)*r;
     ParamMemFunc nmf = new pkg.xfl_mf_triangle();
     nmf.label = prefix+i; nmf.u = u;
     try { nmf.set(param); pmf[i] = nmf; } catch(XflException ex) {}
    }
    break;
   case 1: /* TRIANGULOS CON "HOMBRERAS" */
    param = new double[4];
    r = (max-min)/(mfs+1);
    mf0 = new pkg.xfl_mf_trapezoid();
    param[0] = min-r; param[1] = min; param[2] = min+r; param[3] = min+2*r;
    mf0.label = prefix+"0"; mf0.u = u;
    try { mf0.set(param); pmf[0] = mf0; } catch(XflException ex) {}
    param = new double[3];
    for(int i=1; i<mfs-1; i++) {
     param[0] = min + i*r;
     param[1] = min + (i+1)*r;
     param[2] = min + (i+2)*r;
     ParamMemFunc nmf = new pkg.xfl_mf_triangle();
     nmf.label = prefix+i; nmf.u = u;
     try { nmf.set(param); pmf[i] = nmf; } catch(XflException ex) {}
    }
    if(mfs == 1) break;
    param = new double[4];
    mfl = new pkg.xfl_mf_trapezoid();
    param[0] = max-2*r; param[1] = max-r; param[2] = max; param[3] = max+r;
    mfl.label = prefix+(mfs-1); mfl.u = u;
    try { mfl.set(param); pmf[mfs-1] = mfl; } catch(XflException ex) {}
    break;
   case 2: /* CAMPANAS EQUIESPACIADAS */
    param = new double[2];
    r = (max-min)/(mfs-1);
    for(int i=0; i<mfs; i++) {
     param[0] = min + i*r;
     param[1] = 0.6*r;
     ParamMemFunc nmf = new pkg.xfl_mf_bell();
     nmf.label = prefix+i; nmf.u = u;
     try { nmf.set(param); pmf[i] = nmf; } catch(XflException ex) {}
    }
    break;
   case 3: /* CAMPANAS CON "HOMBRERAS" */
    param = new double[2];
    r = (max-min)/(mfs+1);
    mf0 = new pkg.xfl_mf_sigma();
    param[0] = min + 1.5*r; param[1] = -0.17*r;
    mf0.label = prefix+"0"; mf0.u = u;
    try { mf0.set(param); pmf[0] = mf0; } catch(XflException ex) {}
    for(int i=1; i<mfs-1; i++) {
     param[0] = min + (i+1)*r;
     param[1] = 0.6*r;
     ParamMemFunc nmf = new pkg.xfl_mf_bell();
     nmf.label = prefix+i; nmf.u = u;
     try { nmf.set(param); pmf[i] = nmf; } catch(XflException ex) {}
    }
    if(mfs == 1) break;
    mfl = new pkg.xfl_mf_sigma();
    param[0] = max - 1.5*r; param[1] = 0.17*r;
    mfl.label = prefix+(mfs-1); mfl.u = u;
    try { mfl.set(param); pmf[mfs-1] = mfl; } catch(XflException ex) {}
    break;
   case 4: /* TRAPECIOS EQUIESPACIADOS */
    param = new double[4];
    r = (max-min)/(3*mfs-1);
    for(int i=0; i<mfs; i++) {
     param[0] = min + (3*i-1)*r;
     param[1] = min + 3*i*r;
     param[2] = min + (3*i+2)*r;
     param[3] = min + (3*i+3)*r;
     ParamMemFunc nmf = new pkg.xfl_mf_trapezoid();
     nmf.label = prefix+i; nmf.u = u;
     try { nmf.set(param); pmf[i] = nmf; } catch(XflException ex) {}
    }
    break;
   case 5: /* SINGULARIDADES EQUIESPACIADAS */
    for(int i=0; i<mfs; i++) {
     r = (max-min)/(mfs-1);
     double pos = min + i*r;
     ParamMemFunc nmf = new pkg.xfl_mf_singleton();
     nmf.label = prefix+i; nmf.u = u;
     nmf.set(pos); pmf[i] = nmf;
    }
    break;
   case 6: /* CAMPANAS CENTRADAS */
    param = new double[2];
    param[0] = (min + max)/2;
    param[1] = (max - min)/8;
    for(int i=0; i<mfs; i++) {
     ParamMemFunc nmf = new pkg.xfl_mf_bell();
     nmf.label = prefix+i; nmf.u = u;
     try { nmf.set(param); pmf[i] = nmf; } catch(XflException ex) {}
    }
    break;
   case 7: /* SINGULARIDADES CENTRADAS */
    double middle = (min + max)/2 ;
    for(int i=0; i<mfs; i++) {
     ParamMemFunc nmf = new pkg.xfl_mf_singleton();
     nmf.label = prefix+i; nmf.u = u;
     nmf.set(middle); pmf[i] = nmf;
    }
    break;
  }

  try { for(int i=0; i<mfs; i++) add(pmf[i]); }
  catch(XflException e) {}
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //			FUNCIONES GENERALES			//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 public boolean equals(String name) { return this.name.equals(name); }
 public void setEditing(boolean editing) { this.editing = editing; }
 public void link() { this.link++; }
 public void unlink() { this.link--; }

 public void addChild(Type newchild) {
  Type ach[] = new Type[this.child.length+1];
  System.arraycopy(this.child,0,ach,0,this.child.length);
  ach[this.child.length] = newchild;
  this.child = ach;
 }

 public boolean testUniverse(double min, double max, int card) {
  double oldmin = this.u.min();
  double oldmax = this.u.max();
  int oldcard = this.u.card();

  try { this.u.set(min,max,card); } catch(XflException ex) { return false; }

  boolean test = true;
  for(int i=0; i<this.mf.length; i++) if(!this.mf[i].test()) test = false;
  for(int i=0; i<this.child.length; i++)
   if(!this.child[i].testUniverse(min,max,card)) test = false;

  try { this.u.set(oldmin,oldmax,oldcard); } catch(XflException ex) {}
  return test;
 }

 public Object clone() {
  //***** ESTO ESTA MAL PARA TIPOS CON PADRES O CON HIJOS ****//
  try {
   Type clone = (Type) super.clone();
   Universe cu = (Universe) this.u.clone();
   ParamMemFunc cmf[] = new ParamMemFunc[this.mf.length];
   for(int i=0; i<cmf.length; i++) cmf[i]=(ParamMemFunc) this.mf[i].clone(cu);
   clone.setName(this.name.toString());
   clone.setUniverse(cu);
   clone.setMembershipFunctions(cmf);
   return clone;
  }
  catch (CloneNotSupportedException e) { return null; }
 }

 public void update() {
  if(!adjustable) return;
  for(int i=0; i<mf.length; i++) mf[i].update();
 }

 public int prune() {
  int pruned = 0;
  for(int i=0; i<mf.length; i++) if(!mf[i].isLinked()) pruned++;
  if(pruned == 0) return 0;
  ParamMemFunc[] aux = new ParamMemFunc[mf.length - pruned];
  for(int i=0,j=0; i<mf.length; i++) if(mf[i].isLinked()) { aux[j]=mf[i]; j++;}
  mf = aux;
  return pruned;
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //	FUNCIONES DE EDICION DE UNA FUNCION DE PERTENENCIA	//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 public void remove(ParamMemFunc pmf) {
  if(pmf.isLinked()) return;
  int i;
  for(i=0; i<this.mf.length ; i++) if(this.mf[i]==pmf) break;
  if(i == this.mf.length) return;
  ParamMemFunc[] amf = new ParamMemFunc[this.mf.length-1];
  System.arraycopy(this.mf,0,amf,0,i);
  System.arraycopy(this.mf,i+1,amf,i,this.mf.length-i-1);
  this.mf = amf;
 }

 public void add(ParamMemFunc mf) throws XflException {
  for(int i=0; i<this.mf.length; i++)
   if(this.mf[i].name.equals(mf.label)) throw new XflException(8);

  ParamMemFunc amf[] = new ParamMemFunc[this.mf.length+1];
  System.arraycopy(this.mf,0,amf,0,this.mf.length);
  amf[this.mf.length] = mf;
  this.mf = amf;
 }

 ParamMemFunc search(String label) {
  for(int i=0; i<this.mf.length; i++) {
   if(this.mf[i].label.equals(label)) return mf[i];
  }
  if(parent!=null) return parent.search(label);
  return null;
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //		 FUNCIONES PARA GENERAR CODIGO			//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 public String toString() { return this.name; }

 public String toXfl() {
  String eol = System.getProperty("line.separator", "\n");
  String code = "type "+this.name+" ";
  if(this.parent == null) code += this.u.toXfl();
  else code += "parent "+this.parent.getName();
  code += " {"+eol;
  for(int i=0; i<this.mf.length; i++)
   code += "  "+this.mf[i].label+" "+this.mf[i].toXfl()+";"+eol;
  code += " }"+eol+eol;
  return code;
 }

 public String toJava() {
  String eol = System.getProperty("line.separator", "\n");
  String code = "";
  code += " //+++++++++++++++++++++++++++++++++++++++++++++++++++++//"+eol;
  code += " //  Type TP_"+name+"  //"+eol;
  code += " //+++++++++++++++++++++++++++++++++++++++++++++++++++++//"+eol+eol;
  code += " private class TP_"+name;
  if(parent != null) code += " extends TP_"+parent;
  code += " {"+eol;
  if(parent == null) code += this.u.toJava();
  for(int i=0; i<this.mf.length; i++) {
   double param[] = mf[i].get();
   code +="  double _p_"+mf[i].label+"[] = { ";
   for(int j=0; j<param.length; j++) code += (j==0? "":",")+param[j];
   code += " };"+eol;
  }
  for(int i=0; i<this.mf.length; i++) {
   code += "  MF_"+mf[i].pkg+"_"+mf[i].name+" "+mf[i].label;
   code += " = new MF_"+mf[i].pkg+"_"+mf[i].name;
   code += "(min,max,step,_p_"+mf[i].label+");"+eol;
  }
  code += " }"+eol+eol;
  return code;
 }

 public String toStruct() {
  String eol = System.getProperty("line.separator", "\n");
  String code = eol+"typedef struct {"+eol;
  for(int i=0; i<this.mf.length; i++)
   code += " MembershipFunction "+mf[i].label+";"+eol;
  code += "} TP_"+name+";"+eol;
  return code;
 }

 public String toC() {
  String eol = System.getProperty("line.separator", "\n");
  String code = "";
  code += eol+"/***************************************/"+eol;
  code += "/*  Type TP_"+name+" */"+eol;
  code += "/***************************************/"+eol;
  code += eol+"static TP_"+name+" createTP_"+name+"() {"+eol;
  code += " TP_"+name+" tp;"+eol;
  code += this.u.toC();
  ParamMemFunc[] allmf = getAllMembershipFunctions();   
  for(int i=0; i<allmf.length; i++) {
   double param[] = allmf[i].get();
   code +=" double _p_"+allmf[i].label+"["+param.length+"] = { ";
   for(int j=0; j<param.length; j++) code += (j==0? "":",")+param[j];
   code += " };"+eol;
  }
  for(int i=0; i<allmf.length; i++) {
   double param[] = allmf[i].get();
   code +=" tp."+allmf[i].label+" = createMF_"+allmf[i].pkg+"_"+allmf[i].name;
   code += "(min,max,step,_p_"+allmf[i].label+","+param.length+");"+eol;
  }
  code += " return tp;"+eol;
  code += "}"+eol;
  return code;
 }

 public String toCpp(String specname) {
  String eol = System.getProperty("line.separator", "\n");
  String typename = "TP_"+specname+"_"+name;
  String code = "";
  code += "//+++++++++++++++++++++++++++++++++++++//"+eol;
  code += "//  Type "+typename+" //"+eol;
  code += "//+++++++++++++++++++++++++++++++++++++//"+eol+eol;
  code += typename+"::"+typename+"() {"+eol;
  code += " min = "+this.u.min()+";"+eol;
  code += " max = "+this.u.max()+";"+eol;
  code += " step = "+this.u.step()+";"+eol;
  ParamMemFunc[] allmf = getAllMembershipFunctions();   
  for(int i=0; i<allmf.length; i++) {
   double param[] = allmf[i].get();
   code +=" double _p_"+allmf[i].label+"["+param.length+"] = { ";
   for(int j=0; j<param.length; j++) code += (j==0? "":",")+param[j];
   code += " };"+eol;
  }
  for(int i=0; i<allmf.length; i++) {
   String mfname = "MF_"+specname+"_"+allmf[i].pkg+"_"+allmf[i].name;
   code += " "+allmf[i].label+" = "+mfname;
   code +="(min,max,step,_p_"+allmf[i].label+","+allmf[i].get().length+");"+eol;
  }
  code += "}"+eol+eol;
  return code;
 }

 public String toHpp(String specname) {
  String eol = System.getProperty("line.separator", "\n");
  String typename = "TP_"+specname+"_"+name;
  String code = "";
  code += "//+++++++++++++++++++++++++++++++++++++//"+eol;
  code += "//  Type "+typename+" //"+eol;
  code += "//+++++++++++++++++++++++++++++++++++++//"+eol+eol;
  code += "class "+typename+" {"+eol;
  code += "private:"+eol;
  code += " double min;"+eol;
  code += " double max;"+eol;
  code += " double step;"+eol;
  code += "public:"+eol;
  ParamMemFunc[] allmf = getAllMembershipFunctions();
  for(int i=0; i<allmf.length; i++) {
   String mfname = "MF_"+specname+"_"+allmf[i].pkg+"_"+allmf[i].name;
   code += " "+mfname+" "+allmf[i].label+";"+eol;
  }
  code += " "+typename+"();"+eol;
  code += "};"+eol+eol;
  return code;
 }
}
