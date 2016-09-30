#! /bin/bash
BASE_DIR="/opt/apps"
PROJ_DIR="tomcat8-admin-service"
BACK_DIR="back"
PACKAGE="cgpboss-admin-service.war"

TIME_YmdHMS=`date '+%Y%m%d%H%M%S'`

echo tar -zcvf "$BASE_DIR/$BACK_DIR/cdiamond_$TIME_YmdHMS".tar.gz "$BASE_DIR/$PROJ_DIR/webapps/"
tar -zcvf "$BASE_DIR/$BACK_DIR/cdiamond_$TIME_YmdHMS".tar.gz "$BASE_DIR/$PROJ_DIR/webapps/"

echo "$BASE_DIR/$PROJ_DIR"/bin/shutdown.sh
sh "$BASE_DIR/$PROJ_DIR"/bin/shutdown.sh

echo rm -fr "$BASE_DIR/$PROJ_DIR/webapps/ROOT/"
rm -fr "$BASE_DIR/$PROJ_DIR/webapps/ROOT/"

echo unzip "$BASE_DIR/$PACKAGE" -d "$BASE_DIR/$PROJ_DIR/webapps/ROOT/"
unzip "$BASE_DIR/$PACKAGE" -d "$BASE_DIR/$PROJ_DIR/webapps/ROOT/"

echo sh "$BASE_DIR/$PROJ_DIR"/bin/startup.sh
sh "$BASE_DIR/$PROJ_DIR"/bin/startup.sh