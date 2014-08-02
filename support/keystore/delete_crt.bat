@echo off
echo [INFO] delete tomcat.crt 

cd %~dp0

keytool -delete -keystore "%JAVA_HOME%/jre/lib/security/cacerts" -alias cas -storepass 123456

pause

