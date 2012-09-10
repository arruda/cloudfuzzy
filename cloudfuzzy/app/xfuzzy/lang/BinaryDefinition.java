package xfuzzy.lang;

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
//		DEFINICION DE UNA OPERACION BINARIA		//
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

public  class BinaryDefinition extends Definition {
 private String compute;
 private String c_compute;
 private String cpp_compute;
 private String deriv;

 public BinaryDefinition(String pkg, String name) {
  super(pkg, name);
 }

 public int getKind() { return XflPackage.BINARY; }

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
  String code = "binary "+name+" {"+eol;
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

 protected String classname() { return pkg+"_binary_"+name; }

 protected String class_code() {
  return "public class "+classname()+" extends Binary {"+eol;
 }

 protected String compute_code() {
  String code = "";
  if(this.compute != null) {
   code += eol+" public double compute(double a, double b) {"+eol;
   code += variable_code();
   code += this.compute+eol;
   code += "  }"+eol;
  }
  if(this.deriv != null) {
   code += eol+" public double[] derivative(double a, double b) {"+eol;
   code += "   double[] deriv = new double[2];"+eol;
   code += variable_code();
   code += this.deriv+eol;
   code += "   return deriv;"+eol;
   code += "  }"+eol;
  }
  return code;
 }

 protected String java_code() {
  String javacode = eol+" public String toJava(String name) {"+eol;
  javacode += "   String eol = System.getProperty";
  javacode += "(\"line.separator\", \"\\n\");"+eol;
  javacode += "   String code = \"  double \"+name+\"";
  javacode += "(double a, double b) {\"+eol;"+eol;

  for(int i=0; i<param.size(); i++) {
   javacode += "  code += \"   double "+param.elementAt(i);
   javacode += " = \"+parameter["+i+"].value+\";\"+eol;"+eol;
  }

  String sampled[] = super.sample(compute);
  for(int i=0; i<sampled.length; i++)
   if(sampled[i].trim().length() > 0)
    javacode += "   code += \"   "+sampled[i]+"\"+eol;"+eol;
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
  ccode += "(double a, double b) {\"+eol;"+eol;

  for(int i=0; i<param.size(); i++) {
   ccode += "  code += \" double "+param.elementAt(i);
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
  ccode += "(double a, double b) {\"+eol;"+eol;

  for(int i=0; i<param.size(); i++) {
   ccode += "  code += \" double "+param.elementAt(i);
   ccode += " = \"+parameter["+i+"].value+\";\"+eol;"+eol;
  }

  String sampled[] = super.sample(cpp_compute);
  for(int i=0; i<sampled.length; i++)
   if(sampled[i].trim().length() > 0)
    ccode += "   code += \" "+sampled[i]+"\"+eol;"+eol;
  ccode += "   code += \"}\"+eol;"+eol;
  ccode += "   return code;"+eol;
  ccode += "  }"+eol;
  return ccode;
 }

 private String variable_code() {
  String code = "";
  for(int i=0; i<param.size(); i++)
   code += "   double "+param.elementAt(i)+" = parameter["+i+"].value;"+eol;
  return code;
 }
}

