#!/usr/bin/env sh

# $Id: Assembler.sh,v 1.1 2014/06/17 21:14:01 marka Exp $
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
	echo "    `basename "$0"`               Starts the assembler in interactive mode."
	echo "    `basename "$0"` FILE[.asm]    Assembles FILE.asm to FILE.hack."
elif [ $# -eq 0 ]
then
	# Run assembler in interactive mode
	java -classpath "${CLASSPATH}:bin/classes:bin/lib/Hack.jar:bin/lib/HackGUI.jar:bin/lib/Compilers.jar:bin/lib/AssemblerGUI.jar:bin/lib/TranslatorsGUI.jar" HackAssemblerMain &
else
	# Convert arg1 to an absolute path and run assembler with arg1.
	if [ `echo "$1" | sed -e "s/\(.\).*/\1/"` = / ]
	then
		arg1="$1"
	else
		arg1="${dir}/$1"
	fi
	echo Assembling "$arg1"
	java -classpath "${CLASSPATH}:bin/classes:bin/lib/Hack.jar:bin/lib/HackGUI.jar:bin/lib/Compilers.jar:bin/lib/AssemblerGUI.jar:bin/lib/TranslatorsGUI.jar" HackAssemblerMain "$arg1"
fi

