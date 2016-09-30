#查看系统tcp连接中各个状态的连接数。
netstat -an | awk '/^tcp/ {++S[$NF]} END {for(a in S) print a, S[a]}'

#查看和本机23端口建立连接并状态在established的所有ip
netstat -an |grep 23 |grep ESTA |awk '{print$5 "\n"}' |awk 'BEGIN {FS=":"} {print $1 "\n"}' |sort |uniq

#输出每个ip的连接数，以及总的各个状态的连接数。
netstat -n | awk '/^tcp/ {n=split($(NF-1),array,":");if(n<=2)++S[array[(1)]];else++S[array[(4)]];++s[$NF];++N} END {for(a in S){printf("%-20s %s\n", a, S[a]);++I}printf("%-20s %s\n","TOTAL_IP",I);for(a in s) printf("%-20s %s\n",a, s[a]);printf("%-20s %s\n","TOTAL_LINK",N);}'
