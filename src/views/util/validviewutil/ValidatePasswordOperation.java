package views.util.validviewutil;

import models.user.User;

public interface ValidatePasswordOperation {
    User passwordCheck(String email, String password);
}
