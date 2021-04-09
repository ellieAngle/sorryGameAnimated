package ooga.controller.rules;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ooga.gamepiece.Piece;
import ooga.player.HardAIPlayer;
import ooga.player.Player;
import ooga.player.PlayerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TeamRulesTest {

  private AbstractRules rules;
  private Player player1;
  private Player player2;
  private Player player3;
  private Map<Piece, Map<Integer, Map<Piece, Integer>>> expected;
  Map<Player, List<Player>> teams;

  @BeforeEach
  void setup(){
    Map<Player, List<Player>> teams = new HashMap<>();
    player1 = new HardAIPlayer(0, 5, new int[]{-1, -1, -1, -1}, 1, PlayerState.HardAI, new int[]{0,0,0,0});
    player2 = new HardAIPlayer(10, 15, new int[]{-1, -1, -1, -1}, 2, PlayerState.HardAI, new int[]{0,0,0,0});
    player3 = new HardAIPlayer(20, 25, new int[]{-1, -1, -1, -1}, 3, PlayerState.HardAI, new int[]{0,0,0,0});
    expected = new HashMap<>();
    List<Player> player1Team = new ArrayList<>();
    player1Team.add(player3);
    List<Player> player2Team = new ArrayList<>();
    List<Player> player3Team = new ArrayList<>();
    player3Team.add(player1);
    teams.put(player1, player1Team);
    teams.put(player2, player2Team);
    teams.put(player3, player3Team);
    rules = new TeamRules(60, 5, teams);
  }

  @Test
  void handleCanMoveNoOverlap() {
    player1.getGamePieceList().get(0).setLocation(0);
    player1.getGamePieceList().get(0).setLeftBase(true);
    player3.getGamePieceList().get(0).setLocation(2);
    player3.getGamePieceList().get(0).setLeftBase(true);
    assertTrue(rules.handleCanMove(player1.getGamePieceList().get(0), 1, player1));
  }

  @Test
  void handleCanMoveSameTeamOverlap() {
    player1.getGamePieceList().get(0).setLocation(1);
    player1.getGamePieceList().get(0).setLeftBase(true);
    player3.getGamePieceList().get(0).setLocation(2);
    player3.getGamePieceList().get(0).setLeftBase(true);
    assertFalse(rules.handleCanMove(player1.getGamePieceList().get(0), 1, player1));
  }

  @Test
  void handleCanMoveDifferentTeamOverlap() {
    player1.getGamePieceList().get(0).setLocation(1);
    player1.getGamePieceList().get(0).setLeftBase(true);
    player2.getGamePieceList().get(0).setLocation(2);
    player2.getGamePieceList().get(0).setLeftBase(true);
    assertTrue(rules.handleCanMove(player1.getGamePieceList().get(0), 1, player1));
  }
}