package ooga.controller.rules;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import ooga.gamepiece.Piece;
import ooga.player.Player;

/**
 * @author Hayden Lau
 *
 * This class is designed for the use with Team Game Controller and handles specific differences to the general rules
 * This class relies on the AbstractRules for the rest of the implementation of the general rules class
 *
 */
public class TeamRules extends AbstractRules {

  Map<Player, List<Player>> teams;

  public TeamRules(int marginSquareCount, int safetyZoneSize, Map<Player, List<Player>> teams) {
    super(marginSquareCount, safetyZoneSize);
    this.teams = teams;
  }


  /**
   * This method implements the handleCanMove abstract protected method in Abstract Rules
   * It first determines whether the piece is movable and then checks against ALL of the player's team's pieces to make sure it doesn't collide with their pieces
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
    List<Piece> teamPieces = new ArrayList<>();
    teams.get(player).forEach(player1 -> teamPieces.addAll(player1.getGamePieceList()));
    for(Piece piece : teamPieces){
      if(potentialLocation == piece.getLocation() && !piece.inHome() && piece.leftBase()){
        movable = false;
        break;
      }
    }
    return movable;
  }
}
