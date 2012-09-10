/*
 * @(#)Universe.java        1.0 2000/05/09
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
//                      UNIVERSO DE DISCURSO			//
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

package xfuzzy.lang;

public class Universe implements Cloneable {
 private double min;
 private double max;
 private int card;

 public Universe() {
  this.min = 0;
  this.max = 1;
  this.card = 256;
 }

 public Universe(double min, double max) throws XflException{
  if( min >= max ) throw new XflException(1);
  this.min = min;
  this.max = max;
  this.card = 256;
 }

 public Universe(double min, double max, int card) throws XflException{
  if( min >= max ) throw new XflException(1);
  if( card <=1 ) throw new XflException(2);
  this.min = min;
  this.max = max;
  this.card = card;
 }

 public double min() {
  return this.min;
 }

 public double max() {
  return this.max;
 }

 public int card() {
  return this.card;
 }

 public void set(double min, double max, int card) throws XflException {
  if( min >= max ) throw new XflException(1);
  if( card <=1 ) throw new XflException(2);
  this.min = min;
  this.max = max;
  this.card = card;
 }

 public boolean outside(double x) {
  return ( x<this.min || x>this.max );
 }

 public double range() {
  return (this.max - this.min);
 }

 public double point(double x) {
  return (1-x)*this.min + x*this.max;
 }

 public double getRate(double x) {
  return (x-min)/(max-min);
 }

 public double step(){
  return (this.max - this.min)/(this.card - 1);
 }

 public double grain(){
  return (this.max - this.min)/(this.card - 1);
 }

 public int bits(){
  double log2 = Math.log(this.card)/Math.log(2);
  return (int) Math.ceil( log2 );
 }

 public String toXfl() {
  return "["+min+","+max+";"+card+"]";
 }

 public String toJava() {
  String eol = System.getProperty("line.separator", "\n");
  String code = "";
  code += "  private double min = "+min+";"+eol;
  code += "  private double max = "+max+";"+eol;
  code += "  private double step = "+step()+";"+eol;
  return code;
 }

 public String toC() {
  String eol = System.getProperty("line.separator", "\n");
  String code = "";
  code += " double min = "+min+";"+eol;
  code += " double max = "+max+";"+eol;
  code += " double step = "+step()+";"+eol;
  return code;
 }

 public Object clone() {
  try { return super.clone(); }
  catch (CloneNotSupportedException e) { return null; }
 }
}

