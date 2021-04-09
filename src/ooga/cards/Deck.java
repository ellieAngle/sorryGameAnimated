package ooga.cards;

/**
 * This interface will be used to create the Decks a user chooses a card from and the "discard pile"/Deck of cards already chosen during Game-Play
 * At the beginning of the game, the deck the user chooses from will contain 44 cards: 4 of each numeric card, numbered 1-12, and 4 "Sorry" cards
 * As the game is played, selected cards will then be placed in the "discard pile"/deck
 */
public interface Deck {

  /**
   * this method fills the deck with its card
   */
  void fillDeck();

  /**
   * This method is utilized to add a card to the  deck.
   * @param card: the card to add to the deck
   */
  void addCard(Card card);

  /**
   * This method will remove a specific card from the drawing deck (i.e. once it is drawn during Game-Play)
   * @return the Card drawn during Game-Play that is to be acted upon; also the Card that gets added to discard deck
   */
  Card removeCard();

  /**
   * This method returns the number of cards that are left in a deck at the time the method is called.
   * When the count of a deck is 0, we know that the deck is now empty.
   * @return int representing the # of cards currently in the specified deck
   */
  int getCountOfDeck();

  /**
   * This method shuffles the deck based on the deck's implementation.
   */
  void shuffleDeck();
}
