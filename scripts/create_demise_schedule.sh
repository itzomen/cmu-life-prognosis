
#!/bin/bash

# Check if the correct number of arguments is provided
if [ "$#" -ne 2 ]; then
    echo "Usage: $0 <PATIENT_NAME> <YEARS_LEFT>"
    exit 1
fi

PATIENT_NAME=$1
YEARS_LEFT=$2
UUID=$(uuidgen)
DEMISE_DATE=$(date -d "+$YEARS_LEFT years" +"%Y%m%d")
ICS_FILE="${PATIENT_NAME}_demise_schedule.ics"

# Create the iCalendar file
cat <<EOL > "$ICS_FILE"
BEGIN:VCALENDAR
VERSION:2.0
PRODID:-//CMU//LPT 1.0//EN
CALSCALE:GREGORIAN
METHOD:PUBLISH
BEGIN:VEVENT
SUMMARY:Demise of $PATIENT_NAME
UID:$UUID
SEQUENCE:0
STATUS:CONFIRMED
TRANSP:TRANSPARENT
DTSTART:$DEMISE_DATE
DTEND:$(date -d "+$YEARS_LEFT years +1 day" +"%Y%m%d")
DTSTAMP:$(date +"%Y%m%dT%H%M%SZ")
DESCRIPTION:This event marks the expected date of demise for $PATIENT_NAME, based on current life expectancy.
END:VEVENT
END:VCALENDAR
EOL

echo "iCalendar file '$ICS_FILE' created successfully."
