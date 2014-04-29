app = angular.module 'cloudfuzzy.app.static', []

app.controller 'AppController', ['$scope', '$http', ($scope, $http) ->
    $scope.systems = []
    $http.get('/api/systems').then (result) ->
        angular.forEach result.data, (item) ->
            $scope.systems.push item
]
