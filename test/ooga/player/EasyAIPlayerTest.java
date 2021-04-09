package ooga.player;

import java.util.HashMap;
import java.util.Map;
import ooga.gamepiece.Piece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EasyAIPlayerTest {
    
    EasyAIPlayer player;
    
    @BeforeEach
    void setUp() {
        player = new EasyAIPlayer(2, 4, new int[]{-1, -1, -1, -1}, 1, PlayerState.EasyAI, new int[]{0,0,0,0});
        player.setRandomSeed(1);
    }

    @Test
    void selectGamePieceOnlyInitialPieceWithMultipleOptions() {
        Map<Piece, Map<Integer, Map<Piece, Integer>>> movablePieces = new HashMap<>();
        Map<Integer, Map<Piece, Integer>> option1 = new HashMap<>();
        option1.put(4, null);
        option1.put(15, null);
        movablePieces.put(player.getGamePieceList().get(0), option1);
        Map<Piece, Integer> expected = new HashMap<>();
        expected.put(player.getGamePieceList().get(0), 4);
        assertEquals(expected, player.selectGamePiece(movablePieces, 56, 4));
    }

    @Test
    void selectGamePieceInitialAndSecondPieceMovable() {
        Map<Piece, Map<Integer, Map<Piece, Integer>>> movablePieces = new HashMap<>();
        Map<Integer, Map<Piece, Integer>> option1 = new HashMap<>();
        Map<Piece, Integer> option1SecondPiece = new HashMap<>();
        Map<Piece, Integer> option2SecondPiece = new HashMap<>();
        Map<Piece, Integer> option3SecondPiece = new HashMap<>();
        Map<Piece, Integer> option4SecondPiece = new HashMap<>();
        Map<Piece, Integer> option5SecondPiece = new HashMap<>();
        Map<Piece, Integer> option6SecondPiece = new HashMap<>();
        Map<Piece, Integer> option7SecondPiece = new HashMap<>();
        option1SecondPiece.put(player.getGamePieceList().get(1), 20);
        option2SecondPiece.put(player.getGamePieceList().get(1), 19);
        option3SecondPiece.put(player.getGamePieceList().get(1), 18);
        option4SecondPiece.put(player.getGamePieceList().get(1), 17);
        option5SecondPiece.put(player.getGamePieceList().get(1), 16);
        option6SecondPiece.put(player.getGamePieceList().get(1), 15);
        option7SecondPiece.put(player.getGamePieceList().get(1), 14);
        option1.put(4, option1SecondPiece);
        option1.put(5, option2SecondPiece);
        option1.put(6, option3SecondPiece);
        option1.put(7, option4SecondPiece);
        option1.put(8, option5SecondPiece);
        option1.put(9, option6SecondPiece);
        option1.put(10, option7SecondPiece);
        movablePieces.put(player.getGamePieceList().get(0), option1);
        Map<Piece, Integer> expected = new HashMap<>();
        expected.put(player.getGamePieceList().get(0), 8);
        expected.put(player.getGamePieceList().get(1), 16);
        assertEquals(expected, player.selectGamePiece(movablePieces, 56, 4));
    }
}