#!/bin/bash

DUBBO_HOME=$(cd "$(dirname "$0")/.."; pwd)
export JAVA_HOME=/usr/local/java
export JRE_HOME=$JAVA_HOME/jre
export CLASSPATH=.:$JAVA_HOME/lib:$JRE_HOME/lib
export PATH=$PATH:${JAVA_HOME}/bin

#加载工具
if [ -f ${DUBBO_HOME}/bin/util.sh ]; then
    . ${DUBBO_HOME}/bin/util.sh
fi

JAVA_OPTS=" -Djava.awt.headless=true -Djava.net.preferIPv4Stack=true"
JAVA_DUMP_OPTS=" -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/home/admin"	
JAVA_MEM_OPTS="
	-server -Xms2g -Xmx2g -Xss512k -XX:PermSize=256m -XX:MaxPermSize=512m
	-XX:-UseGCOverheadLimit
	-XX:+DisableExplicitGC 
	-XX:+UseConcMarkSweepGC
	-XX:+CMSParallelRemarkEnabled
	-XX:+UseCMSCompactAtFullCollection
	-XX:+UseFastAccessorMethods
	-XX:+UseCMSInitiatingOccupancyOnly
	-XX:CMSInitiatingOccupancyFraction=70
	-XX:LargePageSizeInBytes=128m 
"
JAVA_DEBUG_OPTS="-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=15322,server=y,suspend=n"
JAVA_JMX_OPTS="
	-Dcom.sun.management.jmxremote.port=15331 
	-Dcom.sun.management.jmxremote.ssl=false 
	-Dcom.sun.management.jmxremote.authenticate=false
	-Djava.rmi.server.hostname=$(get_ip eth1)
"