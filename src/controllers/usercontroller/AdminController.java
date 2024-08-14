package controllers.usercontroller;

import dataprovider.userprovider.AdminProvider;
import models.intermediate.ValidationOutput;
import validation.ValidationInterface;
import views.util.exceptions.DuplicateEmail;

public class AdminController implements ValidationInterface {
    final AdminProvider adminProvider;
    public AdminController(AdminProvider adminProvider) {this.adminProvider=adminProvider;}

    public ValidationOutput initiateRegistration(String email){
        try{
            String uuid= adminProvider.initiateRegistration(email);
            return new ValidationOutput(uuid, true);
        }
        catch(DuplicateEmail e){
            return new ValidationOutput("", false);
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

    public boolean exportAnalytics() {
       try {
        adminProvider.exportAnalytics();
        return true;
       } catch (Exception e) {
         return false;
    }
    }

}