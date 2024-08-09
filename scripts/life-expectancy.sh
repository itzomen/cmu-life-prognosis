#!/bin/bash

# Path to the CSV file
life_expectancy_csv="life-expectancy.csv"
user_store="user-store.txt"

compute_remaining_lifespan() {
    local country_iso=$1
    local age=$2
    local hiv_positive=$3
    local diagnosis_date=$4
    local art_start_status=$5
    local art_start_year=$6

    local life_expectancy=$(awk -F ',' -v iso="$country_iso" '$4 == iso {print $7}' "$life_expectancy_csv")
    # echo "Debug: life_expectancy=$life_expectancy"

    if [[ "$hiv_positive" == "true" ]]; then
        local diagnosis_year=$(echo "$diagnosis_date" | cut -d '/' -f3)
        local delay_years=0
        if [[ "$art_start_status" == "true" ]]; then
            if [[ "$art_start_year" != "false" && -n "$art_start_year" ]]; then
                local art_year=$(echo "$art_start_year" | cut -d '/' -f3)
                delay_years=$(($art_year - $diagnosis_year))
            fi
            local remaining_years=$(echo "$life_expectancy - $age" | bc)
            local life_with_art=$(echo "scale=2; $remaining_years * 0.9 * (0.9^$delay_years)" | bc)
            local rounded_life=$(echo "$life_with_art" | awk '{print int($1+0.5)}')
            echo $rounded_life
        else
            local remaining_years=$(echo "$life_expectancy - $age" | bc)
            local reduced_life=$(echo "scale=2; $remaining_years - 5" | bc)
            local rounded_life=$(echo "$reduced_life" | awk '{print int($1+0.5)}')
            if [ "$rounded_life" -lt 0 ]; then
                rounded_life=0
            fi
            echo $rounded_life
        fi
    else
        local remaining_years=$(echo "$life_expectancy - $age" | bc)
        local rounded_life=$(echo "$remaining_years" | awk '{print int($1+0.5)}')
        echo $rounded_life
    fi
}

# Read user data from user-store.txt
email=$1
while IFS=: read -r uuid user_email role hashed_password first_name last_name dob iso hiv diagnosis_date art art_date; do
    if [[ "$user_email" == "$email" ]]; then
        # Calculate age from DOB
        current_year=$(date +%Y)
        birth_year=$(echo $dob | cut -d '/' -f3)
        age=$(($current_year - $birth_year))

        # echo "Debug: age=$age"

        # Compute remaining lifespan
        remaining_lifespan=$(compute_remaining_lifespan "$iso" "$age" "$hiv" "$diagnosis_date" "$art" "$art_date")
        echo $remaining_lifespan
        exit 0
    fi
done < "$user_store"

echo "User not found"
exit 1
