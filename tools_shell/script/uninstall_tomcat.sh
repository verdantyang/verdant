#!/bin/bash

#运行用户判断
current_run_user=$USER
if [ "${current_run_user}" != "root" ] 
then
    echo "本程序必须在root用户下执行"   
    exit 1
fi

#Tomcat项目名称
read -p '请输入运行Tomcat的项目名称[caocao-admins]:' -t 60 project_name
[ "${project_name}" == "" ]  && project_name=caocao-admins
echo "${project_name}"

#删除相关文件
echo "正在检测并停止项目${project_name}"
/usr/local/tomcat/${project_name}/bin/server.sh stop 2>/dev/null
ps -ef | grep tomcat | grep java | grep "/${project_name}/" | grep -v grep  | awk '{print $2}' | xargs kill -9 2>/dev/null
sed -i "/alias ${project_name}=.*$/d" /home/admin/.bashrc

echo "正在删除相关的文件"
/bin/rm -rf /usr/local/tomcat/${project_name}
/bin/rm -rf /opt/src/tomcat/${project_name}
/bin/rm -rf /logs/${project_name}
/bin/rm -rf /home/admin/tomcat/${project_name}
/bin/rm -rf /tmp/*.sh

echo "卸载完成"
