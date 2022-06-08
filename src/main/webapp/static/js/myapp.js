'use strict';
var teste;
var app = angular.module('app', [ 'angularModalService', 'utf8-base64',
		'ngValidate' ]);

app.controller('controller', [ '$location', '$scope', '$rootScope', '$http', 'ModalService', 'sharedProperties', 'base64',
						function($location, $scope, $rootScope, $http, ModalService, sharedProperties, base64) {

	// ================================= 
	// NEW CHANGES - Author: Marcos Alex
	// ================================
	// Propriedades
	$scope.license;
	$scope.instances;
	$scope.modules;
	$scope.company;
	$scope.machine;
	$scope.username;
	$scope.instance_types;
	// Services
	$scope.svcDiginetWeb;
	$scope.svcDiginetDSM;
	$scope.svcBancoDados;
	$scope.svcDiginet = [];
	$scope.svcSAM = [];
	$scope.svcGAS = [];

	// Flags
	$scope.visInstances = false;
	$scope.servicesLoaded = false;
	$scope.initOK = false;
	$scope.hasConnection = false;
	
	// eNUM
	$scope.instanceEnum  = {
		DIGINET: 4,
		GAS: 21,
		SAM: 22
	}

	$scope.msgError = "";
	$scope.isFailed = false;

	$scope.init = function() {
		$http.get("/dsm/init").then(function(response) {
			console.log("INIT: ", response.data);
			if ($scope.isEmpty(response.data)) {
				$scope.msgError = "Nenhum registo encontrado.\r\n Reinicie a aplicação. Caso persista, contate o suporte Digicon.\r\n suporte.vca@digicon.com.br";
				$scope.isFailed = true;
			} else {
				$scope.license = response.data;
				$scope.hasConnection = $scope.license.hasConnection;
				$scope.username = $scope.license.company.username;
				$scope.instances = $scope.license.instances;
				$scope.modules = $scope.license.modules;
				$scope.company = $scope.license.company;
				$scope.machine = $scope.license.machine;
				$scope.instance_types = $scope.license.instance_types;

				// Shared with other controller
				sharedProperties.setInstances($scope.instances);
				sharedProperties.setModules($scope.modules);
				$scope.initOK = true;
			}
		},
		function(data) {
			$scope.initOK = false;
			$scope.msgError = "Erro ao iniciar o DSM.\r\n Reinicie a aplicação. Caso persista, contate o suporte Digicon.\r\n suporte.vca@digicon.com.br";
		});
	};

	$scope.init();

	
	if (!String.prototype.endsWith) {
		String.prototype.endsWith = function(searchString, position) {
		var subjectString = this.toString();
		if (typeof position !== 'number' || !isFinite(position) || Math.floor(position) !== position || position > subjectString.length) {
			position = subjectString.length;
		}
		position -= searchString.length;
		var lastIndex = subjectString.indexOf(searchString, position);
		return lastIndex !== -1 && lastIndex === position;
	  };
	}
	
	if (!String.prototype.includes) {
		String.prototype.includes = function(search, start) {
			if (typeof start !== 'number') {
				start = 0;
			}

			if (start + search.length > this.length) {
				return false;
			} else {
				return this.indexOf(search, start) !== -1;
			}
	    };
	}
	
	
	$scope.getServices = function() {
		$http.get("/dsm/service/get/status").then(
		function(response) {
			
			if (!$scope.isEmpty(response.data)) {
				$scope.servicesLoaded = true;
				var services = response.data;
				// clear
				$scope.svcDiginetWeb = {};
				$scope.svcDiginetDSM = {};
				$scope.svcBancoDados = {};
				$scope.svcDiginet = {};
				$scope.svcGAS = {};
				$scope.svcSAM = {};

				// loop services
				for (var i = 0; i < services.length; i++) {
					var obj = services[i];
					if (obj.name.endsWith("dsm")) {
						$scope.svcDiginetDSM = services[i];
					} else if (obj.name.includes("tomcatdiginet")) {
						$scope.svcDiginetWeb = services[i];
					} else if (obj.name.includes("mysql")) {
						$scope.svcBancoDados = services[i];
					} else if (obj.name.includes("diginet-")) {
						$scope.svcDiginet[removeHifen(services[i].name)] = services[i].status;
					} else if (obj.name.includes("gas-")) {
						$scope.svcGAS[removeHifen(services[i].name)] = services[i].status;
					} else if (obj.name.includes("sam-")) {
						$scope.svcSAM[removeHifen(services[i].name)] = services[i].status;
					}
				}
				$scope.visInstances = true;
				
				//console.log("svc svcDiginetWeb", $scope.svcDiginetWeb);
				//console.log("svc DiginetDSM", $scope.svcDiginetDSM);
				
				//console.log("svc diginet", $scope.svcDiginet);
				//console.log("svc GAS", $scope.svcGAS);
				//console.log("svc SAM", $scope.svcSAM);
			}
		});
	};

	$scope.getServiceByName = function(name) {
		$http.get("/dsm/service/get/status?name="+ name).then(function(response) {
			if (!$scope.isEmpty(response.data)) {
				var service = response.data[0];
				var obj = service;
				if (obj.name.endsWith("dsm")) {
					$scope.svcDiginetDSM = service;
				} else if (obj.name.includes("tomcatdiginet")) {
					$scope.svcDiginetWeb = service;
				} else if (obj.name.includes("mysql")) {
					$scope.svcBancoDados = service;
				} else if (obj.name.includes("diginet-")) {
					$scope.svcDiginet[removeHifen(service.name)] = service.status;
				} else if (obj.name.includes("gas-")) {
					$scope.svcGAS[removeHifen(service.name)] = service.status;
				} else if (obj.name.includes("sam-")) {
					$scope.svcSAM[removeHifen(service.name)] = service.status;
				}
			}
		});
	};

	$scope.replaceAndDecode = function(string) {
		var replaced = string.toString().replace(/DIGICONEQUAL/g, '=');
		var ret;
		// check if is base64
		var base64Matcher = new RegExp("^(?:[A-Za-z0-9+/]{4})*(?:[A-Za-z0-9+/]{2}==|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{4})$");
		if (base64Matcher.test(replaced)) {
			ret = base64.decode(replaced);
		} else {
			ret = replaced;
		}
		return ret;
	}

	$scope.msg = function(strJson, align) {
		if ($scope.isEmpty(align)) {
			align = "center";
		}
		var ret;
		if (!$scope.isEmpty(strJson)) {
			switch (Object.keys(strJson)[0]) {
			case "info":
				ret = $scope.replaceAndDecode(strJson.info);
				return "<pre class=\"text-" + align+ " bg-info\">" + ret+ "</pre>";
			case "warning":
				ret = $scope.replaceAndDecode(strJson.warning);
				return "<pre class=\"text-" + align+ " bg-warning\">" + ret+ "</pre>";
			case "error":
				ret = $scope.replaceAndDecode(strJson.error);
				return "<pre class=\"text-" + align+ " bg-danger\">" + ret+ "</pre>";
			default:
				ret = $scope.replaceAndDecode(strJson.info);
				return "<pre class=\"text-" + align+ " bg-info \">" + ret+ "</pre>";
			}
		}
		return " ";
	};

	// Get services status
	angular.element(document).ready(function() {
		$scope.getServices();
	});

	// CALL MODALS
	$scope.installModule = function(product, moduleName) {
		sharedProperties.setProduct(product);
		sharedProperties.setName(moduleName);
		ModalService.showModal({
			templateUrl : '/dsm/modal/install-module',
			controller : "ModuleController",
			inputs : {
				command : "install",
				scope : $scope
			}
		}).then(function(modal) {
			modal.element.modal();
			modal.close.then(function(result) {
			//$scope.message = "You said "+ result;
			});
		});
	};

	$scope.requestModule = function(product, moduleName) {
		sharedProperties.setProduct(product);
		sharedProperties.setName(moduleName);
		ModalService.showModal({
			templateUrl : '/dsm/modal/request-module',
			controller : "ModuleController",
			inputs : {
				command : "request",
				scope : $scope
			}
		}).then(function(modal) {
			modal.element.modal();
			modal.close.then(function(result) {
				//$scope.message = "You said "+ result;
			});
		});
	};

	$scope.cancelModule = function(product, moduleName) {
		sharedProperties.setProduct(product);
		sharedProperties.setName(moduleName);
		ModalService.showModal({
			templateUrl : '/dsm/modal/cancel-module',
			controller : "ModuleController",
			inputs : {
				command : "cancel",
				scope : $scope
			}
		}).then(function(modal) {
			modal.element.modal();
			modal.close.then(function(result) {
				// $scope.message = "You said "+ result;
			});
		});
	};

	$scope.showLog = function(path) {
		ModalService.showModal({
			templateUrl : '/dsm/modal/show-log',
			controller : "LogController",
			inputs : {
				path : path,
				command : "show",
				scope : $scope
			}
		}).then(function(modal) {
			modal.element.modal();
			modal.close.then(function(result) {
				// $scope.message = "You said " + result;
			});
		});
	};

	$scope.alterLogin = function() {
		ModalService.showModal({
			templateUrl : '/dsm/modal/alter-login',
			controller : "LicenseController",
			inputs : {
				command : "update_login",
				scope : $scope
			}
		}).then(function(modal) {
			modal.element.modal();
			modal.close.then(function(result) {
			});
		});
	};

	$scope.updateLicense = function() {
		ModalService.showModal({
			templateUrl : '/dsm/modal/update-license',
			controller : "LicenseController",
			inputs : {
				command : "update_license",
				scope : $scope
			}
		}).then(function(modal) {
			$('#modal_update_license').modal();
//			$("#licence_file").filestyle({
//				buttonText : "Escolha o arquivo",
//				buttonBefore : true,
//				size : "sm"
//			});
		});
	};

	$scope.changeDiginetPort = function() {
		ModalService.showModal({
			templateUrl : '/dsm/modal/change-diginet-port',
			controller : "ConfigController",
			inputs : {
				command : "change_port",
				scope : $scope
			}
		}).then(function(modal) {
			$('#modal_change_port').modal();
		});
	};

	$scope.sendUpdateKey = function() {
		ModalService.showModal({
			templateUrl : '/dsm/modal/send-update-key',
			controller : "LicenseController",
			inputs : {
				command : "send_update_key",
				scope : $scope
			}
		}).then(function(modal) {
			$('#modal_send_update_key').modal({
				backdrop : 'static',
				keyboard : false
			});
		});
	};

	$scope.sendLog = function(path) {
		ModalService.showModal({
			templateUrl : '/dsm/modal/wait-log',
			controller : "LogController",
			inputs : {
				path : path,
				command : "send",
				scope : $scope
			}
		}).then(function(modal) {
			modal.element.modal();
			modal.close.then(function(result) {
				// $scope.message = "You said " + result;
			});
		});
	};

	$scope.deleteLog = function(path) {
		ModalService.showModal({
			templateUrl : '/dsm/modal/wait-log',
			controller : "LogController",
			inputs : {
				path : path,
				command : "delete",
				scope : $scope
			}
		}).then(function(modal) {
			modal.element.modal();
			modal.close.then(function(result) {
				// $scope.message = "You said " + result;
			});
		});
	};

	$scope.configInstance = function(value) {
		sharedProperties.setName(value);
		ModalService.showModal({
			templateUrl : '/dsm/modal/config-instance',
			controller : "InstanceController",
			inputs : {
				command : "config",
				scope : $scope
							}
		}).then(function(modal) {
			$('#main-modal').modal({
				backdrop : 'static',
				keyboard : false
			});
			modal.close.then(function(result) {
			});
		});
	};

	$scope.installInstance = function(value) {
		sharedProperties.setType(value);
		ModalService.showModal({
			templateUrl : '/dsm/modal/wait-install',
			controller : "InstanceController",
			backdrop : 'static',
			keyboard : false,
			inputs : {
				command : "install",
				scope : $scope
			}
		}).then(function(modal) {
			modal.element.modal({
				backdrop : 'static',
				keyboard : false
			});
			modal.close.then(function(result) {
				//$scope.message = "You said " + result;
			});
		});
	};

	$scope.openModalTest = function() {
		ModalService.showModal({
			templateUrl : '/dsm/modal/config-modal',
			controller : "InstanceController",
			backdrop : 'static',
			keyboard : false,
			inputs : {
				command : "test",
				scope : $scope
			}
		}).then(function(modal) {
			modal.element.modal({
				backdrop : 'static',
				keyboard : false
			});
			modal.close.then(function(result) {
				// $scope.message = "You said " + result;
			});
		});
	};

	$scope.requestInstance = function(type) {
		sharedProperties.setType(type);
		ModalService.showModal({
			templateUrl : '/dsm/modal/request-instance',
			controller : "InstanceController",
			inputs : {
				command : "request",
				scope : $scope
			}
		}).then(function(modal) {
			$('#modal_request_instance').modal({
				backdrop : 'static',
				keyboard : false
			});
			modal.close.then(function(result) {
				// $scope.message = "You said " + result;
			});
		});
	};

	$scope.close = function(id, executeClose) {
		if (id != null && typeof id !== 'undefined') {
			$('#' + id).remove();
		}
		if (typeof executeClose === 'function') {
			executeClose("Closed", 200);
		}
		$('.modal-backdrop').remove();
		$("body").removeClass("modal-open");
		$("body").css("padding-right", "0");
	};

	// ------------------ OrderBy -----------------
	$scope.orderModule = "type";
	$scope.reverseModule = false;
	$scope.orderByModule = function(col) {
		$scope.orderModule = col;
		$scope.reverseModule = !$scope.reverseModule;
	};

	$scope.orderInstance = "name";
	$scope.reverseInstance = false;
	$scope.orderByInstance = function(col) {
		$scope.orderInstance = col;
		$scope.reverseInstance = !$scope.reverseInstance;

		setTimeout(function() {
			$scope.$apply();
		}, 2000);

	};

	$scope.orderFeature = "name";
	$scope.orderByFeature = function(col) {
		$scope.orderFeature = col;
		$scope.reverseFeature = !$scope.reverseFeature;
	};

	$scope.orderServer = "id";
	$scope.orderByServer = function(col) {
		$scope.orderServer = col;
		$scope.reverseServer = !$scope.reverseServer;
	};
	// ------------- END - OrderBy -----------------

	$scope.isServiceRunning = function(status) {
		var str;
		if (!$scope.isEmpty(status)) {
			if (status.includes("diginet-")) {
				str = $scope.svcDiginet[removeHifen(status)];
			} else if (status.includes("gas-")) {
				str = $scope.svcGAS[removeHifen(status)];
			} else if (status.includes("sam-")) {
				str = $scope.svcSAM[removeHifen(status)];
			} else {
				str = status;
			}

			switch (str) {
			case "RUNNING":
				return true;
			case "STOPPED":
				return false;
			case "SOME_ERROR_OCURRED":
				return false;
			default:
				return false;
				break;
			}
		}
	};

	$scope.stopService = function(name) {
		sharedProperties.setName(name);
		ModalService.showModal({
			templateUrl : '/dsm/modal/wait-service',
			controller : "ServiceController",
			inputs : {
				command : "stop",
				scope : $scope
			}
		}).then(function(modal) {
			modal.element.modal({
				backdrop : 'static',
				keyboard : false
			});
			modal.close.then(function(result) {
			});
		});
	};

	$scope.startService = function(name) {
		sharedProperties.setName(name);
		ModalService.showModal({
			templateUrl : '/dsm/modal/wait-service',
			controller : "ServiceController",
			inputs : {
				command : "start",
				scope : $scope
			}		
		}).then(function(modal) {
			modal.element.modal({
				backdrop : 'static',
				keyboard : false
			});
			modal.close.then(function(result) {
			});
		});
	};

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
		// it is empty, but it can't be anything *but*empty
		// Is it empty? Depends on your application.
		if (typeof obj !== "object")
			return true;

		// Otherwise, does it have any properties of its own?
		// Note that this doesn't handletoString and valueOf enumeration bugs in IE < 9
		for ( var key in obj) {
			if (hasOwnProperty.call(obj, key))
				return false;
		}
		return true;
	};
	

	$scope.logout = function() {
		$http.get("/dsm/logout").then(function(response) {
			if (response.data == true) {
				location.reload();
			}
		});
	};
	
	$scope.isIE = function(){
	    var ua = window.navigator.userAgent;
	    var msie = ua.indexOf("MSIE ");

	    if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./))  // If Internet Explorer, return version number
	    {
	    	return true;
	    }
	    return false;
	}

}])

.directive('tableSameHeight',function() {
	return { 
		link : function(scope, element, attrs) {
			var i, highestValue = 0;
			angular.element(document).ready(function() {
				for (i = 0; i < element[0].querySelectorAll('table').length; i++) {
					if (highestValue < element[0].querySelectorAll('table')[i].offsetHeight) {
						highestValue = element[0].querySelectorAll('table')[i].offsetHeight;
						teste = element[0].querySelectorAll('table')[i].offsetHeight;
					}
				}
				for (i = 0; i < element[0].querySelectorAll('table').length; i++) {
					var e = element[0].querySelectorAll('table')[i];
						e.style.height = highestValue+ 'px';
				}
			});
		}
	};
}).directive('heightWindow', function($window) {
	Log("Entrou em heightWindow", "");
	return {
		link : function(scope, element, attrs) {
			element.css('height', $window.innerHeight + 'px');
		}
	};
}).directive('changeGrid', function() {
	return {
		restrict : 'A',
		link : function(scope, element, attrs) {
			element.on('click', function() {
				for (var i = 0; i < scope.listGrid.length; i++) { 
				    var grid = scope.listGrid[i];
				    console.log()
				    if (grid == attrs.target){
				    	console.log('current grid');
				    	$(grid).addClass('in');
			    	} else {
			    		$(grid).removeClass('in');
			    	}
				}
			});
		}
	};
});


/**
 * Filters
 */
app.filter('dateToISO', function() {
	return function(input) {
		return new Date(input).toISOString();
	};
});

app.filter('brdate', function() {
	return function(input) {
		var splited = input.split("-");
		return splited[2]+"/"+splited[1]+"/"+splited[0];
	};
});

app.filter('toUpper', function() {
	return function(input) {
		return input.toUpperCase();
	};
});


/**
 * Services
 */
// Use service to share variables between controllers
app.service('sharedProperties', function() {
	var modules = "";
	var intances = "";
	var index = 0;
	var type = "";
	var name = "";
	var product = "";
	var file = "";

	return {
		getModules : function() {
			return modules;
		},
		setModules : function(value) {
			modules = value;
		},
		getInstances : function() {
			return intances;
		},
		setInstances : function(value) {
			intances = value;
		},
		getIndex : function() {
			return index;
		},
		setIndex : function(value) {
			index = value;
		},
		getType : function() {
			return type;
		},
		setType : function(value) {
			type = value;
		},
		getName : function() {
			return name;
		},
		setName : function(value) {
			name = value;
		},
		getProduct : function() {
			return product;
		},
		setProduct : function(value) {
			product = value;
		},
		getFile : function() {
			return file;
		},
		setFile : function(value) {
			file = value;
		}
	};
});


/**
 * Local Funcions
 */
function Log(text, obj) {
	
}

function removeHifen(str) {
	var array;
	array = str.split("-");
	return array[0] + array[1];

}
