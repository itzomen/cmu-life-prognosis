
package models.user;

import constants.Role;

import static constants.Role.PATIENT;

abstract public class User{
    protected String fName;
    protected String lName;
    protected String email;

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public String getEmail() {
        return email;
    }
    
    protected String password;
    protected Role role= PATIENT;  // setting default value to patient

    public Role getRole() {
        return role;
    }



}