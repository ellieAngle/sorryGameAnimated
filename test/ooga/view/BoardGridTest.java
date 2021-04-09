package ooga.view;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
//import ooga.controller.GameController;
//import ooga.controller.StandardRules;
import ooga.controller.StandardGameController;
import ooga.controller.rules.StandardRules;
import ooga.fileManager.PropertiesReader;
import ooga.gameboard.GameBoard;
import ooga.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class BoardGridTest {
    private final int SAFE_SPOTS = 4;
    PropertiesReader propertiesReader = new PropertiesReader("test_prop_file.properties");
    String propertiesFile;
    private GridPane gameBoard;
    private List<Color> playerColors;
    private StandardGameController myController;
    private int sideLength;
    Map<Integer, List<Integer>> homeLocations;
    Map<Integer, List<Integer>> baseLocations;
    private ResourceBundle viewResource = ResourceBundle.getBundle("viewConfiguration");
    private StandardRules rules;
    private BoardGrid myBoardGrid;
    private Player myPlayer;

    @BeforeEach
    void setUp(){
        myController = new StandardGameController("test_prop_file.properties");
        myPlayer = myController.determinePlayerTurn();

        playerColors = new ArrayList<>();
        playerColors.add(Color.BLUE);
        playerColors.add(Color.RED);
        playerColors.add(Color.GREEN);
        playerColors.add(Color.YELLOW);
        myBoardGrid = new BoardGrid(myController, playerColors);

        homeLocations = myController.getHomeLocation();
        baseLocations = myController.getBaseLocation();
        sideLength = myController.getSideLength();

        gameBoard = myBoardGrid.makeBoard(propertiesReader.propertyFileReader("BoardColor", myController.getPropertiesFile()));
        rules = new StandardRules(sideLength*4, SAFE_SPOTS);

    }
    @Test
    void testGetBaseLocations() {
        assertEquals(baseLocations, myBoardGrid.getBaseLocations());
    }


    @Test
    void testGetDirection() {
        for(List home: homeLocations.values()){
            if(home.contains(sideLength-1)){
                assertEquals(-1, myBoardGrid.getDirection(home));
            }
            else{
                assertEquals(1,myBoardGrid.getDirection(home));
            }
        }

    }


    @Test
    void testGetBoardGrid() {
        assertEquals(gameBoard, myBoardGrid.getBoardGrid());
    }

    @Test
    void testCreateSlides() {

    }

    @Test
    void testGoHomeOrBase() {
    }

    @Test
    void testGetSquareInHome() {
        List<Integer> squareLocation = new ArrayList<>();
        squareLocation.add(1);
        squareLocation.add(2);
        assertEquals(squareLocation, myBoardGrid.getSquareInHome(-1, myPlayer));
    }
}