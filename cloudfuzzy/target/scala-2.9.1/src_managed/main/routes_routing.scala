// @SOURCE:/home/arruda/projetos/cloudfuzzy/cloudfuzzy/conf/routes
// @HASH:df4da84969adc8f47581a49693ec82902040841c
// @DATE:Thu Dec 06 12:21:52 BRST 2012

import play.core._
import play.core.Router._
import play.core.j._

import play.api.mvc._
import play.libs.F

import Router.queryString

object Routes extends Router.Routes {


// @LINE:6
val controllers_Application_index0 = Route("GET", PathPattern(List(StaticPart("/"))))
                    

// @LINE:9
val controllers_Assets_at1 = Route("GET", PathPattern(List(StaticPart("/assets/"),DynamicPart("file", """.+"""))))
                    

// @LINE:13
val controllers_Authentication_register2 = Route("GET", PathPattern(List(StaticPart("/signup"))))
                    

// @LINE:14
val controllers_Authentication_edit3 = Route("GET", PathPattern(List(StaticPart("/users/fakeuser"))))
                    

// @LINE:15
val controllers_Authentication_submit4 = Route("POST", PathPattern(List(StaticPart("/signup"))))
                    

// @LINE:18
val controllers_Authentication_login5 = Route("GET", PathPattern(List(StaticPart("/login"))))
                    

// @LINE:19
val controllers_Authentication_authenticate6 = Route("POST", PathPattern(List(StaticPart("/login"))))
                    

// @LINE:20
val controllers_Authentication_logout7 = Route("GET", PathPattern(List(StaticPart("/logout"))))
                    

// @LINE:28
val controllers_Systems_list8 = Route("GET", PathPattern(List(StaticPart("/system/list"))))
                    

// @LINE:29
val controllers_Systems_prepareCreate9 = Route("GET", PathPattern(List(StaticPart("/system/new"))))
                    

// @LINE:30
val controllers_Systems_create10 = Route("POST", PathPattern(List(StaticPart("/system/new"))))
                    

// @LINE:31
val controllers_Systems_detail11 = Route("GET", PathPattern(List(StaticPart("/system/"),DynamicPart("id_sys", """[^/]+"""))))
                    

// @LINE:32
val controllers_Systems_delete12 = Route("POST", PathPattern(List(StaticPart("/system/"),DynamicPart("id_sys", """[^/]+"""),StaticPart("/del"))))
                    

// @LINE:33
val controllers_Systems_deleteVariable13 = Route("GET", PathPattern(List(StaticPart("/system/"),DynamicPart("id_sys", """[^/]+"""),StaticPart("/var/"),DynamicPart("kind", """[^/]+"""),StaticPart("/"),DynamicPart("id_variable", """[^/]+"""),StaticPart("/del"))))
                    

// @LINE:35
val controllers_Systems_print14 = Route("GET", PathPattern(List(StaticPart("/system/"),DynamicPart("id_sys", """[^/]+"""),StaticPart("/print"))))
                    

// @LINE:38
val controllers_OperatorSets_prepareCreate15 = Route("GET", PathPattern(List(StaticPart("/system/"),DynamicPart("id_sys", """[^/]+"""),StaticPart("/opset/new"))))
                    

// @LINE:39
val controllers_OperatorSets_create16 = Route("POST", PathPattern(List(StaticPart("/system/"),DynamicPart("id_sys", """[^/]+"""),StaticPart("/opset/new"))))
                    

// @LINE:40
val controllers_OperatorSets_detail17 = Route("GET", PathPattern(List(StaticPart("/system/"),DynamicPart("id_sys", """[^/]+"""),StaticPart("/opset/"),DynamicPart("id_opset", """[^/]+"""))))
                    

// @LINE:41
val controllers_OperatorSets_delete18 = Route("GET", PathPattern(List(StaticPart("/system/"),DynamicPart("id_sys", """[^/]+"""),StaticPart("/opset/"),DynamicPart("id_opset", """[^/]+"""),StaticPart("/del"))))
                    

// @LINE:44
val controllers_Types_prepareCreate19 = Route("GET", PathPattern(List(StaticPart("/system/"),DynamicPart("id_sys", """[^/]+"""),StaticPart("/type/new"))))
                    

// @LINE:45
val controllers_Types_create20 = Route("POST", PathPattern(List(StaticPart("/system/"),DynamicPart("id_sys", """[^/]+"""),StaticPart("/type/new"))))
                    

// @LINE:46
val controllers_Types_detail21 = Route("GET", PathPattern(List(StaticPart("/system/"),DynamicPart("id_sys", """[^/]+"""),StaticPart("/type/"),DynamicPart("id_type", """[^/]+"""))))
                    

// @LINE:47
val controllers_Types_delete22 = Route("GET", PathPattern(List(StaticPart("/system/"),DynamicPart("id_sys", """[^/]+"""),StaticPart("/type/"),DynamicPart("id_type", """[^/]+"""),StaticPart("/del"))))
                    

// @LINE:48
val controllers_Types_prepareEdit23 = Route("GET", PathPattern(List(StaticPart("/system/"),DynamicPart("id_sys", """[^/]+"""),StaticPart("/type/"),DynamicPart("id_type", """[^/]+"""),StaticPart("/edit"))))
                    

// @LINE:49
val controllers_Types_edit24 = Route("POST", PathPattern(List(StaticPart("/system/"),DynamicPart("id_sys", """[^/]+"""),StaticPart("/type/"),DynamicPart("id_type", """[^/]+"""),StaticPart("/edit"))))
                    

// @LINE:53
val controllers_MFs_prepareCreate25 = Route("GET", PathPattern(List(StaticPart("/system/"),DynamicPart("id_sys", """[^/]+"""),StaticPart("/type/"),DynamicPart("id_type", """[^/]+"""),StaticPart("/mf/new"))))
                    

// @LINE:54
val controllers_MFs_create26 = Route("POST", PathPattern(List(StaticPart("/system/"),DynamicPart("id_sys", """[^/]+"""),StaticPart("/type/"),DynamicPart("id_type", """[^/]+"""),StaticPart("/mf/new"))))
                    

// @LINE:55
val controllers_MFs_detail27 = Route("GET", PathPattern(List(StaticPart("/system/"),DynamicPart("id_sys", """[^/]+"""),StaticPart("/type/"),DynamicPart("id_type", """[^/]+"""),StaticPart("/mf/"),DynamicPart("id_mf", """[^/]+"""))))
                    

// @LINE:56
val controllers_MFs_delete28 = Route("GET", PathPattern(List(StaticPart("/system/"),DynamicPart("id_sys", """[^/]+"""),StaticPart("/type/"),DynamicPart("id_type", """[^/]+"""),StaticPart("/mf/"),DynamicPart("id_mf", """[^/]+"""),StaticPart("/del"))))
                    

// @LINE:60
val controllers_RuleBases_prepareCreate29 = Route("GET", PathPattern(List(StaticPart("/system/"),DynamicPart("id_sys", """[^/]+"""),StaticPart("/rb/new"))))
                    

// @LINE:61
val controllers_RuleBases_create30 = Route("POST", PathPattern(List(StaticPart("/system/"),DynamicPart("id_sys", """[^/]+"""),StaticPart("/rb/new"))))
                    

// @LINE:62
val controllers_RuleBases_detail31 = Route("GET", PathPattern(List(StaticPart("/system/"),DynamicPart("id_sys", """[^/]+"""),StaticPart("/rb/"),DynamicPart("id_rb", """[^/]+"""))))
                    

// @LINE:63
val controllers_RuleBases_deleteVariable32 = Route("GET", PathPattern(List(StaticPart("/system/"),DynamicPart("id_sys", """[^/]+"""),StaticPart("/rb/"),DynamicPart("id_rb", """[^/]+"""),StaticPart("/var/"),DynamicPart("kind", """[^/]+"""),StaticPart("/"),DynamicPart("id_variable", """[^/]+"""),StaticPart("/del"))))
                    

// @LINE:64
val controllers_RuleBases_deleteRule33 = Route("GET", PathPattern(List(StaticPart("/system/"),DynamicPart("id_sys", """[^/]+"""),StaticPart("/rb/"),DynamicPart("id_rb", """[^/]+"""),StaticPart("/rule/"),DynamicPart("id_rule", """[^/]+"""),StaticPart("/del"))))
                    

// @LINE:72
val controllers_MFs_ajaxGetNumParams34 = Route("GET", PathPattern(List(StaticPart("/ajax/get_num_params_mf"))))
                    

// @LINE:73
val controllers_Types_ajaxGetMFsPlotData35 = Route("GET", PathPattern(List(StaticPart("/ajax/get_mfs_plot_data"))))
                    

// @LINE:75
val controllers_OperatorSets_ajaxGetNumParamsForOption36 = Route("GET", PathPattern(List(StaticPart("/ajax/get_num_params_operator_option"))))
                    

// @LINE:77
val controllers_RuleBases_ajaxAddVariable37 = Route("POST", PathPattern(List(StaticPart("/ajax/ajax_add_variable_rb/"),DynamicPart("id_sys", """[^/]+"""),StaticPart("/"),DynamicPart("id_rb", """[^/]+"""))))
                    

// @LINE:78
val controllers_RuleBases_ajaxAddRuleFromTable38 = Route("POST", PathPattern(List(StaticPart("/ajax/ajax_add_rule_from_table/"),DynamicPart("id_sys", """[^/]+"""),StaticPart("/"),DynamicPart("id_rb", """[^/]+"""))))
                    

// @LINE:80
val controllers_Systems_ajaxAddVariable39 = Route("POST", PathPattern(List(StaticPart("/ajax/ajax_add_variable/"),DynamicPart("id_sys", """[^/]+"""))))
                    

// @LINE:82
val controllers_RuleBaseCalls_ajaxAddCall40 = Route("POST", PathPattern(List(StaticPart("/ajax/ajax_add_call/"),DynamicPart("id_sys", """[^/]+"""),StaticPart("/"),DynamicPart("id_rb", """[^/]+"""))))
                    

// @LINE:83
val controllers_RuleBaseCalls_ajaxAddLink41 = Route("POST", PathPattern(List(StaticPart("/ajax/ajax_add_link/"),DynamicPart("id_sys", """[^/]+"""))))
                    

// @LINE:84
val controllers_RuleBaseCalls_ajaxRemoveLink42 = Route("POST", PathPattern(List(StaticPart("/ajax/ajax_remove_link/"),DynamicPart("id_sys", """[^/]+"""))))
                    
def documentation = List(("""GET""","""/""","""controllers.Application.index()"""),("""GET""","""/assets/$file<.+>""","""controllers.Assets.at(path:String = "/public", file:String)"""),("""GET""","""/signup""","""controllers.Authentication.register()"""),("""GET""","""/users/fakeuser""","""controllers.Authentication.edit()"""),("""POST""","""/signup""","""controllers.Authentication.submit()"""),("""GET""","""/login""","""controllers.Authentication.login()"""),("""POST""","""/login""","""controllers.Authentication.authenticate()"""),("""GET""","""/logout""","""controllers.Authentication.logout()"""),("""GET""","""/system/list""","""controllers.Systems.list()"""),("""GET""","""/system/new""","""controllers.Systems.prepareCreate()"""),("""POST""","""/system/new""","""controllers.Systems.create()"""),("""GET""","""/system/$id_sys<[^/]+>""","""controllers.Systems.detail(id_sys:Long)"""),("""POST""","""/system/$id_sys<[^/]+>/del""","""controllers.Systems.delete(id_sys:Long)"""),("""GET""","""/system/$id_sys<[^/]+>/var/$kind<[^/]+>/$id_variable<[^/]+>/del""","""controllers.Systems.deleteVariable(id_sys:Long, id_variable:java.lang.Integer, kind:java.lang.Integer)"""),("""GET""","""/system/$id_sys<[^/]+>/print""","""controllers.Systems.print(id_sys:Long)"""),("""GET""","""/system/$id_sys<[^/]+>/opset/new""","""controllers.OperatorSets.prepareCreate(id_sys:Long)"""),("""POST""","""/system/$id_sys<[^/]+>/opset/new""","""controllers.OperatorSets.create(id_sys:Long)"""),("""GET""","""/system/$id_sys<[^/]+>/opset/$id_opset<[^/]+>""","""controllers.OperatorSets.detail(id_sys:Long, id_opset:java.lang.Integer)"""),("""GET""","""/system/$id_sys<[^/]+>/opset/$id_opset<[^/]+>/del""","""controllers.OperatorSets.delete(id_sys:Long, id_opset:java.lang.Integer)"""),("""GET""","""/system/$id_sys<[^/]+>/type/new""","""controllers.Types.prepareCreate(id_sys:Long)"""),("""POST""","""/system/$id_sys<[^/]+>/type/new""","""controllers.Types.create(id_sys:Long)"""),("""GET""","""/system/$id_sys<[^/]+>/type/$id_type<[^/]+>""","""controllers.Types.detail(id_sys:Long, id_type:java.lang.Integer)"""),("""GET""","""/system/$id_sys<[^/]+>/type/$id_type<[^/]+>/del""","""controllers.Types.delete(id_sys:Long, id_type:java.lang.Integer)"""),("""GET""","""/system/$id_sys<[^/]+>/type/$id_type<[^/]+>/edit""","""controllers.Types.prepareEdit(id_sys:Long, id_type:java.lang.Integer)"""),("""POST""","""/system/$id_sys<[^/]+>/type/$id_type<[^/]+>/edit""","""controllers.Types.edit(id_sys:Long, id_type:java.lang.Integer)"""),("""GET""","""/system/$id_sys<[^/]+>/type/$id_type<[^/]+>/mf/new""","""controllers.MFs.prepareCreate(id_sys:Long, id_type:java.lang.Integer)"""),("""POST""","""/system/$id_sys<[^/]+>/type/$id_type<[^/]+>/mf/new""","""controllers.MFs.create(id_sys:Long, id_type:java.lang.Integer)"""),("""GET""","""/system/$id_sys<[^/]+>/type/$id_type<[^/]+>/mf/$id_mf<[^/]+>""","""controllers.MFs.detail(id_sys:Long, id_type:java.lang.Integer, id_mf:java.lang.Integer)"""),("""GET""","""/system/$id_sys<[^/]+>/type/$id_type<[^/]+>/mf/$id_mf<[^/]+>/del""","""controllers.MFs.delete(id_sys:Long, id_type:java.lang.Integer, id_mf:java.lang.Integer)"""),("""GET""","""/system/$id_sys<[^/]+>/rb/new""","""controllers.RuleBases.prepareCreate(id_sys:Long)"""),("""POST""","""/system/$id_sys<[^/]+>/rb/new""","""controllers.RuleBases.create(id_sys:Long)"""),("""GET""","""/system/$id_sys<[^/]+>/rb/$id_rb<[^/]+>""","""controllers.RuleBases.detail(id_sys:Long, id_rb:java.lang.Integer)"""),("""GET""","""/system/$id_sys<[^/]+>/rb/$id_rb<[^/]+>/var/$kind<[^/]+>/$id_variable<[^/]+>/del""","""controllers.RuleBases.deleteVariable(id_sys:Long, id_rb:java.lang.Integer, id_variable:java.lang.Integer, kind:java.lang.Integer)"""),("""GET""","""/system/$id_sys<[^/]+>/rb/$id_rb<[^/]+>/rule/$id_rule<[^/]+>/del""","""controllers.RuleBases.deleteRule(id_sys:Long, id_rb:java.lang.Integer, id_rule:java.lang.Integer)"""),("""GET""","""/ajax/get_num_params_mf""","""controllers.MFs.ajaxGetNumParams()"""),("""GET""","""/ajax/get_mfs_plot_data""","""controllers.Types.ajaxGetMFsPlotData()"""),("""GET""","""/ajax/get_num_params_operator_option""","""controllers.OperatorSets.ajaxGetNumParamsForOption()"""),("""POST""","""/ajax/ajax_add_variable_rb/$id_sys<[^/]+>/$id_rb<[^/]+>""","""controllers.RuleBases.ajaxAddVariable(id_sys:Long, id_rb:java.lang.Integer)"""),("""POST""","""/ajax/ajax_add_rule_from_table/$id_sys<[^/]+>/$id_rb<[^/]+>""","""controllers.RuleBases.ajaxAddRuleFromTable(id_sys:Long, id_rb:java.lang.Integer)"""),("""POST""","""/ajax/ajax_add_variable/$id_sys<[^/]+>""","""controllers.Systems.ajaxAddVariable(id_sys:Long)"""),("""POST""","""/ajax/ajax_add_call/$id_sys<[^/]+>/$id_rb<[^/]+>""","""controllers.RuleBaseCalls.ajaxAddCall(id_sys:Long, id_rb:java.lang.Integer)"""),("""POST""","""/ajax/ajax_add_link/$id_sys<[^/]+>""","""controllers.RuleBaseCalls.ajaxAddLink(id_sys:Long)"""),("""POST""","""/ajax/ajax_remove_link/$id_sys<[^/]+>""","""controllers.RuleBaseCalls.ajaxRemoveLink(id_sys:Long)"""))
             
    
def routes:PartialFunction[RequestHeader,Handler] = {        

// @LINE:6
case controllers_Application_index0(params) => {
   call { 
        invokeHandler(_root_.controllers.Application.index(), HandlerDef(this, "controllers.Application", "index", Nil))
   }
}
                    

// @LINE:9
case controllers_Assets_at1(params) => {
   call(Param[String]("path", Right("/public")), params.fromPath[String]("file", None)) { (path, file) =>
        invokeHandler(_root_.controllers.Assets.at(path, file), HandlerDef(this, "controllers.Assets", "at", Seq(classOf[String], classOf[String])))
   }
}
                    

// @LINE:13
case controllers_Authentication_register2(params) => {
   call { 
        invokeHandler(_root_.controllers.Authentication.register(), HandlerDef(this, "controllers.Authentication", "register", Nil))
   }
}
                    

// @LINE:14
case controllers_Authentication_edit3(params) => {
   call { 
        invokeHandler(_root_.controllers.Authentication.edit(), HandlerDef(this, "controllers.Authentication", "edit", Nil))
   }
}
                    

// @LINE:15
case controllers_Authentication_submit4(params) => {
   call { 
        invokeHandler(_root_.controllers.Authentication.submit(), HandlerDef(this, "controllers.Authentication", "submit", Nil))
   }
}
                    

// @LINE:18
case controllers_Authentication_login5(params) => {
   call { 
        invokeHandler(_root_.controllers.Authentication.login(), HandlerDef(this, "controllers.Authentication", "login", Nil))
   }
}
                    

// @LINE:19
case controllers_Authentication_authenticate6(params) => {
   call { 
        invokeHandler(_root_.controllers.Authentication.authenticate(), HandlerDef(this, "controllers.Authentication", "authenticate", Nil))
   }
}
                    

// @LINE:20
case controllers_Authentication_logout7(params) => {
   call { 
        invokeHandler(_root_.controllers.Authentication.logout(), HandlerDef(this, "controllers.Authentication", "logout", Nil))
   }
}
                    

// @LINE:28
case controllers_Systems_list8(params) => {
   call { 
        invokeHandler(_root_.controllers.Systems.list(), HandlerDef(this, "controllers.Systems", "list", Nil))
   }
}
                    

// @LINE:29
case controllers_Systems_prepareCreate9(params) => {
   call { 
        invokeHandler(_root_.controllers.Systems.prepareCreate(), HandlerDef(this, "controllers.Systems", "prepareCreate", Nil))
   }
}
                    

// @LINE:30
case controllers_Systems_create10(params) => {
   call { 
        invokeHandler(_root_.controllers.Systems.create(), HandlerDef(this, "controllers.Systems", "create", Nil))
   }
}
                    

// @LINE:31
case controllers_Systems_detail11(params) => {
   call(params.fromPath[Long]("id_sys", None)) { (id_sys) =>
        invokeHandler(_root_.controllers.Systems.detail(id_sys), HandlerDef(this, "controllers.Systems", "detail", Seq(classOf[Long])))
   }
}
                    

// @LINE:32
case controllers_Systems_delete12(params) => {
   call(params.fromPath[Long]("id_sys", None)) { (id_sys) =>
        invokeHandler(_root_.controllers.Systems.delete(id_sys), HandlerDef(this, "controllers.Systems", "delete", Seq(classOf[Long])))
   }
}
                    

// @LINE:33
case controllers_Systems_deleteVariable13(params) => {
   call(params.fromPath[Long]("id_sys", None), params.fromPath[java.lang.Integer]("id_variable", None), params.fromPath[java.lang.Integer]("kind", None)) { (id_sys, id_variable, kind) =>
        invokeHandler(_root_.controllers.Systems.deleteVariable(id_sys, id_variable, kind), HandlerDef(this, "controllers.Systems", "deleteVariable", Seq(classOf[Long], classOf[java.lang.Integer], classOf[java.lang.Integer])))
   }
}
                    

// @LINE:35
case controllers_Systems_print14(params) => {
   call(params.fromPath[Long]("id_sys", None)) { (id_sys) =>
        invokeHandler(_root_.controllers.Systems.print(id_sys), HandlerDef(this, "controllers.Systems", "print", Seq(classOf[Long])))
   }
}
                    

// @LINE:38
case controllers_OperatorSets_prepareCreate15(params) => {
   call(params.fromPath[Long]("id_sys", None)) { (id_sys) =>
        invokeHandler(_root_.controllers.OperatorSets.prepareCreate(id_sys), HandlerDef(this, "controllers.OperatorSets", "prepareCreate", Seq(classOf[Long])))
   }
}
                    

// @LINE:39
case controllers_OperatorSets_create16(params) => {
   call(params.fromPath[Long]("id_sys", None)) { (id_sys) =>
        invokeHandler(_root_.controllers.OperatorSets.create(id_sys), HandlerDef(this, "controllers.OperatorSets", "create", Seq(classOf[Long])))
   }
}
                    

// @LINE:40
case controllers_OperatorSets_detail17(params) => {
   call(params.fromPath[Long]("id_sys", None), params.fromPath[java.lang.Integer]("id_opset", None)) { (id_sys, id_opset) =>
        invokeHandler(_root_.controllers.OperatorSets.detail(id_sys, id_opset), HandlerDef(this, "controllers.OperatorSets", "detail", Seq(classOf[Long], classOf[java.lang.Integer])))
   }
}
                    

// @LINE:41
case controllers_OperatorSets_delete18(params) => {
   call(params.fromPath[Long]("id_sys", None), params.fromPath[java.lang.Integer]("id_opset", None)) { (id_sys, id_opset) =>
        invokeHandler(_root_.controllers.OperatorSets.delete(id_sys, id_opset), HandlerDef(this, "controllers.OperatorSets", "delete", Seq(classOf[Long], classOf[java.lang.Integer])))
   }
}
                    

// @LINE:44
case controllers_Types_prepareCreate19(params) => {
   call(params.fromPath[Long]("id_sys", None)) { (id_sys) =>
        invokeHandler(_root_.controllers.Types.prepareCreate(id_sys), HandlerDef(this, "controllers.Types", "prepareCreate", Seq(classOf[Long])))
   }
}
                    

// @LINE:45
case controllers_Types_create20(params) => {
   call(params.fromPath[Long]("id_sys", None)) { (id_sys) =>
        invokeHandler(_root_.controllers.Types.create(id_sys), HandlerDef(this, "controllers.Types", "create", Seq(classOf[Long])))
   }
}
                    

// @LINE:46
case controllers_Types_detail21(params) => {
   call(params.fromPath[Long]("id_sys", None), params.fromPath[java.lang.Integer]("id_type", None)) { (id_sys, id_type) =>
        invokeHandler(_root_.controllers.Types.detail(id_sys, id_type), HandlerDef(this, "controllers.Types", "detail", Seq(classOf[Long], classOf[java.lang.Integer])))
   }
}
                    

// @LINE:47
case controllers_Types_delete22(params) => {
   call(params.fromPath[Long]("id_sys", None), params.fromPath[java.lang.Integer]("id_type", None)) { (id_sys, id_type) =>
        invokeHandler(_root_.controllers.Types.delete(id_sys, id_type), HandlerDef(this, "controllers.Types", "delete", Seq(classOf[Long], classOf[java.lang.Integer])))
   }
}
                    

// @LINE:48
case controllers_Types_prepareEdit23(params) => {
   call(params.fromPath[Long]("id_sys", None), params.fromPath[java.lang.Integer]("id_type", None)) { (id_sys, id_type) =>
        invokeHandler(_root_.controllers.Types.prepareEdit(id_sys, id_type), HandlerDef(this, "controllers.Types", "prepareEdit", Seq(classOf[Long], classOf[java.lang.Integer])))
   }
}
                    

// @LINE:49
case controllers_Types_edit24(params) => {
   call(params.fromPath[Long]("id_sys", None), params.fromPath[java.lang.Integer]("id_type", None)) { (id_sys, id_type) =>
        invokeHandler(_root_.controllers.Types.edit(id_sys, id_type), HandlerDef(this, "controllers.Types", "edit", Seq(classOf[Long], classOf[java.lang.Integer])))
   }
}
                    

// @LINE:53
case controllers_MFs_prepareCreate25(params) => {
   call(params.fromPath[Long]("id_sys", None), params.fromPath[java.lang.Integer]("id_type", None)) { (id_sys, id_type) =>
        invokeHandler(_root_.controllers.MFs.prepareCreate(id_sys, id_type), HandlerDef(this, "controllers.MFs", "prepareCreate", Seq(classOf[Long], classOf[java.lang.Integer])))
   }
}
                    

// @LINE:54
case controllers_MFs_create26(params) => {
   call(params.fromPath[Long]("id_sys", None), params.fromPath[java.lang.Integer]("id_type", None)) { (id_sys, id_type) =>
        invokeHandler(_root_.controllers.MFs.create(id_sys, id_type), HandlerDef(this, "controllers.MFs", "create", Seq(classOf[Long], classOf[java.lang.Integer])))
   }
}
                    

// @LINE:55
case controllers_MFs_detail27(params) => {
   call(params.fromPath[Long]("id_sys", None), params.fromPath[java.lang.Integer]("id_type", None), params.fromPath[java.lang.Integer]("id_mf", None)) { (id_sys, id_type, id_mf) =>
        invokeHandler(_root_.controllers.MFs.detail(id_sys, id_type, id_mf), HandlerDef(this, "controllers.MFs", "detail", Seq(classOf[Long], classOf[java.lang.Integer], classOf[java.lang.Integer])))
   }
}
                    

// @LINE:56
case controllers_MFs_delete28(params) => {
   call(params.fromPath[Long]("id_sys", None), params.fromPath[java.lang.Integer]("id_type", None), params.fromPath[java.lang.Integer]("id_mf", None)) { (id_sys, id_type, id_mf) =>
        invokeHandler(_root_.controllers.MFs.delete(id_sys, id_type, id_mf), HandlerDef(this, "controllers.MFs", "delete", Seq(classOf[Long], classOf[java.lang.Integer], classOf[java.lang.Integer])))
   }
}
                    

// @LINE:60
case controllers_RuleBases_prepareCreate29(params) => {
   call(params.fromPath[Long]("id_sys", None)) { (id_sys) =>
        invokeHandler(_root_.controllers.RuleBases.prepareCreate(id_sys), HandlerDef(this, "controllers.RuleBases", "prepareCreate", Seq(classOf[Long])))
   }
}
                    

// @LINE:61
case controllers_RuleBases_create30(params) => {
   call(params.fromPath[Long]("id_sys", None)) { (id_sys) =>
        invokeHandler(_root_.controllers.RuleBases.create(id_sys), HandlerDef(this, "controllers.RuleBases", "create", Seq(classOf[Long])))
   }
}
                    

// @LINE:62
case controllers_RuleBases_detail31(params) => {
   call(params.fromPath[Long]("id_sys", None), params.fromPath[java.lang.Integer]("id_rb", None)) { (id_sys, id_rb) =>
        invokeHandler(_root_.controllers.RuleBases.detail(id_sys, id_rb), HandlerDef(this, "controllers.RuleBases", "detail", Seq(classOf[Long], classOf[java.lang.Integer])))
   }
}
                    

// @LINE:63
case controllers_RuleBases_deleteVariable32(params) => {
   call(params.fromPath[Long]("id_sys", None), params.fromPath[java.lang.Integer]("id_rb", None), params.fromPath[java.lang.Integer]("id_variable", None), params.fromPath[java.lang.Integer]("kind", None)) { (id_sys, id_rb, id_variable, kind) =>
        invokeHandler(_root_.controllers.RuleBases.deleteVariable(id_sys, id_rb, id_variable, kind), HandlerDef(this, "controllers.RuleBases", "deleteVariable", Seq(classOf[Long], classOf[java.lang.Integer], classOf[java.lang.Integer], classOf[java.lang.Integer])))
   }
}
                    

// @LINE:64
case controllers_RuleBases_deleteRule33(params) => {
   call(params.fromPath[Long]("id_sys", None), params.fromPath[java.lang.Integer]("id_rb", None), params.fromPath[java.lang.Integer]("id_rule", None)) { (id_sys, id_rb, id_rule) =>
        invokeHandler(_root_.controllers.RuleBases.deleteRule(id_sys, id_rb, id_rule), HandlerDef(this, "controllers.RuleBases", "deleteRule", Seq(classOf[Long], classOf[java.lang.Integer], classOf[java.lang.Integer])))
   }
}
                    

// @LINE:72
case controllers_MFs_ajaxGetNumParams34(params) => {
   call { 
        invokeHandler(_root_.controllers.MFs.ajaxGetNumParams(), HandlerDef(this, "controllers.MFs", "ajaxGetNumParams", Nil))
   }
}
                    

// @LINE:73
case controllers_Types_ajaxGetMFsPlotData35(params) => {
   call { 
        invokeHandler(_root_.controllers.Types.ajaxGetMFsPlotData(), HandlerDef(this, "controllers.Types", "ajaxGetMFsPlotData", Nil))
   }
}
                    

// @LINE:75
case controllers_OperatorSets_ajaxGetNumParamsForOption36(params) => {
   call { 
        invokeHandler(_root_.controllers.OperatorSets.ajaxGetNumParamsForOption(), HandlerDef(this, "controllers.OperatorSets", "ajaxGetNumParamsForOption", Nil))
   }
}
                    

// @LINE:77
case controllers_RuleBases_ajaxAddVariable37(params) => {
   call(params.fromPath[Long]("id_sys", None), params.fromPath[java.lang.Integer]("id_rb", None)) { (id_sys, id_rb) =>
        invokeHandler(_root_.controllers.RuleBases.ajaxAddVariable(id_sys, id_rb), HandlerDef(this, "controllers.RuleBases", "ajaxAddVariable", Seq(classOf[Long], classOf[java.lang.Integer])))
   }
}
                    

// @LINE:78
case controllers_RuleBases_ajaxAddRuleFromTable38(params) => {
   call(params.fromPath[Long]("id_sys", None), params.fromPath[java.lang.Integer]("id_rb", None)) { (id_sys, id_rb) =>
        invokeHandler(_root_.controllers.RuleBases.ajaxAddRuleFromTable(id_sys, id_rb), HandlerDef(this, "controllers.RuleBases", "ajaxAddRuleFromTable", Seq(classOf[Long], classOf[java.lang.Integer])))
   }
}
                    

// @LINE:80
case controllers_Systems_ajaxAddVariable39(params) => {
   call(params.fromPath[Long]("id_sys", None)) { (id_sys) =>
        invokeHandler(_root_.controllers.Systems.ajaxAddVariable(id_sys), HandlerDef(this, "controllers.Systems", "ajaxAddVariable", Seq(classOf[Long])))
   }
}
                    

// @LINE:82
case controllers_RuleBaseCalls_ajaxAddCall40(params) => {
   call(params.fromPath[Long]("id_sys", None), params.fromPath[java.lang.Integer]("id_rb", None)) { (id_sys, id_rb) =>
        invokeHandler(_root_.controllers.RuleBaseCalls.ajaxAddCall(id_sys, id_rb), HandlerDef(this, "controllers.RuleBaseCalls", "ajaxAddCall", Seq(classOf[Long], classOf[java.lang.Integer])))
   }
}
                    

// @LINE:83
case controllers_RuleBaseCalls_ajaxAddLink41(params) => {
   call(params.fromPath[Long]("id_sys", None)) { (id_sys) =>
        invokeHandler(_root_.controllers.RuleBaseCalls.ajaxAddLink(id_sys), HandlerDef(this, "controllers.RuleBaseCalls", "ajaxAddLink", Seq(classOf[Long])))
   }
}
                    

// @LINE:84
case controllers_RuleBaseCalls_ajaxRemoveLink42(params) => {
   call(params.fromPath[Long]("id_sys", None)) { (id_sys) =>
        invokeHandler(_root_.controllers.RuleBaseCalls.ajaxRemoveLink(id_sys), HandlerDef(this, "controllers.RuleBaseCalls", "ajaxRemoveLink", Seq(classOf[Long])))
   }
}
                    
}
    
}
                