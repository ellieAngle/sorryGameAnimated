package ooga.view;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import ooga.cards.Deck;
import ooga.controller.AbstractController;
import ooga.controller.StandardGameController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class CardViewTest extends DukeApplicationTest {

    private boolean cardText;
    private Deck myGameDeck;
    private VBox deckView;
    private Deck myDiscardDeck;
    private Button gameDeck;
    private Rectangle discardDeck;
    private ResourceBundle textResource = ResourceBundle.getBundle("textResources");
    private ResourceBundle viewResource = ResourceBundle.getBundle("viewConfiguration");
    private AbstractController myController;
    private GameScreen myGameScreen;
    private boolean allowNewCard;
    private CardView myCardView;

    @BeforeEach
    void setUp(){
        myController = new StandardGameController("test_prop_file.properties");
        myGameScreen = new GameScreen("test_prop_file.properties");
        allowNewCard = true;
        myGameDeck = myController.getNewCardDeck();
        myDiscardDeck = myController.getThrowawayDeck();

        myCardView = new CardView(myController, myGameScreen);
        deckView = new VBox();
        myCardView.createDeckView();

        //deckView = lookup("#deck").query();
    }


    @Test
    void testCreateDeckView() {
        // test property of game deck
    }

    @Test
    void testSetAllowNewCard() {
        assertTrue(myCardView.getAllowNewCard());
        myCardView.setAllowNewCard(false);
        assertFalse(myCardView.getAllowNewCard());
    }

    @Test
    void testGetAllowNewCard(){
        assertEquals(allowNewCard, myCardView.getAllowNewCard());
    }

    @Test
    void testRemoveCard() {
        System.out.print(myCardView.getDeckView().getChildren());
        //assertTrue(myCardView.getDeckView().getChildren().contains(deckView.getChildren().size()));
    }
}