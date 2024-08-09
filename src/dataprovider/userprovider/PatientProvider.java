package dataprovider.userprovider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.stream.Collectors;

import models.user.Patient;
import views.util.formatter.CustomFormatter;

public class PatientProvider {


  // isoCode is 2 alpha code
  public int getLifeSpan(String email) throws IOException {
    int lifespan = 0; // Default lifespan initialization

    ProcessBuilder pb = new ProcessBuilder("bash", "-c",
        "../scripts/lifeExpectancy.sh ../user-store.txt ../life-expectancy.csv");
    Map<String, String> env = pb.environment();
    env.put("patientEmail", email);

    Process p = pb.start();

    // Reading output from the process
    BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
    String output = reader.lines().collect(Collectors.joining("\n"));
    System.out.println("fetched data: "+ output);
    lifespan = Integer.parseInt(output); // Correctly convert string to integer

    System.out.println(lifespan);
    return lifespan;
  }

  public void updateProfile(Patient patient) throws IOException, InterruptedException {
    // Prepare the ProcessBuilder with the necessary arguments
    String dob = patient.getDob().format(CustomFormatter.formatter);
    String diagnosisDate = patient.getDiagnsisDate() != null ? patient.getDiagnsisDate().format(CustomFormatter.formatter) : "";
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