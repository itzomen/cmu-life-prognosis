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
import java.text.SimpleDateFormat;
import java.util.Date;

public class AuthenticationProvider {

    public User login(String email, String password) throws InterruptedException, IOException {

        String[] cmd = new String[] { "/bin/bash", "./scripts/login.sh", email, password };
        Process pr = Runtime.getRuntime().exec(cmd);

        BufferedReader reader = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        User user = null;
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("data: ")) {
                String[] userDetails = line.substring(6).split(":");

                if (Role.valueOf(userDetails[2].toUpperCase()) == Role.ADMIN) {
                    user = new Admin(userDetails[4],userDetails[5], userDetails[1], Role.ADMIN);

                } else {
                    Date testDate= new Date(1000);
                    user = new Patient(userDetails[4],userDetails[5], userDetails[1],testDate,false, testDate, false, "US",
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
