
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX FXML Application Loader
 * 
 * Loads an FXML based JavaFX ui and displays it.
 */
public class App extends Application {

    private static Scene scene;
    //MenuController state = new MenuController();
    public String gameState = "WordleGame";

    @Override
    public void start(Stage stage) throws IOException {
        //Change primary to whatever FXML file you wish to load
        scene = new Scene(loadFXML(gameState), 640, 480); 
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}