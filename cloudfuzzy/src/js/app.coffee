# Declare module dependencies
dependencies = [
  'ngRoute'
  'cloudfuzzy.app.systems'
]


# Configure app
config = ($routeProvider) ->
  # $locationProvider.html5Mode(true)
  $routeProvider
  .when '/',
    templateUrl: 'home.html'
    controller: 'systemListCtrl'
  .otherwise
    redirectTo: '/'

# Create app module and configure it
angular
.module('cloudfuzzy.app', dependencies)
.config [
  '$routeProvider'
  config
]
