app.controller('ModuleController', function($scope, $sce, $http, scope, command, sharedProperties) {
	Log('Inside ModuleController', '');
	$scope.title;
	$scope.moduleName = sharedProperties.getName();
	$scope.product = sharedProperties.getProduct();
	$scope.username = scope.company.username;
	$scope.msg = "";
	$scope.trustedHtml;

	$scope.installModule = function(product, moduleName) {
		$http.get("/dsm/module/install/" + $scope.product + "/" + $scope.moduleName).then(function(response) {
			setTimeout(function() {
				$scope.msg = scope.msg(response.data, null);
				$scope.trustedHtml = $sce.trustAsHtml($scope.msg);
				$scope.$apply();
				if (!scope.isEmpty(response.data.info)) {
					$scope.getModules();
				}
			}, 200);
		},function(data){
			$scope.sendMessage({error: "Não foi possível enviar solicitação."});
		});
	};

	$scope.requestModule = function() {
		$http.get('/dsm/request/module?name=' + $scope.moduleName + '&type=' + $scope.product, $scope.moduleName).then(
		function(response) {
			$scope.sendMessage(response.data);
			$scope.getModules();
		},
		function(data){
			$scope.sendMessage({error: "Não foi possível enviar solicitação."});
		});
	}
	
	$scope.sendMessage = function(msg){
		setTimeout(function() {
			$scope.msg = scope.msg(msg, null);
			$scope.trustedHtml = $sce.trustAsHtml($scope.msg);
			$scope.$apply();
		}, 200);
	}

	$scope.cancelModule = function() {
		$http.get('/dsm/request/module/cancel?name=' + $scope.moduleName + '&type=' + $scope.product + '&cancel=' + true, $scope.moduleName).then(function(response) {
			setTimeout(function() {
				$scope.msg = scope.msg(response.data, null);
				$scope.trustedHtml = $sce.trustAsHtml($scope.msg);
				$scope.$apply();
				if (!scope.isEmpty(response.data.info)) {
					$scope.getModules();
				}
			}, 200);
			$scope.getModules();
		},function(data){
			$scope.sendMessage({error: "Não foi possível enviar solicitação."});
		});
		
		setTimeout(function() {
			$scope.getModules();
		}, 5000)
	}

	$scope.getModules = function() {
		$http.get("/dsm/module/get/").then(function(response) {
			if (!scope.isEmpty(response.data)) {
				scope.modules = response.data;
				//console.log("Modules: " , scope.modules);
			}
		},function(data){
			$scope.sendMessage({error: "Não foi possível enviar solicitação."});
		});
	};

	// Commands
	switch (command) {
	case "install":
		
		$scope.title = "Instalando Módulo";
		$scope.installModule();
		break;
	case "request":
		
		$scope.title = "Gerando solicitação do módulo " + $scope.moduleName;
		$scope.requestModule();
		break;
	case "cancel":
		
		$scope.title = "Cancelando solicitação do módulo " + $scope.moduleName;
		$scope.cancelModule();
		break;
	default:
		break;
	}
});