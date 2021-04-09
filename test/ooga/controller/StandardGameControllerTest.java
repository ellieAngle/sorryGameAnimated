package ooga.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import ooga.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StandardGameControllerTest {

  StandardGameController normalController;

  @BeforeEach
  void setup(){
    normalController = new StandardGameController("controllerTest.properties");
  }

  @Test
  void determineWinnerNoneInHome() {
    assertNull(normalController.determineWinner());
  }

  @Test
  void determineWinnerOneInHome() {
    normalController.getAllPlayers().get(0).getGamePieceList().get(0).setLeftBase(true);
    normalController.getAllPlayers().get(0).getGamePieceList().get(0).setInHome(true);
    assertNull(normalController.determineWinner());
  }
  @Test
  void determineWinnerPlayerOneWinner() {
    normalController.getAllPlayers().get(0).getGamePieceList().get(0).setLeftBase(true);
    normalController.getAllPlayers().get(0).getGamePieceList().get(0).setInHome(true);
    normalController.getAllPlayers().get(0).getGamePieceList().get(1).setLeftBase(true);
    normalController.getAllPlayers().get(0).getGamePieceList().get(1).setInHome(true);
    normalController.getAllPlayers().get(0).getGamePieceList().get(2).setLeftBase(true);
    normalController.getAllPlayers().get(0).getGamePieceList().get(2).setInHome(true);
    normalController.getAllPlayers().get(0).getGamePieceList().get(3).setLeftBase(true);
    normalController.getAllPlayers().get(0).getGamePieceList().get(3).setInHome(true);
    assertEquals(normalController.getAllPlayers().get(0), normalController.determineWinner());
  }

  @Test
  void scoreOfPlayerNoneInHome() {
    normalController.updateScores();
    Map<Player, Integer> expectedScore = new HashMap<>();
    expectedScore.put(normalController.getAllPlayers().get(0), 0);
    expectedScore.put(normalController.getAllPlayers().get(1), 0);
    expectedScore.put(normalController.getAllPlayers().get(2), 0);
    expectedScore.put(normalController.getAllPlayers().get(3), 0);
    normalController.getAllPlayers().forEach(player -> assertEquals(expectedScore.get(player), normalController.getPlayerScore(player)));
  }

  @Test
  void scoreOfPlayerOneInHome() {
    normalController.getAllPlayers().get(0).getGamePieceList().get(0).setLeftBase(true);
    normalController.getAllPlayers().get(0).getGamePieceList().get(0).setInHome(true);
    normalController.updateScores();
    Map<Player, Integer> expectedScore = new HashMap<>();
    expectedScore.put(normalController.getAllPlayers().get(0), 1);
    expectedScore.put(normalController.getAllPlayers().get(1), 0);
    expectedScore.put(normalController.getAllPlayers().get(2), 0);
    expectedScore.put(normalController.getAllPlayers().get(3), 0);
    normalController.getAllPlayers().forEach(player -> assertEquals(expectedScore.get(player), normalController.getPlayerScore(player)));
  }

  @Test
  void scoreOfPlayerSomeInHomeForMultiplePlayers() {
    normalController.getAllPlayers().get(0).getGamePieceList().get(0).setLeftBase(true);
    normalController.getAllPlayers().get(0).getGamePieceList().get(0).setInHome(true);
    normalController.getAllPlayers().get(0).getGamePieceList().get(1).setLeftBase(true);
    normalController.getAllPlayers().get(0).getGamePieceList().get(1).setInHome(true);
    normalController.getAllPlayers().get(0).getGamePieceList().get(2).setLeftBase(true);
    normalController.getAllPlayers().get(0).getGamePieceList().get(2).setInHome(true);
    normalController.getAllPlayers().get(1).getGamePieceList().get(3).setLeftBase(true);
    normalController.getAllPlayers().get(1).getGamePieceList().get(3).setInHome(true);
    normalController.updateScores();
    Map<Player, Integer> expectedScore = new HashMap<>();
    expectedScore.put(normalController.getAllPlayers().get(0), 3);
    expectedScore.put(normalController.getAllPlayers().get(1), 1);
    expectedScore.put(normalController.getAllPlayers().get(2), 0);
    expectedScore.put(normalController.getAllPlayers().get(3), 0);
    normalController.getAllPlayers().forEach(player -> assertEquals(expectedScore.get(player), normalController.getPlayerScore(player)));
  }
}