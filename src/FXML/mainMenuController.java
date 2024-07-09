package FXML;

import phase1.User;

public class mainMenuController {
    private User currentuser = new User();


    public void setCurrentuser(User currentuser) {
        this.currentuser = currentuser;
    }
    public User getCurrentuser() {
        return currentuser;
    }
}
