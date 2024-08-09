import dataprovider.userprovider.AuthenticationProvider;
import views.util.landingview.LandingView;
import controllers.usercontroller.AuthenticationController;


class HelathPrognosisApplication{
    public static void main(String[] a1rgs)
   {
        new LandingView(new AuthenticationController(new AuthenticationProvider())).start();
    }
}
