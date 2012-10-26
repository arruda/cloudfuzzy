/*
 * @(#)Operatorset.java        1.0 2000/05/09
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
//			SELECCION DE OPERADORES			//
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

package xfuzzy.lang;

public class Operatorset implements Cloneable {
 private boolean defaultDef;
 private String name;
 private int link;
 private boolean editing = false;

 public Binary and;
 public Binary or;
 public Binary imp;
 public Binary also;
 public Unary not;
 public Unary very;
 public Unary moreorless;
 public Unary slightly;
 public DefuzMethod defuz;

 public Operatorset() {
  this.defaultDef = true;
  this.name = "_default_";
  this.and = (Binary) getDefault(FuzzyOperator.AND);
  this.or = (Binary) getDefault(FuzzyOperator.OR);
  this.imp = (Binary) getDefault(FuzzyOperator.IMP);
  this.also = (Binary) getDefault(FuzzyOperator.ALSO);
  this.not = (Unary) getDefault(FuzzyOperator.NOT); 
  this.very = (Unary) getDefault(FuzzyOperator.VERY);
  this.moreorless = (Unary) getDefault(FuzzyOperator.MOREORLESS);
  this.slightly = (Unary) getDefault(FuzzyOperator.SLIGHTLY);
  this.defuz = (DefuzMethod) getDefault(FuzzyOperator.DEFUZMETHOD);
  this.link = 0;
 }

 public Operatorset(String name) {
  this();
  this.defaultDef = false;
  this.name = name;
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //			FUNCIONES GENERALES			//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 public boolean isDefault() {
  return this.defaultDef;
 }

 public boolean equals(String name) {
  return this.name.equals(name);
 }

 public String getName() {
  return new String(this.name);
 }

 public void setName(String name) {
  this.name = name;
 }

 public Object clone() {
  Operatorset clone;
  try { clone = (Operatorset) super.clone(); }
  catch(CloneNotSupportedException e) { clone = new Operatorset(this.name); }
  for(int kind=1; kind<10; kind++) clone.set(get(kind),kind);
  return clone;
 }

 public void link() {
  this.link++;
 }

 public void unlink() {
  this.link--;
 }

 public boolean isLinked() {
  return (this.link>0);
 }

 public void setEditing(boolean editing) {
  this.editing = editing;
 }

 public boolean isEditing() {
  return this.editing;
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //		FUNCIONES PARA SELECCIONAR UN OPERADOR		//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 public void set(FuzzyOperator fzop, int kind) {
  switch(kind) {
   case FuzzyOperator.AND: this.and = (Binary) fzop; break;
   case FuzzyOperator.OR: this.or = (Binary) fzop; break;
   case FuzzyOperator.IMP: this.imp = (Binary) fzop; break;
   case FuzzyOperator.ALSO: this.also = (Binary) fzop; break;
   case FuzzyOperator.NOT: this.not = (Unary) fzop; break;
   case FuzzyOperator.VERY: this.very = (Unary) fzop; break;
   case FuzzyOperator.MOREORLESS: this.moreorless = (Unary) fzop; break;
   case FuzzyOperator.SLIGHTLY: this.slightly = (Unary) fzop; break;
   case FuzzyOperator.DEFUZMETHOD: this.defuz = (DefuzMethod) fzop; break;
  }
 }
  
 public FuzzyOperator get(int kind) {
  FuzzyOperator fzop = null;
  switch(kind) {
   case FuzzyOperator.AND: fzop = this.and; break;
   case FuzzyOperator.OR: fzop = this.or; break;
   case FuzzyOperator.IMP: fzop = this.imp; break;
   case FuzzyOperator.ALSO: fzop = this.also; break;
   case FuzzyOperator.NOT: fzop = this.not; break;
   case FuzzyOperator.VERY: fzop = this.very; break;
   case FuzzyOperator.MOREORLESS: fzop = this.moreorless; break;
   case FuzzyOperator.SLIGHTLY: fzop = this.slightly; break;
   case FuzzyOperator.DEFUZMETHOD: fzop = this.defuz; break;
  }
  if(fzop == null) return null;
  return (FuzzyOperator) fzop.clone();
 }

 public static FuzzyOperator getDefault(int kind) {
  FuzzyOperator fzop = null;
  switch(kind) {
   case FuzzyOperator.AND:
   case FuzzyOperator.IMP:
     fzop = new pkg.xfl_binary_min(); break;
   case FuzzyOperator.OR:
   case FuzzyOperator.ALSO:
     fzop = new pkg.xfl_binary_max(); break;
   case FuzzyOperator.NOT:
     fzop = new pkg.xfl_unary_not(); break;
   case FuzzyOperator.VERY:
     fzop = new pkg.xfl_unary_pow(); fzop.set(2.0); break;
   case FuzzyOperator.MOREORLESS:
     fzop = new pkg.xfl_unary_pow(); fzop.set(0.5); break;
   case FuzzyOperator.SLIGHTLY:
     fzop = new pkg.xfl_unary_parabola(); break;
   case FuzzyOperator.DEFUZMETHOD:
     fzop = new pkg.xfl_defuz_CenterOfArea(); break;
  }
  if(fzop != null) fzop.setDefault(true);
  return fzop;
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //		    FUNCIONES PARA GENERAR CODIGO		//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 public String toString() {
  return this.name.toString();
 }

 public String toXfl() {
  String eol = System.getProperty("line.separator", "\n");
  if(this.defaultDef) return "";
  String code = "operatorset "+this.name+" {"+eol;

  if(!this.and.isDefault()) code += "  and "+this.and.toXfl()+";"+eol;
  if(!this.or.isDefault()) code += "  or "+this.or.toXfl()+";"+eol;
  if(!this.imp.isDefault()) code += "  imp "+this.imp.toXfl()+";"+eol;
  if(!this.also.isDefault()) code += "  also "+this.also.toXfl()+";"+eol;
  if(!this.not.isDefault()) code += "  not "+this.not.toXfl()+";"+eol;
  if(!this.very.isDefault()) code += "  strongly "+this.very.toXfl()+";"+eol;
  if(!this.moreorless.isDefault())
   code += "  moreorless "+this.moreorless.toXfl()+";"+eol;
  if(!this.slightly.isDefault())
   code += "  slightly "+this.slightly.toXfl()+";"+eol;
  if(!this.defuz.isDefault()) code += "  defuz "+this.defuz.toXfl()+";"+eol;

  code += " }"+eol+eol;
  return code;
 }

 public String toJava() {
  String eol = System.getProperty("line.separator", "\n");
  String code = "";
  code += " //+++++++++++++++++++++++++++++++++++++++++++++++++++++//"+eol;
  code += " //     Operator set OP_"+name+"      //"+eol;
  code += " //+++++++++++++++++++++++++++++++++++++++++++++++++++++//"+eol+eol;
  code += " private class OP_"+name+" extends InnerOperatorset {"+eol;
  code += this.and.toJava("and");
  code += this.or.toJava("or");
  code += this.also.toJava("also");
  code += this.imp.toJava("imp");
  code += this.not.toJava("not");
  code += this.very.toJava("very");
  code += this.moreorless.toJava("moreorless");
  code += this.slightly.toJava("slightly");
  code += this.defuz.toJava("defuz");
  code += " }"+eol+eol;
  return code;
 }

 public String toC() throws XflException {
  String eol = System.getProperty("line.separator", "\n");
  String code = "";
  code += "/***************************************/"+eol;
  code += "/*  Operatorset OP_"+name+" */"+eol;
  code += "/***************************************/"+eol;
  code += eol+this.and.toC("OP_"+name+"_And");
  code += eol+this.or.toC("OP_"+name+"_Or");
  code += eol+this.also.toC("OP_"+name+"_Also");
  code += eol+this.imp.toC("OP_"+name+"_Imp");
  code += eol+this.not.toC("OP_"+name+"_Not");
  code += eol+this.very.toC("OP_"+name+"_Very");
  code += eol+this.moreorless.toC("OP_"+name+"_MoreOrLess");
  code += eol+this.slightly.toC("OP_"+name+"_Slightly");
  code += eol+this.defuz.toC("OP_"+name+"_Defuz");
  code += eol+"static OperatorSet createOP_"+name+"() {"+eol;
  code += " OperatorSet op;"+eol;
  code += " op.and = OP_"+name+"_And;"+eol;
  code += " op.or = OP_"+name+"_Or;"+eol;
  code += " op.also = OP_"+name+"_Also;"+eol;
  code += " op.imp = OP_"+name+"_Imp;"+eol;
  code += " op.not = OP_"+name+"_Not;"+eol;
  code += " op.very = OP_"+name+"_Very;"+eol;
  code += " op.moreorless = OP_"+name+"_MoreOrLess;"+eol;
  code += " op.slightly = OP_"+name+"_Slightly;"+eol;
  code += " op.defuz = OP_"+name+"_Defuz;"+eol;
  code += " return op;"+eol;
  code += "}"+eol+eol;
  return code;
 }

 public String toHpp(String spname) throws XflException {
  String eol = System.getProperty("line.separator", "\n");
  String code = "";
  code += "//+++++++++++++++++++++++++++++++++++++//"+eol;
  code += "//  Operatorset OP_"+spname+"_"+name+" //"+eol;
  code += "//+++++++++++++++++++++++++++++++++++++//"+eol+eol;
  code += "class OP_"+spname+"_"+name+": public Operatorset {"+eol;
  code += "public:"+eol;
  code += " virtual ~OP_"+spname+"_"+name+"() {};"+eol;
  code += " virtual double and(double a, double b);"+eol;
  code += " virtual double or(double a, double b);"+eol;
  code += " virtual double also(double a, double b);"+eol;
  code += " virtual double imp(double a, double b);"+eol;
  code += " virtual double not(double a);"+eol;
  code += " virtual double very(double a);"+eol;
  code += " virtual double moreorless(double a);"+eol;
  code += " virtual double slightly(double a);"+eol;
  code += " virtual double defuz(OutputMembershipFunction &mf);"+eol;
  code += "};"+eol+eol;
  return code;
 }

 public String toCpp(String spname) throws XflException {
  String opname = "OP_"+spname+"_"+name;
  String eol = System.getProperty("line.separator", "\n");
  String code = "";
  code += "//+++++++++++++++++++++++++++++++++++++//"+eol;
  code += "//  Operatorset "+opname+" //"+eol;
  code += "//+++++++++++++++++++++++++++++++++++++//"+eol+eol;
  code += this.and.toCpp(opname+"::and");
  code += this.or.toCpp(opname+"::or");
  code += this.also.toCpp(opname+"::also");
  code += this.imp.toCpp(opname+"::imp");
  code += this.not.toCpp(opname+"::not");
  code += this.very.toCpp(opname+"::very");
  code += this.moreorless.toCpp(opname+"::moreorless");
  code += this.slightly.toCpp(opname+"::slightly");
  code += this.defuz.toCpp(opname+"::defuz");
  return code;
 }
}

