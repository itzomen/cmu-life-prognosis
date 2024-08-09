package views.util.landingview;

import controllers.usercontroller.AdminController;
import controllers.usercontroller.AuthenticationController;
import controllers.usercontroller.PatientController;
import dataprovider.userprovider.AdminProvider;
import dataprovider.userprovider.AuthenticationProvider;
import dataprovider.userprovider.PatientProvider;
import views.util.displayutil.Messages;
import views.util.displayutil.PromptDisplay;
import models.intermediate.RegisterData;
import models.intermediate.RegistrationError;
import models.intermediate.ValidationOutput;
import models.user.Patient;
import models.user.User;
import views.userview.AdminView;
import views.userview.PatientView;
import views.userview.UserView;
import views.util.validviewutil.ValidConcreteOperation;

import java.time.LocalDate;
import java.util.Scanner;

public class LandingView {
    final controllers.usercontroller.AuthenticationController authenticationController;
    PromptDisplay pDisplay = new PromptDisplay(new Scanner(System.in), System.console());

    public LandingView(controllers.usercontroller.AuthenticationController authenticationController) {
        this.authenticationController = authenticationController;
    }

    public void start() {
        ValidConcreteOperation vops = new ValidConcreteOperation();
        ValidationOutput vout;
        DisplayStatus displayStatus = new DisplayStatus(pDisplay, authenticationController, vops, null);
        while (true) {

            // code refactored for better collaboration

            String s = pDisplay.getText(" 1. register \n 2. login \n 3. help \n 4. exit \n ");
            if(s==null) break;
            else if (s.equals("1")) {

                String uuid, fName, lName, isoCode, password;
                boolean hivStatus, takingART;

                vout = vops.performCheck("Enter UUID provided by admin or  * to back",
                        "Invalid UUID. Enter again or * to go back",
                        authenticationController::uuidValid, false, pDisplay);
                if (!vout.isValid())
                    continue;
                uuid = vout.getInput();

                vout = vops.performCheck("Enter First Name or *",
                        "Invalid Name: name shouldn't contain a number. Enter again or * to go back",
                        authenticationController::fNameValid, false, pDisplay);
                if (!vout.isValid())
                    continue;
                fName = vout.getInput();

                vout = vops.performCheck(
                        "Enter Last Name",
                        "Invalid last Name: name shouldn't contain a number. Enter again or * to go back",
                        authenticationController::fNameValid, false, pDisplay);
                if (!vout.isValid())
                    continue;
                lName = vout.getInput();

                LocalDate dobj = null;
                dobj = vops.performDateCheck("Enter DOB mm/dd/yyyy", "Invalid DOB. Enter again or * to go back",
                        authenticationController::dateValid, pDisplay);
                if (dobj == null) 
                    continue;

                HivStatus hstat = displayStatus.getStatusInfo(dobj);
                LocalDate diagDate = hstat.getDiagDate();
                LocalDate artDate = hstat.getArtDate();
                hivStatus = hstat.isStatus();
                takingART = hstat.isTakingART();
                if (!hstat.isValid())
                    break;

                vout = vops.performCheck("Enter the ISO code of your country? ",
                        "Invalid ISO code. Enter again or * to go back",
                        authenticationController::isoValid, false, pDisplay);
                if (!vout.isValid())
                    continue;
                isoCode = vout.getInput();

                vout = vops.performCheck(
                        "Enter password ",
                        "Invalid password(length: 8, numbers, uppercase and lowercase letters). Enter again or * to go back",
                        authenticationController::passwordValid, true, pDisplay);
                if (!vout.isValid())
                    continue;
                password = vout.getInput();

                if (!vops.checkPassConfirmation(password, pDisplay))
                    continue;
                RegisterData rdata = new RegisterData(uuid, fName, lName, dobj,
                        hivStatus, artDate, diagDate, takingART, isoCode, password);

                System.out.println("registering a user....");
                RegistrationError rError = authenticationController.register(rdata);
                if (rError.isUuidNotFound()) {
                    System.out.println("UUID not registered");
                } else if (rError.isOtherError()) {
                    System.out.println("Unable to register user");
                } else
                    System.out.println("User successfully registered");
            }

            else if (s.equals("2")) {
                String email = pDisplay.getText("Enter email or * to go back");
                String password = pDisplay.getPassword("Enter password");
                System.out.println("Log in in progress");
                User user = authenticationController.login(email, password);
                if (user == null) {
                    System.out.println("Unable to login");
                    continue;
                }
                UserView uview;
                if (user instanceof Patient) {
                    uview = new PatientView(new PatientController(new PatientProvider()), new AuthenticationController
                    (new AuthenticationProvider()));
                } else {
                    uview = new AdminView(new AdminController(new AdminProvider()));
                }
                uview.setUser(user);
                uview.start();
            } else if (s.equals("3")) {
                Messages.getHelpMessage();
            } else if (s.equals("4")) {
                System.exit(0);
            } else {
                System.out.println("Invalid option");
            }

        }
    }
}
