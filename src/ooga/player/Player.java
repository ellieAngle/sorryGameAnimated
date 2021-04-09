package ooga.player;

import java.util.Map;
import ooga.gamepiece.Piece;

import java.util.List;

/**
 * This interface is well designed because it allows for the flexibility of our program when making player objects.  Using
 * this interface allows us to greater achieve abstraction and multiple inheritance.
 *
 * The purpose of this particular interface is to allow for new player types to be easily implemented. This interface is implemented
 * in the AbstractPlayer class, which then is extendable for each player type.
 */
public interface Player {
    /**
     * This method will get the user's selected game-piece from the Controller (assuming it is a valid piece to play)
     * or utilize an AI algorithm to get the desired game-piece for a Computer player
     * @return  Game-Piece to play/update
     */
    Map<Piece, Integer> selectGamePiece(Map<Piece, Map<Integer, Map<Piece, Integer>>> movablePieces, int marginSquares, int homeLength);

    /**
     * This method checks whether or not the current player has won the game
     * @return boolean value that is true if the Player has won the game, and false otherwise
     */
    boolean hasWon();

    /**
     *
     * @return List of Piece objects of the player's pieces
     */
    List<Piece> getGamePieceList();

    /**
     *
     * @return the int value representing the home location of a player
     */
    int getHomeLocation();

    /**
     *
     * @return the int value representing the base location of a player
     */
    int getBaseLocation();

    /**
     *
     * @return int value that corresponds to the player number
     */
    int getPlayerNumber();

    /**
     *
     * @return the state of the player (player type) which for our current implementation
     * is EASYAI, HARDAI, or Interactive
     */
    PlayerState getPlayerState();

    /**
     * A setter method used to set a selected piece once one is chosen
     * @param piece the piece that the method is being set to
     */
    void setSelectedPiece(Piece piece);

    /**
     * getter method that returs a Piece object of the selected piece
     * @return the selected piece of the player
     */
    Piece getSelectedPiece();

    /**
     * Sets the movement of a player's moves.  This is chosen from the drawn card and the possible
     * moves that the player can make with the pieces in its current location
     * @param integer value representing the movement of the piece
     */
    void setSelectedMovement(Integer integer);

    /**
     * A getter method to get the movement that was selected for a player
     * @return an integer value representing the movement amount in the single dimension form
     */
    Integer getSelectedMovement();

    /**
     * This method is utilized by the hard AI player to find the "best" move to win the game fastest
     * It is also used in the implementation in the variation of the game that uses scores to determine each players score
     * based on their relative location to their home
     * @param pieces a list of piece objects for a player
     * @param marginSquares the amount of squares around the board that was made - depends on the side length that the user chose
     * @param homeLength also a parameter chosen by the user that represents how many safety squares lead up to the home
     * @return an int value that represents the distance that the pieces are currently in compared to their home base
     */
    int totalDistanceFromHomeCalculator(List<Piece> pieces, int marginSquares, int homeLength);
}
