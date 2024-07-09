package FXML;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import phase1.DatabaseHelper;
import phase1.User;


import javax.security.auth.kerberos.KerberosTicket;
import java.io.IOException;
import java.util.List;

public class logInController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private String Password;
    private static String[] Questions = {"What is your father's name ?",
            "What is your favourite color ?"
            , "What was the name of your first pet?"};
    private User currentuser = new User();

    @FXML TextField textField,textField1,textField11;
    @FXML PasswordField passwordField,passwordField1,passwordField11;

    @FXML Button login,login1,login11,login12;

    @FXML AnchorPane forgot;
    @FXML Button checkusername;

    @FXML Label prompt,question,labelPass;
    @FXML
    ImageView pass;


    @FXML public void initialize(){
        forgot.setVisible(false);
        forgot.setDisable(true);
    }

    public void Login(ActionEvent event) throws IOException {
        String username = textField.getText();
        String password = passwordField.getText();

        if(!DatabaseHelper.checkUser(username,password)){
            login.setStyle("-fx-background-color: red");

        }else{
            login.setStyle("-fx-background-color:  #76ff03;");
            currentuser = User.getUser(username);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/mainMenu.fxml"));
            root = loader.load();
            mainMenuController mainController = loader.getController();
            mainController.setCurrentuser(currentuser);
            stage = (Stage)(((Node)event.getSource()).getScene().getWindow());
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    public void forgotpassword(ActionEvent event){
        textField.setDisable(true);
        passwordField.setDisable(true);
        login.setDisable(true);
        forgot.setVisible(true);
        forgot.setDisable(false);
    }
    public void checkusername(ActionEvent event){
        String username = textField1.getText();
        if(!DatabaseHelper.ExistUsername(username)){
        prompt.setText("Username doesn't exist");
        login1.setStyle("-fx-background-color: red");
        }else{
            currentuser = User.getUser(username);
            assert currentuser != null;
            question.setText(Questions[currentuser.getQuestionNumber()-1]);
            textField11.setOpacity(1);
            login11.setOpacity(1);
        }
    }
    public void checkAnswer(ActionEvent event){
        String answer = textField11.getText();
        System.out.println(answer);
        System.out.println(currentuser.getQuestionAnswer());
        if(!answer.equals(currentuser.getQuestionAnswer())){
            textField11.setText("");
            textField11.setPromptText("Try again");
        }else{
            pass.setOpacity(1);
            passwordField1.setOpacity(1);
            passwordField11.setOpacity(1);
            login12.setOpacity(1);
        }
    }
    public void checkpassword() throws InterruptedException {
        String pass1 = passwordField1.getText();
        String pass2 = passwordField11.getText();
        if (!pass1.equals(pass2) && !(pass1.equals("")||pass2.equals(""))) {
            labelPass.setText("Your password doesn't match!");
            login12.setStyle("-fx-background-color: red");
        } else {
            String reg8char = ".{8,}$";
            String regChars = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[-+_!@#$%^&*.,?]).*$";
            if(pass1.equals(currentuser.getPassword())){
                labelPass.setText("Your password can't be the same as before!");
                login12.setStyle("-fx-background-color: red");
            }else if (!pass1.matches(reg8char) && pass1.matches(regChars)) {
                labelPass.setText("Your password should be atleast 8 characters");
                login12.setStyle("-fx-background-color: red");
            } else if (!pass1.matches(regChars) && pass1.matches(reg8char)) {
                labelPass.setText("Your password should contain lower and upper and special chars");
                login12.setStyle("-fx-background-color: red");
            } else if (!pass1.matches(regChars) && !pass1.matches(reg8char)) {
                labelPass.setText("Your password is trash");
                login12.setStyle("-fx-background-color: red");
            } else {
                currentuser.setPassword(pass1);
                labelPass.setText("passed.");
                Thread.sleep(100);
                DatabaseHelper.changePassword(currentuser.getUsername(), currentuser.getPassword());
                forgot.setVisible(false);
                forgot.setDisable(true);
                textField.setDisable(false);
                passwordField.setDisable(false);
                login.setDisable(false);
            }

        }
    }
    public void revertColor(MouseEvent event){
        labelPass.setText("");
        login12.setStyle("-fx-background-color:  #76ff03;");
    }

}
