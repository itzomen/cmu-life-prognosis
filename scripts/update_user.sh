#!/bin/bash

# Update user script
# Usage: ./update_user.sh email first_name last_name dob iso hiv diagnosis_date art art_date

EMAIL=$1
FIRST_NAME=$2
LAST_NAME=$3
DOB=$4
ISO=$5
HIV=$6
DIAGNOSIS_DATE=$7
ART=$8
ART_DATE=$9

EMAIL_FOUND=false

# Temporary file to store updated data
TEMP_FILE="user-store-temp.txt"

# Loop through the user-store.txt file
while IFS=: read -r uuid email role password fn ln dob iso hiv diagnosis_date art art_date; do
  if [ "$email" == "$EMAIL" ]; then
    EMAIL_FOUND=true
    # Update the user's information
    NEW_LINE="$uuid:$email:$role:$password:$FIRST_NAME:$LAST_NAME:$DOB:$ISO:$HIV:$DIAGNOSIS_DATE:$ART:$ART_DATE"
  else
    # Keep the existing line if the email doesn't match
    NEW_LINE="$uuid:$email:$role:$password:$fn:$ln:$dob:$iso:$hiv:$diagnosis_date:$art:$art_date"
  fi
  echo "$NEW_LINE" >> "$TEMP_FILE"
done < user-store.txt

if [ "$EMAIL_FOUND" = false ]; then
  # If email was not found, remove the temporary file and return an error
  rm "$TEMP_FILE"
  echo "Error: Email not found"
  exit 8
else
  # Replace the original file with the updated file
  mv "$TEMP_FILE" user-store.txt
  echo "User updated successfully for Email: $EMAIL"
  exit 0
fi