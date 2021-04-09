Sorry Game Design
===================

## Team Members and Primary Roles:
As a team, we each worked as full stack developers, and often spent time group-coding. However, the general assignment and breakdown of tasks is as follows: 
* Hayden Lau:
    * Backend: Rules, Controller, Player
    * Frontend: Alert
* Maddie Cecchini
    * Backend: Piece, Player
    * Frontend: Board, Game Screen, Player View
* Ellie Angle
    * Backend: Rules
    * Frontend: Start/Customization Screen, Piece View, Card View, Game Screen
* Nick DeCapite
    * Backend: Controller *(Variations of Game Types)*, Game Board
    * Configuration: File Management, Board
    * Frontend: Customization Screen
## Design Goals and Flexibility

**The goal of our program was to allow for flexibility in both the configuration and customization of the game, and the adding of new features.**

### Existing Flexibility:
In terms of existing flexibility, the user is given the ability to customize the game through choosing the following features: 
- Board sidelength
- Number of players
    - Desired color and type of each player
- Number of game-pieces
    - Whether or not each game-piece starts in its base, or if it starts out on the margin squares
- Length of each slide
- Number of safety squares 
- Scoring system *(determines GameController)*

Additionally, we created a saving feature that allows for the user to stop the game, save the all aspects of the current configuration (piece locations, which player's turn it is, which card is currently being played), and restart this game at another time by uploading the saved file.

### Flexibility for Future Extension:

#### **New Sorry Variations**
* The Controller and Rules interfaces are easily extendable and make simple the process of adding new variations of Sorry 
* **By creating a new Controller**: a programmer could vary what determines who wins the game, how the game updates, or add options to allow a player to skip her turn
* **By creating a new Rules class**: a programmer can change the rules of specific cards to alter player behavior and interaction 

#### **New Player Variations**
* The Player API is also open for extension to new types of players with different decision-making algorithms
    
    
## High Level Design:
Our program has 6 interfaces, one for each Sorry game object. These interfaces were each implemented in from 1-4 concrete classes, and occasionally in abstract classes as well. They are described below. Additionally, three packages do not contain interfaces, they are also described below. 

1. Card: 
    - Used to create Card objects that will be used for the Game & all of Card's methods
    - Classes: 
        - Enums: CardValue, CardBehavior 
        - Concrete: StandardCard
2. Deck:
    - This interface will be used to create the Decks a user chooses a card from (the Game deck) and the "discard pile"/deck of cards already chosen during Game-Play
         - At the beginning of the game, the deck the user chooses from will contain 44 cards: 4 of each numeric card, numbered 1-12, and 4 "Sorry" cards
         - As the game is played, selected cards will then be placed in the "discard pile"/deck 
         - When the Game Deck is empty, it is re-filled and re-shuffled so gameplay can continue 
    - Classes: 
        - Concrete: StandardDeck
3. Board
    - This interface is used to set up the back-end of the board, and handles the relative home and base locations for each player
        - The back-end version of the board treats the board as if it is one-dimensional, each margin square having the location 0-(sideLength-1)*4
    - Classes:
        - Concrete: GameBoard
4. Piece
    - Handles the location of each gamepiece and whether or not the game piece has left base, or if it is in its home
    - Classes:
        - Concrete: GamePiece
5. Player
    - This interface handles the behavior of all players, and easily allows for new player types to be implemented. 
        - This interface is implemented in the AbstractPlayer class, which then is extendable for each player type.
    - Classes:
        - Concrete: EasyAIPlayer, HardAIPlayer, InteractivePlayer
        - Abstract: AbstractPlayer
        - Enum: PlayerState
6. Controller
    - The Controller interface holds all of the other Sorry!© game objects and handles the updating of the game
    - Updating process:
        - Controller determines which player's turn it is
        - Front end interaction via user clicking on Game-deck button to reveal Card
        - Controller takes this information and utilizes Rule class to determine a player's possible movements and resulting interactions from piece-movement 
            - If current player is an AI player the selection of which piece is moved is handled entirely in back-end
            - If current player is an interactive player, the Controller updates front-end to reflect movable pieces and locations to which selected piece can move to, then waits for the user to select a a movable game piece and it's new location
            - If no movement is possible given the current card and the player's pieces' current locations, then it becomes the next player's turn
        - Once the piece and new movement has been determined, the Controller updates the piece(s) locations in the Backend
        - After updating the back-end, the Controller passes  the updated information to the front-end where the updating of the GUI and the animation of piece movement is handled
        - Everything having been updated, the Controller checks if the current player has won and if they have, then the game ends. If not, then the process repeats for the next player
    - Classes:
        - Concrete: ScoreGameController, SorryGameController, StandardGameController, TeamGameController
        - Abstract: AbstractController
        - Related: Rules package, utilizes rules classes within Controller to determine movable pieces 
7. view package
    - Concrete classes: 
        - AlertView, BoardGrid, ButtonHandler, CardView, GameScreen, PieceView, PlayerView, StartScreen 
    - Enum: Alert Enum
    - Behavior:
        -  AlertView, BoardGrid, ButtonHandler, CardView, PieceView: these classes handle smaller parts or objects of the GUI and are objects utilized in StartScreen and GameScreen to create the entire GUI 
        -  StartScreen extends Application, and is the runnable class, containing the GameScreen object
        -  GameScreen creates the depiction of the Sorry! game and holds the framework that updates the game and allows for it to be played. It relies on its' Controller object to update the backend and provide information to update the front end with, and utilizes the other view classes to create front-end game objects

8. fileManager package
    - Concrete classes: InitialDataConfiguration, PropertiesReader
        - PropertiesReader handles the reading, updating, and saving of the game's configuration properties file 
        - InitialDataConfiguration handles the calculations necessary to set up each player at a valid home/base location after the user has selected their desired customizations

## Necessary Assumptions

### Rule Alterations:
- **Alteration of slide rules:** slides are not assigned to players, and each can be used by any player's game piece
- **No option to skip turn:** a player does not have the option to skip their turn, turns are only "skipped" if a player has no movement options
### Configuration Assumptions:
- **User consideration upon selection of larger number of players:** we assume that upon a larger selection of players, the user will choose to make the board larger by choosing a larger sidelength, or by choosing a smaller amount of safety squares
    - With the current configuration customization options, a user could choose 8 players and a sidelength of 13, but it will only show up correctly if the user than selects a safety square size of 2
- **Bug in board customization selections:** After the user has inputted their selection for any of the customization options on StartScreen via selecting a value from ComboBox, they cannot reselect a new value - this value will not be read into the property file, only the initial selection counts
- **Bug in Player Type selection:** The PlayerType ComboBox forces you to select a different type of player than the last Player you selected for *(ex: you wouldn't be able to select HardAI for player #1 and #2).* You can get around this by selecting a different player type, and then going back into the ComboBox and selecting the desired one. 


## Differences from Initial Plan
#### Original Plan:

*"The primary design goals for our project are to:*
*1. Create an interactive online implementation of Sorry as according to the [rules of the game.](https://winning-moves.com/images/sorryrulesbooklet.pdf)*
*2. Allow the user to choose from multiple Board designs and Player types*
*3. Give the user the ability to customize the Board, the Deck and their Game-pieces' appearances including 3d animations."*

Our final project definitely hit the majority of these described goals. As we progressed we took some of the focus away from 3d animation and creating boards of various shapes, and focused more on the flexibility in terms of customization, and on implementing different versions of the Sorry game. 

## Adding New Features:

**Implementing a new variation of Sorry:** *(with altered scoring, rules, or determination of winners)*
1. If applicable (as was in the implementation of Team Sorry), create a new Rules class that handles the rules of each card, determines which of a player's game pieces are movable upon the drawing of a card, and how other game pieces respond to the determined permissible game piece movements. Place this class in the src/ooga/controller/rules package.
2. Create a new Controller, entitled "(Name)GameController" that extends the AbstractController class. 
    - If a new Rules class was added, then set up the constructor as such:
        ```java
             public (Name)GameController(String propertiesFile){
                super(propertiesFile);
                rules = new (Name)Rules((sideLength-1)*4, homeLength);
          }
        ```   
    - otherwise the Rules class should be set to StandardRules as such:
        ```java
            rules = new StandardRules((sideLength-1)*4, homeLength);
        ```
    - Override `public Player determineWinner()` and `public void scoreOfPlayer(Player player)` methods (from the Controller interface which is implemented in AbstractController) and write these methods such that the winner is determined according to the new game type's definition of winning
    - Override any other methods from Controller Interface that apply to the new style of game.
        
3. In ButtonHandler.java add the name of your new game type as an option in the score choice comboBox, as such:
    ```java
        void makeScoreChoice(){
            makeComboBox(scoreChoice);
            scoreChoice.setId("ScoreChoice");
            scoreChoice.setPromptText(textResource.getString("scoreSystem"));
            ObservableList<String> list = FXCollections.observableArrayList();
            list.add("Standard");
            list.add("Sorry");
            list.add("Score");
            list.add("Team");
            //Your Line:
            list.add("(Name)");
            scoreChoice.setItems(list);
        }
    ```
    - This name must be the exact same as the name in your new (Name)GameController for proper reflection determination of the selected Game Controller in GameScreen
4. Either alter an existing .properties file as such:
    ```properties
            Player4Home=44
            Players=4
            Player1Type=EasyAI
            Player2Type=EasyAI
            ...
            Scoring=(Name)
            ...
            Player1Bool=0,0,0,0
            Player2Bool=0,0,0,0
            ...
    ```
    Or go through either of the Customization options and select your new scoring type as the score choice
        - ![](https://i.imgur.com/LEy2f2c.png)

    If these steps are followed properly, and you have programmed the new Controller class and possibly new Rules class according to your new Game type implementation, then you will have implemented a new Game type.


**Adding new Player Type** *with new decision-making algorithm*
1. In the src/ooga/player package, create a new Player class `(Name)Player.java` that extends AbstractPlayer
2. Within the constructor for your class, utilize super() to fully connect class to Abstract Player
3. Override the `public Map<Piece, Integer> selectGamePiece(Map<Piece, Map<Integer, Map<Piece, Integer>>> movablePieces, int marginSquares, int homeLength)` method, and write your own algorithm to determine how the new player will select a game piece
4. In PlayerState.java, add the (Name) of your (Name)Player to the existing Enums
5. In ButtonHandler's makePlayerType() method, add your new Player's playerType as an option
    - For reflection to work correctly, this option needs to be the exact same as (Name) from the name of your class: (Name)Player
6. Follow step 4 from the description on implementing new types of Sorry as above, but with Player(#)Type instead of Scoring.

    Following these steps, and programming your new Player decision-making algorithm correctly, you will have successfully programmed a new Player type. 

   
    