package com.jitterted.ebp.blackjack;

import org.fusesource.jansi.Ansi;

import java.util.Scanner;
import java.util.stream.Collectors;

import static org.fusesource.jansi.Ansi.ansi;

public class Game {

  private final Deck deck;

  private final Hand dealerHand = new Hand();
  private final Hand playerHand = new Hand();
  private int playerBalance = 0;
  private int playerBet = 0;

  public static void main(String[] args) {
    Game game = new Game();

    System.out.println(ansi()
                           .bgBright(Ansi.Color.WHITE)
                           .eraseScreen()
                           .cursor(1, 1)
                           .fgGreen().a("Welcome to")
                           .fgRed().a(" Jitterted's")
                           .fgBlack().a(" BlackJack"));


    game.initialDeal();
    game.play();

    System.out.println(ansi().reset());
  }

  public Game() {
    deck = new Deck();
  }

  void display(Hand hand) {
    System.out.println(hand.cardStream()
                           .map(Card::display)
                           .collect(Collectors.joining(
                               ansi().cursorUp(6).cursorRight(1).toString())));
  }

  public void initialDeal() {
    dealRoundOfCardsPlayerFirst();
    dealRoundOfCardsPlayerFirst();
  }

  private void dealRoundOfCardsPlayerFirst() {
    dealCardToPlayer();
    dealCardToDealer();
  }

  private void dealCardToDealer() {
    dealerHand.drawCard(deck);
  }

  private void dealCardToPlayer() {
    playerHand.drawCard(deck);
  }

  public void play() {
    boolean playerBusted = playerPlays();

    dealerPlays(playerBusted);

    displayFinalGameState();

    determineGameOutcome(playerBusted);
  }

  private void determineGameOutcome(boolean playerBusted) {
    if (playerBusted) {
      System.out.println("You Busted, so you lose.  💸");
      // playerLoses()
    } else if (dealerHand.isBusted()) {
      System.out.println("Dealer went BUST, Player wins! Yay for you!! 💵");
      // playerWins()
    } else if (playerHand.beats(dealerHand)) {
      System.out.println("You beat the Dealer! 💵");
      // playerWins()
    } else if (dealerHand.pushes(playerHand)) {
      System.out.println("Push: The house wins, you Lose. 💸");
      // playerPushes()
    } else {
      System.out.println("You lost to the Dealer. 💸");
      // playerLoses()
    }
  }

  private boolean playerPlays() {
    // get Player's decision: hit until they stand, then they're done (or they go bust)
    boolean playerBusted = false;
    while (!playerBusted) {
      displayGameState();
      String playerChoice = inputFromPlayer().toLowerCase();
      if (playerStands(playerChoice)) {
        break;
      }
      if (playerHits(playerChoice)) {
        dealCardToPlayer();
        if (playerHand.isBusted()) {
          playerBusted = true;
        }
      } else {
        System.out.println("You need to [H]it or [S]tand");
      }
    }
    return playerBusted;
  }

  private void dealerPlays(boolean playerBusted) {
    // Dealer makes its choice automatically based on a simple heuristic (<=16, hit, 17>stand)
    if (!playerBusted) {
      while (dealerHand.dealerShouldHit()) {
        dealCardToDealer();
      }
    }
  }

  private boolean playerHits(String playerChoice) {
    return playerChoice.startsWith("h");
  }

  private boolean playerStands(String playerChoice) {
    return playerChoice.startsWith("s");
  }

  private String inputFromPlayer() {
    System.out.println("[H]it or [S]tand?");
    Scanner scanner = new Scanner(System.in);
    return scanner.nextLine();
  }

  private void displayGameState() {
    System.out.print(ansi().eraseScreen().cursor(1, 1));
    System.out.println("Dealer has: ");
    System.out.println(dealerHand.displayFirstCard()); // first card is Face Up

    // second card is the hole card, which is hidden
    displayBackOfCard();

    System.out.println();
    System.out.println("Player has: ");
    display(playerHand);
    System.out.println(" (" + playerHand.displayValue() + ")");
  }

  private void displayBackOfCard() {
    System.out.print(
        ansi()
            .cursorUp(7)
            .cursorRight(12)
            .a("┌─────────┐").cursorDown(1).cursorLeft(11)
            .a("│░░░░░░░░░│").cursorDown(1).cursorLeft(11)
            .a("│░ J I T ░│").cursorDown(1).cursorLeft(11)
            .a("│░ T E R ░│").cursorDown(1).cursorLeft(11)
            .a("│░ T E D ░│").cursorDown(1).cursorLeft(11)
            .a("│░░░░░░░░░│").cursorDown(1).cursorLeft(11)
            .a("└─────────┘"));
  }

  private void displayFinalGameState() {
    System.out.print(ansi().eraseScreen().cursor(1, 1));
    System.out.println("Dealer has: ");
    display(dealerHand);
    System.out.println(" (" + dealerHand.displayValue() + ")");

    System.out.println();
    System.out.println("Player has: ");
    display(playerHand);
    System.out.println(" (" + playerHand.displayValue() + ")");
  }

  public void playerDeposits(int amount) {
    playerBalance += amount;
  }

  public void playerBets(int betAmount) {
    playerBalance -= betAmount;
    playerBet = betAmount;
  }

  public int playerBalance() {
    return playerBalance;
  }

  public void playerWins() {
    playerBalance += playerBet * 2;
  }

  public void playerLoses() {
    playerBalance += playerBet * 0;
  }

  public void playerPushes() {
    playerBalance += playerBet * 1;
  }

  public void playerWinsBlackjack() {
    playerBalance += playerBet * 2.5;
  }
}
