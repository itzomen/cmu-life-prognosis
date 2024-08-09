package dataprovider.userprovider;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AdminProvider {

    public String exportPatientsInfo() throws IOException, InterruptedException {

        ProcessBuilder processBuilder = new ProcessBuilder("./scripts/export-patients.sh");
        Process process = processBuilder.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }
        int exitCode = process.waitFor();
        if (exitCode == 0) {
            return output.toString();
        } else {
            throw new RuntimeException("Script failed with exit code " + exitCode);
        }
    }

    public String exportAnalytics() {
        return "";
    }

    public String initiateRegistration(String email) throws IOException, InterruptedException {

        ProcessBuilder processBuilder = new ProcessBuilder("./scripts/initiate_user.sh", email);
        Process process = processBuilder.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }
        int exitCode = process.waitFor();
        if (exitCode == 0) {
            return output.toString().split(":")[1].trim(); // Extract UUID from the output
        } else  {
            throw new RuntimeException("Script failed with exit code " + exitCode);
        }

    }
}