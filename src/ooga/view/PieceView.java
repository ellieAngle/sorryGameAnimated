package ooga.view;

import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import ooga.controller.Controller;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
import ooga.gamepiece.Piece;

import java.util.List;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Class that handles the front-end representation of each gamePiece
 * contains methods to handle conversion of 1d location to necessary 2d front-end location
 */
public class PieceView {
    private final ResourceBundle viewResource = ResourceBundle.getBundle("viewConfiguration");
    private final int sideLength;
    private int xDirection;
    private int yDirection;
    private double newXLocation;
    private double newYLocation;
    private double oldXLocation;
    private double oldYLocation;
    private double xLocation;
    private double yLocation;
    private int corner1;
    private int corner2;
    private int corner3;
    private int corner4;
    private final int pieceViewInt;
    private int playerInt;
    private final Piece myPiece;
    private Circle myShape;
    private static BoardGrid boardGrid;

    /**
     * Constructor sets necessary variables, then handles the setting of each corner int
     * @param gameController: current Controller utilized during GamePlay
     * @param gamePiece: back-end GamePiece the PieceView corresponds too
     * @param viewInt: integer corresponding to which pieceView number the pieceView object is
     */
    public PieceView(Controller gameController, Piece gamePiece, int viewInt) {
        myPiece = gamePiece;
        pieceViewInt = viewInt;
        setMyShape();
        sideLength = gameController.getSideLength();
        setCorners();
    }
    private void setCorners() {
        corner1=0;
        corner2= sideLength-1;
        corner3=(sideLength-1)*2;
        corner4=(sideLength-1)*3;
    }

    /**
     * Method to calculate the pieceView's 2-dimensional (x,y) location
     * @param location: 1-dimensional location from back-end
     */
    public void calculate2DimensionalLocation(int location) {
        int y;
        int x;
        if (location < 0) {
            List<Integer> pieceLoc = boardGrid.goHomeOrBase(this);
            y = pieceLoc.get(0);
            x = pieceLoc.get(1);
        } else if (location <= corner2) {
            y = 0;
            x = location;
        } else if (location <= corner3) {
            y = location - (sideLength - 1);
            x = sideLength - 1;
        } else if (location <= corner4) {
            y = sideLength - 1;
            x = (sideLength - 1) - (location - (2 * (sideLength - 1)));
        } else {
            y = (sideLength - 1) - (location - (3 * (sideLength - 1)));
            x = 0;
        }
        List<Double> myValues = getLocationInStackPane(x, y);
        this.determineNewX(myValues.get(1), this.xLocation);
        this.determineNewY(myValues.get(0), this.yLocation);
    }

    /**
     * method used to set the x,y direction of the pieceView given its 1-dimensional location
     * @param location: 1-dimensional location from back-end
     */
    public void setDirections(double location) {
        if (location < corner1) return;
        if(location <= corner2) {
            xDirection = 1;
            yDirection = 0;
        }
        else if (location <= corner3) {
            xDirection = 0;
            yDirection = -1;
        }
        else if(location <= corner4) {
            xDirection = -1;
            yDirection = 0;
        }
        else{
            xDirection = 0;
            yDirection = 1;
        }
    }

    /**
     * Method to determine the new x-location of the pieceView
     * @param newLocation: 1-dimensional new location
     * @param oldLocation: 1-dimensional prior location
     */
    public void determineNewX(double newLocation, double oldLocation) {
        this.newXLocation = newLocation;
        this.oldXLocation = oldLocation;
        this.setDirections(oldLocation);
    }

    /**
     * Setter method for pieceView's x-location
     * @param xLocation: new xLocation for pieceView
     */
    public void setX(double xLocation) {
        this.xLocation = xLocation;
        this.myShape.setCenterX(this.xLocation);
    }
    /**
     * Setter method for pieceView's y-location
     * @param yLocation: new yLocation for pieceView
     */
    public void setY(double yLocation) {
        this.yLocation = yLocation;
        this.myShape.setCenterY(this.yLocation);
    }
    /**
     * Method to determine the new y-location of the pieceView
     * @param newLocation: 1-dimensional new location
     * @param oldLocation: 1-dimensional prior location
     */
    public void determineNewY(double newLocation, double oldLocation) {
        this.newYLocation = newLocation;
        this.oldYLocation = oldLocation;
        this.setDirections(oldLocation);
    }

    /**
     * Setter method for pieceView shape's color
     * @param color: Paint value to fill pieceView's shape with, corresponds with Player
     */
    public void setColor(Paint color) {
        this.myShape.setFill(color);
    }

    /**
     * Getter method for player int
     * @return: player int variable
     */
    public int getPlayerInt() {
        return this.playerInt;
    }

    /**
     * setter method for player int
     * @param i: int value to set player int variable to
     */
    public void setPlayerInt(int i) {
        this.playerInt = i;
    }


    private void setMyShape() {
        myShape = new Circle(Double.parseDouble(viewResource.getString("GamePieceSize")));
        StackPane myStack = new StackPane();
        myShape.setStroke(Color.BLACK);
        Text myLabel = new Text(String.valueOf(pieceViewInt));
        myStack.getChildren().addAll(myShape, myLabel);
    }

    /**
     * Getter method for pieceView object's corresponding Piece object
     * @return pieceView's Piece object
     */
    public Piece getPieceFromPieceView(){return this.myPiece;}

    /**
     * method that takes (row,col) form of location and translates it to (x,y) location within the StackPane/root
     * @param x: row value
     * @param y: col value
     * @return List<Double > representing PieceView's (x,y) location within the stackPane/root
     */
    public List<Double> getLocationInStackPane(int x, int y) {
        List<Double> myValues = new ArrayList<>();
        double squareSize = (Double.parseDouble(viewResource.getString("BoardSize")) / (sideLength-1)) +
                Double.parseDouble(viewResource.getString("SquareStrokeWidth"));
        double halfSquare = squareSize/2;
        double newY = y*(squareSize) + halfSquare;
        double newX = x*(squareSize) + halfSquare;
        myValues.add(newY);
        myValues.add(newX);
        return myValues;
    }

    /**
     * Getter method for new x location
     * @return int new X location
     */
    public double getNewXLocation() {
        return this.newXLocation;
    }
    /**
     * Getter method for new y location
     * @return int new Y location
     */
    public double getNewYLocation() {
        return this.newYLocation;
    }
    /**
     * Getter method for old/prior x location
     * @return int old/prior X location
     */
    public double getOldXLocation() {
        return oldXLocation;
    }

    /**
     * Setter method for PieceView's boardGrid variable
     * @param boardGrid boardGrid to set object's corr. variable to
     */
    public static void setBoardGrid(BoardGrid boardGrid) {
        PieceView.boardGrid = boardGrid;
    }

    /**
     * Getter method for PieceView's boardGrid variable
     * @return pieceView's boardGrid variable
     */
    public static BoardGrid getBoardGrid() {
        return boardGrid;
    }

    /**
     * getter method for pieceView's javaFX Circle object
     * @return pieceView's shape (Circle)
     */
    public Circle getMyShape() {
        return myShape;
    }

    /**
     * Getter method for old/prior y location
     * @return int old/prior Y location
     */
    public double getOldYLocation() {
        return oldYLocation;
    }

    /**
     * Getter method for pieceView's x direction
     * @return  pieceView's x direction
     */
    public int getXDirection(){return xDirection;}
    /**
     * Getter method for pieceView's y direction
     * @return  pieceView's y direction
     */
    public int getYDirection(){return yDirection;}
}
