app.controller('ServiceController', [ '$scope', '$sce', '$http', 'sharedProperties', 'command', 'close', 'scope', '$rootScope',
		function($scope, $sce, $http, sharedProperties, command, close, scope, $rootScope) {
			Log('Entrou no ServiceController [' + command + ']', '');
			$scope.name = sharedProperties.getName();
			$scope.text;
			$scope.serviceMsg = "";
			$scope.trustedHtml;

			$scope.stopService = function(name) {
				$http.get("/dsm/service/stop/" + name).then(function(response) {
					var resp = response.data;
					setTimeout(function() {
						scope.getServiceByName(name);
						$scope.serviceMsg = scope.msg(resp, null);
						$scope.trustedHtml = $sce.trustAsHtml($scope.serviceMsg);
						$scope.$apply();
						if (Object.keys(resp) == "info") {
							if (name == "mysql") {
								location.reload();
							} else {
								scope.close(null, close);
							}
						}
					}, 3000);
				});
			};

			$scope.startService = function(name) {
				$http.get("/dsm/service/start/" + name).then(function(response) {
					var resp = response.data;
					setTimeout(function() {
						scope.getServiceByName(name);

						$scope.serviceMsg = scope.msg(resp, null);
						$scope.trustedHtml = $sce.trustAsHtml($scope.serviceMsg);
						$scope.$apply();

						if (Object.keys(resp) == "info") {
							if (name == "mysql") {
								location.reload();
							} else {
								scope.close(null, close);
							}
						}
					}, 1500);
				});
			};

			$scope.restartService = function(name) {
				$http.get("/dsm/service/restart/" + name).then(function(response) {
					var resp = response.data;
					setTimeout(function() {
						if (Object.keys(resp) == "info") {
							if (name == "mysql") {
								location.reload();
							} else {
								// $scope.close();
							}
						}
					}, 1500);
				});
			};
			
			$scope.close = function(str){
				scope.close(null, close);
			}
			// Comandos
			switch (command) {
			case "start":
				
				$scope.text = "Iniciando Serviço...";
				$scope.startService($scope.name);
				break;
			case "stop":
				
				$scope.text = "Parando Serviço...";
				$scope.stopService($scope.name);
				break;
			case "restart":
				
				$scope.restartService($scope.name);
				break;
			default:
				break;
			}

		} ]);