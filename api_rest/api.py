from tastypie import fields
from tastypie.resources import ModelResource
from tastypie.authentication import SessionAuthentication
from tastypie.authorization import Authorization
from api_rest.authorizations import SystemOwnerAuthorization

from fuzzy_modeling.models import SystemModel, InputVariableModel


class SystemResource(ModelResource):
    class Meta:
        queryset = SystemModel.objects.all()
        resource_name = 'system'
        authentication= SessionAuthentication()
        authorization= SystemOwnerAuthorization(user_path='user')
        allowed_methods = ['get', 'post', 'delete', 'put']
        always_return_data = True

class InputVariableResource(ModelResource):
    system = fields.ForeignKey(SystemResource, 'system')

    class Meta:
        queryset = InputVariableModel.objects.all()
        resource_name = 'input_variable'
        authentication= SessionAuthentication()
        # authorization = Authorization()
        authorization= SystemOwnerAuthorization(user_path='system.user')
        allowed_methods = ['get', 'post', 'delete', 'put']
        always_return_data = True
