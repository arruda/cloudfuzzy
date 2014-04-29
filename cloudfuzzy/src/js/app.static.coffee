app = angular.module 'cloudfuzzy.app.static', ['cloudfuzzy.api']

app.controller 'AppController', ['$scope', 'System', ($scope, System) ->
    $scope.systems = System.query()
]
