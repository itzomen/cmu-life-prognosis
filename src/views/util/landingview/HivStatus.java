package views.util.landingview;

import java.time.LocalDate;

public class HivStatus {

    boolean status;
    LocalDate diagDate;
    boolean takingART;
    LocalDate artDate;
    boolean valid;
    public HivStatus(boolean status, LocalDate diagDate, boolean takingART, LocalDate artDate, boolean valid) {
        this.status = status;
        this.diagDate = diagDate;
        this.takingART = takingART;
        this.artDate = artDate;
        this.valid=valid;
    }
    


}
