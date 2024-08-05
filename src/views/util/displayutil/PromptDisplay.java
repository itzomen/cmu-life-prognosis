package views.util.displayutil;

import java.io.Console;
import java.util.Scanner;

public class PromptDisplay {
Scanner scanner;
Console console;  

public PromptDisplay(Scanner scanner, Console console){
    this.scanner=scanner;
    this.console=console;
}

public String getText(String message){
    System.out.println(message); 
    String input= scanner.nextLine();
    exitProgramCheck(input);
    if(input.equals("*")){
       return null; 
    }
    return input;     
}

public String getPassword(String message){
    System.out.println(message); 
    char[] passArray = console.readPassword();
    String input= new String(passArray);
    exitProgramCheck(input);
    if(input.equals("*")) return null; 
    return new String(passArray);
}

public void exitProgramCheck(String text){
    if(text.equals("^")){
        System.exit(0);
    }
}

}
