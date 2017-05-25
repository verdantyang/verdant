#!/bin/bash

ng=$(ps -ef | grep nginx | grep -v update_nginx | grep -v grep | wc -l)
/usr/local/nginx/sbin/nginx -s stop 2>/dev/null
ps -ef | grep nginx | grep -v update_nginx |  grep -v grep | awk '{print $2}' | xargs kill -9 2>/dev/null
mv /usr/local/nginx /usr/local/nginx-1.9.5
wget http://10.168.73.68:17878/download/nginx-1.10.2.tar.gz -O /tmp/nginx-1.10.2.tar.gz
tar xzf /tmp/nginx-1.10.2.tar.gz -C /opt/src/
ln -sv /opt/src/nginx-1.10.2 /usr/local/nginx
mv /usr/local/nginx/conf/nginx.conf /usr/local/nginx/conf/nginx.bak
cp /usr/local/nginx-1.9.5/conf/nginx.conf /usr/local/nginx/conf/nginx.conf
chown -R admin:grpcaocao /opt/src/nginx-1.10.2
chown -R admin:grpcaocao /usr/local/nginx
/bin/rm -rf /tmp/nginx-1.10.2.tar.gz
if [ ${ng} -gt 0 ]; then
    /usr/local/nginx/sbin/nginx
else
    echo "nginx 不用启动"
fi
/bin/rm -rf /tmp/nginx-1.10.2.tar.gz
