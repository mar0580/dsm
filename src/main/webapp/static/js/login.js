'use strict';
var app = angular.module('login', ['utf8-base64']);

app.controller('controller', function($location, $scope, $sce, $http, $window, base64) {
	$scope.user;
	$scope.pass; 
	$scope.msgReturn;
	 
	$scope.login = function (){     
		var json = {      
				username : $scope.user, 
				password : $scope.pass       
		}; 
		$http.post('/dsm/execlogin', json).then(function(response) {
			$window.location.href = "/dsm/";     
		}, function(data){
			$scope.sendMessage(data.data);
		});        
	}; 
	
	$scope.msg = function(strJson, align) {
		if ($scope.isEmpty(align)) {
			align = "center";
		}

		var ret;
		if (!$scope.isEmpty(strJson)) {
			switch (Object.keys(strJson)[0]) {
			case "info":
				ret = $scope.replaceAndDecode(strJson.info);
				return "<pre class=\"text-" + align + " bg-info\">" + ret + "</pre>";
			case "warning":
				ret = $scope.replaceAndDecode(strJson.warning);
				return "<pre class=\"text-" + align + " bg-warning\">" + ret + "</pre>";
			case "error":
				ret = $scope.replaceAndDecode(strJson.error);
				return "<pre class=\"text-" + align + " bg-danger\">" + ret + "</pre>";
			default:
				ret = $scope.replaceAndDecode(strJson.info);
				return "<pre class=\"text-" + align + " bg-info \">" + ret + "</pre>";
			}
		}
		return " ";
	};
	
	$scope.sendMessage = function(msg){
		$scope.msgReturn = $sce.trustAsHtml($scope.msg(msg, null));
		setTimeout(function() {
			$scope.msgReturn = $sce.trustAsHtml('');
			$scope.$apply();
		}, 3000);
	}
	
	$scope.isEmpty = function isEmpty(obj) {
		// null and undefined are "empty"
		if (obj == null)
			return true;

		// Assume if it has a length property with a non-zero value
		// that that property is correct.
		if (obj.length > 0)
			return false;
		if (obj.length === 0)
			return true;

		// If it isn't an object at this point
		// it is empty, but it can't be anything *but* empty
		// Is it empty? Depends on your application.
		if (typeof obj !== "object")
			return true;

		// Otherwise, does it have any properties of its own?
		// Note that this doesn't handle
		// toString and valueOf enumeration bugs in IE < 9
		for (var key in obj) {
			if (hasOwnProperty.call(obj, key))
				return false;
		}
		return true;
	};
	
	$scope.replaceAndDecode = function(string) {
		var replaced = string.toString().replace(/DIGICONEQUAL/g, '=');
		var ret;
		//check if is base64
		var base64Matcher = new RegExp("^(?:[A-Za-z0-9+/]{4})*(?:[A-Za-z0-9+/]{2}==|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{4})$");
		if (base64Matcher.test(replaced)) {
			ret = base64.decode(replaced);
		} else {
			ret = replaced;
		}

		return ret;
	}
}); 