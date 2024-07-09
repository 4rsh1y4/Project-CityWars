package FXML;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class welcomeController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML private Button LogIn;
    @FXML private  Button SignUp;

    public void sout(){
        System.out.println("clicked");
    }
    private void logIn(){

    }
    public void signup(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/FXML/signUp.fxml"));
        stage = (Stage)(((Node)event.getSource()).getScene().getWindow());
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void login(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/FXML/logIn.fxml"));
        stage = (Stage)(((Node)event.getSource()).getScene().getWindow());
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
