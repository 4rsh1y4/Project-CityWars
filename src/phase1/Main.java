package phase1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args){
        DatabaseHelper.loadCardsFromDatabase();
        DatabaseHelper.loadUsersFromDatabase();

        commandmanager.start();
//launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/FXML/welcome.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
