Team Sorry
====

Names:

* Hayden Lau
* Maddie Cecchini
* Ellie Angle
* Nick DeCapite


### Timeline

Start Date: October 23, 2020

Finish Date: November 19, 2020

Hours Spent: 28 hours per week

### Primary Roles

* Hayden Lau
    * Backend
        * Rules
        * Controller
        * Player
    * Frontend
        * Alert
* Maddie Cecchini
    * Backend
        * Piece
        * Player
    * Frontend
        * Board
        * Game Screen
        * Player View
* Ellie Angle
    * Backend
        * Rules
    * Frontend
        * Splash Screens
        * Piece View
        * Card View
        * Game Screen
* Nick DeCapite
    * Backend
        * Controller
            * Variations of Game Types
            * Game Board
        * Configuration
            * File Management
            * Board
    * Frontend
        * Customization Screen
### Resources Used

 * [Sorry Game Rules](https://winning-moves.com/images/sorryrulesbooklet.pdf)
 * [Animation Java API](https://docs.oracle.com/javafx/2/api/javafx/animation/TranslateTransition.html)
 * TA Office Hours

### Running the Program

Main class: StartScreen.java

Data files needed:

* InputOutputResources.properties
* textResources.properties
* viewConfiguration.properties
* Assorted Card Images in the resources directory
* Winning Screen Image
* Sorry Game Logo Image
* test_prop_file.properties
* default.css

Features implemented:

* 4 different variations of Sorry Game
    * Standard Gameplay
    * Team Gameplay
    * 2 variations of Scoring Gameplay
* Customization
    * UI customization
    * Initial Configuration from Start Screen
    * Initial Configuration from pre-existing properties file
* Save Current Game Status
* 3 different variations of Players
    * Easy AI
        * random selection of moves
    * Hard AI
        * selection of moves based on proximity to home
    * Interactive Player
* Splash Screens
    * Start Screen
    * Various Customization Screens
    * Victory Screen
* Loading any data file, specifically implemented for loading saved games to be completed
### Notes/Assumptions

Known Bugs:

* Re-selecting configuration options
* Player Type choice forces you to switch and reselect initial type

### Impressions

* Improvements and Additional Features
    * "Feelings" based AI player
        * storage of which other opposing players have harmed its progress with selecting moves to target them
    * Pieces animate going around corners (and backwards around the board when sent back to base) instead of going straight to next space
    * Selection and creation of own teams as opposed to automatic placement into teams
    * Front-end score display 
* Overall Impressions of Assignment
    * 4 project members was a happy medium for scheduling meetings and coding assistance while still managing workload
    * project design allows for ease of additional future features
        * For example, any type of player, scoring rules
