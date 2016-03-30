#!/bin/bash
#Func: statics network bandwidth

function usage() {
    echo "Usage: $0 <ethernet device>"
    echo " e.g. $0 eth0"
    exit -1
}

function init() {
    eth=$1
    time=60
}

function stat() {
    in_old=$(cat /proc/net/dev | grep "$eth:" | sed -e "s/\(.*\)\:\(.*\)/\2/g" | awk '{ print $1 }' )
    out_old=$(cat /proc/net/dev | grep "$eth:" | sed -e "s/\(.*\)\:\(.*\)/\2/g" | awk '{ print $9 }' )
    sleep ${time}
    in_now=$(cat /proc/net/dev | grep "$eth:" | sed -e "s/\(.*\)\:\(.*\)/\2/g" | awk '{ print $1 }' )
    out_now=$(cat /proc/net/dev | grep "$eth:" | sed -e "s/\(.*\)\:\(.*\)/\2/g" | awk '{ print $9 }' )
    dif_in=$(((in_now-in_old)/1024))
    dif_out=$(((out_now-out_old)/1024))
    echo "IN: ${dif_in} KB/min OUT: ${dif_out} KB/min"
    in_old=${in}
    out_old=${out}
}

if [ $# -lt 1 ]; then
	usage
fi
init $1
stat
exit 0