#!/bin/bash

BASE_URL="http://localhost:8080"

USERNAME="user"
PASSWORD="qwertyui"

echo "Requesting JWT..."

LOGIN_RESPONSE=$(curl -s -X POST "$BASE_URL/v1/api/auth/login" \
  -H "Content-Type: application/json" \
  -H "Accept: application/json" \
  --data "{\"username\":\"$USERNAME\",\"password\":\"$PASSWORD\"}")

echo "Login response: $LOGIN_RESPONSE"

# Extract token (expects JSON like: {"token":"...."})
JWT=$(echo "$LOGIN_RESPONSE" | jq -r '.body.token')

if [ -z "$JWT" ]; then
  echo "Failed to retrieve JWT"
  exit 1
fi

echo "JWT received: $JWT"

echo "Create brand"
curl -vv -X POST "$BASE_URL/v1/api/brands" \
  -H "Authorization: Bearer $JWT" \
  -H "Content-Type: application/json" \
  -H "Accept: application/json" \
  --data '{"name": "Renault"}'

echo ""
echo "Create car"
curl -vv -X POST "$BASE_URL/v1/api/cars" \
  -H "Authorization: Bearer $JWT" \
  -H "Content-Type: application/json" \
  -H "Accept: application/json" \
  --data '{"brand": "Renault", "model": "laguna", "owner": "test-owner", "license": "1111-T"}'
  
echo ""
echo "Get cars"
curl -vv "$BASE_URL/v1/api/cars" \
  -H "Authorization: Bearer $JWT" \
  -H "Accept: application/json" \
