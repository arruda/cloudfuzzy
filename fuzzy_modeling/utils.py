import imp
import os

from django.utils.translation import ugettext_lazy as _

def get_class_by_python_path(path):
    "return the module/class of the given string, like 'fuzzy.fuzzify.Base.Base' "
    class_name = path.split('.')[-1]
    mod_name = path[:-1*(class_name.__len__()+1)]
    mod =  __import__(mod_name, fromlist=[class_name])
    return getattr(mod, class_name)

def get_choices_from_python_path_listing(path, ignores=[]):
    """
    Given the python path of a module, should list all modules inside it, and return a tuple
    of the given module names except for the ignored ones in the ignores list
    """
    ignores.append('__init__')
    class_name = path.split('.')[-1]
    mod_name = path[:-1*(class_name.__len__()+1)]
    mod =  __import__(mod_name, fromlist=[class_name])
    actual_mod = getattr(mod, class_name)
    pathname = actual_mod.__path__[0]
    MODULE_EXTENSIONS = ('.py', '.pyc', '.pyo')
    choices_names = set(
        [os.path.splitext(module)[0]
        for module in os.listdir(pathname)
        if module.endswith(MODULE_EXTENSIONS) and os.path.splitext(module)[0] not in ignores]
    )
    choices_complete = []
    for choice in choices_names:
        choice_complete_name = "%s.%s.%s" % (path, choice, choice)
        choices_complete.append(
            (choice_complete_name, _(choice))
        )
    return choices_complete
