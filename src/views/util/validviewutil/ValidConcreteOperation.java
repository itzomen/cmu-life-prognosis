package views.util.validviewutil;

import models.intermediate.ValidationOutput;
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
            validToken=operation.check(input);
            if(!validToken) System.out.println(message);
        }
        if(!validToken) {
            return new ValidationOutput("", false);
        }
        return new ValidationOutput(input, true);
    }
    public Date performDateCheck( String message, ValidateDateOperation operation) {

        boolean validToken = false;
        String input="";
        Date dobj=null;
        while(!validToken){
            Scanner scanner = new Scanner(System.in);
            input = scanner.nextLine();
            if(input.equals("*")) break;
            dobj =operation.dateCheck(input);
            validToken= dobj!=null;
            if(!validToken) System.out.println(message);
        }
        return dobj;
    }

}
