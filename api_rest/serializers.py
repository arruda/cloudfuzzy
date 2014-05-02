# -*- coding: utf-8 -*-

from django.contrib.auth import get_user_model

from rest_framework import serializers

from fuzzy_modeling.models import SystemModel


User = get_user_model()


class UserSerializer(serializers.ModelSerializer):
    systems = serializers.HyperlinkedIdentityField(
                                                    'systems',
                                                    view_name='usersystem-list',
                                                    lookup_field='username'
                                                )

    class Meta:
        model = User
        fields = ('id', 'username', 'first_name', 'last_name', 'systems', )


class SystemModelSerializer(serializers.ModelSerializer):
    user = UserSerializer(required=False)
    # user = serializers.HyperlinkedRelatedField(view_name='user-detail',
    #                                               lookup_field='username')

    def get_validation_exclusions(self):
        # Need to exclude `user`
        # since we'll add that later based off the request
        exclusions = super(SystemModelSerializer, self).get_validation_exclusions()
        return exclusions + ['user']

    class Meta:
        model = SystemModel
