package models.intermediate;

import java.util.Date;

public class RegisterData{
    final String uuid, fName, lName, isoCode, password;
    final Date dob, diagnosisDate, artDate;
    final boolean hivStatus, takingART;
    public RegisterData(String uuid, String fName, String lName, Date dob, boolean hivStatus, Date artDate, Date diagnosisDate,
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
    public Date getDob() {
        return dob;
    }
    public Date getDiagnosisDate() {
        return diagnosisDate;
    }
    public boolean isHivStatus() {
        return hivStatus;
    }
    public boolean isTakingART() {
        return takingART;
    }
    public Date getArtDate() {
        return artDate;
    }
    public String getIsoCode() {
        return isoCode;
    }
    public String getPassword() {
        return password;
    }
}