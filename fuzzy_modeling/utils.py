
def get_class_by_python_path(path):
    "return the module/class of the given string, like 'fuzzy.fuzzify.Base.Base' "
    class_name = path.split('.')[-1]
    mod_name = path[:-1*(class_name.__len__()+1)]
    mod =  __import__(mod_name, fromlist=[class_name])
    return getattr(mod, class_name)

