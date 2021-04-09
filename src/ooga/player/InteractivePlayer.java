package ooga.player;

import ooga.gamepiece.Piece;
import ooga.view.PlayerView;
import java.util.Map;

/**
 * @author Hayden Lau
 *
 * This class is an implementation of the Abstract Player and the player API. It determines its moves
 * by the user's input.
 *
 */
public class InteractivePlayer extends AbstractPlayer {

    public InteractivePlayer(int homeLocation, int baseLocation, int[] pieceLocations, int playerNumber, PlayerState playerState, int[] baseLocations){
        super(homeLocation,baseLocation,pieceLocations,playerNumber, playerState, baseLocations);
    }

    /**
     * calls the playerview method that establishes the necessary handling of the clicks to interpret the interactive player's movement
     *
     * @param movablePieces A complex map of map of maps that represents a piece and the possible moves and then the possible
     * options to move based on the card that was drawn
     * @param marginSquares the number of squares around the perimeter of the board; is based on a user input value and read from the properties file
     * @param homeLength changes the number of safety squares until the home; is based on a user input value and read from the properties file
     * @return a map whose key is a piece and the value is an integer representing the moves
     */
    @Override
    public Map<Piece, Integer> selectGamePiece(Map<Piece, Map<Integer, Map<Piece, Integer>>> movablePieces, int marginSquares, int homeLength) {
        PlayerView.getClickedFirstPiece();
        return null;
    }

}
