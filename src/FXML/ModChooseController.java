package FXML;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import phase1.*;
public class ModChooseController {

    @FXML
    private ImageView imageView1;
    @FXML
    private ImageView imageView2;

    @FXML
    private TextField numberInput;
    @FXML
    private Label warningLabel;
    int betValue = -1;
    int mod = -1;
    @FXML
    private Button backButton;
    @FXML
    private Button nextButton;
    @FXML
    private void handleBackButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("P2CharchterSelect.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleImageClick(MouseEvent event) {
        ImageView clickedImageView = (ImageView) event.getSource();
        if (clickedImageView == imageView1) {
            mod = 1;
        } else if (clickedImageView == imageView2) {
            mod = 2;
        }
    }
    @FXML
    private void handleNextButton() {
        if((mod ==2 && betValue!=-1)|| mod==1)
        {try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Game.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) nextButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
        }
    }
    @FXML
    private void initialize() {
        numberInput.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                int value = Integer.parseInt(newValue);
                if (value < 50) {
                    warningLabel.setText("Not Enough money");
                    warningLabel.setVisible(true);
                } else {
                    warningLabel.setVisible(false);
                }
            } catch (NumberFormatException e) {
                warningLabel.setText("Please enter a valid number");
                warningLabel.setVisible(true);
            }
        });
    }

}
