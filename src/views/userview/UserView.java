package views.userview;


import models.user.User;

public abstract class UserView {
    public void setUser(User user) {
        this.user = user;
    }

    protected User user;
    abstract public void start();

}