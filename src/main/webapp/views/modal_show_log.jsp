    <div class="modal fade">
        <div class="modal-dialog modal-log">
            <div class="modal-content">
                <div class="modal-body">
                    <button type="button" class="close" ng-click="close('Cancel')" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
					<h4 class="text-center">Log {{ path }}</h4>					
					<div ng-if="log === '' ">
						<img class="img-responsive img-center load-image"  src="/dsm/static/img/loading.gif" />
					</div>
					
					<pre ng-bind="log" class="pre_log"></pre>
                </div>
            </div>
        </div>
    </div>
