#!/bin/bash

## setting ant home
#ANT_HOME=/Users/kumarpratyush/Documents/Setup/apache-ant-1.9.4
java -cp $ANT_HOME/lib/ant-launcher.jar -Dant.home="$ANT_HOME" -Dant.library.dir="$ANT_HOME/lib" org.apache.tools.ant.launch.Launcher -f build.xml $@
