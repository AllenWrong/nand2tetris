@echo off

rem  $Id: Assembler.bat,v 1.2 2014/05/10 00:52:43 marka Exp $
rem  mark.armbrust@pobox.com

setlocal
if not "%2"=="" goto :USAGE
if "%~1"=="/?" (
:USAGE
  echo Usage:
  echo     Assembler               Starts the assembler in interactive mode.
  echo     Assembler FILE[.asm]    Assembles FILE.asm to FILE.hack.
  exit -b
)
if not "%~1"=="" (
  set "_arg1=%~f1"
)
pushd "%~dp0"
if "%~1"=="" (
  start javaw -classpath "%CLASSPATH%;bin/classes;bin/lib/Hack.jar;bin/lib/HackGUI.jar;bin/lib/Compilers.jar;bin/lib/AssemblerGUI.jar;bin/lib/TranslatorsGUI.jar" ^
    HackAssemblerMain
) else (
  echo Assembling "%_arg1%"
  java -classpath "%CLASSPATH%;bin/classes;bin/lib/Hack.jar;bin/lib/HackGUI.jar;bin/lib/Compilers.jar;bin/lib/AssemblerGUI.jar;bin/lib/TranslatorsGUI.jar" ^
    HackAssemblerMain "%_arg1%"
)
popd
