package models.user;

import java.util.Date;

import constants.Role;

public class Patient extends User{
    private Date dob;
    private boolean HIVStatus;
    private Date diagnsisDate;
    private Date artDate;
    
    public Patient(String fName, String lName, String email,Date dob, boolean hIVStatus, 
    Date diagnsisDate, boolean takingART, Date artDate,  String iSOCode, Role role) {
        this.fName=fName;
        this.lName=lName;
        this.email=email;
        this.dob = dob;
        HIVStatus = hIVStatus;
        this.diagnsisDate = diagnsisDate;
        this.takingART = takingART;
        this.artDate=artDate;
        ISOCode = iSOCode;
    }

    private boolean takingART;

    public Date getDiagnsisDate() {
        return diagnsisDate;
    }

    

    public Date getArtDate() {
        return artDate;
    }



    public void setArtDate(Date artDate) {
        this.artDate = artDate;
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