# -*- coding: utf-8 -*-
from __future__ import absolute_import

from django.contrib.auth import get_user_model

from rest_framework import generics, permissions
from rest_framework import viewsets

from fuzzy_modeling.models import SystemModel, InputVariableModel

from .serializers import SystemModelSerializer, UserSerializer
from .serializers import InputVariableModelSerializer

from .permissions import SystemAuthorPermission, OwnUserPermission


User = get_user_model()


class SystemViewSet(viewsets.ModelViewSet):

    model = SystemModel
    serializer_class = SystemModelSerializer
    permission_classes = [
        permissions.IsAuthenticatedOrReadOnly,
        SystemAuthorPermission
    ]

    def pre_save(self, obj):
        """Force user to the current request user on save"""
        obj.user = self.request.user
        return super(SystemViewSet, self).pre_save(obj)

    def get_queryset(self):
        """
        Only show the systems of the given user
        """

        queryset = super(SystemViewSet, self).get_queryset()
        return queryset.filter(user__username=self.request.user.username)


class UserViewSet(viewsets.ModelViewSet):

    model = User
    serializer_class = UserSerializer
    lookup_field = 'username'

    permission_classes = [
        permissions.IsAuthenticatedOrReadOnly,
        OwnUserPermission
    ]


class UserSystemList(generics.ListAPIView):
    model = SystemModel
    serializer_class = SystemModelSerializer

    def get_queryset(self):
        queryset = super(UserSystemList, self).get_queryset()
        return queryset.filter(user__username=self.kwargs.get('username'))


class InputVariableViewSet(viewsets.ModelViewSet):
    model = InputVariableModel
    serializer_class = InputVariableModelSerializer

    # permission_classes = [
    #     permissions.IsAuthenticatedOrReadOnly,
    #     OwnUserPermission
    # ]
