#!/bin/bash
source ./scripts/life-expectancy.sh
FILE="rem_years_temp.txt"
TEMP_FILE="temp_calculations.txt"
TEMP_HIV="temp_values_hiv.txt"
TEMP_ART="temp_values_art.txt"
TEMP_AGE="temp_sorted_age.txt"
mean=0;
mean_hiv=0;
mean_art=0
mean_age=0
ANALYTIC="./analytics/analytics.csv"
ANALYTIC_HIV="./analytics/analytics_hiv.csv"
ANALYTIC_ART="./analytics/analytics_art.csv"
ANALYTIC_AGE="./analytics/analytics_age.csv"
ANALYTIC_NO_ART="./analytics/analytics_no_art.csv"
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
count=0
count_hiv=0
count_art=0

calculate_means(){
    sum=0
    sum_hiv=0
    sum_art=0
    sum_age=0
    #use count_hiv
# Loop through each line in the file

while IFS=':' read -r rem_span country_iso age hiv diagnosis_date art art_start_year
 do
    curr="${rem_span}:${country_iso}:${age}:${hiv}:${diagnosis_date}:${art}:${art_start_year}"
    if [ "$hiv" = "true" ]; then
        # Add the rem_span to the sum
        sum_hiv=$(echo "$sum_hiv + $rem_span" | bc)
        # Increment the count
        count_hiv=$((count_hiv + 1))
        echo $curr >> $TEMP_HIV
        if [ "$art" = "true" ]; then
        sum_art=$(echo "$sum_art + $rem_span" | bc)
        count_art=$((count_art + 1))
        echo $curr >> $TEMP_ART
        fi
        sum_age=$(echo "$sum_age + $age" | bc)
    fi
    sum=$(echo "$sum + $rem_span" | bc)
    count=$((count + 1))
done < $TEMP_FILE

# Calculate the means
    mean=$(echo "scale=2; $sum / $count" | bc)
    mean_hiv=$(echo "scale=2; $sum_hiv / $count_hiv" | bc)
    mean_art=$(echo "scale=2; $sum_art / $count_art" | bc)
    sum_no_art=$(echo "$sum_hiv - $sum_art" | bc)
    count_no_art=$(echo "$count_hiv - $count_art" | bc)
    mean_no_art=$(echo "scale=2; $sum_no_art / $count_no_art" | bc)
    mean_age=$(echo "scale=2; $sum_age / $count_hiv" | bc)
    sort -t':' -k3,3n $TEMP_HIV -o $TEMP_AGE
}






calculate_median() {
    SORTED_FILE=$1
    total_entries=$2
    if (( $total_entries % 2 == 1 )); then
    # Odd number of elements, median is the middle one
    median_line=$(($total_entries / 2 + 1))
    median=$(awk -F':' "NR == $median_line {print \$1}" $SORTED_FILE)
   else
    # Even number of elements, median is the average of the two middle ones
    middle1_line=$(($total_entries / 2))
    middle2_line=$((middle1_line + 1))
    middle1=$(awk -F':' "NR == $middle1_line {print \$1}" $SORTED_FILE)
    middle2=$(awk -F':' "NR == $middle2_line {print \$1}" $SORTED_FILE)
    median=$(echo "($middle1 + $middle2) / 2" | bc -l)
   fi
 median2=$(printf "%.2f" "$median")
 echo "$median2"
}

calculate_median_age() {
    SORTED_FILE=$1
    total_entries=$2
    if (( $total_entries % 2 == 1 )); then
    # Odd number of elements, median is the middle one
    median_line=$(($total_entries / 2 + 1))
    median=$(awk -F':' "NR == $median_line {print \$3}" $SORTED_FILE)
  else
    # Even number of elements, median is the average of the two middle ones
    middle1_line=$(($total_entries / 2))
    middle2_line=$((middle1_line + 1))
    middle1=$(awk -F':' "NR == $middle1_line {print \$3}" $SORTED_FILE)
    middle2=$(awk -F':' "NR == $middle2_line {print \$3}" $SORTED_FILE)
    median=$(echo "($middle1 + $middle2) / 2" | bc -l)
   fi
 median2=$(printf "%.2f" "$median")
 echo "$median2"
}

calculate_percentile(){
    SORTED_FILE=$1
    total_entries=$2
    p=$3

    # Calculate the rank (position in the sorted list)
    rank=$(echo "($p / 100) * ($total_entries - 1) + 1" | bc -l)
    
    # If rank is not an integer, find the closest rank and its next
    rank_int=$(printf "%.0f" "$rank")
    
    # If rank is an integer, use it directly
    if [[ $(echo "$rank == $rank_int" | bc -l) -eq 1 ]]; then
        percentile_value=$(awk -F':' "NR == $rank_int {print \$1}" $SORTED_FILE)
    else
        # Calculate the interpolation
        lower=$(awk -F':' "NR == ${rank_int} {print \$1}" $SORTED_FILE)
        upper=$(awk -F':' "NR == $((rank_int + 1)) {print \$1}" $SORTED_FILE)
        percentile_value=$(echo "$lower + ($rank - $rank_int) * ($upper - $lower)" | bc -l)
    fi
    
    percentile_value=$(printf "%.2f" "$percentile_value") 
    echo "$percentile_value"

}

calculate_percentile_age(){
    SORTED_FILE=$1
    total_entries=$2
    p=$3

    # Calculate the rank (position in the sorted list)
    rank=$(echo "($p / 100) * ($total_entries - 1) + 1" | bc -l)
    
    # If rank is not an integer, find the closest rank and its next
    rank_int=$(printf "%.0f" "$rank")
    
    # If rank is an integer, use it directly
    if [[ $(echo "$rank == $rank_int" | bc -l) -eq 1 ]]; then
        percentile_value=$(awk -F':' "NR == $rank_int {print \$3}" $SORTED_FILE)
    else
        # Calculate the interpolation
        lower=$(awk -F':' "NR == ${rank_int} {print \$3}" $SORTED_FILE)
        upper=$(awk -F':' "NR == $((rank_int + 1)) {print \$3}" $SORTED_FILE)
        percentile_value=$(echo "$lower + ($rank - $rank_int) * ($upper - $lower)" | bc -l)
    fi
    
    percentile_value=$(printf "%.2f" "$percentile_value") 
    echo "$percentile_value"
}

calculate_means

median=$(calculate_median $TEMP_FILE $count)
median_hiv=$(calculate_median $TEMP_HIV $count_hiv)
median_art=$(calculate_median $TEMP_ART $count_art )
median_age=$(calculate_median_age $TEMP_AGE $count_hiv)

percentile_10=$(calculate_percentile $TEMP_FILE $count 10)
percentile_10_hiv=$(calculate_percentile $TEMP_HIV $count_hiv 10)
percentile_10_art=$(calculate_percentile $TEMP_ART $count_art 10)
percentile_10_age=$(calculate_percentile_age $TEMP_AGE $count_hiv 10)


percentile_25=$(calculate_percentile $TEMP_FILE $count 25)
percentile_25_hiv=$(calculate_percentile $TEMP_HIV $count_hiv 25)
percentile_25_art=$(calculate_percentile $TEMP_ART $count_art 25)
percentile_25_age=$(calculate_percentile_age $TEMP_AGE $count_hiv 25)

percentile_50=$(calculate_percentile $TEMP_FILE $count 50)
percentile_50_hiv=$(calculate_percentile $TEMP_HIV $count_hiv 50)
percentile_50_art=$(calculate_percentile $TEMP_ART $count_art 50)
percentile_50_age=$(calculate_percentile_age $TEMP_AGE $count_hiv 50)

percentile_75=$(calculate_percentile $TEMP_FILE $count 75)
percentile_75_hiv=$(calculate_percentile $TEMP_HIV $count_hiv 75)
percentile_75_art=$(calculate_percentile $TEMP_ART $count_art 75)
percentile_75_age=$(calculate_percentile_age $TEMP_AGE $count_hiv 75)

echo "mean,median,percentile_10,percentile_25,percentile_50,percentile_75" > $ANALYTIC
echo "${mean},${median},${percentile_10},${percentile_25},${percentile_50},${percentile_75}" >> $ANALYTIC

echo "mean,median,percentile_10,percentile_25,percentile_50,percentile_75" > $ANALYTIC_HIV
echo "${mean_hiv},${median_hiv},${percentile_10_hiv},${percentile_25_hiv},${percentile_50_hiv},${percentile_75_hiv}" >> $ANALYTIC_HIV

echo "mean,median,percentile_10,percentile_25,percentile_50,percentile_75" > $ANALYTIC_ART
echo "${mean_art},${median_art},${percentile_10_art},${percentile_25_art},${percentile_50_art},${percentile_75_art}" >> $ANALYTIC_ART

echo "mean,median,percentile_10,percentile_25,percentile_50,percentile_75" > $ANALYTIC_AGE
echo "${mean_age},${median_age},${percentile_10_age},${percentile_25_age},${percentile_50_age},${percentile_75_age}" >> $ANALYTIC_AGE






rm $FILE $TEMP_FILE $TEMP_AGE $TEMP_ART $TEMP_HIV
