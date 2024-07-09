package FXML;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import phase1.User;

public class mainMenuController {
    private User currentuser = new User();
    @FXML  public void intialize(){
        System.out.println(currentuser.getUsername());
        startGameAnchor.setDisable(true);
        startGameAnchor.setVisible(false);
    }

    public void setCurrentuser(User currentuser) {
        this.currentuser = currentuser;
    }
    public User getCurrentuser() {
        return currentuser;
    }


    @FXML Button startGameButton;
    @FXML AnchorPane startGameAnchor;
    @FXML Button game1,game2;
    @FXML ImageView GameAnchorClose;

    public void startGame(){
        startGameAnchor.setDisable(false);
        startGameAnchor.setVisible(true);

    }
    public void closeGame(){
        startGameAnchor.setDisable(true);
        startGameAnchor.setVisible(false);
    }

}
