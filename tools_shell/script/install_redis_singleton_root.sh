#!/bin/bash

# wget http://10.168.73.68/download/scripts/install_redis_singleton_root.sh -O /tmp/install_redis_singleton_root.sh && sh /tmp/install_redis_singleton_root.sh

#运行用户判断
current_run_user=$USER
if [ "${current_run_user}" != "root" ] 
then
    echo "本程序必须在root用户下执行"  
    exit 1
fi

#Redis端口号指定
read -p '请输入Redis运行端口号[15389]:' -t 10 redis_port
[ "${redis_port}" == "" ]  && redis_port=15389
if [[ ! "${redis_port}" =~ ^[0-9]{4,}$ ]]; then
    echo "Redis端口必须为4位以上数字"
    exit 2
fi
echo "${redis_port}"

#Redis授权密码
read -p '请输入Redis运行授权密码[ccdis88]:' -t 10 redis_password
[ "${redis_password}" == "" ]  && redis_password=ccdis88
echo "${redis_password}"

#Redis运行根目录
read -p '请输入Redis运行的数据根目录[/usr/local/redis/data]:' -t 60 redis_base_data
redis_base_data=${redis_base_data:-/usr/local/redis/data}

#创建相关文件夹
mkdir -p /opt/src 2>/dev/null
mkdir -p ${redis_base_data}/{conf,var,logs,snapshot} 2>/dev/null

#安装依赖包
yum -y install tcl

#下载并安装Redis软件
wget -T 5 -t 1 http://10.168.73.68:17878/download/redis/redis-caocao-3.0.5.tar.gz -O /tmp/redis-caocao-3.0.5.tar.gz
if [ $? -ne 0 ]; then
    wget http://log.51caocao.cn/download/redis/redis-caocao-3.0.5.tar.gz -O /tmp/redis-caocao-3.0.5.tar.gz
fi
tar xzvf /tmp/redis-caocao-3.0.5.tar.gz -C /opt/src/
cd /opt/src/redis-3.0.5/
make
if [ $? -ne 0 ];then
    echo "make出错，脚本终止执行"
    exit 3
fi 
make test
if [ $? -ne 0 ];then
    echo "make test出错，脚本终止执行"
    exit 4
fi
make PREFIX=/usr/local/redis install
if [ $? -ne 0 ];then
    echo "make install出错，脚本终止执行"
    exit 5
else
    echo "Redis成功安装"
fi

#配置Redis
echo "export PATH=\$PATH:/usr/local/redis/bin" >> /home/admin/.bash_profile
cp /opt/src/redis-3.0.5/script/caocao.conf ${redis_base_data}/conf/caocao.conf
sed -i "s/^port [0-9]\{1,\}.*\$/port ${redis_port}/g" ${redis_base_data}/conf/caocao.conf
sed -i "s/^requirepass.*\$/requirepass ${redis_password}/g" ${redis_base_data}/conf/caocao.conf
sed -i "s#/ccdata/redis#${redis_base_data}#g" ${redis_base_data}/conf/caocao.conf

cp /opt/src/redis-3.0.5/script/server.sh /usr/local/redis/bin/server.sh
sed -i "s/^REDIS_PORT=[0-9]\{1,\}.*\$/REDIS_PORT=${redis_port}/g" /usr/local/redis/bin/server.sh
sed -i "s/^REDIS_PASS=.*\$/REDIS_PASS=${redis_password}/g" /usr/local/redis/bin/server.sh
sed -i "s#^REDIS_DATA_PATH=.*\$#REDIS_DATA_PATH=${redis_base_data}#g" /usr/local/redis/bin/server.sh

cp /opt/src/redis-3.0.5/script/autostart.sh /usr/local/redis/bin/autostart.sh
sh /usr/local/redis/bin/autostart.sh

echo "redis已配置服务，请在admin用户下使用service redis start|stop|restart|status来运行"

#给admin授权
chown -R admin:grpcaocao /opt/src/redis-3.0.5
chown -R admin:grpcaocao /usr/local/redis
chown -R admin:grpcaocao ${redis_base_data}

#删除相关目录
/bin/rm -rf /tmp/redis-caocao-3.0.5.tar.gz
echo "操作完成。"
