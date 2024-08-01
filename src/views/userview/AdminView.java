package views.userview;

import controllers.usercontroller.AdminController;
import models.intermediate.ValidationOutput;
import views.util.ValidConcreteOperation;

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
        while (true) {
            System.out.println("Hello "+ user.getFName());
            System.out.println("""
                     1. Initiate a registration\s
                     2. Export user files \s
                     3. Get analytical information \s
                     4. go back\s
                     5. exit""");
            Scanner in = new Scanner(System.in);
            String op = in.nextLine();
            if (op.equals("1")) {
                System.out.println("Enter email");

                vout = vops.performCheck("Invalid email. Enter again or * to go back",
                        adminController::emailValid
                );
                if (!vout.isValid()) continue;
                String email= vout.getInput();
                System.out.println("generating a UUID");
                String uuid = adminController.initiateRegistration(email);
                if (uuid == null) {
                    System.out.println("error generating uuid");
                    continue;
                }
                System.out.println("uuid is: " + uuid);
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