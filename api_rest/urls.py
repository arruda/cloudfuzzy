# -*- coding: utf-8 -*-
from django.conf.urls import patterns, include, url

from api_rest.api import SystemResource, InputVariableResource

from tastypie.api import Api

v1_api = Api(api_name='v1')
v1_api.register(SystemResource())
v1_api.register(InputVariableResource())


urlpatterns = patterns('',
    (r'^', include(v1_api.urls)),
)
