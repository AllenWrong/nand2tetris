#!/usr/bin/env sh

# $Id: TextComparer.sh,v 1.1 2014/06/17 21:14:01 marka Exp $
# mark.armbrust@pobox.com

# User's CDPATH can interfere with cd in this script
unset CDPATH
# Get the true name of this script
script="`test -L "$0" && readlink -n "$0" || echo "$0"`"
dir="$PWD"
cd "`dirname "$script"`"
if [ \( $# -ne 2 \) -o \( "$1" = "-h" \) -o \( "$1" = "--help" \) ]
then
	# print usage
	echo "Usage:"
	echo "    `basename "$0"` FILE1 FILE2    Compares FILE1 and FILE2.  The success"
	echo "                                   message or the first miscompared line"
	echo "                                   is printed to the command console."
else
	# Convert arg1 to an absolute path
	if [ `echo "$1" | sed -e "s/\(.\).*/\1/"` = / ]
	then
		arg1="$1"
	else
		arg1="$dir/$1"
	fi
	# Convert arg2 to an absolute path
	if [ `echo "$2" | sed -e "s/\(.\).*/\1/"` = / ]
	then
		arg2="$2"
	else
		arg2="$dir/$2"
	fi
#	echo Comparing "$arg1" "$arg2"
	java -classpath "${CLASSPATH}:bin/classes" TextComparer "$arg1" "$arg2"
fi
