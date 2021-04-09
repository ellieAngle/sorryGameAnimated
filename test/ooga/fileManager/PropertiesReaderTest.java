package ooga.fileManager;

import ooga.InputException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ooga.InputException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PropertiesReaderTest {
    PropertiesReader properties;
    @BeforeEach
    void setup(){
        properties = new PropertiesReader("controllerTest.properties");
    }

    @Test
    void canReadProperty(){
        String playerCount = properties.propertyFileReader("Players", "controllerTest.properties");
        assertEquals(4, Integer.parseInt(playerCount));
    }

    @Test
    void wrongFileName(){
        assertThrows(InputException.class , () ->
                properties.propertyFileReader("SideLength", "nothing"));
    }

    @Test
    void wrongProperty(){
        assertThrows(InputException.class , () ->
                properties.propertyFileReader("", "controllerTest.properties"));
    }

    // FIXME - write tests to catch the errors thrown to increase line coverage

    @Test
    void writePropertiesFile(){
        properties.writePropertiesFile(new File("test2.properties"));
        PropertiesReader newProperties = new PropertiesReader("test2.properties");
        assertEquals("test2.properties",newProperties.getMyPropertyFile());
    }


    @Test
    void changeFileError(){
        List<File> list = new ArrayList<>();
        list.add(new File("new"));
        assertThrows(InputException.class , () ->
                properties.changeToChosenFile(list));
    }

    @Test
    void changeFileSuccess(){
        List<File> list = new ArrayList<>();
        list.add(new File("new.properties"));
        assertEquals("new.properties", properties.changeToChosenFile(list));
    }

    @Test
    void setProperty(){
        properties.setMyPropertyFile("hi");
        assertEquals("hi", properties.getMyPropertyFile());
    }

    @Test
    void notEveryRequiredKey(){
        assertThrows(InputException.class , () ->
                properties = new PropertiesReader("wrongPropertiesKey.properties"));
    }
}