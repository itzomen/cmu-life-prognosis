import dataprovider.userprovider.AuthenticationProvider;
import controllers.usercontroller.AuthenticationController;
import views.LandingView;


// Replace depencency line
class HelathPrognosisApplication{
    public static void main(String[] args)
   {
        new LandingView(new AuthenticationController(new AuthenticationProvider())).start();
    }
}
