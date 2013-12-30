from tastypie.resources import ModelResource
from tastypie.authorization import Authorization

from fuzzy_modeling.models import SystemModel


class SystemResource(ModelResource):
    class Meta:
        queryset = SystemModel.objects.all()
        resource_name = 'system'
        authorization= Authorization()
        allowed_methods = ['get', 'post', 'delete', 'put']
        always_return_data = True
