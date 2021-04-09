# USE CASES

# Nick
Sprint 1
1. Feature: Create a file manager to read in a properties file and create the input data for the map
    - I will create a set of properties file inputs that will determine the map characteristics
2. Feature: Have the View consist of the objects in 2D form
    - The board will need to hold all of the objects that will be moving throughout the game
    - The View will hold the player's game pieces, deck of cards, and the board.

Sprint 2
1. Feature: Have the option to have any type of map be placed onto the screen
    -  The same properties file keys will be used with different values in order to have a different map configuration
3. Feature: Have the object pieces have the option to be 3D
    - I will need to research and find the best way to have a 3D game
5. Create an error handling process for when wrong data is read
    - Similiar to Simulation, there will be no stopping of the game as errors will be caught
# Maddie
Spring 1
1. Feature: A game piece is "knocked" back to home by another player's game piece
    * The location of the knocked game piece must be set back to 0 and the location in the board back to the corresponding base. 
    * The boolean method _leftBase()_ in _GamePieces_ must be set to false.
    * The piece that did the knocking needs its location updated.
2. Feature: The fourth game piece makes it home
    * the boolean value for _isHome()_ must be set to be true
    * _hasWon_ method in player must be set to true
    * _determineWinner_ method in Controller passes the winning player to be displayed
3. Feature: Player wants to choose a specific piece to move
    * Call the _selectGamePiece_ method
    * The location of the selected game piece has to be set using the _setLocation_ method in _GamePiece_

Sprint 2:
1. Feature: Multiple players selected to play
    * When this happens, the specified number of player objects need to be created
    * This will require correlating with the team member in charge of maps 
2. Feature: User wants to play against the computer
    * have to implement types of players as subclasses, one where the player is active and one where the computer is the player (automatically will play)

# Ellie
Sprint 1:
1. Feature: Player with all pins at home pulls a card with the value of 1 or 2 off of the Deck 
    * The player selects a Game Piece off of the board, this selected Game Piece is updated in _Player_'s *selectedGamePiece()*
    * The location of this GamePiece (now just outside of the player's base) is updated through *Controller's updateSelectedPiece(Card card, GamePiece selectedPiece)*
2. Feature: Player with a game-piece X amount of spaces from home draws the that exact X number card from the deck and selects this game-piece to play
    * The player selects a Game Piece off of the board, this selected Game Piece is updated in _Player_'s *selectedGamePiece()*
    * The location of this GamePiece (now just outside of the player's base) is updated through *Controller's updateSelectedPiece(Card card, GamePiece selectedPiece)*
    * *Controller's determineWinner()* is called and finds that this player has won (through *Player's hasWon()*), and the Game ends with this player being declared the winner
3. Player draws a Card with a value of 7, and chooses to split the seven between two active Game-Pieces
    * Upon drawing of a 7, the user is able to input the first split she desires to use on a GamePiece, and the second split is calculated
    * The player selects a GamePiece to use the first split on and this GamePiece is updated according to the methods below with the selected first split value being given as parameter in updateSelectedPiece.
        * The player selects a Game Piece off of the board, this selected Game Piece is updated in _Player_'s *selectedGamePiece()*
        * The location of this GamePiece (now just outside of the player's base) is updated through *Controller's updateSelectedPiece(Card card, GamePiece selectedPiece)*
    * The player selects a GamePiece to use the calculated second split on and this GamePiece is updated according to the methods above with the calculated second split value being given as parameter in updateSelectedPiece.

Sprint 2:
1. Player draws a Card with a value of 8, but has no active Game-Pieces that are able to Move 8 spaces
    * Upon selection of any man, Alert Screen displays message "The Game-Piece you selected is not able to be moved"
    * A "forfeit turn" button pops up on side, and the user clicks the button so that the game moves on to another player's turn
2. The player draws a Card that does not have the value of 1, 2, or Sorry and selects an on-Base Game-Piece to play
    * Upon selection of any man, Alert Screen displays message "The Game-Piece you selected is not able to be moved"
# Hayden
Sprint 1
1. Feature: Player pulls the last card off the deck so that the deck is empty and the throwaway deck needs to be added back and shuffled
    * Upon the completion of the turn, the deck will automatically return itself from the throwaway pile to the new card pile and reshuffle itself. In sprint 2, a popup will appear notifying the players that the deck has been reshuffled.

2. Feature: Player draws a Card with a value of 4 resulting in a game piece having to move backward 4 spaces.
    * Upon drawing the card, the player will have to select a game piece who's location will be updated with a value of 4 spaces back.

Sprint 2
1. Feature: Player tries to select a game piece to move without first drawing a card.
    * A popup will appear if a player tries to select a game piece first without drawing a card reminding the player that to move they need to draw card first.
    
3. Feature: When player draws a card, the card is animated flipping over to reveal the face.
    * In the 3D version when the player draws a card, the card will flip over visually to reveal itself. In the 2D version when the player draws a card, it will simply display itself normally.
5. Feature: Player selects a game piece to move and the 3D view switches to accomodate
    * When a player selects a game piece to move the 3D view will shift its angle to reflect the selected game piece and provide the game piece's view of the path ahead of it.