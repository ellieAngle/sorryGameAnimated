package ooga.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import ooga.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ScoreGameControllerTest {

  ScoreGameController normalController;

  @BeforeEach
  void setup(){
    normalController = new ScoreGameController("controllerTest.properties");
  }

  @Test
  void determineWinnerNoneInHome() {
    assertNull(normalController.determineWinner());
  }

  @Test
  void determineWinnerGameOverNoOtherPieceOut() {
    normalController.getAllPlayers().get(0).getGamePieceList().get(0).setLeftBase(true);
    normalController.getAllPlayers().get(0).getGamePieceList().get(0).setInHome(true);
    assertEquals(normalController.getAllPlayers().get(0),normalController.determineWinner());
  }

  @Test
  void determineWinnerGameOverPlayerWithPieceInNotWinner() {
    normalController.getAllPlayers().get(0).getGamePieceList().get(0).setLeftBase(true);
    normalController.getAllPlayers().get(0).getGamePieceList().get(0).setInHome(true);
    normalController.getAllPlayers().get(1).getGamePieceList().get(0).setLeftBase(true);
    normalController.getAllPlayers().get(1).getGamePieceList().get(0).setLocation(10);
    normalController.getAllPlayers().get(1).getGamePieceList().get(1).setLeftBase(true);
    normalController.getAllPlayers().get(1).getGamePieceList().get(1).setLocation(-1);
    normalController.getAllPlayers().get(1).getGamePieceList().get(2).setLeftBase(true);
    normalController.getAllPlayers().get(1).getGamePieceList().get(2).setLocation(5);
    normalController.getAllPlayers().get(1).getGamePieceList().get(3).setLeftBase(true);
    normalController.getAllPlayers().get(1).getGamePieceList().get(3).setLocation(-3);

    assertEquals(normalController.getAllPlayers().get(1),normalController.determineWinner());
  }

  @Test
  void scoreOfPlayerAllInBase() {
    normalController.updateScores();
    Map<Player, Integer> expectedScore = new HashMap<>();
    expectedScore.put(normalController.getAllPlayers().get(0), 240);
    expectedScore.put(normalController.getAllPlayers().get(1), 240);
    expectedScore.put(normalController.getAllPlayers().get(2), 240);
    expectedScore.put(normalController.getAllPlayers().get(3), 240);
    normalController.getAllPlayers().forEach(player -> assertEquals(expectedScore.get(player), normalController.getPlayerScore(player)));
  }

  @Test
  void scoreOfPlayerOneInHome() {
    normalController.getAllPlayers().get(0).getGamePieceList().get(0).setLeftBase(true);
    normalController.getAllPlayers().get(0).getGamePieceList().get(0).setLocation(-4);
    normalController.getAllPlayers().get(0).getGamePieceList().get(0).setInHome(true);
    normalController.updateScores();
    Map<Player, Integer> expectedScore = new HashMap<>();
    expectedScore.put(normalController.getAllPlayers().get(0), 180);
    expectedScore.put(normalController.getAllPlayers().get(1), 240);
    expectedScore.put(normalController.getAllPlayers().get(2), 240);
    expectedScore.put(normalController.getAllPlayers().get(3), 240);
    normalController.getAllPlayers().forEach(player -> assertEquals(expectedScore.get(player), normalController.getPlayerScore(player)));
  }

  @Test
  void scoreOfPlayerOutOfBaseLessThanHomeLocation() {
    normalController.getAllPlayers().get(0).getGamePieceList().get(0).setLeftBase(true);
    normalController.getAllPlayers().get(0).getGamePieceList().get(0).setLocation(0);
    normalController.updateScores();
    Map<Player, Integer> expectedScore = new HashMap<>();
    expectedScore.put(normalController.getAllPlayers().get(0), 186);
    expectedScore.put(normalController.getAllPlayers().get(1), 240);
    expectedScore.put(normalController.getAllPlayers().get(2), 240);
    expectedScore.put(normalController.getAllPlayers().get(3), 240);
    normalController.getAllPlayers().forEach(player -> assertEquals(expectedScore.get(player), normalController.getPlayerScore(player)));
  }

  @Test
  void scoreOfPlayerOutOfBaseGreaterThanHomeLocation() {
    normalController.getAllPlayers().get(0).getGamePieceList().get(0).setLeftBase(true);
    normalController.getAllPlayers().get(0).getGamePieceList().get(0).setLocation(3);
    normalController.updateScores();
    Map<Player, Integer> expectedScore = new HashMap<>();
    expectedScore.put(normalController.getAllPlayers().get(0), 239);
    expectedScore.put(normalController.getAllPlayers().get(1), 240);
    expectedScore.put(normalController.getAllPlayers().get(2), 240);
    expectedScore.put(normalController.getAllPlayers().get(3), 240);
    normalController.getAllPlayers().forEach(player -> assertEquals(expectedScore.get(player), normalController.getPlayerScore(player)));
  }

  @Test
  void scoreOfPlayerOutOfBaseAtHomeLocation() {
    normalController.getAllPlayers().get(0).getGamePieceList().get(0).setLeftBase(true);
    normalController.getAllPlayers().get(0).getGamePieceList().get(0).setLocation(2);
    normalController.updateScores();
    Map<Player, Integer> expectedScore = new HashMap<>();
    expectedScore.put(normalController.getAllPlayers().get(0), 184);
    expectedScore.put(normalController.getAllPlayers().get(1), 240);
    expectedScore.put(normalController.getAllPlayers().get(2), 240);
    expectedScore.put(normalController.getAllPlayers().get(3), 240);
    normalController.getAllPlayers().forEach(player -> assertEquals(expectedScore.get(player), normalController.getPlayerScore(player)));
  }
}