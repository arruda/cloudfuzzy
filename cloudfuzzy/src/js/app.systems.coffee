app = angular.module 'cloudfuzzy.app.systems', ['cloudfuzzy.api']

app.controller 'list', ['$scope', 'System', ($scope, System) ->

    $scope.systems = System.query()
]