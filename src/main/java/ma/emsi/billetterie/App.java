package ma.emsi.billetterie;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ma.emsi.billetterie.entities.User;
import ma.emsi.billetterie.services.UserService;

import java.security.NoSuchAlgorithmException;

/**
 * Hello world!
 *
 */
public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(parent);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main( String[] args )
    {
        launch();
    }
}
