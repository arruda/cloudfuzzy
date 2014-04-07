# -*- coding: utf-8 -*-
from __future__ import absolute_import

from rest_framework import generics, permissions

from fuzzy_modeling.models import SystemModel

from .serializers import SystemModelSerializer


class SystemMixin(object):

    model = SystemModel
    serializer_class = SystemModelSerializer
    permission_classes = [
        permissions.AllowAny
    ]

    def pre_save(self, obj):
        """Force user to the current request user on save"""
        obj.user = self.request.user
        return super(SystemMixin, self).pre_save(obj)


class SystemList(SystemMixin, generics.ListCreateAPIView):
    pass


class SystemDetail(SystemMixin, generics.RetrieveUpdateDestroyAPIView):
    pass
