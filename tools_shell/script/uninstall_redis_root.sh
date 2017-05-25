#!/bin/bash

#运行用户判断
current_run_user=$USER
if [ "${current_run_user}" != "root" ] 
then
    echo "本程序必须在root用户下执行"   
    exit 1
fi

#Redis数据根目录指定
read -p '请输入Redis运行的数据根目录[/usr/local/redis/data]:' -t 60 redis_base_data
redis_base_data=${redis_base_data:-/usr/local/redis/data}

#删除相关文件
echo "正在删除相关的文件"
sed -i '/export PATH=$PATH:\/usr\/local\/redis\/bin.*$/d' /home/admin/.bash_profile
source /etc/profile
ps -ef | grep redis-server | grep -v grep | awk '{print $2}' | xargs -I{} kill -9 {}
/bin/rm -rf ${mysql_base_data}
/bin/rm -rf /usr/local/redis
/bin/rm -rf /opt/src/redis-3.0.5
/bin/rm -rf /tmp/*.sh

echo "卸载完成"
