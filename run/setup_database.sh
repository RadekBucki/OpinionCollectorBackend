#!/bin/bash
build_docker() {
  echo "-- DOCKER CONFIGURATION --"

  docker_image_name="opinion_collector_image"
  docker_container_name="opinion_collector_container"

  read -rp "Docker image name (default: $docker_image_name): " new_docker_image_name
  if [ "$new_docker_image_name" ]; then
    docker_image_name=new_docker_image_name
  fi
  docker build -t "$docker_image_name" .

  read -rp "Docker container image name (default: $docker_container_name): " new_docker_container_name
  if [ "$new_docker_container_name" ]; then
    docker_container_name=new_docker_image_name
  fi

  read -rp "Database username: " db_user_name
  read -rp "Database password: " db_password

  if ! [[ "$db_user_name" && "$db_password" ]]; then
    echo "Missing arguments. Exiting..."
    exit
  fi

  docker run -d --name "$docker_container_name" \
    -e POSTGRES_USER="$db_user_name" -e POSTGRES_PASSWORD="$db_password" \
    -p 5432:5432 "$docker_image_name"

  echo "-- DATABASE CONFIGURATION FINISHED --"
  echo "You can now build and run your app using given credentials."
}

echo "-- DATABASE CONFIGURATION --"
echo "Note: Make sure you have Docker installed."
build_docker
