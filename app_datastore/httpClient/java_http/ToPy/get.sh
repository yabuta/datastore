#!/bin/sh

java -classpath ./:./lib/* jaClient "select * from android_data_store limit 5;"
