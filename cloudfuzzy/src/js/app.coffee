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
    templateUrl: 'list.html'
    controller: 'systemListCtrl'
  .otherwise
    redirectTo: '/s'

# Create app module and configure it
angular
.module('cloudfuzzy.app', dependencies)
.config [
  '$routeProvider'
  config
]
