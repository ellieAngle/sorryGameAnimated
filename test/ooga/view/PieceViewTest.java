package ooga.view;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ooga.controller.Controller;
import ooga.controller.StandardGameController;
import ooga.gamepiece.Piece;
import ooga.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import util.DukeApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

class PieceViewTest extends DukeApplicationTest {

    private ResourceBundle textResource = ResourceBundle.getBundle("textResources");
    private ResourceBundle viewResource = ResourceBundle.getBundle("viewConfiguration");
    private int sideLength;
    private int xDirection;
    private int yDirection;
    private int newXLocation;
    private int newYLocation;
    private int oldXLocation;
    private int oldYLocation;
    private int xLocation;
    private int yLocation;
    int corner1;
    int corner2;
    int corner3;
    int corner4;
    private int pieceViewInt;
    int playerInt;
    int singleDimensionLocation;
    Piece myPiece;
    StackPane myStack;
    Circle myShape;
    Controller myController;
    private static BoardGrid boardGrid;
    private static GameScreen gameScreen;

    private PieceView myPieceView;
    private Player myPlayer;
    private List<Color> playerColors;

    @Override
    public void start(Stage stage) {
        StartScreen startScreen = new StartScreen();
        startScreen.start(stage);
        gameScreen = new GameScreen("playerViewTest.properties");
        stage.hide();
        stage.setScene(gameScreen.makeScene());
        stage.show();
    }

    @BeforeEach
    void setUp(){
        myController = gameScreen.getController();
        pieceViewInt = 1;
        sideLength = myController.getSideLength();
        playerColors = new ArrayList<>();
        playerColors.add(Color.BLUE);
        playerColors.add(Color.RED);
        playerColors.add(Color.GREEN);
        playerColors.add(Color.YELLOW);
        boardGrid = gameScreen.getMyBoardGrid();
        myPieceView.setBoardGrid(boardGrid);

        myPieceView = new PieceView(myController, myPiece, pieceViewInt);


        corner1=0;
        corner2= sideLength-1;
        corner3=(sideLength-1)*2;
        corner4=(sideLength-1)*3;

        myShape = new Circle(Integer.parseInt(viewResource.getString("GamePieceSize")));
        myStack = new StackPane();
        myShape.setStroke(Color.BLACK);
        Text myLabel = new Text(String.valueOf(pieceViewInt));
        myStack.getChildren().addAll(myShape, myLabel);
    }

    @Test
    void testCalculate2DimensionalLocation() {
        assertEquals(0, myPieceView.getNewXLocation());
        assertEquals(0, myPieceView.getNewYLocation());

        myPieceView.calculate2DimensionalLocation(15);

        assertEquals(796, (int) myPieceView.getNewXLocation());
        assertEquals(82, (int) myPieceView.getNewYLocation());
    }

    @Test
    void testCalculate2DimensionalLocationNegativeBase() {

        gameScreen.getPieceViews().get(0).calculate2DimensionalLocation(-1);

        assertEquals(741, (int) gameScreen.getPieceViews().get(0).getNewXLocation());
        assertEquals(247, (int) gameScreen.getPieceViews().get(0).getNewYLocation());
    }

    @Test
    void testCalculate2DimensionalLocationNegativeHome() {

        gameScreen.getPieceViews().get(0).getPieceFromPieceView().setLeftBase(true);
        gameScreen.getPieceViews().get(0).calculate2DimensionalLocation(-1);

        assertEquals(741, (int) gameScreen.getPieceViews().get(0).getNewXLocation());
        assertEquals(137, (int) gameScreen.getPieceViews().get(0).getNewYLocation());
    }

    @Test
    void testSetDirections() {
        myPieceView.setDirections(5);
        assertEquals(1, myPieceView.getXDirection());
        assertEquals(0, myPieceView.getYDirection());

        myPieceView.setDirections(15);
        assertEquals(0, myPieceView.getXDirection());
        assertEquals(-1, myPieceView.getYDirection());

        myPieceView.setDirections(30);
        assertEquals(-1, myPieceView.getXDirection());
        assertEquals(0, myPieceView.getYDirection());

        myPieceView.setDirections(50);
        assertEquals(0, myPieceView.getXDirection());
        assertEquals(1, myPieceView.getYDirection());

    }

    @Test
    void testDetermineNewX() {
        assertEquals(0, myPieceView.getNewXLocation());
        myPieceView.determineNewX(5, 0);
        assertEquals(5, myPieceView.getNewXLocation());


    }

//    @Test
//    void testSetX() {
//        myPieceView.setX(10);
//
//    }
//
//    @Test
//    void testSetY() {
//    }

    @Test
    void testDetermineNewY() {
        assertEquals(0, myPieceView.getNewYLocation());
        myPieceView.determineNewY(10, 0);
        assertEquals(10, myPieceView.getNewYLocation());
    }

    @Test
    void testGetPlayerInt() {
        assertEquals(playerInt, myPieceView.getPlayerInt());
    }

    @Test
    void testSetPlayerInt() {
        myPieceView.setPlayerInt(3);
        assertEquals(3, myPieceView.getPlayerInt() );
    }

    @Test
    void testGetPieceFromPieceView() {
        assertEquals(this.myPiece, myPieceView.getPieceFromPieceView());
    }

    @Test
    void testGetNewXLocation() {
        assertEquals(this.newXLocation, myPieceView.getNewXLocation());
    }

    @Test
    void testGetNewYLocation() {
        assertEquals(this.newYLocation, myPieceView.getNewYLocation());
    }

    @Test
    void testGetOldXLocation() {
        assertEquals(this.oldXLocation, myPieceView.getOldXLocation());
    }

    @Test
    void testGetOldYLocation() {
        assertEquals(this.oldYLocation, myPieceView.getOldYLocation());
    }



//    @Test
//    void setGameScreen() {
//
//    }

    @Test
    void testGetXDirection(){
        assertEquals(0, myPieceView.getXDirection());
    }

    @Test
    void testGetYDirection(){
        assertEquals(0, myPieceView.getYDirection());
    }

}