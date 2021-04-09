package ooga.fileManager;

import ooga.InputException;

import java.io.*;
import java.util.*;
import ooga.view.AlertEnum;
import ooga.view.AlertView;

public class PropertiesReader {
    private Properties properties;
    ResourceBundle inputOutputBundle = ResourceBundle.getBundle("InputOutputResources");
    private Set<String> requiredKeys;
    private Map<String, String> propertyFileValues;
    private String myPropertyFile;

    public PropertiesReader(String file) throws InputException{
        myPropertyFile = file;
        propertyFileValues = new HashMap<>();
        fillMap();
    }

    private void fillMap() throws InputException{
        setRequiredKeys();
        for (String key: requiredKeys) {
            propertyFileValues.put(key, propertyFileReader(key, myPropertyFile));
        }
    }

    private void setRequiredKeys() throws InputException{
         requiredKeys = new HashSet<>(Arrays.asList(inputOutputBundle.getString("Players"), inputOutputBundle.getString("SideLength"),
                inputOutputBundle.getString("BoardColor"), inputOutputBundle.getString("HomeLength"), inputOutputBundle.getString("SlideLength"),
                inputOutputBundle.getString("SlideLocation"), "Scoring"));
         int players = Integer.parseInt(propertyFileReader("Players", myPropertyFile));
         for(int i = 1; i <= players; i++){
             requiredKeys.add("Player" + i + "Location");
             requiredKeys.add("Player" + i + "Home");
             requiredKeys.add("Player" + i + "Base");
             requiredKeys.add("Player" + i + "Color");
             requiredKeys.add("Player" + i + "Type");
             requiredKeys.add("Player" + i + "Bool");
         }
    }
    public String changeToChosenFile(List<File> chosenFile) {
        String currFile = chosenFile.get(0).toString();
        String[] directoryPath = currFile.split("/");
        int index = directoryPath.length - 1;
        if(!directoryPath[index].endsWith(".properties")){
            throw new InputException("Invalid Input");
        }
        return directoryPath[index];
    }
    public void setMyPropertyFile(String propertyFile) {
        myPropertyFile = propertyFile;
    }

    public String getMyPropertyFile() {
        return this.myPropertyFile;
    }

    public String propertyFileReader(String property, String fileName) throws InputException {
        properties = new Properties();
        try{
            FileInputStream input = new FileInputStream("data/" + fileName);
            properties.load(input);
            checkForRequiredKeys();
            if(properties.containsKey(property)){
                input.close();
                return properties.getProperty(property);
            }
        }
        catch(IOException | InputException e){
            throw new InputException(inputOutputBundle.getString("KeyError"));
        }
        throw new InputException(inputOutputBundle.getString("KeyError"));
    }
    private void checkForRequiredKeys() throws InputException {
        for (String key : requiredKeys) {
            if (!properties.containsKey(key)) {
                throw new InputException(inputOutputBundle.getString("KeyError"));
            }
        }
    }
    public void changeMapValueOnUserInput(String key, String value) {
        propertyFileValues.put(key, value);
    }

    public void writePropertiesFile(File file) throws InputException {
        Properties prop = new Properties();
        try (OutputStream outputStream = new FileOutputStream("data/" + file)) {
            for(String tempKey : propertyFileValues.keySet()){
                prop.setProperty(tempKey, propertyFileValues.get(tempKey));
            }
            prop.store(outputStream, null);
        } catch (IOException | InputException e) {
            AlertView fileError = new AlertView(AlertEnum.BackendError);
            fileError.setContentText(fileError.getContentText()+ ": " + e.getMessage());
            throw new InputException(inputOutputBundle.getString("WriteError"));
        }
    }

    public Map<String, String> getPropertyFileValues() {
        return propertyFileValues;
    }
}
