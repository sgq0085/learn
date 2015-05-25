@echo off
cd %~dp0
thrift-0.9.2.exe -r -gen java ./AdditionService.thrift
echo AdditionService.java generate complete
pause