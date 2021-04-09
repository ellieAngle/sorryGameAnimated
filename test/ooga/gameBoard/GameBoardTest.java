package ooga.gameBoard;

import ooga.gameboard.GameBoard;
import ooga.gamepiece.Piece;
import ooga.player.EasyAIPlayer;
import ooga.player.Player;
import ooga.player.PlayerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class GameBoardTest {
    private List<Player> players;
    private GameBoard board;

    @BeforeEach
    void setup() {
        players = new ArrayList<>();
        players.add(new EasyAIPlayer(16, 18, new int[]{-1, 20, -1, -1}, 1, PlayerState.EasyAI, new int[]{0,0,0,0}));
        board = new GameBoard(players, 15);
    }

    @Test
    void getAllPlayers() {
        List<Player> actual = board.getAllPlayers();
        assertEquals(players.get(0), actual.get(0));
    }

    @Test
    void setLocationsForPlayers() {
        board.setLocationsForPlayers();
        Map<Integer, Piece> boardLayout = board.getBoardLayout();
        assertTrue(boardLayout.get(0) == null);
        assertTrue(boardLayout.get(45) == null);
    }

    @Test
    void fillEmptyGrid() {
        board.fillEmptyGrid();
        for (int i = 1; i < board.getAllPlayers().size() * 14 - 1; i++)
            assertTrue(board.getBoardLayout().get(i) == null);
    }

    @Test
    void getPlayerHomeLocation() {
        assertEquals(0, board.getPlayerBaseLocation(players.get(0)));
        assertEquals(0, board.getPlayerHomeLocation(players.get(0)));
    }

    @Test
    void homeAndBase(){
        board.setLocationsForPlayers();
    }
}