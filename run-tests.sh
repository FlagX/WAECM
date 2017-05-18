#!/usr/bin/env bash
group6_ip="$(docker inspect group6 | jq '.[].NetworkSettings.IPAddress')"
docker run --network=abc -v /home/travis/.m2:/root/.m2 --name group6-test -e "group6_ip=$group6_ip" waecm-bsp1 test
