#!/usr/bin/env bash
group6_ip="$(docker inspect group6 | jq '.[].NetworkSettings.Networks.abc.IPAddress')"
chromedriver_ip="$(docker inspect chromedriver | jq '.[].NetworkSettings.Networks.abc.IPAddress')"
docker run --network=abc -v /home/travis/.m2:/root/.m2 --name group6-test -e "group6_ip=$group6_ip" -e "chromedriver_ip=$chromedriver_ip" waecm-bsp1 test
