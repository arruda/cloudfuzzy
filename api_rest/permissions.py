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

    it requires the Viewset ou APIview to have an method called "get_obj_user"
    that returns the owner of the current object.

    Ex:
    in the SystemModel would return: 'user', since the user in a field
    in the SystemModel object.

    In the InputVariableModel would be: 'system.user', since to get to
    the user, first must go through the system model of the current
    InputVariableModel object.
    """

    def has_object_permission(self, request, view, obj):
        if not hasattr(view, 'get_obj_user'):
            raise Exception("Should have a 'get_obj_user' method in %s" % view)

        obj_user = view.get_obj_user(obj)
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
