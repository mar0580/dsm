<div class="modal fade">
	<div class="modal-dialog modal-login">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" ng-click="close('Cancel')" data-dismiss="modal"
					aria-hidden="true"
				>&times;</button>
				<h3 class="text-center">Alterar Login</h3>
			</div>
			<form name="simpleform" ng-submit="updateLogin()">
				<div class="modal-body">
					<div class="row">
						<div ng-bind-html="trustedHtml"></div>
						<div class="col-lg-12">
							<div class="form-group">
								<label for="currentPass">Senha atual (*) </label> <input class="form-control" type="password" ng-model="currentPassword" required id="currentPass" />
							</div>
							<div class="form-group">
								<label for="newPass">Nova senha (*) </label> <input class="form-control" type="password"
									ng-model="newPassword" required id="newPass"
								/>
							</div>
							<div class="form-group">
								<label for="confNewPass">Repetir nova senha (*) </label> <input class="form-control"
									type="password" ng-model="confPassword" required id="confNewPass"
								/>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-primary">Confirmar</button>
					<button type="button" ng-click="close('No')" data-dismiss="modal" class="btn btn-danger">
						Cancelar</button>
				</div>
			</form>
		</div>
	</div>
</div>
