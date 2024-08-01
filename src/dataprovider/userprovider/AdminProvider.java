package dataprovider.userprovider;

import models.user.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AdminProvider{
   
    
    public List<User> getAllUsers() {
        List<User> users= new ArrayList<User>();
        /*
         * 
         * TODO: complete implementation by accessing file through bash
         */

         return users;
    }

    // public String initiateRegistration(String email) {
    //   // checking if email is unique??
    //   //generating a uuid
    //   // throw if any errors
    //   return "123";
    // }

    public String initiateRegistration(String email) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("./scripts/initiate_user.sh", email);
            Process process = processBuilder.start();
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                return output.toString().split(":")[1].trim(); // Extract UUID from the output
            } else {
                throw new RuntimeException("Script failed with exit code " + exitCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error initiating user registration", e);
        }
    }
}