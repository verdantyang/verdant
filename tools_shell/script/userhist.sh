#!/bin/bash

#创建历史文件目录
mkdir /var/history

#授权
chmod 777 /var/history
chmod a+t /var/history

#修改配置文件
echo 'if [ $UID -ge 500 ]; then'                        >> /etc/profile
echo '    readonly HISTFILE=/var/history/$USER-$UID.log'>> /etc/profile
echo '    readonly HISTFILESIZE=50000'                  >> /etc/profile
echo '    readonly HISTSIZE=10000'                      >> /etc/profile
echo '    readonly HISTTIMEFORMAT="%Y-%m-%d %H:%M:%S "' >> /etc/profile
echo '    readonly HISTCONTROL=ignoredups'              >> /etc/profile
echo '    shopt -s histappend'                          >> /etc/profile
echo '    readonly PROMPT_COMMAND="history -a"'         >> /etc/profile
echo 'fi'                                               >> /etc/profile
echo done
