/*
Name: Megan Meyers
Date:4/23/15
Project: CS182 Project 4 LinkLists --- BLACKJACK
Description: Class CardList creates the LinkLists. Much of this is code given by Prof. Ferguson. I modified the constructor to be no-arg, and only 
sets first to null and numCards to 0. I also added a getNumCards() method. From this class the player cardlist and dealer cardlist are created.
The methods for changing anything about a CardList are here. 
 */
package project.pkg4;

class CardList {

    protected Card first = null;
    protected int numCards = 0;
 
    
    public CardList() {
        first = null;
        numCards= 0;       
    }

    public Card getFirstCard() {
        return first;
    }

    public Card deleteCard(int cardnum) {
        Card target, targetprevious;
        if (numCards == 0) {
            return null;   // not enough cards to delete that one
        } else {
            numCards--;           
        }
        target = first;
        targetprevious = null;
        while (cardnum-- > 0) { //loops from cardnum down to 0
            targetprevious = target;
            target = target.getNextCard();
            if (target == null) {
                return null;  // error, card not found
            }
        }       
        if (targetprevious != null) {
            targetprevious.setNext(target.getNextCard());
        } else {
            first = target.getNextCard();
        }       
        return target;
    }

    public void insertCard(Card target) {
        numCards++;
        if (first != null) {
            target.setNext(first);
        } else {
            target.setNext(null);
        }
        first = target;
       
    }

    public void shuffle() {        
        for (int i = 0; i < 300; i++) {
            int rand = (int) (Math.random() * 100) % numCards; 
            Card temp = deleteCard(rand);
            if (temp != null) {
                insertCard(temp);
            }
        }  // end for loop
    }   // end shuffle

    public int getNumCards() {
        return numCards; //return number of cards in the hand (in the list)
    }

    
    
}    // end class CardList
