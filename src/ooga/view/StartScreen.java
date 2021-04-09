package ooga.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;
import java.util.*;
import java.util.List;

import javafx.scene.shape.Rectangle;
import ooga.InputException;
import ooga.fileManager.InitialDataConfiguration;
import ooga.fileManager.PropertiesReader;

/**
 * StartScreen.java is responsible for creating the 2 initial Customization Screens that enable the user to:
    * upload an existing .properties file
    * customize the board, the players, and the pieces for their game
 * This is the runnable class, extending application. It is thus also responsible for creating the GameScreen after customization or file uploading has finished
 */
public class StartScreen extends Application {
    private final FileChooser fileChooser = new FileChooser();
    private final ResourceBundle textResource = ResourceBundle.getBundle("textResources");
    private final ResourceBundle viewResource = ResourceBundle.getBundle("viewConfiguration");
    private final ResourceBundle inputOutputBundle = ResourceBundle.getBundle("InputOutputResources");
    private final BorderPane root = new BorderPane();
    private Button myStartButton;
    private TextField propertyFileNameInput;
    private boolean startGame;
    private Stage myStage;
    private GameScreen myGameScreen;
    private ButtonHandler myButtonHandler;
    private int playerNumber;
    private int customizationLevel = 1;
    private int pieceNumber;
    private int sideLength;
    private List<ComboBox> comboBoxes;
    private ComboBox numPieceChoice;
    private ComboBox sideLengthChoice;
    private ComboBox slideLengthChoice;
    private ComboBox homeLengthChoice;
    private ComboBox scoreChoice;
    private ComboBox numPlayerChoice;
    private AlertView alertView;
    private InitialDataConfiguration initialDataConfiguration = new InitialDataConfiguration();
//    private PropertiesReader myProperties = new PropertiesReader();
    //private Map<String, String> propertyFileValues;
    private PropertiesReader propertiesReader;
    private final List<Boolean> userInput = new ArrayList<>(Arrays.asList(false, false, false, false, false));

    /**
     * Start method, overriden from Application class, responsible for setting up the Stage with the existing Scene
     * @param stage
     */
    @Override
    public void start(Stage stage) {
        myStage = stage;
        myStage.setTitle(textResource.getString("start"));
        myStage.setScene(makeScene());
        myStage.show();
    }

    /**
     * Creates the StartScreen Scene using a BorderPane as the root
     * @return Scene for StartScreen
     */

    public Scene makeScene() {
        try{
        propertiesReader = new PropertiesReader("test_prop_file.properties");
        } catch(InputException e){
            AlertView fileError = new AlertView(AlertEnum.BackendError);
            fileError.setContentText(fileError.getContentText()+ ": " + e.getMessage());
            fileError.show();
        }

        myButtonHandler = new ButtonHandler();
        Text customText = myButtonHandler.getCustomizationText();
        customText.setId("customText");
        VBox customizationButtons = makeCustomizationButtons();
        root.setTop(customText);
        root.setCenter(customizationButtons);
        root.setId("pane");
        Scene scene = new Scene(root, Integer.parseInt(viewResource.getString("StartScreenSize")), Integer.parseInt(viewResource.getString("StartScreenSize")));
        scene.getStylesheets().add(this.getClass().getResource("/default.css").toExternalForm());
        return scene;
    }

    private HBox makePropertyFileName() {
        Label label = new Label(textResource.getString("nameForConfig"));
        propertyFileNameInput = new TextField();
        propertyFileNameInput.setPromptText(textResource.getString("nameRules"));
        propertyFileNameInput.setId("textInput");
        propertyFileNameInput.setDisable(true);
        propertyFileNameInput.setOnAction(e -> {
                if(!propertyFileNameInput.getText().endsWith(".properties")){
                    alertView = new AlertView(AlertEnum.BackendError);
                    alertView.setContentText("Must be a properties file");
                    alertView.show();
                }else{
                    handleGameConfiguration(propertyFileNameInput.getText());
                }
        });
        HBox hbox = new HBox();
        hbox.getChildren().addAll(label, propertyFileNameInput);
        hbox.setSpacing(10);
        return hbox;
    }

    private VBox makeChoicesAndStart() {
        VBox choiceAndStart = new VBox();
        comboBoxes = new ArrayList<>();
        numPieceChoice = myButtonHandler.getNumPieceChoice();
        sideLengthChoice = myButtonHandler.getSideLengthChoice();
        slideLengthChoice = myButtonHandler.getSlideLengthChoice();
        homeLengthChoice = myButtonHandler.getHomeLengthChoice();
        scoreChoice = myButtonHandler.getScoreChoice();
        numPlayerChoice = myButtonHandler.getNumPlayerChoice();
        setTextID();
        addComboBoxes(numPieceChoice, sideLengthChoice, slideLengthChoice, homeLengthChoice);
        setActionForBox();
        HBox fileNameChoice = makePropertyFileName();
        myStartButton = myButtonHandler.getMyStartButton();
        myStartButton.setId("startButton");
        myStartButton.setOnAction(e -> {
            this.startGame = true;
            beginGame();
        });
        choiceAndStart.getChildren().addAll(sideLengthChoice, numPlayerChoice, numPieceChoice, slideLengthChoice, homeLengthChoice,
                scoreChoice ,fileNameChoice, myStartButton);
        return choiceAndStart;
    }

    private void setActionForBox() {
        sideLengthChoice.setOnAction(e -> numPlayerChoice.setDisable(false));
        slideLengthChoice.setOnAction(e -> setPropertyFileValues(inputOutputBundle.getString("SlideLength"), slideLengthChoice.getSelectionModel().getSelectedItem()));
        homeLengthChoice.setOnAction(e -> setPropertyFileValues(inputOutputBundle.getString("HomeLength"), homeLengthChoice.getSelectionModel().getSelectedItem()));
        scoreChoice.setOnAction(e -> handleRuleChoice(scoreChoice.getSelectionModel().getSelectedItem()));//propertiesReader.changeMapValueOnUserInput("Scoring", (String) scoreChoice.getSelectionModel().getSelectedItem()));
        numPlayerChoice.setOnAction(e -> {
            numPlayerInput(numPlayerChoice.getSelectionModel().getSelectedItem(), comboBoxes);
            setFileParameter(sideLengthChoice);
        });
        numPieceChoice.setOnAction(e -> numPieceInput(numPieceChoice.getSelectionModel().getSelectedItem(), comboBoxes));
    }


    private void setTextID() {
        numPieceChoice.setId(inputOutputBundle.getString("numPieceChoice"));
        sideLengthChoice.setId(inputOutputBundle.getString("sideLengthChoice"));
        slideLengthChoice.setId(inputOutputBundle.getString("slideLengthChoice"));
        homeLengthChoice.setId(inputOutputBundle.getString("homeLengthChoice"));
        scoreChoice.setId(inputOutputBundle.getString("scoreChoice"));
        numPlayerChoice.setId(inputOutputBundle.getString("numPlayerChoice"));
    }

    private void addComboBoxes(ComboBox numPieceChoice, ComboBox sideLengthChoice, ComboBox slideLengthChoice, ComboBox homeLengthChoice) {
        comboBoxes.add(numPieceChoice);
        comboBoxes.add(sideLengthChoice);
        comboBoxes.add(slideLengthChoice);
        comboBoxes.add(homeLengthChoice);
        comboBoxes.add(sideLengthChoice);
    }

    private void setFileParameter(ComboBox sideLengthChoice) {
        setPropertyFileValues(inputOutputBundle.getString(inputOutputBundle.getString("SideLength")), sideLengthChoice.getSelectionModel().getSelectedItem());
        Text myChoice = (Text) sideLengthChoice.getSelectionModel().getSelectedItem();
        sideLength = Integer.parseInt(myChoice.getText());
        for(int idx = 1; idx <= playerNumber; idx ++){
            propertiesReader.changeMapValueOnUserInput(inputOutputBundle.getString("Player") + idx + inputOutputBundle.getString("Home"),
                    String.valueOf(initialDataConfiguration.calculateHomeAndBase(idx, sideLength, true)));
            propertiesReader.changeMapValueOnUserInput(inputOutputBundle.getString("Player") + idx + inputOutputBundle.getString("Base"),
                    String.valueOf(initialDataConfiguration.calculateHomeAndBase(idx, sideLength, false)));
        }
    }


    private void numPlayerInput(Object selectedItem, List<ComboBox> needEnabling) {
        setPropertyFileValues(inputOutputBundle.getString("Players"), selectedItem);
        Text myChoice = (Text)selectedItem;
        int players =  Integer.parseInt(myChoice.getText());
        playerNumber = players;
        handleIndividualPlayerCustomization(1);
        for (ComboBox combo : needEnabling) {
            combo.setDisable(false);
        }
    }

    private void numPieceInput(Object selectedItem, List<ComboBox> needEnabling) {
        setPropertyFileValues(inputOutputBundle.getString("GamePieces"), selectedItem);
        Text myChoice = (Text) selectedItem;
        int choice =  Integer.parseInt(myChoice.getText());
        pieceNumber = choice;
        for(int idx = 1; idx <= playerNumber; idx++){
            propertiesReader.changeMapValueOnUserInput(inputOutputBundle.getString("Player") + idx + inputOutputBundle.getString("Location"), initialDataConfiguration.getDefaultPieceLocation(pieceNumber));
            propertiesReader.changeMapValueOnUserInput(inputOutputBundle.getString("Player") + idx + inputOutputBundle.getString("Bool"), initialDataConfiguration.setDefaultBool(pieceNumber));
        }
        if (customizationLevel == 3) {
            handleIndividualPieceCustomization(1, 1, "", "");
        }
        for (ComboBox combo : needEnabling) {
            combo.setDisable(false);
        }
    }

    private void handleIndividualPlayerCustomization(int nextPlayer) {
        if (nextPlayer <= playerNumber) {
            singlePlayerCustomization(nextPlayer);
        } else {
            return;
        }
    }

    private void singlePlayerCustomization(int playerInt) {
        List<Object> myCustoms = myButtonHandler.makePlayerCustomization(playerInt, customizationLevel);
        VBox playerCustomization = (VBox)myCustoms.get(0);
        root.setRight(playerCustomization);
        ComboBox playerType = (ComboBox) myCustoms.get(1);
        playerType.setId("playerType");
        ComboBox playerColor = (ComboBox) myCustoms.get(2);
        playerColor.setId("playerColor");
        playerType.setOnAction( e -> {
            propertiesReader.changeMapValueOnUserInput(inputOutputBundle.getString("Player") + playerInt + inputOutputBundle.getString("Type"), playerType.getSelectionModel().getSelectedItem().toString());
            playerColor.setDisable(false);
        });
        playerColor.setOnAction(e -> {
            setPropertyFileValuesColor(playerInt, playerColor.getSelectionModel().getSelectedItem());
            root.getChildren().remove(playerCustomization);
            handleIndividualPlayerCustomization(playerInt+1);
        });
    }

    private void handleIndividualPieceCustomization(int nextPiece, int nextPlayer, String location, String booleanLocation) {
        if (nextPiece > pieceNumber ) {
            propertiesReader.changeMapValueOnUserInput(inputOutputBundle.getString("Player") + nextPlayer + inputOutputBundle.getString("Location"), location);
            propertiesReader.changeMapValueOnUserInput(inputOutputBundle.getString("Player") + nextPlayer + inputOutputBundle.getString("Bool"), booleanLocation);
            nextPiece = 1;
            nextPlayer +=1;
            location = "";
            booleanLocation = "";
        }
        if (nextPiece <= pieceNumber && nextPlayer <= playerNumber) {
            singlePieceCustomization(nextPiece, nextPlayer, location, booleanLocation);
        }
    }

    private void singlePieceCustomization(int myPiece, int myPlayer, String location, String booleanLocation) {
        List<Object> myCustoms = myButtonHandler.stateOfPlayerMenu(myPiece, myPlayer);
        VBox newCustomization = (VBox) myCustoms.get(0);
        root.setRight(newCustomization);
        ToggleButton yesButton = (ToggleButton) myCustoms.get(1);
        ComboBox pieceDropDown = (ComboBox) myCustoms.get(2); //myButtonHandler.getPieceLocationDropDown(newCustomization)
        //pieceDropDown.setId("pieceDropDown");
        yesButton.setOnAction(e -> {
            inputPieceBase(myPiece, myPlayer, location, booleanLocation, newCustomization);});
        pieceDropDown.setOnAction(e -> {
            Text mySelection = (Text) pieceDropDown.getSelectionModel().getSelectedItem();
            String newLocation;
            String newBool;
            if (location.equals("")) {
                newLocation =  mySelection.getText();
                newBool =  "1";
            } else {
                newLocation = location + "," + mySelection.getText();
                newBool = booleanLocation + ",1";
            }
            root.getChildren().remove(newCustomization);
            handleIndividualPieceCustomization(myPiece+1, myPlayer, newLocation, newBool);
        });
    }

    private void inputPieceBase(int myPiece, int myPlayer, String location, String booleanLocation, VBox newCustomization) {
        String newLocation;
        String newBool;
        if (location.equals("")) {
            newLocation = "-1";
            newBool =  "0";
        } else {
            newLocation = location + ",-1";
            newBool = booleanLocation + ",0";
        }
        root.getChildren().remove(newCustomization);
        handleIndividualPieceCustomization(myPiece +1, myPlayer, newLocation, newBool);
    }

    private void setPropertyFileValues(String property, Object selectedItem) {
        Text myChoice = (Text)selectedItem;
        String choice =  myChoice.getText();
        try {
            propertiesReader.changeMapValueOnUserInput(property, choice);
        } catch (InputException e){
            AlertView propertiesError = new AlertView(AlertEnum.BackendError);
            propertiesError.setContentText(propertiesError.getContentText()+ ": " + e.getMessage());
        }
        userInput.remove(userInput.size()-1);
        if (userInput.isEmpty()) {
            propertyFileNameInput.setDisable(false);
        }
    }

    private void handleGameConfiguration(String fileName) {
        propertiesReader.setMyPropertyFile(fileName);
        propertiesReader.writePropertiesFile(new File(propertiesReader.getMyPropertyFile()));
        myStartButton.setDisable(false);
    }

    private void beginGame() {
        //this.startGame = true;
        String propertyFile = propertiesReader.getMyPropertyFile();
        myGameScreen = new GameScreen(propertyFile);
        myStage.hide();
        myStage.setScene(myGameScreen.makeScene());
        myStage.show();
    }

    /**
     * Getter method for startGame boolean variable
     * @return value of startGame variable
     */
    public boolean getStartGame(){ return this.startGame; }

    private void setPropertyFileValuesColor(int playerInt, Object selectedChoice) {
        Rectangle objectSelected = (Rectangle) selectedChoice;
        String desiredColor = Color.web(String.valueOf(objectSelected.getFill())).toString();
        String property = inputOutputBundle.getString("Player") + playerInt + inputOutputBundle.getString("Color");
        propertiesReader.changeMapValueOnUserInput(property, desiredColor);
    }

    private VBox makeCustomizationButtons() {
        VBox buttons = new VBox();
        buttons.setId("buttons");
        List<Button> myButtons = myButtonHandler.getCustomButton();
        Button custom1 = myButtons.get(0);
        custom1.setId("custom1");
        custom1.setOnAction(e -> loadFile());
        Button custom2 = myButtons.get(1);
        custom2.setId("custom2");
        custom2.setOnAction(e -> resetScene(2));
        Button custom3 = myButtons.get(2);
        custom3.setId("custom3");
        custom3.setOnAction(e -> resetScene(3));
        buttons.getChildren().addAll(custom1, custom2, custom3);
        return buttons;
    }

    private void loadFile(){
        List<File> newFiles = fileChooser.showOpenMultipleDialog(myStage);
        if (newFiles != null) {
            try{
                String propertiesFileToReadFrom = propertiesReader.changeToChosenFile(newFiles);
                propertiesReader.setMyPropertyFile(propertiesFileToReadFrom);
            }
            catch(InputException exception){
                AlertView alertView = new AlertView(AlertEnum.BackendError);
                alertView.setContentText(inputOutputBundle.getString("Bad"));
                alertView.show();
            }
        }
        beginGame();
    }

    private void resetScene(int customLevel) {
        Rectangle sorryLogo = myButtonHandler.getMySorryLogo();
        VBox ChoicesAndStart = makeChoicesAndStart();
        startGame = false;
        root.getChildren().remove("customText");
        root.setTop(sorryLogo);
        root.getChildren().remove(0);
        root.setLeft(ChoicesAndStart);
        customizationLevel = customLevel;

    }

    private void handleRuleChoice(Object selectedChoice) {
        String selection = (String) selectedChoice;
        propertiesReader.changeMapValueOnUserInput(inputOutputBundle.getString("Scoring"), selection);
        if (selection.equals(inputOutputBundle.getString("Team"))) {
            handleTeamRules();
        }
    }

    private void handleTeamRules() {
        String team1 = "";
        String team2 = "";
        int divider = playerNumber/2;
        for (int i = 1; i <= divider; i ++) {
            team1 +=  i + inputOutputBundle.getString("comma");
        }
        for (int i = divider+1; i <= playerNumber; i ++) {
            team2+=  i + inputOutputBundle.getString("comma");
        }
        propertiesReader.changeMapValueOnUserInput(inputOutputBundle.getString("Team1"), team1);
        propertiesReader.changeMapValueOnUserInput(inputOutputBundle.getString("Team2"), team2);
    }

    /**
     * getter method for user's inputted customization choice
     * @return int representing user's customization choice
     */
    public int getCustomizationLevel() { return this.customizationLevel; }

    /**
     * getter method for the number of player's chosen for the game
     * @return
     */
    public int getPlayerNumber() { return  this.playerNumber; }

    /**
     * getter method for StartScreen's property reader
     * @return StartScreen's property Reader
     */
    public PropertiesReader getPropertiesReader() { return this.propertiesReader; }
}
