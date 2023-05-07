import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

/**
 * The MainGameController class is the controller for the wordle game. All main game operations are controlled by this class.
 * It handles the game logic and user interface for the game scene.
 */
public class MainGameController {

    private int slot = 0;
    private boolean nonChar = false; 
    private boolean firstSlot = true;
    private boolean firstBackSpace = true;
    private boolean numberInField = false;
    private String wordleWord;
    private int wordleIndex = -1;
    private boolean fifthSlot = false;
    private boolean fifthCharBackSpace = false;
    private boolean wordCheck = false;
    private int correctLetters = 0;
    private String originalWord;
    private String yellowCheckWordle;
    private List<String> wordList;
    private boolean validWordCheck;
    private String typedWord = "";
    private boolean gameOver = false;
    private boolean clearInvalid;

    // reference used from: https://stackoverflow.com/questions/12028205/randomly-choose-a-word-from-a-text-file#:~:text=To%20get%20the%20words%20use,yourRandom%20%3D%20new%20Random(words.
    // This code reads the list of words in the "wordleWordListClean" txt file and then randomly choses a word from the list to save to the randomWord variable
    /**
     * Reads a random word from the wordleWordListClean.txt file and sets it as the wordle word for the round.
     * 
     * @return the random word that was read from the file
     * @throws IOException If an I/O error occurs while reading the file
     */
    public String readFromFile() throws IOException {
            try (BufferedReader reader = new BufferedReader(new FileReader("wordleWordListClean.txt"))) {
                String line = reader.readLine(); // reads the line
                List<String> words = new ArrayList<String>(); // creates new string arrayList object
                while(line != null) {
                    String[] wordsLine = line.split(" ");
                    for(String word : wordsLine) { // loops through all the lines
                        words.add(word); // adds the word to the arrayList
                    }
                    line = reader.readLine(); // reads the line
      }
       Random rand = new Random(System.currentTimeMillis()); 
       String randomWord = words.get(rand.nextInt(words.size())); //picks random word
       originalWord = randomWord.toUpperCase(); // makes it uppercase
       yellowCheckWordle = randomWord.toUpperCase();
       wordList = words; // list of all the words from the file
       return randomWord;
            }
    }

    /**
     * Initializes the game by setting the wordle random word for the round and resetting game variables.
     */
    @FXML
    //  intitialize method calls the readFromFile method to get the wordle word for the round (initialize method runs at the start)
    public  void initialize(){
        try {
            wordleWord = readFromFile().toUpperCase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // resets game variables
        slot = 0;
        nonChar = false; 
        firstSlot = true;
        firstBackSpace = true;
        numberInField = false;
        wordleIndex = -1;
        fifthSlot = false;
        fifthCharBackSpace = false;
        wordCheck = false;
        typedWord = "";
        gameOver = false;
        correctLetters = 0;
        clearInvalid = false;
    }

    @FXML
    AnchorPane screenPane;

    @FXML
    Label resultStatement;

    @FXML
    Button replayButton;

    @FXML
    //  text field fx:id's
    TextField zeroZero, zeroOne, zeroTwo, zeroThree, zeroFour, oneZero, oneOne, oneTwo, oneThree, oneFour, twoZero, twoOne, twoTwo,
    twoThree, twoFour, threeZero, threeOne, threeTwo, threeThree, threeFour, fourZero, fourOne, fourTwo, fourThree, fourFour,
    fiveZero, fiveOne, fiveTwo, fiveThree, fiveFour;
    
    /**
     * Runs when a key is pressed down. Handles the user input and checks for valid inputs, backspaces, and the end of a column.
     * Bug proofed with booleans controling when certain operations can or cannot run. Makes sure that only letters are being typed, 
     * one character per text field, etc.
     * 
     * @param event The KeyEvent triggered when a key is pressed down.
     */
    @FXML
    //  runs when the event is keyPressed
    public void keyPressed(KeyEvent event){

        TextField[] letterSlots = {zeroZero, zeroOne, zeroTwo, zeroThree, zeroFour, oneZero, oneOne, oneTwo, oneThree, oneFour, twoZero, twoOne, twoTwo,
            twoThree, twoFour, threeZero, threeOne, threeTwo, threeThree, threeFour, fourZero, fourOne, fourTwo, fourThree, fourFour,
            fiveZero, fiveOne, fiveTwo, fiveThree, fiveFour};
            
        AnchorPane screen = screenPane;  // game screen
        Label result = resultStatement; // text at the bottom of screen when invalid word is typed or game over

        if (!gameOver) {
        // checks if the last text box in the column is activated, if so it doesn't allow further typing into next column until enter is clicked and the typed word is assessed
        if ((slot == 4 || slot == 9 || slot == 14 || slot == 19 || slot == 24 || slot == 29) && !fifthCharBackSpace) {
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

                    // reference used from: https://stackoverflow.com/questions/30884812/javafx-textfield-automatically-transform-text-to-uppercase
                    // changes all typed letters to uppercase
                    letterSlots[slot].setTextFormatter(new TextFormatter<>((change) -> {
                        change.setText(change.getText().toUpperCase());
                        return change;
                    }));
                }
        }
        } else if(event.getCode() == KeyCode.BACK_SPACE){ //  Backspaces the letters and moves the focus on textfields moving backwards
            // Checking if firstBackSpace is false makes sure that the backspace deletes the current text before the preceding one 
            if (!firstBackSpace && slot != 0 && slot != 5 && slot != 10 && slot != 15 && slot != 20 && slot != 25) { // cannot backspace at the column starting points
                slot --;
            } else if (slot >= 1){
                firstBackSpace = false;
            } 
            if (slot == 0) {
                firstSlot = true;
            }
            // clears the text field and focuses on the slot behind the current one (if !firstBackSpace)
            letterSlots[slot].clear();
            letterSlots[slot].requestFocus();
            firstSlot = true;
            if (fifthSlot) { // checks if the last field in the column is backspaced
                fifthCharBackSpace = true;
            }
        } else if (event.getCode() == KeyCode.ENTER && fifthSlot) { // pressing enter activates the word checks in the keyReleased method 
            correctLetters = 0;
            validWordCheck = true;
            wordCheck = true;
        } else { // this is executed when the pressed key is neither a letter or backspace (clears the character immediately)
            if (!(event.getCode() == KeyCode.SHIFT)){ // Makes it okay to click shift
                nonChar = true;
                firstBackSpace = false;
                numberInField = true;
            }
        }
        if (clearInvalid) { // clears the "invalid word" text that pops up when there is a non recognized word
            result.setText("");
        }
    }
    }

    /**
     * Runs when a key is released. Handles the end of a turn, invalid input, and checks the word guess for letters in correct areas, wrong areas or not present.
     * Switches to gameOver when word is either guessed or the player runs out of tries.
     * 
     * @param event The KeyEvent triggered when a key is released.
     * 
     * @throws IOException If there is an error with the IO.
     */
    @FXML
    // when the key pressed is released
    public void keyReleased(KeyEvent event) throws IOException{ 
        TextField[] letterSlots = {zeroZero, zeroOne, zeroTwo, zeroThree, zeroFour, oneZero, oneOne, oneTwo, oneThree, oneFour, twoZero, twoOne, twoTwo,
            twoThree, twoFour, threeZero, threeOne, threeTwo, threeThree, threeFour, fourZero, fourOne, fourTwo, fourThree, fourFour,
            fiveZero, fiveOne, fiveTwo, fiveThree, fiveFour};
        Label result = resultStatement;
        Button restart = replayButton;
        if (!gameOver) {
            // when a non char is typed this clears it because it is invalid
        if (nonChar){
            letterSlots[slot].clear();
            letterSlots[slot].requestFocus();
            numberInField = false;
            firstSlot = false;
            // clears the unnaccepted character making the text field empty
        }
        if (validWordCheck) { // checks if the guessed word is in the word list
            for (int i = (slot - 4); i <= slot; i++) {
                typedWord = typedWord + letterSlots[i].getText(); // adds the typed characters to make it a singular string
            }
            if (wordList.contains(typedWord.toLowerCase()) == false) { // if the word does not exist this runs
                wordCheck = false;
                firstSlot = true;
                fifthSlot = false;
                for (int k = slot; k >= (slot - 4); k--) { // clears all the characters typed in that row
                    letterSlots[k].clear();
                }
                slot -= 4;
                result.setText("Invalid Word");
                clearInvalid = true;
            }
            typedWord = ""; // resets variable for next time there is a word check
            validWordCheck = false;
        }
        if (wordCheck) { // logic for determining whether a slot is green, yellow or grey
            for (int i = (slot - 4); i <= slot; i++) { // loops through the row
                if (wordleIndex < 5){
                    wordleIndex++;
                }
                if (String.valueOf(letterSlots[i].getText()).equals(String.valueOf(wordleWord.charAt(wordleIndex)))) { // If the text field matches the string index text field turns green 
                    letterSlots[i].setStyle("-fx-background-color: #2cd158;");
                    correctLetters++;
                    // replaces the used letter with an underscore to keep the same string length but not reuse the letter (especially important for the yellow box readings)
                    wordleWord = wordleWord.substring(0, wordleIndex) + "_" + wordleWord.substring(wordleIndex + 1); // replaces a used character with an underscore to keep same string length
                    yellowCheckWordle = yellowCheckWordle.substring(0, wordleIndex) + "_" + yellowCheckWordle.substring(wordleIndex + 1); // this one updates two to pass the information to the yellow check
                } 
            }
            wordleIndex = -1;
            wordleWord = originalWord;
            for (int i = (slot - 4); i <= slot; i++){ // yellow slot check same logic as green check however it check the yellowChekWordle to not remove any characters which may later be in a green spot
                wordleIndex++;
                if (yellowCheckWordle.contains(String.valueOf(letterSlots[i].getText()))){ // checks if yellowCheckWordle contains the letter somewhere if so it is replaced with an underscore
                    letterSlots[i].setStyle("-fx-background-color: #f1f520");
                    yellowCheckWordle = yellowCheckWordle.substring(0, yellowCheckWordle.indexOf(String.valueOf(letterSlots[i].getText()))) + "_" + yellowCheckWordle.substring(yellowCheckWordle.indexOf(String.valueOf(letterSlots[i].getText())) + 1);
                } else if (String.valueOf(letterSlots[i].getText()).equals(String.valueOf(wordleWord.charAt(wordleIndex))) == false) { // if neither green or yellow the text field becomes grey
                    letterSlots[i].setStyle("-fx-background-color: #9c9b98");
                }
            }
            if (correctLetters == 5){ // checks if all 5 textfields are green aka (game won)
                gameOver = true;
                result.setText("YOU WON");
            } else { // if enter is clicked on the last slot and the guess is not correct (game lost) resets variables
                if (slot == 29) {
                    gameOver = true;
                    result.setText("Nice Try!   Wordle: " + originalWord);
                }
                fifthSlot = false;
                wordleIndex = -1;
                wordCheck = false;
                fifthSlot = false;
                numberInField = false;
                firstSlot = true;
                slot++;
                wordleWord = originalWord;
                yellowCheckWordle = originalWord;
                correctLetters = 0;
            }
        }
        if (gameOver) { // text pops up at the botton when gameOver is true
            restart.setStyle("-fx-background-color: #e4fcdc");
            restart.setText("PLAY AGAIN");
        }
    }
    }

    /**
     * Resets the game to its initial state, allowing the player to play again.
     * This method is called when the player clicks on the replay button.
     * It clears the text in all letter slots, resets the slot position to zero,
     * and generates a new word for the player to guess. It also resets the
     * game state variables to their default values and updates the user interface to reflect
     * the main game state.
     *
     * @throws IOException If there is an error with the IO.
     */
    @FXML
    private void replayGame() throws IOException {
        TextField[] letterSlots = {zeroZero, zeroOne, zeroTwo, zeroThree, zeroFour, oneZero, oneOne, oneTwo, oneThree, oneFour, twoZero, twoOne, twoTwo,
            twoThree, twoFour, threeZero, threeOne, threeTwo, threeThree, threeFour, fourZero, fourOne, fourTwo, fourThree, fourFour,
            fiveZero, fiveOne, fiveTwo, fiveThree, fiveFour};
        Label result = resultStatement;
        Button restart = replayButton;
        gameOver = false;
        for (int i = 0; i < 30; i++) {
            letterSlots[i].clear();
            letterSlots[i].setStyle("-fx-background-color: white");
            letterSlots[i].setStyle("-fx-border-color: black");
        }
        restart.setStyle("-fx-background-color:  #e0d3a4");
        restart.setText("");
        result.setText("");
        initialize();
    }
}

