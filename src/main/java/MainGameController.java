import java.io.IOException;
import javafx.beans.property.ObjectProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class MainGameController {

    private int slot = 0;
    private boolean nonChar = false; 
    private boolean firstSlot = true;
    private boolean firstBackSpace = true;
    private boolean numberInField = false;

    @FXML
    TextField zeroZero, zeroOne, zeroTwo, zeroThree, zeroFour, oneZero, oneOne, oneTwo, oneThree, oneFour, twoZero, twoOne, twoTwo,
    twoThree, twoFour, threeZero, threeOne, threeTwo, threeThree, threeFour, fourZero, fourOne, fourTwo, fourThree, fourFour,
    fiveZero, fiveOne, fiveTwo, fiveThree, fiveFour;
    
    @FXML
    public void keyPressed(KeyEvent event){

        TextField[] letterSlots = {zeroZero, zeroOne, zeroTwo, zeroThree, zeroFour, oneZero, oneOne, oneTwo, oneThree, oneFour, twoZero, twoOne, twoTwo,
            twoThree, twoFour, threeZero, threeOne, threeTwo, threeThree, threeFour, fourZero, fourOne, fourTwo, fourThree, fourFour,
            fiveZero, fiveOne, fiveTwo, fiveThree, fiveFour};

        // Get the key that was pressed
        // If the key is a letter, it sets the text of the current TextField to the letter and move to the next TextField
        if(event.getCode().isLetterKey()) {
            firstBackSpace = true;
            if (!numberInField) {
                if (!firstSlot){
                    slot++;
                    if (slot == 30) {
                        slot = 0;
                    }
                    System.out.println("this runs");
                } else {firstSlot = false;}
    
                if (!nonChar){
                    letterSlots[slot].requestFocus();
                } else {
                    if (slot != 0){
                        slot--;
                    }
                    //letterSlots[slot].requestFocus();
                    nonChar = false;
                }
                System.out.println(letterSlots[slot]);
            }

        } else if(event.getCode() == KeyCode.BACK_SPACE){
            if (!firstBackSpace && slot > 0) {
                slot --;
            } else if (slot > 1){
                firstBackSpace = false;
            } 
            if (slot == 0) {
                firstSlot = true;
            }
            letterSlots[slot].clear();
            letterSlots[slot].requestFocus();
            firstSlot = true;
        } else {
            nonChar = true;
            firstBackSpace = false;
            numberInField = true;
        }

        // If all the text fields have been filled, reset the slot counter to 0
        // if (slot == letterSlots.length - 1) {
        //     slot = -1;
        // }
        System.out.println(slot);
    }

    @FXML
    public void keyReleased(KeyEvent event){
        TextField[] letterSlots = {zeroZero, zeroOne, zeroTwo, zeroThree, zeroFour, oneZero, oneOne, oneTwo, oneThree, oneFour, twoZero, twoOne, twoTwo,
            twoThree, twoFour, threeZero, threeOne, threeTwo, threeThree, threeFour, fourZero, fourOne, fourTwo, fourThree, fourFour,
            fiveZero, fiveOne, fiveTwo, fiveThree, fiveFour};
        if (nonChar){
            letterSlots[slot].clear();
            letterSlots[slot].requestFocus();
            numberInField = false;
            firstSlot = false;
        }
    }
}

