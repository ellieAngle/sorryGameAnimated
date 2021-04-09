package ooga.gameboard;

import ooga.gamepiece.Piece;
import ooga.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameBoard implements Board {
    private final List<Player> gamePlayers;
    private final int sideLength;
    private Map<Integer, Piece> boardLayout;
    private Map<Integer, String> baseBooleanMap;
    private Map<Integer, String> pieceLocationsInPlay;
    private List<Piece> basePieces;

    public GameBoard(List<Player> gamePlayers, int sideLength){
        this.gamePlayers = gamePlayers;
        this.sideLength = sideLength;
        boardLayout = new HashMap<>();
        basePieces = new ArrayList<>();
        baseBooleanMap = new HashMap<>();
        pieceLocationsInPlay = new HashMap<>();
        setLocationsForPlayers();
    }

    @Override
    public List<Player> getAllPlayers() {
        return gamePlayers;
    }

    @Override
    public void setLocationsForPlayers() {
        basePieces = new ArrayList<>();
        for (Player tempPlayer : gamePlayers) {
            List<Piece> tempPiecesOfPlayer = tempPlayer.getGamePieceList();
            for (Piece tempPiece : tempPiecesOfPlayer) {
                if (!tempPiece.leftBase()) {
                    basePieces.add(tempPiece);
                } else {
                    boardLayout.put(tempPiece.getLocation(), tempPiece);
                }
            }
        }
        fillEmptyGrid();
        writeLocations();
    }

    private void writeLocations() {
        for (int playerIdx = 1; playerIdx <= gamePlayers.size(); playerIdx++) {
            Player player = gamePlayers.get(playerIdx - 1);

            List<Piece> pieceList = player.getGamePieceList();
            StringBuilder pieceLocations = new StringBuilder();
            StringBuilder baseBoolean = new StringBuilder();
            for (Piece tempPiece : pieceList) {
                int location = tempPiece.getLocation();
                pieceLocations.append(location).append(",");

                if (basePieces.contains(tempPiece)) {
                    baseBoolean.append("0,");
                } else {
                    baseBoolean.append("1,");
                }
                baseBooleanMap.put(playerIdx, baseBoolean.substring(0, baseBoolean.length() - 1));
                pieceLocationsInPlay
                    .put(playerIdx, pieceLocations.substring(0, pieceLocations.length() - 1));
            }
        }
    }

    @Override
    public void fillEmptyGrid() {
        for(int i = 0; i < gamePlayers.size()*sideLength; i++){
            boardLayout.putIfAbsent(i, null);
        }
    }

    @Override
    public Map<Integer, Piece> getBoardLayout() {return boardLayout;}

    @Override
    public void updatePieces(List<Piece> pieces) { }

    @Override
    public int getPlayerHomeLocation(Player currentPlayer) {
        return gamePlayers.indexOf(currentPlayer);
    }

    @Override
    public int getPlayerBaseLocation(Player currentPlayer) {
        return gamePlayers.indexOf(currentPlayer);
    }

    @Override
    public String getBaseBooleanLocation(int playerIdx) {return baseBooleanMap.get(playerIdx); }

    @Override
    public String getPieceLocation(int playerIdx) {return pieceLocationsInPlay.get(playerIdx); }
}
