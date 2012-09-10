package xfuzzy.lang;

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
//	     DEFINICION DE UNA FUNCION DE PERTENENCIA		//
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

public  class MFDefinition extends Definition {
 private String java_equal;
 private String java_greq;
 private String java_smeq;
 private String java_center;
 private String java_basis;
 private String deriv_equal;
 private String deriv_greq;
 private String deriv_smeq;
 private String deriv_center;
 private String deriv_basis;
 private String ansic_equal;
 private String ansic_greq;
 private String ansic_smeq;
 private String ansic_center;
 private String ansic_basis;
 private String cpp_equal;
 private String cpp_greq;
 private String cpp_smeq;
 private String cpp_center;
 private String cpp_basis;

 public MFDefinition(String pkg, String name) {
  super(pkg, name);
 }

 public int getKind() { return XflPackage.MFUNC; }

 public String getCode(int kind) {
  switch(kind) {
   case REQUIREMENTS: return super.requires;
   case SOURCE: return super.other;
   case JAVA_EQUAL: return this.java_equal;
   case JAVA_GREQ: return this.java_greq;
   case JAVA_SMEQ: return this.java_smeq;
   case JAVA_CENTER: return this.java_center;
   case JAVA_BASIS: return this.java_basis;
   case DERIV_EQUAL: return this.deriv_equal;
   case DERIV_GREQ: return this.deriv_greq;
   case DERIV_SMEQ: return this.deriv_smeq;
   case DERIV_CENTER: return this.deriv_center;
   case DERIV_BASIS: return this.deriv_basis;
   case C_EQUAL: return this.ansic_equal;
   case C_GREQ: return this.ansic_greq;
   case C_SMEQ: return this.ansic_smeq;
   case C_CENTER: return this.ansic_center;
   case C_BASIS: return this.ansic_basis;
   case CPP_EQUAL: return this.cpp_equal;
   case CPP_GREQ: return this.cpp_greq;
   case CPP_SMEQ: return this.cpp_smeq;
   case CPP_CENTER: return this.cpp_center;
   case CPP_BASIS: return this.cpp_basis;
   default: return null;
  }
 }

 public void setCode(int kind, String code) {
  switch(kind) {
   case REQUIREMENTS: super.requires = code; break;
   case SOURCE: super.other = code; break;
   case JAVA_EQUAL: this.java_equal = code; break;
   case JAVA_GREQ: this.java_greq = code; break;
   case JAVA_SMEQ: this.java_smeq = code; break;
   case JAVA_CENTER: this.java_center = code; break;
   case JAVA_BASIS: this.java_basis = code; break;
   case DERIV_EQUAL: this.deriv_equal = code; break;
   case DERIV_GREQ: this.deriv_greq = code; break;
   case DERIV_SMEQ: this.deriv_smeq = code; break;
   case DERIV_CENTER: this.deriv_center = code; break;
   case DERIV_BASIS: this.deriv_basis = code; break;
   case C_EQUAL: this.ansic_equal = code; break;
   case C_GREQ: this.ansic_greq = code; break;
   case C_SMEQ: this.ansic_smeq = code; break;
   case C_CENTER: this.ansic_center = code; break;
   case C_BASIS: this.ansic_basis = code; break;
   case CPP_EQUAL: this.cpp_equal = code; break;
   case CPP_GREQ: this.cpp_greq = code; break;
   case CPP_SMEQ: this.cpp_smeq = code; break;
   case CPP_CENTER: this.cpp_center = code; break;
   case CPP_BASIS: this.cpp_basis = code; break;
  }
 }

 public String toPkg() {
  boolean javacoded, derivcoded;
  String code = "mf "+name+" {"+eol;
  code += super.aliasblock();
  code += super.paramblock();
  code += super.requiresblock();
  code += " java {"+eol;
  if(this.java_equal != null && this.java_equal.length()>0)
   code += "   equal {"+eol+this.java_equal+eol+"    }"+eol;
  if(this.java_greq != null && this.java_greq.length()>0)
   code += "   greatereq {"+eol+this.java_greq+eol+"    }"+eol;
  if(this.java_smeq != null && this.java_smeq.length()>0)
   code += "   smallereq {"+eol+this.java_smeq+eol+"    }"+eol;
  if(this.java_center != null && this.java_center.length()>0)
   code += "   center {"+eol+this.java_center+eol+"    }"+eol;
  if(this.java_basis != null && this.java_basis.length()>0)
   code += "   basis {"+eol+this.java_basis+eol+"    }"+eol;
  code += "  }"+eol;
  code += " derivative {"+eol;
  if(this.deriv_equal != null && this.deriv_equal.length()>0)
   code += "   equal {"+eol+this.deriv_equal+eol+"    }"+eol;
  if(this.deriv_greq != null && this.deriv_greq.length()>0)
   code += "   greatereq {"+eol+this.deriv_greq+eol+"    }"+eol;
  if(this.deriv_smeq != null && this.deriv_smeq.length()>0)
   code += "   smallereq {"+eol+this.deriv_smeq+eol+"    }"+eol;
  if(this.deriv_center != null && this.deriv_center.length()>0)
   code += "   center {"+eol+this.deriv_center+eol+"    }"+eol;
  if(this.deriv_basis != null && this.deriv_basis.length()>0)
   code += "   basis {"+eol+this.deriv_basis+eol+"    }"+eol;
  code += "  }"+eol;
  code += " ansi_c {"+eol;
  if(this.ansic_equal != null && this.ansic_equal.length()>0)
   code += "   equal {"+eol+this.ansic_equal+eol+"    }"+eol;
  if(this.ansic_greq != null && this.ansic_greq.length()>0)
   code += "   greatereq {"+eol+this.ansic_greq+eol+"    }"+eol;
  if(this.ansic_smeq != null && this.ansic_smeq.length()>0)
   code += "   smallereq {"+eol+this.ansic_smeq+eol+"    }"+eol;
  if(this.ansic_center != null && this.ansic_center.length()>0)
   code += "   center {"+eol+this.ansic_center+eol+"    }"+eol;
  if(this.ansic_basis != null && this.ansic_basis.length()>0)
   code += "   basis {"+eol+this.ansic_basis+eol+"    }"+eol;
  code += "  }"+eol;
  code += " cplusplus {"+eol;
  if(this.cpp_equal != null && this.cpp_equal.length()>0)
   code += "   equal {"+eol+this.cpp_equal+eol+"    }"+eol;
  if(this.cpp_greq != null && this.cpp_greq.length()>0)
   code += "   greatereq {"+eol+this.cpp_greq+eol+"    }"+eol;
  if(this.cpp_smeq != null && this.cpp_smeq.length()>0)
   code += "   smallereq {"+eol+this.cpp_smeq+eol+"    }"+eol;
  if(this.cpp_center != null && this.cpp_center.length()>0)
   code += "   center {"+eol+this.cpp_center+eol+"    }"+eol;
  if(this.cpp_basis != null && this.cpp_basis.length()>0)
   code += "   basis {"+eol+this.cpp_basis+eol+"    }"+eol;
  code += "  }"+eol;
  code += super.otherblock();
  code += " }"+eol+eol;
  return code;
 }

 protected String classname() { return pkg+"_mf_"+name; }

 protected String class_code() {
  return "public class "+classname()+" extends ParamMemFunc {"+eol;
 }

 protected String compute_code() {
  String code = "";

  if(this.java_equal != null) {
   code += eol+" public double compute(double x) {"+eol;
   code += variable_code(this.java_equal);
   code += this.java_equal+eol;
   code += "  }"+eol;
  }

  if(this.java_greq != null) {
   code += eol+" public double greatereq(double x) {"+eol;
   code += variable_code(this.java_greq);
   code += this.java_greq+eol;
   code += "  }"+eol;
  }

  if(this.java_smeq != null) {
   code += eol+" public double smallereq(double x) {"+eol;
   code += variable_code(this.java_smeq);
   code += this.java_smeq+eol;
   code += "  }"+eol;
  }

  if(this.java_center != null) {
   code += eol+" public double center() {"+eol;
   code += variable_code(this.java_center);
   code += this.java_center+eol;
   code += "  }"+eol;
  }

  if(this.java_basis != null) {
   code += eol+" public double basis() {"+eol;
   code += variable_code(this.java_basis);
   code += this.java_basis+eol;
   code += "  }"+eol;
  }

  if(this.deriv_equal != null) {
   code += eol+" public double[] deriv_eq(double x) {"+eol;
   code += "   double[] deriv = new double["+param.size()+"];"+eol;
   code += variable_code(this.deriv_equal);
   code += this.deriv_equal+eol;
   code += "   return deriv;"+eol;
   code += "  }"+eol;
  }

  if(this.deriv_greq != null) {
   code += eol+" public double[] deriv_greq(double x) {"+eol;
   code += "   double[] deriv = new double["+param.size()+"];"+eol;
   code += variable_code(this.deriv_greq);
   code += this.deriv_greq+eol;
   code += "   return deriv;"+eol;
   code += "  }"+eol;
  }

  if(this.deriv_smeq != null) {
   code += eol+" public double[] deriv_smeq(double x) {"+eol;
   code += "   double[] deriv = new double["+param.size()+"];"+eol;
   code += variable_code(this.deriv_smeq);
   code += this.deriv_smeq+eol;
   code += "   return deriv;"+eol;
   code += "  }"+eol;
  }

  if(this.deriv_center != null) {
   code += eol+" public double[] deriv_center() {"+eol;
   code += "   double[] deriv = new double["+param.size()+"];"+eol;
   code += variable_code(this.deriv_center);
   code += this.deriv_center+eol;
   code += "   return deriv;"+eol;
   code += "  }"+eol;
  }

  if(this.deriv_basis != null) {
   code += eol+" public double[] deriv_basis() {"+eol;
   code += "   double[] deriv = new double["+param.size()+"];"+eol;
   code += variable_code(this.deriv_basis);
   code += this.deriv_basis+eol;
   code += "   return deriv;"+eol;
   code += "  }"+eol;
  }

  return code;
 }

 protected String test_code() {
  String code = eol+" public boolean test () {"+eol;
  if( super.requires == null ) code += "   return true;"+eol;
  else {
   code += variable_code(super.requires);
   code += "   return ("+super.requires+");"+eol;
  }
  code += "  }"+eol;
  return code;
 }

 protected String java_code() {
  String [] sampled;
  String javacode = eol+" public String toJava() {"+eol;
  javacode += "   String eol = System.getProperty";
  javacode += "(\"line.separator\", \"\\n\");"+eol;
  javacode += "   String code = \"\";"+eol;
  javacode += "   code += \"  private class MF_"+pkg+"_"+name;
  javacode += " extends InnerMembershipFunction {\"+eol;"+eol;
  for(int i=0; i<param.size(); i++) 
   javacode += "   code += \"   double "+param.elementAt(i)+";\"+eol;"+eol;

  javacode += "   code += eol+\"   MF_"+pkg+"_"+name;
  javacode += "( double min, double max, double step, double param[])";
  javacode += "{\"+eol;"+eol;
  javacode += "   code += \"    super.min = min;\"+eol;"+eol;
  javacode += "   code += \"    super.max = max;\"+eol;"+eol;
  javacode += "   code += \"    super.step = step;\"+eol;"+eol;
  for(int i=0; i<param.size(); i++) {
   javacode += "   code += \"    this."+param.elementAt(i);
   javacode += " = param["+i+"];\"+eol;"+eol;
  }
  javacode += "   code += \"   }\"+eol;"+eol;

  javacode += "   code += \"   double param(int _i) {\"+eol;"+eol;
  javacode += "   code += \"    switch(_i) {\"+eol;"+eol;
  for(int i=0; i<param.size(); i++) {
   javacode += "   code += \"     case "+i+": return ";
   javacode += param.elementAt(i)+";\"+eol;"+eol;
  }
  javacode += "   code += \"     default: return 0;\"+eol;"+eol;
  javacode += "   code += \"    }\"+eol;"+eol;
  javacode += "   code += \"   }\"+eol;"+eol;

  javacode += "   code += \"   double isEqual(double x) {\"+eol;"+eol;
  sampled = super.sample(java_equal);
  for(int i=0; i<sampled.length; i++) if(sampled[i].trim().length() > 0)
   javacode += "   code += \"   "+sampled[i]+"\"+eol;"+eol;
  javacode += "   code += \"   }\"+eol;"+eol;

  if(this.java_greq != null && this.java_greq.length()>0) {
   javacode += "   code += \"   double isGreaterOrEqual(double x) {\"+eol;";
   javacode += eol;
   sampled = super.sample(java_greq);
   for(int i=0; i<sampled.length; i++) if(sampled[i].trim().length() > 0)
    javacode += "   code += \"   "+sampled[i]+"\"+eol;"+eol;
   javacode += "   code += \"   }\"+eol;"+eol;
  }

  if(this.java_smeq != null && this.java_smeq.length()>0) {
   javacode += "   code += \"   double isSmallerOrEqual(double x) {\"+eol;";
   javacode += eol;
   sampled = super.sample(java_smeq);
   for(int i=0; i<sampled.length; i++) if(sampled[i].trim().length() > 0)
    javacode += "   code += \"   "+sampled[i]+"\"+eol;"+eol;
   javacode += "   code += \"   }\"+eol;"+eol;
  }

  if(this.java_center != null && this.java_center.length()>0) {
   javacode += "   code += \"   double center() {\"+eol;";
   javacode += eol;
   sampled = super.sample(java_center);
   for(int i=0; i<sampled.length; i++) if(sampled[i].trim().length() > 0)
    javacode += "   code += \"   "+sampled[i]+"\"+eol;"+eol;
   javacode += "   code += \"   }\"+eol;"+eol;
  }

  if(this.java_basis != null && this.java_basis.length()>0) {
   javacode += "   code += \"   double basis() {\"+eol;";
   javacode += eol;
   sampled = super.sample(java_basis);
   for(int i=0; i<sampled.length; i++) if(sampled[i].trim().length() > 0)
    javacode += "   code += \"   "+sampled[i]+"\"+eol;"+eol;
   javacode += "   code += \"   }\"+eol;"+eol;
  }

  javacode += "   code += \"  }\"+eol;"+eol;
  javacode += "   return code;"+eol;
  javacode += "  }"+eol;
  return javacode;
 }

 protected String c_code() {
  if(ansic_equal==null || ansic_equal.length()==0) {
   String ccode = eol+" public String toC() throws XflException{"+eol;
   ccode += "  throw new XflException();"+eol;
   ccode += " }";
   return ccode;
  }
  String [] sampled;
  String ccode = eol+" public String toC() {"+eol;
  ccode += "   String eol = System.getProperty";
  ccode += "(\"line.separator\", \"\\n\");"+eol;
  ccode += "   String code = \"\";"+eol;
  ccode += c_function_code("MF_"+pkg+"_"+name+"_equal", ansic_equal, true);
  if(this.ansic_greq != null && this.ansic_greq.length()>0)
   ccode += c_function_code("MF_"+pkg+"_"+name+"_greq", ansic_greq, true);
  if(this.ansic_smeq != null && this.ansic_smeq.length()>0)
   ccode += c_function_code("MF_"+pkg+"_"+name+"_smeq", ansic_smeq, true);
  if(this.ansic_center != null && this.ansic_center.length()>0)
   ccode +=c_function_code("MF_"+pkg+"_"+name+"_center",ansic_center,false);
  if(this.ansic_basis != null && this.ansic_basis.length()>0)
   ccode += c_function_code("MF_"+pkg+"_"+name+"_basis", ansic_basis,false);
  ccode += c_create_code();
  ccode += "   return code;"+eol;
  ccode += "  }"+eol;
  return ccode;
 }

 private String c_create_code() {
  String ccode = "";
  ccode += "   code += \"static MembershipFunction createMF_"+pkg+"_"+name;
  ccode += "( double min, double max, double step,";
  ccode += " double *param,int length) {\"+eol;"+eol;
  ccode += "   code += \" int i;\"+eol;"+eol;
  ccode += "   code += \" MembershipFunction _mf;\"+eol;"+eol;
  ccode += "   code += \" _mf.min = min;\"+eol;"+eol;
  ccode += "   code += \" _mf.max = max;\"+eol;"+eol;
  ccode += "   code += \" _mf.step = step;\"+eol;"+eol;
  ccode += "   code += \" _mf.param = ";
  ccode += "(double*) malloc(length*sizeof(double));\"+eol;"+eol;
  ccode += "   code += \" for(i=0;i<length;i++)";
  ccode += " _mf.param[i] = param[i];\"+eol;"+eol;
  ccode += "   code += \" _mf.compute_eq = ";
  ccode += "MF_"+pkg+"_"+name+"_equal;\"+eol;"+eol;
  ccode += "   code += \" _mf.compute_greq = ";
  if(this.ansic_greq != null && this.ansic_greq.length()>0) 
   ccode += "MF_"+pkg+"_"+name+"_greq;\"+eol;"+eol;
  else ccode += "_defaultMFcompute_greq;\"+eol;"+eol;
     
  ccode += "   code += \" _mf.compute_smeq = ";
  if(this.ansic_smeq != null && this.ansic_smeq.length()>0) 
   ccode += "MF_"+pkg+"_"+name+"_smeq;\"+eol;"+eol;
  else ccode += "_defaultMFcompute_smeq;\"+eol;"+eol;

  ccode += "   code += \" _mf.center = ";
  if(this.ansic_center != null && this.ansic_center.length()>0)
   ccode += "MF_"+pkg+"_"+name+"_center;\"+eol;"+eol;
  else ccode += "_defaultMFcenter;\"+eol;"+eol;
     
  ccode += "   code += \" _mf.basis = ";
  if(this.ansic_basis != null && this.ansic_basis.length()>0)
   ccode += "MF_"+pkg+"_"+name+"_basis;\"+eol;"+eol;
  else ccode += "_defaultMFbasis;\"+eol;"+eol;
    
  ccode += "   code += \" return _mf;\"+eol;"+eol;
  ccode += "   code += \"}\"+eol;"+eol;
  return ccode;
 }

 private String c_function_code(String name, String source, boolean hasx) {
  String ccode = "";
  ccode += "   code += \"static double "+name;
  if(hasx) ccode += "(MembershipFunction _mf,double x) {\"+eol;"+eol;
  else ccode += "(MembershipFunction _mf) {\"+eol;"+eol;
  if(source.indexOf("min")!=-1)
   ccode += "   code += \" double min = _mf.min;\"+eol;"+eol;
  if(source.indexOf("max")!=-1)
   ccode += "   code += \" double max = _mf.max;\"+eol;"+eol;
  if(source.indexOf("step")!=-1)
   ccode += "   code += \" double step = _mf.step;\"+eol;"+eol;
  for(int i=0; i<param.size(); i++) {
   ccode += "   code += \" double "+param.elementAt(i);
   ccode += " = _mf.param["+i+"];\"+eol;"+eol;
  }
  String sampled[] = super.sample(source);
  for(int i=0; i<sampled.length; i++) if(sampled[i].trim().length() > 0)
   ccode += "   code += \" "+sampled[i]+"\"+eol;"+eol;
  ccode += "   code += \"}\"+eol;"+eol;
  return ccode;
 }

 private String variable_code(String source) {
  String code = "";
  if(source.indexOf("min")!=-1) code += "   double min = this.u.min();"+eol;
  if(source.indexOf("max")!=-1) code += "   double max = this.u.max();"+eol;
  for(int i=0; i<param.size(); i++)
   code += "   double "+param.elementAt(i)+" = parameter["+i+"].value;"+eol;
  return code;
 }

 protected String cpp_code() {
  if(cpp_equal==null || cpp_equal.length()==0) {
   String ccode = "";
   ccode += " public String toCpp(String name) throws XflException{"+eol;
   ccode += "  throw new XflException();"+eol;
   ccode += " }"+eol+eol;
   ccode += " public String toHpp(String name) throws XflException{"+eol;
   ccode += "  throw new XflException();"+eol;
   ccode += " }"+eol+eol;
   return ccode;
  }
  String [] sampled;
  String ccode = " public String toCpp(String name) {"+eol;
  ccode += "   String eol = System.getProperty";
  ccode += "(\"line.separator\", \"\\n\");"+eol;
  ccode += "   String code = \"\";"+eol;

  ccode +="   code += name+\"::\"+name+\"(double min,double max,double step";
  ccode += ",double *param, int length) :\"+eol;"+eol;
  ccode += "   code += \"ParamMembershipFunction(min,max,step) {\"+eol;"+eol;
  ccode += "   code += \" this->name = \\\"\"+name+\"\\\";\"+eol;"+eol;
  for(int i=0; i<param.size(); i++) {
   ccode += "   code += \" this->"+param.elementAt(i)+" = ";
   ccode += "param["+i+"];\"+eol;"+eol;
  }
  ccode += "   code += \"}\"+eol+eol;"+eol;

  ccode +="   code += name+\" * \"+name+\"::dup() {\"+eol;"+eol;
  ccode +="   code += \" double param["+param.size()+"] = {";
  for(int i=0; i<param.size(); i++) ccode += (i==0? "":",")+param.elementAt(i);
  ccode += "};\"+eol;"+eol;
  ccode +="   code += \" return new \"+name+\"(min,max,step";
  ccode += ",param,"+param.size()+");\"+eol;"+eol;
  ccode += "   code += \"}\"+eol+eol;"+eol;

  ccode += "   code += \"double \"+name+\"::param(int _i) {\"+eol;"+eol;
  ccode += "   code += \" switch(_i) {\"+eol;"+eol;
  for(int i=0; i<param.size(); i++) {
   ccode += "   code += \"  case "+i+":";
   ccode += " return "+param.elementAt(i)+";\"+eol;"+eol;
  }
  ccode += "   code += \"  default: return 0;\"+eol;"+eol;
  ccode += "   code += \" }\"+eol;"+eol;
  ccode += "   code += \"}\"+eol+eol;"+eol;

  ccode += "   code += \"double \"+name+\"::compute_eq";
  ccode += "(double x) {\"+eol;"+eol;
  sampled = super.sample(cpp_equal);
  for(int i=0; i<sampled.length; i++) if(sampled[i].trim().length() > 0)
   ccode += "   code += \" "+sampled[i]+"\"+eol;"+eol;
  ccode += "   code += \"}\"+eol+eol;"+eol;

  if(this.cpp_greq != null && this.cpp_greq.length()>0) {
   ccode += "   code += \"double \"+name+\"::compute_greq";
   ccode += "(double x) {\"+eol;"+eol;
   sampled = super.sample(cpp_greq);
   for(int i=0; i<sampled.length; i++) if(sampled[i].trim().length() > 0)
    ccode += "   code += \" "+sampled[i]+"\"+eol;"+eol;
   ccode += "   code += \"}\"+eol+eol;"+eol;
  }
  if(this.cpp_smeq != null && this.cpp_smeq.length()>0) {
   ccode += "   code += \"double \"+name+\"::compute_smeq";
   ccode += "(double x) {\"+eol;"+eol;
   sampled = super.sample(cpp_smeq);
   for(int i=0; i<sampled.length; i++) if(sampled[i].trim().length() > 0)
    ccode += "   code += \" "+sampled[i]+"\"+eol;"+eol;
   ccode += "   code += \"}\"+eol+eol;"+eol;
  }
  if(this.cpp_center != null && this.cpp_center.length()>0) {
   ccode += "   code += \"double \"+name+\"::center() {\"+eol;"+eol;
   sampled = super.sample(cpp_center);
   for(int i=0; i<sampled.length; i++) if(sampled[i].trim().length() > 0)
    ccode += "   code += \" "+sampled[i]+"\"+eol;"+eol;
   ccode += "   code += \"}\"+eol+eol;"+eol;
  }
  if(this.cpp_basis != null && this.cpp_basis.length()>0) {
   ccode += "   code += \"double \"+name+\"::basis() {\"+eol;"+eol;
   sampled = super.sample(cpp_basis);
   for(int i=0; i<sampled.length; i++) if(sampled[i].trim().length() > 0)
    ccode += "   code += \" "+sampled[i]+"\"+eol;"+eol;
   ccode += "   code += \"}\"+eol+eol;"+eol;
  }
  ccode += "   return code;"+eol;
  ccode += "  }"+eol+eol;
  ccode += " public String toHpp(String name) {"+eol;
  ccode += "   String eol = System.getProperty";
  ccode += "(\"line.separator\", \"\\n\");"+eol;
  ccode += "   String code = \"\";"+eol;
  ccode += "   code += \"class \"+name+\": ";
  ccode += "public ParamMembershipFunction {\"+eol;"+eol;
  ccode += "   code += \"private:\"+eol;"+eol;
  for(int i=0; i<param.size(); i++)
   ccode += "   code += \" double "+param.elementAt(i)+";\"+eol;"+eol;
  ccode += "   code += eol+\"public:\"+eol;"+eol;
  ccode += "   code += \" \"+name+\"() {};\"+eol;"+eol;
  ccode += "   code += \" virtual ~\"+name+\"() {};\"+eol;"+eol;
  ccode += "   code += \" \"+name+\"(double min,double max,double step";
  ccode += ",double *param, int length);\"+eol;"+eol;
  ccode += "   code += \" \"+name+\"*dup();\"+eol;"+eol;
  ccode += "   code += \" virtual double param(int _i);\"+eol;"+eol;
  ccode += "   code += \" virtual double compute_eq(double x);\"+eol;"+eol;
  if(this.cpp_greq != null && this.cpp_greq.length()>0) 
   ccode+="   code += \" virtual double compute_greq(double x);\"+eol;"+eol;
  if(this.cpp_smeq != null && this.cpp_smeq.length()>0) 
   ccode+="   code += \" virtual double compute_smeq(double x);\"+eol;"+eol;
  if(this.cpp_center != null && this.cpp_center.length()>0) 
   ccode += "   code += \" virtual double center();\"+eol;"+eol;
  if(this.cpp_basis != null && this.cpp_basis.length()>0) 
   ccode += "   code += \" virtual double basis();\"+eol;"+eol;
  ccode += "   code += \"};\"+eol+eol;"+eol;
  ccode += "   return code;"+eol;
  ccode += "  }"+eol+eol;
  return ccode;
 }
}


