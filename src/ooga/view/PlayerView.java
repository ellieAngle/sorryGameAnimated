package ooga.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import ooga.gamepiece.Piece;
import ooga.player.Player;

import java.util.List;

/**
 * @author Hayden Lau
 *
 * This class is designed to handle player interactions with pieces and spaces. It relies on the BoardGrid,
 * Game Screen, and PieceView class to properly function.
 *
 */
public class PlayerView {

  private static List<PieceView> myPieceViews;
  private static List<Rectangle> highlightedMoves;
  private static BoardGrid myBoardGrid;
  private static GameScreen myGameScreen;
  private static final ResourceBundle viewResource = ResourceBundle.getBundle("viewConfiguration");
  private static boolean firstRunThrough = true;
  private static AlertView alertView;
  private static double squareSize;

  /**
   * serves as the constructor for the static method and gets established at the start of the game in Game Screen
   *
   * @param pieceViews pieceViews of the game
   * @param boardGrid the game's board grid
   * @param gameScreen the game's game screen
   */
  public static void setUp(List<PieceView> pieceViews, BoardGrid boardGrid, GameScreen gameScreen) {
    myPieceViews = pieceViews;
    highlightedMoves = new ArrayList<>();
    myBoardGrid = boardGrid;
    myGameScreen = gameScreen;
    squareSize = Double.parseDouble(viewResource.getString("BoardSize"))/(myGameScreen.getController().getSideLength()-1);
  }

  /**
   * this function handles the mouse click if player is selecting their first piece of the turn. it determines
   * if the click was within a valid piece that is movable. if so it reassigns the handling of the click
   * to instead be handled by checks for square
   *
   * @param x the x coordinate of the mouse click
   * @param y the y coordinate of the mouse click
   * @param myPlayer the current player
   */
  public static void handleClickOnFirstPiece(double x, double y, Player myPlayer) {
    Map<Piece, Map<Integer, Map<Piece, Integer>>> movablePieces = myGameScreen.getMovablePieces();
    boolean badSelection = true;
    for (PieceView piece : myPieceViews) {
      if (piece.getMyShape().contains(x, y) && (piece.getPlayerInt() == myPlayer.getPlayerNumber())
          && movablePieces.containsKey(piece.getPieceFromPieceView())) {
        badSelection = false;
        myPlayer.setSelectedPiece(piece.getPieceFromPieceView());
        firstRunThrough = true;
        getClickedSquare();
        myGameScreen.getScene().getRoot().setOnMouseClicked(e -> {
        });
        createHighlightedSquares(movablePieces.get(myPlayer.getSelectedPiece()).keySet());
        break;
      }
    }
    if (badSelection && !firstRunThrough) {
      alertView = new AlertView(AlertEnum.BadSelection);
      alertView.show();

    } else if (firstRunThrough) {
      firstRunThrough = false;
    }
  }


  /**
   * This method assigns the gameScreen to call the handleClickOnFirstPiece method when the scene is clicked
   */
  public static void getClickedFirstPiece() {
    myGameScreen.getScene().getRoot().setOnMouseClicked(
        e -> handleClickOnFirstPiece(e.getX(), e.getY(), myGameScreen.getPlayer()));
  }

  /**
   * This method assigns the gameScreen to call the handleClickOnSecondPiece method when the scene is clicked
   */
  public static void getClickedSecondPiece() {
    myGameScreen.getScene().getRoot().setOnMouseClicked(
        e -> handleClickOnSecondPiece(e.getX(), e.getY(), myGameScreen.getPlayer()));
  }

  /**
   * this function handles the mouse click if player is selecting their second piece of the turn. it determines
   * if the click was within a valid piece that is movable. 
   *
   * @param x the x coordinate of the mouse click
   * @param y the y coordinate of the mouse click
   * @param myPlayer the current player
   */
  public static void handleClickOnSecondPiece(double x, double y, Player myPlayer) {
    Map<Piece, Integer> movablePieces = myGameScreen.getMovablePieces()
        .get(myPlayer.getSelectedPiece()).get(myPlayer.getSelectedMovement());
    boolean badSelection = true;
    for (PieceView piece : myPieceViews) {
      if (piece.getMyShape().contains(x, y)
          && movablePieces.containsKey(piece.getPieceFromPieceView())) {
        Map<Piece, Integer> ret = new HashMap<>();
        badSelection = false;
        ret.put(myPlayer.getSelectedPiece(), myPlayer.getSelectedMovement());
        ret.put(piece.getPieceFromPieceView(), movablePieces.get(piece.getPieceFromPieceView()));
        myGameScreen.updateFrontEnd(ret);
        firstRunThrough = true;
        myPlayer.setSelectedPiece(null);
        myPlayer.setSelectedMovement(null);
        myGameScreen.getScene().getRoot().setOnMouseClicked(e -> {
        });
        break;
      }
    }
    if (badSelection && !firstRunThrough) {
      alertView = new AlertView(AlertEnum.BadSelection);
      alertView.show();
    } else if (firstRunThrough) {
      firstRunThrough = false;
    }

  }

  /**
   * this method handles the click on the Board Grid's grid pane to interpret whether the selection is a valid movement square
   * if so it determines whether a second piece needs to be selected or it conducts the movement
   * 
   * @param event the mouse click event
   * @param myPlayer the current player
   */
  public static void handleClickOnSquare(MouseEvent event, Player myPlayer) {
    Map<Integer, Map<Piece, Integer>> movableIntegers = myGameScreen.getMovablePieces()
        .get(myPlayer.getSelectedPiece());
    Integer selectedSpace = clickGrid(event);
    if (movableIntegers.containsKey(selectedSpace)) {
      firstRunThrough = true;
      myBoardGrid.getBoardGrid().setOnMouseClicked(e -> {
      });
      myPlayer.setSelectedMovement(selectedSpace);
      if (movableIntegers.get(selectedSpace) == null) {
        Map<Piece, Integer> ret = new HashMap<>();
        ret.put(myPlayer.getSelectedPiece(), selectedSpace);
        myGameScreen.updateFrontEnd(ret);
        myPlayer.setSelectedPiece(null);
        myPlayer.setSelectedMovement(null);
      } else {
        getClickedSecondPiece();
        for (PieceView pieceView : myGameScreen.getPieceViews()) {
          if (movableIntegers.get(selectedSpace).containsKey(pieceView.getPieceFromPieceView())) {
            pieceView.getMyShape().setStroke(Color.GOLD);
            pieceView.getMyShape().setStrokeType(StrokeType.OUTSIDE);
          } else {
            pieceView.getMyShape().setStroke(Color.BLACK);
            pieceView.getMyShape().setStrokeType(StrokeType.CENTERED);
          }
        }
        alertView = new AlertView(AlertEnum.SelectPiece);
        alertView.show();
      }
      myBoardGrid.getBoardGrid().getChildren().removeAll(highlightedMoves);
    } else {
      alertView = new AlertView(AlertEnum.BadSelection);
      alertView.show();
    }
  }

  /**
   * This method assigns the BoardGrid to call the handClickOnSquare method when the scene is clicked
   */
  public static void getClickedSquare() {
    myBoardGrid.getBoardGrid()
        .setOnMouseClicked(e -> handleClickOnSquare(e, myGameScreen.getPlayer()));

  }

  private static Integer clickGrid(javafx.scene.input.MouseEvent event) {
    Node clickedNode = event.getPickResult().getIntersectedNode();
    if (clickedNode != myBoardGrid.getBoardGrid()) {
      Integer colIndex = GridPane.getColumnIndex(clickedNode);
      Integer rowIndex = GridPane.getRowIndex(clickedNode);
      if (colIndex == null || rowIndex == null) {
        return null;
      }
      if (rowIndex == 0) {
        return colIndex;
      } else if (colIndex == myGameScreen.getController().getSideLength() - 1) {
        return rowIndex + (myGameScreen.getController().getSideLength() - 1);
      } else if (rowIndex == myGameScreen.getController().getSideLength() - 1) {
        return -(colIndex - (myGameScreen.getController().getSideLength() - 1)) + (2 * (
            myGameScreen.getController().getSideLength() - 1));
      } else if (colIndex == 0) {
        return (myGameScreen.getController().getSideLength() - 1) - rowIndex + (3 * ((
            myGameScreen.getController().getSideLength() - 1)));
      } else if (clickedNode.getId() != null) {
        return Integer.parseInt(clickedNode.getId())
            - myGameScreen.getPlayer().getPlayerNumber() * 10;
      }
    } else {
      alertView = new AlertView(AlertEnum.BadSelection);
      alertView.show();
    }
    return null;
  }


  private static void createHighlightedSquares(Collection<Integer> movements) {
    movements.forEach(PlayerView::createSingleHighlight);
  }

  private static void createHighlightSquare(List<Integer> squareHighlight, int singleDimension) {
    Rectangle mySquare;
    int id = myGameScreen.getPlayer().getPlayerNumber() * 10 + singleDimension;
    mySquare = new Rectangle(squareSize, squareSize);
    mySquare.setFill(Color.GOLD);
    mySquare.setId(String.valueOf(id));
    GridPane.setRowIndex(mySquare, squareHighlight.get(1));
    GridPane.setColumnIndex(mySquare, squareHighlight.get(0));
    GridPane.setHalignment(mySquare, HPos.CENTER);
    GridPane.setValignment(mySquare, VPos.CENTER);
    highlightedMoves.add(mySquare);
    myBoardGrid.getBoardGrid().getChildren().add(mySquare);
  }

  private static void createSingleHighlight(int singleDimension) {
    List<Integer> myLocation = getSquare(singleDimension);
    createHighlightSquare(myLocation, singleDimension);
  }

  private static List<Integer> getSquare(int singleDimension) {
    int row;
    int col;
    int sideLength = myGameScreen.getController().getSideLength();
    List<Integer> myXYLocation = new ArrayList<>();
    int corner2 = sideLength - 1;
    int corner3 = (sideLength - 1) * 2;
    int corner4 = (sideLength - 1) * 3;
    if (singleDimension < 0) {
      List<Integer> rowCol = myBoardGrid.getSquareInHome(singleDimension, myGameScreen.getPlayer());
      row = rowCol.get(0);
      col = rowCol.get(1);
    } else if (singleDimension <= corner2) {
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

  public static AlertView getAlertView() {
    return alertView;
  }
}
