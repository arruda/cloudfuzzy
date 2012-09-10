/*
 * @(#)Xfuzzy.java        1.0 2000/05/09
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
//		  VENTANA PRINCIPAL DE Xfuzzy3.0		//
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

package xfuzzy;

import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import play.Play;

public abstract class Xfuzzy {
/** 
 * caminho para ele encontrar a pkg
 * Na realidade esse caminho esta sendo sobre-escrito na aplicacao gesplan
 * pelo JPAStartUpListener, colocando para apontar para o endereco da aplicacao no TomCat
 * Somado com a constante CAMINHO_PKGXFUZZY.
 */
	public static String fuzzyPath = setRelativeFuzzyPath();//"/home/arruda/projetos/cloudfuzzy/cloudfuzzy/app/";

	 /**
	  * this method returns the Xfuzzy.fuzzyPath to be relative to the /srv path.
	  * Is been used to give a apropriete develop environment.
	  * @author felipe.arruda
	  */
	 public static String setRelativeFuzzyPath(){
		 //get the eddress relative to where is this class(bin folder of the project)
		 //ex: C:\\gesplan2010WorkComFuzzy\\xfuzzy\\bin\\
//		String relativePath = Xfuzzy.class.getResource("/").getPath();
		String relativePath = Play.application().configuration().getString("pkgPath");
		//troca "bin" por "src"
		//ex: C:\\gesplan2010WorkComFuzzy\\xfuzzy\\app\\ 
		relativePath = relativePath.replace("bin", "app"); 	
		//returns this path
		return relativePath; 
	 }
}

