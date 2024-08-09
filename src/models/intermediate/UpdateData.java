package models.intermediate;

import java.time.LocalDate;

public class UpdateData {
    private String fName,lName,iSOCode;
    private boolean hIVStatus,takingART;
    private LocalDate dob,diagnsisDate,artDate;
    public UpdateData(String fName, String lName, String iSOCode, boolean hIVStatus, boolean takingART, LocalDate dob,
            LocalDate diagnsisDate, LocalDate artDate) {
        this.fName = fName;
        this.lName = lName;
        this.iSOCode = iSOCode;
        this.hIVStatus = hIVStatus;
        this.takingART = takingART;
        this.dob = dob;
        this.diagnsisDate = diagnsisDate;
        this.artDate = artDate;
    }
    public String getfName() {
        return fName;
    }
    public String getlName() {
        return lName;
    }
    public String getiSOCode() {
        return iSOCode;
    }
    public boolean ishIVStatus() {
        return hIVStatus;
    }
    public boolean isTakingART() {
        return takingART;
    }
    public LocalDate getDob() {
        return dob;
    }
    public LocalDate getDiagnsisDate() {
        return diagnsisDate;
    }
    public LocalDate getArtDate() {
        return artDate;
    } 
}
