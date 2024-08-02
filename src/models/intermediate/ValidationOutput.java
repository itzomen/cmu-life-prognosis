package models.intermediate;


public class ValidationOutput {

    public ValidationOutput(String input, boolean valid) {
        this.input = input;
        this.valid = valid;
    }

    private String  input;
    private boolean valid;

    public String getInput() {
        return input;
    }

    public boolean isValid() {
        return valid;
    }
}
