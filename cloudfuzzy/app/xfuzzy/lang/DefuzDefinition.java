package xfuzzy.lang;

import java.util.Vector;

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
//	    DEFINICION DE UN METODO DE DEFUZZIFICACION		//
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

public  class DefuzDefinition extends Definition {
 private Vector defined = new Vector();
 private String compute;
 private String c_compute;
 private String cpp_compute;
 private String deriv;

 public DefuzDefinition(String pkg, String name) {
  super(pkg, name);
 }

 public int getKind() { return XflPackage.DEFUZ; }

 public String getCode(int kind) {
  switch(kind) {
   case REQUIREMENTS: return super.requires;
   case SOURCE: return super.other;
   case JAVA_EQUAL: return this.compute;
   case C_EQUAL: return this.c_compute;
   case CPP_EQUAL: return this.cpp_compute;
   case DERIV_EQUAL: return this.deriv;
   default: return null;
  }
 }

 public void setCode(int kind, String code) {
  switch(kind) {
   case REQUIREMENTS: super.requires = code; break;
   case SOURCE: super.other = code; break;
   case JAVA_EQUAL: this.compute = code; break;
   case C_EQUAL: this.c_compute = code; break;
   case CPP_EQUAL: this.cpp_compute = code; break;
   case DERIV_EQUAL: this.deriv = code; break;
  }
 }

 public String toPkg() {
  String code = "defuz "+name+" {"+eol;
  code += super.aliasblock();
  code += super.paramblock();
  code += super.requiresblock();
  if(this.compute != null && this.compute.length() >0) 
   code += " java {"+eol+this.compute+eol+"  }"+eol;
  if(this.c_compute != null && this.c_compute.length() >0) 
   code += " ansi_c {"+eol+this.c_compute+eol+"  }"+eol;
  if(this.cpp_compute != null && this.cpp_compute.length() >0)
   code += " cplusplus {"+eol+this.cpp_compute+eol+"  }"+eol;
  if(this.deriv != null && this.deriv.length() >0)
   code += " derivative {"+eol+this.deriv+eol+"  }"+eol;
  code += super.otherblock();
  code += " }"+eol+eol;
  return code;
 }

 public void setDefinedFor(Vector defined) { this.defined = defined; }

 public Vector getDefinedFor() { return this.defined; }

 protected String classname() { return pkg+"_defuz_"+name; }

 protected String class_code() {
  return "public class "+classname()+" extends DefuzMethod {"+eol;
 }

 protected String defined_code() {
  String code = eol+" public boolean test(AggregateMemFunc mf) {"+eol;
  if(defined.size() == 0) code += "   return true;"+eol;
  else {
   code += "   for(int i=0; i<mf.conc.length; i++) {"+eol;
   code += "     ParamMemFunc pmf = mf.conc[i].getMF();"+eol;
   code += "     if(!(pmf instanceof ";
   code += toInstanceName(defined.elementAt(0))+")"+eol;
   for(int i=1; i<defined.size(); i++) {
    code += "       && !(pmf instanceof ";
    code += toInstanceName(defined.elementAt(i))+")"+eol;
   }
   code += "       ) return false;"+eol;
   code += "    }"+eol;
   code += "   return true;"+eol;
  }
  code += "  }"+eol;
  return code;
 }

 protected String compute_code() {
  if(this.compute == null) return "";
  String code = eol+" public double compute(AggregateMemFunc mf) {"+eol;
  code += variable_code(this.compute);
  code += this.compute+eol;
  code += "  }"+eol;
  return code;
 }

 protected String java_code() {
  String javacode = eol+" public String toJava(String name) {"+eol;
  javacode += "   String eol = System.getProperty";
  javacode += "(\"line.separator\", \"\\n\");"+eol;
  javacode += "   String code = \" double \"+name+\"";
  javacode += "(OutputMembershipFunction mf) {\"+eol;"+eol;

  if(compute.indexOf("min")!=-1)
   javacode += "   code += \"   double min = mf.min();\"+eol;"+eol;
  if(compute.indexOf("max")!=-1)
   javacode += "   code += \"   double max = mf.max();\"+eol;"+eol;
  if(compute.indexOf("step")!=-1)
   javacode += "   code += \"   double step = mf.step();\"+eol;"+eol;
  for(int i=0; i<param.size(); i++) {
   javacode += "   code += \"   double "+param.elementAt(i);
   javacode += " = \"+parameter["+i+"].value+\";\"+eol;"+eol;
  }

  String sampled[] = super.sample(compute);
  for(int i=0; i<sampled.length; i++)
   if(sampled[i].trim().length() > 0)
    javacode += "   code += \""+sampled[i]+"\"+eol;"+eol;
  javacode += "   code += \"  }\"+eol;"+eol;
  javacode += "   return code;"+eol;
  javacode += "  }"+eol;
  return javacode;
 }

 protected String c_code() {
  if(c_compute == null || c_compute.length() == 0 ) return super.c_code();
  String ccode = eol+" public String toC(String name) {"+eol;
  ccode += "   String eol = System.getProperty";
  ccode += "(\"line.separator\", \"\\n\");"+eol;
  ccode += "   String code = \"static double \"+name+\"";
  ccode += "(FuzzyNumber mf) {\"+eol;"+eol;
  if(c_compute.indexOf("min")!=-1)
   ccode += "   code += \" double min = mf.conc[0].min;\"+eol;"+eol;
  if(c_compute.indexOf("max")!=-1)
   ccode += "   code += \" double max = mf.conc[0].max;\"+eol;"+eol;
  if(c_compute.indexOf("step")!=-1)
   ccode += "   code += \" double step = mf.conc[0].step;\"+eol;"+eol;
  for(int i=0; i<param.size(); i++) {
   ccode += "   code += \" double "+param.elementAt(i);
   ccode += " = \"+parameter["+i+"].value+\";\"+eol;"+eol;
  }

  String sampled[] = super.sample(c_compute);
  for(int i=0; i<sampled.length; i++)
   if(sampled[i].trim().length() > 0)
    ccode += "   code += \" "+sampled[i]+"\"+eol;"+eol;
  ccode += "   code += \"}\"+eol;"+eol;
  ccode += "   return code;"+eol;
  ccode += "  }"+eol;
  return ccode;
 }

 protected String cpp_code() {
  if(cpp_compute==null || cpp_compute.length()==0) return super.cpp_code();
  String ccode = eol+" public String toCpp(String name) {"+eol;
  ccode += "   String eol = System.getProperty";
  ccode += "(\"line.separator\", \"\\n\");"+eol;
  ccode += "   String code = \"double \"+name+\"";
  ccode += "(OutputMembershipFunction &mf) {\"+eol;"+eol;
  if(cpp_compute.indexOf("min")!=-1)
   ccode += "   code += \" double min = mf.min();\"+eol;"+eol;
  if(cpp_compute.indexOf("max")!=-1)
   ccode += "   code += \" double max = mf.max();\"+eol;"+eol;
  if(cpp_compute.indexOf("step")!=-1)
   ccode += "   code += \" double step = mf.step();\"+eol;"+eol;
  for(int i=0; i<param.size(); i++) {
   ccode += "   code += \" double "+param.elementAt(i);
   ccode += " = \"+parameter["+i+"].value+\";\"+eol;"+eol;
  }

  String sampled[] = super.sample(cpp_compute);
  for(int i=0; i<sampled.length; i++)
   if(sampled[i].trim().length() > 0)
    ccode += "   code += \" "+sampled[i]+"\"+eol;"+eol;
  ccode += "   code += \"}\"+eol+eol;"+eol;
  ccode += "   return code;"+eol;
  ccode += "  }"+eol;
  return ccode;
 }

 private String toInstanceName(Object ob) {
  String name = (String) ob;
  int index = name.indexOf(".");
  if(index==-1) return "pkg."+super.pkg+"_mf_"+name;
  return "pkg."+name.substring(0,index)+"_mf_"+name.substring(index+1);
 }

 private String variable_code(String source) {
  String code = "";
  if(source.indexOf("min")!=-1) code += "   double min = mf.min();"+eol;
  if(source.indexOf("max")!=-1) code += "   double max = mf.max();"+eol;
  if(source.indexOf("step")!=-1) code += "   double step = mf.step();"+eol;
  for(int i=0; i<param.size(); i++)
   code += "   double "+param.elementAt(i)+" = parameter["+i+"].value;"+eol;
  return code;
 }
}

