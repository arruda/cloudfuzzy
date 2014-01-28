 
	/*
	*
    * (c) 2013, 2014 INT - National Institute of Technology
	*
    * CloudFuzzy's team: 
	*	Dayse Mourao Arruda, Felipe Arruda Pontes, Bruno Barcellos Farias de Oliveira.
	*
    * The authors may be contacted by the email address: dayse.arruda@int.gov.br
	*
	* This file is part of CloudFuzzy - version Beta - 2014/January.
	*
    * We apply XFuzzy 3.0 as fuzzy engine to generate and evaluate alternative plans.
    * XFuzzy is a free design environment for fuzzy logic based systems distributed by IMSE-CNM.
	* If you need more information about the XFuzzy 3.0, please go to project's site:
	* http://www2.imse-cnm.csic.es/Xfuzzy/
    *                    
	* CloudFuzzy is free software: you can redistribute it and/or modify
	* it under the terms of the GNU General Public License as published by
	* the Free Software Foundation, either version 3 of the License, or
	* (at your option) any later version.
	*
	* CloudFuzzy is distributed in the hope that it will be useful,
	* but WITHOUT ANY WARRANTY; without even the implied warranty of
	* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
	* GNU General Public License for more details.
	*
	* You should have received a copy of the GNU General Public License
	* along with CloudFuzzy. If not, see <http://www.gnu.org/license/>.
    */
        
           
     
package controllers;

import play.mvc.Action;
import play.mvc.Http.Context;
import play.mvc.Result;

public class EnsureUserIsOwnerOf extends Action.Simple{

	@Override
	public Result call(Context ctx) throws Throwable {
		String username = ctx.session().get("email");
		Long id = null;
		String[] resultRegex = ctx.request().uri().split("/");
		int countNumbers = 0;
		for(int i = 0; i < resultRegex.length; i++){
			if(resultRegex[i].matches("\\d+") && countNumbers == 0){
				id = Long.parseLong(resultRegex[i]);
				countNumbers++;
			}
		}
		if(Secured.isOwnerOf(id, username)){
			return delegate.call(ctx);
		}else{
			return forbidden(); 
		} 
	}
}


