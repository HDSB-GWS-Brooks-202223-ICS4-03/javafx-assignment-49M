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
    public void initialize(){
        System.out.println("The initatize method runs");
    }

    @FXML
    private void buttonIsClicked() throws IOException {

        // if (!buttonClick) {
        //     title.setText("The Button was clicked");
        //     buttonClick = true;
        // } else {
        //     title.setText("Wordle Plus");
        //     buttonClick = false;
        // }
        //  Changes the game state to the actual wordle game
        state.gameState = "WordleGameScreen";
        App.setRoot(state.gameState);
    } 
}
