/*
 * @(#)XflException.java        1.0 2000/05/09
 *
 * This file is part of Xfuzzy 3.0, a design environment for fuzzy logic
 * based systems.
 *
 * (c) 2000 IMSE-CNM. The authors may be contacted by the email address:
 *                    xfuzzy-team@imse.cnm.es
 *
 * Xfuzzy is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation.
 *
 * Xfuzzy is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * for more details.
 */

//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
//                       EXCEPCIONES DE XFL3			//
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

package xfuzzy.lang;

public class XflException extends Exception{
 final static private String msg[] = {
/*  0 */ "Undefined exception",
/*  1 */ "Universe limits not properly defined",
/*  2 */ "Cardinality not properly defined",
/*  3 */ "Operator not found",
/*  4 */ "Cannot instantiate the selected operator",
/*  5 */ "Selected name does not correspond to any fuzzy operator",
/*  6 */ "Membership function not found",
/*  7 */ "Cannot instantiate the selected membership function",
/*  8 */ "Membership function already defined",
/*  9 */ "Operatorset already defined",
/* 10 */ "Operatorset not found",
/* 11 */ "Type already defined",
/* 12 */ "Type not found",
/* 13 */ "Rulebase already defined",
/* 14 */ "Rulebase not found",
/* 15 */ "Variable already defined",
/* 16 */ "Variable not found",
/* 17 */ "Package not found",
/* 18 */ "Variable not valued",
/* 19 */ "Derivative not supported",
/* 20 */ "Unknown rulebase",
/* 21 */ "Wrong number of arguments",
/* 22 */ "Invalid argument. Unvalued inner variable",
/* 23 */ "Invalid argument. Variable cannot be modified",
/* 24 */ "System module already defined",

/* 25 */ "Error Function not properly defined",
/* 26 */ "Learning Algorithm not properly defined",
/* 27 */ "Option not supported",
/* 28 */ "Algorithm not found",
/* 29 */ "Option not found",
/* 30 */ "Cannot set option before selecting learning algorithm",
/* 31 */ "File not found",

/* 32 */ "Cannot instantiate class",
/* 33 */ "Errors reading pattern file",

/* 34 */ "Wrong number of parameters in membership function definition",
/* 35 */ "Parameter values not allowed"
 };

 private int code;

 public XflException() { this.code = 0; }
 public XflException(int code) { this.code = code; }
 public String toString() { return this.msg[ this.code ]; }
 public String getMessage() { return msg[code]; }
 public static String getMessage(int code) { return msg[code]; }
}

