#!/bin/bash

#打印日志
print_log(){
    echo "[$(date +'%Y-%m-%d %H:%M:%S')]${1}" | tee -a ${logfile}
}

#计算时间
calc_diff_time(){
    ts1=$1
    ts2=$(date +'%s')
    let tt=ts2-ts1
    echo $tt
}
check_timeout(){
    ts1=$1
    ts2=$(date +'%s')
    let tt=ts2-ts1
    if [ $tt -gt ${WAIT_TIMEOUT} ]; then
        echo 0
    else
        echo 1
    fi
}

#获取IP
get_ip(){
    interface_name=$1
    OS_VER=$(lsb_release -sr | cut -d "." -f1)
    if [ ${OS_VER} -eq 6 ]; then
        IP=$(/sbin/ifconfig ${interface_name} | grep 'inet' | awk '{print $2}' | cut -d ':' -f2)
    else
        IP=$(/usr/sbin/ifconfig ${interface_name} | grep 'inet' | awk '{print $2}')
    fi  
    echo ${IP}
}
