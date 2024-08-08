package models.intermediate;

import java.time.LocalDate;

public class Expectancy {
    LocalDate date;
    long  remYears;
    public Expectancy(LocalDate date, long remYears) {
        this.date = date;
        this.remYears = remYears;
    }

}
