package ooga.view;
import ooga.view.ButtonHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class ButtonHandlerTest {

//    private ComboBox numPlayerChoice;
//    private ComboBox slideLengthChoice;
//    private ComboBox sideLengthChoice;
//    private ComboBox numPieceChoice;
//    private ComboBox homeLengthChoice;
//    private ComboBox scoreChoice = new ComboBox();
//    private ComboBox ruleChoice = new ComboBox();
//    private ComboBox playerType = new ComboBox();
//    private Button loadIcon;
//    private Button allBotIcon;
//    private Button botAndUserIcon;
//    private Button fullCustomizationIcon;
//    private Button saveButton;
//    private Text customizationText = new Text();
//    private List<ComboBox> playerColorOptions = new ArrayList<>();
//    private ResourceBundle viewResource = ResourceBundle.getBundle("viewConfiguration");
//    private ResourceBundle textResource = ResourceBundle.getBundle("textResources");
//    private Rectangle mySorryLogo;
//    private Button myStartButton;

    private ButtonHandler myButtonHandler = new ButtonHandler();

//    @BeforeEach
//    void setUp(){
//        myButtonHandler.makeCustomizationText();
//        myButtonHandler.makeCustomButtons();
//        myButtonHandler.makeSorryLogo();
//        myButtonHandler.makePlayerNumberChoice();
//        myButtonHandler.makeSideLengthChoice();
//        myButtonHandler.makeSlideLengthChoice();
//        myButtonHandler.makeStartButton();
//        myButtonHandler.makeSaveButton();
//        myButtonHandler.makePieceChoice();
//        myButtonHandler.makeHomeLengthChoice();
//        myButtonHandler.makeScoreChoice();
//        myButtonHandler.makePlayerType();

    //}

    @Test
    void getNumPlayerChoice() {
        ButtonHandler newButtonHandler = new ButtonHandler();
        assertEquals(newButtonHandler.getNumPlayerChoice(), myButtonHandler.getNumPlayerChoice());
    }

    @Test
    void getSideLengthChoice() {
    }

    @Test
    void getSlideLengthChoice() {
    }

    @Test
    void getMySorryLogo() {
    }

    @Test
    void getMyStartButton() {
    }

    @Test
    void getNumPieceChoice() {
    }

    @Test
    void getHomeLengthChoice() {
    }

    @Test
    void getScoreChoice() {
    }

    @Test
    void getRuleChoice() {
    }

    @Test
    void getPlayerType() {
    }

    @Test
    void getPieceLocationDropDown() {
    }

    @Test
    void getPlayerColorOptions() {
    }

    @Test
    void getCustomButton() {
    }

    @Test
    void getCustomizationText() {
    }

    @Test
    void getSaveButton() {
    }

    @Test
    void makeWinnerScreen() {
    }

    @Test
    void returnToStart() {
    }

    @Test
    void makePlayerCustomization() {
    }
}