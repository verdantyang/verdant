#!/bin/bash
#Func: Archive the logs
#00 2 * * * sh /opt/apps/log_archive.sh

function init()
{
   if [ -n "$1" ];then
      TIME_Ymd=$1
   else
      TIME_Ymd=`date -d "5 day ago" +%Y%m%d`
   fi;
   
   TIME_Ym=${TIME_Ymd:0:6}
   TIME_md=${TIME_Ymd:4:4}
   
   MAX_SEQ=30
   BASE_DIR="/opt/data/tomcat/logs"
   ARCH_DIR="/opt/data/tomcat/logs"
   
   # PRJ_FOLDERS=("charging_service" "mobile_web" "portal_web")
   PRJ_FOLDERS=("admin_service" "engine_service" "admin_web" "mobile_web")
   LOG_TYPES=("framework" "monitor" "service")
}


function handle()
{
   if [ ! -e "$ARCH_DIR/$prj/$type" ];then	
      mkdir "$ARCH_DIR/$prj/$type"
   fi
   if [ ! -e "$ARCH_DIR/$prj/$type/$TIME_Ym" ];then
      mkdir "$ARCH_DIR/$prj/$type/$TIME_Ym"
   fi

   for((i=0; i<=$MAX_SEQ; i=i+1))
   do
      if [ -e "$BASE_DIR/$prj/$type""_$TIME_md""_$i.log" ];then
         bzip2 "$BASE_DIR/$prj/$type""_$TIME_md""_$i.log"
      fi
      if  [ -e "$BASE_DIR/$prj/$type""_$TIME_md""_$i.log.bz2" ];then
         mv "$BASE_DIR/$prj/$type""_$TIME_md""_$i.log.bz2" "$ARCH_DIR/$prj/$type/$TIME_Ym"
      fi
   done
}

function historyArch()
   for((i=5; i<=90; i=i+1))
   do
      TIME_Ymd=`date -d "$i day ago" +%Y%m%d`
      sh /opt/apps/log_archive.sh $TIME_Ymd
   done
}


init $1
for prj in ${PRJ_FOLDERS[*]} 
do
   for type in ${LOG_TYPES[*]}
   do
      handle
   done
done