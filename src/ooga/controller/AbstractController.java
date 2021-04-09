package ooga.controller;

import ooga.InputException;
import ooga.cards.Card;
import ooga.cards.Deck;
import ooga.cards.StandardDeck;
import ooga.controller.rules.AbstractRules;
import ooga.fileManager.PropertiesReader;
import ooga.gameboard.Board;
import ooga.gameboard.GameBoard;
import ooga.gamepiece.Piece;

import ooga.player.Player;
import ooga.player.PlayerState;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.*;
import java.lang.reflect.InvocationTargetException;

public abstract class AbstractController implements Controller {
    private static final String PLAYER_PATH = "ooga.player.";
    private final ResourceBundle inputOutput = ResourceBundle.getBundle("InputOutputResources");
    Map<Player, Integer> allScores;
    List<Player> allPlayers;
    List<Integer> slides;
    Map<Integer, List<Integer>> homeLocations;
    Map<Integer, List<Integer>> baseLocations;
    List<String> playerColor;
    Deck newCardDeck;
    Deck throwawayDeck;
    Card currentCard;
    Board gameBoard;
    int sideLength;
    PropertiesReader propertiesReader;
    String propertiesFile;
    int homeLength;
    int slideLength;
    int playerSize;
    AbstractRules rules;
    Map<Piece, Map<Integer, Map<Piece, Integer>>> myActions;

    public AbstractController(String propertiesFile) throws InputException{
        newCardDeck = new StandardDeck();
        newCardDeck.fillDeck();
        newCardDeck.shuffleDeck();
        throwawayDeck = new StandardDeck();
        playerColor = new ArrayList<>();
        baseLocations = new HashMap<>();
        homeLocations = new HashMap<>();
        allScores = new HashMap<>();
        slides = new ArrayList<>();
        this.propertiesFile = propertiesFile;
        propertiesReader = new PropertiesReader(propertiesFile);
        sideLength = Integer.parseInt(propertiesReader.propertyFileReader("SideLength", this.propertiesFile));
        homeLength = Integer.parseInt(propertiesReader.propertyFileReader("HomeLength", this.propertiesFile));
        playerSize = Integer.parseInt(propertiesReader.propertyFileReader("Players", this.propertiesFile));
        createPlayers();
        setUpSlide();
    }

    private void setUpSlide(){
        int[] tempSlides = convertStringToIntLocation(propertiesReader.propertyFileReader(inputOutput.getString("SlideLocation"), this.propertiesFile));
        for(int temp : tempSlides){
            slides.add(temp);
        }
        slideLength = Integer.parseInt(propertiesReader.propertyFileReader(inputOutput.getString("SlideLength"), propertiesFile));
    }

    int[] convertStringToIntLocation(String stringLocations){
        String[] strings = stringLocations.split(inputOutput.getString("comma"));
        int[] ret = new int[strings.length];
        for(int i = 0; i<strings.length; i++){
            ret[i] = Integer.parseInt(strings[i]);
        }
        return ret;
    }

    private void createColor(int idx) throws InputException{
        String tempColor = propertiesReader.propertyFileReader(inputOutput.getString("Player") + idx + inputOutput.getString("Color"), propertiesFile);
        playerColor.add(tempColor);
    }

    @Override
    public List<Integer> getSlideLocation() {
        return slides;
    }

    @Override
    public int getSlideLength() {
        return slideLength;
    }

    @Override
    public int getHomeLength() {
        return homeLength;
    }

    @Override
    public void setUpBoard() {
        gameBoard = new GameBoard(allPlayers, sideLength);
        gameBoard.setLocationsForPlayers();
    }

    @Override
    public void updateBoard() {
        gameBoard.setLocationsForPlayers();
    }

    @Override
    public void createPlayers() throws InputException{
        allPlayers = new ArrayList<>();
        for(int i = 1; i <= playerSize;i++){
            int tempHome = Integer.parseInt(propertiesReader.propertyFileReader(inputOutput.getString("Player") + i + inputOutput.getString("Home"), propertiesFile));
            int tempBase = Integer.parseInt(propertiesReader.propertyFileReader(inputOutput.getString("Player") + i + inputOutput.getString("Base"), propertiesFile));
            String stringLocations = propertiesReader.propertyFileReader(inputOutput.getString("Player")+i+ inputOutput.getString("Location"), propertiesFile);
            String baseLocations = propertiesReader.propertyFileReader(inputOutput.getString("Player")+i+ inputOutput.getString("Bool"), propertiesFile);
            Player player = determinePlayer(inputOutput.getString("Player") + i + inputOutput.getString("Type"),tempHome, tempBase, stringLocations, i, baseLocations);
            int sideIndex = (tempHome / (sideLength-1)) + 1;
            createHomeOrBase(sideIndex, i, tempBase, false );
            createHomeOrBase(sideIndex, i, tempHome, true);
            createColor(i);
            allPlayers.add(player);
        }
    }

    private Player determinePlayer(String player, int home, int base, String locations, int idx, String baseLocations) throws InputException{
        try {
            String playerType = propertiesReader.propertyFileReader(player, propertiesFile);
            Class<?> playerClass = Class.forName(PLAYER_PATH + playerType + inputOutput.getString("Player"));
            Constructor<?> playerConstructor = playerClass.getConstructor(
                    int.class, int.class, int[].class, int.class, PlayerState.class, int[].class);
            Player tempPlayer = (Player) playerConstructor.newInstance
                    (home, base, convertStringToIntLocation(locations), idx, PlayerState.valueOf(propertiesReader.propertyFileReader(player, propertiesFile)), convertStringToIntLocation(baseLocations));
            tempPlayer.getGamePieceList().forEach(piece -> piece.setInHome(piece.getLocation()==-homeLength));
            return tempPlayer;
        } catch (Exception e) {
            throw new InputException(inputOutput.getString("WrongScoring"));
        }
    }

    @Override
    public List<String> getColor() {
        return playerColor;
    }

    @Override
    public void createHomeOrBase(int idx, int playerIdx, int location, boolean home) {
        int row = 0, col = 0;
        switch (idx) {
            case 1 -> {
                col = location;
                if (home) {
                    row = 0;
                } else {
                    row = 1;
                }
            }
            case 2 -> {
                row = location - sideLength + 1;
                if (home) {
                    col = sideLength - 1;
                } else {
                    col = sideLength - 2;
                }
            }
            case 3 -> {
                col = (sideLength - 1) - (location - ((idx - 1) * sideLength - (idx - 1)));
                if (home) {
                    row = sideLength - 1;
                } else {
                    row = sideLength - 2;
                }
            }
            case 4 -> {
                row = (sideLength - 1) - (location - ((idx - 1) * sideLength - (idx - 1)));
                if (home) {
                    col = 0;
                } else {
                    col = 1;
                }
            }
        }
        placeCoordinates(playerIdx, home, row, col);
    }

    private void placeCoordinates(int playerIdx, boolean home, int row, int col) {
        List<Integer> coordinates = new ArrayList<>();
        coordinates.add(row);
        coordinates.add(col);
        if(home){ homeLocations.put(playerIdx, coordinates); }
        else{ baseLocations.put(playerIdx, coordinates); }
    }

    @Override
    public Map<Integer, List<Integer>> getHomeLocation() { return homeLocations; }

    @Override
    public Map<Integer, List<Integer>> getBaseLocation() {
        return baseLocations;
    }

    @Override
    public Player determinePlayerTurn() {
        Player nextPlayer = allPlayers.remove(0);
        allPlayers.add(nextPlayer);
        return nextPlayer;
    }

    @Override
    public List<Piece> handlePlayerTurn(Player player, Map<Piece, Integer> movementPieces) {
        movementPieces.forEach((piece, integer) -> {
            piece.setLocation(integer);
            if(piece.getLocation()>=0){
                piece.setLeftBase(true);
            }
        });
        List<Piece> ret = new ArrayList<>(movementPieces.keySet());
        List<Player> otherPlayers = determineOtherPlayers(player);
        ret.addAll(rules.checkSharedSpace(player, otherPlayers));
        for(Integer slide: slides){
            movementPieces.keySet().forEach(piece -> {
                if(piece.getLocation()==slide){
                    ret.addAll(rules.checkSlideCollision(slide, slideLength, allPlayers));
                    piece.setLocation(slide+slideLength-1);
                }
            });

        }

        return ret;
    }

    @Override
    public Map<Piece, Map<Integer, Map<Piece, Integer>>> getPossibleMovements(Player currentPlayer)
        throws InvocationTargetException, IllegalAccessException {
        List<Player> otherPlayers = determineOtherPlayers(currentPlayer);
        myActions = rules.getPossibleActions(currentCard, currentPlayer, otherPlayers);
        return myActions;
    }

    @Override
    public void resetDeck() {
        Deck temp = newCardDeck;
        newCardDeck = throwawayDeck;
        throwawayDeck = temp;
        newCardDeck.shuffleDeck();
    }

    @Override
    public Card getTopCard() {
        if(newCardDeck.getCountOfDeck()<=0){
            resetDeck();
        }
        currentCard = newCardDeck.removeCard();
        throwawayDeck.addCard(currentCard);
        return currentCard;
    }

    public Deck getNewCardDeck(){ return this.newCardDeck; }

    public Deck getThrowawayDeck(){ return this.throwawayDeck; }

    public List<Player> getAllPlayers(){ return this.allPlayers; }

    public int getSideLength(){ return this.sideLength; }

    public void updateScores(){
        for(Player player : allPlayers){
            scoreOfPlayer(player);
        }
    }

    public Card getCurrentCard() { return currentCard; }

    public void setCurrentCard(Card currentCard) {
        this.currentCard = currentCard;
    }

    public int getPlayerScore(Player player){return allScores.get(player);}

    public String getPropertiesFile() { return this.propertiesFile; }

    List<Player> determineOtherPlayers(Player currentPlayer){
        List<Player> otherPlayers = new ArrayList<>(allPlayers);
        otherPlayers.remove(currentPlayer);
        return otherPlayers;
    }

    public void savePlayerLocationAndBaseBoolean(String file){
        for(int idx = 1; idx <= allPlayers.size(); idx++){
            propertiesReader.changeMapValueOnUserInput(inputOutput.getString("Player") + idx + inputOutput.getString("Location"),
                    gameBoard.getPieceLocation(idx));
            propertiesReader.changeMapValueOnUserInput(inputOutput.getString("Player") + idx + inputOutput.getString("Bool"),
                    gameBoard.getBaseBooleanLocation(idx));
        }
        propertiesReader.writePropertiesFile(new File(file));
    }
}
