package views.userview;

import controllers.usercontroller.AuthenticationController;
import controllers.usercontroller.PatientController;
import models.intermediate.UpdateData;
import models.intermediate.ValidationOutput;
import models.user.Patient;
import views.util.displayutil.PromptDisplay;
import views.util.formatter.CustomFormatter;
import views.util.landingview.DisplayStatus;
import views.util.landingview.HivStatus;
import views.util.landingview.LandingView;
import views.util.validviewutil.ValidConcreteOperation;

import java.time.LocalDate;
import java.util.Scanner;

public class PatientView extends UserView {
    final PatientController patientController;
    PromptDisplay pDisplay = new PromptDisplay(new Scanner(System.in), System.console());
    final AuthenticationController authenticationController;

    public PatientView(PatientController patientController, AuthenticationController authenticationController) {
        this.patientController = patientController;
        this.authenticationController = authenticationController;
    }

    public void start() {
        ValidConcreteOperation vops = new ValidConcreteOperation();
        ValidationOutput vout;
        DisplayStatus displayStatus = new DisplayStatus(pDisplay, authenticationController, vops, (Patient) user);
        outerloop: while (true) {
            if (LandingView.removingScreens)
                break;
            System.out.println("Hello " + user.getfName() + "\n");
            String op = pDisplay.getText(

                    "1. View profile info \n2. Update profile \n3. Get demise schedule \n4. Exit \n");

            if (op == null || LandingView.removingScreens)
                break;
            switch (op) {
                case "1" -> {
                    Patient p = (Patient) user;
                    String hivMess = p.isHIVStatus() ? "p" : "n";
                    String diagDate="-";
                    String takingART="-";
                    String artDate="-";
                    
                    if (p.isHIVStatus()) {
                         diagDate = p.getDiagnsisDate().format(CustomFormatter.formatter);
                         takingART= p.isTakingART() ? "yes": "no";
                        if (p.isTakingART())
                            artDate=p.getArtDate().format(CustomFormatter.formatter);

                    }
                    System.out.format("+---------+---------+---------+-------+---------+---------+---------+-------+-------+-------+---------+-------+-------+------------%n");
                    System.out.format(
                            "| FName    | LName       |  email                  |  DOB            |  ISOcode | status   | diagDate         |  takingART | ARTdate           |%n");
                    System.out.format("+---------+---------+---------+-------+---------+---------+---------+-------+-------+-------+---------+-------+-------+------------%n");

                    String leftAlignment = "| %-10s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s |%n";

                    System.out.format(leftAlignment,p.getfName(),  p.getlName(),
                    p.getEmail(), p.getDob().format(CustomFormatter.formatter),p.getISOCode(),hivMess,diagDate,takingART,artDate );
                    System.out.format("+---------+---------+---------+-------+---------+---------+---------+-------+-------+-------+---------+-------+-------+------------%n");

                    Integer lSpan = patientController.getLifeSpan(p.getEmail());
                    if (lSpan != null) {
                        System.out.println("Years to live: " + lSpan);
                    } else {
                        System.out.println("Error in fetching health information");
                    }
                }
                case "2" -> {
                    Patient p = (Patient) user;
                    System.out.println("Update profile information or leave empty for the default values");
                    vout = vops.performCheck("Update First Name or leave empty: ",
                            "Invalid Name: name shouldn't contain a number",
                            authenticationController::fNameValidWithEmpty, false, pDisplay);
                    if (!vout.isValid())
                        continue;
                    String newFname = vout.getInput().isEmpty() ? p.getfName() : vout.getInput();

                    vout = vops.performCheck("Update last Name or leave empty: ",
                            "Invalid Name: name shouldn't contain a number",
                            authenticationController::fNameValidWithEmpty, false, pDisplay);
                    if (!vout.isValid())
                        continue;
                    String newLname = vout.getInput().isEmpty() ? p.getlName() : vout.getInput();

                    LocalDate newDob = vops.performDateCheckWithEmpty("Update DOB mm/dd/yyyy: ",
                            "Invalid DOB",
                            authenticationController::dateValidWithEmpty, pDisplay, p.getDob());
                    if (newDob == null)
                        continue;

                    vout = vops.performCheck("Update ISO code of your country or empty: ",
                            "Invalid ISO code",
                            authenticationController::isoValidWithEmpty, false, pDisplay);
                    if (!vout.isValid())
                        continue;
                    String newIsoCode = vout.getInput().isEmpty() ? p.getISOCode() : vout.getInput();

                    HivStatus hstat = displayStatus.getStatusInfo(newDob, false);

                    LocalDate newDiagDate = hstat.getDiagDate();
                    LocalDate newArtDate = hstat.getArtDate();
                    String newDiagString = newDiagDate != null ? newDiagDate.format(CustomFormatter.formatter) : "";
                    String newArtString = newArtDate != null ? newArtDate.format(CustomFormatter.formatter) : "";
                    boolean newHivStatus = hstat.isStatus();
                    boolean isTakingART = hstat.isTakingART();
                    if (!hstat.isValid())
                        break;

                    UpdateData newData = new UpdateData(newFname, newLname,
                            newIsoCode, newHivStatus, isTakingART, newDob, newDiagDate, newArtDate);
                    if (patientController.updateProfile(p, newData)) {
                        this.user = new Patient(newFname, newLname, p.getEmail(),
                                newDob, newHivStatus, newDiagDate, isTakingART, newArtDate, newIsoCode, p.getRole());
                        System.out.println("Profile updated succesfully");
                    } else {
                        System.out.println("Error in updating profile");
                    }

                }
                case "3" -> {
                    String path= patientController.getDemiseSchedule(user.getEmail());
                    if(path==null){
                        System.out.println("Unable to export calendar file");
                    }
                    else{
                        System.out.println("Calendar file exported to: "+ path);
                    }
                }
                case "4" -> {
                    System.exit(0);
                }
            }
        }
    }
}