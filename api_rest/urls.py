# -*- coding: utf-8 -*-
from __future__ import absolute_import

from django.conf.urls import patterns, include, url

from .api import SystemList, SystemDetail
from .api import UserList, UserDetail, UserSystemList

system_urls = patterns('',
    url(r'^$', SystemList.as_view(), name='system-list'),
    url(r'^/(?P<pk>\d+)$', SystemDetail.as_view(), name='system-detail'),
)

user_urls = patterns('',
    url(r'^/(?P<username>[0-9a-zA-Z_-]+)/systems$',
        UserSystemList.as_view(),
        name='usersystem-list'),

    url(r'^/(?P<username>[0-9a-zA-Z_-]+)$', UserDetail.as_view(), name='user-detail'),
    url(r'^$', UserList.as_view(), name='user-list')
)

urlpatterns = patterns('',
    url(r'^users', include(user_urls)),
    url(r'^systems', include(system_urls)),
)
