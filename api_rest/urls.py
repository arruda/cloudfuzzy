# -*- coding: utf-8 -*-
from __future__ import absolute_import

from django.conf.urls import patterns, include, url

from rest_framework.routers import DefaultRouter

from .api import SystemViewSet
from .api import UserViewSet
from .api import InputVariableViewSet
from .api import OutputVariableViewSet
from .api import DefuzzifyModelViewSet
from .api import UserSystemList


# Create a router and register our viewsets with it.
router = DefaultRouter()
router.register(r'users', UserViewSet)
router.register(r'systems', SystemViewSet)
router.register(r'input-variables', InputVariableViewSet)
router.register(r'output-variables', OutputVariableViewSet)
router.register(r'defuzzify', DefuzzifyModelViewSet)


user_urls = patterns('',
    url(r'^/(?P<username>[0-9a-zA-Z_-]+)/systems$',
        UserSystemList.as_view(),
        name='usersystem-list'),
)

urlpatterns = patterns('',
    url(r'^', include(router.urls)),
    url(r'^users', include(user_urls)),
)
