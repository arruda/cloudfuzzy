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


