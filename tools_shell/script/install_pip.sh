#!/bin/bash

#运行用户判断
current_run_user=$USER
if [ "${current_run_user}" != "root" ] 
then
    echo "本程序必须在root用户下执行"   
    exit 1
fi

_install(){
    pkg=$1
    echo "正在安装包 ${pkg}"
    wget -T 5 -t 1 http://10.168.73.68:17878/download/python/${pkg}.tar.gz -O /tmp/${pkg}.tar.gz 2>/dev/null
    if [ $? -ne 0 ]; then
        wget http://121.40.204.194/download/python/${pkg}.tar.gz -O /tmp/${pkg}.tar.gz
    fi
    cd /tmp
    tar xzf ${pkg}.tar.gz
    cd ${pkg}
    python setup.py install
    cd /tmp
    [ $? -eq 0 ] && /bin/rm -rf /tmp/${pkg}.tar.gz && /bin/rm -rf /tmp/${pkg}
    echo "安装结束"
}

install_log=/tmp/install_pip.log
_install setuptools-23.0.0 | tee -a ${install_log}
_install wheel-0.29.0 | tee -a ${install_log}
_install pip-9.0.1 | tee -a ${install_log}
pip -V | tee -a ${install_log}

#更换淘宝pypi源
mkdir ~/.pip
echo '[global]' > ~/.pip/pip.conf
echo 'index-url=http://mirrors.aliyun.com/pypi/simple/' >> ~/.pip/pip.conf
echo '[install]' >> ~/.pip/pip.conf
echo 'trusted-host=mirrors.aliyun.com' >>  ~/.pip/pip.conf 
echo '[list]' >> ~/.pip/pip.conf
echo 'format=columns' >> ~/.pip/pip.conf
echo "本次安装结束，详细日志请见${install_log}"
