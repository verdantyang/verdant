#!/bin/bash

logfile=/tmp/up_app.log
printlog(){
    echo "[$(date +'%Y-%m-%d %H:%M:%S')]${1}" | tee -a ${logfile}
}

# 查找tomcat业务
tomcats=($(ls -l /usr/local/tomcat/ | grep '^l' | grep 'tomcat' | awk '{print $NF}'| awk -F'/' '{print $NF}' 2>/dev/null))
# 查找dubbo业务
dubbos=($(ls -l /usr/local/dubbo/ | grep '^l' | grep 'dubbo' | awk '{print $NF}'| awk -F'/' '{print $NF}' 2>/dev/null))
# 合并业务
apps=("${tomcats[*]}" "${dubbos[*]}")
apps=$(echo "${apps[*]}" | tr ' ' ',')
printlog "本次查找到的应用有：${apps}"

# 获取本机内网IP
IP=$(/sbin/ip a | grep 'eth0' -A 3 | head -3 | grep 'inet' | awk '{print $2}' | awk -F'/' '{print $1}')
printlog "内网IP：${IP}"

# 调远程接口上报
# http://127.0.0.1:8000/ccapp/add_ccapphost/?app=care&ip=10.252.162.123
api_url="http://118.178.135.38:82/ccapp/add_ccapphost/"
curl "${api_url}?app=${apps}&ip=${IP}" 2>&1 1>>${logfile}
printlog "本次任务完成！"
