package models.intermediate;

public class RegistrationError {
    public boolean isUuidNotFound() {
        return uuidNotFound;
    }
    public boolean isOtherError() {
        return otherError;
    }
    public boolean isNoError() {
        return noError;
    }
    private boolean uuidNotFound=false;
    private boolean otherError;
    private boolean noError;


    public RegistrationError(boolean otherError, boolean uuidNotFound, boolean noError){
        this.uuidNotFound=uuidNotFound;
        this.otherError=otherError;
        this.noError=noError;
    }
}
