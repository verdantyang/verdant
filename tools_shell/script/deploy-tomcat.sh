#!/bin/bash

project_name=$1

#检查参数
if [ -z "${project_name}" ];then
    echo "请提供项目名"
    exit 1
fi

#环境配置 dev|test|prod
env_profile=prod

#获取包名
package_name=$(cd /share/packages ; ls -tr ${project_name}*.war 2>/dev/null | tail -1)
if [ -z "${package_name}" ]; then
    echo "找不到要发布的包：/share/packages/${project_name}*.war"
    exit 2
fi

#获取应用名，http访问时的项目名，如/caocao
application_name=${project_name}
if [ ! -z "$2" ]; then
    application_name=$2
    echo "采用用户指定的application_name=${application_name}，而不使用application_name=${project_name}"
fi

#停止tomcat
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

#发包
#开发环境检测包是否存在，如果存在则删除历史，生产环境不允许重复发包
if [ -d /home/admin/tomcat/${project_name}/${package_name%.*} ]; then
    if [ "${env_profile}"x="prod"x ]; then
        echo "生产环境不允许重复发包（版本号重复），请修改版本号！"
        exit 3
    else
        /bin/rm -rf /home/admin/tomcat/${project_name}/${package_name} 2>/dev/null 1>/dev/null
        /bin/rm -rf /home/admin/tomcat/${project_name}/${package_name%.*}
    fi
fi
#版本解压
mv /share/packages/${package_name} /home/admin/tomcat/${project_name}/${package_name}
unzip -o /home/admin/tomcat/${project_name}/${package_name} -d /home/admin/tomcat/${project_name}/${package_name%.*}
#创建应用程序访问根目录 
ln -svfT /home/admin/tomcat/${project_name}/${package_name%.*} /home/admin/tomcat/${project_name}/webapps/${application_name}

#获取当前catalina日志最后一行
catalina_log=/usr/local/tomcat/${project_name}/logs/catalina.$(date +'%Y-%m-%d').log
if [ -e "${catalina_log}" ]; then
	lastline=$(wc -l ${catalina_log} | awk '{print $1}')	
fi
lastline=${lastline:-1}

#启动Tomcat
nohup /usr/local/tomcat/${project_name}/bin/server.sh start &
echo "正在等待tomcat启动"
i=1
while true
do
	if [ -e "${catalina_log}" ]; then
		awk '{if(NR>'$lastline'){print $0}}' ${catalina_log}  | egrep -q 'INFO: Server startup in [0-9]+ ms'
		if [ $? -eq 0 ]; then
				break
		fi			
	fi
	let i=i+1
	if [ $i -gt 600 ]; then
		pid=$(ps -ef | grep ${project_name} | grep java | grep -v grep | awk '{print $2}')
		if [ ! -z "${pid}" ]; then
            port=$(ss -nltp | column | grep ",${pid}," | awk '{print $4}' | xargs echo -n 2>/dev/null)
			if [ ! -z "${port}" ]; then
				echo "您的应用启动太慢了，已经超过设定的60秒"
				echo "您的应用已经成功启动端口：${port}"
				exit 0
			else
				echo "您的应用启动太慢了，已经超过设定的60秒，请手工检测应用是否启动！"
				exit 3
			fi
		else
			echo "应用启动失败！"
			exit 4
        fi
	fi
	echo -n '.'
	sleep 0.1
done
echo ""
echo "发布完成"
