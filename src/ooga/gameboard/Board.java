package ooga.gameboard;

import ooga.gamepiece.Piece;
import ooga.player.Player;

import java.util.List;
import java.util.Map;

public interface Board {

    List<Player> getAllPlayers();

    void setLocationsForPlayers();

    void fillEmptyGrid();

    Map<Integer, Piece> getBoardLayout();

    void updatePieces(List<Piece> pieces);

    int getPlayerHomeLocation(Player currentPlayer);

    int getPlayerBaseLocation(Player currentPlayer);

    String getBaseBooleanLocation(int playerIdx);

    String getPieceLocation(int playerIdx);
}
