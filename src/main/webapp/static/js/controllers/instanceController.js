app.controller( 'InstanceController', [ '$scope', '$sce', '$http', 'scope', 'command', 'close', 'sharedProperties',
		function($scope, $sce, $http, scope, command, close, sharedProperties) {
			// VARS
			$scope.instance;
			$scope.myForm;
			$scope.instances = scope.instances;
			$scope.licenseInstancesByType = [];
			$scope.disableLimit = true;
			$scope.disableValidity = true;
			$scope.rows = +0;
			$scope.arrAddRows = [];
			$scope.min = 0;
			$scope.name = sharedProperties.getName();
			$scope.isIE = scope.isIE;
			$scope.ip;
			$scope.msg = '';
			$scope.msgError = '';
//			$scope.porta;
			$scope.type;
			$scope.title;
			$scope.trustedHtml;
			$scope.isWait = false;
			$scope.listGrid = ["#block1", "#block2", "#block3", "#block4", "#block5", "#block6", "#block7"];
			$scope.lastTarget = "#block1";
			$scope.selectedLog;
			$scope.instanceEnum = scope.instanceEnum;
			
			$scope.logLevels = [ {
				id : 0,
				label : 'OFF',
				subItem : {
					name : 'aSubItem'
				}
			}, {
				id : 1,
				label : 'TRACE',
				subItem : {
					name : 'bSubItem'
				}
			}, {
				id : 2,
				label : 'DEBUG',
				subItem : {
					name : 'bSubItem'
				}
			}, {
				id : 3,
				label : 'INFO',
				subItem : {
					name : 'bSubItem'
				}
			}, {
				id : 4,
				label : 'WARN',
				subItem : {
					name : 'bSubItem'
				}
			}, {
				id : 5,
				label : 'ERROR',
				subItem : {
					name : 'bSubItem'
				}
			}, {
				id : 6,
				label : 'FATAL',
				subItem : {
					name : 'bSubItem'
				}
			} ];
			
			$scope.close = function(str){
				scope.close(null, close);
			}

			$scope.reloadPage = function() {
				location.reload();
			};

			$scope.addZeroIfLess10 = function(number){
				return (number < 10) ? "0"+number : number;
			}

			$scope.releaseLimit = function() {
				$scope.disableLimit = false;
			};

			$scope.releaseValidity = function() {
				$scope.disableValidity = false;
			};

			$scope.getInstanceTypes = function() {
				$http.get("/dsm/instance/get/types").then(function(response) {
					scope.instance_types = response.data.info;
				});
			};

			$scope.installInstance = function() {
				$http.get("/dsm/instance/install/" + $scope.type).then(function(response) {
					setTimeout(function() {
						$scope.msg = scope.msg(response.data, null);
						$scope.trustedHtml = $sce.trustAsHtml($scope.msg);
						$scope.$apply();
						$http.get("/dsm/instance/get").then(function(response) {
							scope.instances = response.data;
						});
						$scope.getInstanceTypes();
					}, 1500);
				});
			};
			
			$scope.uninstallInstance = function() {
				$http.get("/dsm/instance/uninstall/" + $scope.name).then(function(response) {
					setTimeout(function() {
						$scope.msg = scope.msg(response.data, null);
						$scope.trustedHtml = $sce.trustAsHtml($scope.msg);
						$scope.$apply();
						setTimeout(function() {
							$scope.reloadPage();
						}, 1500);
					}, 1500);
				});
			};
			
			$scope.updateInstance = function() {
				//Select box
				$scope.instance.log_file_level = $scope.selectedLog.id;
				$http.post('/dsm/instance/update/', $scope.instance).then(function(response) {
					var msg = "";
					$scope.isWait = true;
					if (!scope.isEmpty(response.data.info)) {
						msg = "Reiniciando a instância com as novas configurações...";
						$scope.msgConfirm = $sce.trustAsHtml(scope.msg({
							info : msg
						}, null));
						$http.get("/dsm/service/restart/" + sharedProperties.getName()).then(function(response) {
							$scope.msgConfirm = $sce.trustAsHtml(scope.msg(response.data, null));
							scope.close("modal_request_instance", close);
						});
					} else if (!scope.isEmpty(response.data.error)) {
						msg = "Não foi possivel realizar esta operação.";
						$scope.msgConfirm = $sce.trustAsHtml(scope.msg({
							danger : msg
						}, null));
					}
				});
			};

			$scope.getInstanceByName = function(name) {
				$http.get("/dsm/instance/get?name=" + name).then(function(response) {
					$scope.instance = response.data;
					$scope.selectedLog = $scope.logLevels[$scope.instance.log_file_level];
				});
			};

			$scope.getLicenseInstancesByType = function(type) {
				$http.get("/dsm/instance/get?type=" + type).then(function(response) {
					$scope.licenseInstancesByType = response.data;
				});
			};
			
			$scope.addRowInstances = function(qtdRows) {
				//console.log('Inside here', $scope.arrAddRows.length);
				if($scope.arrAddRows.length > 0){
					var difLen = qtdRows - $scope.arrAddRows.length;
					if (difLen > 0){
						for (var i = 1; i <= difLen; i++) {
							var obj = $scope.createLicenseInstance($scope.type, $scope.licenseInstancesByType.length+i);
							$scope.arrAddRows.push(obj);
						}
					} else if (difLen < 0){
						var iterator = difLen * (-1);
						for (var i = 1; i <= iterator; i++) {
							$scope.arrAddRows.pop();
						}
					}
				}else{
					$scope.arrAddRows = [];
					//console.log('qtdRows', qtdRows);
					if(qtdRows > 0){
						//console.log('qtdRows', qtdRows);
						for (var i = 1; i <= qtdRows; i++) {
							var obj = $scope.createLicenseInstance($scope.type, $scope.licenseInstancesByType.length+i);
							$scope.arrAddRows.push(obj);
						}
					}
				}
				$scope.updateIds();
			};
			
			$scope.updateIds = function(){
				for (var i = 0; i < $scope.arrAddRows.length; i++) {
					$scope.arrAddRows[i].id = $scope.licenseInstancesByType.length+(i+1); 
				}
			}
			
			$scope.createLicenseInstance = function(type, id){
				var obj = {
						registered : false,
						endDate : $scope.getDefaulfDate(),
						deviceLimit : 10,
						product : type,
						id : id,
						originalEndDate : $scope.getDate()
				};
				return obj;
			}
			
			$scope.appendLicenseInstances = function(targetArr, licenseInstances){
				if ($scope.licenseInstancesByType.length > 0){
					for (var i = 0; i < licenseInstances.length; i++) {
						targetArr.push(licenseInstances[i]);
					}
				}
			}
			
			$scope.isRequestFieldsValid = function(){
				//TODO:
				return true;
			};
			
			

			$scope.confirmRequest = function() {
				var arrToSend = [];
				$scope.appendLicenseInstances(arrToSend, $scope.licenseInstancesByType);
				$scope.appendLicenseInstances(arrToSend, $scope.arrAddRows);
				
				$http.post('/dsm/request/instances/' + $scope.type, arrToSend).then(function(response) {
					if (!scope.isEmpty(response.data.info)) {
						$scope.clearAddedRows();
						$scope.msg = scope.msg(response.data, null);
						$scope.getLicenseInstancesByType($scope.type);
					}else{
						$scope.msg = scope.msg(response.data, null);
					}
					
					setTimeout(function(){
						$scope.trustedHtml = $sce.trustAsHtml($scope.msg);
						$scope.$apply();
					}, 200);
					
				});
//				if ($scope.licenseInstancesByType.length > 0){
//					for (var i = 0; i < $scope.licenseInstancesByType.length; i++) {
//						arrToSend.push($scope.licenseInstancesByType[i]);
//					}
//				}
//				if ($scope.arrAddRows.length > 0){
//					for (var i = 0; i < $scope.arrAddRows.length; i++) {
//						arrToSend.push($scope.arrAddRows[i]);
//					}
//				}	
//				$http.post('/dsm/request/instances/'+sharedProperties.getType(), arrToSend).then(function(response) {
//					if (!scope.isEmpty(response.data.info)) {
//						$scope.arrAddRows = [];
//						$scope.msg = scope.msg(response.data, null);
//						$scope.getLicenseInstancesByType($scope.type);
//					}else{
//						$scope.msg = scope.msg(response.data, null);
//					}
//					setTimeout(function(){
//						$scope.trustedHtml = $sce.trustAsHtml($scope.msg);
//						$scope.$apply();
//					}, 200);
//				});
			};
			
			$scope.clearAddedRows = function(){
				$scope.arrAddRows = [];
			};
			
			$scope.changeEndDate = function(instance){
				if (typeof instance.endDate === 'undefined' ) {
					instance.endDate = instance.originalEndDate;
					instance.endDateExtended = false;
					setTimeout(function() {
						$('#value_error').modal();
						$scope.msgError = "A validade informada não pode ser anterior a da LICENÇA.\n Informe uma data superior a "+$scope.dateToBR(instance.originalEndDate)+".";
						$scope.$apply();
					}, 100);
					return;
				}
				
				var extendedDate = new Date(instance.endDate);
				var originalDate = new Date(instance.originalEndDate+"T00:00:00");

				if (extendedDate.getTime() < originalDate.getTime()){
					instance.endDate = instance.originalEndDate;
					instance.endDateExtended = false;
					setTimeout(function() {
						$('#value_error').modal();
						$scope.msgError = "A validade informada não pode ser anterior a da LICENÇA.\n Informe uma data superior a "+$scope.dateToBR(instance.originalEndDate)+".";
						$scope.$apply();
					}, 100);
				} else {
					instance.endDateExtended = true;
				}
			}
			
			$scope.changeDeviceLimit = function (instance){
				if (instance.registered){
					if (typeof instance.deviceLimit === 'undefined' || instance.deviceLimit < instance.originalDeviceLimit){
						instance.deviceLimit = instance.originalDeviceLimit;
						setTimeout(function() {
							$('#value_error').modal();
							$scope.msgError = "O limite de dispositivo não pode ser menor que o limite da LICENÇA. Informe um valor maior que "+instance.originalDeviceLimit+".";
							$scope.$apply();
						}, 100);
						instance.deviceLimitExtended = false;
					} else if (instance.deviceLimit == instance.originalDeviceLimit) {
						instance.deviceLimitExtended = false;
					} else {
						instance.deviceLimitExtended = true;
					}
				} else {
					// is Update
					if (instance.deviceLimit < 1 || typeof instance.deviceLimit === 'undefined'){
						instance.deviceLimit = 1;
						setTimeout(function() {
							$scope.$apply();
						}, 100);
					}
					instance.deviceLimitExtended = true;
				}
			}
			
			$scope.maxDate = function(date){
				if (!scope.isEmpty(date)){
					var splited = date.split("-");
					return (parseInt(splited[0])+20)+"-"+splited[1]+"-"+splited[2];
				} 
			}
			
			$scope.cancelRequest = function(index) {
				$scope.myForm.$setDirty();
				console.log($scope.myForm)
				if (!$scope.licenseInstancesByType[index].registered){
					$scope.licenseInstancesByType.splice(index,1);
					for(var i = 0; i < $scope.licenseInstancesByType.length; i++){
						$scope.licenseInstancesByType[i].id = (i+1);
					}
				} else {
					//Do not remove item of display, 
					var originalLicense = $scope.getOriginalLicenseInstance(index);
				}
				return true;
			};
			
			$scope.removeRequest = function (index){
				if (index > -1) {
					$scope.myForm.$setDirty();
					console.log($scope.myForm)
					$scope.arrAddRows.splice(index, 1);
					//Update Names
					$scope.updateIds();
				}
			}
			
			$scope.dateToBR = function (value){
				var splited = value.split("-");
				return splited[2]+"/"+splited[1]+"/"+splited[0];
			}
			
			$scope.getOriginalLicenseInstance = function(index){
				$http.post('/dsm/license/instance/original', $scope.licenseInstancesByType[index]).then(function(response) {
					$scope.licenseInstancesByType[index] = response.data;
				});
			}

			$scope.getDefaulfDate = function(){
				return (new Date().getFullYear() + 1)+ "-"+ ($scope.addZeroIfLess10(new Date().getMonth()+1))+ "-"+ ($scope.addZeroIfLess10(new Date().getDate()));
			}

			$scope.getDate = function(){
				return (new Date().getFullYear())+ "-"+ ($scope.addZeroIfLess10(new Date().getMonth()+1))+ "-"+ ($scope.addZeroIfLess10(new Date().getDate()));
			}
			
			$scope.getMinValue = function(instance){
				if (scope.isEmpty(instance)){
					return 1;
				}
				if(instance.registered){
					if (!instance.deviceLimitExtended){
						return instance.deviceLimit;
					}else{
						return instance.originalDeviceLimit;
					}
				}
				return 1;
			}
			
			$scope.checkForm = function(){
				console.log("form: ", $scope.myForm);
			}
			
			// Commands
			switch (command) {
			case "config":
				$scope.msgConfirm = scope.msg({
					warning : 'Após dados serem salvos o serviço da instância será reiniciado, confirma esta operação?'
				}, null);
				$scope.msgConfirm = $sce.trustAsHtml($scope.msgConfirm);
				$scope.getInstanceByName($scope.name);
//				jQuery(function($){
//					$("#server_ip").mask("999.999.999.999");
//				});
				if (scope.isIE()){
					jQuery(function($){
						
						$("#summer_time_start").mask("99/99/9999", {placeholder: 'DD/MM/AAAA' });
						$("#summer_time_end").mask("99/99/9999", {placeholder: 'DD/MM/AAAA' });
					});
				}
				
				break;
			case "install":
				
				$scope.type = sharedProperties.getType();
				$scope.title = "Instalando Instância";
				$scope.installInstance();
				break;
			case "uninstall":
				
				$scope.uninstallInstance();
				break;
			case "request":
				if (scope.isIE()){
					jQuery(function($){
						
						$("#endDate").mask("99/99/9999", {placeholder: 'DD/MM/AAAA' });
						$("#addEndDate").mask("99/99/9999", {placeholder: 'DD/MM/AAAA' });
					});
				}
				$scope.type = sharedProperties.getType();
				$scope.title = "Solicitar - " + $scope.type;
				$scope.getLicenseInstancesByType($scope.type);
				break;
			}
			
}]).directive('dateOb', function() {
		return {
			require : 'ngModel',
			link : function(scope, element, attrs, ngModel) {
				// view --> model (change date to string)
				ngModel.$parsers.push(function(viewValue) {
					return viewValue;
				});

				// model --> view (change string to date)
				ngModel.$formatters.push(function(modelValue) {
					var aux = modelValue.split("/");
					return new Date(aux[2], (aux[1] - 1), aux[0]);

				});
			}
		};
	}).directive('dateFormat', function() {
	return {
		require : 'ngModel',
		link : function(scope, element, attrs, ngModel) {
			// view --> model (change date to string)
			ngModel.$parsers.push(function(viewValue) {
				console.log("view --> model: ", viewValue, attrs);
				if (attrs["dateFormat"] !== ""){
					return viewValue;
				} else if (typeof viewValue == 'object'){
					viewValue = viewValue.getTime();
				}
				return viewValue;
			});

			// model --> view (change string to date)
			ngModel.$formatters.push(function(modelValue) {
				if (typeof  modelValue !== 'undefined'){
					var dt = new Date(modelValue);
					var day = scope.addZeroIfLess10(dt.getUTCDate());
					var month = scope.addZeroIfLess10(dt.getUTCMonth() + 1);
					if (scope.isIE()){
						return (day)+"/"+(month)+"/"+dt.getUTCFullYear();
					}else{
						modelValue = dt.getUTCFullYear()+"-"+(month)+"-"+(day);
					}
				}
				return new Date(modelValue + "T00:00:00");
			});
		}
	};
});