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
