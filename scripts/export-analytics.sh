#!/bin/bash
source ./scripts/life-expectancy.sh
FILE="rem_years_temp.txt"
TEMP_FILE="temp_calculations.txt"
touch $FILE $TEMP_FILE
# initalizing the rem_years_temp file with the rem_years calculations 
while IFS=':' read uuid email role hash first_name last_name dob country_iso hiv diagnosis_date art_status art_start_year extra
do
    if [[ "$role" == "patient" ]]; then
        age=$(calculate_age $dob)
        remaining_lifespan=$(compute_remaining_lifespan $country_iso $age $hiv $diagnosis_date $art_status $art_start_year)
        echo "${remaining_lifespan}:${country_iso}:${age}:${hiv}:${diagnosis_date}:${art_status}:${art_start_year}" >> $FILE
    fi

done < "user-store.txt"

#sorting the file entries using the lifespan
sort -k1 -n $FILE > $TEMP_FILE

#calculate total entries
total_entries=$(wc -l < $TEMP_FILE)

# calculate mean
calculate_mean() {
    awk '{sum+=$1} END {print sum/NR}' $TEMP_FILE
}

calculate_median() {
    if (( total_entries % 2 == 1 )); then
    # Odd number of elements, median is the middle one
    median_line=$((total_entries / 2 + 1))
    median=$(awk -F':' "NR == $median_line {print \$1}" $TEMP_FILE)
else
    # Even number of elements, median is the average of the two middle ones
    middle1_line=$((total_entries / 2))
    middle2_line=$((middle1_line + 1))
    middle1=$(awk -F':' "NR == $middle1_line {print \$1}" $TEMP_FILE)
    middle2=$(awk -F':' "NR == $middle2_line {print \$1}" $TEMP_FILE)
    median=$(echo "($middle1 + $middle2) / 2" | bc -l)
fi
 echo $median
}

calculate_percentiles(){
    percentiles=(10 25 50 75 90)

echo "Percentile calculations for remaining_lifespan:"

for p in "${percentiles[@]}"; do
    # Calculate the rank (position in the sorted list)
    rank=$(echo "($p / 100) * ($total_entries - 1) + 1" | bc -l)
    
    # If rank is not an integer, find the closest rank and its next
    rank_int=$(printf "%.0f" "$rank")
    
    # If rank is an integer, use it directly
    if [[ $(echo "$rank == $rank_int" | bc -l) -eq 1 ]]; then
        percentile_value=$(awk -F':' "NR == $rank_int {print \$1}" $TEMP_FILE)
    else
        # Calculate the interpolation
        lower=$(awk -F':' "NR == ${rank_int} {print \$1}" $TEMP_FILE)
        upper=$(awk -F':' "NR == $((rank_int + 1)) {print \$1}" $TEMP_FILE)
        percentile_value=$(echo "$lower + ($rank - $rank_int) * ($upper - $lower)" | bc -l)
    fi

    # Print the percentile and its corresponding value
    echo "${p}th percentile: $percentile_value"
done
}

calculate_mean_for_hiv(){
    sum=0
    count=0

# Loop through each line in the file
while IFS=':' read -r rem_span country_iso age hiv diagnosis_date art_status art_start_year
 do
    if [ "$hiv" = "true" ]; then
        # Add the rem_span to the sum
        sum=$(echo "$sum + $rem_span" | bc)
        # Increment the count
        count=$((count + 1))
    fi
done < $TEMP_FILE

# Calculate the mean
    mean=$(echo "scale=2; $sum / $count" | bc)
    echo "Mean rem_span where status is true: $mean"

}



calculate_mean_for_hiv

rm $FILE $TEMP_FILE


