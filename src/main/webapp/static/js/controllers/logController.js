app.controller('LogController', function($scope, $sce, $http, scope , path, command){
	Log('Entrou no LogController', '');
	
	
	$scope.log = "";
	$scope.path = path;
	$scope.title = "";
	$scope.logMsg = ""; 
	$scope.trustedHtml;
	
	$scope.fileExists;
	 
	$scope.getContentFile = function(path){
		$http.get("/dsm/log/get/"+path).then(function(response) {
			$scope.log = response.data;
		});
	};

	function requisite(path, ret, callback){
		return $http.get("/dsm/downloads/check/"+$scope.path).then(function(response) {
	        callback && callback(response.data.info, ret);
	    });  
	}; 
	
	function downloadFile(){
		var downloadFrame =  document.createElement("iframe");  
		downloadFrame.setAttribute("src", "/dsm/downloads/log/"+$scope.path);
		downloadFrame.setAttribute("style", "display: none"); //hiding the element  
		downloadFrame.setAttribute("id", "downFrame");        //creating a name to the element 
		document.body.appendChild(downloadFrame);
		
		setTimeout(function(){  
			var msg = "\tLog salvo na pasta de <strong>downloads</strong> do browser ('Downloads')\n"+
					  "\tCaso solicitado pelo suporte, enviar para o e-mail: <strong>suporte.vca@digicon.com.br</strong>\n"+
					  "\tColocar no assunto \"Log diginet para análise, empresa: <strong>"+scope.company.name+"</strong>\"";
			$scope.logMsg = scope.msg({info: msg}, "left");		               
			$scope.trustedHtml = $sce.trustAsHtml($scope.logMsg);	
			$scope.$apply(); 
		}, 1500);
		    
		var frameElement = document.getElementById("downFrame"); 
		setTimeout(function(){ 
			frameElement.remove();   
		}, 200);
	}

	function downloadFail(){
		var msg = "\tNão há logs para serem baixados.\n";
		$scope.logMsg = scope.msg({warning: msg}, "left");	 	               
		$scope.trustedHtml = $sce.trustAsHtml($scope.logMsg);	
		$scope.$apply(); 
	}
	 
	$scope.sendLog = function(path){
		var ret = { answer: "" };
		requisite(path, ret, function logInfo(information, ret){
			ret.answer = information;      
			if (ret.answer == false){    
				downloadFail();
			} else { 
				downloadFile(); 
			}
		});   
	};   
	 
	$scope.deleteLog = function(){
		$http.get("/dsm/log/delete/"+$scope.path).then(function(response) {	
			setTimeout(function(){  
				$scope.logMsg = scope.msg(response.data, null);		 
				$scope.trustedHtml = $sce.trustAsHtml($scope.logMsg);	
				$scope.$apply();
			}, 1500);			
		});
	};  
	 
	// Commands
	switch (command) {
	case "show":
		
		$scope.title = "Apresentando arquivo de Log";
		$scope.getContentFile($scope.path);
		break;
	case "send":
		
		$scope.title = "Enviando arquivo de Log";
		$scope.sendLog($scope.path);    
		break;   
	case "delete":
		 
		$scope.title = "Deletando arquivo de Log";
		$scope.deleteLog(); 
		break; 
	default:
		break;
	}
}); 