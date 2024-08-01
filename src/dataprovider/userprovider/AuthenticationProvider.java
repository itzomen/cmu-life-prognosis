package dataprovider.userprovider;

import constants.Role;
import models.intermediate.RegisterData;
import models.user.Admin;
import models.user.Patient;
import models.user.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class AuthenticationProvider{

    public User login(String email, String password) {
        try {
            ProcessBuilder pb = new ProcessBuilder("scripts/login.sh", email, password);
            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("data: ")) {
                    String[] userDetails = line.substring(6).split(":");
                    
                    User user;
        
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
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.err.println("Login script exited with code " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void register(RegisterData rdata) {
        try {
            ProcessBuilder pb = new ProcessBuilder("scripts/register_user.sh",
                                rdata.getUuid(), rdata.getPassword(), rdata.getfName(), rdata.getlName(),
                                rdata.getDob().toString(), rdata.getIsoCode(), String.valueOf(rdata.isHivStatus()),
                                rdata.getDiagnosisDate().toString(), String.valueOf(rdata.isTakingART()), rdata.getArtDate().toString());
            Process process = pb.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

