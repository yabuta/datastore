#!/bin/sh

sed -e "1,1000d" angle.h > angle_turn_left.h

sed -e "1,1000d" accell.h > accell_turn_left.h

sed -e "1,1000d" brake.h > brake_turn_left.h

rm angle.h
rm accell.h
rm brake.h
