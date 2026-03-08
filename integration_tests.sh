#!/bin/bash

BASE_URL="http://localhost:8080"
AUTH_URL="http://localhost:8081"

USERNAME="user"
PASSWORD="qwertyui"

echo "Requesting JWT..."

LOGIN_RESPONSE=$(curl -s -X POST "$AUTH_URL/v1/api/auth/login" \
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
  --data '{"brand": "Renault", "model": "laguna"}'
  
echo ""
echo "Get cars"
curl -vv "$BASE_URL/v1/api/cars?page=0&size=10" \
  -H "Authorization: Bearer $JWT" \
  -H "Accept: application/json"
  
  
echo ""
echo "Get car with id 1"
curl -vv "$BASE_URL/v1/api/cars/1" \
  -H "Authorization: Bearer $JWT" \
  -H "Accept: application/json"

  
echo ""
echo "Update car"
curl -vv -X PUT "$BASE_URL/v1/api/cars/1" \
  -H "Authorization: Bearer $JWT" \
  -H "Content-Type: application/json" \
  -H "Accept: application/json" \
  --data '{"id": "1", "brand": "Renault", "model": "laguna2", "owner": "test-owner2", "license": "1111-X"}'

  
echo ""
echo "Check updated entity"
curl "$BASE_URL/v1/api/cars/1" \
  -H "Authorization: Bearer $JWT" \
  -H "Accept: application/json"
  
echo ""
echo "Partial Update car"
curl -vv -X PATCH "$BASE_URL/v1/api/cars/1" \
  -H "Authorization: Bearer $JWT" \
  -H "Content-Type: application/json" \
  -H "Accept: application/json" \
  --data '{"license": "1111-Y"}'

echo ""
echo "Check partial updated entity"
curl "$BASE_URL/v1/api/cars/1" \
  -H "Authorization: Bearer $JWT" \
  -H "Accept: application/json"
  
echo ""
echo "Delete car"
curl -vv -X DELETE "$BASE_URL/v1/api/cars/1" \
  -H "Authorization: Bearer $JWT" \
  -H "Content-Type: application/json" \
  -H "Accept: application/json"  

echo ""
echo "Check deleted entity"
curl "$BASE_URL/v1/api/cars/1" \
  -H "Authorization: Bearer $JWT" \
  -H "Accept: application/json"

echo
echo "Integration tests finished..."
