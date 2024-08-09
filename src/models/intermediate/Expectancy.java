package models.intermediate;

import java.time.LocalDate;

public class Expectancy {
    private LocalDate date;
    private long  remYears;
    public Expectancy(LocalDate date, long remYears) {
        this.date = date;
        this.remYears = remYears;
    }
    public LocalDate getDate() {
        return date;
    }
    public long getRemYears() {
        return remYears;
    }

}
