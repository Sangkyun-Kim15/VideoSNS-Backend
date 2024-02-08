@echo off
REM Change directory to the project folder
cd C:\java\workspace\VideoSNS

REM Maven clean and build commands with local profile
echo Building the VideoSNS backend...
mvn clean install -Dspring.profiles.active=local

REM Additional build steps can be added here

echo Build completed.
pause