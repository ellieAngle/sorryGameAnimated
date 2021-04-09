# Introduction

Our team chose to represent the Board game genre through writing a program to create an interactive ["Sorry" game.](https://www.amazon.com/Hasbro-Gaming-A5065-Sorry-Game/dp/B076HK9H7Z) 

The primary design goals for our project are to:
1. Create an interactive online implementation of *Sorry* as according to the [rules of the game.](https://winning-moves.com/images/sorryrulesbooklet.pdf)
2. Allow the user to choose from multiple Board designs and Player types 
3. Give the user the ability to customize the Board, the Deck and their Game-pieces' appearances. 

Our design will utilize a Controller to navigate between the GUI and the back-end of the code. The controller will serve as the separation between the front & back end, and will handle updating the Game as according to the user's choices as they play the game. 
The back-end of the game is made up of modules that handle each of the game's objects and those object's behavior. 
The front-end will handle the GUI and the appearance of the game's objects according to user selection. 

# Wireframe
[MockPlus](https://app.mockplus.com/run/rp/JB6zXNQ7kc)

# Overview
This section serves as a map of your design for other programmers to gain a general understanding of how and why the program was divided up, and how the individual parts work together to provide the desired functionality. Describe specific modules you intend to create, their purpose with regards to the program's functionality, and how they collaborate with each other, focusing specifically on each one's API. Include a picture of how the modules are related (these pictures can be hand drawn and scanned in, created with a standard drawing program, or screen shots from a UML design program). Discuss specific classes, methods, and data structures, but not individual lines of code.
![](https://i.imgur.com/Bom2mMD.jpg)

Our team designed several interfaces to encapsulate the necessary components in running our game. These components include Card, Board, Deck, GamePiece, Player, and the Controller.

Deck
- The Deck class consists of adding and removing cards to the deck in order to create and manage the deck for the game.
- The deck will consist of Card objects

Card
- The Card class is used to create Card objects that will be used

Player
- The Player class will have 4 GamePiece objects and will have methods in place to store the objects in the setGamePieceMap method.
- In addition, there will be updating of the player object after each turn

GamePiece
- This class will have the creation of the different objects with their starting position and the "winning" position.
- There will be an updating mechanism in order to set the location and state of the game pieces

Board
- The board will consist of the player objects that will have their game pieces on the board.
- We will probably add more to this further in the development of our project

Controller
- Evidently, the Controller class will consist of every other class as this is where the updating occurs via the front end user action.
- The updating will be called for in the controller to receive the updated data
- This updated data will then cause the front end objects to move and update themselves

# Design Details
This section describes each module introduced in the Overview in detail (as well as any other sub-modules that may be needed but are not significant to include in a high-level description of the program). Describe how each module's API handles specific features given in the assignment specification, what resources it might use, how it collaborates with other modules, and how each could be extended to include additional requirements (from the assignment specification or discussed by your team). Finally, justify the decision to create each module with respect to the design's key goals, principles, and abstractions.

### Specific Methods Associated
**Deck**
1. addCard() - after a card is drawn from the deck, this method can be called to add the recently drawn card to the "discarded" deck.
2. removeCard() - similar to the functionality of _addCard_, _removeCard_ will remove a specific card from the drawing deck, once it is drawn.
3. getCountOfDeck() - this method returns the number of cards that are left in a deck at the time the method is called. When the count of a deck is 0, we know that the deck is now empty.

**Player**
1. selectGamePiece() - this method will get the user's selected game-piece from the Controller or utilize an AI algorithm to get the desired game-piece for a Computer player
2. hasWon() - this method checks whether or not the current player has won the game 
3. setGamePieceMap(List<Integer> location) - this method set's up the Player's 4 gamePieces and their corresponding locations. *At the beginning of the game each game, the user's game-piece locations will all be set to that Player's base*
4. updateGamePieceMap(GamePiece selectedPiece, int newLocation) - when a user moves a game-piece, this method will update the user's map of game pieces with this game-piece's updated location 
5. setPlayerRelativeLocation()
    - as according to our set up of the Board, a ooga.ooga.gamepiece will be designated as being on base if it's location value is 0, and as being home if it's location value is equal to the number of spaces on the board it must traverse (size of the board). However, as each player has a different starting location on the board, then each player has a relative base/home location to the other players.
    - This method takes care of setting the "relative location" as described above.
6. getPlayerRelativeLocation() - this method get's a player's relative location as described above

**GamePiece**
1. getLocation() - this returns a single int value that corresponds to the location of the piece the method is called on.  The location of each piece is relative to the distance from the base.  When the piece's location is equal to the maximum location value for the board, the piece has made it to the home location.
2. inHome() - boolean value that returns true if the game piece is in home (the final destination).
3. leftBase() - boolean that returns true if a game piece has left the base and is in play.
4. setLocation() - void method that allows the location of a game piece to be set once a card has been drawn and a player chooses a piece to move.

**Card**
1. getValue() - returns the value of a drawn card.  If the drawn card is a "Sorry card", its value is 0.

**Board**
1. getPlayerBaseLocation() - returns an int value corresponding to the location on the board that the base for each player is.
2. getPlayerHomeLocation() - eturns an int value corresponding to the location on the board that the home for each player is.
3. setLocationsForPlayers()- this method sets the relative home/base locations for each Player in the game.

**Controller**
1. setUpBoard() - this method takes care of setting up the game's board according to the Controller's properties file
2. determinePlayerTurn() - this method determine's which Player's turn it is to play
3. updateSelectedPiece(Card card, GamePiece selectedPiece) - this method updates the piece the user selected according to which Card was chosen off of the deck
4. resetDeck() - when the deck-to-choose-from is empty (and thus the discard deck is full), or when a new game is started, this method resets the main Deck by shuffling the 44 cards of specific types and placing them in a LinkedList
5. getTopCard() - this method get's the top card off of the Deck of cards (structure being a LinkedList), and then places this card in the discarded deck
6. determineWinner() - this method determines a) if there is a winner at the current point in the game, and b) which Player that winner is.

# Example games

- Sorry
    - Players move multiple different board pieces around the board trying to get all the pieces home before the other players. Players can slow the progress of other players by sending them backwards.

- Monopoly
    - Players move around the board buying up and trading property in attempt to be the last player with money and property. Players can collect rent from other players and have ways to increase the rent as they try to take everyone else's money.

- Settlers of Catan
    - Players act as settlers of the land trying to build and develop what they already hold while also trying to trade and acquire more resources. As their settlements grow, they gain points.

# Design Considerations
This section describes any issues which need to be addressed or resolved before attempting to devise a complete design solution. Include any design decisions discussed at length (include pros and cons from all sides of the discussion) as well as any ambiguities, assumptions, or dependencies regarding the program that impact the overall design.

- We have considered various ways of storing locations of pieces. Initially, we thought of using X and Y coordinates, but decided to allow the front-end and the decided on properties for that game to decide where on the screen everything would be. Instead the backend simply stores where relative the home and base the piece is allowing for everything to be linearly stored in the backend.

- 2D vs 3D visualization models were debated. We believe that 3D models will add a level of sophistication to the project but recognize that 2D models may be preferred by some and is also more straightforward. As such, we plan to implement both, but start with 2D models.

- Storing of Cards and how they are randomized was discussed as the actual game only has a certain amount of cards for each type. As such we cannot just randomize it based on probability. Therefore, the deck will actually have all the cards and the randomization will exist with how they are ordered as opposed to randomizing each value for the card selected.