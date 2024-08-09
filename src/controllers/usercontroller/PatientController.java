package controllers.usercontroller;
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

  public int getLifeSpan(String isoCode) {
    return patientProvider.getLifeSpan(isoCode);
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

  public boolean updatePassword(String email, String password){
     try {
       patientProvider.updatePassword(email, password);
       return true;
     } catch (Exception e) {
       return false; 
     }
  }



}