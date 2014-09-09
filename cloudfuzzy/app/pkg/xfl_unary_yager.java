//+++++++++++++++++++++++++++++++++++++++++++++++++++++
// File automatically generated by Jfuzzy - DO NOT EDIT
//+++++++++++++++++++++++++++++++++++++++++++++++++++++

package pkg;

import xfuzzy.lang.*;

public class xfl_unary_yager extends Unary {
 public xfl_unary_yager() {
   super.parameter = new Parameter[1];
   super.parameter[0] = new Parameter("w");
   super.name = "yager";
   super.pkg = "xfl";
  }

 public double compute(double a) {
   double w = parameter[0].value;
 return Math.pow( ( 1 - Math.pow(a,w) ) , 1/w ); 
  }

 public double derivative(double a) {
   double deriv;
   double w = parameter[0].value;
 deriv = - Math.pow( Math.pow(a,-w) -1, (1-w)/w ); 
   return deriv;
  }

 public boolean test () {
   double w = parameter[0].value;
   return ( w>0 );
  }

 public String toJava(String name) {
   String eol = System.getProperty("line.separator", "\n");
   String code = "  double "+name+"(double a) {"+eol;
  code += "   double w = "+parameter[0].value+";"+eol;
   code += "    return Math.pow( ( 1 - Math.pow(a,w) ) , 1/w ); "+eol;
   code += "  }"+eol;
   return code;
  }

 public String toC(String name) {
   String eol = System.getProperty("line.separator", "\n");
   String code = "static double "+name+"(double a) {"+eol;
  code += " double w = "+parameter[0].value+";"+eol;
   code += "  return pow( ( 1 - pow(a,w) ) , 1/w ); "+eol;
   code += "}"+eol;
   return code;
  }

 public String toCpp(String name) {
   String eol = System.getProperty("line.separator", "\n");
   String code = "double "+name+"(double a) {"+eol;
  code += " double w = "+parameter[0].value+";"+eol;
   code += "  return pow( ( 1 - pow(a,w) ) , 1/w ); "+eol;
   code += "}"+eol+eol;
   return code;
  }
}