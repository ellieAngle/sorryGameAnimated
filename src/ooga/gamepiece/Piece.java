package ooga.gamepiece;

public interface Piece {
    /**
     * This method gets the location of the piece the method is called on.
     * The location of each piece is relative to the distance from it's base.
     * When the piece's location is equal to the maximum location value for the board, the piece has made it to the home location.
     */
    int getLocation();

    /**
     * Method responsible for determining whether or not the piece has made it home or not
     * @return boolean value that returns true if the game piece is in home (the final destination).
     */
    boolean inHome();

    /**
     * Method responsible for determining whether or not the piece has left its based and started active play
     * @return boolean value that is true if a game piece has left the base and is in play, false otherwise
     */
    boolean leftBase();

    /**
     * void method that allows the location of a game piece to be set once a card has been drawn and a player chooses a piece to move.
     * @param newLocation: integer value of the game piece's new location
     */
    void setLocation(int newLocation);

    void setLeftBase(boolean val);

    void setInHome(boolean val);

}

