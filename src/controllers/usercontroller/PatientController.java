package controllers.usercontroller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import dataprovider.userprovider.PatientProvider;
import models.intermediate.Expectancy;
import models.user.Patient;
import validation.ValidationInterface;

public class PatientController implements ValidationInterface {
  final PatientProvider patientProvider;

  public PatientController(PatientProvider patientProvider) {
    this.patientProvider = patientProvider;
  }

  public double getLifeSpan(String isoCode) {
    return patientProvider.getLifeSpan(isoCode);
  }

  public Expectancy getDemiseDate(Patient patient, double avLSpan) {
    long avlSpanl= Double.valueOf(avLSpan).longValue();
    LocalDate dob = LocalDate.ofInstant(patient.getDob().toInstant(), ZoneId.systemDefault());
    LocalDate now = LocalDate.ofInstant(new Date().toInstant(), ZoneId.systemDefault());
    LocalDate spanEndDate= dob.plusYears(avlSpanl);

    ZonedDateTime nowTime = now.atStartOfDay(ZoneId.systemDefault());
    ZonedDateTime spanEndTime= spanEndDate.atStartOfDay(ZoneId.systemDefault());
    long maxSpanYear= ChronoUnit.YEARS.between(nowTime, spanEndTime);
    
    
    if (!patient.isHIVStatus()) {
      return new Expectancy(spanEndDate, maxSpanYear);
    }
    else{
      LocalDate diagDate= LocalDate.ofInstant(patient.getDiagnsisDate().toInstant(), ZoneId.systemDefault());
      if(!patient.isTakingART()){
        LocalDate lastDate= diagDate.plusYears(5);
        ZonedDateTime lastTime= lastDate.atStartOfDay(ZoneId.systemDefault());
        long remYearsL= ChronoUnit.YEARS.between(lastTime, nowTime);
        return new Expectancy(lastDate, remYearsL);
      }
      else{
        LocalDate artDate= LocalDate.ofInstant(patient.getArtDate().toInstant(), ZoneId.systemDefault());
        int yDelay= artDate.getYear() - diagDate.getYear();
        if(yDelay==0){
          double remY= 0.9 * maxSpanYear;
          long remYears= (long) Math.ceil(remY);
          LocalDate lastDate= now.plusYears(remYears);
          return new Expectancy(lastDate, remYears);
        }
        else if(yDelay > 0){
          double remY= 0.9 * (Math.pow(0.9, yDelay)) * maxSpanYear;
          long remYears= (long) Math.ceil(remY);
          LocalDate lastDate= now.plusYears(remYears);
          return new Expectancy(lastDate, remYears);
        }
      }
    }
  }
  // round up to the next full year

}