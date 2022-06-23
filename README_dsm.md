# DSM (Digicon Solution Manager)

Gerenciamento das soluções Digicon: (Licence Manager).
Serviço em Java e AngularJS para controlar e monitorar as instâncias de acordo com as licenças.


## Diretórios

- dig-dsm
- Instalador


## Montar Ambiente

### Instalação Diginet

- Instalar versão mais recente do Diginet com opções padrões

### Instalação Eclipse IDE for Java EE Developers (oxygen)

1. Baixar: <http://www.eclipse.org/downloads/download.php?file=/technology/epp/downloads/release/oxygen/3a/eclipse-jee-oxygen-3a-win32-x86_64.zip>
1. Descompactá-lo
1. Executar eclipse.exe criando novo Workspace em um diretório vazio (Não reutilizar outros workspaces)
1. `Window -> Preferences`
1. `Java -> Installed JREs`
1. Caso exista JREs instaladas na lista, selecionar a default e clicar em Edit...
1. Caso NÃO exista JREs instaladas na lista, clicar em Add, selecionar Standard VM, clicar em Next
1. JRE home: `<caminho>\Instalador\Server\java8`
1. JRE name: `jre1.8.0_121`
1. Clicar em Finish
1. Clicar em Apply
1. Ainda em Preferences Acessar:
1. `Server -> Runtime Enviroments`
1. Clicar em Add
1. `Selecionar Apache -> Apache Tomcat v7.0`
1. Marcar: Create a new local server
1. Clicar em Next
1. Alterar "Tomcat Installation directory": `<caminho>\Instalador\Server\Tomcat 7.0 dsm`
1. Clicar em Finish
1. Clicar em Apply and Close
1. Clicar na guia Servers
1. Duplo clique em "Tomcat v7.0 Server at localhost"
1. Clicar em `Open lauch configurations -> Arguments`
1. Adicionar em VM arguments: ` -Djava.library.path="C:\DigiconApp"`
1. Clicar em Apply e depois OK
1. Expandir Timeouts
1. Start (in secounds): 240
1. `File -> Save (Ctrl + S)`
1. `File -> Import -> General -> Existing Projects into Workspace`
1. Clicar em Next
1. Select root directory: `<caminho>\dig-dsm`
1. Em projects marcar somente: `dsm`
1. Clicar em Finish
1. Propriedades botão direito no `Projeto dsm -> Maven -> Update Project (Alt + F5)`
1. Selecionar o projeto e Clicar em OK

*Observação:* Os passos de Update do Maven podem falhar devido a problemas no download de algumas dependências, para resolver esse tipo de problema, remover o diretório da dependência problemática no repositório `<pasta do seu usuário>\.m2\repository\<caminho do diretório>\<diretório da dependência>` e refazer os passos de Update Maven Project.


## Desenvolvimento

- No Windows abrir services.msc
- Parar o serviço `TomcatDiginetdsm` **DIGINET - DSM**
- Executar `eclipse.exe` apontando Workspace criado
- `Project -> Clean`
- Marcar: `Clean all projects`
- Clicar em Clean
- Botão direito no `projeto -> Run As -> Run on Server (Alt + Shift + X, R)`
- Finish
- Acesso via endereço <http://localhost:8081/dsm/> pelo navegador do eclipse ou por outro navegador normal


## Gerar Pacote

- Executar `eclipse.exe` apontando Workspace criado
- `Project -> Clean`
- Marcar: `Clean all projects`
- Clicar em Clean
- Botão direito no `projeto -> Export -> WAR file`
  * Web project: `dsm`
  * Destination: `<caminho>\diginet-installer\instalador\Tomcat 7.0 dsm\webapps\dsm.war`
  * Marcar: `Overwrite existing file`
- Finish
