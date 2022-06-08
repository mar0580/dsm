<form name="myForm" novalidate ng-submit="changePorts()">
	<div id="modal_change_port" class="modal fade">
		<div class="modal-dialog modal-60">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" ng-click="close('Cancel')"
						data-dismiss="modal" aria-hidden="true">&times;</button>
					<h3 class="text-center">Alterar Portas de Comunicação</h3>
				</div>
				<div class="modal-body">
					<div class="row">
						<div ng-bind-html="htmlModal1"></div>
						<div class="col-lg-6">
							<fieldset>
								<legend>Lista do Client:</legend>
								<div class="form-group" ng-repeat="port in ports.clientPorts track by $index">
									<label for="port">Porta {{ $index + 1 }}</label>
									<input class="form-control" name="port{{ $index + 1 }}" type="number" ng-model="ports.clientPorts[$index]" required id="port" />
								</div>
							</fieldset>
						</div>
						<div class="col-lg-6" ng-if="msg === ''">
							<fieldset>
								<legend>Servidor Diginet:</legend>
								<div class="form-group">
									<label for="port">Porta</label>
									<input class="form-control" name="diginetPort" type="number" ng-model="ports.diginetPort" required id="port" />
								</div>
							</fieldset>
						</div>
						
					</div>
				</div>
				<div class="modal-footer" ng-if="msg === ''">
					<button type="button" class="btn btn-primary" ng-disabled="myForm.$invalid || !myForm.$dirty" data-toggle="modal" href="#confirm-conf">Confirmar</button>
					<button type="button" ng-click="close('No')" data-dismiss="modal" class="btn btn-danger">Cancelar</button>
				</div>
			</div>
		</div>
	</div>
	<div id="confirm-conf" class="modal fade" data-backdrop="static" data-keyboard="false" style="display: none;">
		<div class="modal-dialog modal-60">
			<div class="modal-content">
				<div class="modal-header">
				</div>
				<div class="modal-body" style="padding-right: 28px;">
					<div ng-bind-html="htmlModal2"></div>
					<div ng-if="isWait">
						<img class="img-responsive img-center load-image"  src="/dsm/static/img/loading.gif" />
					</div>
					<pre ng-if="!isWait && msg === ''" class="text-center text-warning">Este procedimento requer reinicialização do DIGINET, deseja continuar?</pre>				
				</div>			
				<div class="modal-footer">
					<button type="submit" ng-if="!isWait && msg === ''" class="btn btn-primary"  > SIM </button>
					<button type="button" ng-if="!isWait && msg === ''" type="button" ng-click="close('No')" class="btn btn-danger">NÃO</button>
					<button type="button" ng-if="msg !== ''" type="button" ng-click="close('Yes')" class="btn btn-primary">OK</button>
				</div>
			</div>
		</div>
	</div>
</form>