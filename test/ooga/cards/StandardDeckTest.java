package ooga.cards;
import ooga.cards.CardValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class StandardDeckTest {
    StandardDeck myDeck;
    //StandardDeck discardDeck;

    @BeforeEach
    void setUp() {
        myDeck = new StandardDeck();
        //discardDeck = new StandardDeck();
    }
    @Test
    void fillDeckTest() {
        myDeck.fillDeck();
        Map<CardValue, Integer> testMap = new HashMap<>();
        for (Card card : myDeck.cards) {
            testMap.putIfAbsent(card.getValue(), 0);
            testMap.put(card.getValue(), testMap.get(card.getValue()) + 1);
        }
        for (Integer i : testMap.values()) {
            assertTrue(i.equals(4));
        }
    }

    @Test
    void addCard() {
        myDeck.addCard(new StandardCard(CardValue.ONE));
        assertEquals(myDeck.cards.size(), 1);
        myDeck.addCard(new StandardCard(CardValue.SORRY));
        assertEquals(myDeck.cards.size(), 2);
    }

    @Test
    void removeCard() {
        myDeck.fillDeck();
        myDeck.removeCard();
        assertEquals(myDeck.cards.size(), 43);
    }

    @Test
    void getCountOfDeck() {
        myDeck.fillDeck();
        assertTrue(myDeck.getCountOfDeck() == 44);
        StandardDeck discardDeck = new StandardDeck();
        assertTrue(discardDeck.getCountOfDeck() == 0);
    }

    @Test
    void shuffleDeck() {
        myDeck.fillDeck();
        myDeck.setRandomSeed(2);
        myDeck.shuffleDeck();
        Card card = myDeck.removeCard();
        assertEquals(card.getValue(), CardValue.FOUR);
    }
}