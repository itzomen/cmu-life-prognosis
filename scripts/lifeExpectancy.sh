#!/bin/bash

# Usage: ./compute_lifespan.sh <user_store> <life_expectancy_csv>
# Example: ./compute_lifespan.sh user-store.txt life-expectancy.csv

# if [ "$#" -ne 2 ]; then
#     echo "Usage: $0 <user_store> <life_expectancy_csv>"
#     exit 1
# fi

user_store="$1"
life_expectancy_csv="$2"

# echo "Checking files: $user_store and $life_expectancy_csv"
# ls -l "$user_store" "$life_expectancy_csv"

if [[ ! -f "$user_store" ]] || [[ ! -f "$life_expectancy_csv" ]]; then
    echo "Error: File not found or not readable."
    echo "Check the paths for user_store and life_expectancy_csv."
    exit 1
fi

calculate_age() {
    local dob=$1
    local current_year=$(date +%Y)
    local birth_year=$(echo $dob | cut -d '/' -f3)
    echo $((current_year - birth_year))
}

compute_remaining_lifespan() {
    local country_iso=$1
    local age=$2
    local hiv_positive=$3
    local diagnosis_date=$4
    local art_start_status=$5
    local art_start_year=$6

    # Fetch the life expectancy from the CSV using the country ISO from the fourth column
    local life_expectancy=$(awk -F ',' -v iso="$country_iso" '$4 == iso {print $7}' "$life_expectancy_csv")
    # echo "Debug: Country ISO: $country_iso, Life Expectancy: $life_expectancy"

    if [[ "$hiv_positive" == "true" ]]; then
        local diagnosis_year=$(echo $diagnosis_date | cut -d '/' -f3)
        local delay_years=0
        if [[ "$art_start_status" == "true" ]]; then
            if [[ "$art_start_year" != "false" && -n "$art_start_year" ]]; then
                local art_year=$(echo $art_start_year | cut -d '/' -f3)
                delay_years=$(($art_year - $diagnosis_year))
            fi
            if [ -n "$life_expectancy" ]; then
                local reduced_life=$(echo "($life_expectancy - $age) * 0.9 * (0.9^$delay_years)" | bc)
                echo $reduced_life
            else
                echo "Life expectancy not found for ISO: $country_iso"
            fi
        else
            
            local reduced_life=$(echo "($diagnosis_year + 5 - $current)" |bc)
            if (("$reduced_life" >= "0")); then
                echo $reduced_life
            else 
                reduced_life=0
                echo $reduced_life
            fi
        fi
    else
        local reduced_life=$(echo "($life_expectancy - $age)" |bc)
        echo $reduced_life
        
    fi


}

while IFS=':' read uuid email role hash first_name last_name dob country_iso hiv diagnosis_date art_status art_start_year extra
do
    if [[ "$role" == "patient" ]]; then
        age=$(calculate_age $dob)
        remaining_lifespan=$(compute_remaining_lifespan $country_iso $age $hiv $diagnosis_date $art_status $art_start_year)
        
        echo "Patient: $first_name $last_name"
        echo "DOB: $dob, Country: $country_iso, Age: $age"
        echo "HIV Status: $hiv, Diagnosis Date: $diagnosis_date, ART Start Year: $art_start_year"
        echo "Remaining Lifespan: $remaining_lifespan years"
        echo "-----------------------------------"
    fi

done < "$user_store"
