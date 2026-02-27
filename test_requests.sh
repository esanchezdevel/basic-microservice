#!/bin/bash

jwt="$1"

echo "JWT received $jwt"

echo "Create brand"
curl -vv -X POST -u "user:$jwt" http://localhost:8080/v1/api/brands -H 'Content-Type: application/json' -H 'Accept: application/json' --data '{"name": "Renault"}'

echo "Create car"
curl -vv -X POST -u "user:$jwt" http://localhost:8080/v1/api/cars -H 'Content-Type: application/json' -H 'Accept: application/json' --data '{"brand": "Renault", "model": "laguna", "owner": "test-owner", "license": "1111-T"}'
