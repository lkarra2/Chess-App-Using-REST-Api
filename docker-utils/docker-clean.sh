#!/usr/bin/env bash
docker ps -a | grep Exit | cut -d ' ' -f 1 | xargs docker rm
remove=$(docker images -f "dangling=true" -q)
[ ! -z "$remove" ] && docker rmi -f $remove
