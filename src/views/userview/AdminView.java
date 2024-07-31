package views.userview;

import controllers.usercontroller.AdminController;

import java.util.Scanner;

public class AdminView extends UserView{
    final AdminController adminController;

    public AdminView(AdminController adminController) {
        this.adminController = adminController;
    }


    @Override
    public void start() {
       while(true){
           System.out.println(" 1. Initiate a registration \n 2. go back \n 3. exit");
            Scanner in = new Scanner(System.in);
           String op=in.nextLine();
           if(op.equals("1")){
             String email = in.nextLine();
             System.out.println("generating a UUID");
             String uuid=adminController.initiateRegistration(email);
             if(uuid==null){
                 System.out.println("error generating uuid");
                 continue;
             }
             System.out.println("uuid is: " + uuid);
           }
           else if(op.equals("2")){ break;}
           else if(op.equals("3")){ System.exit(0); }
           else System.out.println("Invalid option");
       }
    }
}