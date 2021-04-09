package ooga.view;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ooga.fileManager.PropertiesReader;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import javax.swing.*;

import java.awt.*;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class StartScreenTest extends DukeApplicationTest {
    ButtonHandler buttonHandler;
    StartScreen startScreen;
    PropertiesReader myReader;
    @Override
    public void start(Stage stage) {
        startScreen = new StartScreen();
        buttonHandler = new ButtonHandler();
        stage.setScene(startScreen.makeScene());
        myReader = startScreen.getPropertiesReader();
        stage.show();
    }

    @Test
    void testCustomizationLevel2() {
        Button custom2 = lookup("#custom2").queryButton();
        sleep(1000);
        clickOn(custom2);
        assertEquals(startScreen.getCustomizationLevel(), 2);
    }
    @Test
    void testCustomizationLevel3() {
        Button custom3 = lookup("#custom3").queryButton();
        sleep(1000);
        clickOn(custom3);
        assertEquals(startScreen.getCustomizationLevel(), 3);
    }

    @Test
    void testPlayerNumberSelection() {
        getStarted();
        Map<String, String> myValues = myReader.getPropertyFileValues();
        assertEquals(myValues.get("Players"), "4");
        assertEquals(startScreen.getPlayerNumber(), 4);
    }
    @Test
    void testSideLengthSelection() {
        getStarted();
        sleep(1000);
        ComboBox sideLength = lookup("#sideLengthChoice").queryComboBox();
        select(sideLength, new Text("15"));
        sleep(2000);
        Map<String, String> myValues = myReader.getPropertyFileValues();
        assertEquals(myValues.get("SideLength"), "15");
        //assertEquals(myReader.getValue("SideLength"), "15");
    }

    @Test
    void testSlideLengthSelection() {
        getStarted();
        sleep(1000);
        ComboBox sideLength = lookup("#slideLengthChoice").queryComboBox();
        select(sideLength, new Text("2"));
        sleep(2000);
        Map<String, String> myValues = myReader.getPropertyFileValues();
        assertEquals(myValues.get("SlideLength"), "2");
        //assertEquals(myReader.getValue("SideLength"), "15");
    }

    @Test
    void testHomeLengthSelection() {
        getStarted();
        ComboBox homeLength = lookup("#homeLengthChoice").queryComboBox();
        select(homeLength, new Text("3"));
        sleep(2000);
        Map<String, String> myValues = myReader.getPropertyFileValues();
        assertEquals(myValues.get("HomeLength"), "3");
        //assertEquals(myReader.getValue("SideLength"), "15");
    }

    @Test
    void testScoreSelection() {
        getStarted();
        ComboBox score = lookup("#scoreChoice").queryComboBox();
        select(score, "Sorry");
        sleep(2000);
        Map<String, String> myValues = myReader.getPropertyFileValues();
        assertEquals(myValues.get("Scoring"), "Sorry");
    }
    @Test
    void testNumPieceSelection() {
        getStarted();
        ComboBox numPiece = lookup("#numPieceChoice").queryComboBox();
        select(numPiece, new Text("3"));
        sleep(2000);
        Map<String, String> myValues = myReader.getPropertyFileValues();
        assertEquals(myValues.get("GamePieces"), "3");

    }
    @Test
    void testFileInput() {
        getStarted();
        String filename = "ellie.properties";
        getToBeginningOfGame(filename);
        assertEquals(myReader.getMyPropertyFile(), filename);

    }
    @Test
    void testPlayerTypeSelection() {
        getStarted();
        ComboBox playerType = lookup("#playerType").queryComboBox();
        select(playerType, "Interactive");
        ComboBox playerColor = lookup("#playerColor").queryComboBox();
        sleep(1000);
        Map<String, String> myValues = myReader.getPropertyFileValues();
        assertEquals(myValues.get("Player1Type"), "Interactive");
    }
    void getToBeginningOfGame(String filename) {
        ComboBox sideLength = lookup("#sideLengthChoice").queryComboBox();
        select(sideLength, new Text("15"));
        ComboBox slideLength = lookup("#slideLengthChoice").queryComboBox();
        select(slideLength, new Text("2"));
        ComboBox score = lookup("#scoreChoice").queryComboBox();
        select(score, "Sorry");
        ComboBox homeLength = lookup("#homeLengthChoice").queryComboBox();
        select(homeLength, new Text("3"));
        ComboBox numPiece = lookup("#numPieceChoice").queryComboBox();
        select(numPiece, new Text("3"));
        sleep(2000);
        TextInputControl myTextFile = lookup("#textInput").queryTextInputControl();
        clickOn(myTextFile).write(filename).write(KeyCode.ENTER.getChar());
    }
    @Test
    void testPlayerNumOver4Bug() {
        Button custom2 = lookup("#custom2").queryButton();
        clickOn(custom2);
        ComboBox sideLength = lookup("#sideLengthChoice").queryComboBox();
        select(sideLength, new Text("15"));
        sleep(1000);
        ComboBox playerNum = lookup("#numPlayerChoice").queryComboBox();
        select(playerNum, new Text("8"));
        Map<String, String> myValues = myReader.getPropertyFileValues();
        assertEquals("54", myValues.get("Player8Base"));


    }
    @Test
    void testPieceLocationSelection() {
        Button custom3 = lookup("#custom3").queryButton();
        sleep(1000);
        clickOn(custom3);
        ComboBox sideLength = lookup("#sideLengthChoice").queryComboBox();
        select(sideLength, new Text("15"));
        sleep(1000);
        ComboBox playerNum = lookup("#numPlayerChoice").queryComboBox();
        select(playerNum, new Text("4"));
        sleep(1000);
        ComboBox numPiece = lookup("#numPieceChoice").queryComboBox();
        select(numPiece, new Text("2"));
        sleep(2000);
        //ToggleButton noButton = lookup("#no").query();
        ToggleButton yesButton = lookup("#yes").query();
        clickOn(yesButton);
        sleep(1000);
        ToggleButton yesButton2 = lookup("#yes").query();
        clickOn(yesButton2);
        Map<String, String> myValues = myReader.getPropertyFileValues();
        assertEquals(myValues.get("Player1Location"), "-1,-1");
        ToggleButton yesButton3 = lookup("#yes").query();
        clickOn(yesButton3);
        sleep(1000);
        ToggleButton yesButton4 = lookup("#yes").query();
        clickOn(yesButton4);
        Map<String, String> myValues1 = myReader.getPropertyFileValues();
        assertEquals(myValues1.get("Player2Location"), "-1,-1");
    }

    void getStarted() {
        Button custom2 = lookup("#custom2").queryButton();
        sleep(1000);
        clickOn(custom2);
        sleep(1000);
        ComboBox playerNum = lookup("#numPlayerChoice").queryComboBox();
        select(playerNum, new Text("4"));
        sleep(1000);

    }

    @Test
    void getStartGameTest() {
    }
}