#!/bin/bash

# wget http://10.168.73.68/download/scripts/install_publish_root.sh -O /tmp/install_publish_root.sh && sh /tmp/install_publish_root.sh

#运行用户判断
current_run_user=$USER
if [ "${current_run_user}" != "root" ] 
then
    echo "本程序必须在root用户下执行"	
    exit 1
fi

#Git安装
echo "正在安装Git"
wget http://10.168.73.68/download/git_v2.4.0.tar.gz -O /tmp/git_v2.4.0.tar.gz
yum -y install gcc gcc-c++ zlib-devel curl-devel openssl-devel perl cpio expat-devel gettext-devel openssl zlib autoconf tk perl-ExtUtils-MakeMaker
tar xzvf /tmp/git_v2.4.0.tar.gz -C /opt/src/
cd /opt/src/git-2.4.0
autoconf
./configure --prefix=/usr/local/git
make && make install

#Git配置
echo "/usr/local/lib" >> /etc/ld.so.conf
ldconfig -v
ln -sfv /usr/local/git/bin/git-upload-pack /usr/bin/git-upload-pack
cp /opt/src/git-2.4.0/contrib/completion/git-completion.bash /etc/bash_completion.d/
. /etc/bash_completion.d/git-completion.bash

echo "export GIT_HOME=/usr/local/git" >> /etc/profile
echo "export PATH=\$PATH:\${GIT_HOME}/bin" >> /etc/profile
echo 'if [ -f /etc/bash_completion.d/git-completion.bash ]; then' >> /etc/profile
echo ' . /etc/bash_completion.d/git-completion.bash' >> /etc/profile
echo 'fi' >> /etc/profile
echo "Git安装完成"

#Maven 安装
wget http://10.168.73.68/download/apache-maven-3.3.3-bin.tar.gz -O /tmp/apache-maven-3.3.3-bin.tar.gz
tar xzf /tmp/apache-maven-3.3.3-bin.tar.gz -C /opt/src
ln -svf /opt/src/apache-maven-3.3.3 /usr/local/maven

echo "export M2_HOME=/usr/local/maven" >> /etc/profile
echo "export MAVEN_HOME=/usr/local/maven" >> /etc/profile
echo "export PATH=\$PATH:\${MAVEN_HOME}/bin" >> /etc/profile
source /etc/profile

#安装Jenkins
read -p '请输入运行jenkins的运行端口起始值x[5]:' -t 10 port_start
[ "${port_start}" == "" ]  && port_start=5
if [[ ! "${port_start}" =~ ^[0-9]$ ]]; then
    echo "jenkins端口号超始值必须为一位的数字，比如5则端口号最终为5080"
    exit 2
fi
echo "${port_start}"
project_name=jenkins

#创建相关目录
mkdir -p /opt/src/tomcat/ 2>/dev/null
mkdir -p /usr/local/tomcat/ 2>/dev/null

#下载Tomcat软件
wget http://10.168.73.68/download/apache-tomcat-7.0.62-caocao.tar.gz -O /tmp/apache-tomcat-7.0.62-caocao.tar.gz

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

#部署Jenkins
mkdir -p /home/admin/tomcat/${project_name}/webapps
wget http://10.168.73.68/download/test/jenkins-2.17.tar.gz -O /home/admin/tomcat/${project_name}/jenkins-2.17.tar.gz
tar xzvf /home/admin/tomcat/${project_name}/jenkins-2.17.tar.gz -C /home/admin/tomcat/${project_name}/
ln -svfT /home/admin/tomcat/${project_name}/jenkins-2.17 /home/admin/tomcat/${project_name}/webapps/jenkins

wget http://10.168.73.68/download/test/jenkins-home.tar.gz -O /tmp/jenkins-home.tar.gz
tar xzvf /tmp/jenkins-home.tar.gz -C /
echo "export JENKINS_HOME=/jenkins" >> /home/admin/.bash_profile

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
mkdir -p /workspace/{projects,publish,repository}
chown -R admin:grpcaocao /opt/src/git-2.4.0
chown -R admin:grpcaocao /usr/local/git
chown -R admin:grpcaocao /opt/src/apache-maven-3.3.3
chown -R admin:grpcaocao /usr/local/maven
chown -R admin:grpcaocao /workspace
chown -R admin:grpcaocao /home/admin/tomcat/
chown admin:grpcaocao /jenkins


echo "Jenkins安装完成，请在admin中使用jenkins start来启动"
echo "访问地址 http://${IP}:${port_start}080/jenkins，访问账号admin"
echo "完成"