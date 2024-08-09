import dataprovider.userprovider.AuthenticationProvider;
import views.util.landingview.LandingView;
import controllers.usercontroller.AuthenticationController;


class HelathPrognosisApplication{
    public static void main(String[] args)
   {
        new LandingView(new AuthenticationController(new AuthenticationProvider())).start();
    }
}
