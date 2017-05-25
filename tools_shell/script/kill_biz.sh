#!/bin/bash

#----------------------------------
# 脚本：kill_biz.sh
#       用于执行停止业务脚本server.sh
# 前提：
#       业务停止脚本位置规范，/usr/local/{tomcat,dubbo}/${biz}/bin/server.sh
# 实例：
#       sh /script/kill_biz.sh     # 停止本地所有业务
#       sh /script/kill_biz.sh uic # 停止本地uic业务
#       sh /script/kill_biz.sh uic,order # 停止本地uic与order业务
# 建议：
#       本脚本建议用于批量停业务环境中，结合系统crontab来实现定时批量停业务
#       查看某个用户计划任务
#       $ pssh -i -x '-t -t' -H '10.30.201.0' 'sudo cat /var/spool/cron/admin'
#       删除某个计划任务
#       $ pssh -i -x '-t -t' -H '10.30.201.0' 'sudo sed -i '/hostname/d' /var/spool/cron/admin'
#       $ pssh -i -x '-t -t' -H '10.30.201.0' "sudo sed -i '/29 14 29 03/d' /var/spool/cron/admin"
#       下发某个计划任务
#       $ pssh -i -x '-t -t' -H '10.30.201.0' 'echo "29 14 29 03 * /script/kill_biz.sh" | sudo tee -a /var/spool/cron/admin'
#----------------------------------

logfile=/script/kill_biz.log
printlog(){
    echo "[$(date +'%Y-%m-%d %H:%M:%S')]${1}" | tee -a ${logfile}
}

bizs=$1
if [ "${bizs}" != "" ]; then
    # 用户指定业务，多个业务之间使用逗号隔开
    printlog "接收到用户指定业务：${bizs}"
    vbizs=(${bizs/,/ })
    for biz in ${vbizs[*]}; do
        printlog "正在处理业务：${biz}"
        # 查找tomcat业务中是否包含
        tomcats=($(ls /usr/local/tomcat/ 2>/dev/null))
        if [ ${#tomcats[*]} -gt 0 ]; then
            for sbiz in ${tomcats[*]}; do
                if [ "${sbiz}" == "${biz}" ]; then
                    printlog "tomcat下找到业务${biz}，正在调用脚本/usr/local/tomcat/${biz}/bin/server.sh stop停止"
                    sh /usr/local/tomcat/${biz}/bin/server.sh stop
                    break
                fi
            done
        fi
        # 查找dubbo业务中是否包含业务
        dubbos=($(ls /usr/local/dubbo/ 2>/dev/null))
        if [ ${#dubbos[*]} -gt 0 ]; then
            for dbiz in ${dubbos[*]}; do
                if [ "${dbiz}" == "${biz}" ]; then
                    printlog "dubbo下找到业务${biz}，正在调用脚本/usr/local/dubbo/${biz}/bin/server.sh stop停止"
                    sh /usr/local/dubbo/${biz}/bin/server.sh stop
                    break
                fi
            done
        fi
    done
else
    printlog "用户未指定业务，下面开始遍历tomcat与dubbo中的业务进行停止操作"
    # 遍历Tomcat
    tbizs=($(ls /usr/local/tomcat/ 2>/dev/null))
    if [ ${#tbizs[*]} -gt 0 ]; then
        for tbiz in ${tbizs[*]}; do
            echo "检测到业务：${tbiz}"
            printlog "正在处理业务：${tbiz}，调用脚本/usr/local/tomcat/${tbiz}/bin/server.sh stop"
            sh /usr/local/tomcat/${tbiz}/bin/server.sh stop
        done
    else
        printlog "本机没有部署tomcat业务"
    fi

    # 遍历Dubbo
    dbizs=($(ls /usr/local/dubbo/ 2>/dev/null))
    if [ ${#dbizs[*]} -gt 0 ]; then
        for dbiz in ${dbizs[*]}; do
            echo "检测到业务：${dbiz}"
            printlog "正在处理业务：${dbiz}，调用脚本/usr/local/dubbo/${dbiz}/bin/server.sh stop"
            sh /usr/local/dubbo/${dbiz}/bin/server.sh stop
        done
    else
        printlog "本机没有部署dubbo业务"
    fi
fi
printlog "本次任务完成！"