package views.util.landingview;

import java.util.Date;

import controllers.usercontroller.AuthenticationController;
import views.util.displayutil.PromptDisplay;
import views.util.validviewutil.ValidConcreteOperation;

public class DisplayStatus {
  PromptDisplay pDisplay;
  AuthenticationController authenticationController;
  ValidConcreteOperation vops;

  public DisplayStatus(PromptDisplay promptDisplay, AuthenticationController authenticationController,
      ValidConcreteOperation vops) {
    this.pDisplay = promptDisplay;
    this.authenticationController = authenticationController;
    this.vops = vops;
  }

  public HivStatus getStatusInfo() {
    String st;
    Date diagDate = null;
    Date artDate = null;
    boolean stCheck = false;
    boolean takingART = false;
    boolean hivStatus = false;
    while (!stCheck) {
      st = pDisplay.getText(" HIV status \n 1. tested positive \n 2.tested negative");
      if (st == null)
        break;
      else if (st.equals("1") || st.equals("2")) {
        if (st.equals("1")) {
          hivStatus = true;
          diagDate = vops.performDateCheck("when is your diagnosis date mm/dd/yyyy",
              "Invalid date. Enter again or * to go back",
              authenticationController::dateValid, pDisplay);
          if (diagDate == null)
            continue;

          String art = pDisplay.getText("Are you currently on ART? 1. yes 2.No");
          if (art == null) {
            break;
          } else if (art.equals("1") || art.equals("2")) {
            if (art.equals("1")) {
              takingART = true;
              artDate = vops.performDateCheck("Enter date of start for the treatment", "invalid date",
                  authenticationController::dateValid, pDisplay);
              if (artDate == null)
                continue;
            }
          } else {
            System.out.println("Invalid option try again or * to go back");
          }
        }
        stCheck=true;
      } else {
        System.out.println("Invalid option try again or * to go back");
      }
    }
    return new HivStatus(hivStatus, diagDate, takingART, artDate);
  }
}
