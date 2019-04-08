#!/bin/sh

ssh chenry@alihadoop01 "rm -rf /home/chenry/software/tomcat/webapps/graduation_project ; rm -f /home/chenry/software/tomcat/webapps/graduation_project.war"

/home/chenry/software/maven/bin/mvn clean package

ssh chenry@alihadoop01 "cd /home/chenry/software/tomcat/webapps ;  mkdir graduation_project"

scp /home/chenry/IdeaProjects/graduation_project/target/graduation_project.war chenry@alihadoop01:/home/chenry/software/tomcat/webapps/graduation_project

ssh chenry@alihadoop01 "cd /home/chenry/software/tomcat/webapps/graduation_project ; /home/chenry/software/jdk1.8/bin/jar -xvf graduation_project.war"
