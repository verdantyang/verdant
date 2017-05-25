#!/bin/bash

# wget http://10.168.73.68/download/scripts/install_dubbo_admin_root.sh -O /tmp/install_dubbo_admin_root.sh && sh /tmp/install_dubbo_admin_root.sh

#运行用户判断
current_run_user=$USER
if [ "${current_run_user}" != "root" ] 
then
    echo "本程序必须在root用户下执行"	
    exit 1
fi

#Tomcat项目端口号
read -p '请输入运行Dubbo Admin的运行端口起始值x[6]:' -t 10 port_start
[ "${port_start}" == "" ]  && port_start=6
if [[ ! "${port_start}" =~ ^[0-9]$ ]]; then
    echo "Dubbo Admin端口号超始值必须为一位的数字，比如6则端口号最终为6080"
    exit 2
fi
echo "${port_start}"

#Zookeeper集群IP设置
echo "请输入Zookeeper集群IP及客户端口，格式如：192.168.1.2:15311,192.168.1.3:15311"
read -p 'zookeeper ips: ' -t 60 zk_ips
if [ -z "${zk_ips}" ] ; then
    echo "Zookeeper集群IP及客户端口不能为空"
    exit 3
fi
if [[ ! "${zk_ips}" =~ ^(([0-9]{1,3}\.){3}[0-9]{1,3}:[0-9]{3,}|,[^$]){1,}$ ]]; then
    echo "Zookeeper集群IP及客户端口格式不正确"
    exit 4
fi
echo "zk_ips=[${zk_ips}]"

#拼接成dubbo的zookeeper地址
OLDIFS=${IFS};IFS=,
arr_zk_ips=(${zk_ips})
primary_zk_ip=${arr_zk_ips[0]}
count=${#arr_zk_ips[*]}
for ((i=1;i<count;i++))
do
    backup_zk_ip="${backup_zk_ip},${arr_zk_ips[$i]}"
done
[ -n "${backup_zk_ip}" ] && backup_zk_ip=${backup_zk_ip#,*}
IFS=${OLDIFS}

if [ -n "${backup_zk_ip}" ] ; then
	zk_address="zookeeper://${primary_zk_ip}?backup=${backup_zk_ip}"
else
	zk_address="zookeeper://${primary_zk_ip}"
fi


#Tomcat项目名称
project_name=dubbo-admin

#创建相关目录
mkdir -p /opt/src/tomcat/ 2>/dev/null
mkdir -p /usr/local/tomcat/ 2>/dev/null

#下载Tomcat软件
wget http://10.168.73.68:17878/download/apache-tomcat-7.0.62-caocao.tar.gz -O /tmp/apache-tomcat-7.0.62-caocao.tar.gz

#解压并创建超链接
tar xzvf /tmp/apache-tomcat-7.0.62-caocao.tar.gz -C /opt/src/tomcat/
mv /opt/src/tomcat/apache-tomcat-7.0.62 /opt/src/tomcat/${project_name}
ln -svf /opt/src/tomcat/${project_name} /usr/local/tomcat/${project_name}

#修改Tomcat配置
sed -i "s/caocao-admins/${project_name}/g" /usr/local/tomcat/${project_name}/conf/server.xml
sed -i "s/[0-9]\([0-9]\{3\}\)/${port_start}\1/g" /usr/local/tomcat/${project_name}/conf/server.xml
sed -i "s/caocao-admins/${project_name}/g" /usr/local/tomcat/${project_name}/bin/setenv.sh
sed -i "s/caocao-admins/${project_name}/g" /usr/local/tomcat/${project_name}/bin/server.sh
chmod u+x /usr/local/tomcat/${project_name}/bin/setenv.sh
chmod u+x /usr/local/tomcat/${project_name}/bin/server.sh
echo "alias ${project_name}=/usr/local/tomcat/${project_name}/bin/server.sh" >> /home/admin/.bashrc

#下载并部署dubbo-admin
wget http://10.168.73.68:17878/download/dubbo-admin-2.8.4.war -O /tmp/dubbo-admin-2.8.4.war
mkdir -p /usr/local/tomcat/${project_name}/webapps/ROOT
unzip /tmp/dubbo-admin-2.8.4.war -d /usr/local/tomcat/${project_name}/webapps/ROOT/
sed -i 's/^\(.*\) appBase=.*$/\1 appBase="webapps"/g' /usr/local/tomcat/${project_name}/conf/server.xml
/bin/rm -rf /usr/local/tomcat/${project_name}/webapps/ROOT/index.html

dubbo_properties=/usr/local/tomcat/${project_name}/webapps/ROOT/WEB-INF/dubbo.properties
echo "dubbo.registry.address=${zk_address}" > ${dubbo_properties}
echo "dubbo.admin.root.password=admin_caocao1818" >> ${dubbo_properties}
echo "dubbo.admin.guest.password=du-guest-caocao" >> ${dubbo_properties}

rm -rf /tmp/apache-tomcat-7.0.62-caocao.tar.gz
rm -rf /tmp/dubbo-admin-2.8.4.war

#获取外网IP
OS_VER=$(lsb_release -sr | cut -d "." -f1)
if [ ${OS_VER} -eq 6 ]; then
    IP=$(/sbin/ifconfig eth1 | grep 'inet' | awk '{print $2}' | cut -d ':' -f2)
else
    IP=$(/usr/sbin/ifconfig eth1 | grep 'inet' | awk '{print $2}')
fi

#授权
chown -R admin:grpcaocao /opt/src/tomcat/
chown -R admin:grpcaocao /usr/local/tomcat/

echo "DubboAdmin安装完成，请在admin中使用dubbo-admin start来启动"
echo "访问地址 http://${IP}:${port_start}080/，访问账号root/admin_caocao1818"
echo "完成"
