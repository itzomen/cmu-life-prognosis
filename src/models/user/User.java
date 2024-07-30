

abstract class User{
    protected String fName;

    protected String lName;
    protected String email;
    protected String password;
    protected Role role= PATIENT;  // setting default value to patient

    public String getFName(){
        return fName; 
    }
    // TODO: continue on the getters


}