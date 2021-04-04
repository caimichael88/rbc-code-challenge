#!/usr/bin/env bash

NETWORK_EXISTS=$(docker network ls | grep docker-network | awk '{print $1}')

if [ -z "$NETWORK_EXISTS" ]; then
    docker network create docker-network --subnet 192.168.0.240/24
fi

docker-compose up -d

