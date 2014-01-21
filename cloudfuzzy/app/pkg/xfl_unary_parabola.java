//+++++++++++++++++++++++++++++++++++++++++++++++++++++
// File automatically generated by Jfuzzy - DO NOT EDIT
//+++++++++++++++++++++++++++++++++++++++++++++++++++++

package pkg;

import xfuzzy.lang.*;

public class xfl_unary_parabola extends Unary {
 public xfl_unary_parabola() {
   super.parameter = new Parameter[0];
   super.name = "parabola";
   super.pkg = "xfl";
  }

 public double compute(double a) {
 return 4*a*(1-a); 
  }

 public double derivative(double a) {
   double deriv;
 deriv = 4-8*a; 
   return deriv;
  }

 public boolean test () {
   return true;
  }

 public String toJava(String name) {
   String eol = System.getProperty("line.separator", "\n");
   String code = "  double "+name+"(double a) {"+eol;
   code += "    return 4*a*(1-a); "+eol;
   code += "  }"+eol;
   return code;
  }

 public String toC(String name) {
   String eol = System.getProperty("line.separator", "\n");
   String code = "static double "+name+"(double a) {"+eol;
   code += "  return 4*a*(1-a); "+eol;
   code += "}"+eol;
   return code;
  }

 public String toCpp(String name) {
   String eol = System.getProperty("line.separator", "\n");
   String code = "double "+name+"(double a) {"+eol;
   code += "  return 4*a*(1-a); "+eol;
   code += "}"+eol+eol;
   return code;
  }
}