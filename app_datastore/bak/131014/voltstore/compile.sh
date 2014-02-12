#!/bin/sh

export CLASSPATH="./:$HOME/voltdb/lib/*:$HOME/voltdb/voltdb/*"

javac voltStore.java voltjdbc.java ClientInstruction.java ClientCommunicateThread.java ClientAcceptThread.java ServerMain.java

#javac Insert.java voltStore.java ClientInstruction.java ClientCommunicateThread.java ClientAcceptThread.java ServerMain.java



