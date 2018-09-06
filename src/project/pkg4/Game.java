/*
 Name: Megan Meyers
 Date:4/23/15
 Project: CS182 Project 4 LinkLists --- BLACKJACK
 Description: GAME class calls the CardList constructor to create the player and dealer hands. (also creates some TextFields). Holds the methods 
 that are necessary to playing and scoring. The game is using 4 CardLists. (FIX THE PROBLEM WITH WHAT HAPPENS WHEN THE PLAYER BUSTS)
 */
package project.pkg4;

import javax.swing.*;

public class Game {

    static Deck theDeck;
    static CardList player, dealer, discard;
    static JTextField result, other, more, hand;
    private static int wins, hands; //if these variable are limited to this one class only, do i have to use get/set methods?

    public Game() {
        theDeck = new Deck();
        theDeck.shuffle();
        player = new CardList();
        dealer = new CardList();
        discard = new CardList();
        result = new JTextField(25);
        result.setHorizontalAlignment(JTextField.CENTER);
        other = new JTextField(20);
        more = new JTextField(8);
        hand = new JTextField(8);
        Project4.southPanel.add(result);
        Project4.southPanel.add(other);
        Project4.westPanel.add(more);
        Project4.westPanel.add(hand);
        deal();
    }

    public static void deal() { //deals the cards and makes sure there are enough cards to play, checks for immediate blackjack by either, clears old hands
        if (player.getNumCards() > 0 || dealer.getNumCards() > 0) {
            clear();
        }
        if (theDeck.getNumCards() < 8) {
            while (discard.getFirstCard() != null) {
                theDeck.insertCard(discard.deleteCard(0));
            }
            theDeck.shuffle();
            other.setText("LOW DECK, add discards & reshuffle");
        }
        player.insertCard(theDeck.deleteCard(0));
        player.insertCard(theDeck.deleteCard(0));
        dealer.insertCard(theDeck.deleteCard(0));
        dealer.insertCard(new Card(0, Card.Suit.CLUB)); //card back
        if ((scoreHand(player)) == 21 && chkDealerBJ() == false) {
            result.setText("PLAYER BLACKJACK!!!");
            showDealer();
            wins++;
        } else if ((scoreHand(player) == 21 && chkDealerBJ() == true)){
             result.setText("Player/Dealer Blackjack, PUSH");
             showDealer();                             
        } else if (chkDealerBJ() == true) {//check "under the card back" b/c next win could be if the hidden card makes a BJ for the dealer
            result.setText("DEALER BLACKJACK!!");//debug
            showDealer();
            discard.insertCard(player.deleteCard(0)); //clear the player hand right away
            discard.insertCard(player.deleteCard(0));
        } else {
            scoreHand(player);
        }
        hands++;
        hand.setText("Hands " + hands);
        more.setText("Wins: " + wins);
    }

    public static int scoreHand(CardList hand) { //method will score any hand and appropriately adjust ace high/low
        Card temp = hand.getFirstCard();
        int total = 0;
        boolean ace = false;
        for (int i = 0; i < hand.getNumCards(); i++) {
            int value = temp.getCardValue();
            if (value > 10) {
                value = 10;
            }
            if (temp.getCardValue() == 1) {
                ace = true;
            }
            total += value;
            temp = temp.getNextCard();
        } if (ace == true && ((total + 10) <= 21)) {
            total += 10;
        } result.setText("Hand Total: " + total);
        return total;
    }

    public static boolean chkDealerBJ() { //method returns a boolean true if the dealer has blackjack
        Card temp = theDeck.getFirstCard();//peek at the next card coming up on the deck, but don't link it yet. 
        int temptotal = temp.getCardValue();
        if (temp.getCardValue() > 10) {
            temptotal = 10;
        }
        return (scoreHand(dealer) + temptotal) == 21;
    }

    public static void stand() { // player done, dealer flips CB, draws if needed, and outcome determined. 
        other.setText(""); //i don't want the low deck message there all the time so clear it. 
        int playerScore = scoreHand(player);
        int dealerScore = scoreHand(dealer); //gives you the score of one card when done at this point
        Card temp = theDeck.getFirstCard();
        dealer.deleteCard(0); //take out the card back       
        while (dealerScore + (temp.getCardValue()) == 21) { //initial check for dealer BJ was false b/c first card on deck was not such that dealer had 21, but if player hit
            //takes that card and the NEXT card is such that dealer WILL have BJ, that is a problem, looks like we missed initial dealer BJ. to solve it, just shuffle the deck until BJ won't happen.
            theDeck.shuffle();
        }
        dealer.insertCard(theDeck.deleteCard(0));
        while ((scoreHand(dealer) < 17)) {//dealer takes cards until total is 16 or greater
            dealer.insertCard(theDeck.deleteCard(0));
        }
        //now score the game:   
        if (playerScore > 21 && scoreHand(dealer) > 21) {
            result.setText("PLAYER/DEALER BUST");
        } else if (playerScore == (scoreHand(dealer))) {
            result.setText("PUSH, NO WINNER");
        } else if (playerScore > 21) {
            result.setText("PLAYER BUST. DEALER WINS with " + (scoreHand(dealer)));
        } else if (playerScore > scoreHand(dealer)) {
            result.setText("PLAYER WINS with " + playerScore);
            wins++;
        } else if (scoreHand(dealer) > 21) {
            result.setText("Dealer Bust, PLAYER WINS with " + playerScore+ "!!!");
            wins++;
        } else {
            result.setText("Dealer Wins with " + (scoreHand(dealer))); //debug
        }
        more.setText("Wins: " + wins);
    }

    public static void hit() { //player hit but at 21 or bust stand() is automatically triggered
        player.insertCard(theDeck.deleteCard(0));
        if (scoreHand(player) >= 21) {
            stand();
        }
    }

    public static void clear() {
        while (dealer.getFirstCard() != null) {
            Card temp = dealer.getFirstCard(); //extra code in the if/else is to guarantee the CBack never gets into the discard pile, this was only happening if you hit "deal" over and over..
            if (temp.getCardValue() == 0) {
                dealer.deleteCard(0);
            } else {
                discard.insertCard(dealer.deleteCard(0));
            }
        }
        while (player.getFirstCard() != null) {
            discard.insertCard(player.deleteCard(0));
        }
    }

    public static void showDealer(){
        dealer.deleteCard(0); //delete the Card back, replace it with the next card so dealers hand can be seen.
        dealer.insertCard(theDeck.deleteCard(0));
    }
}//end game

