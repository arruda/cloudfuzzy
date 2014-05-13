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
  .otherwise
    redirectTo: '/edit'


app = angular
.module('cloudfuzzy.app.systems', dependencies)
.config [
  '$routeProvider'
  config
]

app.controller 'systemListCtrl', ['$scope', 'System', ($scope, System) ->
    console.log "ab"
    $scope.systems = System.query()
]

