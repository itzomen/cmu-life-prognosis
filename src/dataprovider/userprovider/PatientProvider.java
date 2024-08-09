package dataprovider.userprovider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.stream.Collectors;

import models.user.Patient;

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

  public void updateProfile(Patient patient) {
    // update all the data for patient.email

    throw new RuntimeException("test exception");

  }

  public void updatePassword(String email, String newPassword) {
    // update password by hashing the newPassword
    // for the email given
    // throw if any error
    throw new RuntimeException("test exception");
  }

}