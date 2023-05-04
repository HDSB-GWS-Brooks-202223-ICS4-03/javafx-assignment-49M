import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.fxml.FXML;
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
    private String wordleWord;

    // reference used from: https://stackoverflow.com/questions/12028205/randomly-choose-a-word-from-a-text-file#:~:text=To%20get%20the%20words%20use,yourRandom%20%3D%20new%20Random(words.
    public String readFromFile() throws IOException {
            BufferedReader reader = new BufferedReader(new FileReader("wordleWordListClean.txt"));
            String line = reader.readLine();
            List<String> words = new ArrayList<String>();
            while(line != null) {
                String[] wordsLine = line.split(" ");
                for(String word : wordsLine) {
                    words.add(word);
                }
                line = reader.readLine();
        }
         Random rand = new Random(System.currentTimeMillis());
         String randomWord = words.get(rand.nextInt(words.size()));
         System.out.println(randomWord);
         return randomWord;
    }

    @FXML
    public  void initialize(){
        try {
            wordleWord = readFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
                    nonChar = false;
                }
                System.out.println(wordleWord);
                // reference used from: https://stackoverflow.com/questions/30884812/javafx-textfield-automatically-transform-text-to-uppercase
                letterSlots[slot].setTextFormatter(new TextFormatter<>((change) -> {
                    change.setText(change.getText().toUpperCase());
                    return change;
                }));
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

