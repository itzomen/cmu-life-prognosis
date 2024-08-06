package models.user;

import java.util.Date;

public class Patient extends User{
    private Date dob;
    private boolean HIVStatus;
    private Date diagnsisDate;
    private boolean takingART;

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public void setHIVStatus(boolean hIVStatus) {
        HIVStatus = hIVStatus;
    }

    public void setDiagnsisDate(Date diagnsisDate) {
        this.diagnsisDate = diagnsisDate;
    }

    public void setTakingART(boolean takingART) {
        this.takingART = takingART;
    }

    public void setISOCode(String iSOCode) {
        ISOCode = iSOCode;
    }

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