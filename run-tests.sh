#!/usr/bin/env bash
group6_ip="$(cat test.txt | jq '.[0].Containers[] | select(.Name=="group6") | .IPv4Address')"
group6_ip=${group6_ip/\/16}
docker run --network=abc -v /home/travis/.m2:/root/.m2 --name group6-test -e "group6_ip=$group6_ip" waecm-bsp1 test
