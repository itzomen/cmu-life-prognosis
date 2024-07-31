#!/bin/bash

# Login script
# Usage: ./login.sh email password

EMAIL=$1
PASSWORD=$2
HASHED_PASSWORD=$(echo -n "$PASSWORD" | sha256sum | awk '{print $1}')

while IFS=: read -r uuid email role stored_password fn ln dob hiv diagnosis_date art art_date; do
  if [ "$email" == "$EMAIL" ] && [ "$stored_password" == "$HASHED_PASSWORD" ]; then
    echo "Login successful for email: $EMAIL"
    echo "User details: $uuid:$email:$role:$stored_password:$fn:$ln:$dob:$hiv:$diagnosis_date:$art:$art_date"
    exit 0
  fi
done < user-store.txt

echo "Login failed for email: $EMAIL"
exit 1
