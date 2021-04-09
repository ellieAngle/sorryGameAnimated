package ooga.gamepiece;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GamePieceTest {
    int pieceLocation = 0;
    boolean home;
    boolean base;
    boolean move;
    private GamePiece myGamePiece;

    @BeforeEach
    void setUp(){
        myGamePiece = new GamePiece(pieceLocation, 1);
        home = false;
        move = false;
    }

    @Test
    void testGetLocation() {
        assertEquals(0, myGamePiece.getLocation());
    }

    @Test
    void testSetLocation() {
        myGamePiece.setLocation(10);
        assertEquals(10, myGamePiece.getLocation());
    }

    @Test
    void testInHome() {
        assertFalse(myGamePiece.inHome());
    }

    @Test
    void testSetInHome() {
        myGamePiece.setInHome(true);
        assertTrue(myGamePiece.inHome());
    }

    @Test
    void testSetLeftBase() {
        myGamePiece.setLeftBase(true);
        assertTrue(myGamePiece.leftBase());
    }
}