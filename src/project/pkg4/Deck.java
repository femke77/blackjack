/*
Name: Megan Meyers
Date:4/23/15
Project: CS182 Project 4 LinkLists --- BLACKJACK
Description: Class Deck extends CardList, has one method only for creating the entire deck with appropriate suit and value for each card.
//Any game can be built using this Deck Class. 
 */
package project.pkg4;

public class Deck extends CardList {


   //you can change this to accept (int decks) as a parameter and make a while loop: while (decks-- >0) to make as many decks as you want

    public Deck() { //makes the 52 cards (13 of each suit of 4)
   
        for (int value = 1; value < 14; value++) {           
            for (Card.Suit suit : Card.Suit.values()) {              
                insertCard(new Card(value, suit)); //numCards++ happens with insertcard
               
            }  
            
        }
       
    }

  
  
    
   
    
    
}//end class
