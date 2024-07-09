package FXML;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import phase1.DatabaseHelper;

import java.io.IOException;

public class signUpController {
    private String Username ;
    private String Password ;
    private String Nickname;
    private String Email;

    @FXML Label labelEmail;
    @FXML Label labelUsername;
    @FXML Label labelNickname;

    @FXML Button checkUsername;
    @FXML Button checkNickname;
    @FXML Button checkPassword;

    @FXML TextField usernameText;
    @FXML TextField emailText;
    @FXML TextField nicknameText;

    @FXML PasswordField passwordText1;
    @FXML PasswordField passwordText2;



    @FXML public void initialize(){
        checkUsername.setDisable(false);
        usernameText.setDisable(false);
        emailText.setDisable(true);
        nicknameText.setDisable(true);
        checkNickname.setDisable(true);

        checkUsername.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER){
                checkusername(null);
            }
        });
    }

    public void revertcolor(KeyEvent event){
        labelUsername.setText("");
        checkUsername.setStyle("-fx-background-color:  #ffefc2");
    }
    public void revertcolor1(KeyEvent event){
        labelEmail.setText("");
        labelNickname.setText("");
        checkNickname.setStyle("-fx-background-color:  #ffefc2");
    }
    public void revertcolor2(KeyEvent event){
        passwordText1.setText("");
        passwordText2.setText("");
        checkPassword.setStyle("-fx-background-color:  #ffefc2");

    }
    public void checkusername(ActionEvent event){
        String username = usernameText.getText();
        if (!username.matches("^[a-zA-Z0-9_]*$")) {
            labelUsername.setText("Username can have only letters and number and underscores!");
            checkUsername.setStyle("-fx-background-color: red");
        }else{
                if (DatabaseHelper.ExistUsername(username)) {
                    labelUsername.setText(username+" is already taken!");
                    checkUsername.setStyle("-fx-background-color: red");
                }else{
                    labelUsername.setText("Passed.");
                    checkUsername.setStyle("-fx-background-color: green");
                    checkUsername.setDisable(true);
                    usernameText.setDisable(true);
                    checkNickname.setDisable(false);
                    emailText.setDisable(false);
                    nicknameText.setDisable(false);
                    this.Username = username;
                }
        }
    }

    public void checknickname(ActionEvent event){
        String email = emailText.getText();
        String nickname = nicknameText.getText();
        if (!email.matches("^[A-Za-z0-9.%+-]+@[A-Za-z0-9.-]+\\.(com)$")) {
            labelEmail.setText("Invalid email format!");
            checkNickname.setStyle("-fx-background-color: red");
            if(DatabaseHelper.ExistsNickname(nickname)){
                labelNickname.setText(nickname+" is already taken!");
            }else{
                labelNickname.setText("passed.");
            }
        }else{
            if(DatabaseHelper.ExistsNickname(nickname)){
                labelNickname.setText(nickname+" is already taken!");
                labelEmail.setText("passed.");
                checkNickname.setStyle("-fx-background-color: red");
            }else{
                labelEmail.setText("passed.");
                labelNickname.setText("passed.");
                checkNickname.setStyle("-fx-background-color: green");
                checkNickname.setDisable(true);
                emailText.setDisable(true);
                nicknameText.setDisable(true);
                this.Email=email;
                this.Nickname=nickname;
            }
        }

    }

    public void checkpassword(ActionEvent event){
        String pass1 = passwordText1.getText();
        String pass2 = passwordText2.getText();


    }
}
