
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
    public void setEmail(String email){
        this.email = email;
    }
    public void setLName(String lName){
        this.lName = lName;
    }

    protected String password;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    protected Role role= PATIENT;  // setting default value to patient

    public String getFName(){

        return fName;
    }
    public void setFname(String fName){
        this.fName = fName;
    }
}