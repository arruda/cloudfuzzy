/*
 * @(#)Definition.java        1.0 2000/05/09
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
//		   DEFINICION DE UNA OPERACION			//
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

package xfuzzy.lang;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Vector;

import xfuzzy.Xfuzzy;

public abstract class Definition implements Cloneable {
 public static final int REQUIREMENTS = 0;
 public static final int SOURCE = 1;
 public static final int JAVA_EQUAL = 2;
 public static final int JAVA_GREQ = 3;
 public static final int JAVA_SMEQ = 4;
 public static final int JAVA_CENTER = 5;
 public static final int JAVA_BASIS = 6;
 public static final int DERIV_EQUAL = 7;
 public static final int DERIV_GREQ = 8;
 public static final int DERIV_SMEQ = 9;
 public static final int DERIV_CENTER = 10;
 public static final int DERIV_BASIS = 11;
 public static final int C_EQUAL = 12;
 public static final int C_GREQ = 13;
 public static final int C_SMEQ = 14;
 public static final int C_CENTER = 15;
 public static final int C_BASIS = 16;
 public static final int CPP_EQUAL = 17;
 public static final int CPP_GREQ = 18;
 public static final int CPP_SMEQ = 19;
 public static final int CPP_CENTER = 20;
 public static final int CPP_BASIS = 21;
 
 protected String eol = System.getProperty("line.separator", "\n");
 protected String pkg;
 protected String name;
 private Vector alias;
 protected Vector param;
 protected String requires;
 protected String other;

 private Class defclass;

 public Definition(String pkg, String name) {
  this.pkg = pkg;
  this.name = name;
  this.alias = new Vector();
  this.param = new Vector();
  this.defclass = getDefClass();
 }

 public static Definition createBinaryDefinition(String pkg, String name) {
  return new BinaryDefinition(pkg,name);
 }

 public static Definition createUnaryDefinition(String pkg, String name) {
  return new UnaryDefinition(pkg,name);
 }

 public static  Definition createMFDefinition(String pkg, String name) {
  return new MFDefinition(pkg,name);
 }

 public  static Definition createDefuzDefinition(String pkg, String name) {
  return new DefuzDefinition(pkg,name);
 }

 public String toString() {
  return this.name;
 }

 public boolean equals(String name) {
  if(this.name.equals(name)) return true;
  for(int i=0, size=alias.size(); i<size; i++)
   if(alias.elementAt(i).equals(name)) return true;
  return false;
 }

 public Object clone() {
  try { return super.clone(); }
  catch(CloneNotSupportedException e) { return null; }
 }

 public void unlink() {
  String javafilename = "pkg/"+classname()+".java";
  File path = new File( Xfuzzy.fuzzyPath );
  File javafile = new File(path,javafilename);
  String classfilename = "pkg/"+classname()+".class";
  File classfile = new File(path,classfilename);
  javafile.delete();
  classfile.delete();
 }

 public boolean compile() {
  String filename = "pkg/"+classname()+".java";
  File path = new File( Xfuzzy.fuzzyPath );
  File file = new File(path,filename);
  byte buf[] = source().getBytes();
  String command;

  try {
   OutputStream f = new FileOutputStream(file);
   f.write(buf);
   f.close();

   if(File.separatorChar == '\\')
    command = "javac \""+file.getAbsolutePath()+"\"";
   else command = "javac "+file.getAbsolutePath();
   Runtime r = Runtime.getRuntime();
   Process p = r.exec(command);
   p.waitFor();
  } catch (Exception e) { return false; }

  this.defclass = getDefClass();
  if(this.defclass == null) return false;
  return true;
 }

 public Object instantiate() {
  Object newobject;

  if(defclass == null) return null;
  try { newobject = this.defclass.newInstance(); }
  catch (IllegalAccessException e) { return null; }
  catch (InstantiationException e) { return null; }
  return newobject;
 }

 public String source() {
  String code = headline_code();
  code += class_code();
  code += constructor_code();
  code += compute_code();
  code += test_code();
  code += defined_code();
  code += java_code();
  code += c_code();
  code += cpp_code();
  code += other_code();
  code += "}"+eol;
  return code;
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //		  FUNCIONES DE ACCESO A LOS DATOS		//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 public void setName(String name) { this.name = name; }
 public void setAlias(Vector alias) { this.alias = alias; }
 public void setParameters(Vector param) { this.param = param; }
 public void setDefinedFor(Vector v) {}
 public void setPackageName(String name) { this.pkg = name; }
 public String getPackageName() { return this.pkg; }
 public String getName() { return this.name; }
 public Vector getAlias() { return this.alias; }
 public Vector getParameters() { return this.param; }
 public Vector getDefinedFor() { return new Vector(); }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //			FUNCIONES PRIVADAS			//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 private Class getDefClass() {
  Class getclass;
  try { getclass = Class.forName("pkg."+classname()); }
  catch (ClassNotFoundException e){ return null; }
  return getclass;
 }

 protected String [] sample(String str) {
  int lines=1;
  if(str == null) return new String[0];
  for(int i=0; i<str.length(); i++) if(str.charAt(i) == '\n') lines++;
  String value[] = new String[lines];

  int line=0;
  value[0] = "";
  for(int i=0; i<str.length(); i++)
   switch(str.charAt(i)) {
    case '\b': value[line] += "\\b"; continue;
    case '\t': value[line] += "\\t"; continue;
    case '\f': value[line] += "\\f"; continue;
    case '\"': value[line] += "\\\""; continue;
    case '\'': value[line] += "\\\'"; continue;
    case '\\': value[line] += "\\\\"; continue;
    case '\n': line++; value[line] = ""; continue;
    case '\r': continue;
    case '\0': continue;
    default: value[line] += str.substring(i,i+1);
   }
  return value;
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //	FUNCIONES AUXILIARES PARA ESCRIBIR LA DEFINICION	//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 protected String aliasblock() {
  int size = this.alias.size();
  if(size==0) return "";
  String code = " alias "+this.alias.elementAt(0);
  for(int i=1; i<size; i++) code += ", "+this.alias.elementAt(i);
  code += ";"+eol;
  return code;
 }

 protected String paramblock() {
  int size = this.param.size();
  if(size==0) return "";
  String code = " parameter "+this.param.elementAt(0);
  for(int i=1; i<size; i++) code += ", "+this.param.elementAt(i);
  code += ";"+eol;
  return code;
 }

 protected String requiresblock() {
  if(this.requires == null || this.requires.length()==0) return "";
  return " requires {"+eol+this.requires+eol+"  }"+eol;
 }

 protected String otherblock() {
  if(this.other == null || this.other.length()==0) return "";
  return " source {"+eol+this.other+eol+"  }"+eol;
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //	   FUNCIONES AUXILIARES PARA ESCRIBIR EL CODIGO		//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 protected String headline_code() {
  String headline;
  headline  = "//+++++++++++++++++++++++++++++++++++++++++++++++++++++"+eol;
  headline += "// File automatically generated by Xfuzzy - DO NOT EDIT"+eol;
  headline += "//+++++++++++++++++++++++++++++++++++++++++++++++++++++"+eol;
  headline += eol+"package pkg;"+eol+eol;
  headline += "import xfuzzy.lang.*;"+eol+eol;
  return headline;
 }

 protected String constructor_code() {
  String code = " public "+classname()+"() {"+eol;
  code += "   super.parameter = new Parameter["+param.size()+"];"+eol;
  for(int i=0, size = param.size(); i<size; i++) {
   code += "   super.parameter["+i+"] = ";
   code += "new Parameter(\""+param.elementAt(i)+"\");"+eol;
  }
  code += "   super.name = \""+name+"\";"+eol;
  code += "   super.pkg = \""+pkg+"\";"+eol;
  code += "  }"+eol;
  return code;
 }

 protected String test_code() {
  String code = eol+" public boolean test () {"+eol;
  if( this.requires == null ) code += "   return true;"+eol;
  else {
   for(int i=0; i<param.size(); i++)
    code += "   double "+param.elementAt(i)+" = parameter["+i+"].value;"+eol;
   code += "   return ("+this.requires+");"+eol;
  }
  code += "  }"+eol;
  return code;
 }

 protected String other_code() {
  if(this.other == null) return "";
  return this.other+eol;
 }

 protected String defined_code() { return ""; }

 protected String c_code() {
  String ccode = eol+" public String toC(String name)";
  ccode += " throws XflException {"+eol;
  ccode += "  throw new XflException();"+eol;
  ccode += " }"+eol;
  return ccode;
 }

 protected String cpp_code() {
  String ccode = eol+" public String toCpp(String name)";
  ccode += " throws XflException {"+eol;
  ccode += "  throw new XflException();"+eol;
  ccode += " }"+eol;
  return ccode;
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //			FUNCIONES ABSTRACTAS			//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 abstract public String toPkg();
 abstract public int getKind();
 abstract public String getCode(int kind);
 abstract public void setCode(int kind, String code);
 abstract protected String classname();
 abstract protected String class_code();
 abstract protected String compute_code();
 abstract protected String java_code();


//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
}
