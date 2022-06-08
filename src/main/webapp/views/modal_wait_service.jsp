    <div class="modal fade">
        <div class="modal-dialog modal-install">
            <div class="modal-content">
                <div class="modal-body">
                    <button type="button" class="close" ng-click="close('Cancel')" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
					<h4 class="text-center"> {{text}} </h4>
					<div ng-if="serviceMsg === '' ">
						<img class="img-responsive img-center load-image"  src="/dsm/static/img/loading.gif" />
					</div>
					<div ng-bind-html="trustedHtml"></div>
                </div>
            </div>
        </div>
    </div>
