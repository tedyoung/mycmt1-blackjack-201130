package com.jitterted.ebp.blackjack;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class HandValueAceTest {

  private static final Suit DUMMY_SUIT = Suit.DIAMONDS;

  @Test
  public void handWithOneAceTwoCardsIsValuedAt11() throws Exception {
    var cards = List.of(new Card(DUMMY_SUIT, "A"),
                        new Card(DUMMY_SUIT, "5"));
    Hand hand = new Hand(cards);

    assertThat(hand.valueEquals(11 + 5))
        .describedAs("Value was " + hand.displayValue())
        .isTrue();
  }

  @Test
  public void handWithOneAceAndOtherCardsEqualTo11IsValuedAt1() throws Exception {
    var cards = List.of(new Card(DUMMY_SUIT, "A"),
                        new Card(DUMMY_SUIT, "8"),
                        new Card(DUMMY_SUIT, "3"));
    Hand hand = new Hand(cards);

    assertThat(hand.valueEquals(1 + 8 + 3))
        .describedAs("Value was " + hand.displayValue())
        .isTrue();
  }


  @Test
  public void handWithOneAceAndTenCardIsTotalValueOf21() throws Exception {
    var cards = List.of(new Card(DUMMY_SUIT, "A"),
                        new Card(DUMMY_SUIT, "Q"));
    Hand hand = new Hand(cards);

    assertThat(hand.valueEquals(11 + 10))
        .describedAs("Value was " + hand.displayValue())
        .isTrue();
  }


}
