package views.userview;

import controllers.usercontroller.AdminController;
import models.intermediate.ValidationOutput;
import views.util.displayutil.PromptDisplay;
import views.util.validviewutil.ValidConcreteOperation;

import java.util.Scanner;

public class AdminView extends UserView {
    final AdminController adminController;

    public AdminView(AdminController adminController) {
        this.adminController = adminController;
    }

    @Override
    public void start() {
        ValidConcreteOperation vops = new ValidConcreteOperation();
        ValidationOutput vout;
        PromptDisplay pDisplay= new PromptDisplay(new Scanner(System.in), System.console());
        while (true) {
            System.out.println("Hello "+ user.getfName());
            String op = pDisplay.getText("""
                     1. Initiate a registration\s
                     2. Export user files \s
                     3. Get analytical information \s
                     4. go back\s
                     5. exit\n""");
                if(op==null) break;
                else if (op.equals("1")) {
                vout = vops.performCheck("Enter email","Invalid email. Enter again or * to go back",
                        adminController::emailValid, false, pDisplay
                );
                if (!vout.isValid()) continue;
                String email= vout.getInput();
                System.out.println("generating a UUID");
                ValidationOutput uuidOut= adminController.initiateRegistration(email); 
                
                if (uuidOut == null) {
                    System.out.println("error generating uuid");
                
                }
                else if(!uuidOut.isValid()){
                    System.out.println("Duplicate email registration");
                }
                else{
                    String uuid= uuidOut.getInput();
                    System.out.println("uuid is: " + uuid);
                } 
            }else if(op.equals("2")) {
                String fPath= adminController.exportPatientsInfo();
                if(fPath == null) {
                    System.out.println("Unable to export file");
                    continue;
                }
                System.out.println("file exported to: "+ fPath);
            }
            else if(op.equals("3")){
                String fPath= adminController.exportAnalytics();
                if(fPath == null) {
                    System.out.println("Unable to export file");
                    continue;
                }
                System.out.println("file exported to: "+ fPath);
            }
            else if (op.equals("4")) {
                break;
            } else if (op.equals("5")) {
                System.exit(0);
            } else System.out.println("Invalid option");
        }
    }
}