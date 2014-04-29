(function() {
  var app;

  app = angular.module('cloudfuzzy.app.static', []);

  app.controller('AppController', [
    '$scope', '$http', function($scope, $http) {
      $scope.systems = [];
      return $http.get('/api/systems').then(function(result) {
        return angular.forEach(result.data, function(item) {
          return $scope.systems.push(item);
        });
      });
    }
  ]);

}).call(this);
