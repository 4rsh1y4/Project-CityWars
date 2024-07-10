package FXML;
import phase1.*;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
public class P2CharachterSelectController {

    User user1 = new User();
    Player player2 = new Player(user1);

    @FXML
    private ImageView imageView1;

    @FXML
    private ImageView imageView2;

    @FXML
    private ImageView imageView3;

    @FXML
    private ImageView imageView4;
    public int secondPCarachter = 0;
    @FXML
    private void handleImageClick(MouseEvent event) {
        ImageView clickedImageView = (ImageView) event.getSource();
        if (clickedImageView == imageView1) {
            secondPCarachter = 1;
            player2.getCharacter().setId(1);
        } else if (clickedImageView == imageView2) {
            player2.getCharacter().setId(2);
            secondPCarachter = 2;
        } else if (clickedImageView == imageView3) {
            player2.getCharacter().setId(3);
            secondPCarachter = 3;
        } else if (clickedImageView == imageView4) {
            player2.getCharacter().setId(4);
            secondPCarachter = 4;
        }
        openSelectModScene();
    }
        private void openThirdScene() {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ThirdScene.fxml"));
                Parent root = loader.load();
                ThirdSceneController controller = loader.getController();
                controller.setSelectedImagePath(selectedImagePath);

                Stage stage = (Stage) nextButton.getScene().getWindow();
                stage.setScene(new Scene(root));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
}