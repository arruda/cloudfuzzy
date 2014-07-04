dependencies = [
  'ngRoute'
  'cloudfuzzy.api'
]

config = ($routeProvider) ->
  # $locationProvider.html5Mode(true)
  $routeProvider
  .when '/',
    templateUrl: 'list.html'
    controller: 'systemListCtrl'
  .when '/detail',
    templateUrl: 'detail.html'
    controller: 'systemDetailCtrl'
  .otherwise
    redirectTo: '/edit'


app = angular
.module('cloudfuzzy.app.systems', dependencies)
.config [
  '$routeProvider'
  config
]

app.controller 'systemListCtrl', ['$scope', 'System', ($scope, System) ->

    $scope.detail = (system) ->
        window.location = "#/detail";

    $scope.systems = System.query()
]


app.controller 'systemDetailCtrl', ['$scope', 'System', ($scope, System) ->
    $scope.system = System.query(id:system)
]

