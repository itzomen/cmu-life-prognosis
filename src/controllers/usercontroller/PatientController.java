package controllers.usercontroller;

import dataprovider.userprovider.PatientProvider;
import validation.ValidationInterface;

public class PatientController implements ValidationInterface {
  final PatientProvider patientProvider;

    public PatientController(PatientProvider patientProvider) {
        this.patientProvider = patientProvider;
    }
}