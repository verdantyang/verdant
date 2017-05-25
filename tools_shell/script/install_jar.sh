#!/bin/bash

# wget http://10.168.73.68:17878/download/scripts/install_jar.sh -O /tmp/install_jar.sh && sh /tmp/install_jar.sh
arg="$1"
base_fs_uri=http://10.168.73.68:17878/download

# 退出脚本
exit_script () {
    echo "$1"
    exit $2
}

# 存在输入参数时解析第1个输入参数以#拆分成多个参数
parse_args () {
    if [ ! -z "${arg}" ]; then
        declare -a args
        IFS='#' read -ra args <<< "${arg}"
        test ${#args[*]} -lt 3 && exit_script "使用#隔开的参数必须提供3个，分别为appname, mainclass, startupmessage" 1
        app_name="${args[0]}"
        main_class="${args[1]}"
        startup_message="${args[2]}"
    fi
}

# 提示用户输入
prompt_input () {
    # 提示用户输入应用名
    if [ -z "${app_name}" ]; then
        read -p '请输入运行的应用名称(跟包名一致): ' -t 60 app_name
    fi
    # 提示用户输入main_class
    if [ -z "${main_class}" ]; then
        read -p '请输入运行的Main Class: ' -t 60 main_class
    fi
    # 提示用户输入检测应用启动成功的消息文本
    if [ -z "${startup_message}" ]; then
        read -p '请输入检测应用启动成功的消息文本: ' -t 60 startup_message
    fi
}

# 检测必须参数值
check_args () {
    test -z "${app_name}" && exit_script "应用名不能为空" 2
    # echo "${app_name}" | grep -E -q '^[a-z]+$' || exit_script "应用名只能由小写字线组成" 3
    test -z "${main_class}" && exit_script "Java运行的Main Class不能为空" 2
    test -z "${startup_message}" && exit_script "应用启动成功文本不能为空" 2
}

# 检查应用安装路径权限
check_dir_privileges () {
    if [ "${USER}" == "root" ]; then
        test ! -d /opt/src/jar && mkdir -p /opt/src/jar && chown admin:grpcaocao /opt/src/jar
        test ! -d /usr/local/jar && mkdir -p /usr/local/jar && chown admin:grpcaocao /usr/local/jar
        test ! -d /logs && mkdir /logs && chown admin:grpcaocao /logs
        test ! -d /home/admin/jar && mkdir /home/admin/jar && chown admin:grpcaocao /home/admin/jar
        mkdir /home/admin/jar/${app_name} && chown admin:grpcaocao /home/admin/jar/${app_name}
        return 0
    elif [ "${USER}" == "admin" ]; then
        test ! -w /opt/src/jar && exit_script "没有目录/opt/src/jar的写权限" 4
        test ! -w /usr/local/jar && exit_script "没有目录/usr/local/jar的写权限" 4
        test ! -w /logs && exit_script "没有目录/logs的写权限" 4
        test ! -d /home/admin/jar && mkdir /home/admin/jar
        test ! -w /home/admin/jar && exit_script "没有目录/home/admin/jar的写权限" 4
        mkdir /home/admin/jar/${app_name}
    else
        exit_script "只能在root与admin下运行，其它用户无权运行" 5
    fi
}

#------------------------
# 下载包，如果下载失败直接退出脚本，返回错误码3
#------------------------
wget_soft () {
    test -f /tmp/jar-1.1.zip && return 0
    wget --connect-timeout=10 -t 1 ${base_fs_uri}/jar-1.1.zip -O /tmp/jar-1.1.zip 2>&1 1>/dev/null
    test $? -ne 0 && exit_script "安装软件jar-1.1.zip下载失败" 3
}


# 1. 解析参数（如果有输入参数）
parse_args
# 2. 提示用户输入（参数为空时）
prompt_input
# 3. 检查必须的参数是否为空
check_args
# 4. 应用不能重复安装
test -L /usr/local/jar/${app_name} && exit_script "应用已存在不能重复安装" 2
test -d /usr/local/jar/${app_name} && exit_script "发现目录/usr/local/jar/${app_name}已存在" 3
# 5. 检查目录写权限
check_dir_privileges
# 6. 下载软件
wget_soft

# 7. 解压并创建超链接
unzip /tmp/jar-1.1.zip -d /opt/src/jar
mv /opt/src/jar/jar-1.1 /opt/src/jar/${app_name}
ln -sv /opt/src/jar/${app_name} /usr/local/jar/${app_name}
mkdir /logs/${app_name}
chmod u+x /usr/local/jar/${app_name}/bin/*.sh

# 8. 替换main_class与startup_message
sed -i "s/JAR_MAIN_CLASS=.*/JAR_MAIN_CLASS=\"${main_class}\"/" /usr/local/jar/${app_name}/bin/server.sh
sed -i "s/JAR_STARTUP_MESSAGE=.*/JAR_STARTUP_MESSAGE=\"${startup_message}\"/" /usr/local/jar/${app_name}/bin/server.sh

# 9. 授权
test "${USER}"x == "root"x && chown -R admin:grpcaocao /opt/src/jar/${app_name}
test "${USER}"x == "root"x && chown -R admin:grpcaocao /usr/local/jar/${app_name}
test "${USER}"x == "root"x && chown -R admin:grpcaocao /logs/${app_name}

# 10. 创建别名
echo "alias ${app_name}=/usr/local/jar/${app_name}/bin/server.sh" >> /home/admin/.bashrc

echo "完成"
rm -rf /tmp/jar-1.1.zip
rm -rf /tmp/install_jar.sh