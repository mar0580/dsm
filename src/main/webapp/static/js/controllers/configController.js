app.controller('ConfigController', [ '$scope', '$sce', '$http', 'scope', 'command', 'close', function($scope, $sce, $http, scope , command, close){
	Log('Entrou no ConfigController', '');
	$scope.htmlModal1;
	$scope.htmlModal2;
	$scope.myForm;
	$scope.msg = '';
	$scope.ports;
	$scope.oldDiginetPort;
	$scope.isWait = false;
	
	$scope.getPorts = function(){
		$http.get('/dsm/config/ports/').then(function(response) {
			if (!scope.isEmpty(response.data)){
				$scope.ports = response.data;
				$scope.oldDiginetPort = $scope.ports.diginetPort;
			}
		},
		function(data){
			$scope.msg = data.data;
			setTimeout(function() {
				$scope.htmlModal1 = $sce.trustAsHtml(scope.msg($scope.msg, null));
				$scope.$apply();
			}, 300);
		});
	}

	$scope.changePorts = function(){
		$scope.isWait = true;
		var msg;
		if ($scope.oldDiginetPort === $scope.ports.diginetPort){
			$scope.ports.diginetPort = 0;
		}
		$http.post('/dsm/config/ports/', $scope.ports).then(
		function(response) {
			if (!scope.isEmpty(response.data.info)) {
				
				$scope.msg = response.data;
				setTimeout(function() {
					$scope.htmlModal2 = $sce.trustAsHtml(scope.msg($scope.msg, null));
					$scope.$apply();
				}, 300);
				
			} else if (!scope.isEmpty(response.data.error)) {
				$scope.msg = response.data;
				setTimeout(function() {
					$scope.htmlModal2 = $sce.trustAsHtml(scope.msg($scope.msg, null));
					$scope.$apply();
				}, 300);
				
			}
			$scope.isWait = false;
			
		},
		function(data){
			$scope.msg = data.data;
			setTimeout(function() {
				$scope.htmlModal2 = $sce.trustAsHtml(scope.msg($scope.msg, null));
				$scope.$apply();
			}, 300);
			
			$scope.isWait = false;
		});
	}

	$scope.close = function(){
		scope.close('modal_change_port', close);
	}
	
	$scope.checkForm = function(){
//		console.log("form: ", $scope.myForm);
	};
	
	$scope.getAlertMessage = function(){
		var msg = "";
		if ($scope.myForm.diginetPort.$dirty){
			msg += "Este procedimento requer reinicialização do DIGINET."
		}
		msg += "\n\rAs portas de configuração do Client não devem se repetir."
		return msg + "\n\rDeseja continuar?";
	};
	
	switch (command) {
	case "change_port":
		
		$scope.getPorts();
		break; 
	default:
		break;
	}
}]); 