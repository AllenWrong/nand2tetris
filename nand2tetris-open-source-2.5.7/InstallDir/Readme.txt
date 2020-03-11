Zip Contents:
-------------
This zip file contains all the software tools that support 
the book "The Elements of Computing" by Nisan and Schocken. 
We call this collection the "Software Suite". 


If you are installing the Software Suite for the first time:
--------------------------------------------------------------
Create a new directory on your computer, and extract the
entire contents of the zip file to it AS IS, without changing
the default directories structure. After extracting, your
installation directory should contain several sub-directories
and batch files.

It is recommended not to put any of your own files under this
directory, since installations of new versions may overwrite them.


If you are installing a new version of the software Suite:
--------------------------------------------------------------
It is advisable to erase the directory's contents and then 
install and extract the new version from scratch.


Software Requirements (brief):
--------------------------------------------------------------
The Software Suite requires JRE1.3 or JDK1.3 (or later
versions).


Software Requirements (detailed):
--------------------------------------------------------------
The software tools that we supply were written in Java. In
order to run them, your computer must be equipped with either
the JRE (Java Runtime Environment, about 5MB) or the JDK (Java 
Development Kit, also called SDK, about 30MB). The JRE gives
you the ability to run Java programs on your computer (any
programs, not only ours). The JDK enables you to develop and
compile Java programs on your computer.

If you want to use our software tools, all you need is the JRE.
If you want to do Java programming, you need the JDK (which
includes the JRE).  This decision is up to you, and depends on
things that you do outside this course.

The JRE and the JDK can be downloaded freely from 
http://www.javasoft.com.  You must select version 1.3 or up.

If you've downloaded and installed the JDK, make sure that the 
"bin" folder of the JDK (usually something like c:\JDK1.3.1\bin) 
is included in the PATH system variable on your computer. Also, 
make sure that the CLASSPATH system variable contains the 
tools.jar file, which is located in the "lib" folder of the JDK. 
Note that the installation process may make these required 
changes automatically.

Changing system variables up to Win98 is done in the "set 
<variable-name>=..." line in the c:\autoexec.bat file. In NT and 
WIN2000, it is done by updating the variable line in Control 
Panel->System->Advanced->Environment Variables.

The installation of the JRE is simpler, since the PATH & 
CLASSPATH variables are updated automatically as part of the 
installation.


Trouble Shooting:
-----------------

If our software tools don't work, make sure that the correct
JDK folder is included in your PATH & CLASSPATH system
variables.

If the GUI doesn't work properly, make sure that you are using
the version 1.3 or later of the JRE/JDK. Our software may work 
on version 1.2 as well, but with many GUI problems.
