package ooga.fileManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InitialDataConfigurationTest {
    InitialDataConfiguration initialDataConfiguration;

    @BeforeEach
    void setup(){
        initialDataConfiguration = new InitialDataConfiguration();
    }

    @Test
    void calculateHomeAndBase() {
        assertEquals(2, initialDataConfiguration.calculateHomeAndBase(1,15,true));
        assertEquals(4, initialDataConfiguration.calculateHomeAndBase(1,15,false));
        assertEquals(15, initialDataConfiguration.calculateHomeAndBase(5, 20, true));
        assertEquals(17, initialDataConfiguration.calculateHomeAndBase(5, 20, false));
    }

    @Test
    void getDefaultPieceLocation() {
        assertEquals("-1,-1,-1,-1", initialDataConfiguration.getDefaultPieceLocation(4));
    }

    @Test
    void setDefaultBool() {
        assertEquals("0,0,0,0", initialDataConfiguration.setDefaultBool(4));
    }
}