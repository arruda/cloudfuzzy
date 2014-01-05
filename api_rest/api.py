from tastypie.resources import ModelResource
from tastypie.authentication import SessionAuthentication
from api_rest.authorizations import SystemOwnerAuthorization

from fuzzy_modeling.models import SystemModel


class SystemResource(ModelResource):
    class Meta:
        queryset = SystemModel.objects.all()
        resource_name = 'system'
        authentication= SessionAuthentication()
        authorization= SystemOwnerAuthorization()
        allowed_methods = ['get', 'post', 'delete', 'put']
        always_return_data = True
