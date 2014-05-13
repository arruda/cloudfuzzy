app = angular.module 'cloudfuzzy.app.systems', ['cloudfuzzy.api']

app.controller 'systemListCtrl', ['$scope', 'System', ($scope, System) ->

    $scope.systems = System.query()
]