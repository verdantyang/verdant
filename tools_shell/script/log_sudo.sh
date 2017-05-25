#/bin/bash
# 备份sudo授权中的log用户权限
sed -n '/^\s*log\s.*/p' /etc/sudoers > /etc/sudoers_log
sed -i '/^\s*log\s.*/d' /etc/sudoers
# 重新授权log用户以admin身份运行的命令行
echo '' >> /etc/sudoers
echo 'log ALL=(admin) NOPASSWD: /usr/local/java/bin/jstack,/usr/local/java/bin/jps,/usr/local/bin/jmap,/usr/local/java/bin/jstat' >> /etc/sudoers
# 创建log下上述命令别名
echo 'alias jstack="sudo -u admin /usr/local/java/bin/jstack"' >> /home/log/.bashrc
echo 'alias jps="sudo -u admin /usr/local/java/bin/jps"' >> /home/log/.bashrc
echo 'alias jstat="sudo -u admin /usr/local/java/bin/jstat"' >> /home/log/.bashrc
echo 'alias jmap="sudo -u admin /usr/local/java/bin/jmap"' >> /home/log/.bashrc
exit 0
