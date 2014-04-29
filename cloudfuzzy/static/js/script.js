(function() {
  var app;

  app = angular.module('cloudfuzzy.app.static', []);

  app.controller('AppController', [
    '$scope', '$http', function($scope, $http) {
      return $scope.systems = [
        {
          user: {
            username: 'Arruda'
          },
          name: 'System one',
          description: 'This is the first sample system'
        }, {
          user: {
            username: 'Felipe'
          },
          name: 'System two',
          description: 'This is the second sample system'
        }
      ];
    }
  ]);

}).call(this);
