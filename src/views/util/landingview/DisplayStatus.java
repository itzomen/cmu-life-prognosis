package views.util.landingview;

import java.time.LocalDate;

import controllers.usercontroller.AuthenticationController;
import models.user.Patient;
import views.util.displayutil.PromptDisplay;
import views.util.validviewutil.ValidConcreteOperation;

public class DisplayStatus {
  PromptDisplay pDisplay;
  AuthenticationController authenticationController;
  ValidConcreteOperation vops;
  Patient patient;

  public DisplayStatus(PromptDisplay promptDisplay, AuthenticationController authenticationController,
      ValidConcreteOperation vops, Patient patient) {
    this.pDisplay = promptDisplay;
    this.authenticationController = authenticationController;
    this.vops = vops;
    this.patient=patient;
  }

  public HivStatus getStatusInfo(LocalDate dob) {
    String st;
    LocalDate diagDate = null;
    LocalDate artDate = null;
    boolean stCheck = false;
    boolean takingART = false;
    boolean hivStatus = false;
    boolean valid=false;
    while (!stCheck) {
      st = pDisplay.getText(" HIV status \n 1. tested positive \n 2.tested negative");
      if (st == null){
         break; 
      }
      else if (st.equals("1") || st.equals("2")) {
        if (st.equals("1")) {
          hivStatus = true;
          String pMessage= "when is your diagnosis date mm/dd/yyyy";
          String errMessage="Invalid date. Enter again or * to go back";
          diagDate = patient!=null?  
          vops.performDateRangeWithEmpty(pMessage,
              errMessage,
              authenticationController::dateValidWithEmpty, pDisplay, dob, patient.getDiagnsisDate())
          :vops.performDateRangeCheck(pMessage,
              errMessage,
              authenticationController::dateValid, pDisplay, dob);
          if (diagDate == null)
            continue;

          String art = pDisplay.getText("Are you currently on ART? 1. yes 2.No");
          if (art == null) {
            break;
          } else if (art.equals("1") || art.equals("2")) {
            if (art.equals("1")) {
              takingART = true;
              pMessage="Enter date of start for the treatment";

              artDate = patient!=null? 
              vops.performDateRangeWithEmpty(pMessage,
              errMessage,
              authenticationController::dateValidWithEmpty, pDisplay, diagDate, patient.getArtDate())
              :vops.performDateRangeCheck(pMessage, errMessage,
                  authenticationController::dateValid, pDisplay, diagDate);
              if (artDate == null)
                continue;
            }
          } else {
            System.out.println("Invalid option try again or * to go back");
            continue;
          }
        }
        stCheck=true;
        valid=true;
      } else {
        System.out.println("Invalid option try again or * to go back");
      }
    }
    return new HivStatus(hivStatus, diagDate, takingART, artDate,valid);
  }
}
