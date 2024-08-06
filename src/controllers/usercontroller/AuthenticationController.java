

package controllers.usercontroller;
import dataprovider.userprovider.AuthenticationProvider;
import models.intermediate.RegisterData;
import models.intermediate.RegistrationError;
import models.user.User;
import validation.ValidationInterface;
import views.util.exceptions.UuidException;

public class AuthenticationController  implements ValidationInterface {
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

    public RegistrationError register(RegisterData rdata){
        try{
            authenticationProvider.register(rdata);
            return new RegistrationError(false, false, true);
        }
        catch(UuidException uuidException){
            return new RegistrationError(false, true, false);
        }
        catch(Exception e){
            return new RegistrationError(true, false, false);
        }

    }

    


}
