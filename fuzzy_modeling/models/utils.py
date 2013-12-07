# -*- coding: utf-8 -*-

class PyFuzzyMixin(object):
    """
    Base mixing class for pyfuzzy model representation
    implements the "get_pyfuzzy" and "from_pyfuzzy" methods
    """
    # def __init__(self, arg):
    #     super(PyFuzzyMixin, self).__init__()
    #     self.arg = arg

    def get_pyfuzzy(self):
        """
        Return the Pyfuzzy class of this model
        """
        pass

    @classmethod
    def from_pyfuzzy(cls, pyfuzzy):
        """
        Return the model representation of an instance of the pyfuzzy attr
        """
        pass
