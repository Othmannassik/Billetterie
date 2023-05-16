package ma.emsi.billetterie;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ma.emsi.billetterie.services.BilletService;
import ma.emsi.billetterie.services.MatchService;
import ma.emsi.billetterie.services.StadeService;


/**
 * Hello world!
 *
 */
public class App extends Application {
    public static void main( String[] args )
    {
        /*StadeService stadeService = new StadeService();
        MatchService matchService = new MatchService();
        BilletService billetService = new BilletService();*/
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
}
