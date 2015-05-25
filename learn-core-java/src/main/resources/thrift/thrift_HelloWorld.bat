@echo off
cd %~dp0
thrift-0.9.2.exe -r -gen java ./HelloWorld.thrift
echo Hello.java generate complete
pause