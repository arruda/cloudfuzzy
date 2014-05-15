# -*- coding: utf-8 -*-
from __future__ import absolute_import

from rest_framework import permissions


class SafeMethodsOnlyPermission(permissions.BasePermission):
    """Only can access non-destructive methods (like GET and HEAD)"""
    def has_permission(self, request, view):
        return self.has_object_permission(request, view)

    def has_object_permission(self, request, view, obj=None):
        return request.method in permissions.SAFE_METHODS


class SystemAuthorPermission(permissions.BasePermission):
    """
        Permissions in the object only for the object owner
    """

    def has_object_permission(self, request, view, obj):
        return obj.user == request.user


class AuthorPermission(permissions.BasePermission):
    """
    Permissions in the object only for the object owner.
    This is a generic permission for any fuzzy model object in the API

    it requires the Viewset ou APIview to have an attr called "owner_path"
    that is a doted path to find the owner in the current object.

    Ex:
    in the SystemModel would be: 'user', since the user in a field
    in the SystemModel object.

    In the InputVariableModel would be: 'system.user', since to get to
    the user, first must go through the system model of the current
    InputVariableModel object.
    """

    def __get_obj_user(self, obj, user_path):

        for attr_name in user_path.split('.'):
            obj = getattr(obj, attr_name)

        return obj

    def has_object_permission(self, request, view, obj):
        if not hasattr(view, 'user_path'):
            raise Exception("Should have a 'user_path' attr in %s" % view)

        obj_user = self.__get_obj_user(obj, view.user_path)
        return obj_user == request.user


class OwnUserPermission(permissions.BasePermission):
    """
        Only has permission if the current user is checking/editing itself
        no list permissions
    """
    def has_permission(self, request, view):
        if view.action == 'list':
            return False
        return True

    def has_object_permission(self, request, view, obj):
        return obj == request.user
