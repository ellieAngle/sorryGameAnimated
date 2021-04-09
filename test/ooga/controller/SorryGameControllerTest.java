package ooga.controller;

import ooga.cards.CardBehavior;
import ooga.cards.CardValue;
import ooga.gamepiece.Piece;
import ooga.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SorryGameControllerTest {
    private Controller myController;

    @BeforeEach
    void setup(){
        myController = new SorryGameController("controllerTest.properties");
        for(Player player : myController.getAllPlayers()){
            myController.getTopCard();
            myController.scoreOfPlayer(player);
        }
    }

    @Test
    void determineWinnerNull() {
        assertEquals(null, myController.determineWinner());
    }

    @Test
    void scoreOfPlayerWins() {
        Player player = myController.getAllPlayers().get(0);
        List<Piece> pieces = player.getGamePieceList();
        for(Piece piece : pieces){
            piece.setInHome(true);
        }
        for(Player tempPlayer : myController.getAllPlayers()){
            myController.getTopCard();
            myController.scoreOfPlayer(tempPlayer);
        }
        assertEquals(player, myController.determineWinner());
    }

    @Test
    void scoreWithSorryCard(){
        for(Player tempPlayer : myController.getAllPlayers()){
            for(int i = 0; i < 25; i++){
                if(myController.getTopCard().getValue() == CardValue.ONE){
                    myController.getTopCard();
                    myController.scoreOfPlayer(tempPlayer);
                    int count = 0;
                    while(count != 1){
                        if(myController.getTopCard().getValue() == CardValue.SORRY){
                            myController.getTopCard();
                            myController.scoreOfPlayer(tempPlayer);
                            count +=1;
                        }
                    }
                }
            }
            for(Player playerTwo : myController.getAllPlayers()){
                myController.getTopCard();
                myController.scoreOfPlayer(playerTwo);
            }
            assertEquals(null, myController.determineWinner());
        }
    }

    @Test
    void scoreWithOtherCards(){
        for(Player tempPlayer : myController.getAllPlayers()){
            for(int i = 0; i < 25; i++){
                if(myController.getTopCard().getValue() == CardValue.ONE){
                    myController.getTopCard();
                    myController.scoreOfPlayer(tempPlayer);
                    for(int j = 0; j < 25; j++){
                        myController.getTopCard();
                        tempPlayer.getGamePieceList().get(0).setLeftBase(true);
                        tempPlayer.getGamePieceList().get(1).setInHome(true);
                        myController.scoreOfPlayer(tempPlayer);
                    }
                }
            }
            for(Player playerTwo : myController.getAllPlayers()){
                myController.getTopCard();
                myController.scoreOfPlayer(playerTwo);
            }
            assertEquals(null, myController.determineWinner());
        }
    }
}