package views.util.displayutil;
public class Messages{
  public static void getHelpMessage(){
    System.out.println("   Use the numberings to choose the options \n" +
    "   beware of input validations as names shouldn't contain number \n" +
    "   Password should be at least a length of 8, contain upper and lowercase letters \n"+
    "   UUID and email should be in a valid format \n" +
            "   Use * to go back to previous options \n" +
    "   Use exit options to stop the program \n"
    );
  }
}