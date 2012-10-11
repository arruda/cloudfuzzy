import play.api.templates._

package views.html.fuzzy.system.operatorsets.MyHelpers {

	object repeatOperators {

		def apply(field: play.api.data.Field, min: Int = 1)(f: play.api.data.Field => Html) = {
		  (0 until math.max(if (field.indexes.isEmpty) 0 else field.indexes.max + 1, min)).map(i => 
		  		f(field("[" + i + "]"))
		  	)
		}

	}
}