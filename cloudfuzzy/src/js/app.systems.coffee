app = angular.module 'cloudfuzzy.app.systems', ['cloudfuzzy.api']

app.controller 'AppController', ['$scope', 'System', ($scope, System) ->
    $scope.systems = System.query()
]
