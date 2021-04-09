package ooga.controller;

import java.util.List;
import ooga.controller.rules.StandardRules;
import ooga.gamepiece.Piece;
import ooga.player.Player;

public class StandardGameController extends AbstractController{

  public StandardGameController(String propertiesFile){
    super(propertiesFile);
    rules = new StandardRules((sideLength-1)*4, homeLength);
  }

  @Override
  public Player determineWinner() {
    for (Player player : allPlayers) {
      if(player.hasWon()){
        return player;
      }
    }
    return null;
  }

  @Override
  public void scoreOfPlayer(Player player) {
    List<Piece> pieces = player.getGamePieceList();
    int score = 0;
    for(Piece tempPiece : pieces){
      if(tempPiece.inHome()){
        score += 1;
      }
    }
    allScores.put(player, score);
  }

}
