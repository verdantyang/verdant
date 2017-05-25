#!/bin/bash
ng=$(ps -ef | grep nginx | grep -v grep | wc -l)
sudo -u root /usr/local/nginx/sbin/nginx -s stop 2>/dev/null
ps -ef | grep nginx | grep -v grep | awk '{print $2}' | xargs kill -9 2>/dev/null
mv /usr/local/nginx/conf/nginx.conf /usr/local/nginx/conf/nginx.conf.bak
mv /usr/local/nginx/conf/nginx.conf.account.pre /usr/local/nginx/conf/nginx.conf
if [ ${ng} -gt 0 ]; then
    sudo -u root /usr/local/nginx/sbin/nginx
else
    echo "nginx 不启动"
fi
/bin/rm -f /tmp/update-nginx-conf.sh
    
