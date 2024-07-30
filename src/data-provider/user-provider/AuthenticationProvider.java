import java.util.UUID;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

class AuthenticationProvider{
    
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
}

