(function() {
  var config, dependencies;

  dependencies = ['ngRoute', 'cloudfuzzy.app.systems'];

  config = function($routeProvider) {
    return $routeProvider.when('/', {
      templateUrl: 'list.html',
      controller: 'systemListCtrl'
    }).otherwise({
      redirectTo: '/s'
    });
  };

  angular.module('cloudfuzzy.app', dependencies).config(['$routeProvider', config]);

}).call(this);

(function() {
  var app;

  app = angular.module('cloudfuzzy.app.static', ['cloudfuzzy.api']);

  app.controller('AppController', [
    '$scope', 'System', function($scope, System) {
      return $scope.systems = System.query();
    }
  ]);

}).call(this);

(function() {
  var app, config, dependencies;

  dependencies = ['ngRoute', 'cloudfuzzy.api'];

  config = function($routeProvider) {
    return $routeProvider.when('/', {
      templateUrl: 'list.html',
      controller: 'systemListCtrl'
    }).when('/new', {
      templateUrl: 'new.html',
      controller: 'systemNewCtrl'
    }).when('/detail/:itemId', {
      templateUrl: 'detail.html',
      controller: 'systemDetailCtrl'
    }).otherwise({
      redirectTo: '/'
    });
  };

  app = angular.module('cloudfuzzy.app.systems', dependencies).config(['$routeProvider', config]);

  app.controller('systemListCtrl', [
    '$scope', 'System', function($scope, System) {
      $scope.detail = function(system) {
        return window.location = "#/detail/" + system.id;
      };
      $scope["new"] = function() {
        console.log("a");
        return window.location = "#/new";
      };
      return $scope.systems = System.query();
    }
  ]);

  app.controller('systemDetailCtrl', [
    '$scope', 'System', '$routeParams', function($scope, System, $routeParams) {
      var id;
      $scope.itemId = $routeParams.itemId;
      id = $scope.itemId;
      $scope.system = System.get({
        id: id
      });
      return $scope.list = function() {
        return window.location = "#/";
      };
    }
  ]);

  app.controller('systemNewCtrl', [
    '$scope', 'System', function($scope, System) {
      $scope.addSystem = function() {
        var system;
        system = new System({
          name: $scope.newSystemName
        });
        $scope.newSystemName = "";
        return system.$save().then($scope.list);
      };
      return $scope.list = function() {
        return window.location = "#/";
      };
    }
  ]);

}).call(this);

(function() {
  var app;

  app = angular.module('cloudfuzzy.app.users', ['cloudfuzzy.api']);

  app.controller('AppController', [
    '$scope', 'User', 'UserSystem', function($scope, User, UserSystem) {
      $scope.systems = {};
      $scope.users = User.query();
      return $scope.users.$promise.then(function(results) {
        return angular.forEach(results, function(user) {
          return $scope.systems[user.username] = UserSystem.query({
            username: user.username
          });
        });
      });
    }
  ]);

}).call(this);

(function() {
  var app;

  app = angular.module('cloudfuzzy.api', ['ngResource']);

  app.factory('User', [
    '$resource', function($resource) {
      return $resource('/api/users/:username', {
        username: '@username'
      });
    }
  ]);

  app.factory('System', [
    '$resource', function($resource) {
      return $resource('/api/systems/:id', {
        id: '@id'
      });
    }
  ]);

  app.factory('UserSystem', [
    '$resource', function($resource) {
      return $resource('/api/users/:username/systems/:id');
    }
  ]);

}).call(this);
