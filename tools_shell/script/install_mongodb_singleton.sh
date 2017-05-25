#!/bin/bash

#运行用户判断
current_run_user=$USER
if [ "${current_run_user}" != "root" ] 
then
    echo "本程序必须在root用户下执行"  
    exit 1
fi

#MongoDB端口号指定
read -p '请输入MongoDB运行端口号[15317]:' -t 10 mongodb_port
[ "${mongodb_port}" == "" ]  && mongodb_port=15317
if [[ ! "${mongodb_port}" =~ ^[0-9]{4,}$ ]]; then
    echo "MongoDB端口必须为4位以上数字"
    exit 2
fi
echo "${mongodb_port}"

#MongoDB数据目录
read -p '请输入MongoDB数据目录[/ccdata/mongodb]:' -t 10 mongodb_data_path
[ "${mongodb_data_path}" == "" ]  && mongodb_data_path=/ccdata/mongodb
echo "${mongodb_data_path}"
mkdir -p ${mongodb_data_path}


#下载并安装MongoDB软件
wget http://10.168.73.68:17878/download/mongodb-caocao-3.2.4.tar.gz -P /tmp/
tar xzvf /tmp/mongodb-caocao-3.2.4.tar.gz -C /opt/src/
ln -sv /opt/src/mongodb-3.2.4 /usr/local/mongodb
rm -rf /tmp/mongodb-caocao-3.2.4.tar.gz

#配置MongoDB
mkdir ${mongodb_data_path}/{data,logs,conf,var}
echo "port=${mongodb_port}"							 > ${mongodb_data_path}/conf/mongodb.conf
echo "pidfilepath=${mongodb_data_path}/var/mongodb.pid"	>> ${mongodb_data_path}/conf/mongodb.conf
echo "dbpath=${mongodb_data_path}/data"				>> ${mongodb_data_path}/conf/mongodb.conf
echo "logpath=${mongodb_data_path}/logs/mongodb.log">> ${mongodb_data_path}/conf/mongodb.conf
echo "logappend=true"								>> ${mongodb_data_path}/conf/mongodb.conf
echo "fork=true"									>> ${mongodb_data_path}/conf/mongodb.conf
echo "auth=true"									>> ${mongodb_data_path}/conf/mongodb.conf
echo "nounixsocket=false"							>> ${mongodb_data_path}/conf/mongodb.conf
echo "unixSocketPrefix=${mongodb_data_path}/var"	>> ${mongodb_data_path}/conf/mongodb.conf
echo "noscripting=true"								>> ${mongodb_data_path}/conf/mongodb.conf
echo "maxConns=300"									>> ${mongodb_data_path}/conf/mongodb.conf

#修改MongoDB的owner
chown -R admin:grpcaocao /opt/src/mongodb-3.2.4
chown -R admin:grpcaocao /usr/local/mongodb
chown -R admin:grpcaocao ${mongodb_data_path}
echo "export PATH=\$PATH:/usr/local/mongodb/bin" >> /home/admin/.bash_profile

#配置开机自启动
sed -i "s#^PIDFile=.*#PIDFile=${mongodb_data_path}/var/mongodb.pid#g" /usr/local/mongodb/scripts/mongod.service
sed -i "s#^ExecStart=.*#ExecStart=/usr/local/mongodb/bin/mongod --quiet -f ${mongodb_data_path}/conf/mongodb.conf#g" /usr/local/mongodb/scripts/mongod.service
cp /usr/local/mongodb/scripts/mongod.service /usr/lib/systemd/system/mongod.service
systemctl enable mongod.service
systemctl daemon-reload

echo "操作完成。"
