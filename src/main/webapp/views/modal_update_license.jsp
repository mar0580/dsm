    <div id="modal_update_license" class="modal fade">
        <div class="modal-dialog modal-login">
           	<div class="modal-content">
               	<div class="modal-header">
                   	<button type="button" class="close" ng-click="close('Cancel')" data-dismiss="modal" aria-hidden="true">
	                       &times;
                   	</button>
					<h3 class="text-center">{{ title }}</h3>
                </div>
               	<div class="modal-body">
					<div class="row">
						<div class="col-lg-12">
							<div ng-if="!isSuccess" class="form-group">
								<label for="newLicense">Selecione o arquivo (*) </label> 
								<!-- <input class="form-control" name="file" type="file" id="newLicense"/> -->
								<input ng-disabled="isWait" type="file" name="myFile" id="licence_file" class="filestyle" file-model="myFile">
							</div>
							<div ng-if="isWait">
								<img class="img-responsive img-center load-image"  src="/dsm/static/img/loading.gif" />
							</div>
							<div ng-bind-html="trustedHtml" ng-if="trustedHtml !== ''"></div>
						</div>
					</div>
				</div>
   	            <div ng-if="!isSuccess" class="modal-footer">
                	<button type="button" class="btn btn-primary" ng-click="updateLicense()">
                       Confirmar
                	</button>
                    <button type="button" ng-click="close('No')" data-dismiss="modal" class="btn btn-danger" >
                       Cancelar
                   	</button>
               	</div>
           	</div>
       	</div>
   	</div>
