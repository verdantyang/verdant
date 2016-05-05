#! /bin/bash
BASE_DIR="/opt/apps"
PROJ_DIR="tomcat8-admin-service"
PACKAGE="cgpboss-admin-service.war"

echo "$BASE_DIR/$PROJ_DIR"/bin/shutdown.sh
sh "$BASE_DIR/$PROJ_DIR"/bin/shutdown.sh

echo rm -fr "$BASE_DIR/$PROJ_DIR/webapps/ROOT/"
rm -fr "$BASE_DIR/$PROJ_DIR/webapps/ROOT/"

echo unzip "$BASE_DIR/$PACKAGE" -d "$BASE_DIR/$PROJ_DIR/webapps/ROOT/"
unzip "$BASE_DIR/$PACKAGE" -d "$BASE_DIR/$PROJ_DIR/webapps/ROOT/"

echo sh "$BASE_DIR/$PROJ_DIR"/bin/startup.sh
sh "$BASE_DIR/$PROJ_DIR"/bin/startup.sh