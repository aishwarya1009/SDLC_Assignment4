#!/bin/bash

# do image build
# All environment variables are set in set-env-vars.sh or Github actions
docker build --add-host="$MYSQL_CONTAINER_NAME":"$HOST_IP" \
  --build-arg PROJECT_NAME="$PROJECT_NAME" \
  --build-arg PORT="$PORT" \
  --build-arg MYSQL_URL="$MYSQL_URL" \
  --build-arg MYSQL_USER="$MYSQL_USER" \
  --build-arg MYSQL_PASSWORD="$MYSQL_PASSWORD" \
  --rm -f "Dockerfile" \
  -t "$IMAGE_NAME":$IMAGE_VERSION \
  -t "$IMAGE_NAME":latest \
  -t $IMAGE_TAG_LATEST \
  -t $IMAGE_TAG_VERSION \
  "."

# remove intermediate builds
# shellcheck disable=SC2046
docker image rm $(docker images -f "dangling=true" -q)
