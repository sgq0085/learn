@echo off
cd %~dp0
call mvn exec:java
pause