#!/bin/bash
wget http://10.168.73.68:17878/download/zabbix-3.0.4.tar.gz

tar xzf zabbix-3.0.4.tar.gz -C /opt/src
cd /opt/src/zabbix-3.0.4/
./configure --prefix=/usr/local/zabbix --enable-agent
make && make install

mkdir /usr/local/zabbix/log
chown -R admin.grpcaocao /usr/local/zabbix

ip=$(/usr/bin/get_ip.sh eth0)

sed -i "s@Server=127.0.0.1@Server=10.117.69.148@g" /usr/local/zabbix/etc/zabbix_agentd.conf
sed -i "s@ServerActive=127.0.0.1@ServerActive=10.117.69.148:15378@g" /usr/local/zabbix/etc/zabbix_agentd.conf
sed -i "s@tmp/zabbix_agentd.log@usr/local/zabbix/log/zabbix_agentd.log@g" /usr/local/zabbix/etc/zabbix_agentd.conf
sed -i "s@^# UnsafeUserParameters=0@UnsafeUserParameters=1\n@g" /usr/local/zabbix/etc/zabbix_agentd.conf
sed -i "s@^# ListenPort=10050@ListenPort=15379\n@g" /usr/local/zabbix/etc/zabbix_agentd.conf
sed -i "s@Hostname=Zabbix server@Hostname=$ip@g" /usr/local/zabbix/etc/zabbix_agentd.conf
