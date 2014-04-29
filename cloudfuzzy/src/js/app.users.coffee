app = angular.module 'cloudfuzzy.app.users', ['cloudfuzzy.api']

app.controller 'AppController', ['$scope', 'User', 'UserSystem', ($scope, User, UserSystem) ->

    $scope.systems = {}
    $scope.users = User.query()
    $scope.users.$promise.then (results) ->
        # Load the photos
        angular.forEach results, (user) ->
            $scope.systems[user.username] = UserSystem.query(username: user.username)
]
