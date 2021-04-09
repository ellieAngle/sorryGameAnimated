package ooga.controller.rules;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ooga.cards.CardValue;
import ooga.cards.StandardCard;
import ooga.gamepiece.Piece;
import ooga.player.HardAIPlayer;
import ooga.player.Player;
import ooga.player.PlayerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AbstractRulesTest {
  private AbstractRules rules;
  private List<Player> allPlayers;
  private Player player1;
  private Player player2;
  private Map<Piece, Map<Integer, Map<Piece, Integer>>> expected;

  @BeforeEach
  void setup(){
    rules = new StandardRules(60, 5);
    allPlayers = new ArrayList<>();
    player1 = new HardAIPlayer(0, 5, new int[]{-1, -1, -1, -1}, 1, PlayerState.HardAI, new int[]{0,0,0,0});
    player2 = new HardAIPlayer(10, 15, new int[]{-1, -1, -1, -1}, 2, PlayerState.HardAI, new int[]{0,0,0,0});
    allPlayers.add(player2);
    expected = new HashMap<>();
  }

  @Test
  void moveNumericAmountAllInBase() {
    assertEquals(expected ,rules.moveNumericAmount(new StandardCard(CardValue.FIVE), player1, allPlayers));
  }

  @Test
  void moveNumericAmountSeveralOutOfBase() {
    player1.getGamePieceList().get(0).setLocation(10);
    player1.getGamePieceList().get(0).setLeftBase(true);
    player1.getGamePieceList().get(1).setLocation(11);
    player1.getGamePieceList().get(1).setLeftBase(true);
    Map<Integer, Map<Piece, Integer>> mapPiece0 = new HashMap<>();
    mapPiece0.put(15, null);
    Map<Integer, Map<Piece, Integer>> mapPiece1 = new HashMap<>();
    mapPiece1.put(16, null);
    expected.put(player1.getGamePieceList().get(0), mapPiece0);
    expected.put(player1.getGamePieceList().get(1), mapPiece1);
    assertEquals(expected ,rules.moveNumericAmount(new StandardCard(CardValue.FIVE), player1, allPlayers));
  }

  @Test
  void moveNumericAmountNegative() {
    player1.getGamePieceList().get(0).setLocation(10);
    player1.getGamePieceList().get(0).setLeftBase(true);
    Map<Integer, Map<Piece, Integer>> mapPiece0 = new HashMap<>();
    mapPiece0.put(6, null);
    expected.put(player1.getGamePieceList().get(0), mapPiece0);
    assertEquals(expected ,rules.moveNumericAmount(new StandardCard(CardValue.FOUR), player1, allPlayers));
  }

  @Test
  void moveManOutAllInBase() {
    Map<Integer, Map<Piece, Integer>> mapPiece0 = new HashMap<>();
    mapPiece0.put(5, null);
    Map<Integer, Map<Piece, Integer>> mapPiece1 = new HashMap<>();
    mapPiece1.put(5, null);
    Map<Integer, Map<Piece, Integer>> mapPiece2 = new HashMap<>();
    mapPiece2.put(5, null);
    Map<Integer, Map<Piece, Integer>> mapPiece3 = new HashMap<>();
    mapPiece3.put(5, null);
    expected.put(player1.getGamePieceList().get(0), mapPiece0);
    expected.put(player1.getGamePieceList().get(1), mapPiece1);
    expected.put(player1.getGamePieceList().get(2), mapPiece2);
    expected.put(player1.getGamePieceList().get(3), mapPiece3);
    assertEquals(expected ,rules.moveManOut(new StandardCard(CardValue.ONE), player1, allPlayers));
  }

  @Test
  void moveManOutOneRightOutsideBase() {
    player1.getGamePieceList().get(0).setLocation(5);
    player1.getGamePieceList().get(0).setLeftBase(true);
    Map<Integer, Map<Piece, Integer>> mapPiece0 = new HashMap<>();
    mapPiece0.put(6, null);
    expected.put(player1.getGamePieceList().get(0), mapPiece0);
    assertEquals(expected ,rules.moveManOut(new StandardCard(CardValue.ONE), player1, allPlayers));
  }

  @Test
  void moveManOutMultipleMovements() {
    player1.getGamePieceList().get(0).setLocation(6);
    player1.getGamePieceList().get(0).setLeftBase(true);
    Map<Integer, Map<Piece, Integer>> mapPiece0 = new HashMap<>();
    mapPiece0.put(8, null);
    Map<Integer, Map<Piece, Integer>> mapPiece1 = new HashMap<>();
    mapPiece1.put(5, null);
    Map<Integer, Map<Piece, Integer>> mapPiece2 = new HashMap<>();
    mapPiece2.put(5, null);
    Map<Integer, Map<Piece, Integer>> mapPiece3 = new HashMap<>();
    mapPiece3.put(5, null);
    expected.put(player1.getGamePieceList().get(0), mapPiece0);
    expected.put(player1.getGamePieceList().get(1), mapPiece1);
    expected.put(player1.getGamePieceList().get(2), mapPiece2);
    expected.put(player1.getGamePieceList().get(3), mapPiece3);
    assertEquals(expected ,rules.moveManOut(new StandardCard(CardValue.TWO), player1, allPlayers));
  }

  @Test
  void handleTenNormal() {
    player1.getGamePieceList().get(0).setLocation(5);
    player1.getGamePieceList().get(0).setLeftBase(true);
    Map<Integer, Map<Piece, Integer>> mapPiece0 = new HashMap<>();
    mapPiece0.put(15, null);
    mapPiece0.put(4, null);
    expected.put(player1.getGamePieceList().get(0), mapPiece0);
    assertEquals(expected ,rules.handleTen(new StandardCard(CardValue.TEN), player1, allPlayers));
  }

  @Test
  void handleTenMovementIntoBase() {
    player1.getGamePieceList().get(0).setLocation(55);
    player1.getGamePieceList().get(0).setLeftBase(true);
    Map<Integer, Map<Piece, Integer>> mapPiece0 = new HashMap<>();
    mapPiece0.put(-5, null);
    mapPiece0.put(54, null);
    expected.put(player1.getGamePieceList().get(0), mapPiece0);
    assertEquals(expected ,rules.handleTen(new StandardCard(CardValue.TEN), player1, allPlayers));
  }

  @Test
  void handleTenMovementOutOfBase() {
    player2.getGamePieceList().get(0).setLocation(-1);
    player2.getGamePieceList().get(0).setLeftBase(true);
    Map<Integer, Map<Piece, Integer>> mapPiece0 = new HashMap<>();
    mapPiece0.put(10, null);
    expected.put(player2.getGamePieceList().get(0), mapPiece0);
    assertEquals(expected ,rules.handleTen(new StandardCard(CardValue.TEN), player2, allPlayers));
  }

  @Test
  void handleElevenPieceInSafetySquare() {
    player1.getGamePieceList().get(0).setLocation(-1);
    player1.getGamePieceList().get(0).setLeftBase(true);
    player2.getGamePieceList().get(0).setLocation(11);
    player2.getGamePieceList().get(0).setLeftBase(true);
    assertEquals(expected ,rules.handleEleven(new StandardCard(CardValue.ELEVEN), player1, allPlayers));
  }

  @Test
  void handleElevenOtherPlayerInSafetySquare() {
    player2.getGamePieceList().get(0).setLocation(-1);
    player2.getGamePieceList().get(0).setLeftBase(true);
    player1.getGamePieceList().get(0).setLocation(30);
    player1.getGamePieceList().get(0).setLeftBase(true);
    Map<Integer, Map<Piece, Integer>> mapPiece0 = new HashMap<>();
    mapPiece0.put(41, null);
    expected.put(player1.getGamePieceList().get(0), mapPiece0);
    assertEquals(expected ,rules.handleEleven(new StandardCard(CardValue.ELEVEN), player1, allPlayers));
  }

  @Test
  void handleElevenSwitchable() {
    player2.getGamePieceList().get(0).setLocation(1);
    player2.getGamePieceList().get(0).setLeftBase(true);
    player1.getGamePieceList().get(0).setLocation(30);
    player1.getGamePieceList().get(0).setLeftBase(true);
    Map<Integer, Map<Piece, Integer>> mapPiece0 = new HashMap<>();
    mapPiece0.put(41, null);
    Map<Piece, Integer> other = new HashMap<>();
    other.put(player2.getGamePieceList().get(0),30);
    mapPiece0.put(1, other);
    expected.put(player1.getGamePieceList().get(0), mapPiece0);
    assertEquals(expected ,rules.handleEleven(new StandardCard(CardValue.ELEVEN), player1, allPlayers));
  }

  @Test
  void sorryAllOutOfBase() {
    player1.getGamePieceList().get(0).setLocation(30);
    player1.getGamePieceList().get(0).setLeftBase(true);
    player1.getGamePieceList().get(1).setLocation(31);
    player1.getGamePieceList().get(1).setLeftBase(true);
    player1.getGamePieceList().get(2).setLocation(32);
    player1.getGamePieceList().get(2).setLeftBase(true);
    player1.getGamePieceList().get(3).setLocation(33);
    player1.getGamePieceList().get(3).setLeftBase(true);
    player2.getGamePieceList().get(0).setLocation(34);
    player2.getGamePieceList().get(0).setLeftBase(true);
    player2.getGamePieceList().get(1).setLocation(35);
    player2.getGamePieceList().get(1).setLeftBase(true);
    player2.getGamePieceList().get(2).setLocation(36);
    player2.getGamePieceList().get(2).setLeftBase(true);
    player2.getGamePieceList().get(3).setLocation(37);
    player2.getGamePieceList().get(3).setLeftBase(true);
    assertEquals(expected ,rules.sorry(new StandardCard(CardValue.SORRY), player1, allPlayers));
  }

  @Test
  void sorryOneInBaseOtherOneOut() {
    player1.getGamePieceList().get(1).setLocation(31);
    player1.getGamePieceList().get(1).setLeftBase(true);
    player1.getGamePieceList().get(2).setLocation(32);
    player1.getGamePieceList().get(2).setLeftBase(true);
    player1.getGamePieceList().get(3).setLocation(33);
    player1.getGamePieceList().get(3).setLeftBase(true);
    player2.getGamePieceList().get(0).setLocation(34);
    player2.getGamePieceList().get(0).setLeftBase(true);
    Map<Integer, Map<Piece, Integer>> mapPiece0 = new HashMap<>();
    mapPiece0.put(34, null);
    expected.put(player1.getGamePieceList().get(0), mapPiece0);
    assertEquals(expected ,rules.sorry(new StandardCard(CardValue.SORRY), player1, allPlayers));
  }

  @Test
  void sorryOneInBaseOtherSafety() {
    player1.getGamePieceList().get(1).setLocation(31);
    player1.getGamePieceList().get(1).setLeftBase(true);
    player1.getGamePieceList().get(2).setLocation(32);
    player1.getGamePieceList().get(2).setLeftBase(true);
    player1.getGamePieceList().get(3).setLocation(33);
    player1.getGamePieceList().get(3).setLeftBase(true);
    player2.getGamePieceList().get(0).setLocation(-1);
    player2.getGamePieceList().get(0).setLeftBase(true);
    assertEquals(expected ,rules.sorry(new StandardCard(CardValue.SORRY), player1, allPlayers));
  }

  @Test
  void splitSevenOnlyOneInSafetySquare() {
    player2.getGamePieceList().get(0).setLocation(-1);
    player2.getGamePieceList().get(0).setLeftBase(true);
    assertEquals(expected ,rules.splitSeven(new StandardCard(CardValue.SEVEN), player2, allPlayers));
  }

  @Test
  void splitSevenTwoOut() {
    player2.getGamePieceList().get(0).setLocation(0);
    player2.getGamePieceList().get(0).setLeftBase(true);
    player2.getGamePieceList().get(1).setLocation(30);
    player2.getGamePieceList().get(1).setLeftBase(true);
    Map<Integer, Map<Piece, Integer>> mapPiece0 = new HashMap<>();
    Map<Piece, Integer> complementary1 = new HashMap<>();
    Map<Piece, Integer> complementary2 = new HashMap<>();
    Map<Piece, Integer> complementary3 = new HashMap<>();
    Map<Piece, Integer> complementary4 = new HashMap<>();
    Map<Piece, Integer> complementary5 = new HashMap<>();
    Map<Piece, Integer> complementary6 = new HashMap<>();
    complementary6.put(player2.getGamePieceList().get(1),31);
    complementary5.put(player2.getGamePieceList().get(1),32);
    complementary4.put(player2.getGamePieceList().get(1),33);
    complementary3.put(player2.getGamePieceList().get(1),34);
    complementary2.put(player2.getGamePieceList().get(1),35);
    complementary1.put(player2.getGamePieceList().get(1),36);
    mapPiece0.put(7, null);
    mapPiece0.put(1, complementary1);
    mapPiece0.put(2, complementary2);
    mapPiece0.put(3, complementary3);
    mapPiece0.put(4, complementary4);
    mapPiece0.put(5, complementary5);
    mapPiece0.put(6, complementary6);
    Map<Integer, Map<Piece, Integer>> mapPiece1 = new HashMap<>();
    Map<Piece, Integer> complementary31 = new HashMap<>();
    Map<Piece, Integer> complementary32 = new HashMap<>();
    Map<Piece, Integer> complementary33 = new HashMap<>();
    Map<Piece, Integer> complementary34 = new HashMap<>();
    Map<Piece, Integer> complementary35 = new HashMap<>();
    Map<Piece, Integer> complementary36 = new HashMap<>();
    complementary36.put(player2.getGamePieceList().get(0),1);
    complementary35.put(player2.getGamePieceList().get(0),2);
    complementary34.put(player2.getGamePieceList().get(0),3);
    complementary33.put(player2.getGamePieceList().get(0),4);
    complementary32.put(player2.getGamePieceList().get(0),5);
    complementary31.put(player2.getGamePieceList().get(0),6);
    mapPiece1.put(37, null);
    mapPiece1.put(31, complementary31);
    mapPiece1.put(32, complementary32);
    mapPiece1.put(33, complementary33);
    mapPiece1.put(34, complementary34);
    mapPiece1.put(35, complementary35);
    mapPiece1.put(36, complementary36);
    expected.put(player2.getGamePieceList().get(0), mapPiece0);
    expected.put(player2.getGamePieceList().get(1), mapPiece1);
    assertEquals(expected ,rules.splitSeven(new StandardCard(CardValue.SEVEN), player2, allPlayers));
  }

  @Test
  void splitSevenTwoOutButLimited() {
    player2.getGamePieceList().get(0).setLocation(-4);
    player2.getGamePieceList().get(0).setLeftBase(true);
    player2.getGamePieceList().get(1).setLocation(30);
    player2.getGamePieceList().get(1).setLeftBase(true);
    Map<Integer, Map<Piece, Integer>> mapPiece0 = new HashMap<>();
    Map<Piece, Integer> complementary5 = new HashMap<>();
    complementary5.put(player2.getGamePieceList().get(1),36);
    mapPiece0.put(-5, complementary5);
    Map<Integer, Map<Piece, Integer>> mapPiece1 = new HashMap<>();
    Map<Piece, Integer> complementary36 = new HashMap<>();
    complementary36.put(player2.getGamePieceList().get(0),-5);
    mapPiece1.put(37, null);
    mapPiece1.put(36, complementary36);
    expected.put(player2.getGamePieceList().get(0), mapPiece0);
    expected.put(player2.getGamePieceList().get(1), mapPiece1);
    assertEquals(expected ,rules.splitSeven(new StandardCard(CardValue.SEVEN), player2, allPlayers));
  }

  @Test
  void checkSharedSpaceNoneShared() {
    List<Piece> expected = new ArrayList<>();
    assertEquals(expected, rules.checkSharedSpace(player1, allPlayers));
  }

  @Test
  void checkSharedSpaceSharedSpacesExist() {
    player1.getGamePieceList().get(0).setLocation(30);
    player1.getGamePieceList().get(0).setLeftBase(true);
    player2.getGamePieceList().get(1).setLocation(30);
    player2.getGamePieceList().get(1).setLeftBase(true);
    List<Piece> expected = new ArrayList<>();
    expected.add(player2.getGamePieceList().get(1));
    assertEquals(expected, rules.checkSharedSpace(player1, allPlayers));
  }

  @Test
  void checkSharedSpacePiecesInHome() {
    player1.getGamePieceList().get(0).setLocation(-3);
    player1.getGamePieceList().get(0).setLeftBase(true);
    player2.getGamePieceList().get(1).setLocation(-3);
    player2.getGamePieceList().get(1).setLeftBase(true);
    List<Piece> expected = new ArrayList<>();
    assertEquals(expected, rules.checkSharedSpace(player1, allPlayers));
  }

  @Test
  void checkSlideCollisionPieceAtStartAndEnd() {
    player2.getGamePieceList().get(1).setLocation(1);
    player2.getGamePieceList().get(1).setLeftBase(true);
    player2.getGamePieceList().get(2).setLocation(6);
    player2.getGamePieceList().get(2).setLeftBase(true);
    List<Piece> expected = new ArrayList<>();
    expected.add(player2.getGamePieceList().get(1));
    expected.add(player2.getGamePieceList().get(2));
    rules.checkSlideCollision(1, 5, allPlayers);
  }

  @Test
  void checkSlideCollisionPieceInside() {
    player2.getGamePieceList().get(1).setLocation(3);
    player2.getGamePieceList().get(1).setLeftBase(true);
    List<Piece> expected = new ArrayList<>();
    expected.add(player2.getGamePieceList().get(1));
    rules.checkSlideCollision(1, 5, allPlayers);
  }

  @Test
  void checkSlideCollisionPieceOutside() {
    player2.getGamePieceList().get(1).setLocation(7);
    player2.getGamePieceList().get(1).setLeftBase(true);
    List<Piece> expected = new ArrayList<>();
    rules.checkSlideCollision(1, 5, allPlayers);
  }

  @Test
  void getPossibleActionsMoveNumericAmountCard(){
    player1.getGamePieceList().get(0).setLocation(10);
    player1.getGamePieceList().get(0).setLeftBase(true);
    player1.getGamePieceList().get(1).setLocation(11);
    player1.getGamePieceList().get(1).setLeftBase(true);
    Map<Integer, Map<Piece, Integer>> mapPiece0 = new HashMap<>();
    mapPiece0.put(15, null);
    Map<Integer, Map<Piece, Integer>> mapPiece1 = new HashMap<>();
    mapPiece1.put(16, null);
    expected.put(player1.getGamePieceList().get(0), mapPiece0);
    expected.put(player1.getGamePieceList().get(1), mapPiece1);
    try {
      assertEquals(expected, rules.getPossibleActions(new StandardCard(CardValue.FIVE),player1, allPlayers));
    } catch (Exception e){
      e.printStackTrace();
    }
  }

  @Test
  void getPossibleActionsMoveManOutCard(){
    Map<Integer, Map<Piece, Integer>> mapPiece0 = new HashMap<>();
    mapPiece0.put(5, null);
    Map<Integer, Map<Piece, Integer>> mapPiece1 = new HashMap<>();
    mapPiece1.put(5, null);
    Map<Integer, Map<Piece, Integer>> mapPiece2 = new HashMap<>();
    mapPiece2.put(5, null);
    Map<Integer, Map<Piece, Integer>> mapPiece3 = new HashMap<>();
    mapPiece3.put(5, null);
    expected.put(player1.getGamePieceList().get(0), mapPiece0);
    expected.put(player1.getGamePieceList().get(1), mapPiece1);
    expected.put(player1.getGamePieceList().get(2), mapPiece2);
    expected.put(player1.getGamePieceList().get(3), mapPiece3);
    try {
      assertEquals(expected, rules.getPossibleActions(new StandardCard(CardValue.ONE),player1, allPlayers));
    } catch (Exception e){
      e.printStackTrace();
    }
  }

  @Test
  void getPossibleActionsHandleTenCard(){
    player1.getGamePieceList().get(0).setLocation(5);
    player1.getGamePieceList().get(0).setLeftBase(true);
    Map<Integer, Map<Piece, Integer>> mapPiece0 = new HashMap<>();
    mapPiece0.put(15, null);
    mapPiece0.put(4, null);
    expected.put(player1.getGamePieceList().get(0), mapPiece0);
    try {
      assertEquals(expected, rules.getPossibleActions(new StandardCard(CardValue.TEN),player1, allPlayers));
    } catch (Exception e){
      e.printStackTrace();
    }
  }

  @Test
  void getPossibleActionsHandleElevenCard(){
    player2.getGamePieceList().get(0).setLocation(-1);
    player2.getGamePieceList().get(0).setLeftBase(true);
    player1.getGamePieceList().get(0).setLocation(30);
    player1.getGamePieceList().get(0).setLeftBase(true);
    Map<Integer, Map<Piece, Integer>> mapPiece0 = new HashMap<>();
    mapPiece0.put(41, null);
    expected.put(player1.getGamePieceList().get(0), mapPiece0);
    try {
      assertEquals(expected, rules.getPossibleActions(new StandardCard(CardValue.ELEVEN),player1, allPlayers));
    } catch (Exception e){
      e.printStackTrace();
    }
  }

  @Test
  void getPossibleActionsSorryCard(){
    player1.getGamePieceList().get(1).setLocation(31);
    player1.getGamePieceList().get(1).setLeftBase(true);
    player1.getGamePieceList().get(2).setLocation(32);
    player1.getGamePieceList().get(2).setLeftBase(true);
    player1.getGamePieceList().get(3).setLocation(33);
    player1.getGamePieceList().get(3).setLeftBase(true);
    player2.getGamePieceList().get(0).setLocation(34);
    player2.getGamePieceList().get(0).setLeftBase(true);
    Map<Integer, Map<Piece, Integer>> mapPiece0 = new HashMap<>();
    mapPiece0.put(34, null);
    expected.put(player1.getGamePieceList().get(0), mapPiece0);
    try {
      assertEquals(expected, rules.getPossibleActions(new StandardCard(CardValue.SORRY),player1, allPlayers));
    } catch (Exception e){
      e.printStackTrace();
    }
  }

  @Test
  void getPossibleActionsSplitSevenCard(){
    player1.getGamePieceList().get(0).setLocation(-4);
    player1.getGamePieceList().get(0).setLeftBase(true);
    player1.getGamePieceList().get(1).setLocation(30);
    player1.getGamePieceList().get(1).setLeftBase(true);
    Map<Integer, Map<Piece, Integer>> mapPiece0 = new HashMap<>();
    Map<Piece, Integer> complementary5 = new HashMap<>();
    complementary5.put(player1.getGamePieceList().get(1),36);
    mapPiece0.put(-5, complementary5);
    Map<Integer, Map<Piece, Integer>> mapPiece1 = new HashMap<>();
    Map<Piece, Integer> complementary36 = new HashMap<>();
    complementary36.put(player1.getGamePieceList().get(0),-5);
    mapPiece1.put(37, null);
    mapPiece1.put(36, complementary36);
    expected.put(player1.getGamePieceList().get(0), mapPiece0);
    expected.put(player1.getGamePieceList().get(1), mapPiece1);
    try {
      assertEquals(expected, rules.getPossibleActions(new StandardCard(CardValue.SEVEN),player1, allPlayers));
    } catch (Exception e){
      e.printStackTrace();
    }
  }
}