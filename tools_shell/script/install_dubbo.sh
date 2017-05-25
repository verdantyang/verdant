#!/bin/bash

# wget http://10.168.73.68:17878/download/scripts/install_dubbo.sh -O /tmp/install_dubbo.sh && sh /tmp/install_dubbo.sh

#Dubbo项目名称
read -p '请输入运行Dubbo的项目名称[order]:' -t 10 project_name
project_name=${project_name:-order}


#运行用户判断
current_run_user=$USER
if [ "${current_run_user}" != "root" ] 
then
    echo "本程序必须在root用户下执行"   
    exit 1
fi

#创建相关目录
mkdir -p /opt/src/dubbo 2>/dev/null
mkdir -p /usr/local/dubbo 2>/dev/null
mkdir -p /home/admin/dubbo/${project_name} 2>/dev/null
mkdir -p /logs/${project_name} 2>/dev/null

#下载Dubbo软件
wget -T 5 -t 1 http://10.168.73.68:17878/download/dubbo-caocao-1.0.zip -O /tmp/dubbo-1.0.zip 2>&1 1>/dev/null
if [ $? -ne 0 ]; then
    echo "无法使用内网下载tomcat，改使用外网下载"
    wget http://121.40.204.194/download/dubbo-caocao-1.0.zip -O /tmp/dubbo-1.0.zip
fi

#解压并创建超链接
cd /opt/src/dubbo/
unzip /tmp/dubbo-1.0.zip -d /opt/src/dubbo
mv /opt/src/dubbo/dubbo-1.0 /opt/src/dubbo/${project_name}
ln -sv /opt/src/dubbo/${project_name} /usr/local/dubbo/${project_name}
chmod u+x /usr/local/dubbo/${project_name}/bin/*.sh
echo "alias ${project_name}=/usr/local/dubbo/${project_name}/bin/server.sh" >> /home/admin/.bashrc

#改变目录属主
chown -R admin:grpcaocao /opt/src/dubbo
chown -R admin:grpcaocao /usr/local/dubbo
chown -R admin:grpcaocao /home/admin/dubbo
chown -R admin:grpcaocao /logs

echo "完成"
rm -rf /tmp/dubbo-1.0.zip
rm -rf /tmp/install_dubbo.sh
