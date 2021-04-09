package ooga.controller.rules;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ooga.cards.Card;
import ooga.cards.CardValue;
import ooga.gamepiece.Piece;
import ooga.player.Player;

/**
 * @author Hayden Lau, Ellie Angle
 *
 * This class determines the rules of the card behavior. Each different rule is defined a method and given a player, the card itself, and a list of the other players
 * returns out a map of which pieces can move where and if there is any complementary pieces that also have to move as a result of the initial movement.
 *
 */
public abstract class AbstractRules {
  protected final int marginSquareCount;
  protected final int safetyZoneSize;

  public AbstractRules(int marginSquareCount, int safetyZoneSize) {
    this.marginSquareCount = marginSquareCount;
    this.safetyZoneSize = safetyZoneSize;
  }

  /**
   * This method uses reflection to determine which rule needs to be applied based on the card behavior and the helper method correspondingMovableMethod
   *
   * @param gameCard the card to get the rule for
   * @param currentPlayer the current player
   * @param otherPlayers list of all the other players
   * @return map of which pieces can move where and if there is any complementary pieces that also have to move as a result of the initial movement
   * @throws InvocationTargetException if the invocation isn't done properly
   * @throws IllegalAccessException if the method called isn't accessible
   */
  public Map<Piece, Map<Integer, Map<Piece, Integer>>> getPossibleActions(
      Card gameCard, Player currentPlayer, List<Player> otherPlayers)
      throws InvocationTargetException, IllegalAccessException {
    Method movementType = correspondingMovableMethod(gameCard);
      return (Map<Piece, Map<Integer, Map<Piece, Integer>>>)
          movementType.invoke(this, gameCard, currentPlayer, otherPlayers);
  }

  /**
   * This method handles the numeric movement card behavior. Given the card, a player's piece,
   * if movable, is able to move to its current location plus the card's value
   *
   * @param card the card to get the rule for
   * @param currentPlayer the current player
   * @param otherPlayers list of all the other players
   * @return a map of pieces that can move and the location of where they are moving to
   */
  public Map<Piece, Map<Integer, Map<Piece, Integer>>> moveNumericAmount(Card card, Player currentPlayer, List<Player> otherPlayers) {
    Map<Piece, Map<Integer, Map<Piece, Integer>>> ret = new HashMap<>();

    List<Piece> movables = getMovablePieces(card, currentPlayer);

    for(Piece piece : movables) {
      Map<Integer, Map<Piece, Integer>> movement = new HashMap<>();
      Integer newLocation = determineNewLocation(currentPlayer, piece.getLocation(), card.getValue().getValue());
      movement.put(newLocation, null);
      ret.put(piece, movement);
    }
    return ret;

  }


  /**
   * This method handles the move a man out rule. If a player's piece is in base, then this card
   * allows them to move it out of base or they can move an already out piece the card value's numeric amount.
   *
   * @param card the card to get the rule for
   * @param currentPlayer the current player
   * @param otherPlayers list of all the other players
   * @return a map of pieces that can move and the location of where they are moving to
   */
  public Map<Piece, Map<Integer, Map<Piece, Integer>>> moveManOut(Card card, Player currentPlayer, List<Player> otherPlayers) {
    Map<Piece, Map<Integer, Map<Piece, Integer>>> ret = new HashMap<>();

    List<Piece> movables = getMovablePieces(card, currentPlayer);

    for(Piece piece : movables){
      Map<Integer, Map<Piece, Integer>> movement = new HashMap<>();
      int newLocation = determineNewLocation(currentPlayer, piece.getLocation(), card.getValue().getValue());
      if(!piece.leftBase()){
        newLocation = currentPlayer.getBaseLocation();
      }
      movement.put(newLocation, null);
      ret.put(piece, movement);
    }
    return ret;
  }

  /**
   * This method handles the ten card's behavior. It allows a piece to either move 10 forward or 1 backwards
   *
   * @param card the card to get the rule for
   * @param currentPlayer the current player
   * @param otherPlayers list of all the other players
   * @return a map of pieces that can move and the location of where they are moving to
   */
  public Map<Piece, Map<Integer, Map<Piece, Integer>>> handleTen(Card card, Player currentPlayer, List<Player> otherPlayers) {
    Map<Piece, Map<Integer, Map<Piece, Integer>>> ret = moveNumericAmount(card, currentPlayer, otherPlayers);

    for (Piece piece : currentPlayer.getGamePieceList()) {
      if (handleCanMove(piece, -1, currentPlayer)) {
        Integer newLocation = determineNewLocation(currentPlayer, piece.getLocation(), -1);
        Map<Integer, Map<Piece, Integer>> movement = new HashMap<>();
        movement = ret.getOrDefault(piece, movement);
        movement.put(newLocation, null);
        ret.put(piece, movement);
      }
    }
    return ret;
  }

  /**
   * This method handles the eleven card's movement. It allows a piece that is out of base to move either 11 spaces numerically or switch locations with any opponent's piece.
   *
   * @param card the card to get the rule for
   * @param currentPlayer the current player
   * @param otherPlayers list of all the other players
   * @return a map of pieces that can move and the location of where they are moving to, and if they
   * are switching location it stores the other piece that is being switched and the location it is being sent to
   */
  public Map<Piece, Map<Integer, Map<Piece, Integer>>> handleEleven(Card card, Player currentPlayer, List<Player> otherPlayers) {
    Map<Piece, Map<Integer, Map<Piece, Integer>>> ret = moveNumericAmount(card, currentPlayer, otherPlayers);

    for(Piece piece : currentPlayer.getGamePieceList()){
      if(piece.getLocation()>=0){
        for(Player otherPlayer : otherPlayers){
          List<Piece> switchablePieces = getSwitchablePieces(otherPlayer);
          for(Piece otherPiece : switchablePieces){
            Map<Integer, Map<Piece, Integer>> movement = new HashMap<>();
            Integer newLocation = otherPiece.getLocation();
            movement = ret.getOrDefault(piece, movement);
            Integer otherNewLocation = piece.getLocation();
            Map<Piece, Integer> otherPieceMovement = new HashMap<>();
            otherPieceMovement.put(otherPiece, otherNewLocation);
            movement.putIfAbsent(newLocation, otherPieceMovement);
            ret.put(piece, movement);
          }
        }
      }
    }
    return ret;
  }

  /**
   * This method handles the sorry card's behavior. It allows a piece still in base to move to any opponent's pieces' location and send them back to their base.
   *
   * @param card the card to get the rule for
   * @param currentPlayer the current player
   * @param otherPlayers list of all the other players
   * @return a map of pieces that can move and the location of where they are moving to
   */
  public Map<Piece, Map<Integer, Map<Piece, Integer>>> sorry(Card card, Player currentPlayer, List<Player> otherPlayers) {
    Map<Piece, Map<Integer, Map<Piece, Integer>>> ret = new HashMap<>();

    for(Piece piece : currentPlayer.getGamePieceList()){
      if(!piece.leftBase()){
        for(Player otherPlayer : otherPlayers){
          List<Piece> switchablePieces = getSwitchablePieces(otherPlayer);
          for(Piece otherPiece : switchablePieces){
            Map<Integer, Map<Piece, Integer>> movement = new HashMap<>();
            movement = ret.getOrDefault(piece, movement);
            Integer newLocation = otherPiece.getLocation();
            movement.put(newLocation, null);
            ret.put(piece, movement);
          }
        }
      }
    }
    return ret;
  }

  /**
   * This method handles the seven card's movement. It allows a piece to move seven spaces or split the seven spaces with a piece that can move the remainder of the seven spaces.
   *
   * @param card the card to get the rule for
   * @param currentPlayer the current player
   * @param otherPlayers list of all the other players
   * @return a map of pieces that can move and the location of where they are moving to and if the
   * movement amount is being split it also stores the complementary piece and its new location
   */
  public Map<Piece, Map<Integer, Map<Piece, Integer>>> splitSeven(Card card, Player currentPlayer, List<Player> otherPlayers) {
    Map<Piece, Map<Integer, Map<Piece, Integer>>> ret = moveNumericAmount(card, currentPlayer, otherPlayers);


    for(Piece piece : currentPlayer.getGamePieceList()){
      if(piece.leftBase() && !piece.inHome()){
        Map<Integer, Map<Piece, Integer>> movement = new HashMap<>();
        Map<Integer, Map<Piece, Integer>> complementaryMoves = complementaryMoves(piece, currentPlayer);
        movement = ret.getOrDefault(piece, movement);
        movement.putAll(complementaryMoves);
        if(!movement.isEmpty()) {
          ret.put(piece, movement);
        }
      }
    }
    return ret;
  }

  private Method correspondingMovableMethod(Card gameCard){
    Method[] moveMethods =  StandardRules.class.getMethods();
    Method ret = null;
    for(Method movement : moveMethods){
      if(movement.getName().equals(gameCard.getBehavior().name())) {
        ret = movement;
        break;
      }
    }
    return ret;
  }

  private static List<Piece> getSwitchablePieces(Player player) {
    List<Piece> switchablePieces = new ArrayList<>();
    for (Piece gamePiece : player.getGamePieceList()) {
      if (gamePiece.getLocation()>=0) {
        switchablePieces.add(gamePiece);
      }
    }
    return switchablePieces;
  }

  private List<Piece> getMovablePieces(Card gameCard, Player currentPlayer) {
    CardValue cardValue = gameCard.getValue();
    List<Piece> movablePieces = new ArrayList<>();

    for (Piece gamePiece : currentPlayer.getGamePieceList()) {
      if (gamePiece.inHome()) {
        continue;
      }
      if (handleCanMove(gamePiece, cardValue.getValue(), currentPlayer)) {
        movablePieces.add(gamePiece);
      }
    }
    return movablePieces;
  }

  protected abstract boolean handleCanMove(Piece gamePiece, int cardValue, Player player);

  protected int determineNewLocation(Player player, int currLocation, int adjustment) {
    int ret;
    if(currLocation<0){
      ret = currLocation - adjustment;
      if(ret >= 0){
        ret = player.getHomeLocation()-ret;
      }
    } else {
      if (adjustment > 0) {
        if ((currLocation <= player.getHomeLocation()) && (currLocation + adjustment > player.getHomeLocation()) && (currLocation + adjustment <= player.getHomeLocation() + safetyZoneSize)) {
          return -(adjustment - (player.getHomeLocation() - currLocation));
        }
        if (currLocation + adjustment >= marginSquareCount) {
          adjustment -= (marginSquareCount - currLocation);
          currLocation = 0;
        }
        if ((currLocation <= player.getHomeLocation()) && (currLocation + adjustment > player.getHomeLocation()) && (currLocation + adjustment <= player.getHomeLocation() + safetyZoneSize)) {
          ret = -(adjustment - (player.getHomeLocation() - currLocation));
        } else {
          ret = currLocation + adjustment;
        }
      } else {
        ret = currLocation + adjustment;
        if (ret < 0) {
          ret = marginSquareCount + ret;
        }
      }
    }
    return ret;
  }

  private Map<Integer, Map<Piece, Integer>> complementaryMoves(Piece selectedPiece, Player currentPlayer) {
    HashMap<Integer, Map<Piece, Integer>> moveOptions = new HashMap<>();
    for (int i = 1; i < 7; i++) {
      if (handleCanMove(selectedPiece, i, currentPlayer)) {
        Map<Piece, Integer> complementaryPieces = new HashMap<>();
        for (Piece piece : currentPlayer.getGamePieceList()) {
          if (handleCanMove(piece, 7 - i, currentPlayer) && !piece.equals(selectedPiece)
              && piece.leftBase() && (determineNewLocation(currentPlayer, piece.getLocation(), 7-i)!=determineNewLocation(currentPlayer, selectedPiece.getLocation(), i))) {
            complementaryPieces.put(piece, determineNewLocation(currentPlayer, piece.getLocation(), 7-i));
          }
        }
        if (!complementaryPieces.isEmpty()) {
          moveOptions.put(determineNewLocation(currentPlayer, selectedPiece.getLocation(),i), complementaryPieces);
        }
      }
    }
    return moveOptions;
  }

  /**
   * Checks to see if any of the current player's pieces share a location with any opponent's pieces. If so it bumps them back to base.
   *
   * @param currentPlayer the current player
   * @param otherPlayers list of other players
   * @return the list of pieces that got sent back to base
   */
  public List<Piece> checkSharedSpace(Player currentPlayer, List<Player> otherPlayers) {
    List<Piece> ret = new ArrayList<>();
    for(Piece piece : currentPlayer.getGamePieceList()){
      for (Player other : otherPlayers) {
        for (Piece otherPiece : other.getGamePieceList()) {
          if (piece.getLocation() == otherPiece.getLocation() && otherPiece.getLocation()>=0) {
            otherPiece.setLocation(-1);
            otherPiece.setLeftBase(false);
            ret.add(otherPiece);
          }
        }
      }
    }
    return ret;
  }


  /**
   * This method checks to see if there were any pieces along a slide that was landed on. If there are pieces, the pieces along the slide get bumped back to base.
   *
   * @param slideLocation the start of the slide
   * @param slideLength the end of the slide
   * @param otherPlayers list of all all players
   * @return list of pieces that got bumped back to base
   */
  public List<Piece> checkSlideCollision(int slideLocation, int slideLength, List<Player> otherPlayers){
    List<Piece> ret = new ArrayList<>();
    for (Player other : otherPlayers) {
      for (Piece otherPiece : other.getGamePieceList()) {
        if(otherPiece.getLocation()>slideLocation && otherPiece.getLocation()<=slideLocation+slideLength){
          otherPiece.setLocation(-1);
          otherPiece.setLeftBase(false);
          ret.add(otherPiece);
        }
      }
    }
    return ret;
  }

}
