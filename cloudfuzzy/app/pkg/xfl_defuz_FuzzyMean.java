//+++++++++++++++++++++++++++++++++++++++++++++++++++++
// File automatically generated by Jfuzzy - DO NOT EDIT
//+++++++++++++++++++++++++++++++++++++++++++++++++++++

package pkg;

import xfuzzy.lang.*;

public class xfl_defuz_FuzzyMean extends DefuzMethod {
 public xfl_defuz_FuzzyMean() {
   super.parameter = new Parameter[0];
   super.name = "FuzzyMean";
   super.pkg = "xfl";
  }

 public double compute(AggregateMemFunc mf) {
   double min = mf.min();
   double max = mf.max();
  double num=0, denom=0;
  for(int i=0; i<mf.conc.length; i++) {
   num += mf.conc[i].degree() * mf.conc[i].center();
   denom += mf.conc[i].degree();
  }
  if(denom==0) return (min+max)/2;
  return num/denom;
  }

 public boolean test () {
   return true;
  }

 public boolean test(AggregateMemFunc mf) {
   for(int i=0; i<mf.conc.length; i++) {
     ParamMemFunc pmf = mf.conc[i].getMF();
     if(!(pmf instanceof pkg.xfl_mf_triangle)
       && !(pmf instanceof pkg.xfl_mf_isosceles)
       && !(pmf instanceof pkg.xfl_mf_trapezoid)
       && !(pmf instanceof pkg.xfl_mf_bell)
       && !(pmf instanceof pkg.xfl_mf_rectangle)
       && !(pmf instanceof pkg.xfl_mf_singleton)
       ) return false;
    }
   return true;
  }

 public String toJava(String name) {
   String eol = System.getProperty("line.separator", "\n");
   String code = " double "+name+"(OutputMembershipFunction mf) {"+eol;
   code += "   double min = mf.min();"+eol;
   code += "   double max = mf.max();"+eol;
   code += "  double num=0, denom=0;"+eol;
   code += "  for(int i=0; i<mf.conc.length; i++) {"+eol;
   code += "   num += mf.conc[i].degree() * mf.conc[i].center();"+eol;
   code += "   denom += mf.conc[i].degree();"+eol;
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
   code += "   double num=0, denom=0;"+eol;
   code += "   int i;"+eol;
   code += "   for(i=0; i<mf.length; i++) {"+eol;
   code += "    num += mf.degree[i] * center(mf.conc[i]);"+eol;
   code += "    denom += mf.degree[i];"+eol;
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
   code += "   double num=0, denom=0;"+eol;
   code += "   for(int i=0; i<mf.length; i++) {"+eol;
   code += "    num += mf.conc[i]->degree() * mf.conc[i]->center();"+eol;
   code += "    denom += mf.conc[i]->degree();"+eol;
   code += "   }"+eol;
   code += "   if(denom==0) return (min+max)/2;"+eol;
   code += "   return num/denom;"+eol;
   code += "}"+eol+eol;
   return code;
  }

//+++++++++++++++++++
 public void derivative(AggregateMemFunc mf, double derror) {
   double num=0, denom=0;
   for(int i=0; i<mf.conc.length; i++) {
     num += mf.conc[i].degree() * mf.conc[i].center();
     denom += mf.conc[i].degree();
    }
   double y = num/denom;
   for(int i=0; i<mf.conc.length; i++) {
     mf.conc[i].setDegreeDeriv(derror*(mf.conc[i].center()-y)/denom);
     mf.conc[i].setCenterDeriv(derror*mf.conc[i].degree()/denom);
    }
  }
//+++++++++++++++++++

}
