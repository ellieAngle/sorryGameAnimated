import java.util.List;

/**
 * The Player interface will have 4 GamePiece objects and will have methods in place to store the objects in the setGamePieceMap method.
    * In addition, there will be updating of the player object after each turn
 */
public interface Player {
    /**
     * This method will get the user's selected game-piece from the Controller (assuming it is a valid piece to play)
     * or utilize an AI algorithm to get the desired game-piece for a Computer player
     * @return  Game-Piece to play/update
     */
    GamePiece selectGamePiece();

    /**
     * This method checks whether or not the current player has won the game
     * @return boolean value that is true if the Player has won the game, and false otherwise
     */
    boolean hasWon();

    /**
     * This method set up a Map containing the Player's 4 gamePieces and their corresponding locations
        *At the beginning of the game each game, the user's game-piece locations will all be set to that Player's base
     * @param locations
     */
    void setGamePieceMap(List<Integer> locations);

    /**
     * When a user moves a game-piece:
        * this method will be called update the user's map of game pieces with this game-piece's updated location
     * @param selectedPiece: selected Game Piece to update the location of
     * @param newLocation: new location of the selected Game Piece
     */
    void updateGamePieceMap(GamePiece selectedPiece, int newLocation);

    /**
     * This method is responsible for setting a player's relative base/home location on the Board
        * A player's gamepieces will be designated as being on base if it's location value is 0,
        * and as being home if it's location value is equal to the number of spaces on the board it must traverse (size of the board).
        * However, each player has a different starting location on the board, so each player has a relative base/home location to the other players.
     */
    void setPlayerRelativeLocation();

    /**
     * This method is responsible for getting a Player's relative base/home location (described above)
     * @return int value of player's relative location
     */
    int getPlayerRelativeLocation();
}