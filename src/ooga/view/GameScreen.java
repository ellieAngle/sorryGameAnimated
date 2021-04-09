package ooga.view;

import java.lang.reflect.Constructor;
import java.util.*;


import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import javafx.util.Duration;
import ooga.InputException;
import ooga.cards.CardValue;
import ooga.controller.Controller;
import ooga.fileManager.PropertiesReader;
import ooga.gamepiece.Piece;
import ooga.player.Player;
import ooga.player.PlayerState;

import java.io.File;
import java.util.List;

/**
 * GameScreen.java: Class responsible for creating a interactive GUI version of Sorry!©, and for updating the Game as it progresses
 */
public class GameScreen {
    private static final String CONTROLLER_PATH = "ooga.controller.";
    protected StackPane stack = new StackPane();
    private final Insets MARGINS = new Insets(350,350, 350, 350);

    private final ResourceBundle textResource = ResourceBundle.getBundle("textResources");
    private final ResourceBundle viewResource = ResourceBundle.getBundle("viewConfiguration");
    private final String myPropertyFile;
    private CardView cardView;
    private Group root;
    private Scene scene;
    private BoardGrid myBoardGrid;
    private Controller myController;
    private List<PieceView> myPieceViews;
    private Player myPlayer;
    private PropertiesReader propertiesReader;
    private final List<Color> myColors = new ArrayList<>();
    private ButtonHandler myButtonHandler;
    private AlertView alertView;
    private Map<Piece, Map<Integer, Map<Piece, Integer>>> movablePieces;

    /**
     * GameScreen Constructor: sets property file for Game configuration, check's that property file contains all keys and is a valid file
     * @param propertyFile
     */
    public GameScreen(String propertyFile) {
        myPropertyFile = propertyFile;
        try{
            propertiesReader = new PropertiesReader(myPropertyFile);
            int numPlayers = Integer.parseInt(propertiesReader.propertyFileReader("Players", myPropertyFile));
            for (int i = 1; i <= numPlayers; i++) {
                String color = propertiesReader.propertyFileReader("Player" + i + "Color", myPropertyFile);
                myColors.add(Color.web(color));
            }
        } catch (InputException e){
            AlertView fileError = new AlertView(AlertEnum.BackendError);
            fileError.setContentText(fileError.getContentText()+ ": " + e.getMessage());
            fileError.show();
        }
    }

    /**
     * Creates the GameScreen Scene to be placed on Stage
     * @return GameScreen's Scene, including all necessary view Objects
     */
    public Scene makeScene() {
        setUpInitialVariables();
        VBox centeredBox = new VBox();
        root = new Group();
        stack.getChildren().add(myBoardGrid.getBoardGrid());
        Button saveBtn = createSave();
        VBox myDeckView = (VBox) cardView.createDeckView();
        centeredBox.getChildren().addAll(myDeckView, saveBtn);
        stack.getChildren().add(centeredBox);
        root.getChildren().add(stack);
        setMyPieceViews(myPieceViews);
        StackPane.setMargin(centeredBox, MARGINS);
        scene = new Scene(root, Integer.parseInt(viewResource.getString("SceneWidth")),
                Integer.parseInt(viewResource.getString("SceneHeight")));
        return scene;
    }

    private Button createSave(){
        Button saveButton = myButtonHandler.getSaveButton();
        saveButton.setOnAction(e -> {
                try{
                    save();
                }
                catch(InputException exception){
                    alertView = new AlertView(AlertEnum.BackendError);
                    alertView.setContentText("Must be a properties file");
                    alertView.show();
                }
        });
        saveButton.setId("save");
        return saveButton;
    }

    private void save(){
        TextInputDialog textBox = new TextInputDialog("Save File");
        textBox.setHeaderText("File name to save");
        Optional<String> file = textBox.showAndWait();
        if(file.isEmpty() || !file.get().endsWith(".properties")){
            throw new InputException("Must be a properties file");
        }
        myController.savePlayerLocationAndBaseBoolean(file.get());
    }

    private void setUpInitialVariables() {
        myButtonHandler = new ButtonHandler();
        myPieceViews = new ArrayList<>();
        try{
        myController = determineController(myPropertyFile);
        }
        catch(InputException e){
            AlertView configurationError = new AlertView(AlertEnum.BackendError);
            configurationError.setContentText(configurationError.getContentText()+ ": " + e.getMessage());
            configurationError.show();
        }
        myController.setUpBoard();
        List<Player> myPlayers = myController.getAllPlayers();
        myPlayer = myController.determinePlayerTurn();
        myBoardGrid = new BoardGrid(myController, myColors);
        PieceView.setBoardGrid(myBoardGrid);
        for (Player player : myPlayers) {
            List<Piece> myPieces = player.getGamePieceList();
            int i = 1;
            for (Piece piece: myPieces) {
                PieceView pieceView = new PieceView(myController, piece, i);
                handlePieceViewSetUp(player.getPlayerNumber(), pieceView);
                pieceView.setColor(myColors.get(player.getPlayerNumber()-1));
                myPieceViews.add(pieceView);

                i++;
            }
        }
        PlayerView.setUp(myPieceViews, myBoardGrid, this);
        cardView = new CardView(myController, this);

    }

    /**
     * Method that handles front-end updating of GameScreen's myPlayer variable
     */
    public void updatePlayerTurn() {
        myPlayer = myController.determinePlayerTurn();
    }

    private void handlePieceViewSetUp(int playerInt, PieceView pieceView) {
        pieceView.setPlayerInt(playerInt);
        int singleDimensionalLocation = pieceView.getPieceFromPieceView().getLocation();
        pieceView.calculate2DimensionalLocation(singleDimensionalLocation);
        pieceView.setX(pieceView.getNewXLocation());
        pieceView.setY(pieceView.getNewYLocation());
    }


    private void setMyPieceViews(List<PieceView> myPieceViews){
        for (PieceView piece : myPieceViews) {
            root.getChildren().add(piece.getMyShape());
        }
    }

    private void handlePieceViewMovement(List<PieceView> movingPieceViews) {

        for (PieceView piece : movingPieceViews) {
            int singleDimensionalLocation = piece.getPieceFromPieceView().getLocation();
            piece.calculate2DimensionalLocation(singleDimensionalLocation);
            piece.setX(piece.getNewXLocation());
            piece.setY(piece.getNewYLocation());
        }
    }

    /**
     * Method that handles updating front-end to reflect movable pieces (for Interactive Player),
     * and handles updating of the back-end after the piece & movement has been selected. Then calls updateFrontEnd() to handle further updating
     */
    public void updateBackEnd() {
        myController.updateScores();
        handlePlayerDisplay();
        try {
            movablePieces = myController.getPossibleMovements(myPlayer);
        } catch (Exception e){
            alertView = new AlertView(AlertEnum.BackendError);
            alertView.setContentText(alertView.getContentText()+ ": " + e.getMessage());
            alertView.show();
        }
        Map<Piece, Integer> piecesMovement = myPlayer.selectGamePiece(movablePieces, (myController.getSlideLength()-1)*4, myController.getHomeLength());
        if (myPlayer.getPlayerState() ==  PlayerState.Interactive) {
                for(PieceView pieceView : myPieceViews){
                    if(movablePieces.containsKey(pieceView.getPieceFromPieceView())){
                        pieceView.getMyShape().setStroke(Color.GOLD);
                        pieceView.getMyShape().setStrokeType(StrokeType.OUTSIDE);
                    }
                }
            if(movablePieces.isEmpty()){
                piecesMovement = new HashMap<>();
                updateFrontEnd(piecesMovement);
            } else {
                alertView = new AlertView(AlertEnum.SelectPiece);
                alertView.show();
            }
        }else {
            updateFrontEnd(piecesMovement);
        }
    }

    /**
     * Getter method for current movable pieces
     * @return Data Structure containing all of a player's current movablePieces, their possible movements, and any corresponding movements by other pieces
     */
    public Map<Piece, Map<Integer, Map<Piece, Integer>>> getMovablePieces(){
        return movablePieces;
    }

    /**
     * Getter Method for AlertView
     * @return alertView variable
     */
    public AlertView getAlertView() {
        return alertView;
    }

    /**
     * Method that handles the front-end updating of moved Pieces after piece(s) selection(s)
     * @param piecesMovement: Map of Pieces and new locations, utilizes to update front-end pieceView locations
     */
    public void updateFrontEnd(Map<Piece, Integer> piecesMovement){
        for(PieceView pieceView : myPieceViews){
            pieceView.getMyShape().setStroke(Color.BLACK);
            pieceView.getMyShape().setStrokeType(StrokeType.CENTERED);
        }
        List<Piece> movedPieces = myController.handlePlayerTurn(myPlayer, piecesMovement);

        List<PieceView> movedPieceViews = new ArrayList<>();
        for (PieceView piece : myPieceViews) {
            if (movedPieces.contains(piece.getPieceFromPieceView())) {
                movedPieceViews.add(piece);
            }
            if(piece.getPieceFromPieceView().getLocation()==-myController.getHomeLength()){
                piece.getPieceFromPieceView().setInHome(true);
            }
        }
        myController.updateBoard();
        if (!movedPieceViews.isEmpty()) {
            handlePieceViewMovement(movedPieceViews);
            translate(movedPieceViews);
        }
        if(myController.determineWinner()!=null){
            winnerScreenCreation(myController.determineWinner().getPlayerNumber());
        }
        if (!myController.getCurrentCard().getValue().equals(CardValue.TWO)) {
            updatePlayerTurn();
        }
        cardView.setAllowNewCard(true);
    }

    /**
     * Getter method for current Player
     * @return Current Player, myPlayer
     */
    public Player getPlayer(){
        return myPlayer;
    }

    private void translate(List<PieceView> movedPieceViews) {
        for (PieceView movedPiece : movedPieceViews) {
            TranslateTransition translateTransition = new TranslateTransition();
            translateTransition.setDuration(Duration.seconds(1));
            translateTransition.setFromX(movedPiece.getOldXLocation()-movedPiece.getNewXLocation());
            translateTransition.setToX(0);
            translateTransition.setFromY(movedPiece.getOldYLocation()-movedPiece.getNewYLocation());
            translateTransition.setToY(0);
            translateTransition.setNode(movedPiece.getMyShape());
            translateTransition.play();
        }
    }

    private void handlePlayerDisplay() {
        int playerNum = myPlayer.getPlayerNumber();
        Text displayText = new Text(textResource.getString("PlayerDisplay") + playerNum);
        cardView.getDeckView().getChildren().add(displayText);
    }

    /**
     * Getter Method for all PieceView objects
     * @return List of all PieceView objects
     */
    public List<PieceView> getPieceViews(){return myPieceViews;}

    /**
     * getter Method for BoardGrid object
     * @return myBoardGrid variable
     */
    public BoardGrid getMyBoardGrid() {
        return myBoardGrid;
    }

    /**
     * getter Method for CardView object
     * @return cardView variable
     */
    public CardView getCardView() {
        return cardView;
    }

    /**
     * Getter method for Controller object
     * @return myController variable
     */
    public Controller getController(){
        return this.myController;
    }

    /**
     * Getter method for GameScreen's Scene
     * @return scene variable
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Method to determine the GameScreen's Controller using Reflectiohn
     * @param filename: property file to read Controller type from
     * @return: Controller object for GameScreen
     * @throws InputException: throws exception if file does not contain the "Scoring" key that determines Controller type
     */
    public Controller determineController(String filename) throws InputException {
        try {
            String controllerType = propertiesReader.propertyFileReader("Scoring", filename);
            Class<?> controller = Class.forName(CONTROLLER_PATH + controllerType + "GameController");
            Constructor<?> controllerConstructor = controller.getConstructor(String.class);
            myController = (Controller) controllerConstructor.newInstance(filename);
        } catch (Exception e) {
            throw new InputException(e.getMessage());
        }
        return myController;
    }

    private void winnerScreenCreation(int winnerNumber) {
        cardView.getGameDeck().setDisable(true);
        Rectangle myWinnerScreen = myButtonHandler.makeWinnerScreen();
        Button restartGame = myButtonHandler.getMyStartButton();
        Text winnerText = new Text("Player " + winnerNumber + " has won!");
        StackPane tempStack = new StackPane();
        tempStack.getChildren().addAll(myWinnerScreen, winnerText);
        VBox myVBox = new VBox();
        myVBox.getChildren().add(tempStack);
        while (root.getChildren().size() > 1) {
            root.getChildren().remove(root.getChildren().size()-1);
        }
        StackPane myStack = new StackPane();
        myStack.getChildren().add(myVBox);
        StackPane.setMargin(myVBox, MARGINS);
        root.getChildren().add(myStack);
        root.getChildren().remove(0);

    }
}
