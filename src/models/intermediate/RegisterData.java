package models.intermediate;

import java.util.Date;

public class RegisterData{
    final String uuid, fName, lName, isoCode, password;
    final Date dob, diagnosisDate;
    final boolean hivStatus, takingART;
    public RegisterData(String uuid, String fName, String lName, Date dob, boolean hivStatus, Date diagnosisDate,
                        boolean takingART, String isoCode, String password) {
        this.uuid = uuid;
        this.fName = fName;
        this.lName = lName;
        this.dob = dob;
        this.hivStatus = hivStatus;
        this.diagnosisDate = diagnosisDate;
        this.takingART = takingART;
        this.isoCode = isoCode;
        this.password = password;
    }
}