#!/bin/bash

#dubbo相关目录
DUBBO_HOME=$(cd "$(dirname "$0")/.."; pwd)
DUBBO_BIN=${DUBBO_HOME}/bin
DUBBO_CONF=${DUBBO_HOME}/conf
DUBBO_LOG=${DUBBO_HOME}/logs
DUBBO_LIB=${DUBBO_HOME}/lib
DUBBO_PID_FILE=${DUBBO_HOME}/dubbo.pid
DUBBO_MAIN_CLASS=com.caocao.clog.server.CLogServer
WAIT_TIMEOUT=60
DUBBO_STARTUP_MESSAGE="Dubbo service server started!"

#脚本使用示例
usage(){
        echo "Usage:$0 start|stop|restart|status|dump [debug|jmx]"
        exit 1
}

#加载环境变量
if [ -f ${DUBBO_HOME}/bin/setenv.sh ]; then
	. ${DUBBO_HOME}/bin/setenv.sh
fi
JAVA=$(which java)

#加载工具
if [ -f ${DUBBO_HOME}/bin/util.sh ]; then
    . ${DUBBO_HOME}/bin/util.sh
fi

#读取配置文件
SERVER_NAME=$(sed '/dubbo.application.name/!d;s/.*=//' ${DUBBO_CONF}/dubbo.properties | tr -d '\r')
[ -z "${SERVER_NAME}" ] && SERVER_NAME=$(hostname)

#创建日志目录
[ ! -d ${DUBBO_LOG} ] && mkdir ${DUBBO_LOG}
STDOUT_FILE=${DUBBO_LOG}/stdout.log

#获取dubbo的进程PID
get_pid(){
	pid=$(ps -ef | grep java | grep "${DUBBO_CONF}" | awk '{print $2}')
	echo ${pid}
}

#获取dubbo进程监听端口
get_port(){
	pid=$1
    #优先从dubbo.properties中取端口号
    port=$(sed '/dubbo.protocol.port/!d;s/.*=//' ${DUBBO_CONF}/dubbo.properties | tr -d '\r')
    if [ -z "${port}" ] ; then
	    port=$(netstat -ntpl 2>/dev/null | grep ${pid} | awk '{print substr($4,9)}')
    fi
	echo ${port}
}

#检测应用端口是否启动
check_dubbo_port(){
    pid=$1
    err_code=$2
    ok_msg=$3
    err_msg=$4
    port=$(get_port ${pid})
    netstat -lnp 2>/dev/null | grep ${port} 2>&1 1>/dev/null
    if [ $? -eq 0 ]; then
        print_log "${ok_msg}"
        err_code=0
    else
        print_log "${err_msg}"
    fi
    exit ${err_code}
}

#检查dubbo就否已启动完毕
check_dubbo_startup_status(){
    pid=$1
    egrep -q "${DUBBO_STARTUP_MESSAGE}" ${STDOUT_FILE}
    if [ $? -eq 0 ]; then
        echo ""
        print_log "Dubbo已启动完毕"
        check_dubbo_port ${pid} 3 \
            "Dubbo服务成功启动." \
            "Dubbo服务已启动，但应用端口监听没有启动，启动失败！"
    fi
}

#检查是否已启动
wait_listening_start(){
    pid=$1
	ts1=$(date +'%s')
	print_log "正在等待监听启动完成"
    while :
    do
        check_dubbo_startup_status ${pid}
		timeout=$(check_timeout ${ts1})
		[ ${timeout} -eq 0 ] && echo "" && print_log "等待启动超时($(calc_diff_time ${ts1})s)" && exit 2
        echo -n "."
        sleep 0.1
    done    
    echo ""
}


#检查进程是否停止
wait_process_stop(){
    pid=$1
    ts1=$(date +'%s')
    print_log "正在等待进程[pid=${pid}]结束"
    while :
    do
        count=$(ps -ef | grep ${pid} | grep -v grep | wc -l)
        [ ${count} -eq 0 ] && break
        timeout=$(check_timeout ${ts1})
        [ ${timeout} -eq 0 ] && print_log "等待进程[pid=${pid}]结束超时(${timeout}s)" && exit 4
        echo -n "."
        sleep 0.1
    done
    echo ""
}

#启动dubbo服务
do_start(){
	jvm_extra_option=$1
	
	pid=$(get_pid)
	[ -n "${pid}" ] && print_log "dubbo[pid=${pid}]已是运行状态" && exit 5

	#JVM配置
	if [ ! "${jvm_extra_option}"x == "debug"x ]; then
		JAVA_DEBUG_OPTS=""
	fi

	if [ ! "${jvm_extra_option}"x == "jmx"x ]; then
		JAVA_JMX_OPTS=""
	fi

	#启动dubbo
    print_log "正在启动dubbo进程..."
	[ -f ${STDOUT_FILE} ] && mv ${STDOUT_FILE} ${STDOUT_FILE}-$(date +'%Y-%m-%d-%H-%M-%S')    
    DUBBO_LIB=$(ls ${DUBBO_LIB} | grep '.jar' | awk '{print "'${DUBBO_LIB}'/"$0}' | tr "\n" ":")
	nohup $JAVA \
		${JAVA_MEM_OPTS} ${JAVA_OPTS} ${JAVA_DEBUG_OPTS} ${JAVA_JMX_OPTS} ${JAVA_DUMP_OPTS} \
		-cp ${DUBBO_CONF}:${DUBBO_LIB} \
		${DUBBO_MAIN_CLASS} > $STDOUT_FILE 2>&1 &

	#检查监听是否已启动
    pid=$(get_pid)
    print_log "dubbo进程已启动完毕，pid=${pid}"
	echo "${pid}" > ${DUBBO_PID_FILE}
    wait_listening_start ${pid}
}

#停止dubbo服务
do_stop(){
	stop_option=$1
	pid=$(get_pid)
	[ -z "${pid}" ] && print_log "dubbo已是停止状态" && exit 6
	if [ "${stop_option}"x != "skip"x ]; then 
		print_log "正在dump进程，请稍等..."
		${DUBBO_BIN}/dump.sh
	fi
	print_log "正在停止dubbo进程[pid=${pid}]，请稍等..."
	pid=$(get_pid)
	kill ${pid} >> $STDOUT_FILE 2>&1

    #等待进程是否结束
    wait_process_stop ${pid}
    print_log "dubbo服务已停止."
}

#重启dubbo服务
do_restart(){
	do_stop
	do_start $1
}

#查看dubbo状态
do_status(){
	pid=$(get_pid)	
	if [ -n "${pid}" ]; then
		port=$(get_port ${pid})
		print_log "dubbo正在运行，pid=${pid}, port=${port}"
        if [ ! -z "${pid}" ]; then
            echo -n "进程监听的端口："
            ss -nltp | column | grep "${pid}" | awk '{print $4}' | xargs echo -n 2>/dev/null
            echo ""
        fi
	else
		print_log "dubbo没有运行"
	fi
}

#dump进程
do_dump(){
    ${DUBBO_BIN}/dump.sh
}

#程序执行
case $1 in 
        start )
                do_start $2
                ;;
        stop )
                do_stop $2
                ;;
        restart )
                do_restart $2
                ;;
        status )
                do_status
                ;;
        dump )
                do_dump
                ;;
        * )
                usage
                ;;
esac
exit 0
