@echo off
rem -------------------------------------------------------------------------
rem JBoss Cluster FrameWork Demo Bootstrap Script for Windows
rem -------------------------------------------------------------------------


@if not "%ECHO%" == ""  echo %ECHO%
@if "%OS%" == "Windows_NT" setlocal

if "%OS%" == "Windows_NT" (
  set "DIRNAME=%~dp0%"
) else (
  set DIRNAME=.\
)

pushd %DIRNAME%..
set "RESOLVED_DEMO_HOME=%CD%"
popd

if "x%DEMO_HOME%" == "x" (
  set "DEMO_HOME=%RESOLVED_DEMO_HOME%"
)

rem Read an optional configuration file.
if "x%STANDALONE_CONF%" == "x" (
   set "STANDALONE_CONF=%DIRNAME%run.conf.bat"
)
if exist "%STANDALONE_CONF%" (
   echo Calling "%STANDALONE_CONF%"
   call "%STANDALONE_CONF%" %*
) else (
   echo Config file not found "%STANDALONE_CONF%"
)

set "JAVA_OPTS=%JAVA_OPTS%
 
if "x%JAVA_HOME%" == "x" (
  set  JAVA=java
  echo JAVA_HOME is not set. Unexpected results may occur.
  echo Set JAVA_HOME to the directory of your local JDK to avoid this message.
) else (
  set "JAVA=%JAVA_HOME%\bin\java"
)


echo ===============================================================================
echo.
echo   JBoss Cluster FrameWork Demo Bootstrap Environment
echo.
echo   DEMO_HOME: %DEMO_HOME%
echo.
echo   JAVA: %JAVA%
echo.
echo   JAVA_OPTS: %JAVA_OPTS%
echo.
echo ===============================================================================
echo.

:RESTART
"%JAVA%" %JAVA_OPTS% ^
 "-Ddemo.home.dir=%DEMO_HOME%" ^
    -jar "%DEMO_HOME%\jboss-modules-1.1.2.GA.jar" ^
    -mp "%DEMO_HOME%\modules" ^
     bootstrap.infinispan.football.rest ^
     %*

if ERRORLEVEL 10 goto RESTART

:END
if "x%NOPAUSE%" == "x" pause

:END_NO_PAUSE