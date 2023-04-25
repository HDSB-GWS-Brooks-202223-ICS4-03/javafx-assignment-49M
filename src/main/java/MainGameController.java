import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class MainGameController {
    
    @FXML
    TextField zeroZero, zeroOne, zeroTwo, zeroThree, zeroFour, oneZero, oneOne, oneTwo, oneThree, oneFour, twoZero, twoOne, twoTwo,
    twoThree, twoFour, threeZero, threeOne, threeTwo, threeThree, threeFour, fourZero, fourOne, fourTwo, fourThree, fourFour,
    fiveZero, fiveOne, fiveTwo, fiveThree, fiveFour;
    
    @FXML
    public void keyPressed(){

        TextField[] letterSlots = {zeroZero, zeroOne, zeroTwo, zeroThree, zeroFour, oneZero, oneOne, oneTwo, oneThree, oneFour, twoZero, twoOne, twoTwo,
            twoThree, twoFour, threeZero, threeOne, threeTwo, threeThree, threeFour, fourZero, fourOne, fourTwo, fourThree, fourFour,
            fiveZero, fiveOne, fiveTwo, fiveThree, fiveFour};

        for (int i = 0; i <= letterSlots.length - 1; i++) {
            TextField letter1 = letterSlots[i];
            TextField letter2 = letterSlots[i + 1];
            letterSlots[i].setTextFormatter(new TextFormatter<>((change) -> {
                change.setText(change.getText().toUpperCase());
                return change;
            }));

            letterSlots[i].setOnKeyPressed(event -> {
                if (letter1.getLength() >= 1){
                    letter2.requestFocus();
                }
            });            
        }
    }
}
