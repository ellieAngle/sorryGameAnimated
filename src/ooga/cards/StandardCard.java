package ooga.cards;

/**
 * @author Hayden Lau
 *
 * implements the standardCard through the Card interface for the game to utilize; used through the Deck class
 */
public class StandardCard implements Card{
  CardValue myValue;

  public StandardCard(CardValue cardValue){
    myValue = cardValue;
  }

  /**
   * implementation of the interface's method
   *
   * @return CardValue of this card
   */
  @Override
  public CardValue getValue() {
    return myValue;
  }


  /**
   * implementation of the interface's method
   *
   * @return CardBehavior of this card as determined by the CardValue's enum
   */
  @Override
  public CardBehavior getBehavior() {
    return myValue.getCardBehavior();
  }
}


