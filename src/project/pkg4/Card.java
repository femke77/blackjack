/*
Name: Megan Meyers
Date:4/23/15
Project: CS182 Project 4 LinkLists --- BLACKJACK
Description: Card class extends link, the LinkLists are to be made up of these objects. each object has a suit, a value and an image, those are
the named variables declared as global. I chose to use enums for the Suit instead of just strings. 
 */
package project.pkg4;

import java.awt.Image;


class Card extends Link {

    protected Image cardimage;    
    protected Suit suit;
    protected int value;


    public enum Suit {
        CLUB, DIAMOND, HEART, SPADE
    }

    
    public Card(int value, Suit suit)  {
        this.value = value;
        this.suit = suit;
        cardimage = Project4.load_picture("images/"+suit+"-"+value+".gif");
       
        if (cardimage == null) {
            System.out.println("Error - image failed to load: ");
            System.exit(-1);
        }       
    }
    
     
    public Card getNextCard() { //method returns next Card
        return (Card) next;
    }

    public Image getCardImage() { //method returns Image object 
        return cardimage;
    }
    
    public Suit getCardSuit(){
        return suit;
    }
    
    public int getCardValue(){
        return value;
    }

}  //end class Card
