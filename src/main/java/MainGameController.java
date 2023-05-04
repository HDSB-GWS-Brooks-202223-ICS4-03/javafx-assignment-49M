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
import javafx.scene.layout.AnchorPane;

public class MainGameController {

    private int slot = 0;
    private boolean nonChar = false; 
    private boolean firstSlot = true;
    private boolean firstBackSpace = true;
    private boolean numberInField = false;
    private String wordleWord;
    private boolean fifthSlot = false;
    private boolean fifthCharBackSpace = false;

    // reference used from: https://stackoverflow.com/questions/12028205/randomly-choose-a-word-from-a-text-file#:~:text=To%20get%20the%20words%20use,yourRandom%20%3D%20new%20Random(words.
    // This code reads the list of words in the "wordleWordListClean" txt file and then randomly choses a word from the list to save to the randomWord variable
    public String readFromFile() throws IOException {
            try (BufferedReader reader = new BufferedReader(new FileReader("wordleWordListClean.txt"))) {
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
       return randomWord;
            }
    }

    @FXML
    //  intitialize method calls the readFromFile method to get the wordle word for the round (initialize method runs at the start)
    public  void initialize(){
        try {
            wordleWord = readFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    AnchorPane screenPane;

    @FXML
    //  text field fx:id's
    TextField zeroZero, zeroOne, zeroTwo, zeroThree, zeroFour, oneZero, oneOne, oneTwo, oneThree, oneFour, twoZero, twoOne, twoTwo,
    twoThree, twoFour, threeZero, threeOne, threeTwo, threeThree, threeFour, fourZero, fourOne, fourTwo, fourThree, fourFour,
    fiveZero, fiveOne, fiveTwo, fiveThree, fiveFour;
    
    @FXML
    //  runs when the event is keyPressed
    public void keyPressed(KeyEvent event){

        TextField[] letterSlots = {zeroZero, zeroOne, zeroTwo, zeroThree, zeroFour, oneZero, oneOne, oneTwo, oneThree, oneFour, twoZero, twoOne, twoTwo,
            twoThree, twoFour, threeZero, threeOne, threeTwo, threeThree, threeFour, fourZero, fourOne, fourTwo, fourThree, fourFour,
            fiveZero, fiveOne, fiveTwo, fiveThree, fiveFour};
            
        AnchorPane screen = screenPane;    

        if ((slot == 4 || slot == 9 || slot == 14 || slot == 19 || slot == 24 || slot == 25) && !fifthCharBackSpace) {
            fifthSlot = true;
            screen.requestFocus();
        } else {
            fifthSlot = false;
        }

        // Get the key that was pressed
        // If the key pressed is a letter, it is typed in the current text field and then focuses on the next field
        if(event.getCode().isLetterKey()) {
            firstBackSpace = true;
            //  checking if it is the fifth slot prevents program from advancing in a column until enter key is pressed
            if (!fifthSlot) {
                fifthCharBackSpace = false;
                //  runs if there is no non letter (ex; number) present in the text field
                if (!numberInField) {
                    //  if slot is not the first text field, the if statement is executed
                    if (!firstSlot){
                        slot++;
                        if (slot == 30) {
                            slot = 0;
                        }
                    } else {firstSlot = false;} //  setting firsSlot to false allows the preceding if statement to run and move past slot 1
        
                    // if it is an alphabetic character this runs
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
                    // changes all typed letters to uppercase
                    letterSlots[slot].setTextFormatter(new TextFormatter<>((change) -> {
                        change.setText(change.getText().toUpperCase());
                        return change;
                    }));
                }
        }
        } else if(event.getCode() == KeyCode.BACK_SPACE){ //  Backspaces the letters and moves the focus on textfields backwards
            // Checking if firstBackSpace is false makes sure that the backspace deletes the current text before the preceding one
            System.out.println("Backspace"); 
            if (!firstBackSpace && slot > 0) {
                slot --;
            } else if (slot > 1){
                firstBackSpace = false;
            } 
            if (slot == 0) {
                firstSlot = true;
            }
            // clears the text field and focuses on the slot behind the current one (if !firstBackSpace)
            letterSlots[slot].clear();
            letterSlots[slot].requestFocus();
            firstSlot = true;
            if (fifthSlot) {
                fifthCharBackSpace = true;
            }
        } else { // this is executed when the pressed key is neither a letter or backspace
            nonChar = true;
            firstBackSpace = false;
            numberInField = true;
        }
        System.out.println(slot);
    }

    @FXML
    // when the key pressed is released
    public void keyReleased(KeyEvent event){ 
        TextField[] letterSlots = {zeroZero, zeroOne, zeroTwo, zeroThree, zeroFour, oneZero, oneOne, oneTwo, oneThree, oneFour, twoZero, twoOne, twoTwo,
            twoThree, twoFour, threeZero, threeOne, threeTwo, threeThree, threeFour, fourZero, fourOne, fourTwo, fourThree, fourFour,
            fiveZero, fiveOne, fiveTwo, fiveThree, fiveFour};
        // AnchorPane screen = screenPane;
            // when a non char is typed this clears it because it is invalid
            if (nonChar){
            letterSlots[slot].clear();
            letterSlots[slot].requestFocus();
            numberInField = false;
            firstSlot = false;
        }
        // if (slot == 4 || slot == 9 || slot == 14 || slot == 19 || slot == 24 || slot == 25 && !fifthCharBackSpace) {
        //     fifthSlot = true;
        //     screen.requestFocus();
        // } else {
        //     fifthSlot = false;
        //     letterSlots[slot].requestFocus();
        // }
    }
}

