# Team Sorry
### Genre Game
Board Game - Sorry©

### Game Features Completed
* [Standard gameplay](https://winning-moves.com/images/sorryrulesbooklet.pdf)
    * Features
        * Backend
            * Handling of Piece Movement
                * Moving the Pieces
                * Determining which Pieces are movable
            * Easy AI Player
                * selection of pieces to move
                * selection of movement
                * storing of home and base locations and pieces
            * Creation of reading of different property files
                * interpretation of property files
                * basic error handling of bad inputs
            * Constructing and Updating the Game Board
            * Piece
                * storing location of the game piece in a 1D interpretation of the board
        * Frontend
            * Card View
                * handles displaying of the card
                * handles the view of the new card deck and throwaway deck
                * pulling of new cards
                * info of each card
            * Game View
                * handles of elements
                * handles updating of the board view with the movement of pieces
                * text displaying current player turn
            * Board Grid
                * displaying of the board
                * basic player home and base locations and margin square locations
                * sets up the border panes for the view
                * updates location of each piece in the front end
            * Piece View
                * handles the display of each piece
                * handles conversion of piece location stored in backend to 2D front end view
### Significant Event
    * Functional Backend for standard game
        * informs us how to go about implementing more variations of the Sorry game
        * provides support for the frontend

### Teamwork and Communication

    * Worked Well
        * Responsibilities
            * individual responsibilities
            * assistance of others through overlaps of responsibilities
    * Worked Poorly
        * didn't use issues at all
            * caused front end to fall behind
            * to combat this we are heavily utilizing the issues feature in gitlab

### Planned Features

    * Increased flexibility of the game
        * various inputs in start screen to control game setup
            * writes to property file and initializes based on the newly written file
        * front end display changes to cater to player's desires
    * Player options
        * additional AI player
        * interactive player
    * Error handling throughout the game
    * Slides
        * game feature in backend and frontend
    * Front end
        * fixing bugs in movement
        * adding home and base icons to identify where the home and base are
    * taking out hard coded values and allow to be changed through property files
        
