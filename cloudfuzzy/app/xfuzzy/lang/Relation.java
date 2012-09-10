/*
 * @(#)Relation.java        1.0 2000/05/09
 *
 * This file is part of Xfuzzy 3.0, a design environment for fuzzy logic
 * based systems.
 *
 * (c) 2000 IMSE-CNM. The authors may be contacted by the email address:
 *                    xfuzzy-team@imse.cnm.es
 *
 * Xfuzzy is free software; you can redistribute it and/or rbify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation.
 *
 * Xfuzzy is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * for more details.
 */

//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
//			RELACION DIFUSA				//
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

package xfuzzy.lang;

public abstract class Relation {
 public abstract String toXfl();
 public abstract String toJava();
 public abstract String toC();
 public abstract String toCpp();
 public abstract double compute() throws XflException;
 public abstract void derivative(double de) throws XflException;
 public abstract boolean isAdjustable();
 public abstract void dispose();
 public abstract void exchange(ParamMemFunc oldmf, ParamMemFunc newmf);
 public abstract void exchange(Variable oldvar, Variable newvar);
 public abstract Object clone(Rulebase rb);
 public abstract int getKind();
 public abstract Variable getVariable();
 public abstract ParamMemFunc getMembershipFunction();
 public abstract Relation getLeftRelation();
 public abstract Relation getRightRelation();
 public abstract void setVariable(Variable var);
 public abstract void setMembershipFunction(ParamMemFunc mf);
 public abstract void setLeftRelation(Relation rel);
 public abstract void setRightRelation(Relation rel);

 public static final int NULL = 0;
 public static final int AND = 1;
 public static final int OR = 2;
 public static final int IS = 3;
 public static final int ISNOT = 4;
 public static final int GR_EQ = 5;
 public static final int SM_EQ = 6;
 public static final int GREATER = 7;
 public static final int SMALLER = 8;
 public static final int APP_EQ = 9;
 public static final int VERY_EQ = 10;
 public static final int SL_EQ = 11;
 public static final int NOT = 12;
 public static final int MoL = 13;
 public static final int SLIGHTLY = 14;
 public static final int VERY = 15;

 public static final Relation create (int kind, Relation l, Relation r,
 Variable var, ParamMemFunc mf, Rulebase rb) {
  switch (kind) {
   case AND: return new And(l,r,rb);
   case OR: return new Or(l,r,rb);
   case IS: return new Is(var,mf,rb);
   case ISNOT: return new IsNot(var,mf,rb);
   case SM_EQ: return new SmallerOrEqual(var,mf,rb);
   case GR_EQ: return new GreaterOrEqual(var,mf,rb);
   case SMALLER: return new Smaller(var,mf,rb);
   case GREATER: return new Greater(var,mf,rb);
   case APP_EQ: return new ApproxEqual(var,mf,rb);
   case VERY_EQ: return new VeryEqual(var,mf,rb);
   case SL_EQ: return new SlightlyEqual(var,mf,rb);
   case NOT: return new Not(l,rb);
   case MoL: return new MoreOrLess(l,rb);
   case SLIGHTLY: return new Slightly(l,rb);
   case VERY: return new Very(l,rb);
   default: return null;
  }
 }

 public boolean isBinary() {
  int kind = getKind();
  return (kind == AND || kind == OR);
 }

 public boolean isUnary() {
  int kind = getKind();
  return (kind == NOT || kind == MoL || kind == SLIGHTLY || kind == VERY);
 }

 public boolean isSingle() {
  int kind = getKind();
  return (kind >= IS && kind <= SL_EQ);
 }

 private Relation() {}

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //			RELACION SIMPLE				//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 private static abstract class SingleRelation extends Relation {
  protected Variable var;
  protected ParamMemFunc mf;
  protected Rulebase rb;

  SingleRelation(Variable var, ParamMemFunc mf, Rulebase rb) {
   this.var = var;
   this.var.link();
   this.mf = mf;
   this.mf.link();
   this.rb = rb;
  }

  public boolean isAdjustable() { return mf.isAdjustable(); }

  public void dispose() {
   if(this.mf != null) this.mf.unlink();
   if(this.var != null) this.var.unlink();
  }

  public void exchange(ParamMemFunc oldmf, ParamMemFunc newmf) {
   if(this.mf == oldmf) {
    this.mf.unlink();
    this.mf = newmf;
    this.mf.link();
   }
  }

  public void exchange(Variable oldvar, Variable newvar) {
   if(this.var == oldvar) {
    this.var.unlink();
    this.var = newvar;
    this.var.link();
   }
  }

  public Variable getVariable() { return this.var; }
  public ParamMemFunc getMembershipFunction() { return this.mf; }
  public Relation getLeftRelation() { return null; }
  public Relation getRightRelation() { return null; }
  public void setVariable(Variable var) { this.var = var; }
  public void setMembershipFunction(ParamMemFunc mf) { this.mf = mf; }
  public void setLeftRelation(Relation rel) { }
  public void setRightRelation(Relation rel) { }
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //			RELACION BINARIA			//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 private static abstract class BinaryRelation extends Relation {
  protected Rulebase rb;
  protected Relation l;
  protected Relation r;
  protected double ldegree;
  protected double rdegree;

  BinaryRelation(Relation l, Relation r, Rulebase rb) {
   this.rb = rb;
   this.l = l;
   this.r = r;
  }

  public boolean isAdjustable() {
   return (l.isAdjustable() || r.isAdjustable());
  }

  public void dispose() {
   if(this.l != null) this.l.dispose(); this.l = null;
   if(this.r != null) this.r.dispose(); this.r = null;
  }

  public void exchange(ParamMemFunc oldmf, ParamMemFunc newmf) {
   this.l.exchange(oldmf,newmf);
   this.r.exchange(oldmf,newmf);
  }

  public void exchange(Variable oldvar, Variable newvar) {
   this.l.exchange(oldvar,newvar);
   this.r.exchange(oldvar,newvar);
  }

  public Variable getVariable() { return null; }
  public ParamMemFunc getMembershipFunction() { return null; }
  public Relation getLeftRelation() { return this.l; }
  public Relation getRightRelation() { return this.r; }
  public void setVariable(Variable var) { }
  public void setMembershipFunction(ParamMemFunc mf) {}
  public void setLeftRelation(Relation rel) { this.l = rel; }
  public void setRightRelation(Relation rel) { this.r = rel; }
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //			RELACION UNARIA				//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 private static abstract class UnaryRelation extends Relation {
  protected Rulebase rb;
  protected Relation r;
  protected double degree;

  UnaryRelation(Relation r, Rulebase rb) {
   this.rb = rb;
   this.r = r;
  }

  public boolean isAdjustable() { return r.isAdjustable(); }
  public void dispose() { if(this.r != null) this.r.dispose(); }
  public void exchange(ParamMemFunc oldmf, ParamMemFunc newmf) {
   this.r.exchange(oldmf,newmf);
  }
  public void exchange(Variable oldvar, Variable newvar) {
   this.r.exchange(oldvar,newvar);
  }
  public Variable getVariable() { return null; }
  public ParamMemFunc getMembershipFunction() { return null; }
  public Relation getLeftRelation() { return this.r; }
  public Relation getRightRelation() { return this.r; }
  public void setVariable(Variable var) { }
  public void setMembershipFunction(ParamMemFunc mf) { }
  public void setLeftRelation(Relation rel) { this.r = rel; }
  public void setRightRelation(Relation rel) { this.r = rel; }
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //			    RELACION AND			//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 private static class And extends BinaryRelation {
  And(Relation l, Relation r, Rulebase rb) {
   super(l,r,rb);
  }

  public double compute() throws XflException {
   ldegree = l.compute();
   rdegree = r.compute();
   return rb.operation.and.compute(ldegree,rdegree);
  }

  public void derivative(double de) throws XflException {
   double deriv[] = rb.operation.and.derivative(ldegree,rdegree);
   if(deriv[0] != 0) l.derivative(de*deriv[0]);
   if(deriv[1] != 0) r.derivative(de*deriv[1]);
  }

  public String toXfl() {
   String code = "";
   if(parenthesis(this.l)) code += "("+this.l.toXfl()+")";
   else code += this.l.toXfl();
   code += " & ";
   if(parenthesis(this.r)) code += "("+this.r.toXfl()+")";
   else code += this.r.toXfl();
   return code;
  }

  public String toJava() {
   return "_op.and("+this.l.toJava()+","+this.r.toJava()+")";
  }

  public String toC() {
   return "_op.and("+this.l.toC()+","+this.r.toC()+")";
  }

  public String toCpp() {
   return "_op.and("+this.l.toCpp()+","+this.r.toCpp()+")";
  }

  public Object clone(Rulebase rbclone) {
   Relation lclone = (Relation) this.l.clone(rbclone);
   Relation rclone = (Relation) this.r.clone(rbclone);
   return new And(lclone,rclone,rbclone);
  }

  public int getKind() { return AND; }

  private boolean parenthesis(Relation rel) {
   if(rel instanceof And) return false;
   if(rel instanceof SingleRelation) return false;
   return true;
  }
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //			    RELACION OR				//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 private static class Or extends BinaryRelation {
  Or(Relation l, Relation r, Rulebase rb) {
   super(l,r,rb);
  }

  public double compute() throws XflException {
   this.ldegree = this.l.compute();
   this.rdegree = this.r.compute();
   return rb.operation.or.compute(this.ldegree,this.rdegree);
  }

  public void derivative(double de) throws XflException {
   double deriv[] = rb.operation.or.derivative(ldegree,rdegree);
   if(deriv[0] != 0) l.derivative(de*deriv[0]);
   if(deriv[1] != 0) r.derivative(de*deriv[1]);
  }

  public String toXfl() {
   String code = "";
   if(parenthesis(this.l)) code += "("+this.l.toXfl()+")";
   else code += this.l.toXfl();
   code += " | ";
   if(parenthesis(this.r)) code += "("+this.r.toXfl()+")";
   else code += this.r.toXfl();
   return code;
  }

  public String toJava() {
   return "_op.or("+this.l.toJava()+","+this.r.toJava()+")";
  }

  public String toC() {
   return "_op.or("+this.l.toC()+","+this.r.toC()+")";
  }

  public String toCpp() {
   return "_op.or("+this.l.toCpp()+","+this.r.toCpp()+")";
  }

  public Object clone(Rulebase rbclone) {
   Relation lclone = (Relation) this.l.clone(rbclone);
   Relation rclone = (Relation) this.r.clone(rbclone);
   return new Or(lclone,rclone,rbclone);
  }

  public int getKind() { return OR; }

  private boolean parenthesis(Relation rel) {
   if(rel instanceof Or) return false;
   if(rel instanceof SingleRelation) return false;
   return true;
  }
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //			    RELACION IS				//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 private static class Is extends SingleRelation {
  Is(Variable var, ParamMemFunc mf, Rulebase rb) {
   super(var,mf,rb);
  }

  public double compute() throws XflException {
   MemFunc value = this.var.getValue();
   if(value == null) throw new XflException(18);
   if(value instanceof pkg.xfl_mf_singleton) {
    return this.mf.compute( ((ParamMemFunc) value).get()[0] );
   }

   if((value instanceof AggregateMemFunc) &&
      ((AggregateMemFunc) value).isDiscrete() ) {
    double[][] val = ((AggregateMemFunc) value).getDiscreteValues();
    double deg = 0;
    for(int i=0; i<val.length; i++){
     double mu = this.mf.compute(val[i][0]);
     double minmu = (mu<val[i][1] ? mu : val[i][1]);
     if( deg<minmu ) deg = minmu;
    }
    return deg;
   }

   double min = this.mf.u.min();
   double max = this.mf.u.max();
   double step = this.mf.u.step();
   double deg = 0;

   for(double x=min; x<=max; x+=step){
    double mu1 = this.mf.compute(x);
    double mu2 = value.compute(x);
    double minmu = (mu1<mu2 ? mu1 : mu2);
    if( deg<minmu ) deg = minmu;
   }
   return deg;
  }

  public void derivative(double de) throws XflException {
   if(!this.mf.isAdjustable()) return;
   double deriv[] = this.mf.deriv_eq(this.var.getCrispValue());
   for(int i=0; i<this.mf.parameter.length; i++)
    this.mf.parameter[i].addDeriv( de*deriv[i] );
   }

  public String toXfl() { return this.var.getName()+" == "+this.mf.label; }
  public String toJava() { return "_t_"+var+"."+mf.label+".isEqual("+var+")"; }
  public String toC() { return "_isEqual(_t_"+var+"."+mf.label+","+var+")"; }
  public String toCpp() { return "_t_"+var+"."+mf.label+".isEqual("+var+")"; }
  public Object clone(Rulebase rbcl) { return new Is(this.var,this.mf,rbcl); }
  public int getKind() { return IS; }
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //		    RELACION SMALLER OR EQUAL			//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 private static class SmallerOrEqual extends SingleRelation {
  SmallerOrEqual(Variable var, ParamMemFunc mf, Rulebase rb) {
   super(var,mf,rb);
  }

  public double compute() throws XflException {
   MemFunc value = this.var.getValue();
   if(value == null) throw new XflException(18);
   if(value instanceof pkg.xfl_mf_singleton) {
    return this.mf.smallereq( ((ParamMemFunc) value).get()[0] );
   }

   if((value instanceof AggregateMemFunc) &&
      ((AggregateMemFunc) value).isDiscrete() ) {
    double[][] val = ((AggregateMemFunc) value).getDiscreteValues();
    double deg = 0;
    for(int i=0; i<val.length; i++){
     double mu = this.mf.smallereq(val[i][0]);
     double minmu = (mu<val[i][1] ? mu : val[i][1]);
     if( deg<minmu ) deg = minmu;
    }
    return deg;
   }

   double min = this.mf.u.min();
   double max = this.mf.u.max();
   double step = this.mf.u.step();
   double deg=0, smeq=0;

   for(double x=max; x>=min; x-=step){
    double mu1 = value.compute(x);
    double mu2 = this.mf.compute(x);
    if( mu2>smeq ) smeq = mu2;
    double minmu = (mu1<smeq ? mu1 : smeq);
    if( deg<minmu ) deg = minmu;
   }
   return deg;
  }

  public void derivative(double de) throws XflException {
   if(!this.mf.isAdjustable()) return;
   double deriv[] = this.mf.deriv_smeq(this.var.getCrispValue());
   for(int i=0; i<this.mf.parameter.length; i++)
    this.mf.parameter[i].addDeriv( de*deriv[i] );
  }

  public String toXfl() { return this.var.getName()+" <= "+this.mf.label; }

  public String toJava() {
   return "_t_"+var+"."+mf.label+".isSmallerOrEqual("+var+")";
  }

  public String toC() {
   return "_isSmallerOrEqual(_t_"+var+"."+mf.label+","+var+")";
  }

  public String toCpp() {
   return "_t_"+var+"."+mf.label+".isSmallerOrEqual("+var+")";
  }

  public Object clone(Rulebase rbcl) {
   return new SmallerOrEqual(this.var,this.mf,rbcl);
  }

  public int getKind() { return SM_EQ; }
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //		    RELACION GREATER OR EQUAL			//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 private static class GreaterOrEqual extends SingleRelation {
  GreaterOrEqual(Variable var, ParamMemFunc mf, Rulebase rb) {
   super(var,mf,rb);
  }

  public double compute() throws XflException {
   MemFunc value = this.var.getValue();
   if(value == null) throw new XflException(18);
   if(value instanceof pkg.xfl_mf_singleton) {
    return this.mf.greatereq( ((ParamMemFunc) value).get()[0] );
   }

   if((value instanceof AggregateMemFunc) &&
      ((AggregateMemFunc) value).isDiscrete() ) {
    double[][] val = ((AggregateMemFunc) value).getDiscreteValues();
    double deg = 0;
    for(int i=0; i<val.length; i++){
     double mu = this.mf.greatereq(val[i][0]);
     double minmu = (mu<val[i][1] ? mu : val[i][1]);
     if( deg<minmu ) deg = minmu;
    }
    return deg;
   }

   double min = this.mf.u.min();
   double max = this.mf.u.max();
   double step = this.mf.u.step();
   double deg=0, greq=0;

   for(double x=min; x<=max; x+=step){
    double mu1 = value.compute(x);
    double mu2 = this.mf.compute(x);
    if( mu2>greq ) greq = mu2;
    double minmu = (mu1<greq ? mu1 : greq);
    if( deg<minmu ) deg = minmu;
   }
   return deg;
  }

  public void derivative(double de) throws XflException {
   if(!this.mf.isAdjustable()) return;
   double deriv[] = this.mf.deriv_greq(this.var.getCrispValue());
   for(int i=0; i<this.mf.parameter.length; i++)
    this.mf.parameter[i].addDeriv( de*deriv[i] );
  }

  public String toXfl() { return this.var.getName()+" >= "+this.mf.label; }

  public String toJava() {
   return "_t_"+var+"."+mf.label+".isGreaterOrEqual("+var+")";
  }

  public String toC() {
   return "_isGreaterOrEqual(_t_"+var+"."+mf.label+","+var+")";
  }

  public String toCpp() {
   return "_t_"+var+"."+mf.label+".isGreaterOrEqual("+var+")";
  }

  public Object clone(Rulebase rbcl) {
   return new GreaterOrEqual(this.var,this.mf,rbcl);
  }

  public int getKind() { return GR_EQ; }
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //			   RELACION SMALLER			//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 private static class Smaller extends SingleRelation {
  private double degree;

  Smaller(Variable var, ParamMemFunc mf, Rulebase rb) {
   super(var,mf,rb);
  }

  public double compute() throws XflException {
   MemFunc value = this.var.getValue();
   if(value == null) throw new XflException(18);
   if(value instanceof pkg.xfl_mf_singleton) {
    degree = this.mf.greatereq( ((ParamMemFunc) value).get()[0] );
    return rb.operation.not.compute(degree);
   }

   if((value instanceof AggregateMemFunc) &&
      ((AggregateMemFunc) value).isDiscrete() ) {
    double[][] val = ((AggregateMemFunc) value).getDiscreteValues();
    double deg = 0;
    for(int i=0; i<val.length; i++){
     double mu = rb.operation.not.compute( this.mf.greatereq(val[i][0]) );
     double minmu = (mu<val[i][1] ? mu : val[i][1]);
     if( deg<minmu ) deg = minmu;
    }
    return deg;
   }

   double min = this.mf.u.min();
   double max = this.mf.u.max();
   double step = this.mf.u.step();
   double deg = 0, greq = 0;

   for(double x=min; x<=max; x+=step){
    double mu1 = value.compute(x);
    double mu2 = this.mf.compute(x);
    if( mu2>greq ) greq = mu2;
    double sm = rb.operation.not.compute(greq);
    double minmu = (mu1<sm ? mu1 : sm);
    if( deg<minmu ) deg = minmu;
   }
   return deg;
  }

  public void derivative(double de) throws XflException {
   if(!this.mf.isAdjustable()) return;
   double dnot = rb.operation.not.derivative(degree);
   double deriv[] = this.mf.deriv_greq(this.var.getCrispValue());
   for(int i=0; i<this.mf.parameter.length; i++)
    this.mf.parameter[i].addDeriv( de*dnot*deriv[i] );
  }

  public String toXfl() { return this.var.getName()+" < "+this.mf.label; }

  public String toJava() {
   return "_t_"+var+"."+mf.label+".isSmaller("+var+", _op)";
  }

  public String toC() {
   return "_isSmaller(_t_"+var+"."+mf.label+","+var+", _op)";
  }

  public String toCpp() {
   return "_t_"+var+"."+mf.label+".isSmaller("+var+", _op)";
  }

  public Object clone(Rulebase rbcl) {
   return new Smaller(this.var,this.mf,rbcl);
  }

  public int getKind() { return SMALLER; }
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //			 RELACION GREATER			//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 private static class Greater extends SingleRelation {
  private double degree;

  Greater(Variable var, ParamMemFunc mf, Rulebase rb) {
   super(var,mf,rb);
  }

  public double compute() throws XflException {
   MemFunc value = this.var.getValue();
   if(value == null) throw new XflException(18);
   if(value instanceof pkg.xfl_mf_singleton) {
    degree = this.mf.smallereq( ((ParamMemFunc) value).get()[0] );
    return rb.operation.not.compute(degree);
   }

   if((value instanceof AggregateMemFunc) &&
      ((AggregateMemFunc) value).isDiscrete() ) {
    double[][] val = ((AggregateMemFunc) value).getDiscreteValues();
    double deg = 0;
    for(int i=0; i<val.length; i++){
     double mu = rb.operation.not.compute( this.mf.smallereq(val[i][0]) );
     double minmu = (mu<val[i][1] ? mu : val[i][1]);
     if( deg<minmu ) deg = minmu;
    }
    return deg;
   }

   double min = this.mf.u.min();
   double max = this.mf.u.max();
   double step = this.mf.u.step();
   double deg = 0, smeq = 0;

   for(double x=max; x>=min; x-=step){
    double mu1 = value.compute(x);
    double mu2 = this.mf.compute(x);
    if( mu2>smeq ) smeq = mu2;
    double gr = rb.operation.not.compute(smeq);
    double minmu = (mu1<gr ? mu1 : gr);
    if( deg<minmu ) deg = minmu;
   }
   return deg;
  }

  public void derivative(double de) throws XflException {
   if(!this.mf.isAdjustable()) return;
   double dnot = rb.operation.not.derivative(degree);
   double deriv[] = this.mf.deriv_smeq(this.var.getCrispValue());
   for(int i=0; i<this.mf.parameter.length; i++)
    this.mf.parameter[i].addDeriv( de*dnot*deriv[i] );
  }

  public String toXfl() { return this.var.getName()+" > "+this.mf.label; }

  public String toJava() {
   return "_t_"+var+"."+mf.label+".isGreater("+var+", _op)";
  }

  public String toC() {
   return "_isGreater(_t_"+var+"."+mf.label+","+var+", _op)";
  }

  public String toCpp() {
   return "_t_"+var+"."+mf.label+".isGreater("+var+", _op)";
  }

  public Object clone(Rulebase rbcl) {
   return new Greater(this.var,this.mf,rbcl);
  }

  public int getKind() { return GREATER; }
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //			  RELACION ISNOT			//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 private static class IsNot extends SingleRelation {
  private double degree;

  IsNot(Variable var, ParamMemFunc mf, Rulebase rb) {
   super(var,mf,rb);
  }

  public double compute() throws XflException {
   MemFunc value = this.var.getValue();
   if(value == null) throw new XflException(18);
   if(value instanceof pkg.xfl_mf_singleton) {
    degree = this.mf.compute( ((ParamMemFunc) value).get()[0] );
    return rb.operation.not.compute(degree);
   }

   if((value instanceof AggregateMemFunc) &&
      ((AggregateMemFunc) value).isDiscrete() ) {
    double[][] val = ((AggregateMemFunc) value).getDiscreteValues();
    double deg = 0;
    for(int i=0; i<val.length; i++){
     double mu = rb.operation.not.compute( this.mf.compute(val[i][0]) );
     double minmu = (mu<val[i][1] ? mu : val[i][1]);
     if( deg<minmu ) deg = minmu;
    }
    return deg;
   }

   double min = this.mf.u.min();
   double max = this.mf.u.max();
   double step = this.mf.u.step();
   double deg = 0;

   for(double x=min; x<=max; x+=step){
    double mu1 = rb.operation.not.compute( this.mf.compute(x) );
    double mu2 = value.compute(x);
    double minmu = (mu1<mu2 ? mu1 : mu2);
    if( deg<minmu ) deg = minmu;
   }
   return deg;
  }

  public void derivative(double de) throws XflException {
   if(!this.mf.isAdjustable()) return;
   double dnot = rb.operation.not.derivative(degree);
   double deriv[] = this.mf.deriv_eq(this.var.getCrispValue());
   for(int i=0; i<this.mf.parameter.length; i++)
    this.mf.parameter[i].addDeriv( de*dnot*deriv[i] );
  }

  public String toXfl() { return this.var.getName()+" != "+this.mf.label; }

  public String toJava() {
   return "_t_"+var+"."+mf.label+".isNotEqual("+var+", _op)";
  }

  public String toC() {
   return "_isNotEqual(_t_"+var+"."+mf.label+","+var+", _op)";
  }

  public String toCpp() {
   return "_t_"+var+"."+mf.label+".isNotEqual("+var+", _op)";
  }

  public Object clone(Rulebase rbclone) {
   return new IsNot(this.var,this.mf,rbclone);
  }

  public int getKind() { return ISNOT; }
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //			RELACION APPROXEQUAL			//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 private static class ApproxEqual extends SingleRelation {
  private double degree;

  ApproxEqual(Variable var, ParamMemFunc mf, Rulebase rb) {
   super(var,mf,rb);
  }

  public double compute() throws XflException {
   MemFunc value = this.var.getValue();
   if(value == null) throw new XflException(18);
   if(value instanceof pkg.xfl_mf_singleton) {
    degree = this.mf.compute( ((ParamMemFunc) value).get()[0] );
    return rb.operation.moreorless.compute(degree);
   }

   if((value instanceof AggregateMemFunc) &&
      ((AggregateMemFunc) value).isDiscrete() ) {
    double[][] val = ((AggregateMemFunc) value).getDiscreteValues();
    double deg = 0;
    for(int i=0; i<val.length; i++){
     double mu = rb.operation.moreorless.compute( this.mf.compute(val[i][0]) );
     double minmu = (mu<val[i][1] ? mu : val[i][1]);
     if( deg<minmu ) deg = minmu;
    }
    return deg;
   }

   double min = this.mf.u.min();
   double max = this.mf.u.max();
   double step = this.mf.u.step();
   double deg = 0;
   for(double x=min; x<=max; x+=step){
    double mu1 = rb.operation.moreorless.compute( this.mf.compute(x) );
    double mu2 = value.compute(x);
    double minmu = (mu1<mu2 ? mu1 : mu2);
    if( deg<minmu ) deg = minmu;
   }
   return deg;
  }

  public void derivative(double de) throws XflException {
   if(!this.mf.isAdjustable()) return;
   double dnot = rb.operation.moreorless.derivative(degree);
   double deriv[] = this.mf.deriv_eq(this.var.getCrispValue());
   for(int i=0; i<this.mf.parameter.length; i++)
    this.mf.parameter[i].addDeriv( de*dnot*deriv[i] );
  }

  public String toXfl() { return this.var.getName()+" ~= "+this.mf.label; }

  public String toJava() {
   return "_t_"+var+"."+mf.label+".isApproxEqual("+var+", _op)";
  }

  public String toC() {
   return "_isApproxEqual(_t_"+var+"."+mf.label+","+var+", _op)";
  }

  public String toCpp() {
   return "_t_"+var+"."+mf.label+".isApproxEqual("+var+", _op)";
  }

  public Object clone(Rulebase rbclone) {
   return new ApproxEqual(this.var,this.mf,rbclone);
  }

  public int getKind() { return APP_EQ; }
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //			RELACION VERYEQUAL			//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 private static class VeryEqual extends SingleRelation {
  private double degree;

  VeryEqual(Variable var, ParamMemFunc mf, Rulebase rb) {
   super(var,mf,rb);
  }

  public double compute() throws XflException {
   MemFunc value = this.var.getValue();
   if(value == null) throw new XflException(18);
   if(value instanceof pkg.xfl_mf_singleton) {
    degree = this.mf.compute( ((ParamMemFunc) value).get()[0] );
    return rb.operation.very.compute(degree);
   }

   if((value instanceof AggregateMemFunc) &&
      ((AggregateMemFunc) value).isDiscrete() ) {
    double[][] val = ((AggregateMemFunc) value).getDiscreteValues();
    double deg = 0;
    for(int i=0; i<val.length; i++){
     double mu = rb.operation.very.compute( this.mf.compute(val[i][0]) );
     double minmu = (mu<val[i][1] ? mu : val[i][1]);
     if( deg<minmu ) deg = minmu;
    }
    return deg;
   }

   double min = this.mf.u.min();
   double max = this.mf.u.max();
   double step = this.mf.u.step();
   double deg = 0;
   for(double x=min; x<=max; x+=step){
    double mu1 = rb.operation.very.compute( this.mf.compute(x) );
    double mu2 = value.compute(x);
    double minmu = (mu1<mu2 ? mu1 : mu2);
    if( deg<minmu ) deg = minmu;
   }
   return deg;
  }

  public void derivative(double de) throws XflException {
   if(!this.mf.isAdjustable()) return;
   double dnot = rb.operation.very.derivative(degree);
   double deriv[] = this.mf.deriv_eq(this.var.getCrispValue());
   for(int i=0; i<this.mf.parameter.length; i++)
    this.mf.parameter[i].addDeriv( de*dnot*deriv[i] );
  }

  public String toXfl() { return this.var.getName()+" += "+this.mf.label; }

  public String toJava() {
   return "_t_"+var+"."+mf.label+".isVeryEqual("+var+", _op)";
  }

  public String toC() {
   return "_isVeryEqual(_t_"+var+"."+mf.label+","+var+", _op)";
  }

  public String toCpp() {
   return "_t_"+var+"."+mf.label+".isVeryEqual("+var+", _op)";
  }

  public Object clone(Rulebase rbclone) {
   return new VeryEqual(this.var,this.mf,rbclone);
  }

  public int getKind() { return VERY_EQ; }
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //		      RELACION SLIGHTLYEQUAL			//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 private static class SlightlyEqual extends SingleRelation {
  private double degree;

  SlightlyEqual(Variable var, ParamMemFunc mf, Rulebase rb) {
   super(var,mf,rb);
  }

  public double compute() throws XflException {
   MemFunc value = this.var.getValue();
   if(value == null) throw new XflException(18);
   if(value instanceof pkg.xfl_mf_singleton) {
    degree = this.mf.compute( ((ParamMemFunc) value).get()[0] );
    return rb.operation.slightly.compute(degree);
   }

   if((value instanceof AggregateMemFunc) &&
      ((AggregateMemFunc) value).isDiscrete() ) {
    double[][] val = ((AggregateMemFunc) value).getDiscreteValues();
    double deg = 0;
    for(int i=0; i<val.length; i++){
     double mu = rb.operation.slightly.compute( this.mf.compute(val[i][0]) );
     double minmu = (mu<val[i][1] ? mu : val[i][1]);
     if( deg<minmu ) deg = minmu;
    }
    return deg;
   }

   double min = this.mf.u.min();
   double max = this.mf.u.max();
   double step = this.mf.u.step();
   double deg = 0;
   for(double x=min; x<=max; x+=step){
    double mu1 = rb.operation.slightly.compute( this.mf.compute(x) );
    double mu2 = value.compute(x);
    double minmu = (mu1<mu2 ? mu1 : mu2);
    if( deg<minmu ) deg = minmu;
   }
   return deg;
  }

  public void derivative(double de) throws XflException {
   if(!this.mf.isAdjustable()) return;
   double dnot = rb.operation.slightly.derivative(degree);
   double deriv[] = this.mf.deriv_eq(this.var.getCrispValue());
   for(int i=0; i<this.mf.parameter.length; i++)
    this.mf.parameter[i].addDeriv( de*dnot*deriv[i] );
  }

  public String toXfl() { return this.var.getName()+" %= "+this.mf.label; }

  public String toJava() {
   return "_t_"+var+"."+mf.label+".isSlightlyEqual("+var+", _op)";
  }

  public String toC() {
   return "_isSlightlyEqual(_t_"+var+"."+mf.label+","+var+", _op)";
  }

  public String toCpp() {
   return "_t_"+var+"."+mf.label+".isSlightlyEqual("+var+", _op)";
  }

  public Object clone(Rulebase rbclone) {
   return new SlightlyEqual(this.var,this.mf,rbclone);
  }

  public int getKind() { return SL_EQ; }
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //			   RELACION NOT				//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 private static class Not extends UnaryRelation {
  Not(Relation r, Rulebase rb) {
   super(r,rb);
  }

  public double compute() throws XflException {
   degree = r.compute();
   return rb.operation.not.compute(degree);
  }

  public void derivative(double de) throws XflException {
   double deriv = rb.operation.not.derivative(degree);
   if(deriv != 0) r.derivative(deriv*de);
  }

  public String toXfl() { return "!("+this.r.toXfl()+")"; }
  public String toJava() { return "_op.not("+this.r.toJava()+")"; }
  public String toC() { return "_op.not("+this.r.toC()+")"; }
  public String toCpp() { return "_op.not("+this.r.toCpp()+")"; }
  public Object clone(Rulebase rbclone) {
   Relation rclone = (Relation) this.r.clone(rbclone);
   return new Not(rclone,rbclone);
  }
  public int getKind() { return NOT; }
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //			RELACION MORE OR LESS			//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 private static class MoreOrLess extends UnaryRelation {
  MoreOrLess(Relation r, Rulebase rb) {
   super(r,rb);
  }

  public double compute() throws XflException {
   degree = r.compute();
   return rb.operation.moreorless.compute(degree);
  }

  public void derivative(double de) throws XflException {
   double deriv = rb.operation.moreorless.derivative(degree);
   if(deriv != 0) r.derivative(deriv*de);
  }

  public String toXfl() { return "~("+this.r.toXfl()+")"; }
  public String toJava() { return "_op.moreorless("+this.r.toJava()+")"; }
  public String toC() { return "_op.moreorless("+this.r.toC()+")"; }
  public String toCpp() { return "_op.moreorless("+this.r.toCpp()+")"; }
  public Object clone(Rulebase rbclone) {
   Relation rclone = (Relation) this.r.clone(rbclone);
   return new MoreOrLess(rclone,rbclone);
  }
  public int getKind() { return MoL; }
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //			RELACION SLIGHTLY			//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 private static class Slightly extends UnaryRelation {
  Slightly(Relation r, Rulebase rb) {
   super(r,rb);
  }

  public double compute() throws XflException {
   degree = r.compute();
   return rb.operation.slightly.compute(degree);
  }

  public void derivative(double de) throws XflException {
   double deriv = rb.operation.slightly.derivative(degree);
   if(deriv != 0) r.derivative(deriv*de);
  }

  public String toXfl() { return "%("+this.r.toXfl()+")"; }
  public String toJava() { return "_op.slightly("+this.r.toJava()+")"; }
  public String toC() { return "_op.slightly("+this.r.toC()+")"; }
  public String toCpp() { return "_op.slightly("+this.r.toCpp()+")"; }
  public Object clone(Rulebase rbclone) {
   Relation rclone = (Relation) this.r.clone(rbclone);
   return new Slightly(rclone,rbclone);
  }
  public int getKind() { return SLIGHTLY; }
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //			  RELACION VERY				//
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

 private static class Very extends UnaryRelation {
  Very(Relation r, Rulebase rb) {
   super(r,rb);
  }

  public double compute() throws XflException {
   degree = r.compute();
   return rb.operation.very.compute(degree);
  }

  public void derivative(double de) throws XflException {
   double deriv = rb.operation.very.derivative(degree);
   if(deriv != 0) r.derivative(deriv*de);
  }

  public String toXfl() { return "+("+this.r.toXfl()+")"; }
  public String toJava() { return "_op.very("+this.r.toJava()+")"; }
  public String toC() { return "_op.very("+this.r.toC()+")"; }
  public String toCpp() { return "_op.very("+this.r.toCpp()+")"; }
  public Object clone(Rulebase rbclone) {
   Relation rclone = (Relation) this.r.clone(rbclone);
   return new Very(rclone,rbclone);
  }
  public int getKind() { return VERY; }
 }
}
