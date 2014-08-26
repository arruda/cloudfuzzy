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
  .when '/new',
    templateUrl: 'new.html'
    controller: 'systemNewCtrl'
  .when '/detail/:itemId',
    templateUrl: 'detail.html'
    controller: 'systemDetailCtrl'
  .otherwise
    redirectTo: '/'


app = angular
.module('cloudfuzzy.app.systems', dependencies)
.config [
  '$routeProvider'
  config
]

app.controller 'systemListCtrl', ['$scope', 'System', ($scope, System) ->

    $scope.detail = (system) ->
        window.location = "#/detail/" + system.id

    $scope.new = ->
        console.log  "a"
        window.location = "#/new"

    $scope.systems = System.query()
]


app.controller 'systemDetailCtrl', ['$scope', 'System', '$routeParams', ($scope, System, $routeParams) ->
    $scope.itemId = $routeParams.itemId
    id = $scope.itemId
    $scope.system = System.get(id:id)

    $scope.list = ->
        window.location = "#/"
]


app.controller 'systemNewCtrl', ['$scope', 'System','User', 'AuthUser', ($scope, System, User, AuthUser) ->

    $scope.newSystem = new System()
    user = User.get(username:AuthUser.username)
    user.$promise.then (result) ->
        $scope.newSystem.user = user.url

    $scope.save = ->
        $scope.newSystem.$save().then (result) ->
            # Clear any errors
            window.location = "#/detail/" + result.id
        , (rejection) ->
            $scope.errors = rejection.data
            console.log rejection.data

    $scope.list = ->
        window.location = "#/"
]
