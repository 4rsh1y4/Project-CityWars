package FXML;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import phase1.Charachter;
import phase1.User;

import java.awt.event.ActionEvent;
import java.io.IOException;

//this class is to be started only after login or by setting user1 and user2;
public class characterSelectionController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    private User user1,user2;
    private Integer ch1,ch2;
    private int mode;
    private MediaPlayer mediaPlayer;

    @FXML private HBox boxCharacters1,boxCharacters2;
    @FXML private AnchorPane startAnchor;
    @FXML private ImageView mode1,mode2;
    @FXML private Button startGame;

    @FXML public void initialize(){
        startAnchor.setOpacity(0.6);
        startAnchor.setDisable(true);
        for(int i=0;i<4;i++) {
            StackPane CharacterPane = new StackPane();
            ImageView cardImageView = new ImageView(new Image(getClass().getResourceAsStream(Charachter.urls[i])));
            cardImageView.setOnMouseClicked(this::handleImageClick1);
            cardImageView.setFitHeight(180);
            cardImageView.setFitWidth(135);
            cardImageView.setId(Charachter.names[i]);
            CharacterPane.getChildren().add(cardImageView);
            boxCharacters1.getChildren().add(CharacterPane);
        }
        for(int i=0;i<4;i++) {
            StackPane CharacterPane = new StackPane();
            ImageView cardImageView = new ImageView(new Image(getClass().getResourceAsStream(Charachter.urls[i])));
            cardImageView.setOnMouseClicked(this::handleImageClick2);
            cardImageView.setFitHeight(180);
            cardImageView.setFitWidth(135);
            cardImageView.setId(Charachter.names[i]);
            CharacterPane.getChildren().add(cardImageView);
            boxCharacters2.getChildren().add(CharacterPane);
        }
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public void handleImageClick1(MouseEvent event){
        ImageView cardImageView = (ImageView) event.getSource();
        boxCharacters1.setDisable(true);
        boxCharacters1.setOpacity(0.5);
        for(int i=0;i<4;i++){
            if(cardImageView.getId().equals(Charachter.names[i])) ch1 = i;
        }
        System.out.println(ch1);
    }
    public void handleImageClick2(MouseEvent event){
        ImageView cardImageView = (ImageView) event.getSource();
        boxCharacters2.setDisable(true);
        boxCharacters1.setOpacity(0.5);
        for(int i=0;i<4;i++){
            if(cardImageView.getId().equals(Charachter.names[i])) ch2 = i;
        }
        System.out.println(ch2);
        if(ch1!=null){
            gomode();
        }
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }
    public void setUser2(User user2) {
        this.user2 = user2;
    }
    public void gomode(){
        startAnchor.setOpacity(1);
        startAnchor.setDisable(false);
    }
    public void goback(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/mainMenu.fxml"));
        root = loader.load();
        mainMenuController mainController = loader.getController();
        mainController.setCurrentuser(user1);
        mainController.setSecondUser(user2);
        mainController.init();
        stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void selectMode1(){
        mode2.setOpacity(0.6);
        startGame.setDisable(false);
        mode = 1;
    }

    public void selectMode2(){
        mode1.setOpacity(0.6);
        startGame.setDisable(false);
        mode = 2;
    }
    public void startgame(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Game.fxml"));
        root = loader.load();
        GameController GameController = loader.getController();
        GameController.setPlayers(user1,user2,ch1,ch2);
        GameController.setMode(mode);
        GameController.init();
        stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
