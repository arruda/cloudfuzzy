//+++++++++++++++++++++++++++++++++++++++++++++++++++++
// File automatically generated by Jfuzzy - DO NOT EDIT
//+++++++++++++++++++++++++++++++++++++++++++++++++++++

package pkg;

import xfuzzy.lang.*;

public class xfl_binary_zadeh extends Binary {
 public xfl_binary_zadeh() {
   super.parameter = new Parameter[0];
   super.name = "zadeh";
   super.pkg = "xfl";
  }

 public double compute(double a, double b) {
 return (a<0.5 || 1-a>b? 1-a : (a<b? a : b)); 
  }

 public boolean test () {
   return true;
  }

 public String toJava(String name) {
   String eol = System.getProperty("line.separator", "\n");
   String code = "  double "+name+"(double a, double b) {"+eol;
   code += "    return (a<0.5 || 1-a>b? 1-a : (a<b? a : b)); "+eol;
   code += "  }"+eol;
   return code;
  }

 public String toC(String name) {
   String eol = System.getProperty("line.separator", "\n");
   String code = "static double "+name+"(double a, double b) {"+eol;
   code += "  return (a<0.5 || 1-a>b? 1-a : (a<b? a : b)); "+eol;
   code += "}"+eol;
   return code;
  }

 public String toCpp(String name) {
   String eol = System.getProperty("line.separator", "\n");
   String code = "double "+name+"(double a, double b) {"+eol;
   code += "  return (a<0.5 || 1-a>b? 1-a : (a<b? a : b)); "+eol;
   code += "}"+eol;
   return code;
  }
}
