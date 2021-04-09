package ooga.controller.rules;

import ooga.player.*;
import ooga.gamepiece.*;

/**
 * @author Hayden Lau
 *
 * This class is the standard rules implementation for any gameplay that has standard rules.
 * This class relies on the Abstract Rules class for the full implementation of card movement.
 *
 */
public class StandardRules extends AbstractRules{

  public StandardRules(int marginSquareCount, int safetyZoneSize) {
    super(marginSquareCount, safetyZoneSize);
  }


  /**
   * This method implements the handleCanMove abstract protected method in Abstract Rules
   * It first determines whether the piece is movable and then checks against ALL of the player's own pieces to make sure it doesn't collide with their pieces
   *
   * @param gamePiece the gamePiece in Question of whether it can move
   * @param cardValue the cardValue it is attempting to move
   * @param player the player who is attempting to move
   * @return boolean for whether or not the player can actually move
   */
  @Override
  protected boolean handleCanMove(Piece gamePiece, int cardValue, Player player) {
    int location = gamePiece.getLocation();
    boolean movable = true;
    int potentialLocation = determineNewLocation(player, gamePiece.getLocation(), cardValue);
    if(gamePiece.leftBase() && location<0){
      movable = !(potentialLocation<-safetyZoneSize);
    }
    if (!gamePiece.leftBase() && (cardValue == 0 || cardValue == 1 || cardValue == 2)) {
      potentialLocation = player.getBaseLocation();
    } else if (!gamePiece.leftBase() || gamePiece.inHome()) {
      movable = false;
    }
    for(Piece piece : player.getGamePieceList()){
      if(potentialLocation == piece.getLocation() && !piece.inHome() && piece.leftBase()){
        movable = false;
        break;
      }
    }
    return movable;
  }
}
