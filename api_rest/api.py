# -*- coding: utf-8 -*-
from __future__ import absolute_import

from django.contrib.auth import get_user_model

from rest_framework import generics, permissions

from fuzzy_modeling.models import SystemModel

from .serializers import SystemModelSerializer, UserSerializer
from .permissions import SystemAuthorPermission


User = get_user_model()


class SystemMixin(object):

    model = SystemModel
    serializer_class = SystemModelSerializer
    permission_classes = [
        permissions.IsAuthenticatedOrReadOnly,
        SystemAuthorPermission
    ]

    def pre_save(self, obj):
        """Force user to the current request user on save"""
        obj.user = self.request.user
        return super(SystemMixin, self).pre_save(obj)

    def get_queryset(self):
        """
        Only show the systems of the given user
        """

        return self.model._default_manager.filter(user=self.request.user)


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
