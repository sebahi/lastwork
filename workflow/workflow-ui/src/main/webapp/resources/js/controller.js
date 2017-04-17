var instanceApp = angular.module('instanceApp', []);

instanceApp.controller('instanceCtrl', function($scope, $http) {
	
	$scope.refreshInstance = function(instanceId) {
		$http.get('http://localhost:8080/jbpm-console/rest/history/instances').success(function(data) {
			$scope.instance = data;
		});
	};

	$scope.initInstanceId = function(instanceId) {
		alert('Hello');
		$scope.instanceId = instanceId;
		$scope.refreshInstance($scope.instanceId);
	};
});