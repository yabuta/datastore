#!/bin/sh

#while getopts r:opt
#do
#    case ${opt} in
#	r)
#	    CLEAN = TRUE;;
#	\?)
#	    exit 1;;
#    esac
#done

#if [${CLEAN}=TRUE]
#then
    rm ./*.class
    rm ./CarDataSender/*.class
    rm ./CarDataSender/SendPostgres/*.class
#else

    export CLASSPATH="./:./CarDataSender:./CarDataSender/SendPostgres"
    javac ClientMain.java
#fi
