@echo off
echo [INFO] import tomcat.crt 

cd %~dp0

echo y|keytool -import -keystore %JAVA_HOME%\jre\lib\security\cacerts -file cas.crt -alias cas -storepass 123456

pause

