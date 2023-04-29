import java.io.IOException;

import javafx.beans.property.ObjectProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyEvent;

public class MainGameController {

    public int slot = 0;
    public boolean nonChar = false; 
    public boolean firstSlot = true;

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
            if (!firstSlot){
                slot++;
            } else {firstSlot = false;}

            if (!nonChar){
                //letterSlots[slot].clear();
                letterSlots[slot].requestFocus();
            } else {
                if (slot != 0){
                    slot--;
                }
                letterSlots[slot].clear();
                letterSlots[slot].requestFocus();
                nonChar = false;
            }
        } else {
            nonChar = true;
            letterSlots[slot].clear();
        }

        // If all the text fields have been filled, reset the slot counter to 0
        if (slot == letterSlots.length - 1) {
            slot = -1;
        }
        System.out.println(slot);
    }

    @FXML
    public void keyReleased(KeyEvent event){
        TextField[] letterSlots = {zeroZero, zeroOne, zeroTwo, zeroThree, zeroFour, oneZero, oneOne, oneTwo, oneThree, oneFour, twoZero, twoOne, twoTwo,
            twoThree, twoFour, threeZero, threeOne, threeTwo, threeThree, threeFour, fourZero, fourOne, fourTwo, fourThree, fourFour,
            fiveZero, fiveOne, fiveTwo, fiveThree, fiveFour};
        if (nonChar){
            letterSlots[slot].clear();
        }
    }
}

