<form name="myForm" novalidate ng-submit="confirmRequest()">
	<div id="modal_request_instance" class="modal fade">
		<div class="modal-dialog modal-90">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" ng-click="close('Cancel')" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h3 class="modal-title text-center">{{ title }}</h3>
                </div>

                <div class="modal-body table-responsive">
                	<div ng-if="msg !== ''" ng-bind-html="trustedHtml"></div>
                    <table ng-if="msg === ''" class="table  table-bordered table-condensed">
                        <thead ng-if="!isEmpty(instancesByType) && !isEmpty(arrAddRows.instances)">
						<tr>
							<th colspan="4" class="text-center bg-title">
								<label>Instâncias</label>
								<button type="button" ng-click="addRowInstances(rows)" title="Solicitar novas instâncias" class="btn pull-right btn-xs btn-success">
									Adicionar
								</button>
								<input type="number"
									name="qtdToAdd"
									ng-model="qtdRows"
									class="text-right pull-right input-number-add" 
									min="0"/>
							</th>
						</tr>
						<tr>
							<td class="col-md-4">
								<label>Nome</label>
							</td>
							<td class="text-center">
								<label>Validade</label>
								<button type="button" class="btn btn-xs btn-default pull-right" ng-click="releaseValidity()">
									Habilitar Edição
								</button>
							</td>
							<td class="text-center">
								<label>Limite de Dispositivos</label>
								<button type="button" class="btn btn-xs btn-default pull-right" ng-click="releaseLimit()">
									Habilitar Edição
								</button>
							</td>
							<td class="col-md-2">
								<label>Ação</label>
							</td>
						</tr>
						</thead>
						<tbody>
							<tr ng-repeat="instance in licenseInstancesByType" ng-class="{'bg-registered' : instance.registered, 'border-green' : !instance.registered}"  ng-if="!isEmpty(instancesByType)">
								<td><label class="espaceLeft">{{ instance.product }}-{{ addZeroIfLess10(instance.id) }}</label></td>
								<td class="text-right">
									<!-- <input type="date" ng-model="instance.endDate" ng-blur="changeEndDate(instance)" min="{{ instance.originalEndDate }}" max="{{ maxDate(instance.originalEndDate) }}" style="height: 25px; width: 128px" class="text-right" ng-disabled="disableValidity" ng-class="{'border-green' : instance.endDateExtended && instance.registered}" date-format/> -->
									<input  ng-if="!isIE()" type="date"
										name="endDate{{$index}}"
										ng-model="instance.endDate" 
										ng-blur="changeEndDate(instance)" 
										min="{{ instance.originalEndDate }}" 
										max="{{ maxDate(instance.originalEndDate) }}" 
										style="height: 25px; width: 128px" 
										class="text-right endDate" 
										ng-disabled="disableValidity" 
										ng-class="{'border-green' : instance.endDateExtended && instance.registered}" 
										date-format="text" />

									<input  ng-if="isIE()" type="text"
										name="endDate{{$index}}"
										ng-model="instance.endDate" 
										ng-blur="changeEndDate(instance)" 
										min="{{ instance.originalEndDate }}" 
										max="{{ maxDate(instance.originalEndDate) }}" 
										style="height: 25px; width: 128px" 
										class="text-right endDate" 
										ng-disabled="disableValidity" 
										ng-class="{'border-green' : instance.endDateExtended && instance.registered}" 
										date-format="text" /> 
								</td>
								<td class="text-right">
									<!-- <input type="number" ng-model="instance.deviceLimit" ng-blur="changeDeviceLimit(instance)" min="{{ ::getMinValue(instance) }}" class="text-right iptWidtNumber" ng-disabled="disableLimit" ng-class="{'border-green' : instance.deviceLimitExtended && instance.registered}" /> -->
									<input type="number" 
										name="deviceLimit{{$index}}"
										ng-model="instance.deviceLimit" 
										ng-blur="changeDeviceLimit(instance)" 
										min="{{ ::getMinValue(instance) }}" 
										class="text-right iptWidtNumber" 
										ng-disabled="disableLimit" 
										ng-class="{'border-green' : instance.deviceLimitExtended && instance.registered}" />
								</td>
								<td class="text-center">
									<button type="button" ng-if="(instance.endDateExtended || instance.deviceLimitExtended)" ng-click="cancelRequest($index)" class="btn btn-xs btn-danger">Cancelar</button>
									<strong>{{ (!instance.endDateExtended && !instance.deviceLimitExtended) ? '-' : '' }}</strong>
								</td>
							</tr>
							<tr ng-repeat="addRow in arrAddRows" class="border-green">
								<td><strong class="espaceLeft">{{ addRow.product }}-{{addZeroIfLess10(addRow.id)}}</strong></td>
								<td class="text-right">
									<input ng-if="!isIE()" type="date"
										name="addEndDate{{$index}}" 
										ng-model="addRow.endDate" 
										ng-disabled="disableValidity" 
										ng-blur="changeEndDate(addRow)" 
										style="height: 25px; width: 128px" 
										class="text-right addEndDate" 
										date-format="text"/>

									<input ng-if="isIE()" type="text"
										name="addEndDate{{$index}}" 
										ng-model="addRow.endDate" 
										ng-disabled="disableValidity" 
										ng-blur="changeEndDate(addRow)" 
										style="height: 25px; width: 128px" 
										class="text-right addEndDate" 
										date-format="text"/>
								</td>
								<td class="text-right">
									<input 
										type="number"
										name="addDeviceLimit{{$index}}" 
										ng-model="addRow.deviceLimit" 
										ng-disabled="disableLimit"  
										ng-blur="changeDeviceLimit(addRow)" 
										min="1" 
										class="text-right iptWidtNumber"/>
								</td>
								<td class="text-center">
									<button type="button" class="btn btn-xs btn-danger" ng-click="removeRequest($index)">Cancelar</button>
								</td>
							</tr>
						</tbody>
						<tfoot>
							<tr class="bg-footer">
								<td colspan="4" class="text-center">
									<abbr class="square square-registered"></abbr>Instâncias registradas
									<abbr class="square square-success"></abbr>Itens solicitados
								</td>
							</tr>
						</tfoot>
					</table>
				</div>
                <div class="modal-footer" ng-if="msg === ''" >
                     <button type="submit" ng-disabled="myForm.$invalid || !myForm.$dirty"class="btn btn-primary">
                          Confirmar Solicitação
                      </button>
                      <button type="button" ng-click="close('No')" class="btn btn-danger">
                          Cancelar
                      </button>
                </div>
            </div>
        </div>
    </div>
    
   	<div id="value_error" class="modal fade" data-backdrop="static" data-keyboard="false" style="display: none;">
		<div class="modal-dialog modal-60">
			<div class="modal-content">
				<div class="modal-header">
					<h3 class="modal-title text-center">{{ title }}</h3>				
				</div>
				<div class="modal-body" style="padding-right: 28px">
					<pre class="text-center bg-warning"> {{ msgError }} </pre>
				</div>
				<div class="modal-footer" ng-if="!isWait">
					<button type="button" class="btn btn-primary" data-dismiss="modal" > OK </button>
				</div>
			</div>
		</div>
	</div>
</form>
       
