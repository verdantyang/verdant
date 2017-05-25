export JAVA_HOME=/usr/local/java
export JRE_HOME=$JAVA_HOME/jre
export CLASSPATH=.:$JAVA_HOME/lib:$JRE_HOME/lib
export CATALINA_BASE=/usr/local/tomcat/calarm-web
export CATALINA_HOME=/usr/local/tomcat/calarm-web
JAVA_OPTS="-server -Xms2g -Xmx2g -Xss512k -XX:PermSize=128m -XX:MaxPermSize=512m"
#JAVA_OPTS="-server -Xms4g -Xmx4g -Xss512k -XX:PermSize=256m -XX:MaxPermSize=512m"
JAVA_OPTS="${JAVA_OPTS} -XX:+UseConcMarkSweepGC"
JAVA_OPTS="${JAVA_OPTS} -XX:+CMSParallelRemarkEnabled"
JAVA_OPTS="${JAVA_OPTS} -XX:+UseCMSCompactAtFullCollection"
JAVA_OPTS="${JAVA_OPTS} -XX:+UseFastAccessorMethods"
JAVA_OPTS="${JAVA_OPTS} -XX:+UseCMSInitiatingOccupancyOnly"
JAVA_OPTS="${JAVA_OPTS} -XX:CMSInitiatingOccupancyFraction=70"
#JAVA_OPTS="${JAVA_OPTS} -Dcom.sun.management.jmxremote.port=15321"
#JAVA_OPTS="${JAVA_OPTS} -Dcom.sun.management.jmxremote.ssl=false"
#JAVA_OPTS="${JAVA_OPTS} -Dcom.sun.management.jmxremote.authenticate=false"
#JAVA_OPTS="${JAVA_OPTS} -Djava.rmi.server.hostname=114.55.235.187"
JAVA_OPTS="${JAVA_OPTS} -XX:-UseGCOverheadLimit -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/home/admin"
#JAVA_OPTS="${JAVA_OPTS} -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5888"
#JPDA_OPTS='-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005'
