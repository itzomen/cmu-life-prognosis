
package models.user;

import constants.Role;

public class Admin extends User{
   public Admin(String fName, String lName ,String email, Role role){
    this.email=email;
    this.lName=lName;
    this.email=email;
    this.role=role;
    this.fName=fName;
   } 
}