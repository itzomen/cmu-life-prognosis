package validation;

import constants.ISO;
import views.util.formatter.CustomFormatter;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public interface ValidationInterface {

    default  boolean emailValid(String email){
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

        return email.matches(regexPattern);
    }

    default boolean uuidValid(String uuid){
        String regexPattern= "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";
        return uuid.matches(regexPattern);
    }

    default boolean isoValid(String iso){
        return ISO.getIsoCountries().contains(iso);
    }

    default boolean isoValidWithEmpty(String iso){
        return ISO.getIsoCountries().contains(iso) || iso.isEmpty();
    }

    default boolean fNameValid(String fName){
        return fName.matches("^[A-Za-z_-]+$");
    }

    default boolean fNameValidWithEmpty(String fName){
        return fName.matches("^[A-Za-z_-]+$") || fName.isEmpty();
    }

    default boolean passwordValid(String password){
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,}$"
        );
    }
    

    default LocalDate dateValid(String date){
      
        try{
            LocalDate localDate = LocalDate.parse(date,CustomFormatter.formatter);
            return localDate;    
        }
        catch(DateTimeParseException e){
           return null;
        }

    }

    default LocalDate dateValidWithEmpty(String date, LocalDate initDate){
        try{
            LocalDate localDate = LocalDate.parse(date,CustomFormatter.formatter);
            return localDate;    
        }
        catch(DateTimeParseException e){
           if(date.isEmpty()) return initDate; 
           return null;
        }
    }
    default boolean beforeNow(LocalDate date){
        LocalDate nowDate= LocalDate.now();
        return date.isBefore(nowDate); 
    }

    default boolean checkDateRange(LocalDate date, LocalDate leDate){
        LocalDate nowDate= LocalDate.now();
        if(!date.isBefore(leDate) && date.isBefore(nowDate) ){
            return true;
        }
        return false;
    }

}
