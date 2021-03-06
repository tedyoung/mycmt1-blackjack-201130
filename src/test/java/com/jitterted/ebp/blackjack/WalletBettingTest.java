package com.jitterted.ebp.blackjack;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class WalletBettingTest {

  @Test
  public void walletWith12Bet8BalanceIs4() throws Exception {
    // GIVEN
    Wallet wallet = new Wallet();
    wallet.addMoney(12);

    // WHEN
    wallet.bet(8);

    // THEN
    assertThat(wallet.balance())
        .isEqualTo(12 - 8); // EVIDENT DATA
  }

  @Test
  public void walletWith27Bet7AndBet9BalanceIs11() throws Exception {
    Wallet wallet = new Wallet();
    wallet.addMoney(27);

    wallet.bet(7);
    wallet.bet(9);

    assertThat(wallet.balance())
        .isEqualTo(27 - 7 - 9);
  }

  @Test
  public void walletWhenBetAmountOfBalanceIsEmpty() throws Exception {
    Wallet wallet = new Wallet();
    wallet.addMoney(33);

    wallet.bet(33);

    assertThat(wallet.isEmpty())
        .isTrue();
  }

  @Test
  public void betMoreThanBalanceThrowsException() throws Exception {
    Wallet wallet = new Wallet();
    wallet.addMoney(73);

    assertThatThrownBy(() -> {
      wallet.bet(74);
    }).isInstanceOf(IllegalArgumentException.class);
  }

}
