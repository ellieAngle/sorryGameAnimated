# OOGA Lab Discussion
## Names and NetIDs
Hayden Lau (hpl5)
Ellie Angle (ega12)
Nick DeCapite (ned13)
Maddie Cecchini (mjc88)

## Fluxx

### High Level Design Ideas


### CRC Card Classes

This class's purpose or value is to manage something:
```java
 public abstract class Card implements Function {
    private Function cardFunction
    public Value getValue ()
 }
```

This class's purpose or value is to manage something:
```java
 public interface Function {
     void cardAction()
 }
```

This class's purpose or value is to be useful:
```java
 public class GoalCard extends Card {
     public void cardAction()
 }
```


This class's purpose or value is to be useful:
```java
 public class RuleCard extends Card {
     public void cardAction()
 }
```

This class's purpose or value is to be useful:
```java
 public class ActionCard extends Card {
     public void cardAction()
 }
```

This class's purpose or value is to be useful:
```java
 public class Player {
     private Card cards
     public Card playCard(Card c)
     public void receiveCard(Card c)
     public Card giveCard()
     public void checkWins()
 }
```


### Use Cases

A player plays a Goal card, changing the current goal, and wins the game.
```java
Player.playCard(GoalCard)
Player.checkWins()

```
A player plays an Action card, allowing him to choose cards from another player's hand and play them.
```java
Player1.playCard(ActionCard)
Player1.receiveCard(Player2.giveCard())
```

A player plays a Rule card, adding to the current rules to set a hand-size limit, requiring all players to immediately drop cards from their hands if necessary.
```java
Player1.playCard(RuleCard)
forEach(Player player : players){
player.giveCard()
}
```