package ooga.cards;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @author Hayden Lau
 *
 * Standard Deck implementation of the interface
 * relies on the Card interface
 *
 */
public class StandardDeck implements Deck {
  List<Card> cards;
  Random shuffleRandomizer;

  public StandardDeck(){
    cards = new LinkedList<>();
    shuffleRandomizer = new Random();
  }


  /**
   * implementation of the interface method; fills the deck based on standard gameplay rules
   */
  @Override
  public void fillDeck(){
    for(CardValue value : CardValue.values()){
      int tempCount = 0;
      while(tempCount<4){
        cards.add(new StandardCard(value));
        tempCount++;
      }
    }
  }


  /**
   * implementation of the interface method; adds the card to the top of the deck
   *
   * @param card: the card to add to the deck
   */
  @Override
  public void addCard(Card card) {
    cards.add(0, card);
  }


  /**
   * implementation of the interface method; draws the top card off of the deck
   *
   * @return the top card of the deck
   */
  @Override
  public Card removeCard() {
    return cards.remove(0);
  }


  /**
   * implements the interface method; returns the number of cards in the deck
   *
   * @return the size of the deck
   */
  @Override
  public int getCountOfDeck() {
    return cards.size();
  }


  /**
   * shuffles the deck based on a random generator
   */
  @Override
  public void shuffleDeck() {
    List<Card> temp = new LinkedList<>();
    while(!cards.isEmpty()){
      temp.add(cards.remove(shuffleRandomizer.nextInt(cards.size())));
    }
    cards = temp;
  }


  /**
   * allows the deck's randomization to be controlled for testing purposes
   *
   * @param seed random seed to assign to the deck
   */
  public void setRandomSeed(int seed){
    shuffleRandomizer.setSeed(seed);
  }

}
