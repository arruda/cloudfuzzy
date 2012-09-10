//+++++++++++++++++++++++++++++++++++++++++++++++++++++
// File automatically generated by Jfuzzy - DO NOT EDIT
//+++++++++++++++++++++++++++++++++++++++++++++++++++++

package pkg;

import xfuzzy.lang.*;

public class xfl_mf_triangle extends ParamMemFunc {
 public xfl_mf_triangle() {
   super.parameter = new Parameter[3];
   super.parameter[0] = new Parameter("a");
   super.parameter[1] = new Parameter("b");
   super.parameter[2] = new Parameter("c");
   super.name = "triangle";
   super.pkg = "xfl";
  }

 public double compute(double x) {
   double a = parameter[0].value;
   double b = parameter[1].value;
   double c = parameter[2].value;
 return (a<x && x<=b? (x-a)/(b-a) : (b<x && x<c? (c-x)/(c-b) : 0)); 
  }

 public double greatereq(double x) {
   double a = parameter[0].value;
   double b = parameter[1].value;
   double c = parameter[2].value;
 return (x<a? 0 : (x>b? 1 : (x-a)/(b-a) )); 
  }

 public double smallereq(double x) {
   double a = parameter[0].value;
   double b = parameter[1].value;
   double c = parameter[2].value;
 return (x<b? 1 : (x>c? 0 : (c-x)/(c-b) )); 
  }

 public double center() {
   double a = parameter[0].value;
   double b = parameter[1].value;
   double c = parameter[2].value;
 return b; 
  }

 public double basis() {
   double a = parameter[0].value;
   double b = parameter[1].value;
   double c = parameter[2].value;
 return (c-a); 
  }

 public double[] deriv_eq(double x) {
   double[] deriv = new double[3];
   double a = parameter[0].value;
   double b = parameter[1].value;
   double c = parameter[2].value;
   deriv[0] = (a<x && x<b ? (x-b)/((b-a)*(b-a)) : (x==a? 0.5/(a-b) : 0));
   deriv[1] = (a<x && x<b ? (a-x)/((b-a)*(b-a)) :
              (b<x && x<c ? (c-x)/((c-b)*(c-b)) :
              (x==b? 0.5/(a-b) + 0.5/(c-b) : 0)));
   deriv[2] = (b<x && x<c ? (x-b)/((c-b)*(c-b)) : (x==c? 0.5/(c-b) : 0));
   return deriv;
  }

 public double[] deriv_greq(double x) {
   double[] deriv = new double[3];
   double a = parameter[0].value;
   double b = parameter[1].value;
   double c = parameter[2].value;
   deriv[0] = (a<x && x<b ? (x-b)/((b-a)*(b-a)) : (x==a? 0.5/(a-b) : 0));
   deriv[1] = (a<x && x<b ? (a-x)/((b-a)*(b-a)) : (x==b? 0.5/(a-b) : 0));
   deriv[2] = 0;
   return deriv;
  }

 public double[] deriv_smeq(double x) {
   double[] deriv = new double[3];
   double a = parameter[0].value;
   double b = parameter[1].value;
   double c = parameter[2].value;
   deriv[0] = 0;
   deriv[1] = (b<x && x<c ? (c-x)/((c-b)*(c-b)) : (x==b? 0.5/(c-b) : 0));
   deriv[2] = (b<x && x<c ? (x-b)/((c-b)*(c-b)) : (x==c? 0.5/(c-b) : 0));
   return deriv;
  }

 public double[] deriv_center() {
   double[] deriv = new double[3];
   double a = parameter[0].value;
   double b = parameter[1].value;
   double c = parameter[2].value;
   deriv[0] = 1;
   deriv[1] = 1;
   deriv[2] = 1;
   return deriv;
  }

 public double[] deriv_basis() {
   double[] deriv = new double[3];
   double a = parameter[0].value;
   double b = parameter[1].value;
   double c = parameter[2].value;
   deriv[0] = -1;
   deriv[1] = 0;
   deriv[2] = 1;
   return deriv;
  }

 public boolean test () {
   double min = this.u.min();
   double max = this.u.max();
   double a = parameter[0].value;
   double b = parameter[1].value;
   double c = parameter[2].value;
   return ( a<b && b<c && b>=min && b<=max );
  }

 public String toJava() {
   String eol = System.getProperty("line.separator", "\n");
   String code = "";
   code += "  private class MF_xfl_triangle extends InnerMembershipFunction {"+eol;
   code += "   double a;"+eol;
   code += "   double b;"+eol;
   code += "   double c;"+eol;
   code += eol+"   MF_xfl_triangle( double min, double max, double step, double param[]){"+eol;
   code += "    super.min = min;"+eol;
   code += "    super.max = max;"+eol;
   code += "    super.step = step;"+eol;
   code += "    this.a = param[0];"+eol;
   code += "    this.b = param[1];"+eol;
   code += "    this.c = param[2];"+eol;
   code += "   }"+eol;
   code += "   double param(int _i) {"+eol;
   code += "    switch(_i) {"+eol;
   code += "     case 0: return a;"+eol;
   code += "     case 1: return b;"+eol;
   code += "     case 2: return c;"+eol;
   code += "     default: return 0;"+eol;
   code += "    }"+eol;
   code += "   }"+eol;
   code += "   double isEqual(double x) {"+eol;
   code += "    return (a<x && x<=b? (x-a)/(b-a) : (b<x && x<c? (c-x)/(c-b) : 0)); "+eol;
   code += "   }"+eol;
   code += "   double isGreaterOrEqual(double x) {"+eol;
   code += "    return (x<a? 0 : (x>b? 1 : (x-a)/(b-a) )); "+eol;
   code += "   }"+eol;
   code += "   double isSmallerOrEqual(double x) {"+eol;
   code += "    return (x<b? 1 : (x>c? 0 : (c-x)/(c-b) )); "+eol;
   code += "   }"+eol;
   code += "   double center() {"+eol;
   code += "    return b; "+eol;
   code += "   }"+eol;
   code += "   double basis() {"+eol;
   code += "    return (c-a); "+eol;
   code += "   }"+eol;
   code += "  }"+eol;
   return code;
  }

 public String toC() {
   String eol = System.getProperty("line.separator", "\n");
   String code = "";
   code += "static double MF_xfl_triangle_equal(MembershipFunction _mf,double x) {"+eol;
   code += " double a = _mf.param[0];"+eol;
   code += " double b = _mf.param[1];"+eol;
   code += " double c = _mf.param[2];"+eol;
   code += "  return (a<x && x<=b? (x-a)/(b-a) : (b<x && x<c? (c-x)/(c-b) : 0)); "+eol;
   code += "}"+eol;
   code += "static double MF_xfl_triangle_greq(MembershipFunction _mf,double x) {"+eol;
   code += " double a = _mf.param[0];"+eol;
   code += " double b = _mf.param[1];"+eol;
   code += " double c = _mf.param[2];"+eol;
   code += "  return (x<a? 0 : (x>b? 1 : (x-a)/(b-a) )); "+eol;
   code += "}"+eol;
   code += "static double MF_xfl_triangle_smeq(MembershipFunction _mf,double x) {"+eol;
   code += " double a = _mf.param[0];"+eol;
   code += " double b = _mf.param[1];"+eol;
   code += " double c = _mf.param[2];"+eol;
   code += "  return (x<b? 1 : (x>c? 0 : (c-x)/(c-b) )); "+eol;
   code += "}"+eol;
   code += "static double MF_xfl_triangle_center(MembershipFunction _mf) {"+eol;
   code += " double a = _mf.param[0];"+eol;
   code += " double b = _mf.param[1];"+eol;
   code += " double c = _mf.param[2];"+eol;
   code += "  return b; "+eol;
   code += "}"+eol;
   code += "static double MF_xfl_triangle_basis(MembershipFunction _mf) {"+eol;
   code += " double a = _mf.param[0];"+eol;
   code += " double b = _mf.param[1];"+eol;
   code += " double c = _mf.param[2];"+eol;
   code += "  return (c-a); "+eol;
   code += "}"+eol;
   code += "static MembershipFunction createMF_xfl_triangle( double min, double max, double step, double *param,int length) {"+eol;
   code += " int i;"+eol;
   code += " MembershipFunction _mf;"+eol;
   code += " _mf.min = min;"+eol;
   code += " _mf.max = max;"+eol;
   code += " _mf.step = step;"+eol;
   code += " _mf.param = (double*) malloc(length*sizeof(double));"+eol;
   code += " for(i=0;i<length;i++) _mf.param[i] = param[i];"+eol;
   code += " _mf.compute_eq = MF_xfl_triangle_equal;"+eol;
   code += " _mf.compute_greq = MF_xfl_triangle_greq;"+eol;
   code += " _mf.compute_smeq = MF_xfl_triangle_smeq;"+eol;
   code += " _mf.center = MF_xfl_triangle_center;"+eol;
   code += " _mf.basis = MF_xfl_triangle_basis;"+eol;
   code += " return _mf;"+eol;
   code += "}"+eol;
   return code;
  }
 public String toCpp(String name) {
   String eol = System.getProperty("line.separator", "\n");
   String code = "";
   code += name+"::"+name+"(double min,double max,double step,double *param, int length) :"+eol;
   code += "ParamMembershipFunction(min,max,step) {"+eol;
   code += " this->name = \""+name+"\";"+eol;
   code += " this->a = param[0];"+eol;
   code += " this->b = param[1];"+eol;
   code += " this->c = param[2];"+eol;
   code += "}"+eol+eol;
   code += name+" * "+name+"::dup() {"+eol;
   code += " double param[3] = {a,b,c};"+eol;
   code += " return new "+name+"(min,max,step,param,3);"+eol;
   code += "}"+eol+eol;
   code += "double "+name+"::param(int _i) {"+eol;
   code += " switch(_i) {"+eol;
   code += "  case 0: return a;"+eol;
   code += "  case 1: return b;"+eol;
   code += "  case 2: return c;"+eol;
   code += "  default: return 0;"+eol;
   code += " }"+eol;
   code += "}"+eol+eol;
   code += "double "+name+"::compute_eq(double x) {"+eol;
   code += "  return (a<x && x<=b? (x-a)/(b-a) : (b<x && x<c? (c-x)/(c-b) : 0)); "+eol;
   code += "}"+eol+eol;
   code += "double "+name+"::compute_greq(double x) {"+eol;
   code += "  return (x<a? 0 : (x>b? 1 : (x-a)/(b-a) )); "+eol;
   code += "}"+eol+eol;
   code += "double "+name+"::compute_smeq(double x) {"+eol;
   code += "  return (x<b? 1 : (x>c? 0 : (c-x)/(c-b) )); "+eol;
   code += "}"+eol+eol;
   code += "double "+name+"::center() {"+eol;
   code += "  return b; "+eol;
   code += "}"+eol+eol;
   code += "double "+name+"::basis() {"+eol;
   code += "  return (c-a); "+eol;
   code += "}"+eol+eol;
   return code;
  }

 public String toHpp(String name) {
   String eol = System.getProperty("line.separator", "\n");
   String code = "";
   code += "class "+name+": public ParamMembershipFunction {"+eol;
   code += "private:"+eol;
   code += " double a;"+eol;
   code += " double b;"+eol;
   code += " double c;"+eol;
   code += eol+"public:"+eol;
   code += " "+name+"() {};"+eol;
   code += " virtual ~"+name+"() {};"+eol;
   code += " "+name+"(double min,double max,double step,double *param, int length);"+eol;
   code += " "+name+"*dup();"+eol;
   code += " virtual double param(int _i);"+eol;
   code += " virtual double compute_eq(double x);"+eol;
   code += " virtual double compute_greq(double x);"+eol;
   code += " virtual double compute_smeq(double x);"+eol;
   code += " virtual double center();"+eol;
   code += " virtual double basis();"+eol;
   code += "};"+eol+eol;
   return code;
  }

//+++++++++++++++++++++++++++++++++++++++++++++++++
 public void update() {
  if(!isAdjustable()) return;
  double[] prev = get();
  double[] desp = getDesp();
  boolean[] adj = getAdjustable();
  double[] pos = sortedUpdate(prev,desp,adj);

  double min = this.u.min();
  double max = this.u.max();
  double step = this.u.step();

  if(pos[1]<min) {
   pos[1]=min;
   if(pos[2]<=pos[1]) pos[2] = pos[1]+step;
  }
  if(pos[1]>max) {
   pos[1]=max;
   if(pos[0]>=pos[1]) pos[0] = pos[1]-step;
  }

  for(int i=0; i<parameter.length; i++) {
   parameter[i].value = pos[i];
   parameter[i].setDesp(0.0);
   parameter[i].setPrevDesp(pos[i] - prev[i]);
  }
 }
//+++++++++++++++++++++++++++++++++++++++++++++++++
}
