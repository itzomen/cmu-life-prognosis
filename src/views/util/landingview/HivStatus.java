package views.util.landingview;

import java.util.Date;

public class HivStatus {

    boolean status;
    Date diagDate;
    boolean takingART;
    Date artDate;
    public HivStatus(boolean status, Date diagDate, boolean takingART, Date artDate) {
        this.status = status;
        this.diagDate = diagDate;
        this.takingART = takingART;
        this.artDate = artDate;
    }
    


}
