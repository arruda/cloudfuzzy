//+++++++++++++++++++++++++++++++++++++++++++++++++++++
// File automatically generated by Jfuzzy - DO NOT EDIT
//+++++++++++++++++++++++++++++++++++++++++++++++++++++

package pkg;

import xfuzzy.lang.*;

public class xfl_defuz_CenterOfArea extends DefuzMethod {
 public xfl_defuz_CenterOfArea() {
   super.parameter = new Parameter[0];
   super.name = "CenterOfArea";
   super.pkg = "xfl";
  }

 public double compute(AggregateMemFunc mf) {
   double min = mf.min();
   double max = mf.max();
   double step = mf.step();
  double num=0, denom=0;
  for(double x=min; x<=max; x+=step) {
   double m = mf.compute(x);
   num += x*m;
   denom += m;
  }
  if(denom==0) return (min+max)/2;
  return num/denom;
  }

 public boolean test () {
   return true;
  }

 public boolean test(AggregateMemFunc mf) {
   return true;
  }

 public String toJava(String name) {
   String eol = System.getProperty("line.separator", "\n");
   String code = " double "+name+"(OutputMembershipFunction mf) {"+eol;
   code += "   double min = mf.min();"+eol;
   code += "   double max = mf.max();"+eol;
   code += "   double step = mf.step();"+eol;
   code += "  double num=0, denom=0;"+eol;
   code += "  for(double x=min; x<=max; x+=step) {"+eol;
   code += "   double m = mf.compute(x);"+eol;
   code += "   num += x*m;"+eol;
   code += "   denom += m;"+eol;
   code += "  }"+eol;
   code += "  if(denom==0) return (min+max)/2;"+eol;
   code += "  return num/denom;"+eol;
   code += "  }"+eol;
   return code;
  }

 public String toC(String name) {
   String eol = System.getProperty("line.separator", "\n");
   String code = "static double "+name+"(FuzzyNumber mf) {"+eol;
   code += " double min = mf.conc[0].min;"+eol;
   code += " double max = mf.conc[0].max;"+eol;
   code += " double step = mf.conc[0].step;"+eol;
   code += "   double x, m, num=0, denom=0;"+eol;
   code += "   for(x=min; x<=max; x+=step) {"+eol;
   code += "    m = compute(mf,x);"+eol;
   code += "    num += x*m;"+eol;
   code += "    denom += m;"+eol;
   code += "   }"+eol;
   code += "   if(denom==0) return (min+max)/2;"+eol;
   code += "   return num/denom;"+eol;
   code += "}"+eol;
   return code;
  }

 public String toCpp(String name) {
   String eol = System.getProperty("line.separator", "\n");
   String code = "double "+name+"(OutputMembershipFunction &mf) {"+eol;
   code += " double min = mf.min();"+eol;
   code += " double max = mf.max();"+eol;
   code += " double step = mf.step();"+eol;
   code += "   double num=0, denom=0;"+eol;
   code += "   for(double x=min; x<=max; x+=step) {"+eol;
   code += "    double m = mf.compute(x);"+eol;
   code += "    num += x*m;"+eol;
   code += "    denom += m;"+eol;
   code += "   }"+eol;
   code += "   if(denom==0) return (min+max)/2;"+eol;
   code += "   return num/denom;"+eol;
   code += "}"+eol+eol;
   return code;
  }
}