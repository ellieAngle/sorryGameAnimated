package ooga.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ooga.cards.Card;
import ooga.cards.CardValue;
import ooga.cards.StandardCard;
import ooga.gamepiece.Piece;
import ooga.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TeamGameControllerTest {
  TeamGameController normalController;

  @BeforeEach
  void setup(){
    normalController = new TeamGameController("controllerTeamTest.properties");
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
    expectedScore.put(normalController.getAllPlayers().get(1), 1);
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
    expectedScore.put(normalController.getAllPlayers().get(0), 4);
    expectedScore.put(normalController.getAllPlayers().get(1), 4);
    expectedScore.put(normalController.getAllPlayers().get(2), 0);
    expectedScore.put(normalController.getAllPlayers().get(3), 0);
    normalController.getAllPlayers().forEach(player -> assertEquals(expectedScore.get(player), normalController.getPlayerScore(player)));
  }

  @Test
  void determineOtherPlayers() {
    List<Player> expected = new ArrayList<>();
    expected.add(normalController.getAllPlayers().get(2));
    expected.add(normalController.getAllPlayers().get(3));
    assertEquals(expected, normalController.determineOtherPlayers(normalController.getAllPlayers().get(0)));
  }

  @Test
  void teamsInRulesInitialized(){
    normalController.setCurrentCard(new StandardCard(CardValue.FIVE));
    Map<Piece, Map<Integer, Map<Piece, Integer>>> expected = new HashMap<>();
    try {
      assertEquals(expected , normalController.getPossibleMovements(normalController.getAllPlayers().get(0)));
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
  }

}