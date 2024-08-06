package dataprovider.userprovider;

import constants.Role;
import models.intermediate.RegisterData;
import models.user.Admin;
import models.user.Patient;
import models.user.User;
import views.util.exceptions.UuidException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AuthenticationProvider {

    public User login(String email, String password) throws InterruptedException, IOException, ParseException {

        String[] cmd = new String[] { "/bin/bash", "./scripts/login.sh", email, password };
        Process pr = Runtime.getRuntime().exec(cmd);

        BufferedReader reader = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        User user = null;
        String line;
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("data: ")) {
                String[] userDetails = line.substring(6).split(":");

                if (Role.valueOf(userDetails[2].toUpperCase()) == Role.ADMIN) {
                    // `uuid`: A unique identifier for the user.
                    // - `email`: The email address of the user.
                    // - `role`: The role of the user. Can be either `admin` or `patient`.
                    // - `hashed_password`: The hashed password of the user.
                    // - `first_name`: The first name of the user.
                    // - `last_name`: The last name of the user.
                    // - `dob`: The date of birth of the user.
                    // - `country`: The country of the user.
                    // - `hiv_status`: The HIV status of the user. (true or false)
                    // - `diagnosis_date`: The date of diagnosis of the user.
                    // - `art_status`: The ART status of the user. (true or false)
                    // - `art_date`: The date the patient started ART drugs.
                    System.out.println(userDetails[6]); 
                    user = new Admin(userDetails[4],userDetails[5], userDetails[1], Role.ADMIN);

                } else {
                    Date testDate= new Date(1000);
                    Date dob= userDetails[6].isEmpty() ? null: df.parse(userDetails[6]);
                    boolean isHiv= userDetails[8].isEmpty()? false: Boolean.parseBoolean(userDetails[8]); 
                    Date diDate= userDetails[9].isEmpty() ? null : df.parse(userDetails[9]);
                    boolean takArt= userDetails[10].isEmpty() ? false :Boolean.parseBoolean(userDetails[10]);
                    Date  artDate= userDetails[11].isEmpty() ? null :   df.parse(userDetails[11]);

                    user = new Patient(userDetails[4],userDetails[5], userDetails[1],dob,isHiv, diDate,
                     takArt, artDate, "US",
                    Role.PATIENT
                    );
                    
                }
                return user;
            }
        }
        return null;
    }

    public void register(RegisterData rdata) throws IOException, InterruptedException {

        // Format dates as "dd/MM/yyyy"
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dob = dateFormat.format(rdata.getDob());
        String diagnosisDate = rdata.getDiagnosisDate() != null ? dateFormat.format(rdata.getDiagnosisDate()) : "";
        String artDate = rdata.getArtDate() != null ? dateFormat.format(rdata.getArtDate()) : "";
        ProcessBuilder pb = new ProcessBuilder("scripts/register_user.sh",
                rdata.getUuid(), rdata.getPassword(), rdata.getfName(), rdata.getlName(),
                dob, rdata.getIsoCode(), String.valueOf(rdata.isHivStatus()),
                diagnosisDate, String.valueOf(rdata.isTakingART()), artDate);

        Process process = pb.start();
        int exitCode = process.waitFor();
        if (exitCode == 8) {
            throw new UuidException("Uuid not found");
        }

    }
}
