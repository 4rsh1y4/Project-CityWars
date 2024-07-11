package FXML;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import phase1.Charachter;
import phase1.Player;
import phase1.User;

public class GameController {
    private int mode ;
    private Player player1,player2;

    @FXML private Label test;


    @FXML public void initialize(){
        test.setText(player1.getCharacter().toString() + "  "+player2.getCharacter().toString());
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getMode() {
        return mode;
    }
    public void setPlayers(User user1, User user2, int ch1, int ch2){
        player1 = new Player(user1);
        player1.setCharacter(new Charachter(ch1));
        player2 = new Player(user2);
        player2.setCharacter(new Charachter(ch2));
    }
}
