package FXML;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import phase1.*;

import java.io.IOException;

public class GameController {
    private int mode ;
    private Player player1,player2;

    @FXML private Label label1,label2,who;
    @FXML private HBox box1,box2;


    @FXML public void init(){
        label1.setText(player1.getNickname()+"       playing as:"+player1.getCharacter() + "          hp:"+player1.getHp());
        label2.setText(player1.getNickname()+"       playing as:"+player2.getCharacter() + "          hp:"+player2.getHp());
//        Game game = new Game(player1.getUser(),player2.getUser());
//        game.startGame();
//        boolean wh = game.getcurrentPlayer();
//        if(wh){
//            who.setText(player1.getNickname() + "  starts the game!");
//        }else{
//            who.setText(player2.getNickname() + "  starts the game!");
//        }
//        game.nextTurn(player1,player2);

        for(int i=0;i<player1.getBoard().length;i++) {
            Card card = player1.getBoard()[i];
            StackPane CharacterPane = new StackPane();
            ImageView cardImageView = null;
            try {
                if (card == null) {
                    String url = "/resources/empty.png";
                    cardImageView = new ImageView(new Image(getClass().getResourceAsStream(url)));

                } else {
                    cardImageView = new ImageView(new Image(getClass().getResourceAsStream(card.url)));
                    cardImageView.setId(card.getName());
                }
            }catch(Exception e){
                e.printStackTrace();
            }
//            cardImageView.setOnMouseClicked(this::handleImageClick1);
            cardImageView.setFitHeight(114);
            cardImageView.setFitWidth(43.5);
            CharacterPane.getChildren().add(cardImageView);
            box1.getChildren().add(CharacterPane);
        }
        for(int i=0;i<player2.getBoard().length;i++) {
            Card card = player2.getBoard()[i];
            StackPane CharacterPane = new StackPane();
            ImageView cardImageView = null;
            try {
                if (card == null) {
                    String url = "/resources/empty.png";
                    cardImageView = new ImageView(new Image(getClass().getResourceAsStream(url)));

                } else {
                    cardImageView = new ImageView(new Image(getClass().getResourceAsStream(card.url)));
                    cardImageView.setId(card.getName());
                }
            }catch(Exception e){
                e.printStackTrace();
            }
//            cardImageView.setOnMouseClicked(this::handleImageClick1);
            cardImageView.setFitHeight(114);
            cardImageView.setFitWidth(43.5);
            CharacterPane.getChildren().add(cardImageView);
            box2.getChildren().add(CharacterPane);
        }
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
