#!/bin/bash

#项目名
project_name=$1
if [ -z "${project_name}" ]; then
    echo "请提供项目名"
    exit 1
fi

#环境变量设置
env_profile=prod

#获取包名
package_name=$(cd /share/packages ; ls -tr ${project_name}*.zip 2>/dev/null | tail -1)
if [ -z "${package_name}" ]; then
    echo "找不到要发布的包：/share/packages/${project_name}*.zip"
    exit 2
fi

#停止dubbo
/usr/local/dubbo/${project_name}/bin/server.sh stop

echo "正在等待dubbo停止"
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
#开发环境检测包是否存在，如果存在则删除历史
if [ -d /home/admin/dubbo/${project_name}/${package_name%.*} ]; then
    if [ "${env_profile}"x="prod"x ]; then
        echo "生产环境不允许重复发包（版本号重复），请修改版本号！"
        exit 3
    else
        /bin/rm -rf /home/admin/dubbo/${project_name}/${package_name} 2>/dev/null 1>/dev/null
        /bin/rm -rf /home/admin/dubbo/${project_name}/${package_name%.*}
    fi
fi
#将包移动到发包目录并解压
mv /share/packages/${package_name} /home/admin/dubbo/${project_name}/${package_name}
unzip -o /home/admin/dubbo/${project_name}/${package_name} -d /home/admin/dubbo/${project_name}/
#创建lib目录超链接 
ln -svfT /home/admin/dubbo/${project_name}/${package_name%.*}/lib /usr/local/dubbo/${project_name}/lib

#备份stdout.log日志
stdout_log=/usr/local/dubbo/${project_name}/logs/stdout.log
stdout_log_bak=/usr/local/dubbo/${project_name}/logs/stdout.append.log
if [ -e "${stdout_log}" ]; then
    cat ${stdout_log} >> ${stdout_log_bak}
fi

#启动Tomcat
nohup /usr/local/dubbo/${project_name}/bin/server.sh start &
echo "正在等待dubbo启动"
i=1
while true
do
	if [ -e "${stdout_log}" ]; then
		awk '{if(NR>0){print $0}}' ${stdout_log}  | egrep -q 'Dubbo service server started'
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
