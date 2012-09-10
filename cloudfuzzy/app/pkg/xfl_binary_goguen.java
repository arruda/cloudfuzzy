//+++++++++++++++++++++++++++++++++++++++++++++++++++++
// File automatically generated by Jfuzzy - DO NOT EDIT
//+++++++++++++++++++++++++++++++++++++++++++++++++++++

package pkg;

import xfuzzy.lang.*;

public class xfl_binary_goguen extends Binary {
 public xfl_binary_goguen() {
   super.parameter = new Parameter[0];
   super.name = "goguen";
   super.pkg = "xfl";
  }

 public double compute(double a, double b) {
 return (a<b? 1 : b/a); 
  }

 public double[] derivative(double a, double b) {
   double[] deriv = new double[2];
  deriv[0] = (a>b? -b/(a*a) : (a==b? -0.5/a : 0));
  deriv[1] = (a>b? 1/a : (a==b? 0.5/a : 0));
   return deriv;
  }

 public boolean test () {
   return true;
  }

 public String toJava(String name) {
   String eol = System.getProperty("line.separator", "\n");
   String code = "  double "+name+"(double a, double b) {"+eol;
   code += "    return (a<b? 1 : b/a); "+eol;
   code += "  }"+eol;
   return code;
  }

 public String toC(String name) {
   String eol = System.getProperty("line.separator", "\n");
   String code = "static double "+name+"(double a, double b) {"+eol;
   code += "  return (a<b? 1 : b/a); "+eol;
   code += "}"+eol;
   return code;
  }

 public String toCpp(String name) {
   String eol = System.getProperty("line.separator", "\n");
   String code = "double "+name+"(double a, double b) {"+eol;
   code += "  return (a<b? 1 : b/a); "+eol;
   code += "}"+eol;
   return code;
  }
}
