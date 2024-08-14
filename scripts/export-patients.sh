#!/bin/bash

# Export patients script
# Usage: ./export-patients.sh

OUTPUT_FILE="patients.csv"
PDF_FILE="patients.pdf"

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

# create a temporary csv that for pdf export
echo "Email,First Name,Last Name,Date of Birth,ISO,HIV,Diagnosis Date,ART,ART Date" > temp.csv
while IFS=: read -r uuid email role password fn ln dob country hiv diagnosis_date art art_date; do
  # Only include patients in the CSV file
  if [ "$role" == "patient" ]; then
    echo "$email,$fn,$ln,$dob,$country,$hiv,$diagnosis_date,$art,$art_date" >> temp.csv
  fi
done < user-store.txt

# check pandoc is installed, if yes, convert the csv to pdf
if command -v pandoc &> /dev/null
then
  pandoc -o $PDF_FILE temp.csv --metadata-file=metadata.yaml
  echo "Exported patient data to $PDF_FILE"
else
  echo "Pandoc is not installed. Please install pandoc to convert the csv to pdf"
fi
