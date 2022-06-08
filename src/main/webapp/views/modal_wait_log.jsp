    <div class="modal fade" id="wait_log.jsp">
        <div class="modal-dialog modal-log">
            <div class="modal-content">
                <div class="modal-body">
                    <button type="button" class="close" ng-click="close('Cancel')" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
					<h4 class="text-center">{{ title }} {{ path }}</h4>
					<div ng-if="logMsg === '' ">
						<img class="img-responsive img-center load-image"  src="/dsm/static/img/loading.gif" />
					</div>
					<div ng-bind-html="trustedHtml"></div>
                </div>
            </div>
        </div>
    </div>
