#!/usr/bin/env sh

# $Id: JackCompiler.sh,v 1.1 2014/06/17 21:14:01 marka Exp $
# mark.armbrust@pobox.com

# User's CDPATH can interfere with cd in this script
unset CDPATH
# Get the true name of this script
script="`test -L "$0" && readlink -n "$0" || echo "$0"`"
dir="$PWD"
cd "`dirname "$script"`"
if [ \( $# -gt 1 \) -o \( "$1" = "-h" \) -o \( "$1" = "--help" \) ]
then
	echo "Usage:"
	echo "    `basename "$0"`              Compiles all .jack files in the current"
	echo "                                 working directory."
	echo "    `basename "$0"` DIRECTORY    Compiles all .jack files in DIRECTORY."
	echo "    `basename "$0"` FILE.jack    Compiles FILE.jack to FILE.vm."
else
	if [ $# -eq 0 ]
	then
		# Use current directory as arg1
		arg1="$dir"
	else
		# Convert arg1 to an absolute path
		if [ `echo "$1" | sed -e "s/\(.\).*/\1/"` = / ]
		then
			arg1="$1"
		else
			arg1="$dir/$1"
		fi
	fi
	echo Compiling "$arg1"
	java -classpath "${CLASSPATH}:bin/classes:bin/lib/Hack.jar:bin/lib/Compilers.jar" Hack.Compiler.JackCompiler "$arg1"
fi
