#!/bin/bash

# wget http://10.168.73.68/download/scripts/install_zookeeper_root.sh -O /tmp/install_zookeeper_root.sh && sh /tmp/install_zookeeper_root.sh

#运行用户判断
current_run_user=$USER
if [ "${current_run_user}"x != "root"x ] 
then
    echo "本程序必须在root用户下执行"	
    exit 1
fi

#Zookeeper端口号指定
read -p '请输入Zookeeper客户端监听端口号[15311]:' -t 10 zk_client_port
[ "${zk_client_port}"x == ""x ]  && zk_client_port=15311
if [[ ! "${zk_client_port}" =~ ^[0-9]{4,}$ ]]; then
    echo "端口必须为4位以上数字"
    exit 2
fi
read -p '请输入Zookeeper内部通讯端口号[15312]:' -t 10 zk_peer_port
[ "${zk_peer_port}"x == ""x ]  && zk_peer_port=15312
if [[ ! "${zk_peer_port}" =~ ^[0-9]{4,}$ ]]; then
    echo "端口必须为4位以上数字"
    exit 3
fi
read -p '请输入Zookeeper选举端口号[15313]:' -t 10 zk_leader_port
[ "${zk_leader_port}"x == ""x ]  && zk_leader_port=15313
if [[ ! "${zk_leader_port}" =~ ^[0-9]{4,}$ ]]; then
    echo "端口必须为4位以上数字"
    exit 4
fi
echo "Zookeeper peer port=${zk_peer_port}, leader port=${zk_leader_port}, client port=${zk_client_port}"

#Zookeeper集群IP指定
read -p '请输入Zookeeper部署集群的节点IP, 使用逗号隔开:' -t 60 zk_cluster_ips
if [ "${zk_cluster_ips}"x == ""x ] ; then
	echo "Zookeeper集群IP不能为空"
	exit 5
fi
echo "zk_cluster_ips=[${zk_cluster_ips}]"

#Zookeeper集群中当前节点编号
read -p '请输入Zookeeper集群中当前节点编号，从1开始:' -t 10 zk_no
if [ "${zk_no}"x == ""x ] ; then
	echo "Zookeeper集群中当前节点编号不能为空"
	exit 6
fi
if [[ ! "${zk_no}" =~ ^[1-9]$ ]]; then
    echo "Zookeeper集群中当前节点编号为从1开始编号的1位数字"
    exit 7
fi

#Zookeeper数据目录
read -p '请输入Zookeeper数据目录[/usr/local/zookeeper]:' -t 10 zk_data_path
[ "${zk_data_path}" == "" ]  && zk_data_path=/usr/local/zookeeper
echo "${zk_data_path}"


#下载Tomcat软件
echo "当前正在下载zookeeper-3.4.8"
wget -T 5 -t 1 http://10.168.73.68:17878/download/zookeeper-3.4.8.tar.gz -O /tmp/zookeeper-3.4.8.tar.gz 2>/dev/null
if [ $? -ne 0 ]; then
    wget http://log.51caocao.cn/download/zookeeper-3.4.8.tar.gz -O /tmp/zookeeper-3.4.8.tar.gz
fi

#创建链接
mkdir -p /opt/src 2>/dev/null
tar xzvf /tmp/zookeeper-3.4.8.tar.gz -C /opt/src/
ln -sv /opt/src/zookeeper-3.4.8/ /usr/local/zookeeper
mkdir -p ${zk_data_path}/{data,logs} 2>/dev/null

#配置Zookeeper
echo "tickTime=2000"					> /usr/local/zookeeper/conf/zoo.cfg
echo "initLimit=10"						>> /usr/local/zookeeper/conf/zoo.cfg
echo "syncLimit=5"						>> /usr/local/zookeeper/conf/zoo.cfg
echo "dataDir=${zk_data_path}/data"		>> /usr/local/zookeeper/conf/zoo.cfg
echo "dataLogDir=${zk_data_path}/logs"	>> /usr/local/zookeeper/conf/zoo.cfg
echo "clientPort=${zk_client_port}"		>> /usr/local/zookeeper/conf/zoo.cfg
OLDIFS=${IFS};IFS=,
ips=(${zk_cluster_ips})
set -a i=0
for zkip in ${ips[*]}
do
	let i=i+1
	echo "server.${i}=${zkip}:${zk_peer_port}:${zk_leader_port}" >> /usr/local/zookeeper/conf/zoo.cfg
done
IFS=${OLDIFS}

#集群节点
echo "${zk_no}" > ${zk_data_path}/data/myid

#其它设置
echo 'alias zks="/usr/local/zookeeper/bin/zkServer.sh"' >> /home/admin/.bashrc
echo 'alias zkc="/usr/local/zookeeper/bin/zkCli.sh"' >> /home/admin/.bashrc
echo "export ZOO_LOG_DIR=${zk_data_path}/logs" >> /home/admin/.bash_profile
echo "export ZOOCFGDIR=/usr/local/zookeeper/conf" >> /home/admin/.bash_profile

chown -R admin:grpcaocao /opt/src/zookeeper-3.4.8
chown -R admin:grpcaocao /usr/local/zookeeper
chown -R admin:grpcaocao ${zk_data_path}

echo "配置完毕，请在admin下使用运行zks start|stop|status来操作zookeeper"

rm -rf /tmp/zookeeper-3.4.8.tar.gz
echo "完成"
