#!/bin/bash

#运行用户判断
current_run_user=$USER
if [ "${current_run_user}" != "admin" ] 
then
    echo "本程序必须在admin用户下执行"  
    exit 1
fi

#前提条件判断
if [ ! -w "/opt/src/redis" ]; then
    echo "请先创建/opt/src/redis文件夹并授权给admin"
    exit 2
fi

if [ ! -w "/usr/local/redis" ]; then
    echo "请先创建/usr/local/redis文件夹并授权给admin"
    exit 3
fi

if [ ! -w "/ccdata/redis" ]; then
    echo "请先创建/ccdata/redis文件夹并授权给admin"
    exit 4
fi

#Redis端口号指定
read -p '请输入Redis运行端口号[15389]:' -t 10 redis_port
[ "${redis_port}" == "" ]  && redis_port=15389
if [[ ! "${redis_port}" =~ ^[0-9]{4,}$ ]]; then
    echo "Redis端口必须为4位以上数字"
    exit 5
fi
echo "${redis_port}"

#Redis授权密码
read -p '请输入Redis运行授权密码[ccdis88]:' -t 10 redis_password
[ "${redis_password}" == "" ]  && redis_password=ccdis88
echo "${redis_password}"

#下载并安装Redis软件
wget http://10.168.73.68:17878/download/redis/redis-caocao-3.0.5.tar.gz -P /tmp/
tar xzvf /tmp/redis-caocao-3.0.5.tar.gz -C /opt/src/redis/
rm -rf /tmp/redis-caocao-3.0.5.tar.gz
cd /opt/src/redis/redis-3.0.5/
make
if [ $? -ne 0 ];then
    echo "make出错，脚本终止执行"
    exit 6
fi 
make test
if [ $? -ne 0 ];then
    echo "make test出错，脚本终止执行"
    exit 7
fi
make PREFIX=/usr/local/redis install
if [ $? -ne 0 ];then
    echo "make install出错，脚本终止执行"
    exit 8
else
    echo "Redis成功安装"
fi

#配置Redis
echo "export PATH=\$PATH:/usr/local/redis/bin" >> ~/.bash_profile
mkdir -p /ccdata/redis/{conf,var,logs,snapshot}
cp /opt/src/redis/redis-3.0.5/script/caocao.conf /ccdata/redis/conf/caocao.conf
cp /opt/src/redis/redis-3.0.5/script/server.sh /usr/local/redis/bin/server.sh
cp /opt/src/redis/redis-3.0.5/script/autostart.sh /usr/local/redis/bin/autostart.sh
sed -i "s/^port [0-9]\{1,\}.*$/port ${redis_port}/g" /ccdata/redis/conf/caocao.conf
sed -i "s/^requirepass.*$/requirepass ${redis_password}/g" /ccdata/redis/conf/caocao.conf
sed -i "s/^REDIS_PORT=[0-9]\{1,\}.*$/REDIS_PORT=${redis_port}/g" /usr/local/redis/bin/server.sh
sed -i "s/^REDIS_PASS=.*$/REDIS_PASS=${redis_password}/g" /usr/local/redis/bin/server.sh
echo "Redis配置完成"
echo "请使用root用户执行脚本 /usr/local/redis/bin/autostart.sh 将Redis添加到服务并开机自启动."

echo "操作完成。"
