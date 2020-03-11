@echo off

rem  $Id: JackCompiler.bat,v 1.2 2014/05/10 00:52:43 marka Exp $
rem  mark.armbrust@pobox.com

setlocal
if not "%2"=="" goto :USAGE
if "%~1"=="/?" (
:USAGE
  echo Usage:
  echo     JackCompiler              Compiles all .jack files in the current
  echo                               working directory.
  echo     JackCompiler DIRECTORY    Compiles all .jack files in DIRECTORY.
  echo     JackCompiler FILE.jack    Compiles FILE.jack to FILE.vm.
  exit -b
)
if not "%~1"=="" (
  set "_arg1=%~f1"
) else (
  set "_arg1=%CD%"
)
pushd "%~dp0"
echo Compiling "%_arg1%"
java -classpath "%CLASSPATH%;bin/classes;bin/lib/Hack.jar;bin/lib/Compilers.jar" ^
  Hack.Compiler.JackCompiler "%_arg1%"
popd
