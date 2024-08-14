package models.user;

import java.time.LocalDate;

import constants.Role;

public class Patient extends User{
    private LocalDate dob;
    private boolean HIVStatus;
    private LocalDate diagnsisDate;
    private LocalDate artDate;
    
    public Patient(String fName, String lName, String email,LocalDate dob, boolean hIVStatus, 
    LocalDate diagnsisDate, boolean takingART, LocalDate artDate,  String iSOCode, Role role) {
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

    public LocalDate getDiagnsisDate() {
        return diagnsisDate;
    }

    

    public LocalDate getArtDate() {
        return artDate;
    }



    public void setArtDate(LocalDate artDate) {
        this.artDate = artDate;
    }



    public LocalDate getDob() {
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

    
}
