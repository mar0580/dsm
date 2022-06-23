===================================
dig-dsm (Digicon Soluction Manager)
===================================
  Comments:
    a) Gerenciamento das soluções da Digicon: (Licence Manager)

Diginet 4.x.x
   (New) Uses: angular.min.js & \angular-smart-table	

==============================================
HOW TO PREPARE ECLIPSE DEVELOPMENT ENVIRONMENT
==============================================

Prerequisites:
1) Eclipse Java EE IDE for Web Developers. Version: Kepler Release build id:20130614-0229 or newest
2) Diginet2.7.0 or Newert Installed because it need MySql5.1 Installed, and diginet.db configured
3) Checked out project: dig-dsm from ICTS SVN at icts/Digicom/Diginet4.6.0/dig-dsm/trunk 
4) Checked out project: diginet-installer from ICTS SVN at icts/Digicom/Diginet4.6.0/diginet-installer/trunk
5) **NEW** angularJS Lib downloaded <wks>\dig-dsm\src\main\webapp\js <Folder for Angular Libs > 
6) Understanding file <wks>\man-diginet\icts-projeto\arquitetura\DSM_Tables_and_Services.txt

===================================
HOW TO BUILD ON ECLIPSE ENVIRONMENT
===================================
1) Right Click in Project > Maven > Update Project
2) Right Click in *.jar from src/main/webapp/WEB-INF/lib > Build Path > Add to Build Path
3) Right Click in Project > Properties > Deployment Assembly > Add > Java Built Path Entries > Maven Dependencies > Apply
4) Project > clean               select[x]dig-dsm
5) Project > Build Project       select[x]dig-dsm
==================
RUNNING ON ECLIPSE
==================
1) Right-Click on dig-dsm project > Run As > Run On Server
2) At Menu select: apache > tomcat v7.0 Server
	Server´s Host Name:localhost
	Server Name: Tomcat v7.0 Server at localhost
	Server runtime environment: Apache Tomcat v7.0
3) Next > dig-dsm finish.
   
===================================
HOW TO GENERATE (WAR) FILE
===================================
1) browse: D:\wks_diginet\dig-dsm\src\main\webapp\views\index.jsp
         edit: <label class="diginet-version">4.6.1.0</label>
1) Right-Click on dig-dsm project > Export > WAR File
2) Browse: <wks>\dig-dsm\dist\dsm.war  > Salvar   [X]overighting existing file

========================================
UPDATING INSTALL DIRECTORY BY COPY (WAR) 
========================================
1) Copy: dsm.war to <wks>\diginet-installer\instalador\Tomcat 7.0 dsm\webapps
