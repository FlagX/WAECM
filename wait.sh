#!/usr/bin/env bash
until $(curl --insecure --output /dev/null --silent --head --fail https://localhost:8080/login.html); do
  printf '.';
  sleep 5;
done
