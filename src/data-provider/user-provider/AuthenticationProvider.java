import java.util.UUID;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

class AuthenticationProvider{
    
    public String createUser(String email, String password, Role role){
        // append the user details to the file user.txt as follows:
        // uuid:email:password:role
        // We use bash to append the user details to the file user.txt
    }

    

    public boolean authenticateUser(String email, String password){
        // read the file user.txt and check if the user exists
        // We use bash to read the file user.txt
    }
}

