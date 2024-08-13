#!/bin/bash
patientEmail=$1
life_expectancy_csv="life-expectancy.csv"
current=$(date +%Y)


calculate_age() {
    local dob=$1
    local birth_year=$(echo $dob | cut -d '/' -f3)
    echo $((current - birth_year))
}

compute_remaining_lifespan() {
    local country_iso=$1
    local age=$2
    local hiv_positive=$3
    local diagnosis_date=$4
    local art_start_status=$5
    local art_start_year=$6

    local life_expectancy=$(awk -F ',' -v iso="$country_iso" '$4 == iso {print $7}' "$life_expectancy_csv")

    if [[ "$hiv_positive" == "true" ]]; then
        local diagnosis_year=$(echo $diagnosis_date | cut -d '/' -f3)
        local delay_years=0
        if [[ "$art_start_status" == "true" ]]; then
            if [[ "$art_start_year" != "false" && -n "$art_start_year" ]]; then
                local art_year=$(echo $art_start_year | cut -d '/' -f3)
                delay_years=$(($art_year - $diagnosis_year))
            fi
            if [ -n "$life_expectancy" ]; then
                #using the mathematical model defined in the documentation
                local reduced_life=$(echo "($life_expectancy - $age) * 0.9 * (0.9^$delay_years)" | bc)
                local rounded_life=$(echo "($reduced_life + 0.999999)/1" | bc)
                echo $rounded_life
            else
                echo "Life expectancy not found for ISO: $country_iso"
            fi
        else
            # hiv diagnosed individuals only have 5 years if no on ART
            local reduced_life=$(echo "($diagnosis_year + 5 - $current)" |bc)
            # rounding to the next integer
            local rounded_life=$(echo "($reduced_life + 0.999999)/1" | bc)
            if (("$rounded_life" >= "0")); then
                echo $rounded_life
            else 
                rounded_life=0
                echo $rounded_life
            fi
        fi
    else
        # normal course of life
        local reduced_life=$(echo "($life_expectancy - $age)" |bc)
        # rounding the value to the next largest integer below
        local rounded_life=$(echo "($reduced_life + 0.999999)/1" | bc)
        echo $rounded_life
        
    fi


}

while IFS=':' read uuid email role hash first_name last_name dob country_iso hiv diagnosis_date art_status art_start_year extra
do
    if [[ "$role" == "patient" && "$patientEmail" == "$email" ]]; then
        age=$(calculate_age $dob)
        remaining_lifespan=$(compute_remaining_lifespan $country_iso $age $hiv $diagnosis_date $art_status $art_start_year)     
        echo $remaining_lifespan
    fi

done < "user-store.txt"

