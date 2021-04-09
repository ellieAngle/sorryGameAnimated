package ooga.player;

import java.util.ArrayList;
import java.util.HashMap;
import ooga.gamepiece.GamePiece;
import ooga.gamepiece.Piece;

import java.util.List;
import java.util.Map;

/**
 * @author Hayden Lau
 *
 * This class is an implementation of the Abstract Player and the player API. It determines its moves
 * by deciding which movement option most benfits the player by decreasing its total distance of
 * pieces from home.
 *
 */
public class HardAIPlayer extends AbstractPlayer {

  public HardAIPlayer(int homeLocation, int baseLocation, int[] pieceLocations, int playerNumber, PlayerState playerState, int[] baseLocations) {
    super(homeLocation, baseLocation, pieceLocations, playerNumber, playerState, baseLocations);
  }

  /**
   * Overrides the method from the interface and instead implements the algorithm for the EasyAIPlayer to select
   * a game piece based on which piece movement decreases the total distance from home the most
   *
   * @param movablePieces A complex map of map of maps that represents a piece and the possible moves and then the possible
   * options to move based on the card that was drawn
   * @param marginSquares the number of squares around the perimeter of the board; is based on a user input value and read from the properties file
   * @param homeLength changes the number of safety squares until the home; is based on a user input value and read from the properties file
   * @return a map whose key is a piece and the value is an integer representing the moves
   */
  @Override
  public Map<Piece, Integer> selectGamePiece(Map<Piece, Map<Integer, Map<Piece, Integer>>> movablePieces, int marginSquares, int homeLength) {
    Map<Piece, Integer> moves = new HashMap<>();
    int totalDistanceFromHome = (homeLength + marginSquares) * super.getGamePieceList().size();
    if (!movablePieces.isEmpty()) {
      for (Piece myPiece : movablePieces.keySet()) {
        Map<Piece, Integer> temp = new HashMap<>();
        Map<Integer, Map<Piece, Integer>> resultingMovement = movablePieces.get(myPiece);
        for (Integer myMove : resultingMovement.keySet()) {
          temp.put(myPiece, myMove);
          if (resultingMovement.get(myMove) != null) {
            Map<Piece, Integer> resultingMoves = resultingMovement.get(myMove);
            for (Piece resultPiece : resultingMoves.keySet()) {
              int resultMove = resultingMoves.get(resultPiece);
              temp.put(resultPiece, resultMove);
              totalDistanceFromHome = determineBetterBenefit(temp, marginSquares, homeLength, totalDistanceFromHome, moves);
              temp.remove(resultPiece, resultMove);
            }
          } else {
            totalDistanceFromHome = determineBetterBenefit(temp, marginSquares, homeLength, totalDistanceFromHome, moves);
          }
          temp.remove(myPiece, myMove);
        }
      }
    }
    return moves;
  }

  private int determineBetterBenefit(Map<Piece, Integer> temp, int marginSquares, int homeLength, int totalDistanceFromHome, Map<Piece, Integer> moves) {
    List<Piece> tempPieces = new ArrayList<>();
    for (Piece piece : super.getGamePieceList()) {
      Piece tempPiece = new GamePiece(piece);
      if (temp.containsKey(piece)) {
        tempPiece.setLocation(temp.get(piece));
      }
      tempPieces.add(tempPiece);
    }
    int thisMove = totalDistanceFromHomeCalculator(tempPieces, marginSquares, homeLength);
    if (thisMove < totalDistanceFromHome) {
      moves.clear();
      moves.putAll(temp);
      return thisMove;
    }
    return totalDistanceFromHome;
  }

}
