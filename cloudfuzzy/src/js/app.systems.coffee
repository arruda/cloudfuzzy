app = angular.module 'cloudfuzzy.app.systems', ['cloudfuzzy.api']

app.controller 'AppController', ['$scope', 'System', ($scope, System) ->

    $scope.systems = System.query()

    $scope.a_system = System.get(id: 2)
    $scope.get = (system) ->
        console.log '1'
        System.get(id: system.id).$promise.then (results) ->
            console.log '2'
            $scope.a_system = results
]
