package views.util.validviewutil;

import models.intermediate.ValidationOutput;
import views.util.displayutil.PromptDisplay;

import java.util.Date;

public class ValidConcreteOperation {
    public ValidationOutput performCheck( String pMessage,String message, ValidateOperation operation, 
    boolean isPassword, PromptDisplay pDisplay) {
        boolean validToken = false;
        String input="";
        while(!validToken){
            input = isPassword?  pDisplay.getPassword(pMessage): pDisplay.getText(pMessage);
            if(input== null) break;
            validToken=operation.check(input);
            if(!validToken) System.out.println(message);
        }
        if(!validToken) {
            return new ValidationOutput("", false);
        }
        return new ValidationOutput(input, true);
    }
    public Date performDateCheck(String pMessage,String message, ValidateDateOperation operation, PromptDisplay pDisplay) {

        boolean validToken = false;
        String input="";
        Date dobj=null;
        while(!validToken){
            input = pDisplay.getText(pMessage);
            dobj =operation.dateCheck(input);
            validToken= dobj!=null;
            if(!validToken) System.out.println(message);
        }
        return dobj;
    }
    public boolean checkPassConfirmation(String password, PromptDisplay pDisplay){
        String cpass= pDisplay.getPassword("Confirm password");
        while(!cpass.equals(password)){
            cpass=pDisplay.getPassword("Passwords don't match. Enter again or * to go back, ^ to exit");
            if(cpass==null) return false; 
        }
        return true; 
    }
}
