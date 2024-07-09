package phase1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import phase1.DatabaseHelper;

public class Main extends Application {
    public static void main(String[] args){
        DatabaseHelper.loadCardsFromDatabase();
        DatabaseHelper.loadUsersFromDatabase();

        launch();
        //add random cards after sign up;
    }

    @Override
    public void start(Stage stage) throws Exception {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/FXML/welcome.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
