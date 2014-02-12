#!/usr/bin/python
#-*- coding:utf-8 -*-

import httplib
import urllib
import datetime
import time
import sys

"""
httpを使ってサーバにデータの送信を行う
httpサーバ（python）を使った場合どれくらいの時間がかかるのかっをテストする

"""


def http_client(sql):


    host = "localhost"
    path = "/cgi-bin/insertData/insertsql.py"
    params = urllib.urlencode({ "sql": sql})

    conn = httplib.HTTPConnection(host)
    conn.request("POST",path,params)
    res = conn.getresponse()

    print res.read()
    conn.close()


def createSQL(count):
    time = datetime.datetime.today()
    speed = count/2
    battery = count/2
    angle = count/2
    drivepedal = count/2
    bbrakepress = count/2
    brakepedal = count/2
    voltage = count/2
    anp = count/2
    battmaxtemparature = count/2
    battmintemparature = count/2
    speedfl = count/2
    speedfr = count/2
    speedrr = count/2
    speedrl = count/2
    gaslevel = count/2



    print time

    sql = "insert into prius_data_store(tm,speed,battery,angle,drivepedal,bbrakepress,brakepedal,voltage,anp,battmaxtemparature,battmintemparature,speedfl,speedfr,speedrr,speedrl,gaslevel) values('%s',%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s);" % (time,speed,battery,angle,drivepedal,bbrakepress,brakepedal,voltage,anp,battmaxtemparature,battmintemparature,speedfl,speedfr,speedrr,speedrl,gaslevel)
    return sql 


if __name__ == "__main__":

    count = 0

    while True:
        sql = createSQL(count)
        print sql
        http_client(sql)
        count += 1
        if count > 100:
            count = 0
        time.sleep(1)