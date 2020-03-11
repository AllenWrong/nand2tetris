#!/usr/bin/env sh

# $Id: HardwareSimulator.sh,v 1.1 2014/06/17 21:14:01 marka Exp $
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
	echo "    `basename "$0"`             Starts the Hardware Simulator in"
	echo "                                     interactive mode."
	echo "    `basename "$0"` FILE.tst    Starts the Hardware Simulator and runs the"
	echo "                                     FILE.tst test script.  The success/failure"
	echo "                                     message is printed to the command console."
elif [ $# -eq 0 ]
then
	# Run hardware simulator in interactive mode
	java -classpath "${CLASSPATH}:bin/classes:BuiltIn:bin/lib/Hack.jar:bin/lib/HackGUI.jar:bin/lib/Simulators.jar:bin/lib/SimulatorsGUI.jar:bin/lib/Compilers.jar" HardwareSimulatorMain &
else
	# Convert arg1 to an absolute path and run hardware simulator with arg1
	if [ `echo "$1" | sed -e "s/\(.\).*/\1/"` = / ]
	then
		arg1="$1"
	else
		arg1="${dir}/$1"
	fi
#	echo Running "$arg1"
	java -classpath "${CLASSPATH}:bin/classes:BuiltIn:bin/lib/Hack.jar:bin/lib/HackGUI.jar:bin/lib/Simulators.jar:bin/lib/SimulatorsGUI.jar:bin/lib/Compilers.jar" HardwareSimulatorMain "$arg1"
fi
