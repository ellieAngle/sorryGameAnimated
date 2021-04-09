# Team Sorry
### Genre Game
Board Game - Sorry©

### Game Features
* [Standard gameplay](https://winning-moves.com/images/sorryrulesbooklet.pdf)
    * Features
        * Deck of rule and movement cards
        * 2-4 players
        * 4 game pieces per player
        * game board
* Variations of gameplay
    * variations of how to win
        * maximization of points
    * variations of rules
        * elimination of player pieces
        * different card rules
    * variation of game board
        * shape
        * layout
        * quantity of players
        * different location mechanisms

### Member Roles
    1. Rules/Controller: 
        - primary responsibility - Ellie
        - secondary responsibility - Hayden
    2. Deck/Cards
        - primary responsibility - Hayden
        - secondary responsibility - Ellie
    3. Player/Pins
        - primary responsibility - Maddie
        - secondary responsibility - Nick
    4. Map/Front-End
        - primary responsibility - Nick
        - secondary responsibility - Everyone

### Sprints 

#### Sprint 1:
- Front end: Basic implementation, no customization, set number of players, two dimensional design & basic animation
- Back end: game-play functionality delimited by existing front end, all rules implemented, functional control class

#### Sprint 2:
- Front end: alert screens, create multiple maps to choose from, handle having different number of players
- Back end: adjust the backend to account for new front end features (especially new Maps & new player types) and alternative rules

#### Complete:
- Add in customization as seen fit, complete test and all features 
    - Have start screens for choosing language, # of players, map, and dimensionality of the game (potentially 2D vs 3D, custom pins/appearance)
    
### UI Wireframe Interface

[MockPlus](https://app.mockplus.com/run/rp/JB6zXNQ7kc)

### Design Plan

#### Flexible and Open vs Fixed and Closed

* Interfaces are open and flexible to extend via fixed and closed specific class implementation for each variation of the overarching Sorry© game.
    * StandardCard and StandardDeck are fixed classes that implement the flexible Card and Deck interfaces, respectively.
    
#### Modules/Packages

* cards Package
    * handles individual cards and deck behavior
    * stores card and deck specific variables such as card values and behaviors
* controller Package
    * handles the rules of the game and handles the interaction between multiple game objects
* ooga.gamepiece Package
    * handles the location and control of individual game pieces
* player Package
    * handles player's interactions (AI or Interactive) and stores the necessary instance variables such as game pieces
* board Package
    * handles setting up the board from a configuration properties file
    * acts overarching connection point of the rest of the backend and frontend
* visualization package
    * handles the view of the game and display to a user
    * allows for interactions of a user with the game

#### API Details
* Card Interface
    * allows for the controller to acquire a card's specific value and behavior
    * allows for implementing classes to dictate the specific card functionality
    * supports abstraction for group members to store Card objects regardless of variation of the game
        * used in Deck class to store all the cards relative to this game's deck
* Player Interface
    * allows for the controller to ask a player to select game pieces
    * allows for the controller to access a player's game pieces
    * supports abstraction for interactive player vs AI player

#### Use Cases

The method pieceKnocked is a method that will handle the actions of the pieces when one piece gets to "knock" another piece from its location.  This requires the change of location for both pieces.  The "knocking" piece must have its location be the spot that it just landed on, and the "knocked" piece is sent back to the base so the location must be that base and the boolean that it left the base must be reset to false.
 ```java
  public void pieceKnocked(){
    ExamplePlayer.selectGamePiece().setLocation(newLocation);
    int homeLoc = ExamplePlayer.getPlayerHomeLocation();
    ExamplePlayer.hitGamePiece().setLocation(homeLoc);
    ExamplePlayer.hitGamePiece().leftBase() == false;
  }
```

The method reshuffleDeck is a method that would handle when a player's turn exhausts the deck. It would check to see if the Deck is empty and then return all the Cards in the throwawayDeck to the newCardDeck and reshuffles the deck.
```java
  public void reshuffleDeck(){
  if(newCardDeck.getCountOfDeck()<=0){
    newCardDeck = throwawayDeck;
    throwawayDeck = new ExampleDeck();
    newCardDeck.shuffleDeck();
  }
```

#### Alternative Design
##### Design decision for how to create the map for our board game
It took an hour-two hour discussion for the best way to create the mapping of Sorry. The alternatives we thought about were:

    1. Have a 2D grid based game with each square resemble a different cell configuration or game piece.
    2. Have only the outer boundary be associated with an ID for the player pieces and objects.

I ended up deciding with my team that I should implement Idea 2. This will make it a lot easier with the saving of the different player objects in order to load the same configuration.


