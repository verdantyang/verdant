#!/bin/bash

# wget http://10.168.73.68:17878/download/scripts/install_tomcat.sh -O /tmp/install_tomcat.sh && sh /tmp/install_tomcat.sh

#运行用户判断
current_run_user=$USER
if [ "${current_run_user}" != "admin" ] 
then
    echo "本程序必须在admin用户下执行"	
    exit 2
fi

#Tomcat项目名称
read -p '请输入运行Tomcat的项目名称[caocao-admins]:' -t 60 project_name
[ "${project_name}" == "" ]  && project_name=caocao-admins
echo "${project_name}"

#Tomcat项目端口号
read -p '请输入运行Tomcat的运行端口起始值x[8]:' -t 60 port_start
[ "${port_start}" == "" ]  && port_start=8
if [[ ! "${port_start}" =~ ^[0-9]$ ]]; then
    echo "Tomcat端口号超始值必须为一位的数字，比如1则端口号最终为1080"
    exit 5
fi
echo "${port_start}"

#前提条件判断
if [ ! -w "/opt/src/tomcat" ]; then
    echo "admin用户需要拥有对/opt/src/tomcat的写权限"
    exit 3
fi

if [ ! -w "/usr/local/tomcat" ]; then
    echo "admin用户需要拥有对/usr/local/tomcat的写权限"
    exit 3
fi

#下载Tomcat软件
wget http://10.168.73.68:17878/download/apache-tomcat-7.0.62-caocao.tar.gz -O /tmp/apache-tomcat-7.0.62-caocao.tar.gz

#解压并创建超链接
tar xzf /tmp/apache-tomcat-7.0.62-caocao.tar.gz -C /opt/src/tomcat/
mv /opt/src/tomcat/apache-tomcat-7.0.62 /opt/src/tomcat/${project_name}
ln -sv /opt/src/tomcat/${project_name} /usr/local/tomcat/${project_name}

#修改Tomcat配置
sed -i "s/caocao-admins/${project_name}/g" /usr/local/tomcat/${project_name}/conf/server.xml
sed -i "s/[0-9]\([0-9]\{3\}\)/${port_start}\1/g" /usr/local/tomcat/${project_name}/conf/server.xml
sed -i "s/caocao-admins/${project_name}/g" /usr/local/tomcat/${project_name}/bin/setenv.sh
sed -i "s/caocao-admins/${project_name}/g" /usr/local/tomcat/${project_name}/bin/server.sh
chmod u+x /usr/local/tomcat/${project_name}/bin/setenv.sh
chmod u+x /usr/local/tomcat/${project_name}/bin/server.sh
echo "alias ${project_name}=/usr/local/tomcat/${project_name}/bin/server.sh" >> ~/.bashrc
source ~/.bashrc
mkdir -p /home/admin/tomcat/${project_name}/webapps
mkdir -p /logs/${project_name}/
rm -rf /tmp/apache-tomcat-7.0.62-caocao.tar.gz
