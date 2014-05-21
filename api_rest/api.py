# -*- coding: utf-8 -*-
from __future__ import absolute_import

from django.contrib.auth import get_user_model
from django.template import RequestContext, loader

from rest_framework import renderers
from rest_framework import generics, permissions
from rest_framework import viewsets
from rest_framework.decorators import link
from rest_framework.response import Response

from fuzzy_modeling.models import SystemModel, InputVariableModel, OutputVariableModel,\
    DefuzzifyModel

from .serializers import SystemModelSerializer, UserSerializer
from .serializers import InputVariableModelSerializer, OutputVariableModelSerializer
from .serializers import DefuzzifyModelSerializer

from .permissions import OwnUserPermission, AuthorPermission


User = get_user_model()


class BaseHTMLViewSet(object):

    template_name = "template.html"

    def get_html_request_context(self, request):
        return {}

    @link(renderer_classes=(renderers.StaticHTMLRenderer,))
    def html_template(self, request, *args, **kwargs):
        """
        Return the template html for angular js to deal with
        """
        t = loader.get_template(self.template_name)
        c = RequestContext(request, self.get_html_request_context(request))

        return Response(t.render(c))


class UserViewSet(viewsets.ModelViewSet):

    model = User
    serializer_class = UserSerializer
    lookup_field = 'username'

    permission_classes = [
        permissions.IsAuthenticated,
        OwnUserPermission
    ]


class UserSystemList(generics.ListAPIView):
    model = SystemModel
    serializer_class = SystemModelSerializer

    permission_classes = [
        permissions.IsAuthenticated,
        OwnUserPermission
    ]

    def get_queryset(self):
        queryset = super(UserSystemList, self).get_queryset()
        return queryset.filter(user__username=self.kwargs.get('username'))


class SystemViewSet(BaseHTMLViewSet, viewsets.ModelViewSet):

    model = SystemModel
    serializer_class = SystemModelSerializer
    permission_classes = [
        permissions.IsAuthenticated,
        AuthorPermission
    ]
    template_name = "_systems.html"

    def get_obj_user(self, obj):
        return obj.user

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


class InputVariableViewSet(viewsets.ModelViewSet):
    model = InputVariableModel
    serializer_class = InputVariableModelSerializer

    permission_classes = [
        permissions.IsAuthenticated,
        AuthorPermission
    ]

    def get_obj_user(self, obj):
        return obj.system.user

    def get_queryset(self):
        """
        Only show the objects list of the given user
        """

        queryset = super(InputVariableViewSet, self).get_queryset()
        return queryset.filter(system__user__username=self.request.user.username)


class OutputVariableViewSet(viewsets.ModelViewSet):
    model = OutputVariableModel
    serializer_class = OutputVariableModelSerializer

    permission_classes = [
        permissions.IsAuthenticated,
        AuthorPermission
    ]

    def get_obj_user(self, obj):
        return obj.system.user

    def get_queryset(self):
        """
        Only show the objects list of the given user
        """

        queryset = super(OutputVariableViewSet, self).get_queryset()
        return queryset.filter(system__user__username=self.request.user.username)


class DefuzzifyModelViewSet(viewsets.ModelViewSet):
    model = DefuzzifyModel
    serializer_class = DefuzzifyModelSerializer

    permission_classes = [
        permissions.IsAuthenticated,
        AuthorPermission
    ]

    def get_obj_user(self, obj):
        return obj.output_variable.system.user

    def get_queryset(self):
        """
        Only show the objects list of the given user
        """

        queryset = super(DefuzzifyModelViewSet, self).get_queryset()
        return queryset.filter(output_variable__system__user__username=self.request.user.username)
