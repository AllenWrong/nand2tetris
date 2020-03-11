@echo off

rem  $Id: VMEmulator.bat,v 1.3 2014/05/10 00:51:55 marka Exp $
rem  mark.armbrust@pobox.com

setlocal 
if not "%2"=="" goto :USAGE
if "%~1"=="/?" (
:USAGE
  echo Usage:
  echo     VMEmulator             Starts the VM Emulator in interactive mode.
  echo     VMEmulator FILE.tst    Starts the VM Emulator and runs the FILE.tst test
  echo                            script.  The success/failure message is
  echo                            printed to the command console.
  exit -b
)
if not "%~1"=="" (
  set "_arg1=%~f1"
)
pushd "%~dp0"
if "%~1"=="" (
  start javaw -classpath "%CLASSPATH%;.;bin/classes;bin/lib/Hack.jar;bin/lib/HackGUI.jar;bin/lib/Simulators.jar;bin/lib/SimulatorsGUI.jar;bin/lib/Compilers.jar" ^
    VMEmulatorMain
) else (
rem  echo Running "%_arg1%"
  java -classpath "%CLASSPATH%;.;bin/classes;bin/lib/Hack.jar;bin/lib/HackGUI.jar;bin/lib/Simulators.jar;bin/lib/SimulatorsGUI.jar;bin/lib/Compilers.jar" ^
    VMEmulatorMain "%_arg1%"
)
popd
