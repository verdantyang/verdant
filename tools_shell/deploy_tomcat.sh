#!/bin/bash

#变量配置
project_name=cdiamond
project_version=$1
application_name=cdiamond
application_check_url="http://localhost:8081/cdiamond/"
debug=${2:-false}
current_date=$(date +'%Y-%m-%d')
current_timestamp=$(date +'%Y-%m-%d-%H-%M-%S')
tomcat_catalina_log=/usr/local/tomcat/${project_name}/logs/catalina.${current_date}.log
tomcat_localhost_log=/usr/local/tomcat/${project_name}/logs/localhost.${current_date}.log
logfile=/script/logs/${project_name}.log
rollbacksh=/usr/local/tomcat/${project_name}/bin/rollback.sh

#打印日志
print_log(){
	echo "[$(date +'%Y-%m-%d %H:%M:%S')]${1}" | tee -a ${logfile}
}

#处理发包前后Tomcat的日志
clear_log(){
    append_text="${1}"
    cp ${tomcat_catalina_log} ${tomcat_catalina_log}.${append_text} 2>&1 1>/dev/null
    /bin/cat /dev/null > ${tomcat_catalina_log} 2>&1 1>/dev/null
    cp ${tomcat_localhost_log} ${tomcat_localhost_log}.${append_text} 2>&1 1>/dev/null
    /bin/cat /dev/null > ${tomcat_localhost_log} 2>&1 1>/dev/null
}

#计算Tomcat部署耗时
calc_diff_time(){
    ts2=$(date +'%s')
    let tt=ts2-ts1
    print_log "本次发包共耗时${tt}秒"
}
calc_check_grant_one_minute(){
    ts2=$(date +'%s')
    let tt=ts2-ts1
    if [ $tt -gt 60 ]; then
        echo 0
    else
        echo 1
    fi
}

#检测localhost中配置参数的问题
check_param_placeholder(){
    grep -q 'Could not resolve placeholder' ${tomcat_localhost_log} 2>&1 1>/dev/null
    if [ $? -eq 0 ]; then
        [ "${debug}" == "true" ] && echo ""
        grep "Could not resolve placeholder" ${tomcat_localhost_log} | 
            awk -F'[{}]' '{print $2}' | 
                sort  | uniq | 
                    xargs -I{} echo "没有提供参数{}" | tee -a ${logfile}
        print_log "由于配置问题，本次发布失败！"
        exit 2
    else 
        [ "${debug}" == "true" ] && echo -n "localhost:no placeholder error " | tee -a ${logfile}
    fi
}

#检测catalina中SEVERE严重的错误
check_server_error(){
    grep -q 'SEVERE:' ${tomcat_catalina_log}
    if [ $? -eq 0 ]; then
        [ "${debug}" == "true" ] && echo "" | tee -a ${logfile}
        print_log "Tomcat启动报严重错误，本次发布失败！"
        exit 1
    else
        [ "${debug}" == "true" ] && echo -n "catalina:no severe " | tee -a ${logfile}
    fi
}

#检查接口是否可访问
check_url_status(){
	err_code=$1
	ok_msg=$2
	err_msg=$3
	response_code=$(curl -s --connect-timeout 1 -w %{http_code} -o /dev/null ${application_check_url})
	if [ $response_code -eq 200 -o $response_code -eq 302 ] ; then
		print_log "${ok_msg}"
		calc_diff_time
		exit 0
	else
		print_log "${err_msg}"
		exit ${err_code}
	fi
}

#检测catalina中Tomcat应用启动标识
check_tomcat_startup_and_url_status(){
	egrep -q 'INFO: Server startup in [0-9]+ ms' ${tomcat_catalina_log}
    if [ $? -eq 0 ]; then
        [ "${debug}" == "true" ] && echo "" | tee -a ${logfile}
        print_log "Tomcat已启动完毕"
        check_url_status 3 "Tomcat已启动请求也可正常访问，本次发布成功！" "Tomcat已启动但应用无法访问，本次发布失败！" 
    else 
        [ "${debug}" == "true" ] && print_log " 等待Tomcat部署完成..."
    fi
}

#检测检测脚本是否超时
check_check_timeout(){
	elapsetime=$(calc_check_grant_one_minute)
    if [ ${elapsetime} -eq 0 ]; then
        print_log "Tomcat状态检测超时！"
		check_url_status 4 "Tomcat状态无法检测但请求验证通过，本次发布成功！" "Tomcat状态无法检测并且请求验证失败，本次发布失败！"       
    fi
}

print_log "本次发包${current_timestamp}开始...."

#解压包
print_log "正在解压包${project_name}-${project_version}.war"
unzip /home/admin/tomcat/${project_name}/${project_name}-${project_version}.war \
    -d /home/admin/tomcat/${project_name}/${project_name}-${project_version} 2>&1 1>/dev/null
if [ $? -ne 0 ]; then
	print_log "包${project_name}-${project_version}.war解压失败"
	print_log "本次发包${current_timestamp}失败!"
	exit 5
fi

#生成回滚脚本
print_log "正在创建回滚脚本 ${rollbacksh}"
echo '#!/bin/bash' > ${rollbacksh}
ls -l /home/admin/tomcat/${project_name}/webapps/${application_name} | awk '{print "ln -svfT "$10" "$8}' >> ${rollbacksh}
echo "sh /usr/local/tomcat/${project_name}/bin/server.sh restart" >> ${rollbacksh}

print_log "正在创建链接文件"
/bin/ln -svfT /home/admin/tomcat/${project_name}/${project_name}-${project_version} \
    /home/admin/tomcat/${project_name}/webapps/${application_name} 2>&1 1>/dev/null
ls -l /home/admin/tomcat/${project_name}/webapps/${application_name} 2>&1 1>/dev/null | tee -a ${logfile}

#重启Tomcat
print_log "正在清理Tomcat的catalina与localhost日志"
clear_log "${current_timestamp}"

print_log "正在停止Tomcat"
sh /usr/local/tomcat/${project_name}/bin/server.sh stop
clear_log "${current_timestamp}.stop"

print_log "正在启动Tomcat"
ts1=$(date +'%s')
nohup sh /usr/local/tomcat/${project_name}/bin/server.sh start &

#检测Tomcat应用是否成功启动
print_log "正在等待Tomcat发布应用..."
while [ true ] 
do
    [ "${debug}" == "true" ] && echo -n "[$(date +'%Y-%m-%d %H:%M:%S')]正在检测Tomcat应用启动状态" | tee -a ${logfile}
    
    #检测localhost中配置参数的问题
	check_param_placeholder

    #检测catalina中SEVERE严重的错误
	check_server_error

    #检测catalina中Tomcat应用启动标识
    check_tomcat_startup_and_url_status
    
	#检测检测脚本是否超时（默认1分钟）
	check_check_timeout
    
    echo -e '.\c'
    sleep 0.1
done

exit 0
