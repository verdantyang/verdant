#!/bin/bash

#运行用户判断
current_run_user=$USER
if [ "${current_run_user}" != "admin" ] 
then
    echo "本程序必须在admin用户下执行"	
    exit 1
fi

#前提条件判断
if [ ! -w "/opt/src/mysql" ]; then
    echo "admin用户需要拥有对/opt/src/mysql的写权限"
    exit 2
fi

if [ ! -w "/usr/local/mysql" ]; then
    echo "admin用户需要拥有对/usr/local/mysql的写权限"
    exit 3
fi

if [ ! -w "/ccdata/mysql" ]; then
    echo "admin用户需要拥有对/ccdata/mysql的写权限"
    exit 4
fi

#mysql端口号指定
read -p '请输入mysql运行端口号[15381]:' -t 10 mysql_port
[ "${mysql_port}" == "" ]  && mysql_port=15381
if [[ ! "${mysql_port}" =~ ^[0-9]{4,}$ ]]; then
    echo "MySQL端口必须为4位以上数字"
    exit 5
fi
echo "${mysql_port}"

#下载mysql软件
wget http://10.168.73.68/download/mysql-5.6.16-caocao.tar.gz -P /opt/src/mysql
tar xzvf /opt/src/mysql/mysql-5.6.16-caocao.tar.gz -C /opt/src/mysql/
ln -sv /opt/src/mysql/mysql-5.6.16-caocao /usr/local/mysql/mysql

#修改配置
mkdir -p /ccdata/mysql/mysql/logs
cp /usr/local/mysql/mysql/support-files/caocao.cnf /ccdata/mysql/mysql/caocao.cnf
sed -i "s/\(port\s*= \)[0-9]\{1,\}.*$/\1${mysql_port}/g" /ccdata/mysql/mysql/caocao.cnf 
echo "export PATH=\$PATH:/usr/local/mysql/mysql/bin" >> ~/.bash_profile
echo 'alias mysql="/usr/local/mysql/mysql/bin/mysql --defaults-file=/ccdata/mysql/mysql/caocao.cnf"' >> ~/.bashrc

#初始化mysql
/usr/local/mysql/mysql/scripts/mysql_install_db --basedir=/usr/local/mysql/mysql --datadir=/ccdata/mysql/mysql/data --user=admin
[ $? -eq 0 ] && echo "数据库初始化完成"

#启动MySQL服务
echo "正在启动MySQL服务"
/usr/local/mysql/mysql/support-files/caocao.server start

#初始化用户
echo "正在初始化MySQL用户"
/usr/local/mysql/mysql/bin/mysql --defaults-file=/ccdata/mysql/mysql/caocao.cnf -u root mysql < /usr/local/mysql/mysql/scripts/caocao_init.sql 

echo "操作完成。"
