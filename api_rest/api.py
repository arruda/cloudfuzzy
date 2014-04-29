# -*- coding: utf-8 -*-
from __future__ import absolute_import

from django.contrib.auth.models import User

from rest_framework import generics, permissions

from fuzzy_modeling.models import SystemModel

from .serializers import SystemModelSerializer, UserSerializer


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


class UserList(generics.ListCreateAPIView):
    model = User
    serializer_class = UserSerializer
    permission_classes = [
        permissions.AllowAny
    ]


class UserDetail(generics.RetrieveAPIView):
    model = User
    serializer_class = UserSerializer
    lookup_field = 'username'


class UserSystemList(generics.ListAPIView):
    model = SystemModel
    serializer_class = SystemModelSerializer

    def get_queryset(self):
        queryset = super(UserSystemList, self).get_queryset()
        return queryset.filter(user__username=self.kwargs.get('username'))
