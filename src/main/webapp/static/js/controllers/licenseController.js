app.controller('LicenseController', ['$scope', '$sce', '$http', 'scope', 'command', 'sharedProperties', function($scope, $sce, $http, scope, command, sharedProperties) {
	Log('Inside LicenseController', '');
	$scope.title;
	$scope.username = scope.company.username;
	$scope.currentPassword; 
	$scope.newPassword;
	$scope.confPassword;
	$scope.trustedHtml; 
	$scope.pemFmt = ''; 
	$scope.msg = ''; 
	$scope.isSuccess = false;
	$scope.isWait = false;
	$scope.jsonObj = {};
	$scope.instances;
	
	$scope.login = {
			username: scope.company.username,
			newPassword: "",
			currentPassword: "",
			confPassword: ""
	};
	
	$scope.uploadFile = function(){
        
    };
	
	$scope.updateLicense = function() {
		
//		$scope.isWait = true;
//		$scope.trustedHtml = "";
		
		var file = sharedProperties.getFile();
		console.log('file is: ', file);
		
//		var file = $scope.myFile;
        console.dir(file);
        
        var fd = new FormData();
        fd.append('file', file);
        $http.post("/dsm/license/update/", fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        }).then(function(response) {
        	console.log(response.data);
    		$scope.msg = scope.msg(response.data, null);
    		$scope.trustedHtml = $sce.trustAsHtml($scope.msg);
    		setTimeout(function() {
    			$scope.$apply();
			}, 2000);
    		
			
        	
//        	$scope.isWait = false;
//			$scope.trustedHtml = $sce.trustAsHtml(scope.msg(response.data, null));
			
			if(!scope.isEmpty(response.data.info)){
//				$scope.isSuccess = true; 
				setTimeout(function() {
					location.reload(); 
				}, 2000); 
			}else{
				setTimeout(function() {
					$scope.trustedHtml = "";
					$scope.$apply();
				}, 2000);
			}
		});
	};

	$scope.updateLogin = function() {
		if($scope.confPassword !== $scope.newPassword){
			$scope.trustedHtml = $sce.trustAsHtml(scope.msg({warning : 'Senhas não conferem!'}, null));
			$scope.$apply();
			return;
		}
		
		$scope.login = {
			username : $scope.username,
			confPassword: $scope.confPassword,
			newPassword : $scope.newPassword,
			currentPassword : $scope.currentPassword
		};
		
		$http.post("/dsm/alter/login/", $scope.login).then(function(response) {
			
			setTimeout(function() {
				$scope.trustedHtml = $sce.trustAsHtml(scope.msg(response.data, null));
				$scope.$apply();
			}, 200);
		});
	};
	
	$scope.getUpdateKeyPemFmt = function(){
		$http.get("/dsm/license/get/pem-update").then(function(response){
			if (!scope.isEmpty(response.data)){
				$scope.pemFmt = response.data;
				setTimeout(function() {
					$scope.msg = "Envie a solicitação para \"suporte.vca@digicon.com.br\" com o assunto \"Solicitação DIGINET\", " +
						"especificando a quantidade de dispositivos que deseja controlar e uma das 3 opções de licença:\n" +
						"\"Diginet + Web Service\", \"Somente Diginet\" ou \"Integração GAS - Senior Ronda\". \n" +
						"Junto a solicitação copie o bloco de texto abaixo:";
					$scope.trustedHtml = $sce.trustAsHtml("<pre class=\"text-info text-center\">" + $scope.pemFmt + "</pre>");
					$scope.$apply();
				}, 200);
			} else {
				setTimeout(function() {
					$scope.trustedHtml = $sce.trustAsHtml(scope.msg({error: "Não foi possível gerar o arquivo de solicitação."}));
					$scope.$apply();
				}, 200);
			}
		});
	};
	
	$scope.getUpdateKey = function(){
		$http.get("/dsm/license/get/update").then(function(response){
			if (!scope.isEmpty(response.data)){
				$scope.instances = response.data.instances;
				$scope.modules = response.data.modules;
			}
			if(scope.isEmpty($scope.instances) && scope.isEmpty($scope.modules)){
				setTimeout(function() {
					var msg = "Não há solicitação para serem enviadas.\n" +
							"Para solicitar módulos, clique no botão \"Solicitar\" na sessão \"MÓDULOS\". \n" +
							"Para solicitar instâncias, clique no botão \"Solicitar\" na sessão \"INSTÂNCIAS - SERVIDORES\"";
					$scope.trustedHtml = $sce.trustAsHtml(scope.msg({warning: msg}));
					$scope.$apply();
				}, 200);
			}
		});
	};

	$scope.close = function(id, time) {
		if(time){
			setTimeout(function() {
				scope.close(id);
			}, time);
		}else{
			scope.close(id);
		}
		
	};
	
	$scope.isEmpty = function(value){
		return scope.isEmpty(value);
	}

	// Commands
	switch (command) {
	case "update_login":
		
		$scope.title = "Alterar Login";
		break;
	case "update_license":
		
		$scope.title = "Atualizar Licença";
		break;
	case "send_update_key":
		
		$scope.title = "Enviar solicitação";
		$scope.getUpdateKeyPemFmt();
		break;
	default:
		break;
	}
	
}]);

app.directive('fileModel', ['sharedProperties', function (sharedProperties) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
        	
//            var model = $parse(attrs.fileModel);
//            var modelSetter = model.assign;
            
            element.bind('change', function(){
            	
            	sharedProperties.setFile(element[0].files[0]);
//                scope.$apply(function(){
//                    modelSetter(scope, element[0].files[0]);
//                });
            });
        }
    };
}]);