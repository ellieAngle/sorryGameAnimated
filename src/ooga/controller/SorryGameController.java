package ooga.controller;

import ooga.cards.CardBehavior;
import ooga.controller.rules.StandardRules;
import ooga.gamepiece.Piece;
import ooga.player.Player;

import java.util.List;

public class SorryGameController extends AbstractController {

  public SorryGameController(String propertiesFile) {
    super(propertiesFile);
    rules = new StandardRules((sideLength - 1) * 4, homeLength);
  }

    @Override
    public Player determineWinner() {
        Player winner = null;
        int maxScore = 0;
        for(Player player: allPlayers){
          if(player.hasWon()){
            int score = allScores.get(player);
            if(score > maxScore){
              maxScore = score;
              winner = player;
            }
          }

        }
        return winner;
    }

  @Override
  public void scoreOfPlayer(Player player) {
    List<Piece> pieces = player.getGamePieceList();
    int score = 0;
    for (Piece tempPiece : pieces) {
      if (tempPiece.inHome()) { score += 2; }
      if (tempPiece.leftBase()) { score += 1; }
    }
    if (myActions != null) {
      if (!myActions.isEmpty()) {
        if (currentCard.getBehavior() == CardBehavior.sorry) {
          score += 5;
        } else {
          score += 1;
        }
      }
    }
    allScores.put(player, score);
  }
}
