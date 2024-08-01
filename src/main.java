import dataprovider.userprovider.AuthenticationProvider;
import controllers.usercontroller.AuthenticationController;
import views.LandingView;

class HelathPrognosisApplication{
    public static void main(String[] args)
   {
        new LandingView(new AuthenticationController(new AuthenticationProvider())).start();
    }
}
