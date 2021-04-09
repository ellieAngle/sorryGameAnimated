package ooga.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * ButtonHandler.java:
 * This class serves to create a variety of ComboBoxes, Buttons, and Rectangles
 * that appear in StartScreen and GameScreen
 */

public class ButtonHandler {
    private ComboBox numPlayerChoice = new ComboBox();
    private ComboBox slideLengthChoice = new ComboBox();
    private ComboBox sideLengthChoice = new ComboBox();
    private ComboBox numPieceChoice = new ComboBox();
    private ComboBox homeLengthChoice = new ComboBox();
    private ComboBox scoreChoice = new ComboBox();
    private ComboBox playerType = new ComboBox();
    private Button loadIcon;
    private Button customLevel2Icon;
    private Button customLevel3Icon;
    private Button saveButton;
    private Text customizationText = new Text();
    private List<ComboBox> playerColorOptions = new ArrayList<>();
    private ResourceBundle viewResource = ResourceBundle.getBundle("viewConfiguration");
    private ResourceBundle textResource = ResourceBundle.getBundle("textResources");
    private Rectangle mySorryLogo;
    private Button myStartButton;

    /**
     * Constructor: handles the setting up of:
        * Customization Text, Customization Choice Buttons, the Sorry Logo,
        * ComboBoxes:
            * Player Number Choice, Side Length Choice, Slide Length Choice, Piece Number Choice, Home Length Choice, Score Type Choice
     */
    public ButtonHandler() {
        makeCustomizationText();
        makeCustomButtons();
        makeSorryLogo();
        makePlayerNumberChoice();
        makeSideLengthChoice();
        makeSlideLengthChoice();
        makeStartButton();
        makeSaveButton();
        makePieceChoice();
        makeHomeLengthChoice();
        makeScoreChoice();
        makePlayerType();
    }
    void makeSorryLogo() {
        mySorryLogo = new Rectangle( Integer.parseInt(viewResource.getString("SorryLogoWidth")), Integer.parseInt(viewResource.getString("SorryLogoHeight")));
        String display = viewResource.getString("SorryLogo");
        ImagePattern imagePattern = new ImagePattern(new Image(display));
        mySorryLogo.setFill(imagePattern);
    }
    private void makeComboBox(ComboBox myComboBox) {
        Color color = Color.WHITE;
        int colorFormat = Integer.valueOf(viewResource.getString("ColorFormatComboBox"));
        String hex = String.format("#%02X%02X%02X",
                (int) (color.getRed() * colorFormat),
                (int) (color.getGreen() * colorFormat),
                (int) (color.getBlue() * colorFormat));
        myComboBox.setStyle("-fx-background-color: " + hex);
    }
    private void makeDropDown(ComboBox comboBox, int dropDownStart, int dropDownEnd) {
        for (int i = dropDownStart; i <= dropDownEnd; i++) {
            Text text = new Text(i+"");
            comboBox.getItems().add(text);
        }
    }

    void makePlayerType(){
        makeComboBox(playerType);
        playerType.setId("PlayerType");
        playerType.setPromptText(textResource.getString("selType"));
        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("Interactive");
        list.add("EasyAI");
        list.add("HardAI");
        playerType.setItems(list);
    }

    void makeScoreChoice(){
        makeComboBox(scoreChoice);
        scoreChoice.setId("ScoreChoice");
        scoreChoice.setPromptText(textResource.getString("scoreSystem"));
        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("Standard");
        list.add("Sorry");
        list.add("Score");
        list.add("Team");
        scoreChoice.setItems(list);
    }

    void makeHomeLengthChoice() {
        makeComboBox(homeLengthChoice);
        homeLengthChoice.setId("HomeLength");
        homeLengthChoice.setPromptText(textResource.getString("selSafety"));
        homeLengthChoice.setDisable(true);
        makeDropDown(homeLengthChoice, 2, 5);
    }

    void makePlayerNumberChoice() {
        makeComboBox(numPlayerChoice);
        numPlayerChoice.setId("playerNumber");
        numPlayerChoice.setPromptText(textResource.getString("selNumPlay"));
        makeDropDown(numPlayerChoice, 2, 8);
        numPlayerChoice.setDisable(true);
    }
    void makeSlideLengthChoice() {
        makeComboBox(slideLengthChoice);
        slideLengthChoice.setId("slideLength");
        slideLengthChoice.setPromptText(textResource.getString("selSlide"));
        slideLengthChoice.setDisable(true);
        makeDropDown(slideLengthChoice, 2, 6);
    }
    void makeSideLengthChoice() {
            makeComboBox(sideLengthChoice);
            sideLengthChoice.setId("sideLength");
            sideLengthChoice.setPromptText(textResource.getString("selSide"));
            makeDropDown(sideLengthChoice, 13, 20);
    }
    void makePieceChoice(){
        makeComboBox(numPieceChoice);
        numPieceChoice.setId("pieces");
        numPieceChoice.setPromptText(textResource.getString("selNumGam"));
        numPieceChoice.setDisable(true);
        makeDropDown(numPieceChoice, 2, 8);
    }
    void makeStartButton() {
        myStartButton = new Button();
        File file = new File("resources/" + viewResource.getString("PlayButton"));
        try {
            int startButtonSize = Integer.parseInt(viewResource.getString("StartButtonSize"));
            BufferedImage temp = ImageIO.read(file);
            BufferedImage scaleImage = new BufferedImage(startButtonSize, startButtonSize, temp.getType());
            Graphics2D g2d = scaleImage.createGraphics();
            g2d.drawImage(temp, 0, 0, startButtonSize, startButtonSize, null);
            Image image = SwingFXUtils.toFXImage(scaleImage, null);
            myStartButton.setGraphic(new ImageView(image));
        } catch (IOException e) {
            myStartButton.setText("error");
        }
        myStartButton.setDisable(true);
    }

    /**
     * Getter method for Player Number Choice ComboBox
     * @return comboBox for number of players selection
     */
    public ComboBox getNumPlayerChoice(){ return this.numPlayerChoice; }
    /**
     * Getter method for Side Length Choice ComboBox
     * @return comboBox for side length selection
     */
    public ComboBox getSideLengthChoice() { return this.sideLengthChoice; }
    /**
     * Getter method for Slide Length Choice ComboBox
     * @return comboBox for slide length selection
     */
    public ComboBox getSlideLengthChoice(){ return this.slideLengthChoice; }
    /**
     * Getter method for Sorry Logo Rectangle
     * @return Rectangle containing Sorry Logo graphic
     */
    public Rectangle getMySorryLogo(){ return this.mySorryLogo; }
    /**
     * Getter method for Start/Play Button
     * @return Start Button
     */
    public Button getMyStartButton() { return this.myStartButton; }
    /**
     * Getter method for number of game piece choice ComboBox
     * @return comboBox for number of pieces selection
     */
    public ComboBox getNumPieceChoice() {return this.numPieceChoice;}
    /**
     * Getter method for Home Length Choice ComboBox - home length corresponds to number of safety squares
     * @return comboBox for home length selection
     */
    public ComboBox getHomeLengthChoice() {return this.homeLengthChoice;}
    /**
     * Getter method for Scoring type Choice ComboBox
     * @return comboBox for Scoring type selection
     */
    public ComboBox getScoreChoice() {return this.scoreChoice;}

    /**
     * Getter method for piece location selection drop down
     * @param vbox: VBox to add this piece location drop down ComboBox to
     * @return comboBox for piece location selection
     */
    public ComboBox getPieceLocationDropDown(VBox vbox) {return this.pieceLocationDropDown(vbox);}

    /**
     * Getter method for Player Color Option ComboBoxes
     * @param numPlayer: total number of players in the game
     * @return List of ComboBoxes, each corresponding to a certain Player and containing all color selection options
     */
    public List<ComboBox> getPlayerColorOptions(int numPlayer){
        makePlayerColorList(numPlayer);
        return this.playerColorOptions;
    }
    private void makePlayerColorList(int numPlayers){
        for (int i = 1; i <= numPlayers; i++) {
            ComboBox myComboBox = new ComboBox();
            makeComboBox(myComboBox);
            colorDropDown(myComboBox);
            myComboBox.setPromptText("Player #" + i + " Color Choice");
            myComboBox.setId(i+"");
            playerColorOptions.add(myComboBox);
        }
    }


    private void colorDropDown(ComboBox colorBox) {
        Paint[] colorOptions = {Color.PINK, Color.RED,Color.ORANGE, Color.YELLOW, Color.LIGHTGREEN,
                Color.LIGHTSKYBLUE, Color.BLUE, Color.PURPLE};
        for (int i = 0; i < colorOptions.length; i++) {
            double colorOptionSize = Double.valueOf(viewResource.getString("ColorBoxSize"));
            Rectangle colorOption = new Rectangle(colorOptionSize, colorOptionSize);
            colorOption.setId(colorOptions[i].toString());
            colorOption.setFill(colorOptions[i]);
            colorBox.getItems().add(colorOption);
        }
    }

    void makeCustomizationText() {
        String message = textResource.getString("CustomizationSelection");
        customizationText = new Text(message);
    }

    void makeCustomButtons() {
        loadIcon = new Button();
        customLevel2Icon = new Button();
        customLevel3Icon = new Button();
        loadIcon.setText(textResource.getString("Custom1"));
        customLevel2Icon.setText(textResource.getString("Custom2"));
        customLevel3Icon.setText(textResource.getString("Custom3"));
    }

    /**
     * Getter Method for Customization Option Buttons
     * @return List of Customization Button Options
     */
    public List<Button> getCustomButton() {
        List<Button> myButtons = new ArrayList<>();
        myButtons.add(loadIcon);
        myButtons.add(customLevel2Icon);
        myButtons.add(customLevel3Icon);
        return myButtons;
    }

    /**
     * Getter method for customization description Text
     * @return: Text object containing associated Customization description
     */
    public Text getCustomizationText() {
        return this.customizationText;
    }
    void makeSaveButton(){
        File file = new File("resources/" + viewResource.getString("SAVE"));
        saveButton = new Button();
        try {
            int buttonSize = 20;
            int buttonLocation = 350;
            BufferedImage temp = ImageIO.read(file);
            BufferedImage scaleImage = new BufferedImage(buttonSize, buttonSize, temp.getType());
            Graphics2D g2d = scaleImage.createGraphics();
            g2d.drawImage(temp, buttonLocation, buttonLocation, buttonSize, buttonSize, null);
            WritableImage image = SwingFXUtils.toFXImage(scaleImage, null);
            saveButton.setGraphic(new ImageView(image));
            saveButton.setText("Save");
        } catch (IOException e) {
            saveButton.setText(textResource.getString("ErrorButtonText"));
        }
    }

    /**
     * Getter method for Save Button used in GameScreen
     * @return Save Button
     */
    public Button getSaveButton() {
        return this.saveButton;
    }

    /**
     * Method to create and return the Winner Screen
     * @return: Rectangle representing the Winner Screen background
     */
    public Rectangle makeWinnerScreen() {
        Rectangle myWinnerScreen = new Rectangle( Integer.parseInt(viewResource.getString("WinnerWidth")), Integer.parseInt(viewResource.getString("WinnerHeight")));
        String display = viewResource.getString("WinnerScreenBackground");
        ImagePattern imagePattern = new ImagePattern(new Image(display));
        myWinnerScreen.setFill(imagePattern);
        return myWinnerScreen;
    }


    private ComboBox pieceLocationDropDown(VBox vbox){
        ComboBox comboBox = new ComboBox();
        comboBox.setPromptText(textResource.getString("pieceLoc"));
        makeDropDown(comboBox, 0, ((Integer.parseInt(((Text) sideLengthChoice.getSelectionModel().getSelectedItem()).getText()))-1)*4-1);
        if (vbox.getChildren().size() > 4) {
            return comboBox;
        }
        vbox.getChildren().add(comboBox);
        comboBox.setId("pieceDropDown");
        return comboBox;

    }

    /**
     * Method that creates a List of Objects within the piece location selection VBox
     * @param pieceNumber: The current piece number the user is setting the location on
     * @param playerNumber: Player number whose piece's location is being set
     * @return List<Objects> in the piece location selection VBox
     */
    public List<Object> stateOfPlayerMenu(int pieceNumber, int playerNumber){
        List<Object> myCustoms = new ArrayList<>();
        Label playerPieceNumber = new Label( textResource.getString("PlayerNum")+ playerNumber + textResource.getString("PieceNum") + pieceNumber);
        VBox stateOfPlayer = new VBox();
        Label pieceInBaseLabel = new Label(textResource.getString("PieceInBase"));
        ToggleGroup group = new ToggleGroup();
        ToggleButton yesButton = new ToggleButton("Yes");
        yesButton.setId("yes");
        ToggleButton noButton = new ToggleButton("No");
        noButton.setId("no");
        yesButton.setToggleGroup(group);
        noButton.setToggleGroup(group);
        stateOfPlayer.getChildren().addAll(playerPieceNumber, pieceInBaseLabel, yesButton, noButton);
        noButton.setOnAction(e -> getPieceLocationDropDown(stateOfPlayer));
        yesButton.setSelected(true);
        myCustoms.add(stateOfPlayer);
        myCustoms.add(yesButton);
        myCustoms.add(pieceLocationDropDown(stateOfPlayer));
        myCustoms.add(group);
        return myCustoms;
    }

    /**
     * Method responsible for making each Player's player-type and player-color selection VBox
     * @param playerNumber: Current player being customized
     * @param customizationLevel: chosen customization level
     * @return List<Object> that are contained within the Player customization VBox
     */
    public List<Object> makePlayerCustomization(int playerNumber, int customizationLevel) {
        List<Object> myPlayerCustomizations = new ArrayList<>();
        VBox playerCustomization = new VBox();
        Label headText = new Label(textResource.getString("PlayerNum") + playerNumber + " " + textResource.getString("custom"));
        ComboBox playerColor = getPlayerColorOptions(playerNumber).get(playerNumber-1);
        playerCustomization.getChildren().addAll(headText, playerType, playerColor);
        myPlayerCustomizations.add(playerCustomization);
        myPlayerCustomizations.add(playerType);
        myPlayerCustomizations.add(playerColor);
        playerColor.setDisable(true);
        return myPlayerCustomizations;
    }

}


