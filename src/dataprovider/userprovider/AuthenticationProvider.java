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


public class AuthenticationProvider{

    public User login(String email, String password) {
        try {
            String[] cmd = new String[]{"/bin/bash", "./scripts/login.sh", email,password};
            Process pr = Runtime.getRuntime().exec(cmd);

            BufferedReader reader = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            User user=null;
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("data: ")) {
                    String[] userDetails = line.substring(6).split(":");
                    
        
        
                    if (Role.valueOf(userDetails[2].toUpperCase()) == Role.ADMIN) {
                        user = new Admin();
                        user.setRole(Role.ADMIN);
                        
                    }
                    else{
                        user = new Patient();
                        user.setRole(Role.PATIENT);
                    }
                    user.setEmail(userDetails[1]);
                    user.setFname(userDetails[4]);
                    user.setLName(userDetails[5]);

                    return user;
                }
            }
            int exitCode = pr.waitFor();
            if (exitCode != 0) {
                System.err.println("Login script exited with code " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void register(RegisterData rdata) throws IOException, InterruptedException {
       
            // Format dates as "dd/MM/yyyy"
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String dob = dateFormat.format(rdata.getDob());
            String diagnosisDate = rdata.getDiagnosisDate()!=null? dateFormat.format(rdata.getDiagnosisDate()) : "";
            String artDate = rdata.getArtDate()!=null? dateFormat.format(rdata.getArtDate()): "";
            ProcessBuilder pb = new ProcessBuilder("scripts/register_user.sh",
                    rdata.getUuid(), rdata.getPassword(), rdata.getfName(), rdata.getlName(),
                    dob, rdata.getIsoCode(), String.valueOf(rdata.isHivStatus()),
                    diagnosisDate, String.valueOf(rdata.isTakingART()), artDate);

            Process process = pb.start();
            int exitCode=process.waitFor();
            if(exitCode==8){
                throw new RuntimeException("UUId not found");
            }
        
    }
}

