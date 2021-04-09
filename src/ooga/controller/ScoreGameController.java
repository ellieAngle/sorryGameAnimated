package ooga.controller;

import ooga.controller.rules.StandardRules;
import ooga.gamepiece.Piece;
import ooga.player.Player;

public class ScoreGameController extends AbstractController{

  public ScoreGameController(String propertiesFile) {
    super(propertiesFile);
    rules = new StandardRules((sideLength-1)*4, homeLength);
  }

  @Override
  public Player determineWinner() {
    boolean gameOver = false;
    for (Player player : allPlayers) {
      for (Piece piece :  player.getGamePieceList()){
        if(piece.inHome()){
          gameOver = true;
        }
      }
    }
    if(gameOver){
      Player lowestPlayer = null;
      int lowestDistance = Integer.MAX_VALUE;
      for(Player player : allPlayers){
        int currentPlayerScore = player.totalDistanceFromHomeCalculator(player.getGamePieceList(), (sideLength-1)*4, homeLength);
        if(currentPlayerScore<lowestDistance){
          lowestPlayer = player;
          lowestDistance = currentPlayerScore;
        }
      }
      return lowestPlayer;
    }
    return null;
  }

  @Override
  public void scoreOfPlayer(Player player) {
    allScores.put(player, player.totalDistanceFromHomeCalculator(player.getGamePieceList(),(sideLength-1)*4, homeLength));
  }

}
