#!/bin/bash

# Initiate user script
# Usage: ./initiate_user.sh email

EMAIL=$1

# Check if the email already exists
EMAIL_FOUND=false

while IFS=: read -r uuid email role password; do
  if [ "$email" == "$EMAIL" ]; then
    EMAIL_FOUND=true
    break
  fi
done < user-store.txt

if [ "$EMAIL_FOUND" = true ]; then
  echo "Error: Email already exists"
  exit 9
fi

# Generate UUID and initiate the user
UUID=$(uuidgen)
ROLE="patient"
PASSWORD=""

echo "$UUID:$EMAIL:$ROLE:$PASSWORD" >> user-store.txt
echo "User initiated with UUID: $UUID"
