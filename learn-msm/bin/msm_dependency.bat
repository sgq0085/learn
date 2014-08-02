@echo off
cd %~dp0
cd ..
echo y|rd/s/q .\target\lib
call mvn dependency:copy-dependencies  -DexcludeScope=compile  -DoutputDirectory=./target/lib
echo [INFO] download to ./target/lib
pause