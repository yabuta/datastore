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


    host = "db1.ertl.jp"
    path = "/cgi-bin/getData/exesql.py"
    #path = "/cgi-bin/getData/exesql.py"
    params = urllib.urlencode({ "sql": sql})

    conn = httplib.HTTPConnection(host)
    conn.request("POST",path,params)
    res = conn.getresponse()

    print res.read()
    conn.close()



if __name__ == "__main__":

    sql = sys.argv[1]

    print sql

    time1 = time.time()
    http_client(sql)
    time2 = time.time()
    duration = time2 - time1
    print duration
