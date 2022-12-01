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

  run_app "$db_user_name" "$db_password"
}

run_app() {
  echo "Build JAR application first using mvn clean install"
  echo "Note: Remember to include spring.datasource.username and password fields in application.yml"
  read -rp "JAR file path: " jar_file_path
  if ! [ -f "$jar_file_path" ]; then
    echo "File $jar_file_path not exists. Exiting..."
    exit
  fi

  java -jar "$jar_file_path" --spring.datasource.username="$1" --spring.datasource.password="$2"
}

echo "-- DATABASE CONFIGURATION --"
echo "Note: Make sure you have Docker installed."

read -rp "Do you have database docker image? (y/n): " docker_installed
if [ "$docker_installed" == 'n' ]; then
  build_docker
elif [ "$docker_installed" == 'y' ]; then
  read -rp "Database username: " db_user_name
  read -rp "Database password: " db_password

  if ! [[ "$db_user_name" && "$db_password" ]]; then
    echo "Missing arguments. Exiting..."
    exit
  fi
  run_app "$db_user_name" "$db_password"
else
  echo "Wrong character: $docker_installed. Exiting..."
  exit
fi

