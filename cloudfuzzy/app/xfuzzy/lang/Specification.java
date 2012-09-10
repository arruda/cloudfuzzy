/*
 * @(#)Specification.java        1.0 2000/05/09
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
//			ESPECIFICACION				//
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

package xfuzzy.lang;

import java.io.*;

public class Specification {
 private String name;
 private File file;
 private Operatorset operation[];
 private Type type[];
 private Rulebase rulebase[];
 private SystemModule system;
 private Parameter[] adjustable;
 private boolean modified;
 private boolean fileediting;
 private Object editor;

 public Specification(File file) {
  String filename = file.getName();
  int index = filename.toLowerCase().lastIndexOf(".xfl");
  if(index <= 0) this.name = new String(filename);
  else this.name = filename.substring(0,index);
  this.file = file;
  this.operation = new Operatorset[0];
  this.type = new Type[0];
  this.rulebase = new Rulebase[0];
  this.system = new SystemModule();
  this.modified = false;
  this.editor = null;
  this.fileediting = false;
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //		FUNCIONES PARA ACCEDER A LOS DATOS		//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 public String getName() { return new String(this.name); }
 public File getFile() { return this.file; }
 public Operatorset [] getOperatorsets() { return this.operation; }
 public Type [] getTypes() { return this.type; }
 public Rulebase [] getRulebases() { return this.rulebase; }
 public SystemModule getSystemModule() { return this.system; }
 public Object getEditor() { return this.editor; }
 public boolean isModified() { return this.modified; }
 public boolean isEditing() { return (this.editor!=null); }
 public boolean isFileEditing() { return this.fileediting; }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //		FUNCIONES PARA MODIFICAR LOS DATOS		//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 public void setFile(File file) {
  String filename = file.getName();
  int index = filename.lastIndexOf(".xfl");
  if(index == -1) this.name = new String(filename);
  else this.name = filename.substring(0,index);
  this.file = file;
 }

 public void setOperatorsets(Operatorset op[]) { this.operation = op; }
 public void setTypes(Type type[]) { this.type = type; }
 public void setRulebases(Rulebase rulebase[]) { this.rulebase = rulebase; }
 public void setSystemModule(SystemModule system) { this.system = system; }
 public void setModified(boolean modif) { this.modified = modif; }
 public void setEditor(Object editor) { this.editor = editor; }
 public void setFileEditing(boolean editing) { this.fileediting = editing; }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //		   FUNCIONES PARA ANNADIR MODULOS		//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 public void addOperatorset(Operatorset op) { 
  Operatorset aop[] = new Operatorset[this.operation.length+1];
  System.arraycopy(this.operation,0,aop,0,this.operation.length);
  aop[this.operation.length] = op;
  this.operation = aop;
 }

 public void addType(Type tp) {
  Type atp[] = new Type[this.type.length+1];
  System.arraycopy(this.type,0,atp,0,this.type.length);
  atp[this.type.length] = tp;
  this.type = atp;
 }

 public void addRulebase(Rulebase rb) {
  Rulebase arb[] = new Rulebase[this.rulebase.length+1];
  System.arraycopy(this.rulebase,0,arb,0,this.rulebase.length);
  arb[this.rulebase.length] = rb;
  this.rulebase = arb;
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //		   FUNCIONES PARA BORRAR MODULOS		//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 public void removeOperatorset(Operatorset op) {
  if(op.isLinked()) return;
  int i;
  for(i=0; i<this.operation.length ; i++) if(this.operation[i]==op) break;
  if(i == this.operation.length) return;
  Operatorset aop[] = new Operatorset[this.operation.length-1];
  System.arraycopy(this.operation,0,aop,0,i);
  System.arraycopy(this.operation,i+1,aop,i,this.operation.length-i-1);
  this.operation = aop;
 }

 public void removeType(Type tp) {
  if(tp.isLinked()) return;
  int i;
  for(i=0; i<this.type.length ; i++) if(this.type[i]==tp) break;
  if(i == this.type.length) return;
  Type atp[] = new Type[this.type.length-1];
  System.arraycopy(this.type,0,atp,0,i);
  System.arraycopy(this.type,i+1,atp,i,this.type.length-i-1);
  this.type = atp;
 }

 public void removeRulebase(Rulebase rb) {
  if(rb.isLinked()) return;
  int i;
  for(i=0; i<this.rulebase.length ; i++) if(this.rulebase[i]==rb) break;
  if(i == this.rulebase.length) return;
  Rulebase arb[] = new Rulebase[this.rulebase.length-1];
  System.arraycopy(this.rulebase,0,arb,0,i);
  System.arraycopy(this.rulebase,i+1,arb,i,this.rulebase.length-i-1);
  this.rulebase = arb;
  rb.dispose();
 }

 public void exchangeRulebase(Rulebase oldrb, Rulebase newrb) {
  for(int i=0; i<this.rulebase.length ; i++)
   if(this.rulebase[i]==oldrb)  { this.rulebase[i] = newrb; oldrb.dispose(); }
  system.exchangeRulebase(oldrb, newrb);
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //		   FUNCIONES PARA BUSCAR MODULOS		//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 public Operatorset searchOperatorset(String opname) {
  for(int i=0; i<this.operation.length ; i++)
   if(this.operation[i].equals(opname))
    return this.operation[i];
  return null;
 }

 public Type searchType(String typename) {
  for(int i=0; i<this.type.length ; i++)
   if(this.type[i].equals(typename))
    return this.type[i];
  return null;
 }

 public Rulebase searchRulebase(String rbname) {
  for(int i=0; i<this.rulebase.length ; i++)
   if(this.rulebase[i].equals(rbname))
    return this.rulebase[i];
  return null;
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //		  FUNCIONES PARA SUSTITUIR DATOS		//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 public void exchange(ParamMemFunc oldmf, ParamMemFunc newmf) {
  for(int i=0; i<this.rulebase.length; i++) 
   if(oldmf != null) rulebase[i].exchange(oldmf,newmf);
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //			FUNCIONES GENERALES			//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 public boolean equals(String name) {
  return this.name.equals(name);
 }

 public String toString() {
  return new String(this.name);
 }

 public String toXfl() {
  String code = "";
  for(int i=0; i<this.operation.length; i++) code += this.operation[i].toXfl();
  for(int i=0; i<this.type.length; i++) code += this.type[i].toXfl();
  for(int i=0; i<this.rulebase.length; i++) code += this.rulebase[i].toXfl();
  if(this.system != null) code += this.system.toXfl();
  return code;
 }

 public boolean save() {
  byte buf[] = toXfl().getBytes();
  try {
   OutputStream stream = new FileOutputStream(this.file);   
   stream.write(buf);
   stream.close();
  }
  catch (IOException e) { return false; }
  return true;
 }

 public boolean save_as(File file) {
  setFile(file);
  return save();
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //           FUNCIONES QUE NECESITA EL APRENDIZAJE		//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 public void update() {
  for(int i=0; i<this.type.length; i++) this.type[i].update();
 }

 public void setAdjustable() {
  for(int i=0; i<this.type.length; i++) this.type[i].setAdjustable();
  for(int i=0; i<this.rulebase.length; i++) this.rulebase[i].setAdjustable();
  int count = 0;
  int outputs = system.getOutputs().length;
  for(int i=0; i<type.length; i++) {
   ParamMemFunc[] mf = type[i].getMembershipFunctions();
   for(int j=0; j<mf.length; j++) 
    for(int k=0; k<mf[j].parameter.length; k++)
     if(mf[j].parameter[k].isAdjustable()) count++;
  }

  adjustable = new Parameter[count];
  for(int i=0,p=0; i<type.length; i++) {
   ParamMemFunc[] mf = type[i].getMembershipFunctions();
   for(int j=0; j<mf.length; j++)
    for(int k=0; k<mf[j].parameter.length; k++) 
     if(mf[j].parameter[k].isAdjustable()) {
      mf[j].parameter[k].oderiv = new double[outputs];
      adjustable[p] = mf[j].parameter[k];
      p++;
     }
  }
 }

 public Parameter[] getAdjustable() { return adjustable; }

 public double[] estimate_derivatives(double[] input, double perturb) {
  double[] output1 = system.crispInference(input);
  for(int i=0; i<type.length; i++) {
   ParamMemFunc[] mf = type[i].getMembershipFunctions();
   for(int j=0; j<mf.length; j++)
    for(int k=0; k<mf[j].parameter.length; k++) {
     Parameter param = mf[j].parameter[k];
     if(!param.isAdjustable()) continue;
     param.oderiv = new double[output1.length];
     double prev = param.value;
     double sign = (Math.random() <0.5? 1.0 : -1.0);
     param.setDesp(sign*perturb);
     mf[j].update();
     double[] output2 = system.crispInference(input);
     if((param.value - prev)/(sign*perturb) >0.001 )
       for(int o=0; o<output1.length; o++)
         param.oderiv[o] = (output2[o] - output1[o])/(param.value - prev);
     param.value = prev;
    }
  }
  return output1;
 }

 public double[] stochastic_derivatives(double[] input, double perturb) {
  double prev[] = new double[adjustable.length];
  double val1[] = new double[adjustable.length];
  double sign[] = new double[adjustable.length];
  for(int i=0; i<adjustable.length; i++) {
   prev[i] = adjustable[i].value;
   sign[i] = (Math.random() <0.5? 1.0 : -1.0);
   adjustable[i].setDesp(sign[i]*perturb);
  }
  update();
  double[] output1 = system.crispInference(input);

  for(int i=0; i<adjustable.length; i++) {
   val1[i] = adjustable[i].value;
   adjustable[i].value = prev[i];
   adjustable[i].setDesp(-sign[i]*perturb);
  }
  update();
  double[] output2 = system.crispInference(input);

  for(int i=0; i<adjustable.length; i++) {
   double val2 = adjustable[i].value;
   adjustable[i].value = prev[i];
   if( (val1[i]-val2)/(sign[i]*perturb) >0.001 )
    for(int o=0; o<output1.length; o++)
     adjustable[i].oderiv[o] = (output2[o] - output1[o])/(val2 - val1[i]);
  }
  return system.crispInference(input);
 }

 public double[] compute_derivatives(double[] input) throws XflException {
  double[] output = system.crispInference(input);
  for(int i=0; i<output.length; i++) {
   double[] deriv = new double[output.length];
   deriv[i] = 1;
   system.derivative(deriv);
   for(int p=0; p<adjustable.length; p++) {
    adjustable[p].oderiv[i] = adjustable[p].getDeriv();
    adjustable[p].setDeriv(0);
   }
  }
  return output;
 }
}
