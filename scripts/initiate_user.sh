#!/bin/bash

# Initiate user script
# Usage: ./initiate_user.sh email

EMAIL=$1
UUID=$(uuidgen)
ROLE="patient"
PASSWORD=""

echo "$UUID:$EMAIL:$ROLE:$PASSWORD" >> user-store.txt
echo "User initiated with UUID: $UUID"
