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
}
