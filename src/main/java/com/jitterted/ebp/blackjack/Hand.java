package com.jitterted.ebp.blackjack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Hand {
  private final List<Card> cards = new ArrayList<>();

  public Hand() {
  }

  public Hand(List<Card> cards) {
    this.cards.addAll(cards);
  }

  void drawCard(Deck deck) {
    cards.add(deck.draw());
  }

  String displayValue() {
    return String.valueOf(value());
  }

  private int value() {
    int handValue = cards
        .stream()
        .mapToInt(Card::rankValue)
        .sum();

    // does the hand contain at least 1 Ace?
    boolean hasAce = cards
        .stream()
        .anyMatch(card -> card.rankValue() == 1);

    // if the total hand value <= 11, then count the Ace as 11 by adding 10
    if (hasAce && handValue <= 11) {
      handValue += 10;
    }

    return handValue;
  }

  public Stream<Card> cardStream() {
    return cards.stream();
  }

  String displayFirstCard() {
    return cards.get(0).display();
  }

  boolean dealerShouldHit() {
    return value() <= 16;
  }

  boolean isBusted() {
    return value() > 21;
  }

  boolean pushes(Hand hand) {
    return value() == hand.value();
  }

  boolean beats(Hand hand) {
    return hand.value() < value();
  }

  public boolean valueEquals(int value) {
    return value() == value;
  }
}
