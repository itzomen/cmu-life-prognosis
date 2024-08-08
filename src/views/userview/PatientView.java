package views.userview;

import controllers.usercontroller.PatientController;
import models.user.Patient;
import views.util.displayutil.PromptDisplay;

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
           String op= pDisplay.getText(" 1. view profile info \n 2. Update profile \n 3. go back \n 4. exit");
           switch(op){
               case "1"->{
                   System.out.println("--------------------------------");
                   System.out.println("First name: "+ patient.getfName()+ " , "+ "Last name: " + patient.getlName());
                   String hivMess= patient.isHIVStatus() ? "positive" : "negative";
                   System.out.println("Email: " + patient.getEmail() + " , " + "Hiv status " + hivMess);
                   if(patient.isHIVStatus()){
                    String diagDate = patient.getDiagnsisDate().toString();  
                    System.out.println("Diagnosis date: "+ diagDate + " , " + "taking ART: "+ patient.isTakingART() );
                    if(patient.isTakingART()) System.out.print("ART start date: "+ patient.getArtDate().toString());
                    System.out.println(" Date of birth: "+ patient.getDob().toString());
                    System.out.println("Iso code: "+ patient.getISOCode());
                    System.out.println("\nHealth prediction information: \n\n");
        
                    // System.out.println("Date of Demise: "+ exp.getDate().toString()
                    // );
                    // System.out.println("Years to live: " + exp.getRemYears()+ "\n\n"); 
                    System.out.println("--------------------------------\n\n");

                   }
               }
               case "2"->{
                    System.out.println("Update profile information or leave empty for the default values"); 
                    System.out.println("--------------------------------");

               }
               case "3"->{
                   break outerloop;
               }
               case "4"->{
                   System.exit(0);
               }
           }
       }
    }
}