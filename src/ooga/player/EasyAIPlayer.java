package ooga.player;

import java.util.*;

import ooga.gamepiece.Piece;

/**
 * This class is an example of the implementation of the super-classes.  This class extends AbstractPlayer
 * which will represent how simple implementing a new player is based on the design of the code and using the Player API
 *
 * The EasyAIPlayer chooses its location randomly (no logic behind movement decisions)
 */
public class EasyAIPlayer extends AbstractPlayer {
    Random randomizer;

    /**
     * The EasyAIPlayer constructor calls the super-class and also creates a randomizer to choose a random movement
     * @param homeLocation int value representing the home location of a player
     * @param baseLocation int value representing the base location of a player
     * @param pieceLocations an int array that represents the piece locations that the pieces of a player occupy
     * @param playerNumber int value that represents the number of a player
     * @param playerState the type of player that the specific player is (currently can be interactive, hard AI, or easy AI)
     * @param baseLocations the base locations of a piece based on the user input data
     */
    public EasyAIPlayer(int homeLocation, int baseLocation, int[] pieceLocations, int playerNumber, PlayerState playerState, int[] baseLocations) {
        super(homeLocation, baseLocation, pieceLocations, playerNumber, playerState, baseLocations);
        randomizer = new Random();
    }

    /**
     * Overrides the method from the interface and instead implements the algorithm for the EasyAIPlayer to select
     * a game piece based on the randomizer value
     * @param movablePieces A complex map of map of maps that represents a piece and the possible moves and then the possible
     * options to move based on the card that was drawn
     * @param marginSquares the number of squares around the perimeter of the board; is based on a user input value and read from the properties file
     * @param homeLength changes the number of safety squares until the home; is based on a user input value and read from the properties file
     * @return a map whose key is a piece and the value is an integer representing the moves
     */
    @Override
    public Map<Piece, Integer> selectGamePiece(Map<Piece, Map<Integer, Map<Piece, Integer>>> movablePieces, int marginSquares, int homeLength) {
        Map<Piece, Integer> moves = new HashMap<>();
        if(!movablePieces.isEmpty()){
            List<Piece> pieces = new ArrayList<>(movablePieces.keySet());
            int rand = randomizer.nextInt(pieces.size());
            Piece myPiece = pieces.get(rand);
            Map<Integer, Map<Piece, Integer>> resultingMovement = movablePieces.get(myPiece);
            List<Integer> moveInts = new ArrayList<>(resultingMovement.keySet());
            rand = randomizer.nextInt(moveInts.size());
            int myMove = moveInts.get(rand);
            moves.put(myPiece, myMove);
            if (resultingMovement.get(myMove) == null) { return moves;}
            else {
                Map<Piece, Integer> resultingMoves = resultingMovement.get(myMove);
                List<Piece> resultingPieces = new ArrayList<>(resultingMoves.keySet());
                rand = randomizer.nextInt(resultingPieces.size());
                Piece resultPiece = resultingPieces.get(rand);
                int resultMove = resultingMoves.get(resultPiece);
                moves.put(resultPiece, resultMove);
            }
        }
        return moves;
    }


    /**
     * Used in testing to make sure that the random values are consistent and we can test the outcome to be correct
     * @param seed int value for the chosen seed
     */
    public void setRandomSeed(int seed){
        randomizer.setSeed(seed);
    }
}

