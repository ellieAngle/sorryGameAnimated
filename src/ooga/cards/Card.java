package ooga.cards;

/**
 * The Card interface is used to create Card objects that will be used for the Game & a Card's methods
 */
public interface Card {

  /**
   * Method responsible for getting a Card's value
   * @return CardValue of the card.
   */
  CardValue getValue();


  /**
   * Method responsible for getting the CardBehavior of a card corresponding to to the rule the card follows
   * @return CardBehavior corresponding to the card.
   */
  CardBehavior getBehavior();
}
