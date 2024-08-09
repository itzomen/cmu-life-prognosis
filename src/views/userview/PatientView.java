package views.userview;

import controllers.usercontroller.AuthenticationController;
import controllers.usercontroller.PatientController;
import models.intermediate.UpdateData;
import models.intermediate.ValidationOutput;
import models.user.Patient;
import views.util.displayutil.Messages;
import views.util.displayutil.PromptDisplay;
import views.util.formatter.CustomFormatter;
import views.util.landingview.DisplayStatus;
import views.util.landingview.HivStatus;
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
        Patient patient = (Patient) user;
        ValidConcreteOperation vops = new ValidConcreteOperation();
        ValidationOutput vout;
        DisplayStatus displayStatus = new DisplayStatus(pDisplay, authenticationController, vops, patient);
        outerloop: while (true) {
            System.out.println("Hello " + patient.getfName() + "\n");
            String op = pDisplay.getText(

                    " 1. View profile info \n 2. Update profile \n 3. Update password \n 4. go back \n 5. exit");

            if(op==null ) break;
            switch (op) {
                case "1" -> {
                    System.out.println("--------------------------------");
                    System.out
                            .println("First name: " + patient.getfName() + " , " + "Last name: " + patient.getlName());
                    String hivMess = patient.isHIVStatus() ? "positive" : "negative";
                    System.out.println("Email: " + patient.getEmail() + " , " + "Hiv status: " + hivMess);
                    if (patient.isHIVStatus()) {
                        String diagDate = patient.getDiagnsisDate().format(CustomFormatter.formatter);
                        System.out.println(
                                "Diagnosis date: " + diagDate + " , " + "taking ART: " + patient.isTakingART());
                        if (patient.isTakingART())
                            System.out

                                    .print("ART start date: ," + patient.getArtDate().format(CustomFormatter.formatter));
                        System.out.println(" Date of birth: " + patient.getDob().format(CustomFormatter.formatter));
                        System.out.println("Iso code: " + patient.getISOCode());
                        Integer lSpan= patientController.getLifeSpan(patient.getEmail());
                        if(lSpan!=null){
                            System.out.println("\nHealth prediction information: \n\n");
                            System.out.println("Years to live: " + lSpan);
                        }
                        else{
                            System.out.println("Error in fetching health information");
                        }

                        System.out.println("--------------------------------\n\n");

                    }
                }
                case "2" -> {
                    System.out.println("Update profile information or leave empty for the default values");
                    System.out.println("--------------------------------");
                    vout = vops.performCheck("Update First Name, leave empty or *",
                            "Invalid Name: name shouldn't contain a number. Enter again or * to go back",
                            authenticationController::fNameValidWithEmpty, false, pDisplay);
                    if (!vout.isValid())
                        continue;
                    String newFname = vout.getInput().isEmpty() ? patient.getfName() : vout.getInput();

                    vout = vops.performCheck("Update last Name, leave empty or *",
                            "Invalid Name: name shouldn't contain a number. Enter again or * to go back",
                            authenticationController::fNameValidWithEmpty, false, pDisplay);
                    if (!vout.isValid())
                        continue;
                    String newLname = vout.getInput().isEmpty() ? patient.getfName() : vout.getInput();

                    LocalDate newDob = vops.performDateCheckWithEmpty("Update DOB mm/dd/yyyy",
                            "Invalid DOB. Enter again or * to go back",
                            authenticationController::dateValidWithEmpty, pDisplay, patient.getDob());
                    if (newDob == null)
                        continue;

                    vout = vops.performCheck("Update ISO code of your country, empty, or * ",
                            "Invalid ISO code. Enter again or * to go back",
                            authenticationController::isoValidWithEmpty, false, pDisplay);
                    if (!vout.isValid())
                        continue;
                    String newIsoCode = vout.getInput().isEmpty() ? patient.getISOCode() : vout.getInput();

                    HivStatus hstat = displayStatus.getStatusInfo(newDob);
                    LocalDate newDiagDate = hstat.getDiagDate();
                    LocalDate newArtDate = hstat.getArtDate();
                    String newDiagString= newDiagDate!=null ? newDiagDate.format(CustomFormatter.formatter) : "";
                    String newArtString= newArtDate!=null ? newArtDate.format(CustomFormatter.formatter) : "";
                    boolean newHivStatus = hstat.isStatus();
                    boolean isTakingART = hstat.isTakingART();
                    if (!hstat.isValid())
                        break;
                    
                        UpdateData newData= new UpdateData(newFname, newLname, 
                        newIsoCode, newHivStatus, isTakingART, newDob, newDiagDate, newArtDate);
                        System.out.println(newFname+ " "+ newLname + " " + newIsoCode + " "
                        + newHivStatus + " " + isTakingART + " " + newDob.format(CustomFormatter.formatter) + " " + 
                        newDiagString + " " + newArtString
                        );
                        if(patientController.updateProfile(patient, newData)){
                            System.out.println("Profile updated succesfully");
                        }
                        else{
                            System.out.println("Password updated successfully");
                        }

                }
                case "3" -> {
                    System.out.println("Update Password");
                    String password = "";
                    System.out.println("--------------------------------");

                    String pass = vops.checkPasswordValidity(patient.getEmail(), pDisplay,
                            authenticationController::login);
                    if (pass == null)
                        continue outerloop;
                    vout = vops.performCheck(
                            "Enter new password ",
                            Messages.invalidPassword,
                            authenticationController::passwordValid, true, pDisplay);
                    if (!vout.isValid())
                        continue outerloop;
                    password = vout.getInput();

                    if (!vops.checkPassConfirmation(password, pDisplay))
                        continue outerloop;

                    if (!patientController.updatePassword(patient.getEmail(), password)) {
                        System.out.println("Unable to update password");
                    } else {
                        System.out.println("Password updated successfully");
                    }

                }
                case "4" -> {
                    break outerloop;
                }
                case "5" -> {
                    System.exit(0);
                }
            }
        }
    }
}