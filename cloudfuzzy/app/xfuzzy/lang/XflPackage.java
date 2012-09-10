/*
 * @(#)XflPackage.java        1.0 2000/05/09
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
//			PAQUETE DE OPERACIONES			//
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

package xfuzzy.lang;

import java.util.Vector;
import java.io.*;

public class XflPackage {
 final static public int BINARY = 0;
 final static public int UNARY = 1;
 final static public int DEFUZ = 2;
 final static public int MFUNC = 3;

 private String name;
 private File file;
 private Vector binary;
 private Vector unary;
 private Vector mfunc;
 private Vector defuz;
 private boolean modified;
 private Object editor;
 private String resume;

 public XflPackage(File file) {
  String filename = file.getName();
  int index = filename.toLowerCase().indexOf(".pkg");
  if(index >0) this.name = filename.substring(0, index);
  else this.name = new String(filename);
  this.file = file;
  this.binary = new Vector();
  this.unary = new Vector();
  this.defuz = new Vector();
  this.mfunc = new Vector();
  this.modified = false;
 }

 public String toString() {
  return this.name;
 }

 public boolean equals(String name) {
  return this.name.equals(name);
 }

 public void add(Definition def, int kind) {
  Vector vector = get(kind);
  if(vector != null) vector.addElement(def);
 }

 public boolean remove(Definition def, int kind) {
  Vector vector = get(kind);
  if(vector == null) return false;
  return vector.removeElement(def);
 }

 public boolean remove(String name, int kind) {
  Vector vector = get(kind);
  if(vector == null) return false;
  Definition def = search(vector,name);
  if(def == null) return false;
  return vector.removeElement(def);
 }

 public boolean contains(String name, int kind) {
  Vector vector = get(kind);
  Definition def = search(vector,name);
  if(def == null) return false;
  return true;
 }

 public Object instantiate(String name, int kind) {
  Vector vector = get(kind);
  Definition def = search(vector,name);
  if(def == null) return null;
  return def.instantiate();
 }

 public Vector get(int kind) {
  switch(kind) {
   case BINARY: return this.binary;
   case UNARY: return this.unary;
   case DEFUZ: return this.defuz;
   case MFUNC: return this.mfunc;
   default: return null;
  }
 }

 public Definition search(Vector vector, String name) {
  if(vector == null) return null;
  for(int i=0, size = vector.size(); i<size; i++) {
   Definition def = (Definition) vector.elementAt(i);
   if(def.equals(name)) return def;
  }
  return null;
 }

 public String resume() {
  return this.resume;
 }

 public boolean compile() {
  Vector v = new Vector();
  for(int i=0, size=binary.size(); i<size; i++) {
   Definition def = (Definition) binary.elementAt(i);
   if(!def.compile()) v.addElement(def);
  }
  for(int i=0, size=unary.size(); i<size; i++) {
   Definition def = (Definition) unary.elementAt(i);
   if(!def.compile()) v.addElement(def);
  }
  for(int i=0, size=mfunc.size(); i<size; i++) {
   Definition def = (Definition) mfunc.elementAt(i);
   if(!def.compile()) v.addElement(def);
  }
  for(int i=0, size=defuz.size(); i<size; i++) {
   Definition def = (Definition) defuz.elementAt(i);
   if(!def.compile()) v.addElement(def);
  }

  String eol = System.getProperty("line.separator", "\n");
  resume = "";
  for(int i=0; i<v.size(); i++)
   resume += "Can't compile "+v.elementAt(i)+"."+eol;
  return (v.size()==0);
 }

 public void delete() {
  for(int i=0, size=binary.size(); i<size; i++)
   ((Definition) binary.elementAt(i)).unlink();
  for(int i=0, size=unary.size(); i<size; i++)
   ((Definition) unary.elementAt(i)).unlink();
  for(int i=0, size=defuz.size(); i<size; i++)
   ((Definition) defuz.elementAt(i)).unlink();
  for(int i=0, size=mfunc.size(); i<size; i++)
   ((Definition) mfunc.elementAt(i)).unlink();
  file.delete();
 }

 public String toPkg() {
  String code = "";
  for(int i=0, size=binary.size(); i<size; i++)
   code += ((Definition) binary.elementAt(i)).toPkg();
  for(int i=0, size=unary.size(); i<size; i++)
   code += ((Definition) unary.elementAt(i)).toPkg();
  for(int i=0, size=defuz.size(); i<size; i++)
   code += ((Definition) defuz.elementAt(i)).toPkg();
  for(int i=0, size=mfunc.size(); i<size; i++)
   code += ((Definition) mfunc.elementAt(i)).toPkg();
  return code;
 }

 public boolean save() {
  if(this.file == null) return false;
  String code = toPkg();
  byte buf[] = code.getBytes();

  try {
   OutputStream stream = new FileOutputStream(this.file);
   stream.write(buf);
   stream.close();
  }
  catch (IOException e) { return false; }
  return true;
 }

 public boolean save_as(File file) {
  String filename = file.getName();
  this.file = file;
  if(!save()) return false;
  rename(filename.substring(0, filename.indexOf(".")));
  compile();
  return true;
 }

 public void setModified(boolean b) {
  this.modified = b;
 }

 public boolean isModified() {
  return this.modified;
 }

 public void setEditor(Object editor) {
  this.editor = editor;
 }

 public Object getEditor() {
  return this.editor;
 }

 public boolean isEditing() {
  return (this.editor != null);
 }

 public boolean isEditable() {
  return !this.name.equals("xfl");
 }

 public File getFile() {
  return this.file;
 }

 public void rename(String name) {
  this.name = name;
  for(int i=0; i<binary.size(); i++)
   ((Definition) binary.elementAt(i)).setPackageName(name);
  for(int i=0; i<unary.size(); i++)
   ((Definition) unary.elementAt(i)).setPackageName(name);
  for(int i=0; i<defuz.size(); i++)
   ((Definition) defuz.elementAt(i)).setPackageName(name);
  for(int i=0; i<mfunc.size(); i++)
   ((Definition) mfunc.elementAt(i)).setPackageName(name);
 }
}

