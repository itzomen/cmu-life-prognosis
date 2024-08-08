package views.userview;

import controllers.usercontroller.PatientController;
import models.user.Patient;
import views.util.displayutil.PromptDisplay;

import java.text.SimpleDateFormat;
import java.util.Scanner;

public class PatientView extends UserView{
    final PatientController patientController;
    PromptDisplay pDisplay= new PromptDisplay(new Scanner(System.in), System.console()); 
    public PatientView(PatientController patientController) {
        this.patientController = patientController;
    }

    public void start() {
       Patient patient= (Patient) user;
       outerloop:
       while(true){
           System.out.println("Hello "+ patient.getfName()+ "\n");           
           String op= pDisplay.getText("1. view profile info \n 2. go back \n 3. exit");
           switch(op){
               case "1"->{
                   System.out.println("--------------------------------");
                   System.out.println("First name: "+ patient.getfName()+ " , "+ "Last name: " + patient.getlName());
                   String hivMess= patient.isHIVStatus() ? "positive" : "negative";
                   System.out.println("Email: " + patient.getEmail() + " , " + "Hiv status " + hivMess);
                   if(patient.isHIVStatus()){
                    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy"); 
                    String diagDate = formatter.format(patient.getDiagnsisDate());  
                    System.out.println("Diagnosis date: "+ diagDate + " , " + "taking ART: "+ patient.isTakingART() );
                    if(patient.isTakingART()) System.out.print("ART start date: "+ formatter.format(patient.getArtDate()));
                    System.out.println(" Date of birth: "+ formatter.format(patient.getDob()));
                    System.out.println("Iso code: "+ patient.getISOCode()); 
                   }
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