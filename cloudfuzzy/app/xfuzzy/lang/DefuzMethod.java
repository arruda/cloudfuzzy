/*
 * @(#)DefuzMethod.java        1.0 2000/05/09
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
//			METODOS DE DEFUZZIFICACION		//
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

package xfuzzy.lang;

public abstract class DefuzMethod extends FuzzyOperator {
 public abstract double compute(AggregateMemFunc mf);
 public abstract boolean test(AggregateMemFunc mf);

 public void derivative(AggregateMemFunc mf, double de) throws XflException {
  throw new XflException(19);
 }
}
