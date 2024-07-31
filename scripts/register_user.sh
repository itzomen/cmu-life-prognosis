#!/bin/bash

# Register user script
# Usage: ./register_user.sh uuid password first_name last_name dob iso hiv diagnosis_date art art_date

UUID=$1
PASSWORD=$2
HASHED_PASSWORD=$(echo -n "$PASSWORD" | sha256sum | awk '{print $1}')
FIRST_NAME=$3
LAST_NAME=$4
DOB=$5
ISO=$6
HIV=$7
DIAGNOSIS_DATE=$8
ART=$9
ART_DATE=$10

UUID_FOUND=false

while IFS=: read -r uuid email role stored_password fn ln dob iso hiv diagnosis_date art art_date; do
  if [ "$uuid" == "$UUID" ]; then
    UUID_FOUND=true
    NEW_LINE="$UUID:$email:$role:$HASHED_PASSWORD:$FIRST_NAME:$LAST_NAME:$DOB:$ISO:$HIV:$DIAGNOSIS_DATE:$ART:$ART_DATE"
  else
    NEW_LINE="$uuid:$email:$role:$stored_password:$fn:$ln:$dob:$iso:$hiv:$diagnosis_date:$art:$art_date"
  fi
  echo "$NEW_LINE" >> user-store-temp.txt
done < user-store.txt

if [ "$UUID_FOUND" = false ]; then
  rm user-store-temp.txt
  echo "Error: UUID not found"
  exit 1
else
  mv user-store-temp.txt user-store.txt
  echo "User registration/update completed for UUID: $UUID"
  exit 0
fi
