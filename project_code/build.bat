@echo off
call gradlew clean build
call gradlew createAll
copy build\windows\instalador\Product*.msi ..\dist\
xcopy .\build\windows\ejecutable ..\dist\ /E /H