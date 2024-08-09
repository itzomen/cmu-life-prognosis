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
      ProcessBuilder processBuilder = new ProcessBuilder("");
      Process process = processBuilder.start();

      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
      StringBuilder output = new StringBuilder();

      int exitCode = process.waitFor();
      if (exitCode == 0){
        return output.toString();
      } 
      else {
        throw new RuntimeException("Script failed with exit code " + exitCode);
      }      
    }     
}