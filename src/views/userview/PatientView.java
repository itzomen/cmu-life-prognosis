package views.userview;

import controllers.usercontroller.PatientController;

import java.util.Scanner;

public class PatientView extends UserView{
    final PatientController patientController;

    public PatientView(PatientController patientController) {
        this.patientController = patientController;
    }

    @Override
    public void start() {
       outerloop:
       while(true){
           System.out.println("Hello "+ user.getFName());
           System.out.println("1. view profile info \n 2. go back \n 3. exit");
           Scanner in = new Scanner(System.in);
           String op= in.nextLine();
           switch(op){
               case "1"->{
                   System.out.println("First name: "+ user.getFName());
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