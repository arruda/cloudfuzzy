//+++++++++++++++++++++++++++++++++++++++++++++++++++++
// File automatically generated by Jfuzzy - DO NOT EDIT
//+++++++++++++++++++++++++++++++++++++++++++++++++++++

package pkg;

import xfuzzy.lang.*;

public class xfl_mf_singleton extends ParamMemFunc {
 public xfl_mf_singleton() {
   super.parameter = new Parameter[1];
   super.parameter[0] = new Parameter("a");
   super.name = "singleton";
   super.pkg = "xfl";
  }

 public double compute(double x) {
   double a = parameter[0].value;
 return (x==a? 1 : 0); 
  }

 public double greatereq(double x) {
   double a = parameter[0].value;
 return (x>=a? 1 : 0); 
  }

 public double smallereq(double x) {
   double a = parameter[0].value;
 return (x<=a? 1 : 0); 
  }

 public double center() {
   double a = parameter[0].value;
 return a; 
  }

 public double[] deriv_center() {
   double[] deriv = new double[1];
   double a = parameter[0].value;
 deriv[0] = 1; 
   return deriv;
  }

 public boolean test () {
   double min = this.u.min();
   double max = this.u.max();
   double a = parameter[0].value;
   return ( a>=min && a<=max );
  }

 public String toJava() {
   String eol = System.getProperty("line.separator", "\n");
   String code = "";
   code += "  private class MF_xfl_singleton extends InnerMembershipFunction {"+eol;
   code += "   double a;"+eol;
   code += eol+"   MF_xfl_singleton( double min, double max, double step, double param[]){"+eol;
   code += "    super.min = min;"+eol;
   code += "    super.max = max;"+eol;
   code += "    super.step = step;"+eol;
   code += "    this.a = param[0];"+eol;
   code += "   }"+eol;
   code += "   double param(int _i) {"+eol;
   code += "    switch(_i) {"+eol;
   code += "     case 0: return a;"+eol;
   code += "     default: return 0;"+eol;
   code += "    }"+eol;
   code += "   }"+eol;
   code += "   double isEqual(double x) {"+eol;
   code += "    return (x==a? 1 : 0); "+eol;
   code += "   }"+eol;
   code += "   double isGreaterOrEqual(double x) {"+eol;
   code += "    return (x>=a? 1 : 0); "+eol;
   code += "   }"+eol;
   code += "   double isSmallerOrEqual(double x) {"+eol;
   code += "    return (x<=a? 1 : 0); "+eol;
   code += "   }"+eol;
   code += "   double center() {"+eol;
   code += "    return a; "+eol;
   code += "   }"+eol;
   code += "  }"+eol;
   return code;
  }

 public String toC() {
   String eol = System.getProperty("line.separator", "\n");
   String code = "";
   code += "static double MF_xfl_singleton_equal(MembershipFunction _mf,double x) {"+eol;
   code += " double a = _mf.param[0];"+eol;
   code += "  return (x==a? 1 : 0); "+eol;
   code += "}"+eol;
   code += "static double MF_xfl_singleton_greq(MembershipFunction _mf,double x) {"+eol;
   code += " double a = _mf.param[0];"+eol;
   code += "  return (x>=a? 1 : 0); "+eol;
   code += "}"+eol;
   code += "static double MF_xfl_singleton_smeq(MembershipFunction _mf,double x) {"+eol;
   code += " double a = _mf.param[0];"+eol;
   code += "  return (x<=a? 1 : 0); "+eol;
   code += "}"+eol;
   code += "static double MF_xfl_singleton_center(MembershipFunction _mf) {"+eol;
   code += " double a = _mf.param[0];"+eol;
   code += "  return a; "+eol;
   code += "}"+eol;
   code += "static MembershipFunction createMF_xfl_singleton( double min, double max, double step, double *param,int length) {"+eol;
   code += " int i;"+eol;
   code += " MembershipFunction _mf;"+eol;
   code += " _mf.min = min;"+eol;
   code += " _mf.max = max;"+eol;
   code += " _mf.step = step;"+eol;
   code += " _mf.param = (double*) malloc(length*sizeof(double));"+eol;
   code += " for(i=0;i<length;i++) _mf.param[i] = param[i];"+eol;
   code += " _mf.compute_eq = MF_xfl_singleton_equal;"+eol;
   code += " _mf.compute_greq = MF_xfl_singleton_greq;"+eol;
   code += " _mf.compute_smeq = MF_xfl_singleton_smeq;"+eol;
   code += " _mf.center = MF_xfl_singleton_center;"+eol;
   code += " _mf.basis = _defaultMFbasis;"+eol;
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
   code += "}"+eol+eol;
   code += name+" * "+name+"::dup() {"+eol;
   code += " double param[1] = {a};"+eol;
   code += " return new "+name+"(min,max,step,param,1);"+eol;
   code += "}"+eol+eol;
   code += "double "+name+"::param(int _i) {"+eol;
   code += " switch(_i) {"+eol;
   code += "  case 0: return a;"+eol;
   code += "  default: return 0;"+eol;
   code += " }"+eol;
   code += "}"+eol+eol;
   code += "double "+name+"::compute_eq(double x) {"+eol;
   code += "  return (x==a? 1 : 0); "+eol;
   code += "}"+eol+eol;
   code += "double "+name+"::compute_greq(double x) {"+eol;
   code += "  return (x>=a? 1 : 0); "+eol;
   code += "}"+eol+eol;
   code += "double "+name+"::compute_smeq(double x) {"+eol;
   code += "  return (x<=a? 1 : 0); "+eol;
   code += "}"+eol+eol;
   code += "double "+name+"::center() {"+eol;
   code += "  return a; "+eol;
   code += "}"+eol+eol;
   return code;
  }

 public String toHpp(String name) {
   String eol = System.getProperty("line.separator", "\n");
   String code = "";
   code += "class "+name+": public ParamMembershipFunction {"+eol;
   code += "private:"+eol;
   code += " double a;"+eol;
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
   code += "};"+eol+eol;
   return code;
  }

//+++++++++++++++++++++++++++++++++++++++++++++++++
 public void update() {
  if(!isAdjustable()) return;
  double min = this.u.min();
  double max = this.u.max();
  double prev = parameter[0].value;
  parameter[0].value += parameter[0].getDesp();

  if(parameter[0].value<min) parameter[0].value = min;
  if(parameter[0].value>max) parameter[0].value = max;
  parameter[0].setDesp(0.0);
  parameter[0].setPrevDesp(parameter[0].value - prev);
 }
//+++++++++++++++++++++++++++++++++++++++++++++++++
}
