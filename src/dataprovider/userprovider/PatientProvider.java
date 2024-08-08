import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.stream.Collectors;

package dataprovider.userprovider;



public class PatientProvider {
    
    // isoCode is 2 alpha code
    public String getLifeSpan(String email) {

        String lifespan;

        try {
            ProcessBuilder pb = new ProcessBuilder("bash", "-c", "../scripts/Predict.sh ../user-store.txt ../life-expectancy.csv");
            Map<String, String> env = pb.environment();
            env.put("patientEmail", email);

            Process p = pb.start();
            
            // Reading output from the process
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            lifespan = reader.lines().collect(Collectors.joining("\n"));
            
            // System.out.println(lifespan);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lifespan;
    }
    

}
