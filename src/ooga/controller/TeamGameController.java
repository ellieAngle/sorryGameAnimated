package ooga.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ooga.InputException;
import ooga.controller.rules.TeamRules;
import ooga.fileManager.PropertiesReader;
import ooga.gamepiece.Piece;
import ooga.player.Player;

/**
 * @author Hayden Lau
 *
 * Team Game Controller is the controller that handles the team game. It extends the Abstract Controller
 * and is an example of how the Controller API can be implemented for different types of games. It differs in how the other players are decided and the score as it is based on the teams
 *
 */
public class TeamGameController extends AbstractController {

  private Map<Player, List<Player>> teams;

  public TeamGameController(String propertiesFile) throws InputException {
    super(propertiesFile);
    PropertiesReader propertiesReader = new PropertiesReader(propertiesFile);
    String team1 = propertiesReader.propertyFileReader("Team1", propertiesFile);
    String team2 = propertiesReader.propertyFileReader("Team2", propertiesFile);
    int[] teamOne = convertStringToIntLocation(team1);
    int[] teamTwo = convertStringToIntLocation(team2);
    teams = new HashMap<>();
    super.allPlayers.forEach(player -> {
      List<Player> team = new ArrayList<>();
      boolean thisTeam = false;
      for (int i : teamOne) {
        team.add(super.allPlayers.get(i - 1));
        if (player.getPlayerNumber() == i) {
          thisTeam = true;
        }
      }
      if (!thisTeam) {
        team.clear();
        for (int i : teamTwo) {
          team.add(super.allPlayers.get(i - 1));
        }
      }
      teams.put(player, team);
    });
    rules = new TeamRules((sideLength - 1) * 4, homeLength, teams);
  }

  /**
   * This method determines the player who has won based on whichever player got all of their pieces in the home
   *
   * @return the player who has won
   */
  @Override
  public Player determineWinner() {
    for (Player player : allPlayers) {
      if (player.hasWon()) {
        return player;
      }
    }
    return null;
  }

  /**
   * Determines the score based on how many pieces are in the home across the player's entire team
   *
   * @param player the player who has won
   */
  @Override
  public void scoreOfPlayer(Player player) {
    List<Piece> pieces = new ArrayList<>();
    teams.get(player).forEach(player1 -> pieces.addAll(player1.getGamePieceList()));
    int score = 0;
    for (Piece tempPiece : pieces) {
      if (tempPiece.inHome()) {
        score += 1;
      }
    }
    allScores.put(player, score);
  }

  /**
   * determines the other players by removing the player's team from the list of all players
   *
   * @param currentPlayer current player to determine other player for
   * @return the List of other players
   */
  @Override
  List<Player> determineOtherPlayers(Player currentPlayer) {
    List<Player> otherPlayers = new ArrayList<>(allPlayers);
    otherPlayers.removeAll(teams.get(currentPlayer));
    return otherPlayers;
  }
}
