    <div id="modal_send_update_key" class="modal fade">
        <div class="modal-dialog modal-90">
           	<div class="modal-content">
               	<div class="modal-header">
                   	<button type="button" class="close" ng-click="close('modal_send_update_key')" data-dismiss="modal" aria-hidden="true">
	                       &times;
                   	</button>
					<h3 class="text-center">{{ title }}</h3>
                </div>
               	<div class="modal-body">
					<div class="row table-responsive" ng-if="pemFmt === '' && !isEmpty(instances)">
						<div class="col-xs-12">
							<table class="table table-condensed table-bordered table-bordered2">
								<thead>
									<tr class="bg-title">
										<th colspan="6" class="text-center">Instâncias</th>
									</tr>
									<tr class="bg-subtitle">
										<th rowspan="2" class="text-center" style="vertical-align: middle">Produto</th>
										<th rowspan="2" class="text-center" style="vertical-align: middle">ID</th>
										<th colspan="2" class="text-center">Validade</th>
										<th colspan="2" class="text-center">Limite de dispositivos</th>
									</tr>
									<tr class="bg-subtitle">
										<th class="text-center">Atual</th>
										<th class="text-center">Nova</th>
										<th class="text-center">Atual</th>
										<th class="text-center">Novo</th>
									</tr>
								</thead>
								<tbody>
									<tr ng-repeat="instance in instances" ng-if="instance.endDateExtended || instance.deviceLimitExtended" ng-class="{'bg-registered' : instance.registered, 'bg-requested' : !instance.registered}">
										<td><strong> {{ instance.product }} </strong> </td>
										<td class="text-center" ><strong> {{ instance.id }} </strong> </td>
										<td class="text-center" ><strong> {{ (instance.registered) ? (instance.originalEndDate | brdate ) : '--' }} </strong></td>
										<td class="text-center" > <strong> {{ (instance.endDateExtended) ? (instance.endDate | brdate ) : '--' }} </strong> </td>
										<td class="text-center" ><strong>{{ (instance.registered) ? instance.originalDeviceLimit : '--' }}</strong></td>
										<td class="text-center" ><strong> {{ (instance.deviceLimitExtended) ? instance.deviceLimit : '--' }} </strong></td>
									</tr>
								</tbody>
								<tfoot>
									<tr class="bg-footer">
										<td colspan="6" class="text-center">
											<abbr class="square square-registered"></abbr>Instâncias registradas
											<abbr class="square square-requested"></abbr>Intâncias Solicitadas
										</td>
									</tr>
								</tfoot>
							</table>
						</div>
					</div>
					<div class="row table-responsive" ng-if="pemFmt === '' && !isEmpty(modules)">
						<div class="col-xs-12">
							<table class="table table-condensed table-bordered table-bordered2">
								<thead>
									<tr class="bg-title">
										<th colspan="5" class="text-center">Módulos</th>
									</tr>
									<tr class="bg-subtitle">
										<th class="text-center" style="vertical-align: middle">Produto</th>
										<th class="text-center">Nome</th>
									</tr>
								</thead>
								<tbody>
									<tr ng-repeat="module in modules">
										<td class="text-center" ><strong> {{ module.type | toUpper }} </strong> </td>
										<td class="text-center" ><strong> {{ module.name }} </strong></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12" ng-if="pemFmt !== ''">
							<p class="text-center"><strong>{{ msg }}</strong></p>
						</div>
						<div class="col-xs-12">
							<div ng-bind-html="trustedHtml"></div>
						</div>
					</div>
				</div>
   	            <div ng-if="!isSuccess" class="modal-footer" ng-if="pemFmt === ''">
                	<button type="button" class="btn btn-primary" ng-if="!isEmpty(instances) || !isEmpty(modules)" ng-click="getUpdateKeyPemFmt()">
                       Enviar
                	</button>
                    <button type="button" ng-click="close('modal_send_update_key')" data-dismiss="modal" class="btn btn-danger" >
                       Cancelar
                   	</button>
               	</div>
           	</div>
       	</div>
   	</div>