#!/bin/bash

SCRIPT_NAME=$0
ACTION_NAME=$1
CATALINA_BASE=/usr/local/tomcat/calarm-web

#读取配置文件，本脚本主要读取其中的CATALINA_HOME变量定义
if [ -e ${CATALINA_BASE}/bin/setenv.sh ]; then
	. ${CATALINA_BASE}/bin/setenv.sh
else
    echo "本脚本需要读取配置文件setenv.sh"
    exit 101
fi

if [ "${CATALINA_HOME}" == "" ]; then
	echo "本脚本执行需要setenv.sh中配置CATALINA_HOME变量的支持"	
	exit 100
fi

LOG=$CATALINA_HOME/logs/server.log

#脚本使用示例
usage(){
	echo "Usage:${SCRIPT_NAME} start|stop|restart|status"
	exit 1
}

#获取当前操作Tomcat的进程ID
get_pid(){
	PID=$(ps -ef | grep "${CATALINA_HOME}/conf/logging.properties" | grep -v grep | awk '{print $2}')
}

#获取当前操作Tomcat的端口号
get_port(){
	PORT=`grep 'port=' ${CATALINA_HOME}/conf/server.xml | grep '<Connector' | head -n 1 | cut -d'"' -f2`
}

#启动Tomcat
start(){
	get_pid
	if [[ "${PID}x" != "x" ]];then
		echo "检测到Tomcat[${PID}]正在运行."
	else
		${CATALINA_HOME}/bin/startup.sh > /dev/null 2>&1
		get_pid
		get_port
		echo "已经启动了Tomcat[pid=${PID},port=${PORT}]."
	fi
}

#停止Tomcat
stop(){
	get_pid
	if [[ "${PID}x" != "x" ]] ; then
		LOCK_FILE=/tmp/kill-tomcat-${CATALINA_HOME##*/}.lock
		echo "lock_file=${LOCK_FILE}"
		if [ -e $LOCK_FILE ]; then
			echo "Tomcat正在停止中，请稍候..."
			exit 3
		fi
		echo $$ > $LOCK_FILE
		${CATALINA_HOME}/bin/shutdown.sh > /dev/null 2>&1
		sleep 0.5
		kill -9 ${PID} 2>/dev/null
		rm -rf $LOCK_FILE
		echo "Tomcat已经停止"
	else
		echo "检测到Tomcat没在运行"
	fi
}

#重启Tomcat
restart(){
	stop
	start
}

#检测Tomcat状态
status(){
	get_pid
	get_port
	echo "当前Tomcat状态：port=${PORT}, pid=${PID}"
    if [ ! -z "${PID}" ]; then
        echo -n "进程监听的端口："
        ss -nltp | column | grep "${PID}" | awk '{print $4}' | xargs echo -n 2>/dev/null
        echo ""
    fi
}

#Tomcat操作动作
case ${ACTION_NAME} in 
	start )
		start
		;;
	stop )
		stop
		;;
	restart )
		restart
		;;
	status )
		status
		;;
	* )
		usage
		;;
esac

exit 0
