package views.userview;

import controllers.usercontroller.PatientController;
import views.util.displayutil.PromptDisplay;

import java.util.Scanner;

public class PatientView extends UserView{
    final PatientController patientController;
    PromptDisplay pDisplay= new PromptDisplay(new Scanner(System.in), System.console()); 
    public PatientView(PatientController patientController) {
        this.patientController = patientController;
    }

    public void start() {
       outerloop:
       while(true){
           System.out.println("Hello "+ user.getfName()+ "\n");           
           String op= pDisplay.getText("1. view profile info \n 2. go back \n 3. exit");
           switch(op){
               case "1"->{
                   System.out.println("First name: "+ user.getfName());
                   System.out.println("Last name: " + user.getlName());
                   System.out.println("Email: " + user.getEmail());
               }

               case "2"->{
                   break outerloop;
               }
               case "3"->{
                   System.exit(0);
               }
           }
       }
    }
}