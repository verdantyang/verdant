#!/bin/bash

[ -z "$1" ] && echo "需要指定查看的网卡，如eth1" && exit 1 
OS_VER=$(lsb_release -sr | cut -d "." -f1)
if [ ${OS_VER} -eq 6 ]; then
    IP=$(/sbin/ifconfig $1 | grep 'inet' | awk '{print $2}' | cut -d ':' -f2)
else
    IP=$(/usr/sbin/ifconfig $1 | grep 'inet' | awk '{print $2}')
fi
echo ${IP}
exit 0
