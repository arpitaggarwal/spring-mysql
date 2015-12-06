var app = angular.module("app", [ 'ngRoute' ]);
app.controller('EmployeeController', function($scope, $http) {
	$scope.employeeFormData = {
		name : "",
		age : ""
	};
	$scope.submit = function() {
		$http({
			method : 'POST',
			url : '/spring-mysql/addEmployee',
			dataType : 'json',
			headers : {
				'Content-Type' : 'application/json'
			},
			data : $scope.employeeFormData,
		}).success(function(data) {
			$scope.result = data.result;
		});
	};

	$http.get('/spring-mysql/getEmployees').then(function(response) {
		$scope.employees = response.data;
	}, function(response) {
	});
});