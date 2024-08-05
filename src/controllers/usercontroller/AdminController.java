
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
        return adminProvider.exportPatientsInfo();
    }

    public String exportAnalytics() {
        return adminProvider.exportAnalytics();
    }

}