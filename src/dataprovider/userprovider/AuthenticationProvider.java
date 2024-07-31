package dataprovider.userprovider;

import constants.Role;
import models.intermediate.RegisterData;
import models.user.Admin;
import models.user.User;

import java.util.UUID;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;



public class AuthenticationProvider{
    
    public String createUser(String email, String password){
        String uuid = UUID.randomUUID().toString();
        String user = uuid + "," + email + "," + password;
        try {
            FileWriter fileWriter = new FileWriter("user.txt", true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(user);
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uuid;
    }

    public User login(String email, String password) {

        // logic of login  >> >>
        // throw if any error
        // should be able to get the role
        User us= new Admin();
        us.setRole(Role.ADMIN);
        return us; // example of  a user
    }

    public void register(RegisterData rdata) {
        // register a user
        // throw if any error
        // add it to the second file
        // throw if any error
    }
}

