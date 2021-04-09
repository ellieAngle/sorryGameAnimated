# Team Sorry
### Genre Game
Board Game - Sorry©

### Game Features Completed
* Features
    * Backend:
        * Reworked entire Rules class to enable interactive player decision making & hardAI player decision making
        * Created HardAI player & InteractivePlayer
        * Created Slides in Backend
        * Fixed bugs pertaining to piece movement
    * Configuration: 
        * Customization of game
        * Uploading of existing gameBoard files 
        * Increased flexibility for user
    * Frontend
        * Added a Start Screen for the player that enables choice of level of customization and corresponding customization choices 
        * Implemented front end of interactive player 
            * indication of movable pieces upon card draw
            * clicking to select gamePiece
            * indication of possible locations to move to after piece selection
            * clicking on location to move 
        * Fixed piece movement front-end bugs 
        * Enabled movement of piece into home location
        * Home and base location indicators, piece labels 
    * Controller
        * Added different game types through two different types of scoring
        * Reworked controller interaction with front end 
            
### Significant Event
* Refactoring entire Rules class 
    * Rules class used to call the piece selection functions and handle back-end movement of piece object
    * Now rules class returns all movement options for any player's movable piece given the card rule, as well as possible resulting movements from a player's move
    * This change allows for Interactive player to be most effectively implemented & allows for better separation of front-end and back-end 

* Front end implementation of Interactive player
    * Allows for game to actually be played (not simulated)

* Implementation of Start Screen that connects to file manager and allows for flexibility within game configuration

### Teamwork and Communication
* Worked Well
    * We used our issues and had a planning period of 2 hours on Tuesday to create and assign issues.
    * During the assigning process, we continued to break up the new tasks as according to tasks each group member had completed
        * Ellie and Maddie mostly worked on front-end
        * Hayden and Nick mostly worked on back-end
        * This allowed for harder issues to be solved by two people
            rather than one ---> increased efficiency.
    * Worked Poorly
        * During the design of our EasyAI player, there was not enough communication between members nor well-rounded consideration  of what implementing an interactive would mean for our code. This resulted in us having to overhaul the back-end during this sprint, and us losing valuable time that could have been spent on other issues
        * Additionally due to scheduling errors sometimes only a few members could make it to group coding sessions
            


### Planned Features
* Front-end:
    * Animation of pieces as they move (as opposed to hopping from currently occupied space to new space)
    * Alerts to the player regarding movement and errors
    * Scoring indication, and indication of Player's pieces' locations relative to Player's home
    * Customization handling on start screen for different game types using reflection, more customization of types of players
        * update player # choice to reflect decision that only 2-4 players should play (this is how the real Sorry board game works)
    * Winner screen pop-up upon a player winning 

* Back-end:
    * creation of "Team Sorry" - can pair up with another player
        * if one player on teams wins, then both do
        * the movement of team members will not detract from another player's game status (a team member won't bump another team member's game piece)




        
