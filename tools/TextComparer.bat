@echo off

rem  $Id: TextComparer.bat,v 1.2 2014/05/10 00:52:43 marka Exp $
rem  mark.armbrust@pobox.com

setlocal
if not "%3"=="" goto :USAGE
if "%1"=="/?" goto :USAGE
if not "%~1"=="" (
  set "_arg1=%~f1"
)
if not "%~2"=="" (
  set "_arg2=%~f2"
)
pushd "%~dp0"
if  NOT "%~1"=="" (
  if  NOT "%~2"=="" (
  java -classpath "%CLASSPATH%;bin/classes" TextComparer ^
    "%_arg1%" "%_arg2%"
  popd
  exit /B
  )
)
:USAGE
echo Usage:
echo     TextComparer FILE1 FILE2    Compares FILE1 and FILE2.  The success
echo                                 message or the first miscompared line
echo                                 is printed to the command console.
popd
