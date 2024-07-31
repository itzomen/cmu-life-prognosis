package models.user;

import java.util.Date;

public class Patient extends User{
    private Date dob;
    private boolean HIVStatus;
    private Date diagnsisDate;
    private boolean takingART;

    public Date getDiagnsisDate() {
        return diagnsisDate;
    }

    public Date getDob() {
        return dob;
    }

    public boolean isHIVStatus() {
        return HIVStatus;
    }

    public boolean isTakingART() {
        return takingART;
    }

    public String getISOCode() {
        return ISOCode;
    }

    private String ISOCode;

    // getter and setters
}