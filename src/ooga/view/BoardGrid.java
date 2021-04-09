package ooga.view;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import ooga.InputException;
import ooga.controller.Controller;
import ooga.fileManager.PropertiesReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import ooga.player.Player;

/**
 * Class that handles the setting up of and relevant methods for the BoardGrid Object, representative of the backEnd GameBoard
 */
public class BoardGrid {

    private int SAFE_SPOTS=0;
    private GridPane gameBoard;
    private final List<Color> myPlayerColors;
    private final Controller myController;
    private List<Rectangle> cells;
    private final int sideLength;
    private final Map<Integer, List<Integer>> homeLocations;
    private final Map<Integer, List<Integer>> baseLocations;
    private final ResourceBundle viewResource = ResourceBundle.getBundle("viewConfiguration");


    /**
     * Creates the board that is a GridPane with the squares and homes and bases
     * @param controller GameController that is passed in
     * @param playerColors Colors that each player has put in, is read from the properties file
     */
    public BoardGrid(Controller controller, List<Color> playerColors){
        myController = controller;
        myPlayerColors = playerColors;
        homeLocations = myController.getHomeLocation();
        baseLocations = myController.getBaseLocation();
        sideLength = myController.getSideLength();
        String propertiesFile = myController.getPropertiesFile();
        try{
            PropertiesReader propertiesReader = new PropertiesReader(propertiesFile);
            SAFE_SPOTS= Integer.parseInt(propertiesReader.propertyFileReader("HomeLength", propertiesFile));
            gameBoard = makeBoard(propertiesReader.propertyFileReader("BoardColor", propertiesFile));
        } catch (InputException e) {
            AlertView fileError = new AlertView(AlertEnum.BackendError);
            fileError.setContentText(fileError.getContentText()+ ": " + e.getMessage());
            fileError.show();
        }
    }

    /**
     *
     * @return returns the GridPane that is the Board view
     */
    GridPane makeBoard(String boardColor){
        cells = new ArrayList<>();
        gameBoard = new GridPane();
        gameBoard.setPrefSize(Double.parseDouble(viewResource.getString("BoardSize")), Double.parseDouble(viewResource.getString("BoardSize")));
        for (int i = 0; i < sideLength; i++) {
            for (int j = 0; j < sideLength; j++) {
                if(i == 0 || j == 0 || i == sideLength - 1 || j == sideLength - 1){
                    makeCell(i, j, Color.web(boardColor));
                }
            }
        }
        makeHomes();
        makeBases();
        createSlides();
        return gameBoard;
    }

    private void makeCell(int i, int j, Color color) {
        double rectangleSize = Double.parseDouble(viewResource.getString("BoardSize"))/(sideLength-1);
        Rectangle gridSpace = new Rectangle(rectangleSize, rectangleSize);

        gridSpace.setFill(color);
        gridSpace.setStroke(Color.BLACK);
        gridSpace.setStrokeWidth(Double.parseDouble(viewResource.getString("SquareStrokeWidth")));
        Text text = new Text();
        //text.setFont(Font.font(40));

        GridPane.setRowIndex(gridSpace, i);
        GridPane.setColumnIndex(gridSpace, j);
        cells.add(gridSpace);
        gameBoard.getChildren().addAll(gridSpace, text);

    }

    private void makeHomes(){
        int direction;
        int changeColor = 0;

        for(List<Integer> home: homeLocations.values()){
            int i = home.get(0);
            int j = home.get(1);
            direction = getDirection(home);

            if (i == sideLength -1 || i == 0) {
                    makeVerticalSafeSpots(i,j,direction,changeColor);
                } else {
                    makeHorizontalSafeSpots(i,j,direction,changeColor);
                }
                changeColor ++;
            }

        }

    /**
     * Used to build the homes in the correct direction based on orientation
     *
     * @param home List of the home coordinates
     * @return direction value that is either 1 or -1
     */
    public int getDirection(List<Integer> home) {
        int direction;
        if (home.contains(sideLength-1)) {
            direction = -1;
        } else {
            direction = 1;
        }
        return direction;
    }

    private void makeVerticalSafeSpots(int i, int j, int direction, int changeColor){
        int val = i + direction;
        int control = val + (direction * SAFE_SPOTS);

        while (val + direction != control) {
            makeCell(val, j, myPlayerColors.get(changeColor));
            val = val + direction;
            if(val + direction == control){
                StackPane stack = addHomeText(changeColor);

                GridPane.setRowIndex(stack, val);
                GridPane.setColumnIndex(stack, j);

                gameBoard.getChildren().addAll(stack);
            }
        }
    }

    private void makeHorizontalSafeSpots(int i, int j, int direction, int changeColor){
        int val = j + direction;
        int control = val + (direction * SAFE_SPOTS);

        while (val + direction != control) {
            makeCell(i, val, myPlayerColors.get(changeColor));
            val = val + direction;
            if(val + direction == control){
                StackPane stack = addHomeText(changeColor);

                GridPane.setRowIndex(stack, i);
                GridPane.setColumnIndex(stack, val);

                gameBoard.getChildren().addAll(stack);
            }
        }
    }


    private StackPane addHomeText(int changeColor) {
        Circle myShape = new Circle((Double.parseDouble(viewResource.getString("BoardSize"))/(sideLength-1))/2, myPlayerColors.get(changeColor));
        myShape.setStroke(Color.BLACK);
        Text text = new Text("HOME");
        text.setFill(Color.WHITE);

        StackPane stack = new StackPane();
        stack.getChildren().addAll(myShape, text);
        return stack;
    }

    private void makeBases(){
        int changeColor = 0;

        for(List<Integer> base: baseLocations.values()){
            int i = base.get(0);
            int j = base.get(1);
            makeCell(i,j,myPlayerColors.get(changeColor));
            changeColor++;

        }

    }

    /**
     *
     * @return A map of the base locations for each player (key)
     */
    public Map<Integer, List<Integer>> getBaseLocations(){return baseLocations;}

    /**
     *
     * @return A list of rectangles representing the cells
     */
    public List<Rectangle> getCells() {
        return cells;
    }

    /**
     *
     * @return returns the BoardGrid
     */
    public GridPane getBoardGrid(){return gameBoard;}

    public void createSlides() {
        List<Integer> slideLocations = myController.getSlideLocation();
        int slideLength = myController.getSlideLength();
        int colorIndex = 0;
        for (int singleDimension: slideLocations) {
            if (colorIndex >= myPlayerColors.size()) { break; }
            createSingleSlide(singleDimension, slideLength, myPlayerColors.get(colorIndex));
            colorIndex++;
        }
    }

    private void createSingleSlide(int singleDimension, int slideLength, Color slideFill) {
        for (int i = singleDimension; i < singleDimension + slideLength; i++) {
            List<Integer> myLocation = getSquare(i);
            Rectangle mySlide;
            if (myLocation.get(0) == sideLength-1 || myLocation.get(0)==0){
                mySlide = new Rectangle(2,
                        Double.parseDouble(viewResource.getString("BoardSize")) / (sideLength-1));
            } else {
                mySlide = new Rectangle(Double.parseDouble(viewResource.getString("BoardSize")) / (sideLength-1), 2);
            }
            mySlide.setFill(slideFill);
            GridPane.setRowIndex(mySlide, myLocation.get(1));
            GridPane.setColumnIndex(mySlide, myLocation.get(0));
            GridPane.setHalignment(mySlide, HPos.CENTER); // To align horizontally in the cell
            GridPane.setValignment(mySlide, VPos.CENTER);
            gameBoard.getChildren().addAll(mySlide);
        }
    }
    private List<Integer> getSquare(int singleDimension) {
        int row;
        int col;
        List<Integer> myXYLocation = new ArrayList<>();
        int corner2= sideLength-1;
        int corner3=(sideLength-1)*2;
        int corner4=(sideLength-1)*3;
        if (singleDimension <= corner2) {
            row = 0;
            col = singleDimension;
        } else if (singleDimension <= corner3) {
            row = singleDimension - (sideLength - 1);
            col = sideLength - 1;
        } else if (singleDimension <= corner4) {
            row = sideLength - 1;
            col = (sideLength - 1) - (singleDimension - (2 * (sideLength - 1)));
        } else {
            row = (sideLength - 1) - (singleDimension - (3 * (sideLength - 1)));
            col = 0;
        }
        myXYLocation.add(col);
        myXYLocation.add(row);
        return myXYLocation;
    }

    /**
     *
     * @param pieceView piece that is being either sent home of to base
     * @return x, y location for the two dimensional locations (helper method)
     */
    public List<Integer> goHomeOrBase(PieceView pieceView){
        List<Integer> pieceLoc;
        int y;
        int x;
        if(pieceView.getPieceFromPieceView().leftBase()){
            pieceLoc = homeLocations.get(pieceView.getPlayerInt());
            y = pieceLoc.get(0);
            x = pieceLoc.get(1);
            int direction = -getDirection(pieceLoc);
            if(y == 0 || y== sideLength -1 ){
                return verticalSafetyMovement(pieceView, direction,y,x);
            }
            else if(x == 0 || x == sideLength -1){
                return horizontalSafetyMovement(pieceView, direction, y, x);
            }
            return null;
        }
        else{
            pieceLoc = baseLocations.get(pieceView.getPlayerInt());
            return pieceLoc;

        }
    }

    private List<Integer> horizontalSafetyMovement(PieceView pieceView, int direction, int y, int x){
        List<Integer> ret = new ArrayList<>();
        int newX = x + (direction * pieceView.getPieceFromPieceView().getLocation());
        ret.add(y);
        ret.add(newX);
        return ret;
    }

    private List<Integer> verticalSafetyMovement(PieceView pieceView, int direction, int y, int x){
        List<Integer> ret = new ArrayList<>();
        int newY = y + (direction * (pieceView.getPieceFromPieceView().getLocation()));
        ret.add(newY);
        ret.add(x);
        return ret;
    }

    /**
     *
     * @param singleDimension given a single dimension value on the grid
     * @param player the player that pieces are being moved
     * @return the two dimensional location of what square the piece should move to in the home
     */
    public List<Integer> getSquareInHome(int singleDimension, Player player) {
        List<Integer> myHome = homeLocations.get(player.getPlayerNumber());
        int row = myHome.get(0);
        int col = myHome.get(1);
        int direction = -getDirection(myHome);
        List<Integer> squareLocation = new ArrayList<>();
        if(row == 0 || row== sideLength -1 ){
            int newRow = row + (direction * singleDimension);
            squareLocation.add(newRow);
            squareLocation.add(col);
        }
        else if(col == 0 || col == sideLength -1){
            int newCol = col + (direction * singleDimension);
            squareLocation.add(row);
            squareLocation.add(newCol);
        }
        return squareLocation;
    }


}