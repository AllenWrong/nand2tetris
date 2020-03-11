@echo off
cd %0\..
java -classpath "%CLASSPATH%;bin/classes" TextComparer %1 %2
