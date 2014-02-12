#!/usr/bin/python
#-*- coding:utf-8 -*-

ACCELLPEDAL = 6
BRAKEPEDAL = 23
ANGLE = 20
accell_header = "double __accell_turn_left[] = {\n"
brake_header = "double __brake_turn_left[] = {\n"
angle_header = "double __angle_turn_left[] = {\n"
footer = "};\n"


def file():

    """
    double __angle_turn_left[] = {
    ...

    }

    """
    apf = open('./accell.h','w')
    bpf = open('./brake.h','w')
    anf = open('./angle.h','w')


    apf.write(accell_header)
    bpf.write(brake_header)
    anf.write(angle_header)


    for i,line in enumerate(open('./turnleft.csv','r')):
        temp = line.split(',')
        row = "%s,\n" % (temp[ACCELLPEDAL-1])
        apf.write(row)
        row = "%s,\n" % (temp[BRAKEPEDAL-1])
        bpf.write(row)
        row = "%s,\n" % (temp[ANGLE-1])
        anf.write(row)


    apf.write(footer)
    bpf.write(footer)
    anf.write(footer)

    apf.close()
    bpf.close()
    anf.close()



if __name__ == "__main__":

    file()
