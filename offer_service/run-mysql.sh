#!/bin/bash

# All environment variables are set in set-env-vars.sh or in Github actions

# remove previous instance of my sql
# If the container exists
if [ "$(docker ps -q -f name="$MYSQL_CONTAINER_NAME")" ]; then
  # If the container is running
  if [ "$(docker ps -q -f status=running -f name="$MYSQL_CONTAINER_NAME")" ]; then
    docker stop "$MYSQL_CONTAINER_NAME"
    echo "Stopped container $MYSQL_CONTAINER_NAME"
  fi
  docker rm "$MYSQL_CONTAINER_NAME"
  echo "Removed container $MYSQL_CONTAINER_NAME"
fi

#get mysql server running in port 6606
docker  run -d --name="$MYSQL_CONTAINER_NAME" --env="MYSQL_ROOT_PASSWORD=root" -p 6608:3306 --health-cmd='mysqladmin ping --silent'  mysql:5.7.29

#wait for mysql server to come up
echo "Waiting for MySQL container to come up..."
while [ $(docker inspect --format "{{json .State.Health.Status }}" "$MYSQL_CONTAINER_NAME") != "\"healthy\"" ];
  do printf "."
  sleep 1
done

## Copy the schema to the container
echo ""
echo "Copying schema to container"
docker cp setup-schema.sql "$MYSQL_CONTAINER_NAME":/setup-schema.sql

#executing the schema setup
echo "Creating schema in DB"
docker exec -i "$MYSQL_CONTAINER_NAME" \
mysql -uroot -proot \
-e "SET @MYSQL_DBNAME='$MYSQL_DBNAME';
SET @MYSQL_USER='$MYSQL_USER';
SET @MYSQL_PASSWORD='$MYSQL_PASSWORD';
source setup-schema.sql;"