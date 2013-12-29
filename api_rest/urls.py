# -*- coding: utf-8 -*-
from django.conf.urls import patterns, include, url

from api_rest.api import SystemResource

system_resource = SystemResource()

urlpatterns = patterns('',
    (r'^', include(system_resource.urls)),
)
