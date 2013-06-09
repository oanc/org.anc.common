@echo off

rem Deploy a version to the ANC Maven repository
setlocal
if "%1"=="" goto usage
if "%2"=="" goto usage

set RELEASE=%1
set TYPE=%2

set PACK=org.anc
set NAME=common
set GROUP=-DgroupId=%PACK%
set ARTIFACT=-DartifactId=%NAME%
set REPO=-DrepositoryId=anc-dev-%TYPE%
set VERSION=-Dversion=%RELEASE%
set FILE=-Dfile=target\%NAME%-%RELEASE%.jar
set URL=-Durl=dav:http://www.americannationalcorpus.org/maven/%TYPE%

mvn deploy:deploy-file -Dpackaging=jar %GROUP% %ARTIFACT% %REPO% %VERSION% %FILE% %URL%
goto end

:usage
echo USAGE
echo     deploy <version> <type>
echo EXAMPLE
echo     deploy 1.0.0-SNAPSHOT snapshot

:end
endlocal