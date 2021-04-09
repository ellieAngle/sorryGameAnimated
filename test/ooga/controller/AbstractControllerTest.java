package ooga.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import ooga.cards.Card;
import ooga.cards.CardValue;
import ooga.cards.StandardCard;
import ooga.gamepiece.Piece;
import ooga.player.Player;
import ooga.view.AlertView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
class AbstractControllerTest {
    private AbstractController normalController;
    private AbstractController longController;

    @BeforeEach
    void setup(){
        normalController = new StandardGameController("controllerTest.properties");
        longController = new StandardGameController("controllerTest1.properties");
    }

    /*
    Works for side length 15
     */
    @Test
    void createHomeOrBase() {
        normalController.createHomeOrBase(1, 1, 4, false);
        normalController.createHomeOrBase(1, 1, 2, true);
        normalController.createHomeOrBase(2, 2, 18, false);
        normalController.createHomeOrBase(2, 2, 16, true);
        normalController.createHomeOrBase(3, 3, 32, false);
        normalController.createHomeOrBase(3, 3, 30, true);
        normalController.createHomeOrBase(4, 4, 46, false);
        normalController.createHomeOrBase(4, 4, 44, true);
        Map<Integer, List<Integer>> home = normalController.getHomeLocation();
        Map<Integer, List<Integer>> base = normalController.getBaseLocation();
        assertEquals(0, home.get(1).get(0));
        assertEquals(2, home.get(1).get(1));
        assertEquals(2, home.get(2).get(0));
        assertEquals(14, home.get(2).get(1));
        assertEquals(14, home.get(3).get(0));
        assertEquals(12, home.get(3).get(1));
        assertEquals(12, home.get(4).get(0));
        assertEquals(0, home.get(4).get(1));
        assertEquals(1, base.get(1).get(0));
        assertEquals(4, base.get(1).get(1));
        assertEquals(4, base.get(2).get(0));
        assertEquals(13, base.get(2).get(1));
        assertEquals(13, base.get(3).get(0));
        assertEquals(10, base.get(3).get(1));
        assertEquals(10, base.get(4).get(0));
        assertEquals(1, base.get(4).get(1));
    }

    @Test
    void createHomeOrBaseLargerSideLength() {
        longController.createHomeOrBase(1, 1, 4, false);
        longController.createHomeOrBase(1, 1, 2, true);
        longController.createHomeOrBase(2, 2, 28, false);
        longController.createHomeOrBase(2, 2, 26, true);
        longController.createHomeOrBase(3, 3, 52, false);
        longController.createHomeOrBase(3, 3, 50, true);
        longController.createHomeOrBase(4, 4, 76, false);
        longController.createHomeOrBase(4, 4, 74, true);
        longController.createHomeOrBase(1, 5, 18, false);
        longController.createHomeOrBase(1, 5, 16, true);
        longController.createHomeOrBase(3, 6, 62, false);
        longController.createHomeOrBase(3, 6, 60, true);
        Map<Integer, List<Integer>> home = longController.getHomeLocation();
        Map<Integer, List<Integer>> base = longController.getBaseLocation();
        assertEquals(0, home.get(1).get(0));
        assertEquals(2, home.get(1).get(1));
        assertEquals(2, home.get(2).get(0));
        assertEquals(24, home.get(2).get(1));
        assertEquals(24, home.get(3).get(0));
        assertEquals(22, home.get(3).get(1));
        assertEquals(22, home.get(4).get(0));
        assertEquals(0, home.get(4).get(1));
        assertEquals(0, home.get(5).get(0));
        assertEquals(16, home.get(5).get(1));
        assertEquals(24, home.get(6).get(0));
        assertEquals(12, home.get(6).get(1));
        assertEquals(1, base.get(1).get(0));
        assertEquals(4, base.get(1).get(1));
        assertEquals(1, base.get(5).get(0));
        assertEquals(18, base.get(5).get(1));
        assertEquals(23, base.get(6).get(0));
        assertEquals(10, base.get(6).get(1));
    }

    @Test
    void creatPlayersWithNormalSideLength(){
        Map<Integer, List<Integer>> home = normalController.getHomeLocation();
        Map<Integer, List<Integer>> base = normalController.getBaseLocation();
        assertEquals(0, home.get(1).get(0));
        assertEquals(2, home.get(1).get(1));
        assertEquals(2, home.get(2).get(0));
        assertEquals(14, home.get(2).get(1));
        assertEquals(14, home.get(3).get(0));
        assertEquals(12, home.get(3).get(1));
        assertEquals(12, home.get(4).get(0));
        assertEquals(0, home.get(4).get(1));
        assertEquals(1, base.get(1).get(0));
        assertEquals(4, base.get(1).get(1));
        assertEquals(4, base.get(2).get(0));
        assertEquals(13, base.get(2).get(1));
        assertEquals(13, base.get(3).get(0));
        assertEquals(10, base.get(3).get(1));
        assertEquals(10, base.get(4).get(0));
        assertEquals(1, base.get(4).get(1));
    }

    @Test
    void creatPlayersWithLongerSideLength(){
        Map<Integer, List<Integer>> home = longController.getHomeLocation();
        Map<Integer, List<Integer>> base = longController.getBaseLocation();
        assertEquals(0, home.get(1).get(0));
        assertEquals(2, home.get(1).get(1));
        assertEquals(2, home.get(2).get(0));
        assertEquals(24, home.get(2).get(1));
        assertEquals(24, home.get(3).get(0));
        assertEquals(22, home.get(3).get(1));
        assertEquals(22, home.get(4).get(0));
        assertEquals(0, home.get(4).get(1));
        assertEquals(0, home.get(5).get(0));
        assertEquals(16, home.get(5).get(1));
        assertEquals(24, home.get(6).get(0));
        assertEquals(12, home.get(6).get(1));
        assertEquals(1, base.get(1).get(0));
        assertEquals(4, base.get(1).get(1));
        assertEquals(1, base.get(5).get(0));
        assertEquals(18, base.get(5).get(1));
        assertEquals(23, base.get(6).get(0));
        assertEquals(10, base.get(6).get(1));
    }

    @Test
    void sideLength(){
        assertEquals(15, normalController.getSideLength());
        assertEquals(25, longController.getSideLength());
    }

    @Test
    void determinePlayer(){
        assertEquals(1, normalController.determinePlayerTurn().getPlayerNumber());
        assertEquals(2, normalController.determinePlayerTurn().getPlayerNumber());
        assertEquals(3, normalController.determinePlayerTurn().getPlayerNumber());
        assertEquals(4, normalController.determinePlayerTurn().getPlayerNumber());
        assertEquals(1, normalController.determinePlayerTurn().getPlayerNumber());
        assertEquals(2, normalController.determinePlayerTurn().getPlayerNumber());
        assertEquals(3, normalController.determinePlayerTurn().getPlayerNumber());
        assertEquals(4, normalController.determinePlayerTurn().getPlayerNumber());
    }

    @Test
    void handlePlayerTurnCollision(){
        Map<Piece, Integer> movementPieces = new HashMap<>();
        Player player1 = normalController.getAllPlayers().get(0);
        movementPieces.put(player1.getGamePieceList().get(0), 1);
        normalController.getAllPlayers().get(1).getGamePieceList().get(0).setLocation(1);
        normalController.getAllPlayers().get(1).getGamePieceList().get(0).setLeftBase(true);
        List<Piece> expected = new ArrayList<>();;
        expected.add(player1.getGamePieceList().get(0));
        expected.add(normalController.getAllPlayers().get(1).getGamePieceList().get(0));
        assertEquals(expected, normalController.handlePlayerTurn(player1, movementPieces));
    }

    @Test
    void handlePlayerTurnCollisionSlide(){
        normalController.createPlayers();
        Map<Piece, Integer> movementPieces = new HashMap<>();
        Player player1 = normalController.getAllPlayers().get(0);
        movementPieces.put(player1.getGamePieceList().get(0), 6);
        player1.getGamePieceList().get(1).setLocation(7);
        player1.getGamePieceList().get(1).setLeftBase(true);
        normalController.getAllPlayers().get(1).getGamePieceList().get(0).setLocation(8);
        normalController.getAllPlayers().get(1).getGamePieceList().get(0).setLeftBase(true);
        List<Piece> expected = new ArrayList<>();;
        expected.add(player1.getGamePieceList().get(0));
        expected.add(player1.getGamePieceList().get(1));
        expected.add(normalController.getAllPlayers().get(1).getGamePieceList().get(0));
        assertEquals(expected, normalController.handlePlayerTurn(player1, movementPieces));
        assertEquals(9 ,player1.getGamePieceList().get(0).getLocation());
    }

    @Test
    void getPossibleMovementsNoMovementsPossible(){
        normalController.setCurrentCard(new StandardCard(CardValue.FIVE));
        try {
            assertEquals(new HashMap<>(), normalController.getPossibleMovements(normalController.getAllPlayers().get(0)));
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getPossibleMovementsMovementOutOfBase(){
        normalController.setCurrentCard(new StandardCard(CardValue.ONE));
        Map<Piece, Map<Integer, Map<Piece, Integer>>> expected = new HashMap<>();
        Map<Integer, Map<Piece, Integer>> integerMap0 = new HashMap<>();
        integerMap0.put(4, null);
        Map<Integer, Map<Piece, Integer>> integerMap1 = new HashMap<>();
        integerMap1.put(4, null);
        Map<Integer, Map<Piece, Integer>> integerMap2 = new HashMap<>();
        integerMap2.put(4, null);
        Map<Integer, Map<Piece, Integer>> integerMap3 = new HashMap<>();
        integerMap3.put(4, null);
        expected.put(normalController.getAllPlayers().get(0).getGamePieceList().get(0), integerMap0);
        expected.put(normalController.getAllPlayers().get(0).getGamePieceList().get(1), integerMap1);
        expected.put(normalController.getAllPlayers().get(0).getGamePieceList().get(2), integerMap2);
        expected.put(normalController.getAllPlayers().get(0).getGamePieceList().get(3), integerMap3);
        try {
            assertEquals(expected, normalController.getPossibleMovements(normalController.getAllPlayers().get(0)));
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getPossibleMovementsMovementIntoSafetySquare(){
        normalController.setCurrentCard(new StandardCard(CardValue.FIVE));
        normalController.getAllPlayers().get(0).getGamePieceList().get(0).setLocation(0);
        normalController.getAllPlayers().get(0).getGamePieceList().get(0).setLeftBase(true);
        Map<Piece, Map<Integer, Map<Piece, Integer>>> expected = new HashMap<>();
        Map<Integer, Map<Piece, Integer>> integerMap0 = new HashMap<>();
        integerMap0.put(-3, null);
        expected.put(normalController.getAllPlayers().get(0).getGamePieceList().get(0), integerMap0);
        try {
            assertEquals(expected, normalController.getPossibleMovements(normalController.getAllPlayers().get(0)));
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void deckTests(){
        Map<CardValue, Integer> actual = new HashMap<>();
        Map<CardValue, Integer> expected = new HashMap<>();
        expected.put(CardValue.ONE, 4);
        expected.put(CardValue.TWO, 4);
        expected.put(CardValue.THREE, 4);
        expected.put(CardValue.FOUR, 4);
        expected.put(CardValue.FIVE, 4);
        expected.put(CardValue.SEVEN, 4);
        expected.put(CardValue.EIGHT, 4);
        expected.put(CardValue.TEN, 4);
        expected.put(CardValue.ELEVEN, 4);
        expected.put(CardValue.SORRY, 4);
        expected.put(CardValue.TWELVE, 4);
        int cardDrawn = 0;
        while(cardDrawn!=44){
            Card card = normalController.getTopCard();
            actual.put(card.getValue(), actual.getOrDefault(card.getValue(), 0)+1);
            cardDrawn++;
            assertEquals(44-cardDrawn, normalController.getNewCardDeck().getCountOfDeck());
            assertEquals(cardDrawn, normalController.throwawayDeck.getCountOfDeck());
        }
        assertEquals(11, actual.keySet().size());
        expected.keySet().forEach(cardValue -> assertEquals(expected.get(cardValue), actual.get(cardValue)));
        normalController.getTopCard();
        assertEquals(43, normalController.getNewCardDeck().getCountOfDeck());
        assertEquals(1, normalController.getThrowawayDeck().getCountOfDeck());
    }
}