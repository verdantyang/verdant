#!/bin/bash

project_name=dubbo-admin

#停止tomcat
function do_stop(){
	/usr/local/tomcat/${project_name}/bin/server.sh stop
	echo "正在等待tomcat停止"
	i=1
	while true
	do
		pid=$(ps -ef | grep ${project_name} | grep java | grep -v grep | awk '{print $2}')
		if [ -z "${pid}" ]; then
			break
		fi
		let i=i+1
		if [ $i -gt 600 ]; then
			kill -9 ${pid}
			sleep 0.5
			break;
		fi
		echo -n '.'
		sleep 0.1
	done
	echo ""
}

#启动Tomcat
function do_start(){
	#获取当前catalina日志最后一行
	catalina_log=/usr/local/tomcat/${project_name}/logs/catalina.$(date +'%Y-%m-%d').log
	lastline=$(wc -l ${catalina_log} | awk '{print $1}')
	lastline=${lastline:-1}
	
	#启动Tomcat
	nohup sh /usr/local/tomcat/${project_name}/bin/server.sh start &
	echo "正在等待tomcat启动"
	i=1
	while true
	do
		awk '{if(NR>'$lastline'){print $0}}' ${catalina_log}  | egrep -q 'INFO: Server startup in [0-9]+ ms'
		if [ $? -eq 0 ]; then
			break
		fi
		let i=i+1
		if [ $i -gt 600 ]; then
			echo "启动超时（约60秒）"
			exit 3
		fi
		echo -n '.'
		sleep 0.1
	done
	echo ""
}


case $1 in 
    start)
        do_start
        ;;
    stop)
        do_stop
        ;;
    restart)
        do_stop
        do_start
        ;;
    status)
        /usr/local/tomcat/${project_name}/bin/server.sh status
        ;;
    *)
        /usr/local/tomcat/${project_name}/bin/server.sh 
        ;;
esac
exit 0
