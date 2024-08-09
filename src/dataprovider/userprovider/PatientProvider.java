package dataprovider.userprovider;

import models.user.Patient;

public class PatientProvider {
    // isoCode is 2 alpha code
    public int getLifeSpan(String email) {

        // logic for fetch data from patient.csv
        // throw any errors

        return 48;
    }

    public void updateProfile(Patient patient){
      // update all the data for patient.email
      
      throw new RuntimeException("test exception");
      
    } 

    public void updatePassword(String email, String newPassword){
      // update password by hashing the newPassword
      // for the email given
      //throw if any error
      throw new RuntimeException("test exception");
    }
    

}