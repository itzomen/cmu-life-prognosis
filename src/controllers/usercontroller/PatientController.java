package controllers.usercontroller;

import java.io.IOException;


import constants.Role;
import dataprovider.userprovider.PatientProvider;
import models.intermediate.UpdateData;
import models.user.Patient;
import validation.ValidationInterface;

public class PatientController implements ValidationInterface {
  final PatientProvider patientProvider;

  public PatientController(PatientProvider patientProvider) {
    this.patientProvider = patientProvider;
  }

  public Integer getLifeSpan(String email) {
    try {
      return patientProvider.getLifeSpan(email);
    } catch (IOException e) {
      return null;
    }
  }

  

  public boolean updateProfile(Patient initPatient,UpdateData udata){
      try{
        patientProvider.updateProfile(new Patient(udata.getfName(), udata.getlName(), initPatient.getEmail(), udata.getDob(),
         udata.ishIVStatus(), udata.getDiagnsisDate(), udata.isTakingART(), 
         udata.getArtDate(), udata.getiSOCode(), Role.PATIENT));
         return true;
      }
      catch(Exception e){
         return false;
      }
  }

  public String getDemiseSchedule(String email){
    try {
      return patientProvider.getDemiseSchedule(email);
       
    } catch (Exception e) {
      return null; 
    }
  }

}
