package ooga.player;

import java.util.ArrayList;
import java.util.List;

import ooga.gamepiece.GamePiece;
import ooga.gamepiece.Piece;

/**
 * The AbstractPlayer class increases the flexibility of the program, allowing for easy extension, but closing the program to
 * modification (Open Closed Principle).  Implementing the AbstractPlayer class allows the addition of new player types to be as simple
 * as possible, only requiring the algorithm of the specific player type.
 */
public abstract class AbstractPlayer implements Player {
  private final List<Piece> listOfGamePieces;
  private final int homeLocation;
  private final int baseLocation;
  private final int playerNumber;
  private final PlayerState myPlayerState;
  private Piece selectedFirstPiece;
  private Integer selectedInteger;

  /**
   * Abstract player constructor
   *
   * @param homeLocation int value representing the home location of a player
   * @param baseLocation int value representing the base location of a player
   * @param pieceLocations an int array that represents the piece locations that the pieces of a player occupy
   * @param playerNumber int value that represents the number of a player
   * @param playerState the type of player that the specific player is (currently can be interactive, hard AI, or easy AI)
   * @param baseLocations the base locations of a piece based on the user input data
   */
  public AbstractPlayer(int homeLocation, int baseLocation, int[] pieceLocations, int playerNumber, PlayerState playerState, int[] baseLocations){
    listOfGamePieces = new ArrayList<>();
    makeListOfGamePieces(pieceLocations, baseLocations);
    this.homeLocation = homeLocation;
    this.baseLocation = baseLocation;
    this.playerNumber = playerNumber;
    this.myPlayerState = playerState;
  }

  // Based on the array containing the piece location and the base locations, moves each of the pieces to the base location that it must start in according to the user input
  private void makeListOfGamePieces(int[] pieceLocations, int[] baseLocations) {
    int i = 0;
    for (int pieceLocation : pieceLocations) {
      Piece temp = new GamePiece(pieceLocation, baseLocations[i]);
      listOfGamePieces.add(temp);
      i++;
    }
  }


  /**
   * This is an override method that will toggle the boolean to true (player has won) if all the pieces are in the home
   * @return boolean value true, if a player has won and false otherwise
   */
  @Override
  public boolean hasWon() {
    for (Piece piece: listOfGamePieces) {
      if(!piece.inHome()){
        return false;
      }
    }
    return true;
  }

  /**
   * getter method for the pieces of a player
   * @return list of Piece objects
   */
  public List<Piece> getGamePieceList() {
    return listOfGamePieces;
  }

  /**
   * getter method for a player's home location in int form
   * @return int value for the home location of a player
   */
  public int getHomeLocation(){ return homeLocation; }

  /**
   * getter method for a player's base location in int form
   * @return int value for the base location of a player
   */
  public int getBaseLocation(){ return baseLocation; }

  /**
   * getter method for a player number
   * @return int value for the player number
   */
  public int getPlayerNumber(){ return this.playerNumber; }

  /**
   * As implemented right now, can return Hard AI, Easy AI, or Interactive
   * @return an enum value representing the player type
   */
  public PlayerState getPlayerState() { return this.myPlayerState; }

  /**
   *
   * @param piece the piece that the method is being set to
   */
  public void setSelectedPiece(Piece piece) { this.selectedFirstPiece = piece; }

  /**
   * The first piece that the player selects (there are times when more than one piece must be selected, depending on what card is drawn)
   * @return Piece that the method is returning
   */
  public Piece getSelectedPiece(){
    return this.selectedFirstPiece;
  }

  /**
   * How many spots a piece must move based on the card that was drawn from the deck
   * @param integer value representing the movement of the piece
   */
  public void setSelectedMovement(Integer integer){
    this.selectedInteger = integer;
  }

  /**
   * gets the value that was selected
   * @return integer value for the selected movement in single dimension
   */
  public Integer getSelectedMovement(){
    return this.selectedInteger;
  }

  /**
   * handles the piece moving into base/home depending on the situation.  If the distance is negative and it has not left the base, then the distance
   * will be the amount of margin squares in the game.  If the distance is negative and a player is not in base, then the player must be moving into its respective home
   * @param pieces a list of piece objects for a player
   * @param marginSquares the amount of squares around the board that was made - depends on the side length that the user chose
   * @param homeLength also a parameter chosen by the user that represents how many safety squares lead up to the home
   * @return int value representing a piece's distance from the home
   */
  public int totalDistanceFromHomeCalculator(List<Piece> pieces, int marginSquares, int homeLength) {
    int ret = 0;
    for (Piece piece : pieces) {
      int pieceLocation = piece.getLocation();
      int homeLocation = getHomeLocation();
      int distance = homeLocation - pieceLocation;
      if (distance < 0) {
        distance = marginSquares - pieceLocation + homeLocation;
      }
      if (pieceLocation < 0) {
        if (!piece.leftBase()) {
          distance = marginSquares;
        } else {
          distance = pieceLocation;
        }
      }
      distance += homeLength;
      ret += distance;
    }
    return ret;
  }

}
