/**
 * The Card interface is used to create Card objects that will be used for the Game & a Card's methods
 */
public interface Card {
    /**
     * Method responsible for getting a Card's value
     * @return int value of a drawn card.  If the drawn card is a "Sorry card", its value is 0.
     */
    int getValue();

}