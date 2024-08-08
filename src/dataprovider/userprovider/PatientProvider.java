package dataprovider.userprovider;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.stream.Collectors;

public class PatientProvider {
    
    // isoCode is 2 alpha code
    public int getLifeSpan(String email) {
        int lifespan = 0;  // Default lifespan initialization

        try {
            ProcessBuilder pb = new ProcessBuilder("bash", "-c", "../scripts/Predict.sh ../user-store.txt ../life-expectancy.csv");
            Map<String, String> env = pb.environment();
            env.put("patientEmail", email);

            Process p = pb.start();
            
            // Reading output from the process
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String output = reader.lines().collect(Collectors.joining("\n"));

            lifespan = Integer.parseInt(output);  // Correctly convert string to integer
            
            // System.out.println(lifespan);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lifespan;
    }
}
