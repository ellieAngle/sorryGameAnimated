/**
 * The board interface represents the Board object upon which the Game is played
 * It consist of the player objects (that consist of their own GamePieces),
 * and methods to determine a player's base/home location & a player's pin locations
 */
public interface Board {
    /**
     * Method responsible for getting a Player's relative home location
     * @param player: Player for whom the home location is gotten
     * @return: int value corresponding to the Player's home location relative to the board.
     */
    int getPlayerHomeLocation(Player player);
    /**
     * Method responsible for getting a Player's relative base location
     * @param player: Player for whom the base location is gotten
     * @return: int value corresponding to the Player's base location relative to the board.
     */
    int getPlayerBaseLocation(Player player);

    /**
     * This method sets the relative home/base locations for each Player in the game.
     */
    void setLocationsForPlayers();
}