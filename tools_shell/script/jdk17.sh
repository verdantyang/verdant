#!/bin/bash

# wget http://log.51caocao.cn:17878/download/scripts/jdk17.sh -O /tmp/jdk17.sh && sh /tmp/jdk17.sh

#运行用户判断
current_run_user=$USER
if [ "${current_run_user}" != "root" ] 
then
    echo "本程序必须在root用户下执行"  
    exit 1
fi

#创建相关文件夹
mkdir -p /opt/src 2>/dev/null

#下载并安装JDK
echo "正在下载jdk-7u75-linux-x64.tar.gz"
wget -T 5 -t 1 http://10.168.73.68:17878/download/jdk-7u75-linux-x64.tar.gz -O /tmp/jdk-7u75-linux-x64.tar.gz 2>/dev/null
if [ $? -ne 0 ]; then
    wget http://log.51caocao.cn/download/jdk-7u75-linux-x64.tar.gz -O /tmp/jdk-7u75-linux-x64.tar.gz
fi
echo "正在安装中..."
tar xzvf /tmp/jdk-7u75-linux-x64.tar.gz -C /opt/src/
ln -sv /opt/src/jdk1.7.0_75/ /usr/local/java

#配置Java环境变量
echo "" >> /etc/profile
echo "#Java环境变量" >> /etc/profile
echo "export JAVA_HOME=/usr/local/java" >> /etc/profile
echo "export JRE_HOME=\$JAVA_HOME/jre" >> /etc/profile
echo "export CLASSPATH=.:\$JAVA_HOME/lib:\$JRE_HOME/lib" >> /etc/profile
echo "export PATH=\$PATH:\$JAVA_HOME/bin" >> /etc/profile

#输出Java版本号及安装退出
. /etc/profile 
java -version
/bin/rm -rf /tmp/jdk-7u75-linux-x64.tar.gz
/bin/rm -rf /tmp/jdk17.sh
echo "操作完成。"
