<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!doctype html>
<html>
<head>
<spring:url value="/js/jquery-2.1.4.min.js" var="jqueryJs" />
<spring:url value="/js/angular.min.js" var="angularJs" />
<spring:url value="/js/angular-route.min.js" var="angularRouteJs" />
<spring:url value="/js/base.js" var="baseJs" />
<script src="${jqueryJs}"></script>
<script src="${angularJs}"></script>
<script src="${angularRouteJs}"></script>
<script src="${baseJs}"></script>
</head>
<title>Spring Application | Linking Docker Containers Example</title>
<body>
	<div data-ng-app="app" data-ng-controller="EmployeeController">
		<form>
			<table>
				<tr>
					<td>Name:</td>
					<td><input type="text" data-ng-model="employeeFormData.name" /></td>
				</tr>
				<tr>
					<td>Age:</td>
					<td><input type="text" data-ng-model="employeeFormData.age" /></td>
				</tr>
				<tr>
					<td><button data-ng-click="submit()">Submit</button></td>
				</tr>
			</table>
		</form>
		<div id="results">{{result}}</div>
		<div id="employees">Employees:</div>
		<table>
			<tr data-ng-repeat="item in employees">
				<td data-ng-repeat="(key, val) in item">{{val}}</td>
			</tr>
		</table>
	</div>
</body>
</html>
