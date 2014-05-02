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
    """only the user can modify existing instances"""

    def has_permission(self, request, view):
        return self.has_object_permission(request, view)

    def has_object_permission(self, request, view, obj=None):
        if obj is None:
            # Either a list or a create, so no author
            can_edit = True
        else:
            can_edit = request.user == obj.user

        return can_edit


class SystemAuthorCanEditPermission(permissions.BasePermission):
    """only the user can modify existing instances"""

    def has_permission(self, request, view):
        return self.has_object_permission(request, view)

    def has_object_permission(self, request, view, obj=None):
        if obj is None:
            # Either a list or a create, so no author
            can_edit = True
        else:
            can_edit = request.user == obj.user

        return can_edit or super(SystemAuthorCanEditPermission, self).has_object_permission(request, view, obj)
