# -*- coding: utf-8 -*-
from __future__ import absolute_import

from django.conf.urls import patterns, include, url

from rest_framework.routers import DefaultRouter

from .api import SystemViewSet
from .api import UserList, UserDetail, UserSystemList


# Create a router and register our viewsets with it.
router = DefaultRouter()
router.register(r'systems', SystemViewSet)


user_urls = patterns('',
    url(r'^/(?P<username>[0-9a-zA-Z_-]+)/systems$',
        UserSystemList.as_view(),
        name='usersystem-list'),

    url(r'^/(?P<username>[0-9a-zA-Z_-]+)$', UserDetail.as_view(), name='user-detail'),
    url(r'^$', UserList.as_view(), name='user-list')
)

urlpatterns = patterns('',
    url(r'^', include(router.urls)),
    url(r'^users', include(user_urls)),
)
