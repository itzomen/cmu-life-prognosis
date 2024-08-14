package views.util.displayutil;

import java.io.Console;
import java.util.Scanner;

import views.util.landingview.LandingView;
// this class is used to handle the interaction with the user input
public class PromptDisplay {
Scanner scanner;
Console console;  

public PromptDisplay(Scanner scanner, Console console){
    this.scanner=scanner;
    this.console=console;
}

public String getText(String message){
    System.out.println("——————————————————————————————————————————————————————————————————————————— * back ^ firstscreen(logout) $ exit");
    System.out.print(message); 
    String input= scanner.nextLine();
    exitProgramCheck(input);
    if(input.equals("*")){
       return null; 
    }
    else if(input.equals("^")){
        LandingView.removingScreens=true;

    }
    return input;     
}

public String getPassword(String message){
    System.out.println("——————————————————————————————————————————————————————————————————————————— * back ^ logout $ exit");
    System.out.print(message); 
    char[] passArray = console.readPassword();
    String input= new String(passArray);
    exitProgramCheck(input);
    if(input.equals("*")) return null; 
    if(input.equals("^")) {
        LandingView.removingScreens=true;
    }
    return new String(passArray);
}

public void exitProgramCheck(String text){
    if(text.equals("$")){
        System.exit(0);
    }
}

}
