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
  .when '/detail/:itemId',
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
        window.location = "#/detail/" + system.id

    $scope.systems = System.query()
]


app.controller 'systemDetailCtrl', ['$scope', 'System', '$routeParams', ($scope, System, $routeParams) ->
    $scope.itemId = $routeParams.itemId
    id = $scope.itemId
    $scope.system = System.get(id:id)
]

