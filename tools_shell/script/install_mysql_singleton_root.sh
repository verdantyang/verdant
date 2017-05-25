#!/bin/bash

#运行用户判断
current_run_user=$USER
if [ "${current_run_user}" != "root" ] 
then
    echo "本程序必须在root用户下执行"   
    exit 1
fi

#mysql端口号指定
read -p '请输入mysql运行端口号[15381]:' -t 10 mysql_port
[ "${mysql_port}" == "" ]  && mysql_port=15381
if [[ ! "${mysql_port}" =~ ^[0-9]{4,}$ ]]; then
    echo "MySQL端口必须为4位以上数字"
    exit 5
fi
echo "${mysql_port}"

#mysql数据根目录指定
read -p '请输入mysql运行的数据根目录[/usr/local/mysql/data]:' -t 60 mysql_base_data
mysql_base_data=${mysql_base_data:-/usr/local/mysql/data}

#安装依赖包
yum -y install libaio perl perl-CPAN

#下载mysql软件
wget -T 5 -t 1 http://10.168.73.68:17878/download/mysql-5.6.16-caocao.tar.gz -O /tmp/mysql-5.6.16-caocao.tar.gz 2>/dev/null
if [ $? -ne 0 ]; then
    wget http://121.40.204.194/download/mysql-5.6.16-caocao.tar.gz -O /tmp/mysql-5.6.16-caocao.tar.gz
fi
tar xzvf /tmp/mysql-5.6.16-caocao.tar.gz -C /opt/src/
ln -sv /opt/src/mysql-5.6.16-caocao /usr/local/mysql

#修改配置
mkdir -p ${mysql_base_data}/{data,logs}
cp /usr/local/mysql/support-files/caocao.cnf ${mysql_base_data}/my.cnf
sed -i "s/\(port\s*= \)[0-9]\{1,\}.*$/\1${mysql_port}/g" ${mysql_base_data}/my.cnf
sed -i "s#^basedir.*\$#basedir=/usr/local/mysql#g" ${mysql_base_data}/my.cnf
sed -i "s#/ccdata/mysql/mysql#${mysql_base_data}#g" ${mysql_base_data}/my.cnf
echo "export PATH=\$PATH:/usr/local/mysql/bin" >> /etc/profile
echo "alias mysql='/usr/local/mysql/bin/mysql --defaults-file=${mysql_base_data}/my.cnf'" >> /home/admin/.bashrc
echo "alias mysqlserver=/usr/local/mysql/support-files/caocao.server" >> /home/admin/.bashrc
sed -i "s#basedir=/usr/local/mysql/mysql#basedir=/usr/local/mysql#g" /usr/local/mysql/support-files/caocao.server
sed -i "s#datadir=/ccdata/mysql/mysql/data#datadir=${mysql_base_data}/data#g" /usr/local/mysql/support-files/caocao.server
sed -i "s#lockdir='/ccdata/mysql/mysql/lock'#lockdir='${mysql_base_data}/lock'#g" /usr/local/mysql/support-files/caocao.server
sed -i "s#mysqld_pid_file_path=/ccdata/mysql/mysql/mysql.pid#mysqld_pid_file_path=${mysql_base_data}/mysql.pid#g" /usr/local/mysql/support-files/caocao.server
sed -i "s#conf=/ccdata/mysql/mysql/caocao.cnf#conf=${mysql_base_data}/my.cnf#g" /usr/local/mysql/support-files/caocao.server
sed -i "s#^extra_args=.*\$#extra_args=\"-e ${mysql_base_data}/my.cnf\"#g" /usr/local/mysql/support-files/caocao.server
sed -i "s#\$bindir/mysqld_safe --defaults-file=/ccdata/mysql/mysql/caocao.cnf#\$bindir/mysqld_safe --defaults-file=${mysql_base_data}/my.cnf#g" /usr/local/mysql/support-files/caocao.server
sed -i "s#/ccdata/mysql/mysql#${mysql_base_data}#g" /usr/local/mysql/support-files/caocao.server

#初始化mysql
/usr/local/mysql/scripts/mysql_install_db --basedir=/usr/local/mysql --datadir=${mysql_base_data}/data --user=admin
[ $? -eq 0 ] && echo "数据库初始化完成"

#正在设置mysql文件
chown -R admin:grpcaocao /usr/local/mysql
chown -R admin:grpcaocao ${mysql_base_data}

#启动MySQL服务
echo "正在启动MySQL服务"
su admin /usr/local/mysql/support-files/caocao.server start

#初始化用户
echo "正在初始化MySQL用户"
sudo -H -u admin bash -c "/usr/local/mysql/bin/mysql --defaults-file=${mysql_base_data}/my.cnf -u root mysql < /usr/local/mysql/scripts/caocao_init.sql" 2>&1 >/dev/null
if [ $? -ne 0 ] ; then
    echo '用户初始化失败，请在admin用户下执行命令mysql -u root mysql < /usr/local/mysql/scripts/caocao_init.sql 来初始化'
fi

#删除临时文件
/bin/rm -rf /tmp/mysql-5.6.16-caocao.tar.gz

echo "mysql已经安装初始化完成，请使用admin用户来操作mysql"
echo "mysqlserver start|stop|restart|status 来启动停止mysql"
echo "初始化root密码为cao-mysql-cao"
echo "操作完成。"
