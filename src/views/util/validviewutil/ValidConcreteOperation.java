package views.util.validviewutil;

import models.intermediate.ValidationOutput;
<<<<<<< HEAD
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
=======
import java.util.Date;
import java.util.Scanner;

public class ValidConcreteOperation {
    public ValidationOutput performCheck( String message, ValidateOperation operation) {
        boolean validToken = false;
        String input="";
        while(!validToken){
            Scanner scanner = new Scanner(System.in);
            input = scanner.nextLine();
            if(input.equals("*")) break;
>>>>>>> dd5bbb9990628c6153b9c52d0ded033f9977a975
            validToken=operation.check(input);
            if(!validToken) System.out.println(message);
        }
        if(!validToken) {
            return new ValidationOutput("", false);
        }
        return new ValidationOutput(input, true);
    }
<<<<<<< HEAD
    public Date performDateCheck(String pMessage,String message, ValidateDateOperation operation, PromptDisplay pDisplay) {
=======
    public Date performDateCheck( String message, ValidateDateOperation operation) {
>>>>>>> dd5bbb9990628c6153b9c52d0ded033f9977a975

        boolean validToken = false;
        String input="";
        Date dobj=null;
        while(!validToken){
<<<<<<< HEAD
            input = pDisplay.getText(pMessage);
=======
            Scanner scanner = new Scanner(System.in);
            input = scanner.nextLine();
            if(input.equals("*")) break;
>>>>>>> dd5bbb9990628c6153b9c52d0ded033f9977a975
            dobj =operation.dateCheck(input);
            validToken= dobj!=null;
            if(!validToken) System.out.println(message);
        }
        return dobj;
    }
<<<<<<< HEAD
    public boolean checkPassConfirmation(String password, PromptDisplay pDisplay){
        String cpass= pDisplay.getPassword("Confirm password");
        while(!cpass.equals(password)){
            cpass=pDisplay.getPassword("Passwords don't match. Enter again or * to go back, ^ to exit");
            if(cpass==null) return false; 
        }
        return true; 
    }
=======

>>>>>>> dd5bbb9990628c6153b9c52d0ded033f9977a975
}
