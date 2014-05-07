app = angular.module 'cloudfuzzy.app.systems', ['cloudfuzzy.api']

app.controller 'AppController', ['$scope', 'System', ($scope, System) ->

    $scope.obj_list = System.query()
    $scope.detailed_obj = null

    $scope.get = (object) ->
        console.log '1'
        System.get(id: object.id).$promise.then (result) ->
            console.log '2'
            $scope.obj_list = null
            $scope.detailed_obj = result
]
