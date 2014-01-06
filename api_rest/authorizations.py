from tastypie.authorization import Authorization
from tastypie.exceptions import Unauthorized


class SystemOwnerAuthorization(Authorization):

    def __init__(self, user_path='user'):
        super(SystemOwnerAuthorization, self).__init__()
        self.user_path = user_path
        self.user_filter_key = user_path.replace('.','__')

    def _get_obj_user(self, obj):
        return getattr(self.user_path, obj)

    def read_list(self, object_list, bundle):
        # This assumes a ``QuerySet`` from ``ModelResource``.
        return object_list.filter(user=bundle.request.user)

    def read_detail(self, object_list, bundle):
        # Is the requested object owned by the user?
        return self._get_obj_user(bundle.obj) == bundle.request.user

    def create_list(self, object_list, bundle):
        # Assuming they're auto-assigned to ``user``.
        return object_list

    def create_detail(self, object_list, bundle):
        return self._get_obj_user(bundle.obj) == bundle.request.user

    def update_list(self, object_list, bundle):
        allowed = []

        # Since they may not all be saved, iterate over them.
        for obj in object_list:
            if self._get_obj_user(obj) == bundle.request.user:
                allowed.append(obj)

        return allowed

    def update_detail(self, object_list, bundle):
        return self._get_obj_user(bundle.obj) == bundle.request.user

    def delete_list(self, object_list, bundle):
        allowed = []

        # Since they may not all be saved, iterate over them.
        for obj in object_list:
            if self._get_obj_user(obj) == bundle.request.user:
                allowed.append(obj)

        return allowed

    def delete_detail(self, object_list, bundle):
        return self._get_obj_user(bundle.obj) == bundle.request.user
