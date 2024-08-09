package views.util.validviewutil;

import models.intermediate.ValidationOutput;
import views.util.displayutil.PromptDisplay;

import java.time.LocalDate;

public class ValidConcreteOperation {
    public ValidationOutput performCheck(String pMessage, String message, ValidateOperation operation,
            boolean isPassword, PromptDisplay pDisplay) {
        boolean validToken = false;
        String input = "";
        while (!validToken) {
            input = isPassword ? pDisplay.getPassword(pMessage) : pDisplay.getText(pMessage);
            if (input == null)
                break;
            validToken = operation.check(input);
            if (!validToken)
                System.out.println(message);
        }
        if (!validToken) {
            return new ValidationOutput("", false);
        }
        return new ValidationOutput(input, true);
    }

    public LocalDate performDateCheck(String pMessage, String message, ValidateDateOperation operation,
            PromptDisplay pDisplay) {

        String input = "";
        LocalDate dobj = null;
        while (dobj == null) {
            input = pDisplay.getText(pMessage);
            if (input == null)
                break;
            dobj = operation.dateCheck(input);
            if (dobj == null || !dobj.isBefore(LocalDate.now())) {
                System.out.println(message);
                dobj = null;
            }
        }
        return dobj;
    }

    public LocalDate performDateCheckWithEmpty(String pMessage, String message, ValidateDateOperationEmpty operation,
            PromptDisplay pDisplay, LocalDate initDate) {

        String input = "";
        LocalDate dobj = null;
        while (dobj == null) {
            input = pDisplay.getText(pMessage);
            if (input == null)
                break;
            dobj = operation.dateCheck(input, initDate);
            if (dobj == null || !dobj.isBefore(LocalDate.now())) {
                System.out.println(message);
                dobj = null;
            }
        }
        return dobj;
    }

    public LocalDate performDateRangeCheck(String pMessage, String message,
            ValidateDateOperation operation, PromptDisplay pDisplay, LocalDate befDate) {
        String input = "";
        LocalDate dobj = null;
        while (dobj == null) {
            input = pDisplay.getText(pMessage);
            if (input == null)
                break;
            dobj = operation.dateCheck(input);
            if (dobj == null || !dobj.isBefore(LocalDate.now()) || dobj.isBefore(befDate)) {
                dobj = null;
                System.out.println(message);
            }
        }
        return dobj;
    }

    public LocalDate performDateRangeWithEmpty(String pMessage, String message, ValidateDateOperationEmpty operation,
            PromptDisplay pDisplay, LocalDate befDate, LocalDate initDate) {
        String input = "";
        LocalDate dobj = null;
        while (dobj == null) {
            input = pDisplay.getText(pMessage);
            if (input == null)
                break;
            dobj = operation.dateCheck(input, initDate);
            if (dobj == null || !dobj.isBefore(LocalDate.now()) || dobj.isBefore(befDate)) {
                dobj = null;
                System.out.println(message);
            }
        }
        return dobj;
    }

    public boolean checkPassConfirmation(String password, PromptDisplay pDisplay) {
        String cpass = pDisplay.getPassword("Confirm password");
        while (!cpass.equals(password)) {
            cpass = pDisplay.getPassword("Passwords don't match. Enter again or * to go back, ^ to exit");
            if (cpass == null)
                return false;
        }
        return true;
    }

    public String checkPasswordValidity(String email, PromptDisplay pDisplay,
            ValidatePasswordOperation operation) {
        String pass = "";
        boolean valid = false;
        while (!valid) {
            pass = pDisplay.getPassword("Enter previous password");
            if (pass == null)
                break;
            valid = operation.passwordCheck(email, pass) != null;
            if (!valid)
                System.out.println("Incorrect password");
        }
        return pass;
    }
}
