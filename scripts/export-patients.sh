#!/bin/bash

# Export patients script
# Usage: ./export-patients.sh

OUTPUT_FILE="patients.csv"

# Write the CSV header
echo "UUID,Email,Role,First Name,Last Name,Date of Birth,Country,HIV Status,Diagnosis Date,ART Status,ART Date" > $OUTPUT_FILE

# Read from user-store.txt and write patient details to the CSV file
while IFS=: read -r uuid email role password fn ln dob country hiv diagnosis_date art art_date; do
  # Only include patients in the CSV file
  if [ "$role" == "patient" ]; then
    echo "$uuid,$email,$role,$fn,$ln,$dob,$country,$hiv,$diagnosis_date,$art,$art_date" >> $OUTPUT_FILE
  fi
done < user-store.txt

echo "Exported patient data to $OUTPUT_FILE"
