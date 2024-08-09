package views.util.landingview;

import java.time.LocalDate;

public class HivStatus {

    public boolean isStatus() {
        return status;
    }
    public LocalDate getDiagDate() {
        return diagDate;
    }
    public boolean isTakingART() {
        return takingART;
    }
    public LocalDate getArtDate() {
        return artDate;
    }
    public boolean isValid() {
        return valid;
    }
    private boolean status;
    private LocalDate diagDate;
    private boolean takingART;
    private LocalDate artDate;
    private boolean valid;
    public HivStatus(boolean status, LocalDate diagDate, boolean takingART, LocalDate artDate, boolean valid) {
        this.status = status;
        this.diagDate = diagDate;
        this.takingART = takingART;
        this.artDate = artDate;
        this.valid=valid;
    }
    


}
