package ooga.cards;

public enum CardValue {
  ONE(1, CardBehavior.moveManOut),
  TWO(2, CardBehavior.moveManOut),
  THREE(3, CardBehavior.moveNumericAmount),
  FOUR(-4, CardBehavior.moveNumericAmount),
  FIVE(5, CardBehavior.moveNumericAmount),
  SORRY(0, CardBehavior.sorry),
  SEVEN(7, CardBehavior.splitSeven),
  EIGHT(8, CardBehavior.moveNumericAmount),
  TEN(10, CardBehavior.handleTen),
  ELEVEN(11, CardBehavior.handleEleven),
  TWELVE(12, CardBehavior.moveNumericAmount);

  private final int value;
  private final CardBehavior cardBehavior;
  CardValue(int value, CardBehavior cardBehavior){
    this.value = value;
    this.cardBehavior = cardBehavior;
  }

  public int getValue(){
    return this.value;
  }

  public CardBehavior getCardBehavior() {
    return this.cardBehavior;
  }
}
