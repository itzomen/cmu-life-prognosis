
$patientEmail
user_store="$1"
life_expectancy_csv="$2"


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
                local reduced_life=$(echo "($life_expectancy - $age) * 0.9 * (0.9^$delay_years)" | bc)
                local rounded_life=$(echo "$reduced_life" | awk '{print int($1+0.5)}')
                echo $rounded_life
            else
                echo "Life expectancy not found for ISO: $country_iso"
            fi
        else
            
            local reduced_life=$(echo "($diagnosis_year + 5 - $current)" |bc)
            local rounded_life=$(echo "$reduced_life" | awk '{print int($1+0.5)}')
            if (("$rounded_life" >= "0")); then
                echo $rounded_life
            else 
                rounded_life=0
                echo $rounded_life
            fi
        fi
    else
        local reduced_life=$(echo "($life_expectancy - $age)" |bc)
        local rounded_life=$(echo "$reduced_life" | awk '{print int($1+0.5)}')
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

done < "$user_store"