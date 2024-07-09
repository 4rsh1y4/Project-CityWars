package FXML;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import phase1.DatabaseHelper;
import phase1.Captcha;
import phase1.User;
import phase1.commandmanager;

import java.io.IOException;
import java.util.Random;

public class signUpController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private String Username;
    private String Password;
    private String Nickname;
    private String Email;
    private int questionNumber;
    private String questionAnswer;
    private String captch ;

    @FXML    Label signup,labelEmail;
    @FXML    Label labelUsername;
    @FXML    Label labelNickname;
    @FXML    Label labelPass,captcha;

    @FXML    Button checkUsername;
    @FXML    Button checkNickname;
    @FXML    Button checkPassword;
    @FXML    Button checkQuestion;

    @FXML  TextField usernameText;
    @FXML  TextField emailText;
    @FXML  TextField nicknameText;
    @FXML  TextField questionText,captchaText;

    @FXML  PasswordField passwordText1;
    @FXML  PasswordField passwordText2;

    @FXML CheckBox question1,question2,question3;

    @FXML
    public void initialize() {
        checkUsername.setDisable(false);
        usernameText.setDisable(false);
        emailText.setDisable(true);
        nicknameText.setDisable(true);
        checkNickname.setDisable(true);
        passwordText2.setDisable(true);
        passwordText1.setDisable(true);
        checkPassword.setDisable(true);
        captcha.setDisable(true);
        signup.setDisable(true);

        question1.setDisable(true);
        question2.setDisable(true);
        question3.setDisable(true);
        checkQuestion.setDisable(true);
        questionText.setDisable(true);
        Random random = new Random();
        int n = random.nextInt(3)+5;
        String cap = Captcha.generateCaptchaNumber(n);
        this.captch = cap;
        captcha.setText(Captcha.getCaptchaDisplay(cap));

        checkUsername.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                checkusername(null);
            }
        });
    }

    public void revertcolor(KeyEvent event) {
        labelUsername.setText("");
        checkUsername.setStyle("-fx-background-color:  #ffefc2");
    }

    public void revertcolor1(KeyEvent event) {
        labelEmail.setText("");
        labelNickname.setText("");
        checkNickname.setStyle("-fx-background-color:  #ffefc2");
    }

    public void revertcolor2(MouseEvent event) {
        labelPass.setText("");
        passwordText1.setText("");
        passwordText2.setText("");
        checkPassword.setStyle("-fx-background-color:  #ffefc2");
    }

    public void checkusername(ActionEvent event) {
        String username = usernameText.getText();
        if (!username.matches("^[a-zA-Z0-9_]*$")) {
            labelUsername.setText("Username can have only letters and number and underscores!");
            checkUsername.setStyle("-fx-background-color: red");
        } else {
            if (DatabaseHelper.ExistUsername(username)) {
                labelUsername.setText(username + " is already taken!");
                checkUsername.setStyle("-fx-background-color: red");
            } else {
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

    public void checknickname(ActionEvent event) {
        String email = emailText.getText();
        String nickname = nicknameText.getText();
        if (!email.matches("^[A-Za-z0-9.%+-]+@[A-Za-z0-9.-]+\\.(com)$") &&!(email.equals("")||nickname.equals(""))) {
            labelEmail.setText("Invalid email format!");
            checkNickname.setStyle("-fx-background-color: red");
            if (DatabaseHelper.ExistsNickname(nickname)) {
                labelNickname.setText(nickname + " is already taken!");
            } else {
                labelNickname.setText("passed.");
            }
        } else {
            if (DatabaseHelper.ExistsNickname(nickname)) {
                labelNickname.setText(nickname + " is already taken!");
                labelEmail.setText("passed.");
                checkNickname.setStyle("-fx-background-color: red");
            } else {
                labelEmail.setText("passed.");
                labelNickname.setText("passed.");
                checkNickname.setStyle("-fx-background-color: green");
                checkNickname.setDisable(true);
                emailText.setDisable(true);
                nicknameText.setDisable(true);
                passwordText2.setDisable(false);
                passwordText1.setDisable(false);
                checkPassword.setDisable(false);
                this.Email = email;
                this.Nickname = nickname;
            }
        }

    }

    public void checkpassword(ActionEvent event) {
            String pass1 = passwordText1.getText();
            String pass2 = passwordText2.getText();
            if (!pass1.equals(pass2) && !(pass1.equals("")||pass2.equals(""))) {
                labelPass.setText("Your password doesn't match!");
                checkPassword.setStyle("-fx-background-color: red");
            } else {
                String reg8char = ".{8,}$";
                String regChars = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[-+_!@#$%^&*.,?]).*$";
                if (!pass1.matches(reg8char) && pass1.matches(regChars)) {
                    labelPass.setText("Your password should be atleast 8 characters");
                    checkPassword.setStyle("-fx-background-color: red");
                } else if (!pass1.matches(regChars) && pass1.matches(reg8char)) {
                    labelPass.setText("Your password should contain lower and upper and special chars");
                    checkPassword.setStyle("-fx-background-color: red");
                } else if (!pass1.matches(regChars) && !pass1.matches(reg8char)) {
                    labelPass.setText("Your password is trash");
                    checkPassword.setStyle("-fx-background-color: red");
                } else {
                    this.Password = pass1;
                    labelPass.setText("passed.");
                    checkPassword.setDisable(true);
                    passwordText1.setDisable(true);
                    passwordText2.setDisable(true);

                    question1.setDisable(false);
                    question2.setDisable(false);
                    question3.setDisable(false);
                    captcha.setDisable(false);

                }

            }
    }


    public void randompass(MouseEvent event){
        String pass = commandmanager.generateRandomPassword(10);
        passwordText2.setText(pass);
        passwordText1.setText(pass);
        this.Password = pass;
        checkPassword.setDisable(true);
        passwordText1.setDisable(true);
        passwordText2.setDisable(true);

        question1.setDisable(false);
        question2.setDisable(false);
        question3.setDisable(false);
        captcha.setDisable(false);

    }
    public void question1Selected(ActionEvent event){
        question1.setSelected(true);
        question2.setSelected(false);
        question3.setSelected(false);
        questionText.setDisable(false);
        checkQuestion.setDisable(false);
        this.questionNumber=1;
    }
    public void question2Selected(ActionEvent event){
        question2.setSelected(true);
        question1.setSelected(false);
        question3.setSelected(false);
        questionText.setDisable(false);
        checkQuestion.setDisable(false);
        this.questionNumber=2;

    }
    public void question3Selected(ActionEvent event){
        question3.setSelected(true);
        question2.setSelected(false);
        question1.setSelected(false);
        questionText.setDisable(false);
        checkQuestion.setDisable(false);
        this.questionNumber=3;

    }

    public void checkquestion(ActionEvent event) {
        String captchaAnswer = captchaText.getText();
        this.questionAnswer = questionText.getText();
        if (!captchaAnswer.equals(captch) && !questionAnswer.equals("")) {
            Random random = new Random();
            int n = random.nextInt(3)+5;
            String cap = Captcha.generateCaptchaNumber(n);
            this.captch = cap;
            captcha.setText(Captcha.getCaptchaDisplay(cap));
        } else {
            question1.setDisable(true);
            question2.setDisable(true);
            question3.setDisable(true);
            checkQuestion.setDisable(true);
            questionText.setDisable(true);
            captchaText.setDisable(true);
            checkQuestion.setStyle("-fx-background-color: green");
            signup.setDisable(false);
        }
    }

    public void signUp(MouseEvent event) throws IOException {
        DatabaseHelper.insertUser(Email,Password,Username,Nickname,questionAnswer,questionNumber,1,100,0,222,"z1","");
        User currentuser = User.getUser(Username);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/mainMenu.fxml"));
        root = loader.load();
        mainMenuController mainController = loader.getController();
        mainController.setCurrentuser(currentuser);

        stage = (Stage)(((Node)event.getSource()).getScene().getWindow());
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();    }
}
