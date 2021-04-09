package ooga.view;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import ooga.cards.Card;
import ooga.cards.Deck;

import javafx.scene.control.Button;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;
import ooga.controller.Controller;

/**
 * Class that creates and handles methods of CardView object, front-end object representative of the deck and its cards
 */
public class CardView {
    private boolean cardText;
    private Deck myGameDeck;
    private final VBox deckView;
    private Button gameDeck;
    private final ResourceBundle textResource = ResourceBundle.getBundle("textResources");
    private final ResourceBundle viewResource = ResourceBundle.getBundle("viewConfiguration");
    private final Controller myController;
    private final GameScreen myGameScreen;
    private boolean allowNewCard;

    /**
     * Constructor sets up CardView's global variables
     * @param controller the current GameController being utilized during gameplay
     * @param gameScreen the current gameScreen being displayed and updated throughout play
     */
    public CardView (Controller controller, GameScreen gameScreen) {
        myController = controller;
        myGameScreen = gameScreen;
        allowNewCard = true;
        myGameDeck = myController.getNewCardDeck();
        deckView = new VBox();
    }

    /**
     * Creates DeckView, VBox containing the gameDeck button and, when added, card Buttons & text
     * @return Node (VBox) containing DeckView's JavaFX objects
     */
    public Node createDeckView() {
        deckView.getChildren().add(createGameDeck());
        deckView.setId("deck");
        return deckView;
    }

    private HBox createGameDeck() {
        HBox gameDeckVBox = new HBox();
        Text gameDeckText = (new Text (textResource.getString("GameDeck")));
        gameDeck = new Button();
        javafx.scene.paint.Color color = Color.WHITE;
        int colorFormat = Integer.parseInt(viewResource.getString("ColorFormat"));
        String hex = String.format("#%02X%02X%02X",
                (int) (color.getRed() * colorFormat),
                (int) (color.getGreen() * colorFormat),
                (int) (color.getBlue() * colorFormat));
        gameDeck.setStyle("-fx-background-color: " + hex);
        gameDeck.setOnAction(e -> {
            if(allowNewCard) {
                presentCard();
            }
        });
        gameDeckVBox.getChildren().addAll(gameDeckText, gameDeck);
        return gameDeckVBox;
    }

    private void presentCard() { //Card gameCard
        allowNewCard = false;
        int minimumSize = 2;
        if (deckView.getChildren().size() >= minimumSize) { removeCard(); }
        if (myGameDeck.getCountOfDeck() == 0) {
            myController.resetDeck();
            myGameDeck = myController.getNewCardDeck();
        }
        Button myCard = createCard(myController.getTopCard());
        deckView.getChildren().add(myCard);
        myGameScreen.updateBackEnd();

    }

    /**
     * Sets allowNewCard variable to parameter value
     * @param allowNewCard: boolean value to set allowNewCard variable to
     */
    public void setAllowNewCard(boolean allowNewCard) {
        this.allowNewCard = allowNewCard;
    }

    /**
     * Getter method for allowNewCard
     * @return allowNewCard variable's current value
     */
    public boolean getAllowNewCard(){return this.allowNewCard;}

    private Button createCard(Card gameCard){
        Button cardButton = new Button();
        File file = new File("resources/" +
                viewResource.getString(gameCard.getValue().toString()));
        try {
            BufferedImage temp = ImageIO.read(file);
            int imageWidth= Integer.parseInt(viewResource.getString("CardWidth"));
            int imageHeight= Integer.parseInt(viewResource.getString("CardHeight"));
            BufferedImage scaleImage = new BufferedImage(imageWidth, imageHeight, temp.getType());
            Graphics2D g2d = scaleImage.createGraphics();
            g2d.drawImage(temp, 0, 0, imageWidth, imageHeight, null);
            Image image = SwingFXUtils.toFXImage(scaleImage, null);
            cardButton.setGraphic(new ImageView(image));
        } catch (IOException e) {
            cardButton.setText("error");
        }
        cardButton.setOnAction(e->cardText(gameCard));

        cardButton.setId("card");
        return cardButton;

    }

    /**
     * handles the removal of the current Card Button from deckView Node
     */
    public void removeCard() {
        while (deckView.getChildren().size()>=2) {
            deckView.getChildren().remove(deckView.getChildren().size() -1);
            cardText = false;
        }
    }

    private void cardText(Card gameCard){
        if (! cardText) {
            cardText = true;
            Text cardText = new Text(textResource.getString(gameCard.getValue().toString() + "Instructions"));
            cardText.setId("text");
            deckView.getChildren().add(cardText);
        }

    }

    /**
     * Getter method for deckView
     * @return VBox deckView variable
     */
    public VBox getDeckView(){ return this.deckView; }

    /**
     * getter method for gameDeck button
     * @return Button gameDeck variable
     */
    public Button getGameDeck() { return this.gameDeck; }

}
