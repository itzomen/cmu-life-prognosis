import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.stream.Collectors;

public class LifeExpactancyClassExample {
    public static void main(String[] args) {
        try {
            ProcessBuilder pb = new ProcessBuilder("bash", "-c", "./lifeExpectancy.sh ../user-store.txt ../life-expectancy.csv ");
            Map<String, String> env = pb.environment();
            env.put("patientEmail", "abel@email.com");

            Process p = pb.start();
            
            // Reading output from the process
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String output = reader.lines().collect(Collectors.joining("\n"));
            
            System.out.println(output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
