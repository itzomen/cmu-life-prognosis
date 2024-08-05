package validation;

import constants.ISO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

    default boolean fNameValid(String fName){
        return fName.matches("^[A-Za-z_-]+$");
    }


    default boolean passwordValid(String password){
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,}$"
        );
    }

    default Date dateValid(String date){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH);
        Date dobj=null;
        try{
            dobj= formatter.parse(date);

        }
        catch(ParseException e){

        }
        finally {
            return dobj;
        }
    }
}
