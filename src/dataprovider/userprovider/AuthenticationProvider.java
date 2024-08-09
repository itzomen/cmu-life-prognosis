package dataprovider.userprovider;

import constants.Role;
import models.intermediate.RegisterData;
import models.user.Admin;
import models.user.Patient;
import models.user.User;
import views.util.exceptions.UuidException;
import views.util.formatter.CustomFormatter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.time.LocalDate;

public class AuthenticationProvider {

    public User login(String email, String password) throws InterruptedException, IOException, ParseException {

        String[] cmd = new String[] { "/bin/bash", "./scripts/login.sh", email, password };
        Process pr = Runtime.getRuntime().exec(cmd);

        BufferedReader reader = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        User user = null;
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("data: ")) {
                String[] userDetails = line.substring(6).split(":");
                String fName = userDetails[4];
                String lName=  userDetails[5];
                if (Role.valueOf(userDetails[2].toUpperCase()) == Role.ADMIN) {
                    
                    user = new Admin(fName,lName, email, Role.ADMIN);

                } else {
                    LocalDate dob= userDetails[6].isEmpty() ? null: LocalDate.parse(userDetails[6], CustomFormatter.formatter);
                    boolean isHiv= userDetails[8].isEmpty()? false: Boolean.parseBoolean(userDetails[8]); 
                    LocalDate diDate= userDetails[9].isEmpty() ? null : LocalDate.parse(userDetails[9], CustomFormatter.formatter);
                    boolean takArt= userDetails[10].isEmpty() ? false :Boolean.parseBoolean(userDetails[10]);
                    LocalDate  artDate= userDetails[11].isEmpty() ? null :   LocalDate.parse(userDetails[11], 
                    CustomFormatter.formatter);
                    
                    user = new Patient(fName,lName, email,dob,isHiv, diDate,
                     takArt, artDate, userDetails[7],
                    Role.PATIENT
                    );
                }
                return user;
            }
        }
        return null;
    }

    public void register(RegisterData rdata) throws IOException, InterruptedException {

        
        String dob = rdata.getDob().format(CustomFormatter.formatter);
        String diagnosisDate = rdata.getDiagnosisDate() != null ? rdata.getDiagnosisDate().format(CustomFormatter.formatter) : "";
        String artDate = rdata.getArtDate() != null ? rdata.getArtDate().format(CustomFormatter.formatter) : "";
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
