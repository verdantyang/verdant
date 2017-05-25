#!/bin/bash

#dubbo相关目录
DUBBO_HOME=$(cd "$(dirname "$0")/.."; pwd)
DUBBO_BIN=${DUBBO_BIN}/bin
DUBBO_CONF=${DUBBO_HOME}/conf
DUBBO_LOG=${DUBBO_HOME}/logs
DUBBO_DUMP=${DUBBO_LOG}/dump/$(date +'%Y-%m-%d-%H-%M-%S')

#加载环境变量
if [ -f ${DUBBO_HOME}/bin/setenv.sh ]; then
    . ${DUBBO_HOME}/bin/setenv.sh
fi
JAVA_BIN=$(cd "$(dirname $(which java))"; pwd)

#加载工具
if [ -f ${DUBBO_HOME}/bin/util.sh ]; then
    . ${DUBBO_HOME}/bin/util.sh
fi

#获取dubbo的进程PID
get_pid(){
    pid=$(ps -ef | grep java | grep "${DUBBO_CONF}" | awk '{print $2}')
    echo ${pid}
}

pid=$(get_pid)
if [ -n "${pid}" ]; then
    ts1=$(date +'%s')
    print_log "正在dump进程[pid=${pid}]相关信息"
    [ ! -d ${DUBBO_DUMP} ]  && mkdir -p ${DUBBO_DUMP}
    ${JAVA_BIN}/jstack ${pid}                           > ${DUBBO_DUMP}/jstack-${pid}.dump 2>&1
    ${JAVA_BIN}/jinfo ${pid}                            > ${DUBBO_DUMP}/jinfo-${pid}.dump 2>&1
    ${JAVA_BIN}/jstat -gcutil ${pid}                    > ${DUBBO_DUMP}/jstat-gcutil-${pid}.dump 2>&1
    ${JAVA_BIN}/jstat -gccapacity ${pid}                > ${DUBBO_DUMP}/jstat-gccapacity-${pid}.dump 2>&1
    ${JAVA_BIN}/jmap ${pid}                             > ${DUBBO_DUMP}/jmap-${pid}.dump 2>&1
    ${JAVA_BIN}/jmap -heap ${pid}                       > ${DUBBO_DUMP}/jmap-heap-${pid}.dump 2>&1
    ${JAVA_BIN}/jmap -histo ${pid}                      > ${DUBBO_DUMP}/jmap-histo-${pid}.dump 2>&1
    [ -r /usr/sbin/lsof ]   && /usr/sbin/lsof -p ${pid} > ${DUBBO_DUMP}/lsof-${pid}.dump
    [ -r /bin/netstat ]     && /bin/netstat -an         > ${DUBBO_DUMP}/netstat.dump 2>&1
    [ -r /usr/bin/iostat ]  && /usr/bin/iostat          > ${DUBBO_DUMP}/iostat.dump 2>&1
    [ -r /usr/bin/mpstat ]  && /usr/bin/mpstat          > ${DUBBO_DUMP}/mpstat.dump 2>&1
    [ -r /usr/bin/vmstat ]  && /usr/bin/vmstat          > ${DUBBO_DUMP}/vmstat.dump 2>&1
    [ -r /usr/bin/free ]    && /usr/bin/free -t         > ${DUBBO_DUMP}/free.dump 2>&1
    [ -r /usr/bin/sar ]     && /usr/bin/sar             > ${DUBBO_DUMP}/sar.dump 2>&1
    [ -r /usr/bin/uptime ]  && /usr/bin/uptime          > ${DUBBO_DUMP}/uptime.dump 2>&1
    print_log "dump耗时：$(calc_diff_time ${ts1})s"
else
    print_log "没有找到进程，无法dump"
fi

print_log "dump操作结束"
