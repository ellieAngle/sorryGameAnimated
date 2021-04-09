package ooga.controller;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import ooga.cards.Card;
import ooga.cards.Deck;
import ooga.gamepiece.Piece;
import ooga.player.Player;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The Controller interface (a CRC-card model for the Controller class) will be made of up the objects of our other Sorry! classes
 * as this is where the updating occurs via/according to the front end user action.
 * The updating will be called for in the controller to receive the updated data
 * This updated data will then cause the front end objects to move and update themselves
 */
public interface Controller {
  /**
   * This method takes care of setting up the game's board according to the Controller's properties file
   * @return Board object set up as according to property file
   */

  List<Integer> getSlideLocation();

  int getSlideLength();

  int getHomeLength();

  void setUpBoard();

  void updateBoard();

  void createPlayers();

  List<String> getColor();

  void createHomeOrBase(int idx, int playerIdx, int location, boolean home);

  Map<Integer, List<Integer>> getHomeLocation();

  Map<Integer, List<Integer>> getBaseLocation();

  /**
   * This method determine's which Player's turn it is to play
   * @return Player whose turn it is to participate in game-play
   */
  Player determinePlayerTurn();


  List<Piece> handlePlayerTurn(Player player, Map<Piece, Integer> movementPieces);

  /**
   * When the deck-to-choose-from is empty (and thus the discard deck is full), or when a new game is started:
   * This method resets the main Deck by shuffling the 44 cards of specific types and placing them in a LinkedList
   */
  void resetDeck();

  /**
   * This method gets the top card off of the Deck of cards (structure being a LinkedList) that will be utilized in game-play
   * and then places this card in the discarded deck
   */
  Card getTopCard();

  /**
   * This method determines
   * a) if there is a winner at the current point in the game
   * b) which Player that winner is.
   * @return Player who has won the game or null otherwise
   */
  Player determineWinner();

  void scoreOfPlayer(Player player);

  Deck getNewCardDeck();

  Deck getThrowawayDeck();

  List<Player> getAllPlayers();

  int getSideLength();

  Map<Piece, Map<Integer, Map<Piece, Integer>>> getPossibleMovements(Player currentPlayer)
      throws InvocationTargetException, IllegalAccessException;
  Card getCurrentCard();

  String getPropertiesFile();

  void updateScores();

  void savePlayerLocationAndBaseBoolean(String file);
}