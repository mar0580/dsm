<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.*" %>
<%
Random rand = new Random();
int n = rand.nextInt();
%>
<html ng-app="app">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>DSM - Diginet Solution Manager</title>
<link rel="icon" href="static/img/logo.png" type="image/x-icon">
<link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"></link> 
<link href="<c:url value='/static/css/toggle.css' />" rel="stylesheet"></link>
<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
<script src="/dsm/static/js/jquery-3.2.0.min.js?r=<%=n%>"></script>
<script src="/dsm/static/js/jquery.maskedinput.min.js?r=<%=n%>"></script>
<script src="/dsm/static/js/jquery.validate.min.js?r=<%=n%>"></script>
<script src="/dsm/static/js/bootstrap-filestyle.min.js?r=<%=n%>"></script>
<script src="/dsm/static/menu/js/bootstrap.min.js?r=<%=n%>"></script>
<script src="/dsm/static/js/bootstrap-toggle.js?r=<%=n%>"></script>
<script src="/dsm/static/js/angular.min.js?r=<%=n%>"></script>
<script src="/dsm/static/js/angular-modal-service.js?r=<%=n%>"></script>
<script src="/dsm/static/js/modules/angular-validate.js?r=<%=n%>"></script>
<script src="/dsm/static/js/angular-utf8-base64.js?r=<%=n%>"></script>
<script src="/dsm/static/js/myapp.js?r=<%=n%>"></script>
<script src="/dsm/static/js/controllers/instanceController.js?r=<%=n%>" ></script> 
<script src="/dsm/static/js/controllers/logController.js?r=<%=n%>"></script>
<script src="/dsm/static/js/controllers/licenseController.js?r=<%=n%>"></script>
<script src="/dsm/static/js/controllers/serviceController.js?r=<%=n%>"></script>
<script src="/dsm/static/js/controllers/moduleController.js?r=<%=n%>"></script>
<script src="/dsm/static/js/controllers/configController.js?r=<%= n%>"></script>

<script type="text/javascript">
    // IE9 fix
    if(!window.console) {
        var console = {
            log : function(){},
            warn : function(){},
            error : function(){},
            time : function(){},
            timeEnd : function(){}
        }
    }
</script>

</head>
<body ng-app="aplicativo" ng-controller='controller'>
	<div ng-if="!visInstances && !initOK" id="loading">
		<img class="img-responsive" style="height: 4em; margin-bottom: 3em" src="/dsm/static/img/logo_diginet.jpg">
		<div ng-if="msgError === ''">
			<h1 class="text-center ">Carregando módulos e instâncias...</h1>
			<img class="img-responsive" style="height: 5em" src="/dsm/static/img/loading.gif" />
		</div>
		<pre style="width: 50%; margin: auto" ng-if="msgError !== ''" ng-cloak class="text-center bg-danger">{{msgError}}</pre>
	</div>
	<nav class="navbar navbar-default">
		<div class="container-fluid header"> 
			<div class="navbar-header">
				<a class="navbar-brand" href="#"> <img class="img-responsive" style="height: 43px" src="/dsm/static/img/logo_diginet.jpg"></a>
				<label class="diginet-version">4.6.6.0</label>
			</div> 
			<a class="navbar-brand pull-right" href="#"> <img class="img-responsive" style="height: 43px" src="/dsm/static/img/logo_digicon.png"></a>
			<h1 class="text-center navbar-text txt-title">Diginet Solution Manager</h1>
		</div>
	</nav>
	<div ng-if="msgError === '' " ng-cloak style="width: 97%; display: block; margin: auto; margin-top: -11px; margin-bottom: 22px;">
		<h3 class="text-center bg-danger">{{msgError}}</h3>
	</div>
	<div class="container-fluid" ng-if="!isFailed" ng-cloak>
		<div class="row" id="first-block" table-same-height ng-cloak>
			<div class="col-xs-12 col-sm-4 col-md-4 col-lg-4  table-responsive">
				<table id="tbl_prop" class="table table-hover table-striped table-condensed table-bordered">
					<thead>
						<tr>
							<th class="bg-title text-center" colspan="5">PROPRIETÁRIA</th>
						</tr>
					</thead>
					<tbody>
					<tr>
							<td class="col-md-6"><strong>Empresa:</strong></td>
							<td>{{ company.name }}</td>
						</tr>
						<tr>
							<td class="col-md-6"><strong>CNPJ:</strong></td>
							<td>{{ company.identifier }}</td>
						</tr>
						<tr>
							<td class="col-md-6"><strong>Validade:</strong></td>
							<td>{{ instances[0].endDate }}</td>
						</tr>
						<tr>
							<td class="col-md-6"><strong>Licença</strong></td>
			  				<td class="text-center">
								<a href="#" ng-click="updateLicense()" class="btnLeft"> 
									<span class="glyphicon glyphicon-upload text-primary" aria-hidden="true" title="Atualizar licença"></span>
								</a>
								<a href="#" ng-click="sendUpdateKey()" class="btnLeft"> 
									<span class="glyphicon glyphicon-export text-warning" aria-hidden="true" title="Enviar solicitações para Digicon"></span>
								</a>
							</td>
						</tr>
						<tr>
							<td class="col-md-6"><strong>Login</strong></td>
			  				<td class="text-center">
								<a href="#" ng-click="alterLogin()" class="btnLeft"> 
									<span class="glyphicon glyphicon-cog text-success" aria-hidden="true" title="Alterar a senha"></span>
								</a>
								<a href="#" ng-click="logout()" class="btnLeft"> 
									<span class="glyphicon glyphicon-log-out text-danger" aria-hidden="true" title="Sair"></span>
								</a>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="col-xs-12 col-sm-4 col-md-4 col-lg-4  table-responsive">
				<table id="tbl_service_manager" class="table table-hover table-striped table-condensed table-bordered">
					<thead>
						<tr class="bg-title">
							<th colspan="10" class="text-center">GERENCIAMENTO DOS SERVIÇOS</th>
						</tr>
						<tr class="bg-subtitle">
							<th class="col-lg-4">Nome</th>
							<th class="col-lg-2 text-center">Status/Ação</th>
							<!-- <th class="col-lg-2 text-center">Iniciar/Parar</th> -->
							<th class="col-lg-4">Logs</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><strong>Diginet-WEB</strong></td>
							<td class="text-center">
								<img ng-if="!servicesLoaded" class="img-responsive img-center load-image-service" src="/dsm/static/img/loading.gif" /> 
								<a href="#" ng-click="startService('tomcatdiginet')" ng-if="servicesLoaded && !isServiceRunning(svcDiginetWeb.status)" title="Clique para iniciar o serviço" > 
									<span class="glyphicon glyphicon-play-circle text-danger" aria-hidden="true"></span>
								</a> 
								<a href="#" ng-click="stopService('tomcatdiginet')" ng-if="servicesLoaded && isServiceRunning(svcDiginetWeb.status)" title="Clique para parar o serviço" >
									<span class="glyphicon glyphicon-stop btn-stop text-success" aria-hidden="true"></span>
								</a>
							</td>
							<td>
								<a href="#" class="btnLog" ng-click="showLog('tomcatdiginet')">
									<span class="glyphicon glyphicon-search text-success" aria-hidden="true" title="Visualizar Log" ></span>
								</a> 
								<a href="#" class="btnLog" ng-click="sendLog('tomcatdiginet')"> 
									<span class="glyphicon glyphicon-send text-primary" aria-hidden="true" title="Enviar Log"></span>
								</a> 
								<a href="#" class="btnLog" ng-click="deleteLog('tomcatdiginet')"> 
									<span class="glyphicon glyphicon-trash text-danger" aria-hidden="true" title="Excluir Log"></span>
								</a>
								<a href="#" class="btnLog" ng-click="changeDiginetPort()"> 
									<span class="glyphicon glyphicon-cog text-success" aria-hidden="true" title="Alterar Portas de Comunicação"></span>
								</a>
							</td>
						</tr>
						<tr>
							<td><strong>Diginet-DSM</strong></td>
							<td class="text-center">
								<span>-</span>
							</td>
							<td>
								<a href="#" class="btnLog" ng-click="showLog('tomcatdiginetdsm')"> 
									<span class="glyphicon glyphicon-search text-success" aria-hidden="true" title="Visualizar Log" ></span>
								</a> 
								<a href="#" class="btnLog" ng-click="sendLog('tomcatdiginetdsm')"> 
									<span class="glyphicon glyphicon-send text-primary" aria-hidden="true" title="Enviar Log"></span>
								</a> 
								<a href="#" class="btnLog" ng-click="deleteLog('tomcatdiginetdsm')"> 
									<span class="glyphicon glyphicon-trash text-danger" aria-hidden="true" title="Excluir Log" ></span>
								</a>
							</td>
						</tr>
						<tr>
							<td><strong>Banco de Dados</strong></td>
							<td class="text-center">
								<img ng-if="!servicesLoaded" class="img-responsive img-center load-image-service" src="/dsm/static/img/loading.gif"/> 
								<a href="#" ng-click="startService('mysql')" ng-if="servicesLoaded && !isServiceRunning(svcBancoDados.status)" title="Clique para iniciar o serviço"> 
									<span class="glyphicon glyphicon-play-circle text-danger" aria-hidden="true"></span>
								</a> 
								<a href="#" ng-click="stopService('mysql')" ng-if="servicesLoaded && isServiceRunning(svcBancoDados.status)" title="Clique para parar o serviço" > 
									<span class="glyphicon glyphicon-stop btn-stop text-success" aria-hidden="true"></span>
								</a>
							</td>
							<td>
								<a href="#" class="btnLog" ng-click="showLog('mysql')"> 
									<span class="glyphicon glyphicon-search text-success" aria-hidden="true" title="Visualizar Log" ></span>
								</a> 
								<a href="#" class="btnLog" ng-click="sendLog('mysql')"> 
									<span class="glyphicon glyphicon-send text-primary" aria-hidden="true" title="Enviar Log"></span>
								</a> 
								<a href="#" class="btnLog" ng-click="deleteLog('mysql')"> 
									<span class="glyphicon glyphicon-trash text-danger" aria-hidden="true" title="Excluir Log" ></span>
								</a>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="col-xs-12 col-sm-4 col-md-4 col-lg-4  table-responsive">
				<table id="tbl_services" class="table table-hover table-striped table-condensed table-bordered">
					<thead>
						<tr class="bg-title">
							<th colspan="10" class="text-center">INSTÂNCIAS - SERVIDORES</th>
						</tr>
						<tr class="bg-subtitle">
							<th class="col-lg-6 text-left">Nome</th>
							<th class="col-lg-3 text-center">Status</th>
							<th class="col-lg-3 text-center">Disponíveis</th>
							<th class="col-lg-3 text-center">Instalar</th>
						</tr>
					</thead>
					<tbody>
						<tr ng-repeat="type in instance_types | orderBy: 'name'">
							<td>{{ type.name }}</td>
							<td class="text-center">
								<span ng-if="type.included" class="glyphicon glyphicon-ok-circle text-success" aria-hidden="true"></span> 
								<span ng-if="!type.included" class="glyphicon glyphicon-remove-circle text-danger" aria-hidden="true"></span>
							</td>
							<td class="text-center">{{ type.qtdAvailable }}</td>
							<td class="text-center">
								<a href="#" ng-if="type.included && type.qtdAvailable != 0" ng-click="installInstance(type.name)"> 
									<span class="glyphicon glyphicon glyphicon-plus-sign text-success" aria-hidden="true" title="Instalar Instância"></span>
								</a> 
								<span ng-if="type.qtdAvailable == 0">-</span>
							</td>
						</tr>
						<tr ng-if="visInstances && isEmpty(instance_types)" style="background-color: #bdbdbd">
							<td colspan="5" class="text-center" style="line-height: 80px">
								<label>Não foi possível recuperar as instâncias no banco de dados.</label>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<!-- End  Row -->
		<div class="row" id="second-block">
			<div class="col-lg-12 table-responsive">
				<table id="tbl_instance_manager" class="table table-hover table-striped table-condensed table-bordered">
					<thead>
						<tr class="bg-title">
							<th colspan="10" class="text-center">GERENCIAMENTO DAS INSTÂNCIAS DE COMUNICAÇÃO E TAREFAS</th>
						</tr>
						<tr class="bg-subtitle">
							<th class="col-lg-3 pointer" ng-click="orderByInstance('name')">
								Nome <span class="sortorder" ng-if="orderInstance === 'name'" ng-class="{reverse: reverseInstance}"></span>
							</th>
							<th class="col-lg-2 text-center">Status/Ação</th>
							<th class="col-lg-2 text-center">Configurações</th>
							<th class="col-lg-2 text-center">Dispositivos habilitados</th>
							<th class="col-lg-2 text-center">Dispositivos ativos</th>
							<th class="col-lg-1">Logs</th>
						</tr>
					</thead>
					<tbody>
						<tr ng-repeat="instance in instances | orderBy: 'name' | orderBy: orderInstance:reverseInstance">
							<!-- Name -->
							<td>
								<strong>{{instance.instance_name}}</strong>
							</td>
							<!-- Status/Action -->
							<td class="text-center">
								<img ng-if="!servicesLoaded" class="img-responsive img-center load-image-service" src="/dsm/static/img/loading.gif"/> 
								<a href="#" ng-click="startService(instance.instance_name)" ng-if="servicesLoaded && !isServiceRunning(instance.instance_name)"> 
									<span class="glyphicon glyphicon-play-circle text-danger" aria-hidden="true" title="Clique para iniciar o serviço" ></span>
								</a> 
								<a href="#" ng-click="stopService(instance.instance_name)" ng-if="servicesLoaded && isServiceRunning(instance.instance_name)"> 
									<span class="glyphicon glyphicon-stop btn-stop text-success" aria-hidden="true" title="Clique para parar o serviço"></span>
								</a>
							</td>
							<!-- Config -->
							<td class="text-center">
								<a href="#" ng-disabled="instance.instance_status == 3" ng-click="configInstance(instance.instance_name)"> 
									<span class="glyphicon glyphicon-cog text-success" aria-hidden="true" title="Configurar Instância"></span>
								</a>
							</td>
							<!-- Device limit -->
							<td class="text-center">{{ instance.deviceLimit }}</td>
							<!-- Device list -->
							<td class="text-center">{{ instance.device_list }}</td>
							<!-- Logs -->
							<td>
								<a href="#" class="btnLog" ng-click="showLog(instance.instance_name)"> 
									<span class="glyphicon glyphicon-search text-success" aria-hidden="true" title="Visualizar Log"></span>
								</a> 
								<a href="#" class="btnLog" ng-click="sendLog(instance.instance_name)"> 
									<span class="glyphicon glyphicon-send text-primary" aria-hidden="true" title="Enviar Log"></span>
								</a> 
								<a href="#" class="btnLog" ng-click="deleteLog(instance.instance_name)"> 
									<span class="glyphicon glyphicon-trash text-danger" aria-hidden="true" title="Excluir Log"></span>
								</a>
							</td>
						</tr>
						<tr ng-repeat="instance in instances.expired | orderBy: 'name' | orderBy: orderInstance:reverseInstance">
							<!-- Name -->
							<td>
								<abbr title="Expirada" class="circle circle-danger"></abbr> 
								<strong>{{instance.instance_name }}</strong>
							</td>
							<!-- Validity -->
							<td class="text-center">{{ instance.endDate }}</td>
							<!-- Status/Action -->
							<td class="text-center">
								<a href="#" ng-disabled="true"> 
									<span class="glyphicon glyphicon-play-circle licence_expired" aria-hidden="true" title="Clique para iniciar o serviço"></span>
								</a>
							</td>
							<!-- Config -->
							<td class="text-center">
								<a href="#" ng-disabled="true"> 
									<span class="glyphicon glyphicon-cog licence_expired" aria-hidden="true" title="Configurar Instância"></span>
								</a>
							</td>
							<!-- Device limit -->
							<td class="text-center">{{ instance.devicelimit }}</td>
							<!-- Device list -->
							<td class="text-center">--</td>
							<!-- Logs -->
							<td>
								<a href="#" class="btnLog" ng-click="showLog(instance.instance_name)"> 
									<span class="glyphicon glyphicon-search text-success" aria-hidden="true" title="Visualizar Log"></span>
								</a> 
								<a href="#" class="btnLog" ng-click="sendLog(instance.instance_name)"> 
									<span class="glyphicon glyphicon-send text-primary" aria-hidden="true" title="Enviar Log"></span>
								</a> 
								<a href="#" class="btnLog" ng-click="deleteLog(instance.instance_name)"> 
									<span class="glyphicon glyphicon-trash text-danger" aria-hidden="true" title="Excluir Log"></span>
								</a>
							</td> 

						</tr>
						<tr ng-if="visInstances && isEmpty(instances)" style="background-color: #bdbdbd" ng-cloak>
							<td colspan="8" class="text-center">
								<h4 ng-if="hasConnection == 'true'">
									<strong>Nenhuma instância instalada até o momento!</strong>
								</h4>
								<h4 ng-if="hasConnection == 'false'">
									<strong>Não foi possível comunicar com a base de dados.</strong>
								</h4>
							</td>
						</tr>
						<tr ng-if="instances.error" style="background-color: #bdbdbd">
							<td colspan="8" class="text-center">
								<label><strong>{{instances.error}}</strong></label>
							</td>
						</tr>
					</tbody>
					<tfoot ng-if="visInstances && !instances.error">
						<tr class="bg-footer">
							<td colspan="7" class="text-center">
							</td>
						</tr>
					</tfoot>
				</table>
			</div>
		</div>
		<!-- End  Row -->
		<div class="row" id="third-block">
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12  table-responsive">
				<table id="tbl_modules" class="table table-hover table-striped table-condensed table-bordered">
					<thead>
						<tr class="bg-title">
							<th colspan="10" class="text-center">MÓDULOS</th>
						</tr>
						<tr class="bg-subtitle">
							<th ng-click="orderByModule('type')" class="pointer col-lg-4">
								Tipo de Instância <span class="sortorder" ng-if="orderModule === 'type'" ng-class="{reverse: reverseModule}"></span>
							</th>
							<th ng-click="orderByModule('name')" class="pointer col-lg-4">
								Nome <span class="sortorder" ng-if="orderModule === 'name'" ng-class="{reverse: reverseModule}"></span>
							</th>
							<th class="col-lg-2 text-center">Status</th>
							<th class="col-lg-2 text-center">Instalar</th>
						</tr>
					</thead>
					<tbody>
						<tr ng-repeat="module in modules | orderBy: 'type'  | orderBy: orderModule:reverseModule" wait-modules>
							<td><strong>{{ module.type }}</strong></td>
							<td><strong>{{ module.name }}</strong></td>
							<td class="text-center">
								<span ng-if="module.available" class="glyphicon glyphicon-ok-circle text-success" aria-hidden="true"></span> 
								<span ng-if="!module.available" class="glyphicon glyphicon-remove-circle text-danger" aria-hidden="true"></span>
							</td>
							<td class="text-center">
								<button ng-if="module.status == 'available' && hasConnection == 'true'" ng-click="installModule(module.type ,module.name)" class="btn btn-xs btn-default">Instalar</button>
								<label ng-if="module.status == 'installed'" >Instalado</label>
								<label ng-if="hasConnection == 'false' && module.status == 'available'" >Necessita conexão</label>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<!-- End  Row -->
	</div>
	<!-- End  Container -->
	<footer class="footer">
      <div class="container text-center">
        <span class="text-muted">Copyright © 2010-2022 - Digicon S.A. - Todos os direitos reservados</span>
      </div>
    </footer>
</body>
</html>