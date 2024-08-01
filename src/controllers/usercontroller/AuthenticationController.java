

package controllers.usercontroller;

import dataprovider.userprovider.AuthenticationProvider;
import models.intermediate.RegisterData;
import models.user.User;
import validation.ValidationInterface;

public class AuthenticationController implements ValidationInterface {
    final AuthenticationProvider authenticationProvider;

    public AuthenticationController(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }
    public User login(String username, String password) {

        try{
          return authenticationProvider.login(username, password);
       }
       catch(Exception e){
           // login error
           return null;
       }
    }

    public boolean register(RegisterData rdata){
        try{
            authenticationProvider.register(rdata);
            return true;
        }
        catch(Exception e){
            return false;
        }

    }


}
