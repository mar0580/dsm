    <div id="main-modal" class="modal fade">
        <div class="modal-dialog modal-90">
           	<div class="modal-content">
               	<div class="modal-header">
                   	<button type="button" class="close" ng-click="close('Cancel')" data-dismiss="modal" aria-hidden="true">
	                       &times;
                   	</button>
                </div>
               	<div class="modal-body" style="padding-right: 28px;">
					<div class="row">
						<div class="col-xs12 col-sm-6 col-md-4 col-lg-4 block-buttons">
							<button type="button" class="btn btn-primary btn-sm" data-toggle="toggle" data-target="#block1" change-grid>
								Dados gerais da instância
							</button>							
							<button type="button" class="btn btn-primary btn-sm" data-toggle="toggle" data-target="#block2" change-grid>
								Comunicação com dispositivos
							</button>
							<button type="button" class="btn btn-primary btn-sm" data-toggle="toggle" data-target="#block3" ng-if="instance.instance_type != instanceEnum.DIGINET"  change-grid>
								Comunicação com Sistemas Externos
							</button>
							<button type="button" class="btn btn-primary btn-sm" data-toggle="toggle" data-target="#block4" change-grid>
								Diretórios para integração com arquivos
							</button>
							<button type="button" class="btn btn-primary btn-sm" data-toggle="toggle" data-target="#block5" change-grid>
								Diretórios para exportação de arquivos
							</button>
							<button type="button" class="btn btn-primary btn-sm" data-toggle="toggle" data-target="#block6" change-grid>
								Processamento de pendências
							</button>
							<button type="button" class="btn btn-primary btn-sm" data-toggle="toggle" data-target="#block7" change-grid>
								Processamento de eventos
							</button>							
						</div>
						<div class="col-xs-12 col-sm-6 col-md-8 col-lg-8" id="block-inputs">
							<div id="block1" class="in">
								<div class="form-group">
									<label for="strInstanceType">Tipo</label>
									<input class="form-control" type="text" id="strInstanceType" readonly="true" ng-model="instance.strInstanceType"/>
								</div>
								<div class="form-group">
									<label for="instance_name">Nome</label>
									<input class="form-control" type="text" id="instance_name" readonly="true" ng-model="instance.instance_name"/>
								</div>
								<div class="form-group">
									<label for="strInstanceStatus">Status</label>
									<input class="form-control" type="text" id="strInstanceStatus" readonly="true" ng-model="instance.strInstanceStatus"/>
								</div>
								<div class="form-group">
									<label for="jvm_initmemory">Memória Inicial (JVM) (*)</label>
									<input class="form-control" type="number" id="jvm_initmemory" ng-model="instance.jvm_initmemory"/>
								</div>
								<div class="form-group">
									<label for="jvm_maxmemory">Memória Limite (JVM) (*)</label>
									<input class="form-control" type="number" id="jvm_maxmemory" ng-model="instance.jvm_maxmemory"/>
								</div>
								<div class="form-group">
                    				<label for="log_file_maxsize">Tamanho máximo de cada arquivo de log em MB</label>
                    				<input class="form-control" type="number" id="log_file_maxsize" ng-model="instance.log_file_maxsize"/>
                				</div>
                				<div class="form-group">
                    				<label for="log_file_maxfiles">Quantidade máxima de arquivos armazenados de forma cíclica</label>
                    				<input class="form-control" type="number" id="log_file_maxfiles" ng-model="instance.log_file_maxfiles"/>
                				</div>
								<div class="form-group">
									<label for="log_file_level">Tipo de Log (*)</label>
									<select class="form-control" ng-options="item as item.label for item in logLevels track by item.id" ng-model="selectedLog"></select>
								</div>
								<div class="form-group">
									<label for="path_export_support_logs">Diretório para salvar arquivos de logs a enviar ao suporte (*) </label>
                    				<input class="form-control" type="text" id="path_export_support_logs" ng-model="instance.path_export_support_logs"/>
                				</div>                				
                				<div class="form-group">
                    				<label for="summer_time_start">Inicio horário de verão</label>
                    				<input ng-if="isIE()" class="form-control" type="text" id="summer_time_start" ng-model="instance.summer_time_start" date-format/>
                    				<input ng-if="!isIE()" class="form-control" type="date" id="summer_time_start" ng-model="instance.summer_time_start" date-format/>
                				</div>
                				<div class="form-group">
                    				<label for="summer_time_end">Fim horário de verão</label>
                    				<input ng-if="isIE()" class="form-control" type="text" id="summer_time_end" ng-model="instance.summer_time_end" date-format/>
                    				<input ng-if="!isIE()" class="form-control" type="date" id="summer_time_end" ng-model="instance.summer_time_end" date-format/>
                				</div>
            				</div>            				
            				<div id="block2" class="">
                				<div class="form-group">
                    				<label for="server_ip">IP (*) </label>
                    				<input class="form-control" type="text" id="server_ip" ng-model="instance.server_ip"/>
                				</div>
                				<div class="form-group">
                    				<label for="server_port">Porta (*) </label>
                    				<input class="form-control" type="number" id="server_port" ng-model="instance.server_port"/>
                				</div>
                				<div class="form-group">
                    				<label for="server_keep_alive_interval">Tempo para Keep Alive (*)</label>
                    				<input class="form-control" type="number" id="server_keep_alive_interval" ng-model="instance.server_keep_alive_interval"/>
                				</div>
            				</div>
            				<div id="block3" ng-if="instance.instance_type != instanceEnum.DIGINET" class="">
                				<div class="form-group">
                    				<label for="remote_server_comm_type">Tipo(*) </label>
                    				<select class="form-control" readonly="true" id="remote_server_comm_type">
                        				<option>TCP</option>
                        				<option>Rest</option>
                        				<option>Soap</option>
                    				</select>
                				</div>
                				<div ng-if="instance.instance_type != instanceEnum.DIGINET" class="form-group">
                    				<label  for="remote_server_url">URL (*)</label>
                    				<input class="form-control" type="text" id="remote_server_url" ng-model="instance.remote_server_url"/>
                				</div>
                				<div ng-if="instance.instance_type != instanceEnum.DIGINET" class="form-group">
                    				<label  for="remote_server_ip">IP</label>
                    				<input class="form-control" type="text" id="remote_server_ip" ng-model="instance.remote_server_ip"/>
                				</div>
                				<div class="form-group">
                    				<label for="remote_server_port">Porta</label>
                    				<input class="form-control" type="number" id="remote_server_port" ng-model="instance.remote_server_port"/>
                				</div>
                				<div ng-if="instance.instance_type != instanceEnum.SAM" class="form-group">
                    				<label for="remote_server_token">Token</label>
                    				<input class="form-control" type="text" id="remote_server_token" ng-model="instance.remote_server_token"/>
                				</div>
                				<div class="form-group">
                    				<label for="remote_server_user">Usuário</label>
                    				<input class="form-control" type="text" id="remote_server_user" ng-model="instance.remote_server_user"/>
                				</div>
                				<div class="form-group">
                    				<label for="remote_server_user_passwd">Senha</label>
                    				<input class="form-control" type="text" id="remote_server_user_passwd" ng-model="instance.remote_server_user_passwd"/>
                				</div>
                				<div ng-if="instance.instance_type != instanceEnum.GAS" class="form-group">
                    				<label for="remote_server_certificate">Certificado Digital</label>
                    				<input class="form-control" type="text" id="remote_server_certificate" ng-model="instance.remote_server_certificate"/>
                				</div>
                				<div class="form-group">
                    				<label for="remote_server_id">Identificador do servidor remoto</label>
                    				<input class="form-control" type="number" id="remote_server_id" ng-model="instance.remote_server_id"/>
                				</div>
                				<div class="form-group">
                    				<label for="remote_server_keep_alive_interval">Tempo para Keep Alive</label>
                    				<input class="form-control" type="number" id="remote_server_keep_alive_interval" ng-model="instance.remote_server_keep_alive_interval"/>
                				</div>
                				<div class="form-group">
                    				<label for="remote_server_events_interval">Tempo para envio de eventos</label>
                    				<input class="form-control" type="number" id="remote_server_events_interval" ng-model="instance.remote_server_events_interval"/>
                				</div>
            				</div>
            				<div id="block4" class="">
                				<div class="form-group">
                    				<label for="path_import_person">Importação de pessoas (*) </label>
                    				<input class="form-control" type="text" id="path_import_person" ng-model="instance.path_import_person"/>
                				</div>
                				<div class="form-group">
                    				<label for="path_import_company">Importação da empresa (*) </label>
                    				<input class="form-control" type="text" id="path_import_company" ng-model="instance.path_import_company"/>
                				</div>
                				<div class="form-group">
                    				<label for="path_import_pendrive_operations">Importação de arquivos das operaçõs com pendrive (*) </label>
                    				<input class="form-control" type="text" id="path_import_pendrive_operations" ng-model="instance.path_import_pendrive_operations"/>
                				</div>
            				</div>
            				<div id="block5" class="">                				
                				<div class="form-group">
                    				<label for="path_export_files">Armazenamento de arquivos exportados </label>
                    				<input class="form-control" type="text" id="path_export_files" ng-model="instance.path_export_files"/>
                				</div>
            				</div>
            				<div id="block6" class="">
                				<div class="form-group">
                    				<label for="concurrent_proc_task">Quantidade de filas simultâneas para processamento (*) </label>
                    				<input class="form-control" type="number" id="concurrent_proc_task" ng-model="instance.concurrent_proc_task"/>
                				</div>
                				<div class="form-group">
                    				<label for="task_saving_days">Dias para arquivamento das pendências processadas (*) </label>
                    				<input class="form-control" type="number" id="task_saving_days" ng-model="instance.task_saving_days"/>
                				</div>
            				</div>
            				<div id="block7" class="">
                				<div class="form-group">
                    				<label for="event_collect_interval">Tempo para coleta de eventos (*)</label>
                    				<input class="form-control" type="number" id="event_collect_interval" ng-model="instance.event_collect_interval"/>
                				</div>
                				<div class="form-group">
                    				<label for="path_import_events">Diretório para importação temporária de eventos dos dispositivos (*) </label>
                    				<input class="form-control" type="text" id="path_import_events" ng-model="instance.path_import_events"/>
                				</div>
                				<div class="form-group">
                    				<label for="concurrent_time_event_files">Tempo em segundos para processamento de arquivos de eventos</label>
                    				<input class="form-control" type="number" id="concurrent_time_event_files" ng-model="instance.concurrent_time_event_files"/>
                				</div>
                				<div class="form-group">
                    				<label for="concurrent_proc_files">Quantidade de processos para tratamento de arquivos de eventos (*)</label>
                    				<input class="form-control" type="number" id="concurrent_proc_files" ng-model="instance.concurrent_proc_files"/>
                				</div>
                				<div class="form-group">
                    				<label for="concurrent_numb_event_files">Quantidade de arquivos simultâneos processados (*)</label>
                    				<input class="form-control" type="number" id="concurrent_numb_event_files" ng-model="instance.concurrent_numb_event_files"/>
                				</div>
                				<div class="form-group">
                    				<label for="saving_event_days">Dias para arquivamentos de logs de eventos processados (*)</label>
                    				<input class="form-control" type="number" id="saving_event_days" ng-model="instance.saving_event_days"/>
                				</div>
            				</div>
        				</div>
    				</div>
				</div>			
 	            <div class="modal-footer">
                	<button class="btn btn-primary"  data-toggle="modal" href="#confirm-conf">
						Alterar
                	</button>
                    <button class="btn btn-danger" data-toggle="modal" href="#cancel-conf">
						Cancelar
                   	</button>
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
               		<div ng-bind-html="msgConfirm"></div>
				</div>			
 	            <div class="modal-footer" ng-if="!isWait">
                	<button ng-click="updateInstance()" class="btn btn-primary"  > SIM </button>
                    <button class="btn btn-danger" data-dismiss="modal" > NÂO </button>
               	</div>
           	</div>
       	</div>
   	</div>   	
    <div id="cancel-conf" class="modal fade" data-backdrop="static" data-keyboard="false" style="display: none;">
        <div class="modal-dialog modal-60">
           	<div class="modal-content">
               	<div class="modal-header">
                </div>
               	<div class="modal-body" style="padding-right: 28px;">
					<pre class="text-center text-warning">As alterações serão perdidas, confirma esta operação?</pre>               		
				</div>			
 	            <div class="modal-footer">
                	<button ng-click="close('No')" class="btn btn-primary" > SIM </button>
                    <button class="btn btn-danger" data-dismiss="modal" > NÂO </button>
               	</div>
           	</div>
       	</div>
   	</div>   	