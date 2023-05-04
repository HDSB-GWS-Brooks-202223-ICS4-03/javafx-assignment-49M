import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class MenuController {
    
    //boolean buttonClick = false;
    App state = new App();

    @FXML
    Label title;

    @FXML
    Button clickButton;

    @FXML
    private void buttonIsClicked() throws IOException {
        state.gameState = "WordleGameScreen";
        App.setRoot(state.gameState);
    } 
}
