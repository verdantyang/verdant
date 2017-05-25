#!/bin/bash

# wget http://10.168.73.68:17878/download/scripts/install_tomcat_root.sh -O /tmp/install_tomcat_root.sh && sh /tmp/install_tomcat_root.sh

#运行用户判断
current_run_user=$USER
if [ "${current_run_user}" != "root" ] 
then
    echo "本程序必须在root用户下执行"   
    exit 2
fi

#Tomcat项目名称
read -p '请输入运行Tomcat的项目名称[caocao-admins]:' -t 300 project_name
[ "${project_name}" == "" ]  && project_name=caocao-admins
echo "${project_name}"

#Tomcat项目端口号
read -p '请输入运行Tomcat的运行端口起始值x[2-9]:' -t 300 port_start
[ "${port_start}" == "" ]  && port_start=8
if [[ ! "${port_start}" =~ ^[2-9]$ ]]; then
    echo "Tomcat端口号超始值必须为一位的数字，比如2则端口号最终为2080"
    exit 5
fi
echo "${port_start}"

#创建相关目录
mkdir -p /opt/src/tomcat/ 2>/dev/null
mkdir -p /usr/local/tomcat/ 2>/dev/null
mkdir -p /logs/${project_name}/ 2>/dev/null
mkdir -p /home/admin/tomcat/${project_name}/webapps 2>/dev/null

#Tomcat日志catalina.log依赖cronolog来切割
wcount=$(rpm -qa | grep cronolog | grep -v grep | wc -l)
if [ ${wcount} eq 0 ]; then
    yum -y install cronolog
fi

#下载Tomcat软件
wget -T 5 -t 1 http://10.168.73.68:17878/download/apache-tomcat-7.0.62-caocao.tar.gz -O /tmp/apache-tomcat-7.0.62-caocao.tar.gz 2>&1 1>/dev/null
if [ $? -ne 0 ]; then
    echo "无法使用内网下载tomcat，改使用外网下载"
    wget http://121.40.204.194/download/apache-tomcat-7.0.62-caocao.tar.gz -O /tmp/apache-tomcat-7.0.62-caocao.tar.gz
fi

#解压并创建超链接
tar xzvf /tmp/apache-tomcat-7.0.62-caocao.tar.gz -C /opt/src/tomcat/
mv /opt/src/tomcat/apache-tomcat-7.0.62 /opt/src/tomcat/${project_name}
ln -svf /opt/src/tomcat/${project_name} /usr/local/tomcat/${project_name}

#获取外网IP
wip=$(/sbin/ip a | grep 'eth1:' -A 2 | tail -1 | awk '{print $2}' | sed 's#\(.*\)/.*#\1#g')

#修改Tomcat配置
sed -i "s/caocao-admins/${project_name}/g" /usr/local/tomcat/${project_name}/conf/server.xml
sed -i "s/[0-9]\([0-9]\{3\}\)/${port_start}\1/g" /usr/local/tomcat/${project_name}/conf/server.xml
sed -i "s/caocao-admins/${project_name}/g" /usr/local/tomcat/${project_name}/bin/setenv.sh
sed -i "s/caocao-admins/${project_name}/g" /usr/local/tomcat/${project_name}/bin/server.sh
if [ ! -z "${wip}" ]; then
    sed -i "s/121.41.30.185/${wip}/g" /usr/local/tomcat/${project_name}/bin/setenv.sh
fi
chmod u+x /usr/local/tomcat/${project_name}/bin/setenv.sh
chmod u+x /usr/local/tomcat/${project_name}/bin/server.sh
echo "alias ${project_name}=/usr/local/tomcat/${project_name}/bin/server.sh" >> /home/admin/.bashrc

chown -R admin:grpcaocao /opt/src/tomcat/
chown -R admin:grpcaocao /usr/local/tomcat/
chown -R admin:grpcaocao /logs
chown -R admin:grpcaocao /home/admin/tomcat

rm -rf /tmp/apache-tomcat-7.0.62-caocao.tar.gz
rm -rf /tmp/install_tomcat_root

echo "完成"
