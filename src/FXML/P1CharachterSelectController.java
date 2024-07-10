package FXML;
import phase1.*;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
public class P1CharachterSelectController {
    private commandmanager CM = new commandmanager();
    User user1 = new User();
    Player player1 = new Player(user1);

    @FXML
    private ImageView imageView1;

    @FXML
    private ImageView imageView2;

    @FXML
    private ImageView imageView3;

    @FXML
    private ImageView imageView4;
    public int firstPCarachter = 0;

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
        openP2CharachterSelectScene();

    }

    private void openP2CharachterSelectScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("P2CCharachterSelect.fxml"));
            Parent root = loader.load();
            P2CharachterSelectController controller = loader.getController();
            //controller.setImage(selectedImagePath);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public int P1selectedCharacterId()
    {
        return firstPCarachter;
    }
}
