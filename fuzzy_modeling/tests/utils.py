
class ResetMock(object):

    @classmethod
    def reset_mock(cls,obj,attr_name):

        pre_mock_attr_name = '_pre_mock_' + attr_name
        default_attr = getattr(obj, pre_mock_attr_name, None)
        if default_attr:
            setattr(obj, attr_name, default_attr)
            delattr(obj, pre_mock_attr_name)


    @classmethod
    def reset_all_pre_mocks(cls,obj):
        fields = dir(obj)
        for attr_name in fields:
            if attr_name.startswith('_pre_mock_'):
                cls.reset_mock(obj, attr_name.strip('_pre_mock_'))

    @classmethod
    def set_pre_mock(cls, obj,attr_name):
        pre_mock_attr_name = '_pre_mock_' + attr_name
        default_attr = getattr(obj, attr_name)
        setattr(obj, pre_mock_attr_name, default_attr)
