

public class APIExample {
GameBoard gameBoard = new GameBoard();
Deck newCardDeck = new ExampleDeck();
Deck throwawayDeck = new ExampleDeck();

  /**
   * reshuffleDeck is a method that would handle when a player's turn exhausts the deck. It would
   * check to see if the Deck is empty and then return all the Cards in the throwawayDeck to the
   * newCardDeck and reshuffles the deck
   */
  public void reshuffleDeck(){
  if(newCardDeck.getCountOfDeck()<=0){
    newCardDeck = throwawayDeck;
    throwawayDeck = new ExampleDeck();
    newCardDeck.shuffleDeck();
  }
}

}

  /**
   * pieceKnocked is a method that will handle the actions of the pieces when one piece gets to "knock"
   * another piece from its location.  This requires the change of location for both pieces.  The "knocking"
   * piece must have its location be the spot that it just landed on, and the "knocked" piece
   * is sent back to the base so the location must be that base and the boolean that it left the base
   * must be reset to false
   */
  public void pieceKnocked(){
    ExamplePlayer.selectGamePiece().setLocation(newLocation);
    int homeLoc = ExamplePlayer.getPlayerHomeLocation();
    ExamplePlayer.hitGamePiece().setLocation(homeLoc);
    ExamplePlayer.hitGamePiece().leftBase() == false;
  }

public class ExampleGamePiece implements GamePiece{
  int myLocation;
  String myName;

  public ExampleGamePiece(String name) {
    myName = name;
  }

  int getLocation(){
    return location;
  }
  boolean inHome(){
    return false;
  }
  boolean leftBase(){
    return true;
  }
  void setLocation(int newLocation){
    location = newLocation;
  }
}

public class ExamplePlayer implements Player{
  List<GamePiece> gamePieces;
  GamePiece selectGamePiece() {
    return gamePieces.get(0);
  }

  boolean hasWon() {
    return false;
  }
  void setGamePieceMap(List<Integer> locations){

  }
  void updateGamePieceMap(GamePiece selectedPiece, int newLocation){
    gamePiece.get(selectedPiece).setLocation(newLocation);
  }
  void setPlayerRelativeLocation(){}
  int getPlayerRelativeLocation(){
    return 0;
  }

}

public class ExamplePlayer2 implements Player{
  List<GamePiece> gamePieces;
  Map<ExampleGamePiece, Integer> gamePieceLocationMap;
  GamePiece hitGamePiece() {
    return gamePieces.get(0);
  }
  boolean hasWon() {
    return false;
  }
  void setGamePieceMap(List<Integer> locations){
    int name = 0;
    for (int i : locations) {
      gamePieceLocationMap.put(new ExampleGamePiece(name + ""), 0);
      name++
    }
  }
  void updateGamePieceMap(GamePiece selectedPiece, int newLocation){
    gamePiece.get(selectedPiece).setLocation(newLocation);
  }
  void setPlayerRelativeLocation(){}
  int getPlayerRelativeLocation(){
    return 0;
  }
}

public class ExampleDeck implements Deck{
  List<Card> list;

  void addCard(Card card){
    list.add(card);
  }
  Card removeCard(){
    return list.remove(0);
  }

  void shuffleDeck(){}

  int getCountOfDeck(){
    return list.size();
  }
}


public class ExampleCard implements Card {
  private int value;
  public Card(int value){
    value = value;
  }
  int getValue() {
    return value;
  }
}

public class GameBoard implements Board {
  int getPlayerHomeLocation(Player player) {
    return 0;
  }

  int getPlayerBaseLocation(Player player){
    return 0;
  }

  void setLocationsForPlayers(){

  }

/**
 1. Feature: Player with all pins at home pulls a card with the value of 1 or 2 off of the Deck
 * The player selects a Game Piece off of the board, this selected Game Piece is updated in _Player_'s *selectedGamePiece()*
 * The location of this GamePiece (now just outside of the player's base) is updated through *Controller's updateSelectedPiece(Card card, GamePiece selectedPiece)*
 */
public class ExampleController1 implements Controller {
  ExamplePlayer2.setGamePieceMap()
  Board setUpBoard();
  Player determinePlayerTurn();
  void updateSelectedPiece(Card card, GamePiece selectedPiece) {
    selectedPiece.setLocation(selectedPiece.getLocation += card.getValue());
  }
  void resetDeck();
  Card getTopCard();
  Player determineWinner();

  ExampleCard exampleCard = new ExampleCard(1);
  exampleCard.getValue();
  ExamplePlayer2.updateSelectedPiece(exampleCard, ExamplePlayer2.gamePieceLocationMap.get("1"));

}
}
}