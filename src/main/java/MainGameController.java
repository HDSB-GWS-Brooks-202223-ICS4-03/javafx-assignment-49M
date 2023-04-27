import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class MainGameController {

    public int slot = -1;
    
    @FXML
    TextField zeroZero, zeroOne, zeroTwo, zeroThree, zeroFour, oneZero, oneOne, oneTwo, oneThree, oneFour, twoZero, twoOne, twoTwo,
    twoThree, twoFour, threeZero, threeOne, threeTwo, threeThree, threeFour, fourZero, fourOne, fourTwo, fourThree, fourFour,
    fiveZero, fiveOne, fiveTwo, fiveThree, fiveFour;
    
    @FXML
    public void keyPressed(){

        TextField[] letterSlots = {zeroZero, zeroOne, zeroTwo, zeroThree, zeroFour, oneZero, oneOne, oneTwo, oneThree, oneFour, twoZero, twoOne, twoTwo,
            twoThree, twoFour, threeZero, threeOne, threeTwo, threeThree, threeFour, fourZero, fourOne, fourTwo, fourThree, fourFour,
            fiveZero, fiveOne, fiveTwo, fiveThree, fiveFour};

        // for (int i = 0; i <= letterSlots.length - 1; i++) {
        //     TextField letter1 = letterSlots[i];
        //     TextField letter2 = letterSlots[i + 1];
        //     letterSlots[i].setTextFormatter(new TextFormatter<>((change) -> {
        //         change.setText(change.getText().toUpperCase());
        //         return change;
        //     }));

        //     letterSlots[i].setOnKeyPressed(event -> {
        //         if (letter1.getLength() >= 1){
        //             letter2.requestFocus();
        //         }
        //     });            
        // }

        // Get the key that was pressed
        String keyPressed = "a";
        slot++;
        // If the key is a letter, set the text of the current TextField to the letter and move to the next TextField
        if (keyPressed.matches("[a-zA-Z]") && slot > 0) {
            // letterSlots[slot].setText(keyPressed);
            // System.out.println(slot);
            letterSlots[slot].requestFocus();
        }

        // If all the text fields have been filled, reset the slot counter to 0
        if (slot >= letterSlots.length - 1) {
            slot = 0;
        }
        //zeroOne.requestFocus();
    }

    @FXML
    public int keyReleased(){
        return slot;
    }
}

