package dataprovider.userprovider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import models.user.Patient;
import views.util.formatter.CustomFormatter;

public class PatientProvider {

  public int getLifeSpan(String email) throws IOException {
    System.out.println("email: "+ email);
    // Prepare the ProcessBuilder with the necessary arguments
    ProcessBuilder pb = new ProcessBuilder("scripts/life-expectancy.sh", email);

    // Start the process and capture the output
    Process process = pb.start();

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
      String line;
      if ((line = reader.readLine()) != null) {

        // Parse the output as an integer representing the lifespan
        int lifespan = Integer.parseInt(line.trim());
        return lifespan;
      } else {
        throw new IOException("No output from lifespan script");
      }
    }
  }

  public void updateProfile(Patient patient) throws IOException, InterruptedException {
    // Prepare the ProcessBuilder with the necessary arguments
    String dob = patient.getDob().format(CustomFormatter.formatter);
    String diagnosisDate = patient.getDiagnsisDate() != null
        ? patient.getDiagnsisDate().format(CustomFormatter.formatter)
        : "";
    String artDate = patient.getArtDate() != null ? patient.getArtDate().format(CustomFormatter.formatter) : "";

    ProcessBuilder pb = new ProcessBuilder("scripts/update_user.sh",
        patient.getEmail(), patient.getfName(), patient.getlName(),
        dob, patient.getISOCode(), String.valueOf(patient.isHIVStatus()),
        diagnosisDate, String.valueOf(patient.isTakingART()), artDate);

    // Start the process and wait for its completion
    Process process = pb.start();
    int exitCode = process.waitFor();

    // Check the exit code to handle errors
    if (exitCode == 8) {
      throw new RuntimeException("Email Not Found");
    } else if (exitCode != 0) {
      throw new RuntimeException("Failed to update profile. Exit code: " + exitCode);
    }

  }

  public void updatePassword(String email, String newPassword) {
    // update password by hashing the newPassword
    // for the email given
    // throw if any error
    throw new RuntimeException("test exception");
  }

}
