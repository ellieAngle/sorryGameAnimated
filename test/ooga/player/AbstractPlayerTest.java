package ooga.player;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AbstractPlayerTest {

  Player player;

  @BeforeEach
  void setup(){
    player = new EasyAIPlayer(2, 4, new int[]{-1, -1, -1, -1}, 1, PlayerState.EasyAI, new int[]{0,0,0,0});
  }

  @Test
  void hasWonNoneInBase() {
    assertFalse(player.hasWon());
  }

  @Test
  void hasWonAllButOneInBase() {
    player.getGamePieceList().get(0).setInHome(true);
    player.getGamePieceList().get(1).setInHome(true);
    player.getGamePieceList().get(2).setInHome(true);
    assertFalse(player.hasWon());
  }

  @Test
  void hasWon() {
    player.getGamePieceList().get(0).setInHome(true);
    player.getGamePieceList().get(1).setInHome(true);
    player.getGamePieceList().get(2).setInHome(true);
    player.getGamePieceList().get(3).setInHome(true);
    assertTrue(player.hasWon());
  }

  @Test
  void totalDistanceFromHomeCalculatorAllInBase() {
    assertEquals(240, player.totalDistanceFromHomeCalculator(player.getGamePieceList(), 56, 4));
  }

  @Test
  void totalDistanceFromHomeCalculatorOutOfBaseShortOfBase() {
    player.getGamePieceList().get(0).setLeftBase(true);
    player.getGamePieceList().get(0).setLocation(0);
    assertEquals(186, player.totalDistanceFromHomeCalculator(player.getGamePieceList(), 56, 4));
  }

  @Test
  void totalDistanceFromHomeCalculatorOutOfBasePastBase(){
    player.getGamePieceList().get(0).setLeftBase(true);
    player.getGamePieceList().get(0).setLocation(3);
    assertEquals(239, player.totalDistanceFromHomeCalculator(player.getGamePieceList(), 56, 4));
  }

  @Test
  void totalDistanceFromHomeCalculatorOutOfBaseAtHome(){
    player.getGamePieceList().get(0).setLeftBase(true);
    player.getGamePieceList().get(0).setLocation(2);
    assertEquals(184, player.totalDistanceFromHomeCalculator(player.getGamePieceList(), 56, 4));
  }
}