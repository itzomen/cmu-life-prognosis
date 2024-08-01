package views;

import constants.Role;
import controllers.usercontroller.AdminController;
import controllers.usercontroller.PatientController;
import dataprovider.userprovider.AdminProvider;
import dataprovider.userprovider.PatientProvider;
import models.intermediate.RegisterData;
import models.intermediate.ValidationOutput;
import models.user.User;
import views.userview.AdminView;
import views.userview.PatientView;
import views.userview.UserView;
import views.util.ValidConcreteOperation;
import views.util.ValidateOperation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class LandingView {
  final controllers.usercontroller.AuthenticationController authenticationController;

    public LandingView(controllers.usercontroller.AuthenticationController authenticationController) {
        this.authenticationController = authenticationController;
    }

    public void start(){
     while(true){

         // test with a string
         // input validation should be done...
         System.out.println(" 1. register \n 2. login \n 3. exit \n ");
         Scanner in = new Scanner(System.in);
         String s= in.nextLine();
         ValidConcreteOperation vops= new ValidConcreteOperation();
             ValidationOutput vout;

         if(s.equals("1")){

             String uuid="",fName="", lName="", dob, diagnosisDate="", isoCode="", password="";
             boolean hivStatus=false, takingART=false;

             System.out.println("Enter UUID provided by admin or  * to back");
             vout= vops.performCheck("Invalid UUID. Enter again or * to go back",
                     authenticationController::uuidValid
             );
             if(!vout.isValid()) continue;
             uuid= vout.getInput();

             System.out.println("Enter First Name or *");
             vout= vops.performCheck("Invalid First Name. Enter again or * to go back",
                     authenticationController::fNameValid
             );
             if(!vout.isValid()) continue;
             fName= vout.getInput();

             System.out.println("Enter Last Name");

             vout= vops.performCheck("Invalid last Name. Enter again or * to go back",
                     authenticationController::fNameValid
             );
             if(!vout.isValid()) continue;
             lName= vout.getInput();

             System.out.println("Enter DOB mm/dd/yyyy");
             Date dobj=null;
             dobj= vops.performDateCheck("Invalid DOB. Enter again or * to go back",
                     authenticationController::dateValid
             );
             if(dobj==null) continue;

             System.out.println(" HIV status \n 1. tested positive \n 2.tested negative");
             String st;
             Date dobj2= null;
             boolean stCheck=false;
             while(!stCheck){
                 st=in.nextLine();
                 if(st.equals("*")) break;
                 else if(st.equals("1") || st.equals("2")) {
                     if (st.equals("1")) {
                         hivStatus = true;
                         System.out.println("when is your diagnosis date mm/dd/yyyy");

                         dobj2= vops.performDateCheck("Invalid date. Enter again or * to go back",
                                 authenticationController::dateValid
                         );
                         if(dobj2==null) continue;

                         System.out.println("Are you currently on ART? 1. yes 2.No");
                         String art = in.nextLine();
                         if (art.equals("1")) {
                             takingART = true;
                         }
                     }
                     stCheck = true;
                 }
                 else{
                     System.out.println("Invalid option try again or * to go back");
                 }
             }
             if(!stCheck) continue;
             System.out.println("Enter the ISO code of your country? ");

             vout= vops.performCheck("Invalid ISO code. Enter again or * to go back",
                     authenticationController::isoValid
             );
             if(!vout.isValid()) continue;
             isoCode= vout.getInput();

             System.out.println("Enter password ");

             vout= vops.performCheck("Invalid password(length: 8, numbers, uppercase and lowercase letters). Enter again or * to go back",
                     authenticationController::passwordValid
             );
             if(!vout.isValid()) continue;
             password= vout.getInput();

             System.out.println("Confirm password ");
             String cpass= in.nextLine();
             while(!cpass.equals(password)){
                 System.out.println("Password don't match confirm again or use * to go back");
                 cpass= in.nextLine();
                 if(cpass.equals("*")) continue;
             }
             RegisterData rdata= new RegisterData(uuid, fName, lName ,dobj,
                     hivStatus,dobj2, dobj2, takingART, isoCode, password
             );

             System.out.println("registering a user....");
           if(!authenticationController.register(rdata)){
               System.out.println("Unable to register user");
            }
           else System.out.println("User successfully registered");
         }

         else if(s.equals("2")){
            System.out.println("Enter email or * to go back");
            String email = in.nextLine();
            if(email.equals("*")) continue;
            System.out.println("Enter password");
            String password = in.nextLine();
            System.out.println("Log in in progress");
            User user = authenticationController.login(email, password);
            if(user==null){
                System.out.println("Unable to login");
                continue;
            }
            UserView uview;
            if(user.getRole()== Role.PATIENT){
                 uview= new PatientView(new PatientController(new PatientProvider()))  ;
           }
            else{
                 uview= new AdminView(new AdminController(new AdminProvider()));
            }
             uview.setUser(user);
             uview.start();
         }
         else if(s.equals("3")){
            System.exit(0);
         }
         else {
             System.out.println("Invalid option");
         }

     }
  }

}
