#!/bin/bash

# Register user script
# Usage: ./register_user.sh uuid password first_name last_name dob hiv diagnosis_date art art_date

UUID=$1
PASSWORD=$2
HASHED_PASSWORD=$(echo -n "$PASSWORD" | sha256sum | awk '{print $1}')
FIRST_NAME=$3
LAST_NAME=$4
DOB=$5
HIV=$6
DIAGNOSIS_DATE=$7
ART=$8
ART_DATE=$9

while IFS=: read -r uuid email role; do
  if [ "$uuid" == "$UUID" ]; then
    NEW_LINE="$UUID:$email:$role:$HASHED_PASSWORD:$FIRST_NAME:$LAST_NAME:$DOB:$HIV:$DIAGNOSIS_DATE:$ART:$ART_DATE"
  else
    NEW_LINE="$uuid:$email:$role"
  fi
  echo "$NEW_LINE" >> user-store-temp.txt
done < user-store.txt

mv user-store-temp.txt user-store.txt
echo "User registration completed for UUID: $UUID"
