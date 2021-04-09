package ooga.view;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import ooga.cards.CardValue;
import ooga.cards.StandardCard;
import ooga.controller.AbstractController;
import ooga.gamepiece.Piece;
import ooga.player.Player;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

class PlayerViewTest extends DukeApplicationTest {

  private GameScreen gameScreen;
  private Button gameDeck;

  @Override
  public void start(Stage stage) {
    StartScreen startScreen = new StartScreen();
    startScreen.start(stage);
    gameScreen = new GameScreen("playerViewTest.properties");
    stage.hide();
    stage.setScene(gameScreen.makeScene());
    stage.show();
    gameDeck = gameScreen.getCardView().getGameDeck();
  }

  @Test
  void handleClickOnFirstPieceClickedOnPiece() {
    gameScreen.getController().getNewCardDeck().addCard(new StandardCard(CardValue.ONE));
    clickOn(gameDeck);
    AlertView alertView = gameScreen.getAlertView();
    Button okButton = ( Button ) alertView.getDialogPane().lookupButton(ButtonType.OK);
    PieceView firstPieceView = gameScreen.getPieceViews().get(14);
    clickOn(okButton);
    clickOn(gameScreen.getScene().getRoot(), (int) firstPieceView.getMyShape().getCenterX(),(int) firstPieceView.getMyShape().getCenterY());
    Player player = gameScreen.getPlayer();
    assertEquals(player.getGamePieceList().get(0), player.getSelectedPiece());
  }

  @Test
  void handleClickOnFirstPieceClickedOnNonPiece() {
    gameScreen.getController().getNewCardDeck().addCard(new StandardCard(CardValue.ONE));
    clickOn(gameDeck);
    AlertView alertView = gameScreen.getAlertView();
    Button okButton = ( Button ) alertView.getDialogPane().lookupButton(ButtonType.OK);
    PieceView firstPieceView = gameScreen.getPieceViews().get(14);
    clickOn(okButton);
    clickOn(gameScreen.getScene().getRoot(), (int) firstPieceView.getMyShape().getCenterX()+100,(int) firstPieceView.getMyShape().getCenterY());
    clickOn(gameScreen.getScene().getRoot(), (int) firstPieceView.getMyShape().getCenterX()+100,(int) firstPieceView.getMyShape().getCenterY());
    Player player = gameScreen.getPlayer();
    alertView = PlayerView.getAlertView();
    assertEquals(AlertType.ERROR, alertView.getAlertType());
    assertNull(player.getSelectedPiece());
  }

  @Test
  void handleClickOnFirstPieceClickedOnAnotherPiece() {
    gameScreen.getController().getNewCardDeck().addCard(new StandardCard(CardValue.ONE));
    clickOn(gameDeck);
    AlertView alertView = gameScreen.getAlertView();
    Button okButton = ( Button ) alertView.getDialogPane().lookupButton(ButtonType.OK);
    PieceView firstPieceView = gameScreen.getPieceViews().get(0);
    clickOn(okButton);
    clickOn(gameScreen.getScene().getRoot(), (int) firstPieceView.getMyShape().getCenterX(),(int) firstPieceView.getMyShape().getCenterY());
    clickOn(gameScreen.getScene().getRoot(), (int) firstPieceView.getMyShape().getCenterX(),(int) firstPieceView.getMyShape().getCenterY());
    Player player = gameScreen.getPlayer();
    alertView = PlayerView.getAlertView();
    assertEquals(AlertType.ERROR, alertView.getAlertType());
    assertNull(player.getSelectedPiece());
  }

  @Test
  void handleClickOnSecondPiece() {
    ((AbstractController) gameScreen.getController()).setCurrentCard(new StandardCard(CardValue.TWO));
    gameScreen.getController().getNewCardDeck().addCard(new StandardCard(CardValue.SEVEN));
    Player player = gameScreen.getPlayer();
    Map<Piece, Integer> setup = new HashMap<>();
    setup.put(player.getGamePieceList().get(0), 7);
    setup.put(player.getGamePieceList().get(1), 14);
    gameScreen.updateFrontEnd(setup);
    sleep(1000);
    clickOn(gameDeck);
    AlertView alertView = gameScreen.getAlertView();
    Button okButton = ( Button ) alertView.getDialogPane().lookupButton(ButtonType.OK);
    PieceView firstPieceView = gameScreen.getPieceViews().get(13);
    clickOn(okButton);
    clickOn(gameScreen.getScene().getRoot(), (int) firstPieceView.getMyShape().getCenterX(),(int) firstPieceView.getMyShape().getCenterY());
    clickOn(gameScreen.getMyBoardGrid().getBoardGrid(), gameScreen.getMyBoardGrid().getCells().get(16));
    alertView = PlayerView.getAlertView();
    okButton = ( Button ) alertView.getDialogPane().lookupButton(ButtonType.OK);
    clickOn(okButton);
    PieceView secondPieceView = gameScreen.getPieceViews().get(12);
    clickOn(gameScreen.getScene().getRoot(), (int) secondPieceView.getMyShape().getCenterX(),(int) secondPieceView.getMyShape().getCenterY());
    sleep(1000);
    assertEquals(796, (int) firstPieceView.getMyShape().getCenterX());
    assertEquals(229, (int) firstPieceView.getMyShape().getCenterY());
    assertEquals(688, (int) secondPieceView.getMyShape().getCenterX());
    assertEquals(25, (int) secondPieceView.getMyShape().getCenterY());
  }

  @Test
  void handleClickOnSecondPieceBadClick() {
    ((AbstractController) gameScreen.getController()).setCurrentCard(new StandardCard(CardValue.TWO));
    gameScreen.getController().getNewCardDeck().addCard(new StandardCard(CardValue.SEVEN));
    Player player = gameScreen.getPlayer();
    Map<Piece, Integer> setup = new HashMap<>();
    setup.put(player.getGamePieceList().get(0), 7);
    setup.put(player.getGamePieceList().get(1), 14);
    gameScreen.updateFrontEnd(setup);
    sleep(1000);
    clickOn(gameDeck);
    AlertView alertView = gameScreen.getAlertView();
    Button okButton = ( Button ) alertView.getDialogPane().lookupButton(ButtonType.OK);
    PieceView firstPieceView = gameScreen.getPieceViews().get(13);
    clickOn(okButton);
    clickOn(gameScreen.getScene().getRoot(), (int) firstPieceView.getMyShape().getCenterX(),(int) firstPieceView.getMyShape().getCenterY());
    clickOn(gameScreen.getMyBoardGrid().getBoardGrid(), gameScreen.getMyBoardGrid().getCells().get(16));
    alertView = PlayerView.getAlertView();
    okButton = ( Button ) alertView.getDialogPane().lookupButton(ButtonType.OK);
    clickOn(okButton);
    PieceView secondPieceView = gameScreen.getPieceViews().get(11);
    clickOn(gameScreen.getScene().getRoot(), (int) secondPieceView.getMyShape().getCenterX(),(int) secondPieceView.getMyShape().getCenterY());
    clickOn(gameScreen.getScene().getRoot(), (int) secondPieceView.getMyShape().getCenterX(),(int) secondPieceView.getMyShape().getCenterY());
    alertView = PlayerView.getAlertView();
    assertEquals(AlertType.ERROR, alertView.getAlertType());
  }

  @Test
  void handleClickOnSquareSecondPiece() {
    ((AbstractController) gameScreen.getController()).setCurrentCard(new StandardCard(CardValue.TWO));
    gameScreen.getController().getNewCardDeck().addCard(new StandardCard(CardValue.SEVEN));
    Player player = gameScreen.getPlayer();
    Map<Piece, Integer> setup = new HashMap<>();
    setup.put(player.getGamePieceList().get(0), 7);
    setup.put(player.getGamePieceList().get(1), 14);
    gameScreen.updateFrontEnd(setup);
    sleep(1000);
    clickOn(gameDeck);
    AlertView alertView = gameScreen.getAlertView();
    Button okButton = ( Button ) alertView.getDialogPane().lookupButton(ButtonType.OK);
    PieceView firstPieceView = gameScreen.getPieceViews().get(13);
    clickOn(okButton);
    clickOn(gameScreen.getScene().getRoot(), (int) firstPieceView.getMyShape().getCenterX(),(int) firstPieceView.getMyShape().getCenterY());
    clickOn(gameScreen.getMyBoardGrid().getBoardGrid(), gameScreen.getMyBoardGrid().getCells().get(16));
    alertView = PlayerView.getAlertView();
    assertEquals(AlertType.INFORMATION, alertView.getAlertType());
  }

  @Test
  void handleClickOnSquareSecondPieceDifferentSides() {
    ((AbstractController) gameScreen.getController()).setCurrentCard(new StandardCard(CardValue.TWO));
    gameScreen.getController().getNewCardDeck().addCard(new StandardCard(CardValue.SEVEN));
    Player player = gameScreen.getPlayer();
    Map<Piece, Integer> setup = new HashMap<>();
    setup.put(player.getGamePieceList().get(0), 7);
    setup.put(player.getGamePieceList().get(1), 30);
    gameScreen.updateFrontEnd(setup);
    sleep(1000);
    clickOn(gameDeck);
    AlertView alertView = gameScreen.getAlertView();
    Button okButton = ( Button ) alertView.getDialogPane().lookupButton(ButtonType.OK);
    PieceView firstPieceView = gameScreen.getPieceViews().get(13);
    clickOn(okButton);
    clickOn(gameScreen.getScene().getRoot(), (int) firstPieceView.getMyShape().getCenterX(),(int) firstPieceView.getMyShape().getCenterY());
    clickOn(gameScreen.getMyBoardGrid().getBoardGrid(), gameScreen.getMyBoardGrid().getCells().get(48));
    alertView = PlayerView.getAlertView();
    assertEquals(AlertType.INFORMATION, alertView.getAlertType());
  }

  @Test
  void handleClickOnSquareBadClick() {
    ((AbstractController) gameScreen.getController()).setCurrentCard(new StandardCard(CardValue.TWO));
    gameScreen.getController().getNewCardDeck().addCard(new StandardCard(CardValue.SEVEN));
    Player player = gameScreen.getPlayer();
    Map<Piece, Integer> setup = new HashMap<>();
    setup.put(player.getGamePieceList().get(0), 7);
    setup.put(player.getGamePieceList().get(1), 14);
    gameScreen.updateFrontEnd(setup);
    sleep(1000);
    clickOn(gameDeck);
    AlertView alertView = gameScreen.getAlertView();
    Button okButton = ( Button ) alertView.getDialogPane().lookupButton(ButtonType.OK);
    PieceView firstPieceView = gameScreen.getPieceViews().get(13);
    clickOn(okButton);
    clickOn(gameScreen.getScene().getRoot(), (int) firstPieceView.getMyShape().getCenterX(),(int) firstPieceView.getMyShape().getCenterY());
    clickOn(gameScreen.getMyBoardGrid().getBoardGrid(), gameScreen.getMyBoardGrid().getCells().get(0));
    alertView = PlayerView.getAlertView();
    assertEquals(AlertType.ERROR, alertView.getAlertType());
  }

  @Test
  void handleClickOnSquareMove() {
    gameScreen.getController().getNewCardDeck().addCard(new StandardCard(CardValue.ONE));
    clickOn(gameDeck);
    AlertView alertView = gameScreen.getAlertView();
    Button okButton = ( Button ) alertView.getDialogPane().lookupButton(ButtonType.OK);
    PieceView firstPieceView = gameScreen.getPieceViews().get(12);
    clickOn(okButton);
    clickOn(gameScreen.getScene().getRoot(), (int) firstPieceView.getMyShape().getCenterX(),(int) firstPieceView.getMyShape().getCenterY());
    clickOn(gameScreen.getMyBoardGrid().getBoardGrid(), gameScreen.getMyBoardGrid().getCells().get(4));
    sleep(1000);
    assertEquals(247, (int) firstPieceView.getMyShape().getCenterX());
    assertEquals(25, (int) firstPieceView.getMyShape().getCenterY());
  }
}