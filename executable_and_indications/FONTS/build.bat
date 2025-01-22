@echo off
call gradlew clean build
call gradlew createAll
copy build\windows\instalador\Product*.msi ..\EXE\
xcopy .\build\windows\ejecutable ..\EXE\ /E /H