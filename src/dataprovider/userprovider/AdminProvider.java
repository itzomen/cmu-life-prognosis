package dataprovider.userprovider;

import models.user.User;

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

    public String initiateRegistration(String email) {
      // checking if email is unique??
      //generating a uuid
      // throw if any errors
      return "123";
    }
}