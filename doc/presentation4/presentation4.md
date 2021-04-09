
# Functionality. 
### Show off the features of your running program:

* Run the program from the master branch through a planned series of steps/interactions that shows at least the following features:
    * Choose test_prop_file.properties
        * Play game until winner & explain:
            * How Cards are clicked
                * seeing the cards definition
            * How Interactive Player works
                * show alert when not pressing piece(select different card)
            * When pressing wrong box to move to 
                * piece and location
            * Trying to save to file(not properties file) & trying to save(pressing cancel) ---> error handling
                * Alert pops up

    * Choosing a game to play, playing a game, winning or losing a game, choosing a different game to play and starting that one
    * any optional features implemented
    * Anything else that makes your project unique and interesting 
* Show an example of each kind of data file used by the program and describe which are essential (e.g., internal resources) and which can be user created (e.g., external or example data)
    * Show three examples of making a change in a data file and then seeing that change reflected when the program is run
        * change number of pieces per player from 2 back to 4
        * change number of players
        * change one of the player's colors
        * Try to have file name be not ".properties" --> alert pops up
    
# Design. 
### Revisit the design from the original plan and compare it to your current version (as usual, focus on the behavior and communication between modules, not implementation details):

* Revisit the design's goals: is it as flexible/open as you expected it to be and how have you closed the core parts of the code in a data driven way?
    * Our project is as open as we expected it to be for the controllers. Through the implementation of the backend, we've made it so that the core controller and rules classes (AbstractController and AbstractRules) simply need to be extended to add in more game options which can be seen through variations such as the StandardController vs the ScoreGameController or the StandardRules vs the TeamRules. This allows for the core rules and controller functions to be closed off to modification but allows for the project to maintain flexibility through extensions of the abstract classes.
    * Our project was also as open as we expected it to be for the different types of players. Once again, we made it so that the core player class (AbstractPlayer) that implements the Player interface simply needs to be extended to add in more player options. The three critical variations that we wanted to implement are the InteractivePlayer which functions in conjunction with the PlayerView class, the EasyAIPlayer, and the HardAIPlayer differ in the algorithm/method used to select the movement options which was done by allowing the interface method for each type of player to be determined by the sub-classes. This allows for the core methods of all Players to remain unchanged in the abstract class while allowing the flexibility to easily add in more types of AI Players in the future through creations of a new extending class that utilizes a different algorithm.
    * However, it wasn't as open as we planned in the model and frontend. Initially, we had planned to implement different board shapes to allow for additional customization; however, we faced numerous issues with displaying that model when related to critical aspects of the game. Because of those issues, we closed off the board shape to always be a square.
* describe two APIs in detail (one from the first presentation and a new one):
    * Controller API:
```java
    public interface Controller {
      List<Integer> getSlideLocation();

      int getSlideLength();

      int getHomeLength();

      void setUpBoard();

      void updateBoard();

      void createPlayers();

      List<String> getColor();

      void createHomeOrBase(int idx, int playerIdx, int location, boolean home);

      Map<Integer, List<Integer>> getHomeLocation();

      Map<Integer, List<Integer>> getBaseLocation();

      Player determinePlayerTurn();

      List<Piece> handlePlayerTurn(Player player, Map<Piece, Integer> movementPieces);

      void resetDeck();

      Card getTopCard();

      Player determineWinner();

      void scoreOfPlayer(Player player);

      Deck getNewCardDeck();

      Deck getThrowawayDeck();

      List<Player> getAllPlayers();

      int getSideLength();

      Map<Piece, Map<Integer, Map<Piece, Integer>>> getPossibleMovements(Player currentPlayer)
          throws InvocationTargetException, IllegalAccessException;

      Card getCurrentCard();

      String getPropertiesFile();

      void updateScores();

      void savePlayerLocationAndBaseBoolean(String file); 
}
```

* Player API:
```java
    public interface Player {
        Map<Piece, Integer> selectGamePiece(Map<Piece, Map<Integer, Map<Piece, Integer>>> movablePieces, int marginSquares, int homeLength);
    
        boolean hasWon();

        List<Piece> getGamePieceList();

        int getHomeLocation();

        int getBaseLocation();

        int getPlayerNumber();

        PlayerState getPlayerState();

        void setSelectedPiece(Piece piece);

        Piece getSelectedPiece();

        void setSelectedMovement(Integer integer);

        Integer getSelectedMovement();

        int totalDistanceFromHomeCalculator(List<Piece> pieces, int marginSquares, int homeLength);
    }
```

* show two Use Cases implemented in Java code in detail that show off how to use each of the APIs described above
    * Use Case of Controller's determinePlayerTurn()
    
    ```java
        public void updatePlayerTurn() {
            myPlayer = myController.determinePlayerTurn();
        }
    ```
    * Use Case of Player's totalDistanceFromHomeCalculator()
    ```java
        public void scoreOfPlayer(Player player) {
            allScores.put(player, player.totalDistanceFromHomeCalculator(player.getGamePieceList(),(sideLength-1)*4, homeLength));
        }
    ```
    
* describe two designs
    * one that has remained stable during the project
        * Backend Handling of the Board
            * Our inital design of creating the game board was done almost exactly as our inital design. It was created to be flexible enough to be created for any configuration of shape or size of the actual board. It created a naming mechanism of the locations in 1D that would associate each location with a game piece object. This map was read by the controller to send the FRONTEND location(row, col) based on the BACKEND GAMEBOARD location.  In addition, it stores information on the pieces in the bases, as it is used in any configuration of the game Sorry possible.
    * one that has changed significantly based on your deeper understanding of the project: how were those changes discussed and what trade-offs ultimately led to the changes
    * 
        * Backend Handling of Rules
            * As we worked through implementing Interactive Player we realized that our original structuring and interaction between front-end and back-end that handled piece movement wouldn't work for our implementation of interactive player
                * originally the Rules class took in the card value and a player, then moved the piece automatically according to player selection (all back-end selection handling because only AI players)
            * We changed this class such that upon the drawing of the card, the Rules class would return the selectable/movable pieces and to where they would move, along with any other piece's corresponding movements to that piece's movement (split 7's, switching location via 11)
            * This change enabled the player selection of the piece(s) to move and where to move them to to be separate from the piece's actual movement, which allowed for easier implementation of the Interactive player


# Team
### Contrast the completed project with where you planned it to be in your initial Wireframe and the initial planned priorities and Sprints with the reality of when things were implemented

* Sprint 1
    * We planned on having Sprint 1 implement the basic implementation of our game with no customization, a set number of players, and 2D design and animation. In additon, we wanted to have gameplay functionality working with everything but the frontend.
    * We did a good job at following through with this planning of the sprint. We had much of our backend working with the cards, rules, players, and gameboard. However, we lacked in pretty much any of the frontend and View components. The controller class was not as well done as necessary due to the components not being fully connected.

* Sprint 2
    * We planned on having there be alert screens, 3D handling, and the creation of multiple maps and different number of players. 
    * This sprint presented us with taking the game and extending its flexibility to other inputs. We did a really good job at having there be any amount of players, pieces, and piece locations to be set in the initial configuration of the property files. We disrupted the idea of 3D due to other features having higher priority.

* Complete Sprint
    * We had a rather bland plan for this sprint that consisted of a customization screen.
    * Due to this generity, we did work on this very well, by having a customization screen with the amount of players, sidelength, slidelength, game pieces, piece location, scoring type, home length, player color, and player type.

Our initial wireframe was only for the game screen and not for the customization start screens. Thus, we followed our wireframe and added to it.
    
    
#### Individually, share one thing each person learned from using the Agile/Scrum process to manage the project.

* Maddie
    * By using the Agile/Scrum process, I realized how much more the team gets done when we are organized and have a direct plan of attack.  Writing out each individual task and allegating who is working on each task definitely helped us time manage and complete what we needed to get done in each sprint.

* Hayden
    * During this project, the Agile/Scrum process allowed us to focus our efforts in certain areas that we could then see directly when it was finished. This allowed us to feel a greater sense of accomplishment throughout the project's progress as opposed to only feeling satisfied when the entire project was complete. I learned that utilizing the Agile/Scrum process allowed for a more concrete path towards getting the project done.

* Ellie
    * At first we did not utilize the Agile/Scrum process for the project, which notably slowed our progress. Once we began listing out each small issue and assigning it to individuals, our progess became much more linear and the work I had to do each day became much more clear. 

* Nick
    * During this project, we were introduced to the Agile process through the assignment of issues. I was really impressed with our progress once we converted to the Agile process by meeting at least once every other day. 
### Show a timeline of at least four significant events (not including the Sprint deadlines) and how communication was handled for each (i.e., how each person was involved or learned about it later)
1. Front-end implementation of piece movement & animation
    - after Sprint 1, we were finally able to get the pieces to jump around the boarding according to the chosen card's rules
    - We needed to debug this movement, as occasionally pieces would move incorrectly as according to card rules
    - With Interactive player in Sprint 2, we enabled the selection of a piece and the margin square it would move to
    - In the final week, we were able to get the piece to animate as it moved, as opposed to having the piece jump from one spot to another
2. Implementation of an Interactive player
    - The implementation of an interactive player took far longer than anticipated, and wasn't fully set up until after sprint 2, though the back-end was mostly set up during sprint 2
    - We realized that in order to implement interactive player, we needed to overhaul our Rules class and parts of our controller class 
    - In the days following sprint 2's end date, we were able to successfully create the back-end of the Interactive player and then finally, after much debugging, we finalized the front-end
3. Creation and debugging of Start screen/ customization screen
    - The Start Screen was initially set up during the beginning of Sprint 2, though it was not set up to interact with the Game Screen or the configuration of our game until later
    - After figuring out how to cause the start screen to transition directly into the game screen upon the user's selection of the "Start" button, we worked towards debugging the interaction between the start screen & the game configuration
    - We then additionally added in an initial selection option of regarding the user's desired level of customization which affected whether or not the customization/Start screen appeared and which customization options it held
    - Finally we continued to add new customizations throughout the final week, such as the user's selection over whether or not they wished to start with their pieces in base or on the board 
4. Alert Screens & connection with error handling
    - after Sprint 1, our error handling was limited to basic error handling for the board configuration
    - during Sprint 2, custom exceptions were developed to be thrown out of the backend
    - during the final sprint, these exceptions were combined with interaction handling to dictate when alerts would be displayed to the player to notify them of poor clicks and other input options such as bad input files in addition to any backend error caused by gameplay decisions which allows for a more seamless gameplay experience for an interactive Sorry player

#### Individually, share one thing each person learned from trying to manage a large project yourselves.

* Maddie
    * I learned how important it was to really spend time planning out our sprints and features that we wanted to implement.  With large projects like this it is often easy to fall behind if there is no direction.  Our team spent several hours before each sprint planning out our priorities and what each indiviudal person needed to get done.  This definitely helped us succeed in the long run as we all managed our time well.

* Hayden
    * I learned that it is important to consider features in advance as opposed to dealing with them when it happens. Because I didn't initially consider this, we had to rework the backend to account for features. With large projects such as this one, it can be a major time drain especially if the feature implemented are late in the project to rework it all.

* Nick
    * I learned how the planning stage before the sprint is crucial in order to have the team be on the same page about the progress that needs to be made for the current sprint. I learned a lot about the importance of assigning issues due to importance to try and minimize as many blockers as possible.

* Ellie
    * By planning and breaking it down, the large project felt much more managable. 
    
### Describe specific things the team actively worked to improve on during the project and one thing that could still be improved
- Constant Teamwork: 
    - As a team, we constantly worked together to improve the movement of the pieces. Getting the pieces to move and update correctly in both the front end and back end took whole-team communication, especially as we implemented new features and new player types. Two instances that especially impacted our piece and pieceView objects were the complete overhaul of our back-end to create the Interactive Player, and then the relocating of our pieceViews from being set on the GameBoard's gridPane, to being placed directly on the root. 

- Possible Improvements:
    - One thing we believe could still be improved upon is piece animation around the corner. In spite of all of the time we jointly spent working on piece & pieceView movement, we were never able to quite get a handle on this specific segment of piece animation
    
#### Individually, share one thing each person learned about creating a positive team culture.

* Maddie
    * Our team worked extremely well together.  We genuinely enjoyed meeting and each of us was very motivated to contribute to the group effort.  I think most importantly each of us would happily help out one of our other teammates if they were struggling on a specific feature.  No one was every in it alone.  Speaking for myself, I was so comfortable asking for help or clarificiation (which is not always the case).  Being comfortable in my environment is very important to the success of my work.  The project was very much a team effort instead of individuals working towards the same goal.

* Hayden
    * I think setting clear goals and roles that overlapped allowed for team members to feel more comfortable asking questions and be more willing to support each other on portions of code. It made it easy to see what was being worked on by others and who it made most sense to ask for help from.

* Ellie
    * Having such a positive team culture allowed for us to work most productively together. Throughout the weeks as we spent more time working together, I felt increasingly comfortable posing difficult questions such as "why are we structuring the back-end like this?" or questions regarding sections of code I didn't understand. It also allowed for us to debate the structure of our program without worrying about hurting individuals feelings, which ultimately lead to better decision making. 

* Nick
    * In terms of positive team culture, I think we did a great job at being inclusive withn one other, even if we may have not know each other beforehand.We were able to spend a lot of time working with each other through pair programming and were able to combat many debugging issues because of it.

### Revisit your Team Contract to assess what parts of the contract are still useful and what parts need to be updated (or ifsomething new needs to be added)

* Still Useful:
    * Respect our team-members opinions and work
    * Communicate regularly
    * Regular team meetings
    * Communicate through Zoom, text-message, and Git
    * Make group decisions based on consensus

* To Be Re-visited:
    * Our team-meetings are not always at a set time.  We meet extremely often (sometimes multiple times a day) so we will usually just messgae in the group and see who can attend.
    * We began to use our issues consistently to divide up the tasks and plan out our sprint accordingly.  Would have a meeting to make the issues together.
#### Individually, share one thing each person learned about how to communicate and solve problems collectively, especially ways to handle negative team situations.

* Maddie
    * Our team excelled at working and coding together.  In my past teams, we had worked on individual parts and tried to combine the parts at the end which had caused many issues and was definitely not efficient.  For the final project, our team was constantly connecting the different parts (front-end and back-end) to make sure that the entire project was functioning and a single aspect of the program did not fall behind.

* Hayden
    * I think the use of group coding sessions was something that I found to be useful for communicating questions and solving any problems collectively. In other group projects, coding was generally done individually which made asking questions and giving help much harder. However, we consistently coded in groups and additionally met often to determine what each other was working on which allowed us to be able to get answers and help from group members immediately.

* Ellie
    * One part of the team contract that I felt had been disregarded by other groups I had worked with was the section on helping out teammates no matter the situation as best as one could. As I progressed through the project and found that certain problems/issues assigned to myself were much more complex than anticipated, I felt comfortable enough to reach out for help, and my teammates reciprocated immediately. 

* Nick
    * I also was felt really comfortable asking for help to any of my teammates with any problems that arose. I mainly worked with Ellie and Hayden and felt they did a good job on allowing me to feel included and comfortable enough to ask them for help.