// @SOURCE:/home/arruda/projetos/cloudfuzzy/cloudfuzzy/conf/routes
// @HASH:df4da84969adc8f47581a49693ec82902040841c
// @DATE:Thu Dec 06 12:21:52 BRST 2012

import play.core._
import play.core.Router._
import play.core.j._

import play.api.mvc._
import play.libs.F

import Router.queryString


// @LINE:84
// @LINE:83
// @LINE:82
// @LINE:80
// @LINE:78
// @LINE:77
// @LINE:75
// @LINE:73
// @LINE:72
// @LINE:64
// @LINE:63
// @LINE:62
// @LINE:61
// @LINE:60
// @LINE:56
// @LINE:55
// @LINE:54
// @LINE:53
// @LINE:49
// @LINE:48
// @LINE:47
// @LINE:46
// @LINE:45
// @LINE:44
// @LINE:41
// @LINE:40
// @LINE:39
// @LINE:38
// @LINE:35
// @LINE:33
// @LINE:32
// @LINE:31
// @LINE:30
// @LINE:29
// @LINE:28
// @LINE:20
// @LINE:19
// @LINE:18
// @LINE:15
// @LINE:14
// @LINE:13
// @LINE:9
// @LINE:6
package controllers {

// @LINE:9
class ReverseAssets {
    


 
// @LINE:9
def at(file:String) = {
   Call("GET", "/assets/" + implicitly[PathBindable[String]].unbind("file", file))
}
                                                        

                      
    
}
                            

// @LINE:20
// @LINE:19
// @LINE:18
// @LINE:15
// @LINE:14
// @LINE:13
class ReverseAuthentication {
    


 
// @LINE:14
def edit() = {
   Call("GET", "/users/fakeuser")
}
                                                        
 
// @LINE:18
def login() = {
   Call("GET", "/login")
}
                                                        
 
// @LINE:20
def logout() = {
   Call("GET", "/logout")
}
                                                        
 
// @LINE:13
def register() = {
   Call("GET", "/signup")
}
                                                        
 
// @LINE:15
def submit() = {
   Call("POST", "/signup")
}
                                                        
 
// @LINE:19
def authenticate() = {
   Call("POST", "/login")
}
                                                        

                      
    
}
                            

// @LINE:78
// @LINE:77
// @LINE:64
// @LINE:63
// @LINE:62
// @LINE:61
// @LINE:60
class ReverseRuleBases {
    


 
// @LINE:61
def create(id_sys:Long) = {
   Call("POST", "/system/" + implicitly[PathBindable[Long]].unbind("id_sys", id_sys) + "/rb/new")
}
                                                        
 
// @LINE:60
def prepareCreate(id_sys:Long) = {
   Call("GET", "/system/" + implicitly[PathBindable[Long]].unbind("id_sys", id_sys) + "/rb/new")
}
                                                        
 
// @LINE:77
def ajaxAddVariable(id_sys:Long, id_rb:java.lang.Integer) = {
   Call("POST", "/ajax/ajax_add_variable_rb/" + implicitly[PathBindable[Long]].unbind("id_sys", id_sys) + "/" + implicitly[PathBindable[java.lang.Integer]].unbind("id_rb", id_rb))
}
                                                        
 
// @LINE:78
def ajaxAddRuleFromTable(id_sys:Long, id_rb:java.lang.Integer) = {
   Call("POST", "/ajax/ajax_add_rule_from_table/" + implicitly[PathBindable[Long]].unbind("id_sys", id_sys) + "/" + implicitly[PathBindable[java.lang.Integer]].unbind("id_rb", id_rb))
}
                                                        
 
// @LINE:63
def deleteVariable(id_sys:Long, id_rb:java.lang.Integer, id_variable:java.lang.Integer, kind:java.lang.Integer) = {
   Call("GET", "/system/" + implicitly[PathBindable[Long]].unbind("id_sys", id_sys) + "/rb/" + implicitly[PathBindable[java.lang.Integer]].unbind("id_rb", id_rb) + "/var/" + implicitly[PathBindable[java.lang.Integer]].unbind("kind", kind) + "/" + implicitly[PathBindable[java.lang.Integer]].unbind("id_variable", id_variable) + "/del")
}
                                                        
 
// @LINE:62
def detail(id_sys:Long, id_rb:java.lang.Integer) = {
   Call("GET", "/system/" + implicitly[PathBindable[Long]].unbind("id_sys", id_sys) + "/rb/" + implicitly[PathBindable[java.lang.Integer]].unbind("id_rb", id_rb))
}
                                                        
 
// @LINE:64
def deleteRule(id_sys:Long, id_rb:java.lang.Integer, id_rule:java.lang.Integer) = {
   Call("GET", "/system/" + implicitly[PathBindable[Long]].unbind("id_sys", id_sys) + "/rb/" + implicitly[PathBindable[java.lang.Integer]].unbind("id_rb", id_rb) + "/rule/" + implicitly[PathBindable[java.lang.Integer]].unbind("id_rule", id_rule) + "/del")
}
                                                        

                      
    
}
                            

// @LINE:72
// @LINE:56
// @LINE:55
// @LINE:54
// @LINE:53
class ReverseMFs {
    


 
// @LINE:53
def prepareCreate(id_sys:Long, id_type:java.lang.Integer) = {
   Call("GET", "/system/" + implicitly[PathBindable[Long]].unbind("id_sys", id_sys) + "/type/" + implicitly[PathBindable[java.lang.Integer]].unbind("id_type", id_type) + "/mf/new")
}
                                                        
 
// @LINE:56
def delete(id_sys:Long, id_type:java.lang.Integer, id_mf:java.lang.Integer) = {
   Call("GET", "/system/" + implicitly[PathBindable[Long]].unbind("id_sys", id_sys) + "/type/" + implicitly[PathBindable[java.lang.Integer]].unbind("id_type", id_type) + "/mf/" + implicitly[PathBindable[java.lang.Integer]].unbind("id_mf", id_mf) + "/del")
}
                                                        
 
// @LINE:72
def ajaxGetNumParams() = {
   Call("GET", "/ajax/get_num_params_mf")
}
                                                        
 
// @LINE:54
def create(id_sys:Long, id_type:java.lang.Integer) = {
   Call("POST", "/system/" + implicitly[PathBindable[Long]].unbind("id_sys", id_sys) + "/type/" + implicitly[PathBindable[java.lang.Integer]].unbind("id_type", id_type) + "/mf/new")
}
                                                        
 
// @LINE:55
def detail(id_sys:Long, id_type:java.lang.Integer, id_mf:java.lang.Integer) = {
   Call("GET", "/system/" + implicitly[PathBindable[Long]].unbind("id_sys", id_sys) + "/type/" + implicitly[PathBindable[java.lang.Integer]].unbind("id_type", id_type) + "/mf/" + implicitly[PathBindable[java.lang.Integer]].unbind("id_mf", id_mf))
}
                                                        

                      
    
}
                            

// @LINE:84
// @LINE:83
// @LINE:82
class ReverseRuleBaseCalls {
    


 
// @LINE:82
def ajaxAddCall(id_sys:Long, id_rb:java.lang.Integer) = {
   Call("POST", "/ajax/ajax_add_call/" + implicitly[PathBindable[Long]].unbind("id_sys", id_sys) + "/" + implicitly[PathBindable[java.lang.Integer]].unbind("id_rb", id_rb))
}
                                                        
 
// @LINE:84
def ajaxRemoveLink(id_sys:Long) = {
   Call("POST", "/ajax/ajax_remove_link/" + implicitly[PathBindable[Long]].unbind("id_sys", id_sys))
}
                                                        
 
// @LINE:83
def ajaxAddLink(id_sys:Long) = {
   Call("POST", "/ajax/ajax_add_link/" + implicitly[PathBindable[Long]].unbind("id_sys", id_sys))
}
                                                        

                      
    
}
                            

// @LINE:75
// @LINE:41
// @LINE:40
// @LINE:39
// @LINE:38
class ReverseOperatorSets {
    


 
// @LINE:39
def create(id_sys:Long) = {
   Call("POST", "/system/" + implicitly[PathBindable[Long]].unbind("id_sys", id_sys) + "/opset/new")
}
                                                        
 
// @LINE:38
def prepareCreate(id_sys:Long) = {
   Call("GET", "/system/" + implicitly[PathBindable[Long]].unbind("id_sys", id_sys) + "/opset/new")
}
                                                        
 
// @LINE:75
def ajaxGetNumParamsForOption() = {
   Call("GET", "/ajax/get_num_params_operator_option")
}
                                                        
 
// @LINE:41
def delete(id_sys:Long, id_opset:java.lang.Integer) = {
   Call("GET", "/system/" + implicitly[PathBindable[Long]].unbind("id_sys", id_sys) + "/opset/" + implicitly[PathBindable[java.lang.Integer]].unbind("id_opset", id_opset) + "/del")
}
                                                        
 
// @LINE:40
def detail(id_sys:Long, id_opset:java.lang.Integer) = {
   Call("GET", "/system/" + implicitly[PathBindable[Long]].unbind("id_sys", id_sys) + "/opset/" + implicitly[PathBindable[java.lang.Integer]].unbind("id_opset", id_opset))
}
                                                        

                      
    
}
                            

// @LINE:73
// @LINE:49
// @LINE:48
// @LINE:47
// @LINE:46
// @LINE:45
// @LINE:44
class ReverseTypes {
    


 
// @LINE:45
def create(id_sys:Long) = {
   Call("POST", "/system/" + implicitly[PathBindable[Long]].unbind("id_sys", id_sys) + "/type/new")
}
                                                        
 
// @LINE:48
def prepareEdit(id_sys:Long, id_type:java.lang.Integer) = {
   Call("GET", "/system/" + implicitly[PathBindable[Long]].unbind("id_sys", id_sys) + "/type/" + implicitly[PathBindable[java.lang.Integer]].unbind("id_type", id_type) + "/edit")
}
                                                        
 
// @LINE:44
def prepareCreate(id_sys:Long) = {
   Call("GET", "/system/" + implicitly[PathBindable[Long]].unbind("id_sys", id_sys) + "/type/new")
}
                                                        
 
// @LINE:73
def ajaxGetMFsPlotData() = {
   Call("GET", "/ajax/get_mfs_plot_data")
}
                                                        
 
// @LINE:49
def edit(id_sys:Long, id_type:java.lang.Integer) = {
   Call("POST", "/system/" + implicitly[PathBindable[Long]].unbind("id_sys", id_sys) + "/type/" + implicitly[PathBindable[java.lang.Integer]].unbind("id_type", id_type) + "/edit")
}
                                                        
 
// @LINE:47
def delete(id_sys:Long, id_type:java.lang.Integer) = {
   Call("GET", "/system/" + implicitly[PathBindable[Long]].unbind("id_sys", id_sys) + "/type/" + implicitly[PathBindable[java.lang.Integer]].unbind("id_type", id_type) + "/del")
}
                                                        
 
// @LINE:46
def detail(id_sys:Long, id_type:java.lang.Integer) = {
   Call("GET", "/system/" + implicitly[PathBindable[Long]].unbind("id_sys", id_sys) + "/type/" + implicitly[PathBindable[java.lang.Integer]].unbind("id_type", id_type))
}
                                                        

                      
    
}
                            

// @LINE:6
class ReverseApplication {
    


 
// @LINE:6
def index() = {
   Call("GET", "/")
}
                                                        

                      
    
}
                            

// @LINE:80
// @LINE:35
// @LINE:33
// @LINE:32
// @LINE:31
// @LINE:30
// @LINE:29
// @LINE:28
class ReverseSystems {
    


 
// @LINE:32
def delete(id_sys:Long) = {
   Call("POST", "/system/" + implicitly[PathBindable[Long]].unbind("id_sys", id_sys) + "/del")
}
                                                        
 
// @LINE:29
def prepareCreate() = {
   Call("GET", "/system/new")
}
                                                        
 
// @LINE:35
def print(id_sys:Long) = {
   Call("GET", "/system/" + implicitly[PathBindable[Long]].unbind("id_sys", id_sys) + "/print")
}
                                                        
 
// @LINE:30
def create() = {
   Call("POST", "/system/new")
}
                                                        
 
// @LINE:28
def list() = {
   Call("GET", "/system/list")
}
                                                        
 
// @LINE:33
def deleteVariable(id_sys:Long, id_variable:java.lang.Integer, kind:java.lang.Integer) = {
   Call("GET", "/system/" + implicitly[PathBindable[Long]].unbind("id_sys", id_sys) + "/var/" + implicitly[PathBindable[java.lang.Integer]].unbind("kind", kind) + "/" + implicitly[PathBindable[java.lang.Integer]].unbind("id_variable", id_variable) + "/del")
}
                                                        
 
// @LINE:80
def ajaxAddVariable(id_sys:Long) = {
   Call("POST", "/ajax/ajax_add_variable/" + implicitly[PathBindable[Long]].unbind("id_sys", id_sys))
}
                                                        
 
// @LINE:31
def detail(id_sys:Long) = {
   Call("GET", "/system/" + implicitly[PathBindable[Long]].unbind("id_sys", id_sys))
}
                                                        

                      
    
}
                            
}
                    


// @LINE:84
// @LINE:83
// @LINE:82
// @LINE:80
// @LINE:78
// @LINE:77
// @LINE:75
// @LINE:73
// @LINE:72
// @LINE:64
// @LINE:63
// @LINE:62
// @LINE:61
// @LINE:60
// @LINE:56
// @LINE:55
// @LINE:54
// @LINE:53
// @LINE:49
// @LINE:48
// @LINE:47
// @LINE:46
// @LINE:45
// @LINE:44
// @LINE:41
// @LINE:40
// @LINE:39
// @LINE:38
// @LINE:35
// @LINE:33
// @LINE:32
// @LINE:31
// @LINE:30
// @LINE:29
// @LINE:28
// @LINE:20
// @LINE:19
// @LINE:18
// @LINE:15
// @LINE:14
// @LINE:13
// @LINE:9
// @LINE:6
package controllers.javascript {

// @LINE:9
class ReverseAssets {
    


 
// @LINE:9
def at = JavascriptReverseRoute(
   "controllers.Assets.at",
   """
      function(file) {
      return _wA({method:"GET", url:"/assets/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("file", file)})
      }
   """
)
                                                        

                      
    
}
                            

// @LINE:20
// @LINE:19
// @LINE:18
// @LINE:15
// @LINE:14
// @LINE:13
class ReverseAuthentication {
    


 
// @LINE:14
def edit = JavascriptReverseRoute(
   "controllers.Authentication.edit",
   """
      function() {
      return _wA({method:"GET", url:"/users/fakeuser"})
      }
   """
)
                                                        
 
// @LINE:18
def login = JavascriptReverseRoute(
   "controllers.Authentication.login",
   """
      function() {
      return _wA({method:"GET", url:"/login"})
      }
   """
)
                                                        
 
// @LINE:20
def logout = JavascriptReverseRoute(
   "controllers.Authentication.logout",
   """
      function() {
      return _wA({method:"GET", url:"/logout"})
      }
   """
)
                                                        
 
// @LINE:13
def register = JavascriptReverseRoute(
   "controllers.Authentication.register",
   """
      function() {
      return _wA({method:"GET", url:"/signup"})
      }
   """
)
                                                        
 
// @LINE:15
def submit = JavascriptReverseRoute(
   "controllers.Authentication.submit",
   """
      function() {
      return _wA({method:"POST", url:"/signup"})
      }
   """
)
                                                        
 
// @LINE:19
def authenticate = JavascriptReverseRoute(
   "controllers.Authentication.authenticate",
   """
      function() {
      return _wA({method:"POST", url:"/login"})
      }
   """
)
                                                        

                      
    
}
                            

// @LINE:78
// @LINE:77
// @LINE:64
// @LINE:63
// @LINE:62
// @LINE:61
// @LINE:60
class ReverseRuleBases {
    


 
// @LINE:61
def create = JavascriptReverseRoute(
   "controllers.RuleBases.create",
   """
      function(id_sys) {
      return _wA({method:"POST", url:"/system/" + (""" + implicitly[PathBindable[Long]].javascriptUnbind + """)("id_sys", id_sys) + "/rb/new"})
      }
   """
)
                                                        
 
// @LINE:60
def prepareCreate = JavascriptReverseRoute(
   "controllers.RuleBases.prepareCreate",
   """
      function(id_sys) {
      return _wA({method:"GET", url:"/system/" + (""" + implicitly[PathBindable[Long]].javascriptUnbind + """)("id_sys", id_sys) + "/rb/new"})
      }
   """
)
                                                        
 
// @LINE:77
def ajaxAddVariable = JavascriptReverseRoute(
   "controllers.RuleBases.ajaxAddVariable",
   """
      function(id_sys,id_rb) {
      return _wA({method:"POST", url:"/ajax/ajax_add_variable_rb/" + (""" + implicitly[PathBindable[Long]].javascriptUnbind + """)("id_sys", id_sys) + "/" + (""" + implicitly[PathBindable[java.lang.Integer]].javascriptUnbind + """)("id_rb", id_rb)})
      }
   """
)
                                                        
 
// @LINE:78
def ajaxAddRuleFromTable = JavascriptReverseRoute(
   "controllers.RuleBases.ajaxAddRuleFromTable",
   """
      function(id_sys,id_rb) {
      return _wA({method:"POST", url:"/ajax/ajax_add_rule_from_table/" + (""" + implicitly[PathBindable[Long]].javascriptUnbind + """)("id_sys", id_sys) + "/" + (""" + implicitly[PathBindable[java.lang.Integer]].javascriptUnbind + """)("id_rb", id_rb)})
      }
   """
)
                                                        
 
// @LINE:63
def deleteVariable = JavascriptReverseRoute(
   "controllers.RuleBases.deleteVariable",
   """
      function(id_sys,id_rb,id_variable,kind) {
      return _wA({method:"GET", url:"/system/" + (""" + implicitly[PathBindable[Long]].javascriptUnbind + """)("id_sys", id_sys) + "/rb/" + (""" + implicitly[PathBindable[java.lang.Integer]].javascriptUnbind + """)("id_rb", id_rb) + "/var/" + (""" + implicitly[PathBindable[java.lang.Integer]].javascriptUnbind + """)("kind", kind) + "/" + (""" + implicitly[PathBindable[java.lang.Integer]].javascriptUnbind + """)("id_variable", id_variable) + "/del"})
      }
   """
)
                                                        
 
// @LINE:62
def detail = JavascriptReverseRoute(
   "controllers.RuleBases.detail",
   """
      function(id_sys,id_rb) {
      return _wA({method:"GET", url:"/system/" + (""" + implicitly[PathBindable[Long]].javascriptUnbind + """)("id_sys", id_sys) + "/rb/" + (""" + implicitly[PathBindable[java.lang.Integer]].javascriptUnbind + """)("id_rb", id_rb)})
      }
   """
)
                                                        
 
// @LINE:64
def deleteRule = JavascriptReverseRoute(
   "controllers.RuleBases.deleteRule",
   """
      function(id_sys,id_rb,id_rule) {
      return _wA({method:"GET", url:"/system/" + (""" + implicitly[PathBindable[Long]].javascriptUnbind + """)("id_sys", id_sys) + "/rb/" + (""" + implicitly[PathBindable[java.lang.Integer]].javascriptUnbind + """)("id_rb", id_rb) + "/rule/" + (""" + implicitly[PathBindable[java.lang.Integer]].javascriptUnbind + """)("id_rule", id_rule) + "/del"})
      }
   """
)
                                                        

                      
    
}
                            

// @LINE:72
// @LINE:56
// @LINE:55
// @LINE:54
// @LINE:53
class ReverseMFs {
    


 
// @LINE:53
def prepareCreate = JavascriptReverseRoute(
   "controllers.MFs.prepareCreate",
   """
      function(id_sys,id_type) {
      return _wA({method:"GET", url:"/system/" + (""" + implicitly[PathBindable[Long]].javascriptUnbind + """)("id_sys", id_sys) + "/type/" + (""" + implicitly[PathBindable[java.lang.Integer]].javascriptUnbind + """)("id_type", id_type) + "/mf/new"})
      }
   """
)
                                                        
 
// @LINE:56
def delete = JavascriptReverseRoute(
   "controllers.MFs.delete",
   """
      function(id_sys,id_type,id_mf) {
      return _wA({method:"GET", url:"/system/" + (""" + implicitly[PathBindable[Long]].javascriptUnbind + """)("id_sys", id_sys) + "/type/" + (""" + implicitly[PathBindable[java.lang.Integer]].javascriptUnbind + """)("id_type", id_type) + "/mf/" + (""" + implicitly[PathBindable[java.lang.Integer]].javascriptUnbind + """)("id_mf", id_mf) + "/del"})
      }
   """
)
                                                        
 
// @LINE:72
def ajaxGetNumParams = JavascriptReverseRoute(
   "controllers.MFs.ajaxGetNumParams",
   """
      function() {
      return _wA({method:"GET", url:"/ajax/get_num_params_mf"})
      }
   """
)
                                                        
 
// @LINE:54
def create = JavascriptReverseRoute(
   "controllers.MFs.create",
   """
      function(id_sys,id_type) {
      return _wA({method:"POST", url:"/system/" + (""" + implicitly[PathBindable[Long]].javascriptUnbind + """)("id_sys", id_sys) + "/type/" + (""" + implicitly[PathBindable[java.lang.Integer]].javascriptUnbind + """)("id_type", id_type) + "/mf/new"})
      }
   """
)
                                                        
 
// @LINE:55
def detail = JavascriptReverseRoute(
   "controllers.MFs.detail",
   """
      function(id_sys,id_type,id_mf) {
      return _wA({method:"GET", url:"/system/" + (""" + implicitly[PathBindable[Long]].javascriptUnbind + """)("id_sys", id_sys) + "/type/" + (""" + implicitly[PathBindable[java.lang.Integer]].javascriptUnbind + """)("id_type", id_type) + "/mf/" + (""" + implicitly[PathBindable[java.lang.Integer]].javascriptUnbind + """)("id_mf", id_mf)})
      }
   """
)
                                                        

                      
    
}
                            

// @LINE:84
// @LINE:83
// @LINE:82
class ReverseRuleBaseCalls {
    


 
// @LINE:82
def ajaxAddCall = JavascriptReverseRoute(
   "controllers.RuleBaseCalls.ajaxAddCall",
   """
      function(id_sys,id_rb) {
      return _wA({method:"POST", url:"/ajax/ajax_add_call/" + (""" + implicitly[PathBindable[Long]].javascriptUnbind + """)("id_sys", id_sys) + "/" + (""" + implicitly[PathBindable[java.lang.Integer]].javascriptUnbind + """)("id_rb", id_rb)})
      }
   """
)
                                                        
 
// @LINE:84
def ajaxRemoveLink = JavascriptReverseRoute(
   "controllers.RuleBaseCalls.ajaxRemoveLink",
   """
      function(id_sys) {
      return _wA({method:"POST", url:"/ajax/ajax_remove_link/" + (""" + implicitly[PathBindable[Long]].javascriptUnbind + """)("id_sys", id_sys)})
      }
   """
)
                                                        
 
// @LINE:83
def ajaxAddLink = JavascriptReverseRoute(
   "controllers.RuleBaseCalls.ajaxAddLink",
   """
      function(id_sys) {
      return _wA({method:"POST", url:"/ajax/ajax_add_link/" + (""" + implicitly[PathBindable[Long]].javascriptUnbind + """)("id_sys", id_sys)})
      }
   """
)
                                                        

                      
    
}
                            

// @LINE:75
// @LINE:41
// @LINE:40
// @LINE:39
// @LINE:38
class ReverseOperatorSets {
    


 
// @LINE:39
def create = JavascriptReverseRoute(
   "controllers.OperatorSets.create",
   """
      function(id_sys) {
      return _wA({method:"POST", url:"/system/" + (""" + implicitly[PathBindable[Long]].javascriptUnbind + """)("id_sys", id_sys) + "/opset/new"})
      }
   """
)
                                                        
 
// @LINE:38
def prepareCreate = JavascriptReverseRoute(
   "controllers.OperatorSets.prepareCreate",
   """
      function(id_sys) {
      return _wA({method:"GET", url:"/system/" + (""" + implicitly[PathBindable[Long]].javascriptUnbind + """)("id_sys", id_sys) + "/opset/new"})
      }
   """
)
                                                        
 
// @LINE:75
def ajaxGetNumParamsForOption = JavascriptReverseRoute(
   "controllers.OperatorSets.ajaxGetNumParamsForOption",
   """
      function() {
      return _wA({method:"GET", url:"/ajax/get_num_params_operator_option"})
      }
   """
)
                                                        
 
// @LINE:41
def delete = JavascriptReverseRoute(
   "controllers.OperatorSets.delete",
   """
      function(id_sys,id_opset) {
      return _wA({method:"GET", url:"/system/" + (""" + implicitly[PathBindable[Long]].javascriptUnbind + """)("id_sys", id_sys) + "/opset/" + (""" + implicitly[PathBindable[java.lang.Integer]].javascriptUnbind + """)("id_opset", id_opset) + "/del"})
      }
   """
)
                                                        
 
// @LINE:40
def detail = JavascriptReverseRoute(
   "controllers.OperatorSets.detail",
   """
      function(id_sys,id_opset) {
      return _wA({method:"GET", url:"/system/" + (""" + implicitly[PathBindable[Long]].javascriptUnbind + """)("id_sys", id_sys) + "/opset/" + (""" + implicitly[PathBindable[java.lang.Integer]].javascriptUnbind + """)("id_opset", id_opset)})
      }
   """
)
                                                        

                      
    
}
                            

// @LINE:73
// @LINE:49
// @LINE:48
// @LINE:47
// @LINE:46
// @LINE:45
// @LINE:44
class ReverseTypes {
    


 
// @LINE:45
def create = JavascriptReverseRoute(
   "controllers.Types.create",
   """
      function(id_sys) {
      return _wA({method:"POST", url:"/system/" + (""" + implicitly[PathBindable[Long]].javascriptUnbind + """)("id_sys", id_sys) + "/type/new"})
      }
   """
)
                                                        
 
// @LINE:48
def prepareEdit = JavascriptReverseRoute(
   "controllers.Types.prepareEdit",
   """
      function(id_sys,id_type) {
      return _wA({method:"GET", url:"/system/" + (""" + implicitly[PathBindable[Long]].javascriptUnbind + """)("id_sys", id_sys) + "/type/" + (""" + implicitly[PathBindable[java.lang.Integer]].javascriptUnbind + """)("id_type", id_type) + "/edit"})
      }
   """
)
                                                        
 
// @LINE:44
def prepareCreate = JavascriptReverseRoute(
   "controllers.Types.prepareCreate",
   """
      function(id_sys) {
      return _wA({method:"GET", url:"/system/" + (""" + implicitly[PathBindable[Long]].javascriptUnbind + """)("id_sys", id_sys) + "/type/new"})
      }
   """
)
                                                        
 
// @LINE:73
def ajaxGetMFsPlotData = JavascriptReverseRoute(
   "controllers.Types.ajaxGetMFsPlotData",
   """
      function() {
      return _wA({method:"GET", url:"/ajax/get_mfs_plot_data"})
      }
   """
)
                                                        
 
// @LINE:49
def edit = JavascriptReverseRoute(
   "controllers.Types.edit",
   """
      function(id_sys,id_type) {
      return _wA({method:"POST", url:"/system/" + (""" + implicitly[PathBindable[Long]].javascriptUnbind + """)("id_sys", id_sys) + "/type/" + (""" + implicitly[PathBindable[java.lang.Integer]].javascriptUnbind + """)("id_type", id_type) + "/edit"})
      }
   """
)
                                                        
 
// @LINE:47
def delete = JavascriptReverseRoute(
   "controllers.Types.delete",
   """
      function(id_sys,id_type) {
      return _wA({method:"GET", url:"/system/" + (""" + implicitly[PathBindable[Long]].javascriptUnbind + """)("id_sys", id_sys) + "/type/" + (""" + implicitly[PathBindable[java.lang.Integer]].javascriptUnbind + """)("id_type", id_type) + "/del"})
      }
   """
)
                                                        
 
// @LINE:46
def detail = JavascriptReverseRoute(
   "controllers.Types.detail",
   """
      function(id_sys,id_type) {
      return _wA({method:"GET", url:"/system/" + (""" + implicitly[PathBindable[Long]].javascriptUnbind + """)("id_sys", id_sys) + "/type/" + (""" + implicitly[PathBindable[java.lang.Integer]].javascriptUnbind + """)("id_type", id_type)})
      }
   """
)
                                                        

                      
    
}
                            

// @LINE:6
class ReverseApplication {
    


 
// @LINE:6
def index = JavascriptReverseRoute(
   "controllers.Application.index",
   """
      function() {
      return _wA({method:"GET", url:"/"})
      }
   """
)
                                                        

                      
    
}
                            

// @LINE:80
// @LINE:35
// @LINE:33
// @LINE:32
// @LINE:31
// @LINE:30
// @LINE:29
// @LINE:28
class ReverseSystems {
    


 
// @LINE:32
def delete = JavascriptReverseRoute(
   "controllers.Systems.delete",
   """
      function(id_sys) {
      return _wA({method:"POST", url:"/system/" + (""" + implicitly[PathBindable[Long]].javascriptUnbind + """)("id_sys", id_sys) + "/del"})
      }
   """
)
                                                        
 
// @LINE:29
def prepareCreate = JavascriptReverseRoute(
   "controllers.Systems.prepareCreate",
   """
      function() {
      return _wA({method:"GET", url:"/system/new"})
      }
   """
)
                                                        
 
// @LINE:35
def print = JavascriptReverseRoute(
   "controllers.Systems.print",
   """
      function(id_sys) {
      return _wA({method:"GET", url:"/system/" + (""" + implicitly[PathBindable[Long]].javascriptUnbind + """)("id_sys", id_sys) + "/print"})
      }
   """
)
                                                        
 
// @LINE:30
def create = JavascriptReverseRoute(
   "controllers.Systems.create",
   """
      function() {
      return _wA({method:"POST", url:"/system/new"})
      }
   """
)
                                                        
 
// @LINE:28
def list = JavascriptReverseRoute(
   "controllers.Systems.list",
   """
      function() {
      return _wA({method:"GET", url:"/system/list"})
      }
   """
)
                                                        
 
// @LINE:33
def deleteVariable = JavascriptReverseRoute(
   "controllers.Systems.deleteVariable",
   """
      function(id_sys,id_variable,kind) {
      return _wA({method:"GET", url:"/system/" + (""" + implicitly[PathBindable[Long]].javascriptUnbind + """)("id_sys", id_sys) + "/var/" + (""" + implicitly[PathBindable[java.lang.Integer]].javascriptUnbind + """)("kind", kind) + "/" + (""" + implicitly[PathBindable[java.lang.Integer]].javascriptUnbind + """)("id_variable", id_variable) + "/del"})
      }
   """
)
                                                        
 
// @LINE:80
def ajaxAddVariable = JavascriptReverseRoute(
   "controllers.Systems.ajaxAddVariable",
   """
      function(id_sys) {
      return _wA({method:"POST", url:"/ajax/ajax_add_variable/" + (""" + implicitly[PathBindable[Long]].javascriptUnbind + """)("id_sys", id_sys)})
      }
   """
)
                                                        
 
// @LINE:31
def detail = JavascriptReverseRoute(
   "controllers.Systems.detail",
   """
      function(id_sys) {
      return _wA({method:"GET", url:"/system/" + (""" + implicitly[PathBindable[Long]].javascriptUnbind + """)("id_sys", id_sys)})
      }
   """
)
                                                        

                      
    
}
                            
}
                    


// @LINE:84
// @LINE:83
// @LINE:82
// @LINE:80
// @LINE:78
// @LINE:77
// @LINE:75
// @LINE:73
// @LINE:72
// @LINE:64
// @LINE:63
// @LINE:62
// @LINE:61
// @LINE:60
// @LINE:56
// @LINE:55
// @LINE:54
// @LINE:53
// @LINE:49
// @LINE:48
// @LINE:47
// @LINE:46
// @LINE:45
// @LINE:44
// @LINE:41
// @LINE:40
// @LINE:39
// @LINE:38
// @LINE:35
// @LINE:33
// @LINE:32
// @LINE:31
// @LINE:30
// @LINE:29
// @LINE:28
// @LINE:20
// @LINE:19
// @LINE:18
// @LINE:15
// @LINE:14
// @LINE:13
// @LINE:9
// @LINE:6
package controllers.ref {

// @LINE:9
class ReverseAssets {
    


 
// @LINE:9
def at(path:String, file:String) = new play.api.mvc.HandlerRef(
   controllers.Assets.at(path, file), HandlerDef(this, "controllers.Assets", "at", Seq(classOf[String], classOf[String]))
)
                              

                      
    
}
                            

// @LINE:20
// @LINE:19
// @LINE:18
// @LINE:15
// @LINE:14
// @LINE:13
class ReverseAuthentication {
    


 
// @LINE:14
def edit() = new play.api.mvc.HandlerRef(
   controllers.Authentication.edit(), HandlerDef(this, "controllers.Authentication", "edit", Seq())
)
                              
 
// @LINE:18
def login() = new play.api.mvc.HandlerRef(
   controllers.Authentication.login(), HandlerDef(this, "controllers.Authentication", "login", Seq())
)
                              
 
// @LINE:20
def logout() = new play.api.mvc.HandlerRef(
   controllers.Authentication.logout(), HandlerDef(this, "controllers.Authentication", "logout", Seq())
)
                              
 
// @LINE:13
def register() = new play.api.mvc.HandlerRef(
   controllers.Authentication.register(), HandlerDef(this, "controllers.Authentication", "register", Seq())
)
                              
 
// @LINE:15
def submit() = new play.api.mvc.HandlerRef(
   controllers.Authentication.submit(), HandlerDef(this, "controllers.Authentication", "submit", Seq())
)
                              
 
// @LINE:19
def authenticate() = new play.api.mvc.HandlerRef(
   controllers.Authentication.authenticate(), HandlerDef(this, "controllers.Authentication", "authenticate", Seq())
)
                              

                      
    
}
                            

// @LINE:78
// @LINE:77
// @LINE:64
// @LINE:63
// @LINE:62
// @LINE:61
// @LINE:60
class ReverseRuleBases {
    


 
// @LINE:61
def create(id_sys:Long) = new play.api.mvc.HandlerRef(
   controllers.RuleBases.create(id_sys), HandlerDef(this, "controllers.RuleBases", "create", Seq(classOf[Long]))
)
                              
 
// @LINE:60
def prepareCreate(id_sys:Long) = new play.api.mvc.HandlerRef(
   controllers.RuleBases.prepareCreate(id_sys), HandlerDef(this, "controllers.RuleBases", "prepareCreate", Seq(classOf[Long]))
)
                              
 
// @LINE:77
def ajaxAddVariable(id_sys:Long, id_rb:java.lang.Integer) = new play.api.mvc.HandlerRef(
   controllers.RuleBases.ajaxAddVariable(id_sys, id_rb), HandlerDef(this, "controllers.RuleBases", "ajaxAddVariable", Seq(classOf[Long], classOf[java.lang.Integer]))
)
                              
 
// @LINE:78
def ajaxAddRuleFromTable(id_sys:Long, id_rb:java.lang.Integer) = new play.api.mvc.HandlerRef(
   controllers.RuleBases.ajaxAddRuleFromTable(id_sys, id_rb), HandlerDef(this, "controllers.RuleBases", "ajaxAddRuleFromTable", Seq(classOf[Long], classOf[java.lang.Integer]))
)
                              
 
// @LINE:63
def deleteVariable(id_sys:Long, id_rb:java.lang.Integer, id_variable:java.lang.Integer, kind:java.lang.Integer) = new play.api.mvc.HandlerRef(
   controllers.RuleBases.deleteVariable(id_sys, id_rb, id_variable, kind), HandlerDef(this, "controllers.RuleBases", "deleteVariable", Seq(classOf[Long], classOf[java.lang.Integer], classOf[java.lang.Integer], classOf[java.lang.Integer]))
)
                              
 
// @LINE:62
def detail(id_sys:Long, id_rb:java.lang.Integer) = new play.api.mvc.HandlerRef(
   controllers.RuleBases.detail(id_sys, id_rb), HandlerDef(this, "controllers.RuleBases", "detail", Seq(classOf[Long], classOf[java.lang.Integer]))
)
                              
 
// @LINE:64
def deleteRule(id_sys:Long, id_rb:java.lang.Integer, id_rule:java.lang.Integer) = new play.api.mvc.HandlerRef(
   controllers.RuleBases.deleteRule(id_sys, id_rb, id_rule), HandlerDef(this, "controllers.RuleBases", "deleteRule", Seq(classOf[Long], classOf[java.lang.Integer], classOf[java.lang.Integer]))
)
                              

                      
    
}
                            

// @LINE:72
// @LINE:56
// @LINE:55
// @LINE:54
// @LINE:53
class ReverseMFs {
    


 
// @LINE:53
def prepareCreate(id_sys:Long, id_type:java.lang.Integer) = new play.api.mvc.HandlerRef(
   controllers.MFs.prepareCreate(id_sys, id_type), HandlerDef(this, "controllers.MFs", "prepareCreate", Seq(classOf[Long], classOf[java.lang.Integer]))
)
                              
 
// @LINE:56
def delete(id_sys:Long, id_type:java.lang.Integer, id_mf:java.lang.Integer) = new play.api.mvc.HandlerRef(
   controllers.MFs.delete(id_sys, id_type, id_mf), HandlerDef(this, "controllers.MFs", "delete", Seq(classOf[Long], classOf[java.lang.Integer], classOf[java.lang.Integer]))
)
                              
 
// @LINE:72
def ajaxGetNumParams() = new play.api.mvc.HandlerRef(
   controllers.MFs.ajaxGetNumParams(), HandlerDef(this, "controllers.MFs", "ajaxGetNumParams", Seq())
)
                              
 
// @LINE:54
def create(id_sys:Long, id_type:java.lang.Integer) = new play.api.mvc.HandlerRef(
   controllers.MFs.create(id_sys, id_type), HandlerDef(this, "controllers.MFs", "create", Seq(classOf[Long], classOf[java.lang.Integer]))
)
                              
 
// @LINE:55
def detail(id_sys:Long, id_type:java.lang.Integer, id_mf:java.lang.Integer) = new play.api.mvc.HandlerRef(
   controllers.MFs.detail(id_sys, id_type, id_mf), HandlerDef(this, "controllers.MFs", "detail", Seq(classOf[Long], classOf[java.lang.Integer], classOf[java.lang.Integer]))
)
                              

                      
    
}
                            

// @LINE:84
// @LINE:83
// @LINE:82
class ReverseRuleBaseCalls {
    


 
// @LINE:82
def ajaxAddCall(id_sys:Long, id_rb:java.lang.Integer) = new play.api.mvc.HandlerRef(
   controllers.RuleBaseCalls.ajaxAddCall(id_sys, id_rb), HandlerDef(this, "controllers.RuleBaseCalls", "ajaxAddCall", Seq(classOf[Long], classOf[java.lang.Integer]))
)
                              
 
// @LINE:84
def ajaxRemoveLink(id_sys:Long) = new play.api.mvc.HandlerRef(
   controllers.RuleBaseCalls.ajaxRemoveLink(id_sys), HandlerDef(this, "controllers.RuleBaseCalls", "ajaxRemoveLink", Seq(classOf[Long]))
)
                              
 
// @LINE:83
def ajaxAddLink(id_sys:Long) = new play.api.mvc.HandlerRef(
   controllers.RuleBaseCalls.ajaxAddLink(id_sys), HandlerDef(this, "controllers.RuleBaseCalls", "ajaxAddLink", Seq(classOf[Long]))
)
                              

                      
    
}
                            

// @LINE:75
// @LINE:41
// @LINE:40
// @LINE:39
// @LINE:38
class ReverseOperatorSets {
    


 
// @LINE:39
def create(id_sys:Long) = new play.api.mvc.HandlerRef(
   controllers.OperatorSets.create(id_sys), HandlerDef(this, "controllers.OperatorSets", "create", Seq(classOf[Long]))
)
                              
 
// @LINE:38
def prepareCreate(id_sys:Long) = new play.api.mvc.HandlerRef(
   controllers.OperatorSets.prepareCreate(id_sys), HandlerDef(this, "controllers.OperatorSets", "prepareCreate", Seq(classOf[Long]))
)
                              
 
// @LINE:75
def ajaxGetNumParamsForOption() = new play.api.mvc.HandlerRef(
   controllers.OperatorSets.ajaxGetNumParamsForOption(), HandlerDef(this, "controllers.OperatorSets", "ajaxGetNumParamsForOption", Seq())
)
                              
 
// @LINE:41
def delete(id_sys:Long, id_opset:java.lang.Integer) = new play.api.mvc.HandlerRef(
   controllers.OperatorSets.delete(id_sys, id_opset), HandlerDef(this, "controllers.OperatorSets", "delete", Seq(classOf[Long], classOf[java.lang.Integer]))
)
                              
 
// @LINE:40
def detail(id_sys:Long, id_opset:java.lang.Integer) = new play.api.mvc.HandlerRef(
   controllers.OperatorSets.detail(id_sys, id_opset), HandlerDef(this, "controllers.OperatorSets", "detail", Seq(classOf[Long], classOf[java.lang.Integer]))
)
                              

                      
    
}
                            

// @LINE:73
// @LINE:49
// @LINE:48
// @LINE:47
// @LINE:46
// @LINE:45
// @LINE:44
class ReverseTypes {
    


 
// @LINE:45
def create(id_sys:Long) = new play.api.mvc.HandlerRef(
   controllers.Types.create(id_sys), HandlerDef(this, "controllers.Types", "create", Seq(classOf[Long]))
)
                              
 
// @LINE:48
def prepareEdit(id_sys:Long, id_type:java.lang.Integer) = new play.api.mvc.HandlerRef(
   controllers.Types.prepareEdit(id_sys, id_type), HandlerDef(this, "controllers.Types", "prepareEdit", Seq(classOf[Long], classOf[java.lang.Integer]))
)
                              
 
// @LINE:44
def prepareCreate(id_sys:Long) = new play.api.mvc.HandlerRef(
   controllers.Types.prepareCreate(id_sys), HandlerDef(this, "controllers.Types", "prepareCreate", Seq(classOf[Long]))
)
                              
 
// @LINE:73
def ajaxGetMFsPlotData() = new play.api.mvc.HandlerRef(
   controllers.Types.ajaxGetMFsPlotData(), HandlerDef(this, "controllers.Types", "ajaxGetMFsPlotData", Seq())
)
                              
 
// @LINE:49
def edit(id_sys:Long, id_type:java.lang.Integer) = new play.api.mvc.HandlerRef(
   controllers.Types.edit(id_sys, id_type), HandlerDef(this, "controllers.Types", "edit", Seq(classOf[Long], classOf[java.lang.Integer]))
)
                              
 
// @LINE:47
def delete(id_sys:Long, id_type:java.lang.Integer) = new play.api.mvc.HandlerRef(
   controllers.Types.delete(id_sys, id_type), HandlerDef(this, "controllers.Types", "delete", Seq(classOf[Long], classOf[java.lang.Integer]))
)
                              
 
// @LINE:46
def detail(id_sys:Long, id_type:java.lang.Integer) = new play.api.mvc.HandlerRef(
   controllers.Types.detail(id_sys, id_type), HandlerDef(this, "controllers.Types", "detail", Seq(classOf[Long], classOf[java.lang.Integer]))
)
                              

                      
    
}
                            

// @LINE:6
class ReverseApplication {
    


 
// @LINE:6
def index() = new play.api.mvc.HandlerRef(
   controllers.Application.index(), HandlerDef(this, "controllers.Application", "index", Seq())
)
                              

                      
    
}
                            

// @LINE:80
// @LINE:35
// @LINE:33
// @LINE:32
// @LINE:31
// @LINE:30
// @LINE:29
// @LINE:28
class ReverseSystems {
    


 
// @LINE:32
def delete(id_sys:Long) = new play.api.mvc.HandlerRef(
   controllers.Systems.delete(id_sys), HandlerDef(this, "controllers.Systems", "delete", Seq(classOf[Long]))
)
                              
 
// @LINE:29
def prepareCreate() = new play.api.mvc.HandlerRef(
   controllers.Systems.prepareCreate(), HandlerDef(this, "controllers.Systems", "prepareCreate", Seq())
)
                              
 
// @LINE:35
def print(id_sys:Long) = new play.api.mvc.HandlerRef(
   controllers.Systems.print(id_sys), HandlerDef(this, "controllers.Systems", "print", Seq(classOf[Long]))
)
                              
 
// @LINE:30
def create() = new play.api.mvc.HandlerRef(
   controllers.Systems.create(), HandlerDef(this, "controllers.Systems", "create", Seq())
)
                              
 
// @LINE:28
def list() = new play.api.mvc.HandlerRef(
   controllers.Systems.list(), HandlerDef(this, "controllers.Systems", "list", Seq())
)
                              
 
// @LINE:33
def deleteVariable(id_sys:Long, id_variable:java.lang.Integer, kind:java.lang.Integer) = new play.api.mvc.HandlerRef(
   controllers.Systems.deleteVariable(id_sys, id_variable, kind), HandlerDef(this, "controllers.Systems", "deleteVariable", Seq(classOf[Long], classOf[java.lang.Integer], classOf[java.lang.Integer]))
)
                              
 
// @LINE:80
def ajaxAddVariable(id_sys:Long) = new play.api.mvc.HandlerRef(
   controllers.Systems.ajaxAddVariable(id_sys), HandlerDef(this, "controllers.Systems", "ajaxAddVariable", Seq(classOf[Long]))
)
                              
 
// @LINE:31
def detail(id_sys:Long) = new play.api.mvc.HandlerRef(
   controllers.Systems.detail(id_sys), HandlerDef(this, "controllers.Systems", "detail", Seq(classOf[Long]))
)
                              

                      
    
}
                            
}
                    
                