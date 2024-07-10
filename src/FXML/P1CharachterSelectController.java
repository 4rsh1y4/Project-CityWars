package FXML;
import phase1.*;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.*;
import javafx.scene.control.*;

public class P1CharachterSelectController {
    private commandmanager CM = new commandmanager();
    User user1 = new User();
    Player player1 = new Player(user1);

    @FXML
    private ImageView imageView1;
    @FXML
    private Button backButton , nextButton;

    @FXML
    private ImageView imageView2;

    @FXML
    private ImageView imageView3;

    @FXML
    private ImageView imageView4;
    public int firstPCarachter = -1;

    @FXML
    private void handleImageClick(MouseEvent event) {
        ImageView clickedImageView = (ImageView) event.getSource();
        if (clickedImageView == imageView1) {
            firstPCarachter = 1;
            player1.getCharacter().setId(1);
        } else if (clickedImageView == imageView2) {
            player1.getCharacter().setId(2);
            firstPCarachter = 2;
        } else if (clickedImageView == imageView3) {
            player1.getCharacter().setId(3);
            firstPCarachter = 3;
        } else if (clickedImageView == imageView4) {
            player1.getCharacter().setId(4);
            firstPCarachter = 4;
        }


    }
    @FXML
    private void handleBackButton() {
        if(firstPCarachter == -1)
            return;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleNextButton() {
        openP2CharachterSelectScene();
    }
    private void openP2CharachterSelectScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("P2CharchterSelect.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) nextButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public int P1selectedCharacterId()
    {
        return firstPCarachter;
    }
}
