
package controllers.usercontroller;

import dataprovider.userprovider.AdminProvider;
import validation.ValidationInterface;

public class AdminController implements ValidationInterface {
    final AdminProvider adminProvider;
    public AdminController(AdminProvider adminProvider) {this.adminProvider=adminProvider;}

    public String initiateRegistration(String email){
        try{
            return adminProvider.initiateRegistration(email);
        }
        catch (Exception e){
            return null;
        }
    }


    public String exportPatientsInfo() {
        try {
          return adminProvider.exportPatientsInfo();

        }
        catch(Exception e){
            return null;
        }
    }

    public String exportAnalytics() {
       try {
        return adminProvider.exportAnalytics();
       } catch (Exception e) {
         return null;    
    }
    }

}