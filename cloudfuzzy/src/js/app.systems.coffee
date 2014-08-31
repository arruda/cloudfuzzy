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
  .when '/delete/:itemId',
    templateUrl: 'delete.html'
    controller: 'systemDeleteCtrl'
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
        window.location = "#/new"

    $scope.systems = System.query()
]


app.controller 'systemDetailCtrl', ['$scope', 'System', '$routeParams', ($scope, System, $routeParams) ->
    $scope.itemId = $routeParams.itemId
    id = $scope.itemId
    $scope.system = System.get(id:id)

    $scope.list = ->
        window.location = "#/"

    $scope.delete = (system) ->
        console.log 'a'
        window.location = "#/delete/" + system.id
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

app.controller 'systemDeleteCtrl', ['$scope', 'System', '$routeParams', ($scope, System, $routeParams) ->
    $scope.itemId = $routeParams.itemId
    id = $scope.itemId
    $scope.system = System.get(id:id)

    $scope.list = ->
        window.location = "#/"

    $scope.delete = ->
        console.log "delete"
        $scope.system.$delete().then (result) ->

            console.log "delete ok"
            # Clear any errors
            window.location = "#/"
        , (rejection) ->
            console.log "delete error"
            $scope.errors = rejection.data
            console.log rejection.data

    $scope.list = ->
        window.location = "#/"

    $scope.detail = (system) ->
        window.location = "#/detail/" + system.id
]
