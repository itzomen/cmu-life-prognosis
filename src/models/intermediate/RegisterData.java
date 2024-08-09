package models.intermediate;

import java.time.LocalDate;

public class RegisterData{
    final String uuid, fName, lName, isoCode, password;
    final LocalDate dob, diagnosisDate, artDate;
    final boolean hivStatus, takingART;
    public RegisterData(String uuid, String fName, String lName, LocalDate dob,
                        boolean hivStatus, LocalDate artDate, LocalDate diagnosisDate,
                        boolean takingART, String isoCode, String password) {
        this.uuid = uuid;
        this.fName = fName;
        this.lName = lName;
        this.dob = dob;
        this.hivStatus = hivStatus;
        this.diagnosisDate = diagnosisDate;
        this.takingART = takingART;
        this.artDate = artDate;
        this.isoCode = isoCode;
        this.password = password;
    }

    // getters
    public String getUuid() {
        return uuid;
    }
    public String getfName() {
        return fName;
    }
    public String getlName() {
        return lName;
    }
    public LocalDate getDob() {
        return dob;
    }
    public LocalDate getDiagnosisDate() {
        return diagnosisDate;
    }
    public boolean isHivStatus() {
        return hivStatus;
    }
    public boolean isTakingART() {
        return takingART;
    }
    public LocalDate getArtDate() {
        return artDate;
    }
    public String getIsoCode() {
        return isoCode;
    }
    public String getPassword() {
        return password;
    }
}