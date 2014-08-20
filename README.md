Version Flyway Sql scripts
==========================

This project shows how flyway script versioning can be done effectively in a distributed development environment. If you are in a project that has multiple teams committing flyway scripts using the default way of using version numbers, there is a high chance that multiple people used up the same version number, resulting in a conflict.

This problem can be averted by versioning the script with a timestamp upto millis. Conflicts can be significantly reduced.

Software Prerequisites
----------------------
1. JDK 6+
2. Maven 3+
3. Git

Versioning scripts - How to
--------------------------
1. Create a flyway maven project just like this one.
2. If you have multiple environments to run flyway on, create a folder structure as in src/main/resources
3. Replace the Jdbc driver in the pom.xml with the one you need. It can be mysql, Oracle etc.
4. Copy flyway properties as in this pom.xml
5. Create sql scripts in each environment.    
	* 0sql: This is to hold sql's applicable for all environment   
	* Put environment specific sql's in each of the other folders like local or dev or qa.      
	* Do not version the scripts. Just put a meaningful name like 1\_config\_table\_create.sql      
6. Run "mvn package"     
	*  A junit will run to ensure that all scripts are versioned.    
	*  Maven error console will list all unversioned scripts.        
7. Run "mvn package -DskipTests=true"      
	* This will find unversioned sql scripts and version them with a "V" followed by timestamp      
	* 1\_config\_table\_create.sql  --> V20140820132356560\_\_1\_config\_table\_create.sql 

_Note_: org.anair.flyway.VersionFlywayScript does the versioning. Checkout the class and comments.

>  * If you are in a distributed development environment, ask your team members to follow the above instructions to 
avoid version conflicts. This process will reduce conflicts, but is still not guaranteed to fix it 100%.     
>  * If you are running "flyway:migrate" through Chef/Ansible/Jenkins, make sure "mvn compile test flyway:migrate" is executed. The test will ensure you have versioned scripts.         
>  * Running flyway scripts in "outOfOrder" works well for distributed teams.         

Running Flyway scripts
----------------------
[Reference](http://flywaydb.org/documentation/maven/)


	