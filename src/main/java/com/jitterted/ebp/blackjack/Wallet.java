package com.jitterted.ebp.blackjack;

public class Wallet {

  private int balance = 0;

  public boolean isEmpty() {
    return balance == 0;
  }

  public void addMoney(int amount) {
    ensureAmountGreaterThanOrEqualToZero(amount);
    balance += amount;
  }

  public int balance() {
    return balance;
  }

  public void bet(int amount) {
    ensureSufficientBalance(amount);
    balance -= amount;
  }

  private void ensureSufficientBalance(int amount) {
    if (amount > balance) {
      throw new IllegalArgumentException();
    }
  }

  private void ensureAmountGreaterThanOrEqualToZero(int amount) {
    if (amount < 0) {
      throw new IllegalArgumentException();
    }
  }
}
