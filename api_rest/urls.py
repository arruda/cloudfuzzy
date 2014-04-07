# -*- coding: utf-8 -*-
from __future__ import absolute_import

from django.conf.urls import patterns, include, url

from .api import SystemList, SystemDetail

system_urls = patterns('',
    url(r'^$', SystemList.as_view(), name='system-list'),
    url(r'^/(?P<pk>\d+)$', SystemDetail.as_view(), name='system-detail'),
)

urlpatterns = patterns('',
    url(r'^systems', include(system_urls)),
)
