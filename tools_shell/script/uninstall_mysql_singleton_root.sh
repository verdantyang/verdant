#!/bin/bash

#运行用户判断
current_run_user=$USER
if [ "${current_run_user}" != "root" ] 
then
    echo "本程序必须在root用户下执行"   
    exit 1
fi

#mysql数据根目录指定
read -p '请输入mysql运行的数据根目录[/usr/local/mysql/data]:' -t 60 mysql_base_data
mysql_base_data=${mysql_base_data:-/usr/local/mysql/data}

#删除相关文件
echo "正在删除相关的文件"
sed -i '/alias mysql=.*$/d' /home/admin/.bashrc
sed -i '/alias mysqlserver=.*$/d' /home/admin/.bashrc
sed -i '/export PATH=$PATH:\/usr\/local\/mysql\/bin.*$/d' /etc/profile
source /etc/profile
ps -ef | grep mysql | grep -v grep | egrep '(mysqld_safe|mysqld)' | awk '{print $2}' | xargs -I{} kill -9 {}
/bin/rm -rf ${mysql_base_data}
/bin/rm -rf /usr/local/mysql
/bin/rm -rf /opt/src/mysql-5.6.16-caocao
/bin/rm -rf /tmp/*.sh

echo "本程序可能会误停其它mysql数据库，如果有其它正在运行，请手工启动，谢谢！"
echo "卸载完成"
