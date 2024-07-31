package views.util;

import models.intermediate.ValidationOutput;

public interface ValidateOperation {
    boolean check(String token);
}
